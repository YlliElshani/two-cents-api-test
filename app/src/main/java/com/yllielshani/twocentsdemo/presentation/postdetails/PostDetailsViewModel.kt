package com.yllielshani.twocentsdemo.presentation.postdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yllielshani.twocentsdemo.data.model.CommentNode
import com.yllielshani.twocentsdemo.data.model.CommentsDto
import com.yllielshani.twocentsdemo.data.model.PollOption
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

    private val _pollOptions = MutableStateFlow<UiState<List<PollOption>>>(UiState.Empty)
    val pollOptions = _pollOptions.asStateFlow()

    private val _comments = MutableStateFlow<UiState<List<CommentNode>>>(UiState.Loading)
    val comments: StateFlow<UiState<List<CommentNode>>> = _comments

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
                    loadComments(postId)
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

    private fun loadComments(postId: String) {
        viewModelScope.launch {
            _comments.value = UiState.Loading
            repository.fetchCommentsPerPost(postId, "anon")
                .onSuccess { list -> _comments.value = UiState.Success(list.comments.toNodeForest(postId)) }
                .onFailure { _comments.value = UiState.Error("Failed to load comments") }
        }
    }

    private fun List<CommentsDto>.toNodeForest(postId: String): List<CommentNode> {
        val nodeMap = associate { it.uuid to CommentNode(it) }
        val roots = mutableListOf<CommentNode>()
        for (node in nodeMap.values) {
            val parent = node.comment.replyParentUuid
            if (parent == postId) {
                roots += node
            } else {
                nodeMap[parent]?.children?.add(node)
            }
        }
        return roots
    }
}