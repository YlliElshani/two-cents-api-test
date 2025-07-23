package com.yllielshani.twocentsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.yllielshani.twocentsdemo.presentation.home.TwoCentsHomeScreen
import com.yllielshani.twocentsdemo.ui.theme.TwoCentsDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwoCentsDemoTheme {
                val navController = rememberNavController()
                TwoCentsHomeScreen(navController)
            }
        }
    }
}