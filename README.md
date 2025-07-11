# Sistema de Brainstorming Colaborativo 🧠💡
## Retos de Programación y Coding Challenges

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Status](https://img.shields.io/badge/Status-En%20Desarrollo-yellow?style=for-the-badge)]()
[![Hackathon](https://img.shields.io/badge/Hackathon-POO%202024-blue?style=for-the-badge)]()

## 📋 Descripción del Proyecto

Sistema colaborativo multi-usuario que permite a desarrolladores y estudiantes de programación proponer ideas para nuevos retos de código, desafíos algorítmicos y soluciones innovadoras. La aplicación fomenta la colaboración y competencia sana a través de un sistema de votación y gamificación.

### 🎯 Área de Aplicación: Retos de Programación/Coding Challenges

Ejemplos de ideas que se pueden proponer:
- "Un bot para Discord que recomiende lanzamientos de series"
- "Solución eficiente para programación de la ruta D24 de TransMilenio"
- "Sistema de recomendación de problemas de LeetCode basado en habilidades"
- "Generador automático de casos de prueba para problemas de programación"

## ✨ Características Principales

### 🔧 Funcionalidades Core
- **Proponer Ideas**: Cualquier usuario puede presentar nuevas ideas de retos de programación
- **Proponer Soluciones**: Los usuarios pueden sugerir implementaciones específicas para ideas existentes
- **Sistema de Votación**: Votos a favor o en contra para ideas y soluciones
- **Gamificación**: Sistema de puntos y rankings para motivar la participación

### 🌐 Funcionalidades Avanzadas
- **Multi-Usuario Distribuido**: Múltiples usuarios desde diferentes estaciones de trabajo
- **Sincronización Primitiva**: Sistema de bloqueo de archivos sin bases de datos
- **Autenticación Integrada**: Módulo UserAuth para usuarios normales y administradores
- **Persistencia Híbrida**: Serialización para usuarios + archivos .properties para datos

## 🏗️ Arquitectura del Sistema

### 📊 Diseño Orientado a Objetos
```
Usuario (Clase Base)
├── UsuarioNormal
└── Administrador

Contenido (Clase Base)
├── Idea
└── Solucion

Votacion
├── VotoIdea
└── VotoSolucion

Gamificacion
├── SistemaPuntos
└── Ranking
```

### 💾 Estrategia de Persistencia
- **Serialización**: Objetos User y Admin (users.dat)
- **Archivos .properties**: Ideas, soluciones y datos de gamificación
- **Sistema de Bloqueo**: Archivos .lock para concurrencia
- **Carpeta Compartida**: Sincronización multi-usuario

## 🚀 Instalación y Configuración

### 📋 Prerrequisitos
- Java 17 o superior
- IDE compatible (IntelliJ IDEA, Eclipse, NetBeans)
- Acceso a carpeta compartida en red (para funcionalidad multi-usuario)

### 🔧 Instalación
1. Clona el repositorio:
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd brainstorming-colaborativo
   ```
3. Compila el proyecto:
   ```bash
   mvn clean package
   ```

4. Ejecuta la aplicación:
   ```bash
   java -jr target/codequest-1.0-SNAPSHOT.jar
   ```

## 📁 Estructura del Proyecto

```
brainstorming-colaborativo/
├── src/
│   ├── auth/
│   │   ├── UserAuth.java
│   │   ├── User.java
│   │   └── Admin.java
│   ├── core/
│   │   ├── Idea.java
│   │   ├── Solucion.java
│   │   └── Votacion.java
│   ├── persistence/
│   │   ├── DataManager.java
│   │   └── FileLockManager.java
│   ├── gamification/
│   │   ├── SistemaPuntos.java
│   │   └── Ranking.java
│   ├── ui/
│   │   └── InterfazUsuario.java
│   └── Main.java
├── shared_data/
│   ├── users.dat
│   ├── ideas.properties
│   ├── solutions.properties
│   └── gamification.properties
├── docs/
│   └── diagrama_clases.uml
└── README.md
```

## 🎮 Uso de la Aplicación

### 🔐 Autenticación
1. **Registro**: Crear nueva cuenta de usuario
2. **Login**: Acceder con credenciales existentes
3. **Roles**: Usuario normal o Administrador

### 💡 Gestión de Ideas
1. **Proponer Idea**: Título, descripción, área de aplicación
2. **Ver Ideas**: Explorar propuestas existentes
3. **Votar Ideas**: Expresar apoyo o desacuerdo

### 🛠️ Gestión de Soluciones
1. **Proponer Solución**: Respuesta a idea existente
2. **Detalles Técnicos**: Implementación específica
3. **Votar Soluciones**: Evaluar propuestas

### 🏆 Sistema de Gamificación
- **Puntos por Propuestas**: +10 por idea, +5 por solución
- **Puntos por Votos**: +1 por voto recibido, -1 por voto negativo
- **Rankings**: Top Ideadores y Top Solucionadores

## 🔧 Funcionalidades Técnicas

### 🌐 Multi-Usuario
- **Sincronización**: Actualización automática de datos
- **Bloqueo de Archivos**: Prevención de corrupción
- **Refresco de Datos**: Información siempre actualizada

### 📊 Persistencia
- **Archivos .properties**: Formato clave-valor
- **Serialización**: Objetos de usuario
- **Gestión de Concurrencia**: Sistema de locks

## 👥 Equipo de Desarrollo

### 🏗️ Roles del Equipo
- **Arquitecto/a de POO y Persistencia**: Diseño de clases y estrategia de datos - Juan Triana
- **Ingeniero/a de Sincronización**: Juan Otálora - Johan Bermeo - Juan Triana
- **Desarrollador/a de Lógica de Negocio**: Juan Otálora
- **Líder de Pruebas y Presentación**: Johan Bermeo

## 🧪 Testing

### 🔍 Pruebas Unitarias
```bash
# Ejecutar pruebas
mvn clean compile test
```

### 🌐 Pruebas Multi-Usuario
1. Abrir múltiples instancias en diferentes terminales
2. Verificar sincronización de datos
3. Validar sistema de bloqueo

## 🤝 Contribución

### 📝 Guías de Contribución
1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Añade nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

### 🎨 Estándares de Código
- Nomenclatura en español para clases y métodos
- Documentación JavaDoc
- Principios SOLID
- Patrones de diseño apropiados

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 📞 Contacto

### 👥 Equipo [NOMBRE_DEL_EQUIPO]
- **Integrante 1**: Juan Manuel Otálora Hernandez
- **Integrante 2**: Juan Camilo Triana Paipa
- **Integrante 3**: Johan Stevan Bermeo Buitrago

### 🎓 Información Académica
- **Curso**: Programación Orientada a Objetos
- **Profesor**: Sergio A. Rojas
- **Institución**: Universidad Distrital Francisco José de Caldas
- **Semestre**: 2025-1

---

⭐ **¡Dale una estrella al proyecto si te resulta útil!** ⭐

*Desarrollado con 💜 por el equipo [NOMBRE_DEL_EQUIPO] para el Hackathon POO 2024*
