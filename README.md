# Documentación de Arquitectura e Implementación de Proyecto Alke Wallet M6

# 1. Resumen de la Arquitectura
* La aplicación sigue el patrón de diseño MVVM (Model-View-ViewModel), recomendado por Google. Esta arquitectura permite una clara separación de responsabilidades,
  facilitando el mantenimiento y la escalabilidad del código.
* Se utiliza una arquitectura de capas:
  * Capa de UI (View): Activities y Fragments encargados de mostrar datos al usuario.
  * Capa de Presentación (ViewModel): Gestiona el estado de la UI y se comunica con el repositorio.
  * Capa de Datos (Repository): Actúa como mediador entre las fuentes de datos (Local y Remota).

# 2. Estructura del Código
* El proyecto está organizado por paquetes para mantener la cohesión:
  * data/: Contiene todo lo relacionado con el origen de los datos.
  * data/local/: Implementación de Room (Entidades, DAOs, Database y Entities).
  * data/repository/: Clases que deciden si obtener datos de la red o de la caché
  * data/api: Implementación de Retrofit (Servicios API, Modelos de respuesta).
  * model/: Contiene los modelos de implementación para el registro de usuarios y las transacciones que inciden en el saldo
  * viewModel/: Es la implementación concreta del ViewModel que conecta la base de datos local (Room) y el servicio externo (Retrofit) con la interfaz de usuario.
  * Además contiene las Activities que garantizan la ejecución de la app para el usuario con respecto a las transacciones, logeo de usuario, home de inicio con   opciones de envío, depósito y cierre de sesión.
    
# 3. Gestión de Datos
* A. Almacenamiento Local (Room)
* Base de Datos Local con Room: Se utiliza Room como una capa de abstracción sobre SQLite para el almacenamiento persistente de datos del usuario y transacciones
  recientes.
  * Entidades (UserEntity, TransactionEntity): Representan las tablas de la base de datos.
  * DAO (WalletDao): Define las operaciones de acceso a datos (CRUD) utilizando Coroutines y Flow para una reactividad en tiempo real.
  * Database (WalletDatabase): Clase abstracta que sirve como punto de acceso principal a la conexión SQLite.
* B. Comunicación Remota (Retrofit)
  * Service Interface: Define endpoints como POST /login o GET /balance.
  * Gson: Conversión automática de JSON a objetos Kotlin.
  * Interceptors: Gestión automática de encabezados y tokens de autenticación.
  * Manejo de Errores: Uso de bloques try-catch y clases de estado (Result/Resource) para gestionar códigos HTTP.

# 4. Responsabilidades en el Patrón MVVM
* Principio de Diseño: Cada componente tiene una única razón para cambiar.
* View (Activity/Fragment):
  * Observa LiveData o StateFlow.
  * Contiene solo lógica de visualización y captura de eventos (clicks).
* ViewModel:
  * Expone el estado a la UI.
  * No tiene conocimiento de clases de Android (Context o vistas) para evitar fugas de memoria.
  * Ejecuta tareas asíncronas mediante viewModelScope.
* Repository:
  * Actúa como el mediador centralizado entre las diversas fuentes de datos (locales y remotas) y la lógica de negocio de la aplicación.
  * Su función principal es abstraer el origen de la información, garantizando que el resto de los componentes accedan a una fuente de información
    íntegra y consistente.
  * Decide si los datos se sirven desde la caché local o se solicitan a la API.
* Model (Entities/DTOs):
  * Objetos de datos puros: Representan la estructura de la información (Base de datos vs Red).

# 5. Decisiones de Diseño
* Reactividad: Se eligió el uso de Flow y LiveData para que la interfaz de usuario se actualice automáticamente cuando los datos en la base de datos cambian,
  sin necesidad de recargas manuales.
* Seguridad: El saldo y los datos sensibles se manejan mediante tipos de datos precisos y se almacenan localmente de forma cifrada (opcionalmente) o protegida
  mediante el sistema de archivos privado de la app.
* Escalabilidad: Al separar las fuentes de datos en un repositorio, la aplicación puede funcionar en "Modo Offline" mostrando los últimos datos guardados en Room
  si la API no está disponible.

# 6. Estrategia de Pruebas (Testing)
* Para garantizar la robustez del sistema, se ha implementado una estrategia de pruebas dividida en tres pilares fundamentales, asegurando la integridad de los datos y el comportamiento de la interfaz.
* Pruebas Unitarias en el ViewModel: El objetivo es validar la lógica de presentación y el manejo de estados de la UI sin depender del sistema operativo Android.
* Pruebas de Persistencia (Room Database): Se realizan pruebas sobre los DAOs para validar que la comunicación con SQLite sea exacta.
  * Integridad de Datos: Verificación de operaciones CRUD (Crear, Leer, Actualizar, Borrar) para transacciones y datos de usuario.
* Pruebas de Integración (Retrofit): Estas pruebas garantizan que el contrato de comunicación con el servidor externo se cumpla rigurosamente.
  * Validación de Endpoints: Se comprueba que las solicitudes HTTP se realicen con los parámetros y encabezados de autenticación correctos.
  * Manejo de Respuestas: Simulación de diversos escenarios del servidor (respuestas exitosas, errores de cliente o errores de servidor).
