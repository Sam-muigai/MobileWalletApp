package com.app.compulynx.features.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.compulynx.R
import com.app.compulynx.domain.models.Transaction

@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    val iconRes =
        if (transaction.debitOrCredit == "debit") R.drawable.ic_arrow_downward else R.drawable.ic_arrow_downward
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Surface(
            modifier = Modifier,
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier.padding(12.dp),
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = "icon",
                )
            }
        }
    }
}