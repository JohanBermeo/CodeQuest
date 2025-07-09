# CodeQuest

## Descripción
CodeQuest es una aplicación Java que permite a los usuarios crear, gestionar y participar en desafíos de programación y misiones de código. La aplicación proporciona una interfaz gráfica intuitiva para interactuar con el sistema y está diseñada con un enfoque en la seguridad y la escalabilidad.

## Características Principales

- **Sistema de Autenticación Seguro**
  - Registro de usuarios con validación de datos
  - Login con manejo seguro de contraseñas
  - Persistencia de datos de usuarios

- **Gestión de Contenido**
  - Creación y gestión de retos de programación
  - Sistema de misiones con objetivos específicos
  - Valoración de retos mediante sistema de "likes"
  - Capacidad para añadir soluciones a los retos

- **Notificaciones por Correo**
  - Los usuarios reciben notificaciones por correo electrónico cuando sus retos reciben nuevos "likes".

- **Interfaz Gráfica de Usuario**
  - Diseño moderno y responsive
  - Panel de control intuitivo
  - Navegación sencilla entre funcionalidades

## Requisitos del Sistema

- Java 17 o superior
- Maven 3.6 o superior

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone [url-del-repositorio]
   ```

2. Navegar al directorio del proyecto:
   ```bash
   cd codequest
   ```

3. Compilar el proyecto con Maven:
   ```bash
   mvn clean install
   ```

4. Ejecutar la aplicación:
   ```bash
   java -jar target/codequest-1.0-SNAPSHOT.jar
   ```

## Estructura del Proyecto

```
codequest/
├── src/
│   ├── main/java/com/codequest/
│   │   ├── app/           # Lógica principal de la aplicación
│   │   ├── auth/          # Sistema de autenticación
│   │   └── model/         # Modelos de datos y gestión de persistencia
│   └── test/java/         # Tests unitarios
├── docs/                  # Documentación
└── pom.xml               # Configuración de Maven
```

## Tecnologías Utilizadas

- **Java Swing**: Para la interfaz gráfica de usuario
- **Maven**: Gestión de dependencias y construcción del proyecto
- **JUnit 5**: Framework de pruebas unitarias
- **Jakarta Mail**: Para funcionalidades de notificación por correo

## Desarrollo

El proyecto sigue los principios SOLID y está estructurado en diferentes capas:

- **Presentación**: Interfaces gráficas (GUI)
- **Lógica de Negocio**: Servicios y controladores
- **Persistencia**: Manejo de datos y archivos

## Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.