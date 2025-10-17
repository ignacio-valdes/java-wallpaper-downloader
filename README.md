# Wallpaper Downloader

Este es un proyecto de escritorio en Java que permite a los usuarios buscar y descargar wallpapers de alta resolución desde el sitio web [Wallhaven](https://wallhaven.cc). La aplicación utiliza el patrón de diseño **Proxy** para optimizar la descarga de imágenes y realiza **web scraping** para obtener los enlaces directos de las imágenes.

---

## Características

* **Búsqueda por Término**: Ingresa cualquier término (ej: "montaña", "tokyo revengers") para encontrar el primer resultado.
* **Patrón de Diseño Proxy**: La descarga de la imagen (la operación más costosa) se retrasa hasta el momento exacto en que se solicita su visualización, mejorando la eficiencia.
* **Detección de Formato**: El scraper detecta automáticamente si la imagen es `.jpg` o `.png` para construir la URL correcta.
* **Visualizador Integrado**: La imagen descargada se muestra en una ventana de la aplicación.
* **Ajuste de Imagen**: El código reescala la imagen a 1920x1080 para una visualización consistente.

---

## ¿Cómo Ejecutarlo?

Para ejecutar este proyecto, necesitas tener el **JDK (Java Development Kit)** instalado en tu máquina.

1.  **Clona o descarga el repositorio:**
    ```bash
    git clone https://github.com/ignacio-valdes/java-wallpaper-downloader.git
    ```

2.  **Navega a la carpeta del proyecto:**
    ```bash
    cd java-wallpaper-downloader
    ```

3.  **Compila todos los archivos Java:**
    Si estás en la carpeta raíz que contiene la carpeta `src`:
    ```bash
    javac -d build src/searchwallpaper/proxy/*.java
    ```

4.  **Ejecuta la aplicación:**
    ```bash
    java -cp build searchwallpaper.proxy.SearchWallpaperProxy
    ```
    Al ejecutarlo, se te pedirá en la consola que ingreses un término de búsqueda.

---

## Tecnologías Utilizadas

* **Java**: Lenguaje principal de la aplicación.
* **Java `HttpClient`**: Para realizar las peticiones web y descargar el contenido HTML de Wallhaven.
* **Regex (Expresiones Regulares)**: Para analizar el HTML y extraer la información necesaria (web scraping).
* **Swing**: Para crear la interfaz gráfica de usuario (la ventana que muestra la imagen).
* **Patrón de Diseño Proxy**: Para la gestión eficiente de la descarga de imágenes.
