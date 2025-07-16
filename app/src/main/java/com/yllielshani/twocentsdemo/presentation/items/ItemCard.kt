package com.yllielshani.twocentsdemo.presentation.items

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.yllielshani.twocentsdemo.data.enums.Tier
import com.yllielshani.twocentsdemo.data.model.ItemDto
import com.yllielshani.twocentsdemo.data.model.PosterInfo
import com.yllielshani.twocentsdemo.utils.formatAmountWithDots

@Composable
fun ItemCard(
    number: Int,
    item: ItemDto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconTextRow(tier = item.tier, label = item.posterInfo.assetCount)
                NumberBadge(number = number)
            }
            Spacer(Modifier.height(8.dp))
            Text(item.description, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            Spacer(Modifier.height(8.dp))
            PlaceholderComposable(poster = item.posterInfo)
        }
    }
}

@Composable
fun IconTextRow(
    tier: Tier,
    label: Int,
    modifier: Modifier = Modifier
) {
    val shimmerColor = Color.White.copy(alpha = 0.4f)
    val borderColor = Color.LightGray
    val backgroundColor = Color.Black
    val cornerRadius = 32.dp

    val shimmerTranslate = rememberInfiniteTransition(label = "")
    val shimmerOffset by shimmerTranslate.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(Color.Transparent, shimmerColor, Color.Transparent),
        start = Offset.Zero,
        end = Offset(x = shimmerOffset, y = shimmerOffset)
    )

    val textColor = when (tier) {
        Tier.Gold -> Color(0xFFFFD700)
        Tier.Silver -> Color(0xFFC0C0C0)
        Tier.Bronze -> Color(0xFFCD7F32)
    }

    val formattedAmount = formatAmountWithDots(label)

    Row(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .border(1.dp, borderColor, RoundedCornerShape(cornerRadius))
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(shimmerBrush, blendMode = BlendMode.SrcOver)
                }
            }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = formattedAmount,
            style = MaterialTheme.typography.titleSmall,
            color = textColor
        )
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Tier Icon",
            tint = textColor,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun NumberBadge(
    number: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = number.toString(),
        style = MaterialTheme.typography.labelSmall,
        color = Color.White,
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}


@Composable
fun PlaceholderComposable(
    poster: PosterInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoItem(icon = Icons.Default.Star, text = poster.age.toString())
        InfoItem(icon = Icons.Default.ThumbUp, text = poster.gender)
        InfoItem(icon = Icons.Default.Place, text = poster.location)
    }
}

@Composable
private fun InfoItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}