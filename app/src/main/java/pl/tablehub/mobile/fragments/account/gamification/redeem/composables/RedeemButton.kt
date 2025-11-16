package pl.tablehub.mobile.fragments.account.gamification.redeem.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.account.profile.composables.PrimaryActionButton
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.theme.GlobalDimensions

@Composable
internal fun RedeemButton(
    dims: GlobalDimensions,
    reward: Reward,
    onRedeemClick: (Reward) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dims.paddingBig)
    ) {
        PrimaryActionButton(
            text = stringResource(R.string.confirm),
            onClick = { onRedeemClick(reward) }
        )
    }
}
