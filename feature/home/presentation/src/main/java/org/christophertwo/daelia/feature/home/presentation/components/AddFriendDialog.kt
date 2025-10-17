package org.christophertwo.daelia.feature.home.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import org.christophertwo.daelia.profile.api.UserFirestore

@Composable
fun AddFriendDialog(
    user: UserFirestore,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Agregar amigo",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = "¿Quieres agregar a ${user.name ?: "este usuario"} como amigo?",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

