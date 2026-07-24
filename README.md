# Wallpaper Downloader

Este es un proyecto de escritorio en Java que permite a los usuarios buscar, visualizar y descargar wallpapers de alta resolución desde el sitio web [Wallhaven](https://wallhaven.cc). 

El proyecto destaca por implementar el patrón de diseño **Proxy** para la carga eficiente de recursos de red, combinado con principios de **Arquitectura Limpia (SRP - Single Responsibility Principle)** para separar la lógica de red, el procesamiento de imágenes y la interfaz de usuario.

---

## Características

* **Búsqueda Dinámica**: Ingresa cualquier término (ej: "cyberpunk", "montaña") para encontrar y extraer el mejor resultado mediante **web scraping**.
* **Patrón Proxy (Lazy Loading)**: La descarga de la imagen en memoria (la operación más costosa) se retrasa estratégicamente hasta el momento exacto en que se solicita su visualización.
* **Resolución y Reescalado Dinámico**: La aplicación detecta automáticamente la resolución de la pantalla del dispositivo en el que se ejecuta y reescala la imagen manteniendo su relación de aspecto (*aspect ratio*).
* **Descarga a Disco Local**: Incluye un servicio dedicado para guardar el wallpaper directamente en el equipo del usuario con un solo clic, conservando su extensión original (`.jpg` o `.png`).
* **Manejo de Errores y UX**: Sistema de alertas mediante cuadros de diálogo (`JOptionPane`) y cursores de espera para informar al usuario sobre estados de carga, fallos de red o problemas de permisos.
* **Atribución Legal**: La interfaz gráfica integra los créditos correspondientes a Wallhaven para respetar los términos de uso y derechos de autor.

---

### ¿Cómo Ejecutarlo?

Para ejecutar este proyecto, necesitas tener el **JDK 21** y **Maven** instalados en tu sistema.

1.  **Clona el repositorio:**
    ```bash
    git clone [https://github.com/ignacio-valdes/java-wallpaper-downloader.git](https://github.com/ignacio-valdes/java-wallpaper-downloader.git)
    cd java-wallpaper-downloader
    ```

2.  **Compila el proyecto y descarga dependencias:**
    ```bash
    mvn clean compile
    ```

3.  **Ejecuta la aplicación:**
    *   Para buscar de forma interactiva:
        ```bash
        mvn exec:java -Dexec.mainClass="searchwallpaper.proxy.SearchWallpaperProxy"
        ```
    *   Para buscar directamente desde la terminal (CLI):
        ```bash
        mvn exec:java -Dexec.mainClass="searchwallpaper.proxy.SearchWallpaperProxy" -Dexec.args="tu termino de busqueda"
        ```

---

## Tecnologías y Arquitectura

* **Java Core**: Lenguaje principal.
* **Java `HttpClient`**: Para realizar peticiones HTTP de forma nativa.
* **Java Swing & AWT**: Para la renderización de la interfaz gráfica, botones y detección de hardware (pantalla).
* **Regex (Expresiones Regulares)**: Para analizar el DOM HTML y extraer los enlaces directos del CDN de Wallhaven.
* **Patrones de Diseño**: 
  * **Proxy**: Controla el acceso al objeto real de la imagen.
  * **Separación de Responsabilidades (SRP)**: Uso de servicios auxiliares (`WallpaperImageLoader`, `WallpaperSaveService`, `WallpaperDisplayService`) para evitar el acoplamiento en el código.