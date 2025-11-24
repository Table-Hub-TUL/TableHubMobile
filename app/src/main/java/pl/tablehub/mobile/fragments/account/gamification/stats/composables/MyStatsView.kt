package pl.tablehub.mobile.fragments.account.gamification.stats.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.shared.composables.BackButton
import pl.tablehub.mobile.ui.theme.*

fun <A, B, C> List<A>.zip3(b: List<B>, c: List<C>): List<Triple<A, B, C>> =
    this.zip(b.zip(c)) { a, (b, c) -> Triple(a, b, c) }

@Composable
fun MyStatsView(
    modifier: Modifier = Modifier,
    points: Int = 450,
    reportsCount: Int = 12,
    ranking: Int = 7,
    onBackClick: () -> Unit = {},
    onRewardsClick: () -> Unit = {},
    onRankingsClick: () -> Unit = {},
    onAchievementsClick: () -> Unit = {}
) {
    val dims = rememberGlobalDimensions()
    val spacingFactor = 1.25f
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
            .padding(dims.paddingBig)
    ) {
        BackButton(
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.height(3 * spacingFactor * dims.paddingLarge))
        Card( /* Title card */
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dims.textFieldCornerRadius),
            colors = CardDefaults.cardColors(containerColor = TERTIARY_COLOR),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = stringResource(R.string.my_stats),
                fontSize = dims.textSizeHuge,
                fontWeight = FontWeight.Bold,
                color = SECONDARY_COLOR,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dims.paddingHuge),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(spacingFactor * dims.paddingHuge))
        Card( /* Points card */
            modifier = Modifier
                .fillMaxWidth()
                .border(color = TERTIARY_COLOR, width = 2.dp, shape = RoundedCornerShape(dims.buttonCornerRadius)),
            shape = RoundedCornerShape(dims.buttonCornerRadius),
            colors = CardDefaults.cardColors(containerColor = SECONDARY_COLOR),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dims.paddingBig),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${stringResource(R.string.gam_points)} $points",
                    fontSize = dims.textSizeVeryBig,
                    fontWeight = FontWeight.Bold,
                    color = TERTIARY_COLOR
                )
                Spacer(modifier = Modifier.width(dims.paddingSmall))
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = PRIMARY_COLOR,
                    modifier = Modifier.size(dims.iconSize)
                )
            }
        }
        Spacer(modifier = Modifier.height(dims.paddingHuge))
        Row( /* Stats grid */
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dims.paddingLarge)
        ) {
            val icons = listOf(Icons.Outlined.BarChart, Icons.AutoMirrored.Filled.TrendingUp)
            val values = listOf(reportsCount, ranking)
            val captions = listOf(R.string.gam_reports, R.string.gam_ranking)
            for ((icon, value, caption) in icons.zip3(values, captions)) {
                StatCard(
                    iconVec = icon,
                    value = value.toString(),
                    label = stringResource(caption),
                    iconTint = PRIMARY_COLOR,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        val onClicks = listOf(onRewardsClick, onRankingsClick, onAchievementsClick)
        val captions = listOf(R.string.gam_rewards, R.string.gam_reports, R.string.gam_achievements)
        for ((onClick, caption) in onClicks.zip(captions)) {
            Spacer(modifier = Modifier.height(1.5 * dims.paddingLarge))
            ActionButton(
                text = stringResource(caption),
                onClick = onClick
            )
        }
    }
}