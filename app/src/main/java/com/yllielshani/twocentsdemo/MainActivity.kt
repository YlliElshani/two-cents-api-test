package com.yllielshani.twocentsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.yllielshani.twocentsdemo.presentation.items.HomeRoute
import com.yllielshani.twocentsdemo.presentation.items.HomeViewModel
import com.yllielshani.twocentsdemo.ui.theme.TwoCentsDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwoCentsDemoTheme {
                HomeRoute(viewModel)
            }
        }
    }
}