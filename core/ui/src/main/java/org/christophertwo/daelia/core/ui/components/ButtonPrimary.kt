package org.christophertwo.daelia.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Google

@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = { Text(text = text) },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primaryContainer,
            contentColor = colorScheme.onPrimaryContainer
        )
    )
}


@Composable
fun ButtonSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = { Text(text = text) },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = colorScheme.onSurface,
            containerColor = Color.Transparent,
        )
    )
}

@Composable
fun ContinueWhitGoogle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primaryContainer,
            contentColor = colorScheme.onPrimaryContainer
        ),
        content = {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Continue with Google")
            Spacer(modifier = Modifier.weight(.5f))
            Icon(
                imageVector = FontAwesomeIcons.Brands.Google,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colorScheme.onPrimaryContainer,
            )
            Spacer(modifier = Modifier.weight(1f))
        },
    )
}