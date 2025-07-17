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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yllielshani.twocentsdemo.data.enums.SubscriptionTypeEnum
import com.yllielshani.twocentsdemo.data.model.AuthorMetaDto
import com.yllielshani.twocentsdemo.data.model.PostDto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PostCard(
    number: Int,
    item: PostDto,
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
                IconTextRow(
                    subscriptionTypeEnum = getSubscriptionType(
                        item.authorMetaDto.subscriptionType.toInt()
                    ),
                    amount = formatCurrency(item.authorMetaDto.balance),
                )
                UserAssets(number = number)
            }
            Spacer(Modifier.height(8.dp))
            Text(item.text, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            Spacer(Modifier.height(8.dp))
            UserInformation(poster = item.authorMetaDto)
        }
    }
}

@Composable
fun IconTextRow(
    subscriptionTypeEnum: SubscriptionTypeEnum,
    amount: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(32.dp),
    containerColor: Color = Color.Black,
    borderColor: Color = Color.LightGray,
    borderWidth: Dp = 1.dp,
    padding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
) {
    Row(
        modifier = modifier
            .clip(shape)
            .background(containerColor)
            .border(borderWidth, borderColor, shape)
            .shimmer(durationMillis = 1200)
            .padding(padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = amount,
            style = MaterialTheme.typography.titleSmall,
            color = subscriptionTypeEnum.textColor()
        )
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = subscriptionTypeEnum.textColor(),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun UserAssets(
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
fun UserInformation(
    poster: AuthorMetaDto,
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
        InfoItem(icon = Icons.Default.Place, text = poster.arena)
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

fun getSubscriptionType(level: Int): SubscriptionTypeEnum {
    return SubscriptionTypeEnum.entries.firstOrNull { it.subscriptionLevel == level }
        ?: SubscriptionTypeEnum.Bronze
}


fun Modifier.shimmer(
    colors: List<Color> = listOf(Color.Transparent, Color.White.copy(alpha = 0.4f), Color.Transparent),
    shimmerWidth: Float = 200f,
    durationMillis: Int = 1200
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")
    val progress by transition.animateFloat(
        initialValue  = 0f,
        targetValue   = 1f,
        animationSpec = infiniteRepeatable(
            animation  = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    drawWithCache {
        val widthPx   = size.width
        val band      = shimmerWidth
        val offsetX   = (widthPx + band) * progress - band
        val brush     = Brush.linearGradient(
            colors,
            start = Offset(offsetX, 0f),
            end   = Offset(offsetX + band, 0f)
        )

        onDrawWithContent {
            drawContent()
            drawRect(brush, blendMode = BlendMode.SrcOver)
        }
    }
}

private fun SubscriptionTypeEnum.textColor(): Color = when (this) {
    SubscriptionTypeEnum.Platinum -> Color(0xFF2D2C28)
    SubscriptionTypeEnum.Gold -> Color(0xFFFFD700)
    SubscriptionTypeEnum.Silver -> Color(0xFFC0C0C0)
    SubscriptionTypeEnum.Bronze -> Color(0xFFCD7F32)
}

fun formatCurrency(amount: BigDecimal, locale: Locale = Locale.US): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(amount)
}