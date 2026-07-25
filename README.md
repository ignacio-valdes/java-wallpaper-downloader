# Wallpaper Downloader

Este es un proyecto de escritorio en Java que permite a los usuarios buscar, visualizar y descargar wallpapers de alta resolución consumiendo la API oficial de [Wallhaven](https://wallhaven.cc/).

El proyecto destaca por implementar el patrón de diseño **Proxy** para la carga eficiente de recursos de red, combinado con principios de **Arquitectura Limpia (SRP - Single Responsibility Principle)** para separar la lógica de red, el procesamiento de imágenes y la interfaz de usuario.

## Características

* **Búsqueda Dinámica:** Ingresa cualquier término (ej: "cyberpunk", "montaña") para consultar la API de Wallhaven mediante solicitudes HTTP estructuradas.
* **Patrón Proxy (Lazy Loading):** La descarga de la imagen en memoria (la operación más costosa) se retrasa estratégicamente hasta el momento exacto en que se solicita su visualización.
* **Resolución y Reescalado Dinámico:** La aplicación detecta automáticamente la resolución de la pantalla del dispositivo en el que se ejecuta y reescala la imagen manteniendo su relación de aspecto (*aspect ratio*).
* **Descarga a Disco Local:** Incluye un servicio dedicado para guardar el wallpaper directamente en el equipo del usuario con un solo clic, conservando su extensión original (`.jpg` o `.png`) y automatizando la integración con el sistema operativo (GNOME/Ubuntu).
* **Manejo de Errores y UX:** Sistema de alertas mediante cuadros de diálogo (`JOptionPane`) y cursores de espera para informar al usuario sobre estados de carga, fallos de red o problemas de permisos.
* **Atribución Legal:** La interfaz gráfica integra los créditos correspondientes a Wallhaven para respetar los términos de uso y derechos de autor.

---

## ¿Cómo Ejecutarlo?

Para ejecutar este proyecto, necesitas tener el **JDK 21** y **Maven** instalados en tu sistema.

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/ignacio-valdes/java-wallpaper-downloader.git
   cd java-wallpaper-downloader
   ```

2. **Compila el proyecto y empaqueta el Fat JAR:**
   ```bash
   mvn clean package
   ```

3. **Ejecuta la aplicación:**
   * **Modo interactivo (CLI / Proxy):**
     ```bash
     mvn exec:java -Dexec.mainClass="searchwallpaper.proxy.SearchWallpaperProxy"
     ```
   * **Ejecutando el Fat JAR independiente:**
     ```bash
     java -jar target/wallpaper-downloader-1.0-SNAPSHOT.jar
     ```

---

## Tecnologías y Arquitectura

* **Java Core:** Lenguaje principal.
* **Java HttpClient:** Para realizar peticiones HTTP eficientes y nativas a la API REST.
* **Google Gson:** Para el análisis y deserialización de respuestas JSON.
* **Java Swing & AWT:** Para la renderización de la interfaz gráfica, botones y detección de hardware (pantalla).
* **Patrones de Diseño:**
  * **Proxy:** Controla el acceso perezoso (*lazy*) al objeto real de la imagen.
  * **Separación de Responsabilidades (SRP):** Uso de servicios auxiliares (`WallpaperApiClient`, `WallpaperImageLoader`, `WallpaperSaveService`, `WallpaperDisplayService`) para evitar el acoplamiento en el código.