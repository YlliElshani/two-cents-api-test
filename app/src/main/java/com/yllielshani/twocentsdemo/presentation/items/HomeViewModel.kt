package com.yllielshani.twocentsdemo.presentation.items

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
class HomeViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<PostDto>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<PostDto>>> = _uiState.asStateFlow()

    init {
        loadItems()
    }


    fun loadItems() {
        viewModelScope.launch {
            try {
                val items = repository.fetchItems()
                _uiState.value = if (items.isEmpty()) UiState.Empty else UiState.Success(items)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load items")
            }
        }
    }
}