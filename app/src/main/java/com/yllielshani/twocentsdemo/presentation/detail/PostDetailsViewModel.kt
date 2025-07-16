package com.yllielshani.twocentsdemo.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yllielshani.twocentsdemo.data.model.ItemDto
import com.yllielshani.twocentsdemo.data.repository.ItemRepository
import com.yllielshani.twocentsdemo.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ItemDto>>(UiState.Loading)
    val uiState: StateFlow<UiState<ItemDto>> = _uiState.asStateFlow()

    fun loadPost(postId: String) {
        viewModelScope.launch {
            try {
                val post = repository.fetchItemById(postId)
                _uiState.value = UiState.Success(post)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load post")
            }
        }
    }
}