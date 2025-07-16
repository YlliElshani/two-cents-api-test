package com.yllielshani.twocentsdemo.presentation.items

import com.yllielshani.twocentsdemo.data.model.ItemDto

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data object Empty : HomeUiState()
    data class Success(val items: List<ItemDto>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}