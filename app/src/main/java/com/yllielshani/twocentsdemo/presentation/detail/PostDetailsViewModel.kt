package com.yllielshani.twocentsdemo.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yllielshani.twocentsdemo.data.model.PollOption
import com.yllielshani.twocentsdemo.data.model.PollResultsWrapper
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

    private val _pollState = MutableStateFlow<UiState<PollResultsWrapper>>(UiState.Empty)
    val pollState: StateFlow<UiState<PollResultsWrapper>> = _pollState.asStateFlow()

    private val _pollOptions = MutableStateFlow<UiState<List<PollOption>>>(UiState.Empty)
    val pollOptions = _pollOptions.asStateFlow()

    fun loadPost(postId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.fetchPostById(postId, "anon")
                .onSuccess { post ->
                    _uiState.value = UiState.Success(post)
                    if (post.postType == 2) {
                        post.postMeta?.poll?.let { questions ->
                            loadPollResults(postId, questions)
                        }
                    }
                }
                .onFailure {
                    _uiState.value = UiState.Error("Failed to load post")
                }
        }
    }

    private fun loadPollResults(postId: String, questions: List<String>) {
        viewModelScope.launch {
            _pollOptions.value = UiState.Loading
            repository.fetchPollResults(postId, "anon")
                .onSuccess { wrapper ->
                    val options = questions.mapIndexedNotNull { idx, q ->
                        wrapper.results[idx.toString()]?.let {
                            PollOption(q, it.votes, it.averageBalance)
                        }
                    }
                    _pollOptions.value = UiState.Success(options)
                }
                .onFailure {
                    _pollOptions.value = UiState.Error("Failed to load poll")
                }
        }
    }
}