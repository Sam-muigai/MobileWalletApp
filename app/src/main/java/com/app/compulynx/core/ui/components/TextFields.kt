package com.app.compulynx.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.app.compulynx.R

@Composable
fun LynxTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String? = null,
    maxLines: Int = 1,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    placeholder: String = "",
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isMandatory: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    onImeAction: KeyboardActionScope.() -> Unit = {},
    errorColor: Color = MaterialTheme.colorScheme.error,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
    guideContent: @Composable (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                buildAnnotatedString {
                    append(label)
                    if (isMandatory) {
                        withStyle(
                            SpanStyle(
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold,
                            ),
                        ) {
                            append(" *")
                        }
                    }
                },
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                    ),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (guideContent != null) {
            guideContent()
            Spacer(modifier = Modifier.height(16.dp))
        }
        OutlinedTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            value = value,
            onValueChange = {
                if (!readOnly) {
                    onValueChange(it)
                }
            },
            enabled = enabled,
            readOnly = readOnly,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = errorText != null,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onAny = onImeAction),
            shape = shape,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            placeholder =
                if (placeholder.isNotEmpty()) {
                    {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                } else {
                    null
                },
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = focusedColor,
                    unfocusedBorderColor = unfocusedColor,
                    errorBorderColor = errorColor,
                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                    disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                    errorContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                ),
        )

        AnimatedVisibility(visible = errorText != null) {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                text = errorText ?: "",
                style = MaterialTheme.typography.labelMedium,
                color = errorColor,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Composable
fun LynxPinTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String? = null,
    maxLines: Int = 1,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    placeholder: String = "",
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isMandatory: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    onImeAction: KeyboardActionScope.() -> Unit = {},
    errorColor: Color = MaterialTheme.colorScheme.error,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
    guideContent: @Composable (() -> Unit)? = null,
    isPasswordVisible: Boolean = false,
    onPasswordVisibilityToggle: () -> Unit
) {
    LynxTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        errorText = errorText,
        maxLines = maxLines,
        shape = shape,
        placeholder = placeholder,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = {
            IconButton(
                onClick = onPasswordVisibilityToggle
            ) {
                AnimatedContent(isPasswordVisible) { isVisible ->
                    if (isVisible) {
                        Icon(
                            painter = painterResource(R.drawable.eye_closed),
                            contentDescription = "hide password",
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.eye_open),
                            contentDescription = "show password",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        },
        isMandatory = isMandatory,
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        enabled = enabled,
        onImeAction = onImeAction,
        errorColor = errorColor,
        focusedColor = focusedColor,
        unfocusedColor = unfocusedColor,
        guideContent = guideContent
    )
}