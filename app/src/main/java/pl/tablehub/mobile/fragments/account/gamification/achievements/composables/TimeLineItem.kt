package pl.tablehub.mobile.fragments.account.gamification.achievements.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.model.v2.Achievement
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

// Custom gray colors for the locked state
internal val LOCKED_CIRCLE_BG = Color(0xFFEEEEEE)
internal val LOCKED_LINE_COLOR = Color(0xFFBDBDBD)
internal val LOCKED_TEXT_COLOR = Color(0xFF9E9E9E)

@Composable
fun TimelineItem(
    achievement: Achievement,
    isUnlocked: Boolean,
    isNextUnlocked: Boolean,
    isLast: Boolean
) {
    val dims = rememberGlobalDimensions()
    val circleSize = dims.achievementSize
    val lineThickness = dims.paddingMedium

    val (iconEmoji, rangeText) = getVisualsForAchievement(achievement)

    val circleBgColor = if (isUnlocked) PRIMARY_COLOR else LOCKED_CIRCLE_BG
    val pointsTextColor = if (isUnlocked) PRIMARY_COLOR else LOCKED_TEXT_COLOR
    val titleTextColor = if (isUnlocked) TERTIARY_COLOR else LOCKED_TEXT_COLOR
    val lineColor = if (isUnlocked) PRIMARY_COLOR else LOCKED_LINE_COLOR

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height((dims.logoSize).dp)
    ) {
        Box(
            modifier = Modifier
                .width((dims.logoSize).dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(lineThickness)
                        .fillMaxHeight()
                        .background(lineColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .width(lineThickness)
                        .height(circleSize / 2)
                        .background(lineColor)
                )
            }

            Box(
                modifier = Modifier
                    .size(circleSize)
                    .clip(CircleShape)
                    .background(circleBgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconEmoji,
                    fontSize = dims.textSizeVeryBig
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(top = dims.paddingSmall)
                .fillMaxHeight(),
        ) {
            Text(
                text = achievement.title,
                color = titleTextColor,
                fontSize = dims.textSizeLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(dims.paddingSmall / 2))
            Text(
                text = rangeText,
                color = pointsTextColor,
                fontSize = dims.textSizeMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}