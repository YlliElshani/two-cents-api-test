package com.yllielshani.twocentsdemo.presentation.postdetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yllielshani.twocentsdemo.R
import com.yllielshani.twocentsdemo.data.enums.VoteState
import com.yllielshani.twocentsdemo.data.model.CommentNode
import com.yllielshani.twocentsdemo.data.model.PollOption
import com.yllielshani.twocentsdemo.data.model.PostDto
import com.yllielshani.twocentsdemo.presentation.UiState
import com.yllielshani.twocentsdemo.presentation.common.components.ExpandableText
import com.yllielshani.twocentsdemo.presentation.common.components.IconTextRow
import com.yllielshani.twocentsdemo.presentation.common.components.UserInformation
import com.yllielshani.twocentsdemo.presentation.common.components.getSubscriptionType
import com.yllielshani.twocentsdemo.presentation.common.modifiers.formatCurrency
import com.yllielshani.twocentsdemo.ui.theme.CardBackground
import com.yllielshani.twocentsdemo.ui.theme.GoldenOrange

@Composable
fun PostDetailsRoute(
    postId: String,
    viewModel: PostDetailsViewModel = hiltViewModel(),
    onPosterNetWorthClick: (String) -> Unit,
    onOptionsClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val pollOptions by viewModel.pollOptions.collectAsState()
    val comments by viewModel.comments.collectAsState()

    LaunchedEffect(postId) { viewModel.loadPost(postId) }

    PostDetailsScreen(
        uiState = uiState,
        pollOptions = pollOptions,
        comments = comments,
        onRetry = { viewModel.loadPost(postId) },
        onPosterNetWorthClick = onPosterNetWorthClick,
        onOptionsClick = onOptionsClick
    )
}

@Composable
fun PostDetailsScreen(
    uiState: UiState<PostDto>,
    pollOptions: UiState<List<PollOption>>,
    comments: UiState<List<CommentNode>>,
    onRetry: () -> Unit,
    onPosterNetWorthClick: (String) -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        when (uiState) {
            UiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            UiState.Empty -> Text("No post data", Modifier.align(Alignment.Center))
            is UiState.Error -> ErrorState(uiState.message, onRetry, Modifier.align(Alignment.Center))
            is UiState.Success -> PostDetailsContent(
                post = uiState.data,
                pollState = pollOptions,
                commentsState = comments,
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
    post: PostDto,
    pollState: UiState<List<PollOption>>,
    commentsState: UiState<List<CommentNode>>,
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
            ExpandableText(
                text = post.title,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                modifier = Modifier.weight(1f),
                ignoreExpanded = true
            )

            Spacer(Modifier.width(12.dp))

            IconTextRow(
                subscriptionTypeEnum = getSubscriptionType(post.authorMetaDto.subscriptionType.toInt()),
                amount = formatCurrency(post.authorMetaDto.balance),
                onClick = { onPosterNetWorthClick(post.authorUuid) }
            )

        }
        Spacer(Modifier.height(8.dp))

        Text(post.text, style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(12.dp))


        if (post.postType == 2) {
            when (pollState) {
                UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> Text(pollState.message)
                is UiState.Success -> PollResultsOptionsContent(pollState.data)
                else -> Unit
            }
            Spacer(Modifier.height(16.dp))
        }

        UserInformation(
            poster = post.authorMetaDto,
            postedAt = post.createdAt
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
                StatDisplay(R.drawable.ic_arrow_up, post.upvoteCount, tint = GoldenOrange)
                StatDisplay(R.drawable.ic_comment_bubble, post.commentCount)
                StatDisplay(R.drawable.ic_views, post.viewCount)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(post.topic, style = MaterialTheme.typography.labelLarge)
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
        CommentsSection(commentsState, onNetWorthClick = onPosterNetWorthClick)
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
    val defaultColor = GoldenOrange
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
                    color = if (voteState == VoteState.UPVOTED) GoldenOrange else Color.Transparent,
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

@Composable
fun PollResultsOptionsContent(
    options: List<PollOption>,
    modifier: Modifier = Modifier
) {
    val totalVotes = options.sumOf { it.votes }.coerceAtLeast(1)
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 1.dp,
                color = CardBackground,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(10.dp)
    ) {
        options.forEach { opt ->
            val fraction by animateFloatAsState(
                targetValue = opt.votes.toFloat() / totalVotes,
                animationSpec = tween(durationMillis = 1800), label = ""
            )

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CardBackground)
            ) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GoldenOrange)
                )
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ExpandableText(
                        opt.question,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            opt.votes.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.width(8.dp))
                        Box(
                            Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_dollar_chip),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = GoldenOrange
                                )
                                Text(
                                    formatCurrency(opt.averageBalance),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun CommentsSection(
    state: UiState<List<CommentNode>>,
    onNetWorthClick: (String) -> Unit
) {
    when (state) {
        UiState.Loading -> CircularProgressIndicator()
        is UiState.Error -> Text(state.message)
        is UiState.Success -> state.data.forEach { CommentTree(it, 0, onNetWorthClick) }
        else -> Unit
    }
}

@Composable
private fun CommentTree(
    node: CommentNode,
    depth: Int,
    onNetWorthClick: (String) -> Unit
) {
    val indent = 12.dp * depth
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = indent, top = 8.dp, bottom = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconTextRow(
                subscriptionTypeEnum = getSubscriptionType(node.comment.authorMeta.subscriptionType.toInt()),
                amount = formatCurrency(node.comment.authorMeta.balance),
                onClick = { onNetWorthClick(node.comment.authorUuid) }
            )
            Spacer(Modifier.width(8.dp))
            UserInformation(poster = node.comment.authorMeta, postedAt = node.comment.createdAt)
        }
        Spacer(Modifier.height(4.dp))
        Text(node.comment.text, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(4.dp))
        StatDisplay(R.drawable.ic_arrow_up, node.comment.upvoteCount, tint = GoldenOrange)
    }
    node.children.forEach { CommentTree(it, depth + 1, onNetWorthClick) }
}