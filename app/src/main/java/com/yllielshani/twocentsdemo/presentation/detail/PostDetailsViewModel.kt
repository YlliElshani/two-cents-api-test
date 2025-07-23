package com.yllielshani.twocentsdemo.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class PostDetailsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PostDto>>(UiState.Loading)
    val uiState: StateFlow<UiState<PostDto>> = _uiState.asStateFlow()

    fun loadPost(postId: String) {
        viewModelScope.launch {
            try {
                repository.fetchPostById(postId, "anon").onFailure {
                    Log.d("yll1", "API failed: $it")
                }.onSuccess {
                    _uiState.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load post")
            }
        }
    }
}