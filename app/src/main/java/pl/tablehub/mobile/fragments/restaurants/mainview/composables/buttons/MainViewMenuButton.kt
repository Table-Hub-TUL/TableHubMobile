package pl.tablehub.mobile.fragments.restaurants.mainview.composables.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun MainViewMenuButton(
    onClick: () -> Unit,
    imgName: Int,
    stringFromRes: Int,
    modifier: Modifier = Modifier,
) {
    val dims = rememberGlobalDimensions()

    Row(
        modifier = modifier
            .padding(vertical = dims.paddingLarge, horizontal = dims.paddingMedium)
            .fillMaxWidth()
            .clip(RoundedCornerShape(dims.buttonCornerRadius))
            .background(PRIMARY_COLOR)
            .clickable{ onClick() }
            .padding(vertical = dims.paddingLarge, horizontal = dims.paddingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = imgName),
            contentDescription = stringResource(stringFromRes),
            modifier = modifier
                .padding(start = dims.paddingLarge)
                .size(dims.iconSize)
        )
        Text(
            text = stringResource(stringFromRes),
            color = SECONDARY_COLOR,
            fontSize = dims.textSizeLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = dims.paddingHuge),
        )
    }
}