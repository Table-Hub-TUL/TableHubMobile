package pl.tablehub.mobile.fragments.restaurants.mainview.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v1.Section
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.model.v2.TableListItem
import pl.tablehub.mobile.ui.shared.composables.PopUpWrapper
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import kotlin.math.round
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions


@Composable
fun RatingStars(rating: Double, maxRating: Int = 5) {
    val dims = rememberGlobalDimensions()
    Row(
        modifier = Modifier.offset(y = (-2.5).dp)
    ) {
        val filledStars = round(rating).toInt()
        val unfilledStars = (maxRating - filledStars)
        repeat(filledStars) {
            StarIcon(imageVector = Icons.Filled.Star, tint = PRIMARY_COLOR)
        }
        repeat(unfilledStars) {
            StarIcon(imageVector = Icons.Outlined.Star, tint = TERTIARY_COLOR)
        }
    }
}

@Composable
fun StarIcon(imageVector: ImageVector, tint: Color) {
    val dims = rememberGlobalDimensions()
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = tint,
        modifier = Modifier.size(dims.contentIconSize)
    )
}

@Composable
internal fun PopUpText(
    text: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier
) {
    Text (
        fontSize = fontSize,
        fontWeight = fontWeight,
        text = text,
        color = TERTIARY_COLOR,
        modifier = modifier
    )
}

@Composable
internal fun PopUpButton(
    onClick: () -> Unit = {},
    strRes: Int,
    modifier: Modifier = Modifier
) {
    val dims = rememberGlobalDimensions()
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(CORNER_ROUND_SIZE.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PRIMARY_COLOR)
    ) {
        Text(stringResource(strRes), color = SECONDARY_COLOR, fontSize = dims.textSizeMinimal)
    }
}

@Composable
fun RestaurantDetailsPopup(
    restaurant: RestaurantListItem,
    tables: List<TableListItem>,
    onDismissRequest: () -> Unit,
    onReportTable: (RestaurantListItem) -> Unit,
    onMoreDetailsClick: (RestaurantListItem) -> Unit
) {
    val dims = rememberGlobalDimensions()
    val availableTables by remember { mutableIntStateOf(tables.count { it.tableStatus == TableStatus.AVAILABLE }) }
    PopUpWrapper(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.padding(dims.paddingBig),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dims.smallSpacing)
        ) {
            PopUpText(
                text = restaurant.name,
                fontWeight = FontWeight.Bold,
                fontSize = dims.textSizeLarge
            )
            PopUpText(text = restaurant.address.toString())
            PopUpText(
                text = "${stringResource(R.string.cuisines)}: ${
                    restaurant.cuisine.joinToString() { it.lowercase().replaceFirstChar { c -> c.uppercaseChar() } }
                }"
            )
            Row {
                PopUpText(text = stringResource(R.string.rating))
                RatingStars(restaurant.rating)
            }
            PopUpText(
                text = "${stringResource(R.string.available_tables)}: $availableTables",
                modifier = Modifier.padding(bottom = dims.paddingSmall)
            )
            Row {
                PopUpButton(
                    onClick = { onMoreDetailsClick(restaurant) },
                    strRes = R.string.more_details,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.25f))
                PopUpButton(
                    onClick = { onReportTable(restaurant) },
                    strRes = R.string.report_free_tables,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}