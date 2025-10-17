package org.christophertwo.daelia.feature.home.presentation

import org.christophertwo.daelia.profile.api.UserFirestore

data class HomeState(
    val user: UserFirestore? = null,
    val friends: List<UserFirestore> = emptyList(),
    val availableUsers: List<UserFirestore> = emptyList(),
    val selectedUserForDialog: UserFirestore? = null,
    val isLoading: Boolean = false
)

// Datos dummy para testing
fun getDummyFriends(): List<UserFirestore> {
    return listOf(
        UserFirestore(
            name = "Ana García",
            imageUrl = "https://i.pravatar.cc/150?img=1",
            friends = listOf(
                UserFirestore(name = "Pedro Ruiz", imageUrl = "https://i.pravatar.cc/150?img=12"),
                UserFirestore(name = "Sofía López", imageUrl = "https://i.pravatar.cc/150?img=9")
            )
        ),
        UserFirestore(
            name = "Carlos Martínez",
            imageUrl = "https://i.pravatar.cc/150?img=3",
            friends = listOf(
                UserFirestore(name = "Laura Sanz", imageUrl = "https://i.pravatar.cc/150?img=10"),
                UserFirestore(name = "Diego Torres", imageUrl = "https://i.pravatar.cc/150?img=13")
            )
        ),
        UserFirestore(
            name = "María Fernández",
            imageUrl = "https://i.pravatar.cc/150?img=5",
            friends = listOf(
                UserFirestore(name = "Javier Ramos", imageUrl = "https://i.pravatar.cc/150?img=14"),
                UserFirestore(name = "Elena Castro", imageUrl = "https://i.pravatar.cc/150?img=15")
            )
        ),
        UserFirestore(
            name = "Luis Hernández",
            imageUrl = "https://i.pravatar.cc/150?img=7",
            friends = listOf(
                UserFirestore(name = "Carmen Vega", imageUrl = "https://i.pravatar.cc/150?img=16"),
                UserFirestore(name = "Roberto Gil", imageUrl = "https://i.pravatar.cc/150?img=17")
            )
        ),
        UserFirestore(
            name = "Isabel Romero",
            imageUrl = "https://i.pravatar.cc/150?img=8",
            friends = listOf(
                UserFirestore(name = "Miguel Ortiz", imageUrl = "https://i.pravatar.cc/150?img=18")
            )
        ),
        UserFirestore(
            name = "Alberto Jiménez",
            imageUrl = "https://i.pravatar.cc/150?img=11",
            friends = listOf(
                UserFirestore(name = "Lucía Molina", imageUrl = "https://i.pravatar.cc/150?img=19"),
                UserFirestore(name = "Pablo Serrano", imageUrl = "https://i.pravatar.cc/150?img=20")
            )
        ),
        UserFirestore(
            name = "Patricia Moreno",
            imageUrl = "https://i.pravatar.cc/150?img=24",
            friends = listOf(
                UserFirestore(name = "Andrés Navarro", imageUrl = "https://i.pravatar.cc/150?img=21")
            )
        ),
        UserFirestore(
            name = "Ricardo Suárez",
            imageUrl = "https://i.pravatar.cc/150?img=25",
            friends = listOf(
                UserFirestore(name = "Beatriz Prieto", imageUrl = "https://i.pravatar.cc/150?img=22"),
                UserFirestore(name = "Fernando Cruz", imageUrl = "https://i.pravatar.cc/150?img=23")
            )
        ),
        UserFirestore(
            name = "Verónica Delgado",
            imageUrl = "https://i.pravatar.cc/150?img=26",
            friends = listOf(
                UserFirestore(name = "Raúl Méndez", imageUrl = "https://i.pravatar.cc/150?img=27")
            )
        ),
        UserFirestore(
            name = "Sergio Vargas",
            imageUrl = "https://i.pravatar.cc/150?img=28",
            friends = listOf(
                UserFirestore(name = "Cristina Iglesias", imageUrl = "https://i.pravatar.cc/150?img=29"),
                UserFirestore(name = "Óscar Blanco", imageUrl = "https://i.pravatar.cc/150?img=30")
            )
        )
    )
}
