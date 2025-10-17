package org.christophertwo.daelia.feature.home.presentation.utils

import androidx.compose.ui.geometry.Offset
import org.christophertwo.daelia.feature.home.presentation.model.NetworkConnection
import org.christophertwo.daelia.feature.home.presentation.model.NetworkNode
import org.christophertwo.daelia.profile.api.UserFirestore
import kotlin.math.cos
import kotlin.math.sin

/**
 * Calcula las posiciones de los nodos en la red social
 */
object NetworkLayoutCalculator {

    // Tamaños de los nodos
    private const val MAIN_USER_SIZE = 70f
    private const val FRIEND_SIZE = 60f
    private const val SUB_FRIEND_SIZE = 50f

    /**
     * Genera una red de nodos a partir del usuario principal y sus amigos
     */
    fun calculateNetworkLayout(
        mainUser: UserFirestore?,
        friends: List<UserFirestore>,
        availableUsers: List<UserFirestore>,
        canvasWidth: Float,
        canvasHeight: Float
    ): Pair<List<NetworkNode>, List<NetworkConnection>> {
        val nodes = mutableListOf<NetworkNode>()
        val connections = mutableListOf<NetworkConnection>()

        // Posición central para el usuario principal
        val centerX = canvasWidth / 2f
        val centerY = canvasHeight / 2f

        // Si hay usuario principal, agregarlo al centro
        if (mainUser != null) {
            val mainNode = NetworkNode(
                user = mainUser,
                position = Offset(centerX - (MAIN_USER_SIZE / 2f), centerY - (MAIN_USER_SIZE / 2f)),
                isMainUser = true
            )
            nodes.add(mainNode)
        }

        // Combinar amigos y usuarios disponibles
        val allUsers = friends + availableUsers
        val radius = 220f
        val angleStep = (2 * Math.PI) / allUsers.size

        allUsers.forEachIndexed { index, user ->
            val angle = angleStep * index
            val x = centerX + (radius * cos(angle)).toFloat() - (FRIEND_SIZE / 2f)
            val y = centerY + (radius * sin(angle)).toFloat() - (FRIEND_SIZE / 2f)

            val userNode = NetworkNode(
                user = user,
                position = Offset(x, y),
                isMainUser = false
            )
            nodes.add(userNode)

            // Crear conexión SOLO si es un amigo
            val isFriend = friends.any { friend ->
                friend.name == user.name && friend.imageUrl == user.imageUrl
            }

            if (mainUser != null && isFriend) {
                // Verificar si es amistad mutua
                val isMutualFriend = user.friends?.any { friend ->
                    friend.name == mainUser.name && friend.imageUrl == mainUser.imageUrl
                } ?: false

                connections.add(
                    NetworkConnection(
                        start = Offset(centerX, centerY),
                        end = Offset(x + (FRIEND_SIZE / 2f), y + (FRIEND_SIZE / 2f)),
                        isMutualFriend = isMutualFriend
                    )
                )
            }

            // Agregar amigos de segundo nivel SOLO para amigos directos
            if (isFriend) {
                user.friends?.take(2)?.forEachIndexed { subIndex, subFriend ->
                    val subRadius = 120f
                    val subAngle = angle + (subIndex - 0.5) * 0.5
                    val subX = x + (FRIEND_SIZE / 2f) + (subRadius * cos(subAngle)).toFloat() - (SUB_FRIEND_SIZE / 2f)
                    val subY = y + (FRIEND_SIZE / 2f) + (subRadius * sin(subAngle)).toFloat() - (SUB_FRIEND_SIZE / 2f)

                    val subFriendNode = NetworkNode(
                        user = subFriend,
                        position = Offset(subX, subY),
                        isMainUser = false
                    )
                    nodes.add(subFriendNode)

                    // Conexión entre amigo y su amigo
                    connections.add(
                        NetworkConnection(
                            start = Offset(x + (FRIEND_SIZE / 2f), y + (FRIEND_SIZE / 2f)),
                            end = Offset(subX + (SUB_FRIEND_SIZE / 2f), subY + (SUB_FRIEND_SIZE / 2f)),
                            isMutualFriend = false
                        )
                    )
                }
            }
        }

        return Pair(nodes, connections)
    }

    /**
     * Genera una red de usuarios disponibles SIN conexiones al usuario principal
     * Para usuarios que aún no son amigos
     */
    fun calculateAvailableUsersLayout(
        mainUser: UserFirestore?,
        availableUsers: List<UserFirestore>,
        canvasWidth: Float,
        canvasHeight: Float
    ): Pair<List<NetworkNode>, List<NetworkConnection>> {
        val nodes = mutableListOf<NetworkNode>()
        val connections = mutableListOf<NetworkConnection>()

        // Posición central para el usuario principal
        val centerX = canvasWidth / 2f
        val centerY = canvasHeight / 2f

        // Si hay usuario principal, agregarlo al centro
        if (mainUser != null) {
            val mainNode = NetworkNode(
                user = mainUser,
                position = Offset(centerX - (MAIN_USER_SIZE / 2f), centerY - (MAIN_USER_SIZE / 2f)),
                isMainUser = true
            )
            nodes.add(mainNode)
        }

        // Distribuir usuarios disponibles en círculo alrededor del usuario principal
        // PERO SIN CREAR CONEXIONES (usuarios no conectados)
        val radius = 220f
        val angleStep = (2 * Math.PI) / availableUsers.size

        availableUsers.forEachIndexed { index, user ->
            val angle = angleStep * index
            val x = centerX + (radius * cos(angle)).toFloat() - (FRIEND_SIZE / 2f)
            val y = centerY + (radius * sin(angle)).toFloat() - (FRIEND_SIZE / 2f)

            val userNode = NetworkNode(
                user = user,
                position = Offset(x, y),
                isMainUser = false
            )
            nodes.add(userNode)

            // NO agregamos conexiones aquí - usuarios no conectados
        }

        // Retornar nodos sin conexiones
        return Pair(nodes, connections)
    }
}
