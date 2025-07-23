package com.yllielshani.twocentsdemo.presentation.common.modifiers

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.yllielshani.twocentsdemo.data.enums.SubscriptionTypeEnum
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.Duration
import java.time.Instant
import java.util.Locale

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

fun SubscriptionTypeEnum.textColor(): Color = when (this) {
    SubscriptionTypeEnum.Platinum -> Color(0xFF2D2C28)
    SubscriptionTypeEnum.Gold -> Color(0xFFFFD700)
    SubscriptionTypeEnum.Silver -> Color(0xFFC0C0C0)
    SubscriptionTypeEnum.Bronze -> Color(0xFFCD7F32)
}

fun formatCurrency(amount: BigDecimal, locale: Locale = Locale.US): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(amount)
}

fun formatRelativeTime(postedAt: Instant): String {
    val now = Instant.now()
    val duration = Duration.between(postedAt, now)
    return when {
        duration.toMinutes() < 1 -> "just now"
        duration.toMinutes() < 60 -> "${duration.toMinutes()} min ago"
        duration.toHours() < 24 -> "${duration.toHours()} hrs ago"
        duration.toDays() == 1L -> "yesterday"
        else -> "${duration.toDays()} days ago"
    }
}