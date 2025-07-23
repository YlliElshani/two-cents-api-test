package com.yllielshani.twocentsdemo.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yllielshani.twocentsdemo.data.enums.Filter
import com.yllielshani.twocentsdemo.data.model.PostDto
import com.yllielshani.twocentsdemo.data.repository.PostRepository
import com.yllielshani.twocentsdemo.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<PostDto>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<PostDto>>> = _uiState.asStateFlow()

    fun loadItems(filter: Filter = Filter.NewToday, secretKey: String? = null) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.fetchPosts(filter, secretKey)
                .fold(
                    onSuccess = { items ->
                        _uiState.value =
                            if (items.isEmpty()) UiState.Empty else UiState.Success(items)
                    },
                    onFailure = { error ->
                        _uiState.value = UiState.Error(error.localizedMessage.orEmpty())
                    }
                )
        }
    }


    fun loadPostsByAuthor(
        authorId: String,
        filter: Filter = Filter.NewToday,
        secretKey: String? = null
    ) = viewModelScope.launch {
        _uiState.value = UiState.Loading

        repository.fetchPostsPerAuthor(filter, secretKey, authorId)
            .fold(
                onSuccess = { posts ->
                    _uiState.value =
                        if (posts.isEmpty()) UiState.Empty
                        else UiState.Success(posts)
                },
                onFailure = { e ->
                    _uiState.value = UiState.Error(e.message ?: "Unknown error")
                }
            )
    }
}