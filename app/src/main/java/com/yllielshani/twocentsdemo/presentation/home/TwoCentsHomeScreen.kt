package com.yllielshani.twocentsdemo.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yllielshani.twocentsdemo.R
import com.yllielshani.twocentsdemo.presentation.AppNavGraph
import com.yllielshani.twocentsdemo.presentation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoCentsHomeScreen(navController: NavHostController) {
    val backEntry by navController.currentBackStackEntryAsState()
    val route = backEntry?.destination?.route
    val isHome = route == NavRoutes.Items.route
    val isPost = route == NavRoutes.PostDetails.route
    val isAuthor = route == NavRoutes.AuthorPosts.route
    val backColor = Color(0xFFFFA042)
    val appName = "twocents"

    Scaffold(topBar = {
        when {
            isHome -> CenterAlignedTopAppBar(
                title = { Text(appName) },
                actions = {
                    IconButton(onClick = { /* TODO: filter */ }) {
                        Icon(painterResource(R.drawable.ic_filter), contentDescription = "Filter")
                    }
                }
            )
            isPost -> CenterAlignedTopAppBar(
                title = { Text("Post") },
                navigationIcon = {
                    Row(
                        Modifier
                            .clickable { navController.popBackStack() }
                            .padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = backColor)
                        Spacer(Modifier.width(4.dp))
                        Text("Back", color = backColor)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: app action */ }) {
                        Icon(painterResource(R.drawable.ic_dollar_chip), contentDescription = "App")
                    }
                }
            )
            isAuthor -> CenterAlignedTopAppBar(
                title = { Text(appName) },
                navigationIcon = {
                    Row(
                        Modifier
                            .clickable { navController.popBackStack() }
                            .padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = backColor)
                        Spacer(Modifier.width(4.dp))
                        Text("Back", color = backColor)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: filter */ }) {
                        Icon(painterResource(R.drawable.ic_filter), contentDescription = "Filter")
                    }
                }
            )
            else -> TopAppBar(
                title = { Text(appName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    }) { padding ->
        Box(Modifier.padding(padding)) {
            AppNavGraph(navController)
        }
    }
}