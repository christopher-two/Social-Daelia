package org.christophertwo.daelia.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.christophertwo.daelia.feature.home.presentation.model.NetworkNode

@Composable
fun NetworkUserNode(
    node: NetworkNode,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .offset {
                IntOffset(
                    x = node.position.x.toInt(),
                    y = node.position.y.toInt()
                )
            }
            .widthIn(max = 100.dp), // Limitar el ancho máximo de la columna
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(if (node.isMainUser) 70.dp else 60.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .background(
                    color = if (node.isMainUser)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.secondaryContainer
                )
                .border(
                    width = if (node.isMainUser) 3.dp else 2.dp,
                    color = if (node.isMainUser)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = node.user.imageUrl,
                contentDescription = "Foto de ${node.user.name}",
                modifier = Modifier
                    .size(if (node.isMainUser) 66.dp else 56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = node.user.name ?: "Usuario",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1, // Solo una línea
            overflow = TextOverflow.Ellipsis, // Agregar "..." si es muy largo
            modifier = Modifier
                .padding(top = 4.dp)
                .widthIn(max = 100.dp) // Limitar ancho del texto
        )
    }
}
