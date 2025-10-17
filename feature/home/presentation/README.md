# Pantalla Principal - Red Social

## Descripción
Pantalla principal de la aplicación que muestra una red visual de usuarios conectados. Los usuarios se representan como burbujas y las conexiones como líneas punteadas.

## Características Implementadas

### 🎨 Componentes Visuales
- **SocialNetworkCanvas**: Canvas principal con soporte para pan y zoom
- **NetworkUserNode**: Burbujas de usuario con imagen y nombre
- **NetworkConnectionLines**: Líneas de conexión entre usuarios
- **UserBubble**: Componente simple de burbuja de usuario

### 🏗️ Arquitectura MVVM
- **HomeViewModel**: Maneja el estado y la lógica de negocio
- **HomeState**: Estado de la pantalla (usuario principal, amigos, loading)
- **HomeAction**: Acciones futuras de la UI
- **HomeScreen**: UI composable

### 📊 Modelos de Datos
- **NetworkNode**: Representa un nodo en la red (usuario + posición)
- **NetworkConnection**: Representa una conexión entre dos nodos

### 🛠️ Utilidades
- **NetworkLayoutCalculator**: Calcula posiciones de nodos en la red
  - Usuario principal en el centro
  - Amigos en círculo alrededor del usuario principal
  - Amigos de segundo nivel alrededor de cada amigo

## Funcionalidades

### Gestos Táctiles
- **Pan (Arrastrar)**: Desliza para mover la red
- **Zoom (Pellizcar)**: Acerca o aleja la vista (0.5x a 3x)

### Datos Dummy
Se incluyen 10 usuarios con amigos para pruebas:
- Cada usuario tiene entre 1 y 2 amigos de segundo nivel
- URLs de imágenes de pravatar.cc
- Nombres en español

## Uso de Material 3
Todos los colores utilizan el esquema de Material 3:
- `primaryContainer` / `primary` para el usuario principal
- `secondaryContainer` / `secondary` para amigos
- `outline` para líneas de conexión
- `onSurface` para texto

## Estructura de Archivos

```
feature/home/presentation/
├── HomeScreen.kt
├── HomeViewModel.kt
├── HomeState.kt
├── HomeAction.kt
├── components/
│   ├── SocialNetworkCanvas.kt
│   ├── NetworkUserNode.kt
│   ├── NetworkConnectionLines.kt
│   └── UserBubble.kt
├── model/
│   ├── NetworkNode.kt
│   └── NetworkConnection.kt
└── utils/
    └── NetworkLayoutCalculator.kt
```

## Próximas Mejoras
- Click en usuario para ver perfil
- Animaciones al cargar la red
- Filtros de búsqueda
- Cargar datos reales de Firestore
- Agregar/eliminar amigos

