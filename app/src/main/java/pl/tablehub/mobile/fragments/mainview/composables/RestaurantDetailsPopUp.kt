package pl.tablehub.mobile.fragments.mainview.composables

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.ui.shared.composables.PopUpWrapper
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import kotlin.math.round


@Composable
fun RatingStars(rating: Double, maxRating: Int = 5) {
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
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = tint,
        modifier = Modifier.size(24.dp)
    )
}

@Composable
internal fun PopUpText(
    text: String,
    fontSize: Int = 16,
    fontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier
) {
    Text (
        fontSize = fontSize.sp,
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
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(CORNER_ROUND_SIZE.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PRIMARY_COLOR)
    ) {
        Text(stringResource(strRes), color = SECONDARY_COLOR, fontSize = 13.sp)
    }
}

@Composable
fun RestaurantDetailsPopup(
    restaurant: Restaurant,
    sections: List<Section>,
    onDismissRequest: () -> Unit,
    onReportTable: (Restaurant) -> Unit,
    onMoreDetailsClick: (Restaurant) -> Unit
) {
    val availableTables by remember { mutableIntStateOf(sections.flatMap { it.tables }.count { it.status == TableStatus.AVAILABLE }) }
    PopUpWrapper(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PopUpText(
                text = restaurant.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24
            )
            PopUpText(
                text = buildString {
                    append("${restaurant.address.street} ${restaurant.address.streetNumber}")
                    restaurant.address.apartmentNumber?.let {
                        append("/$it")
                    }
                    append(", ${restaurant.address.postalCode}, ${restaurant.address.city}, ${restaurant.address.country}")
                }
            )
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
                modifier = Modifier.padding(bottom = 8.dp)
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