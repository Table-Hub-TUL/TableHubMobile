package pl.tablehub.mobile.fragments.account.gamification.achievements.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.v2.Achievement
import pl.tablehub.mobile.model.v2.UserProfile
import pl.tablehub.mobile.ui.shared.composables.BackButton
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.WHITE_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun AchievementsView(
    userProfile: UserProfile,
    achievementsList: List<Achievement>,
    onBackClick: () -> Unit
) {
    val dims = rememberGlobalDimensions()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(TERTIARY_COLOR)
                .padding(
                    start = dims.horizontalPadding,
                    end = dims.horizontalPadding,
                    top = dims.verticalPadding,
                    bottom = dims.paddingHuge * 2
                )
        ) {
            BackButton(
                onBackClick = onBackClick,
                backgroundColor = Color.Transparent,
                arrowColor = WHITE_COLOR,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = stringResource(R.string.gam_achievements),
                color = WHITE_COLOR,
                fontSize = dims.textSizeHeader,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = dims.paddingLarge)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dims.paddingLarge),
        ) {
            itemsIndexed(achievementsList) { index, achievement ->
                val isUnlocked = userProfile.points >= achievement.points

                val nextUnlocked = if (index < achievementsList.lastIndex) {
                    userProfile.points >= achievementsList[index + 1].points
                } else {
                    false
                }

                TimelineItem(
                    achievement = achievement,
                    isUnlocked = isUnlocked,
                    isNextUnlocked = nextUnlocked,
                    isLast = index == achievementsList.lastIndex
                )
            }
        }
    }
}