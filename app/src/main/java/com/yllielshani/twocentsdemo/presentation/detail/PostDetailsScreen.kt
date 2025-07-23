package com.yllielshani.twocentsdemo.presentation.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yllielshani.twocentsdemo.R
import com.yllielshani.twocentsdemo.data.enums.VoteState
import com.yllielshani.twocentsdemo.data.model.PostDto
import com.yllielshani.twocentsdemo.presentation.UiState
import com.yllielshani.twocentsdemo.presentation.posts.IconTextRow
import com.yllielshani.twocentsdemo.presentation.posts.UserInformation
import com.yllielshani.twocentsdemo.presentation.posts.formatCurrency
import com.yllielshani.twocentsdemo.presentation.posts.getSubscriptionType

@Composable
fun PostDetailsRoute(
    postId: String,
    viewModel: PostDetailsViewModel = hiltViewModel(),
    onPosterNetWorthClick: (String) -> Unit,
    onOptionsClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(postId) { viewModel.loadPost(postId) }

    PostDetailsScreen(
        uiState = uiState,
        onRetry = { viewModel.loadPost(postId) },
        onPosterNetWorthClick = onPosterNetWorthClick,
        onOptionsClick = onOptionsClick
    )
}


@Composable
fun PostDetailsScreen(
    uiState: UiState<PostDto>,
    onRetry: () -> Unit,
    onPosterNetWorthClick: (String) -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        when (uiState) {
            UiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            UiState.Empty -> Text(
                text = "No post data",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )

            is UiState.Error -> ErrorState(
                message = uiState.message,
                onRetry = onRetry,
                modifier = Modifier.align(Alignment.Center)
            )

            is UiState.Success -> PostDetailsContent(
                item = uiState.data,
                onPosterNetWorthClick = onPosterNetWorthClick,
                onOptionsClick = onOptionsClick
            )
        }
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(message, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetry) { Text("Retry") }
    }
}

@Composable
private fun PostDetailsContent(
    item: PostDto,
    onPosterNetWorthClick: (String) -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(12.dp))

            IconTextRow(
                subscriptionTypeEnum = getSubscriptionType(item.authorMetaDto.subscriptionType.toInt()),
                amount = formatCurrency(item.authorMetaDto.balance),
                onClick = { onPosterNetWorthClick(item.authorUuid) }
            )

        }
        Spacer(Modifier.height(8.dp))

        Text(item.text, style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(12.dp))

        UserInformation(
            poster = item.authorMetaDto,
            postedAt = item.createdAt
        )

        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatDisplay(R.drawable.ic_arrow_up, item.upvoteCount, tint = Color(0xFFFFA042))
                StatDisplay(R.drawable.ic_comment_bubble, item.commentCount)
                StatDisplay(R.drawable.ic_views, item.viewCount)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(item.topic, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = "Options",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onOptionsClick)
                )
            }
        }
        Divider()
        UserActionsBar()
        Divider()
    }
}

@Composable
private fun StatDisplay(
    icon: Int,
    count: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = tint
        )
        Text(count.toString(), style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun UserActionsBar(
    modifier: Modifier = Modifier,
    onReplyClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    var voteState by remember { mutableStateOf(VoteState.NONE) }
    var defaultColor = Color(0xFFFFA042)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = if (voteState == VoteState.UPVOTED) Color(0xFFFFA042) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    voteState =
                        if (voteState == VoteState.UPVOTED) VoteState.NONE else VoteState.UPVOTED
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_up),
                contentDescription = "Upvote",
                modifier = Modifier.size(20.dp),
                tint = if (voteState != VoteState.NONE) Color.White else defaultColor
            )
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = if (voteState == VoteState.DOWNVOTED) Color(0xFFADD8E6) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    voteState =
                        if (voteState == VoteState.DOWNVOTED) VoteState.NONE else VoteState.DOWNVOTED
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = "Downvote",
                modifier = Modifier.size(20.dp),
                tint = if (voteState != VoteState.NONE) Color.White else defaultColor
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_reply),
            contentDescription = "Reply",
            modifier = Modifier
                .size(36.dp)
                .clickable(onClick = onReplyClick),
            tint = defaultColor
        )

        Icon(
            painter = painterResource(R.drawable.ic_share),
            contentDescription = "Share",
            modifier = Modifier
                .size(36.dp)
                .clickable(onClick = onShareClick),
            tint = defaultColor
        )
    }
}