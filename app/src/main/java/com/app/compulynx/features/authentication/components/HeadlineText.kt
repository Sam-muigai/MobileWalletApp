package com.app.compulynx.features.authentication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HeadlineText(
    modifier: Modifier = Modifier,
    text: String,
    description: String? = null
) {
    Column {
        Text(
            modifier = modifier,
            text = text,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        description?.let {
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = modifier,
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}