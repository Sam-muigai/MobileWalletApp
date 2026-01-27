package com.app.compulynx.core.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun LynxButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable (RowScope.() -> Unit),
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        content = content,
        colors = colors
    )
}

@Composable
fun LynxOutlineButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    content: @Composable (RowScope.() -> Unit)
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        content = content
    )
}