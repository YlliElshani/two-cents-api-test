package com.yllielshani.twocentsdemo.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yllielshani.twocentsdemo.R
import com.yllielshani.twocentsdemo.data.enums.SubscriptionTypeEnum
import com.yllielshani.twocentsdemo.data.model.AuthorMetaDto
import com.yllielshani.twocentsdemo.presentation.common.modifiers.formatRelativeTime
import com.yllielshani.twocentsdemo.presentation.common.modifiers.shimmer
import com.yllielshani.twocentsdemo.presentation.common.modifiers.textColor
import java.time.Instant

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    ignoreExpanded: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = text,
        maxLines = if (expanded || ignoreExpanded) Int.MAX_VALUE else maxLines,
        overflow = TextOverflow.Ellipsis,
        style = style,
        color = color,
        modifier = modifier.clickable { expanded = !expanded }
    )
}

fun getSubscriptionType(level: Int): SubscriptionTypeEnum {
    return SubscriptionTypeEnum.entries.firstOrNull { it.subscriptionLevel == level }
        ?: SubscriptionTypeEnum.Bronze
}

@Composable
fun UserInformation(
    modifier: Modifier = Modifier,
    poster: AuthorMetaDto,
    postedAt: Instant? = null,
) {
    val genderTint = when (poster.gender.lowercase()) {
        "m" -> Color(0xFF42A5F5)
        "f" -> Color(0xFFEC407A)
        else -> Color.White
    }

    if (postedAt != null) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(icon = painterResource(R.drawable.ic_age), text = poster.age.toString())
            InfoItem(icon = painterResource(R.drawable.ic_person), text = poster.gender, tint = genderTint)
            InfoItem(icon = painterResource(R.drawable.ic_location), text = poster.arena)
            InfoItem(icon = painterResource(R.drawable.ic_dollar_chip), text = formatRelativeTime(postedAt), tint = Color.Gray)
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoItem(icon = painterResource(R.drawable.ic_age), text = poster.age.toString())
            InfoItem(icon = painterResource(R.drawable.ic_person), text = poster.gender, tint = genderTint)
            InfoItem(icon = painterResource(R.drawable.ic_location), text = poster.arena)
        }
    }
}

@Composable
private fun InfoItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    text: String,
    tint: Color = Color.White,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
    padding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
    onClick: () -> Unit = {}
) {
    val contentColor = when (subscriptionTypeEnum) {
        SubscriptionTypeEnum.Platinum -> Color.White
        else -> subscriptionTypeEnum.textColor()
    }
    Row(
        modifier = modifier
            .clip(shape)
            .background(containerColor)
            .border(borderWidth, borderColor, shape)
            .shimmer(durationMillis = 1200)
            .clickable(onClick = onClick)
            .padding(padding)
            .widthIn(min = 60.dp, max = 140.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = amount,
            style = MaterialTheme.typography.titleSmall,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dollar_sign),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
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