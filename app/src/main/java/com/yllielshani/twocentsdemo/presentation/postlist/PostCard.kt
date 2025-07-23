package com.yllielshani.twocentsdemo.presentation.postlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yllielshani.twocentsdemo.data.model.PostDto
import com.yllielshani.twocentsdemo.presentation.common.components.IconTextRow
import com.yllielshani.twocentsdemo.presentation.common.components.UserAssets
import com.yllielshani.twocentsdemo.presentation.common.components.UserInformation
import com.yllielshani.twocentsdemo.presentation.common.components.getSubscriptionType
import com.yllielshani.twocentsdemo.presentation.common.modifiers.formatCurrency

@Composable
fun PostCard(
    number: Int,
    item: PostDto,
    onClick: () -> Unit,
    onPosterNetWorthClick: () -> Unit,
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
                    onClick = onPosterNetWorthClick
                )
                UserAssets(number = number)
            }
            Spacer(Modifier.height(8.dp))
            Text(item.title, style = MaterialTheme.typography.titleSmall, color = Color.White)
            Spacer(Modifier.height(8.dp))
            UserInformation(poster = item.authorMetaDto)
        }
    }
}