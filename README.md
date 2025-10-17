# Social Network App 🌐

Una aplicación Android moderna de red social que visualiza conexiones entre usuarios utilizando conceptos de teoría de grafos y conjuntos.

## 📋 Descripción

Esta aplicación permite a los usuarios iniciar sesión con Google y visualizar su red social de manera gráfica e interactiva. La aplicación implementa una visualización de grafos donde los usuarios son nodos y las amistades son aristas, permitiendo agregar amigos y ver conexiones de segundo nivel.

## 🏗️ Arquitectura

El proyecto utiliza una arquitectura modular **Clean Architecture** con separación por capas:

### Estructura de Módulos

```
social/
├── app/                          # Módulo principal de la aplicación
├── auth/
│   ├── api/                     # Interfaces de autenticación
│   └── impl/firebase/           # Implementación con Firebase Auth
├── profile/
│   ├── api/                     # Interfaces de perfil de usuario
│   └── impl/firestore/          # Implementación con Firestore
├── session/
│   ├── api/                     # Gestión de sesiones
│   └── impl/datastore/          # Persistencia con DataStore
├── feature/
│   ├── login/
│   │   ├── domain/              # Casos de uso de login
│   │   └── presentation/        # UI de login
│   └── home/
│       ├── domain/              # Casos de uso de home
│       └── presentation/        # UI de visualización de red
└── core/
    ├── common/                  # Utilidades compartidas
    └── ui/                      # Componentes UI reutilizables
```

### Capas de la Arquitectura

1. **Presentation Layer**: ViewModels, Estados, Acciones y Composables
2. **Domain Layer**: Casos de uso (Use Cases) con lógica de negocio
3. **Data Layer**: Repositorios e implementaciones de fuentes de datos

## 🔗 Conexión con Teoría de Conjuntos y Grafos

### 📊 Teoría de Grafos

La aplicación implementa varios conceptos fundamentales de la teoría de grafos:

#### 1. **Grafo No Dirigido con Pesos**
- **Nodos (V)**: Cada usuario es un nodo en el grafo
  - `NetworkNode`: Representa un usuario con su posición en el canvas
  - Usuario principal: Nodo central de mayor tamaño
  - Amigos: Nodos conectados directamente
  - Amigos de amigos: Nodos de segundo nivel

- **Aristas (E)**: Las conexiones entre usuarios
  - `NetworkConnection`: Representa una amistad entre dos usuarios
  - Conexiones simples: Aristas normales (líneas discontinuas grises)
  - Conexiones mutuas: Aristas con mayor peso visual (líneas azules más gruesas)

```kotlin
// Estructura del grafo en código
data class NetworkNode(
    val user: UserFirestore,        // Vértice
    val position: Offset,            // Posición en el espacio 2D
    val isMainUser: Boolean = false  // Marcador de nodo raíz
)

data class NetworkConnection(
    val start: Offset,               // Vértice inicial
    val end: Offset,                 // Vértice final
    val isMutualFriend: Boolean      // Peso de la arista
)
```

#### 2. **Algoritmos de Layout**
La clase `NetworkLayoutCalculator` implementa un algoritmo de distribución circular:

- **Disposición radial**: Los amigos se distribuyen en círculos concéntricos
- **Radio fijo**: `radius = 220f` para el primer nivel
- **Cálculo de ángulos**: `angleStep = (2π) / n` donde n es el número de nodos
- **Coordenadas polares**: Conversión a cartesianas usando `cos(θ)` y `sin(θ)`

```kotlin
val angle = angleStep * index
val x = centerX + (radius * cos(angle)).toFloat()
val y = centerY + (radius * sin(angle)).toFloat()
```

#### 3. **Grafos de Niveles (BFS implícito)**
La aplicación visualiza hasta 2 niveles de profundidad:
- **Nivel 0**: Usuario principal (raíz)
- **Nivel 1**: Amigos directos
- **Nivel 2**: Amigos de los amigos (máximo 2 por amigo)

Esto simula un recorrido en anchura (Breadth-First Search) limitado.

#### 4. **Matriz de Adyacencia Implícita**
La lista de amigos en Firestore representa una matriz de adyacencia:

```kotlin
data class UserFirestore(
    val name: String?,
    val imageUrl: String?,
    val friends: List<UserFirestore>?  // Lista de adyacencia
)
```

Si tenemos usuarios U = {u₁, u₂, u₃, ...}, la relación de amistad se puede representar como:
- Matriz A[i][j] = 1 si uᵢ es amigo de uⱼ
- Matriz A[i][j] = 0 en caso contrario

### 🔢 Teoría de Conjuntos

La aplicación implementa múltiples operaciones de conjuntos:

#### 1. **Conjuntos Básicos**
```kotlin
// Conjunto de todos los usuarios registrados
U = getAllUsers()

// Conjunto de amigos del usuario actual
F = getFriends()

// Conjunto de usuarios disponibles (no amigos)
A = availableUsers
```

#### 2. **Operaciones de Conjuntos**

##### **Unión (∪)**
```kotlin
// Todos los usuarios visibles = Amigos ∪ Usuarios disponibles
val allUsers = friends + availableUsers
```

##### **Diferencia (-)**
```kotlin
// Usuarios disponibles = Todos los usuarios - Amigos - Usuario actual
val availableUsers = allUsers.filter { availableUser ->
    friends.none { friend ->
        friend.name == availableUser.name && 
        friend.imageUrl == availableUser.imageUrl
    }
}
```

Esta operación se realiza en `HomeViewModel.loadUserData()`:
- **U**: Conjunto universal de usuarios
- **F**: Conjunto de amigos
- **S**: Usuario actual (singleton)
- **Disponibles = U - F - S**

##### **Intersección (∩)**
```kotlin
// Verificar si un usuario ya es amigo
val isAlreadyFriend = friends.any { friend ->
    friend.name == user.name && friend.imageUrl == user.imageUrl
}
// Esto verifica si user ∈ F
```

##### **Pertenencia (∈)**
La verificación de amistades mutuas:
```kotlin
val isMutualFriend = user.friends?.any { friend ->
    friend.name == mainUser.name && 
    friend.imageUrl == mainUser.imageUrl
} ?: false
// Verifica si mainUser ∈ friends(user)
```

#### 3. **Relaciones y Funciones**

##### **Relación de Amistad (R)**
Es una relación binaria entre usuarios:
- `R ⊆ U × U` donde `(a, b) ∈ R` significa "a es amigo de b"
- **No necesariamente simétrica**: a puede ser amigo de b, pero b no de a
- **No reflexiva**: un usuario no está en su propia lista de amigos
- **No transitiva**: Si a es amigo de b, y b de c, no implica que a sea amigo de c

##### **Grafo Bipartito Implícito**
Al agregar amigos, la UI separa dos conjuntos:
- **F**: Amigos (conectados al usuario)
- **A**: Usuarios disponibles (no conectados)
- **F ∩ A = ∅** (son disjuntos)
- **F ∪ A ⊆ U** (todos son subconjuntos del universo)

#### 4. **Cardinalidad**
```kotlin
// Número de amigos
|F| = friends.size

// Número de usuarios disponibles
|A| = availableUsers.size

// Total de usuarios visibles
|V| = |F| + |A|
```

La disposición circular usa la cardinalidad para calcular ángulos:
```kotlin
val angleStep = (2 * Math.PI) / allUsers.size  // 2π / |V|
```

## 🛠️ Tecnologías Utilizadas

### Framework y Lenguaje
- **Kotlin** 2.2.20
- **Jetpack Compose** - UI declarativa
- **Material 3** - Sistema de diseño

### Firebase
- **Firebase Authentication** - Autenticación con Google
- **Cloud Firestore** - Base de datos NoSQL

### Arquitectura y DI
- **Koin** 4.1.1 - Inyección de dependencias
- **Clean Architecture** - Separación de capas
- **MVVM** - Patrón de presentación

### Persistencia Local
- **DataStore** - Gestión de sesiones
- **Preferences DataStore** - Key-value storage

### Navegación
- **Navigation Compose** - Navegación entre pantallas
- **Type-safe Navigation** - Rutas tipadas

### UI/UX
- **Coil 3** - Carga de imágenes
- **Material Kolor** - Generación de esquemas de color
- **Font Awesome Icons** - Iconografía
- **Core Splashscreen** - Splash screen nativa

### Utilidades
- **Kotlinx Serialization** - Serialización JSON
- **Kotlinx DateTime** - Manejo de fechas
- **Kotlinx Coroutines** - Programación asíncrona

## 📦 Características Principales

### 🔐 Autenticación
- Inicio de sesión con Google (OAuth 2.0)
- Gestión de sesiones persistentes
- Auto-navegación según estado de sesión

### 👥 Gestión de Amigos
- Visualización de amigos actuales
- Búsqueda de usuarios disponibles
- Agregar/eliminar amigos
- Actualización en tiempo real con Firestore

### 📊 Visualización de Red
- Representación gráfica tipo grafo
- Usuario principal en el centro
- Amigos en círculo alrededor
- Amigos de segundo nivel
- Líneas de conexión diferenciadas:
  - Grises: Conexión simple
  - Azules: Amistad mutua
- Interacción táctil:
  - Pan (desplazamiento)
  - Zoom (pellizcar)
  - Clic en usuarios

### 🎨 UI Moderna
- Diseño Material 3
- Modo claro/oscuro
- Animaciones fluidas
- Splash screen personalizada

## 🚀 Instalación

### Requisitos Previos
- Android Studio Hedgehog o superior
- JDK 11 o superior
- SDK de Android 29+ (Android 10+)
- Cuenta de Firebase

### Pasos

1. **Clonar el repositorio**
```bash
git clone <url-del-repositorio>
cd social
```

2. **Configurar Firebase**
   - Crear un proyecto en [Firebase Console](https://console.firebase.google.com/)
   - Agregar una app Android con el package `org.christophertwo.daelia.social`
   - Descargar `google-services.json` y colocarlo en `app/`
   - Habilitar Authentication (Google Sign-In)
   - Habilitar Cloud Firestore

3. **Configurar OAuth de Google**
   - En Firebase Console, obtener el Web Client ID
   - Actualizar en `app/src/main/res/values/strings.xml`:
   ```xml
   <string name="web_id">TU_WEB_CLIENT_ID</string>
   ```

4. **Compilar y ejecutar**
```bash
./gradlew clean build
./gradlew installDebug
```

## 📱 Uso

1. **Login**: Toca "Continuar con Google" e inicia sesión
2. **Vista Principal**: Verás tu red de amigos en forma de grafo
3. **Agregar Amigos**: 
   - Los usuarios sin conexión son "usuarios disponibles"
   - Toca cualquier usuario disponible
   - Confirma en el diálogo para agregarlo
4. **Navegación**:
   - Pellizca para hacer zoom
   - Arrastra para desplazarte por la red

## 🗄️ Estructura de Datos en Firestore

### Colección: `users`
```json
{
  "uid": {
    "name": "Juan Pérez",
    "imageUrl": "https://example.com/photo.jpg",
    "friends": [
      {
        "name": "María García",
        "imageUrl": "https://example.com/maria.jpg",
        "friends": [...]
      }
    ]
  }
}
```

## 🧪 Testing

El proyecto incluye configuración para:
- **Unit Tests**: Lógica de negocio
- **Instrumentation Tests**: Tests de UI

```bash
# Ejecutar tests unitarios
./gradlew test

# Ejecutar tests instrumentados
./gradlew connectedAndroidTest
```

## 🔧 Configuración de Build

- **minSdk**: 29 (Android 10)
- **targetSdk**: 36 (Android 14+)
- **compileSdk**: 36
- **Java Version**: 11
- **Kotlin Version**: 2.2.20

## 📄 Licencia

Este proyecto es un ejemplo educativo y de demostración.

## 👨‍💻 Autor

Christopher Two - Daelia

## 🙏 Agradecimientos

- Firebase por la infraestructura backend
- Jetpack Compose por el framework UI
- Comunidad de Kotlin por las librerías

---

## 📚 Conceptos Matemáticos Aplicados

### Resumen de Grafos
- **Tipo**: Grafo no dirigido ponderado
- **Vértices (V)**: Usuarios de la red social
- **Aristas (E)**: Relaciones de amistad
- **Grado de un nodo**: Número de amigos directos
- **Camino**: Secuencia de usuarios conectados
- **Distancia**: Número mínimo de conexiones entre dos usuarios

### Resumen de Conjuntos
- **Conjunto Universal (U)**: Todos los usuarios registrados
- **Subconjuntos**: Amigos (F), Disponibles (A), Usuario actual (S)
- **Operaciones**: Unión, Intersección, Diferencia, Pertenencia
- **Propiedades**: Disjunción, Complemento, Cardinalidad

Este proyecto demuestra cómo conceptos matemáticos abstractos se aplican directamente en el desarrollo de software moderno, especialmente en aplicaciones sociales que modelan relaciones humanas como estructuras de datos.

