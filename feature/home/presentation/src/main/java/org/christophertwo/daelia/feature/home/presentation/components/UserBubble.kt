package org.christophertwo.daelia.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.christophertwo.daelia.profile.api.UserFirestore

@Composable
fun UserBubble(
    userFirestore: UserFirestore,
    isUser: Boolean
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(
                color = if (isUser) colorScheme.primaryContainer else colorScheme.secondaryContainer,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center,
        content = {
            AsyncImage(
                model = userFirestore.imageUrl,
                contentDescription = "User Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = Crop
            )
        }
    )
}