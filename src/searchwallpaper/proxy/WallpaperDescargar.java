/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchwallpaper.proxy;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Esta es la clase "RealSubject" en el patrón de diseño Proxy.
 * 
 * @author igxnxcio
 */
public class WallpaperDescargar implements Wallpaper {
    
    // Almacena la URL de la imagen que se ha descargado.
    private String urlImagen;
    
    // Contiene la imagen descargada y lista para ser mostrada.
    private BufferedImage imagen;

    // Servicio responsable de descargar la imagen desde la red.
    private final WallpaperImageLoader imageLoader;

    // Servicio responsable de mostrar la imagen en Swing.
    private final WallpaperDisplayService displayService;
    
    /**
     * Constructor de la clase. Esta es la operación "costosa" que el Proxy retrasa.
     * Al crear una instancia de esta clase, la descarga de la imagen se inicia.
     * @param urlImagen 
     */
    public WallpaperDescargar (String urlImagen){

        this(urlImagen, new WallpaperImageLoader(), new WallpaperDisplayService());
    }

    public WallpaperDescargar(String urlImagen, WallpaperImageLoader imageLoader, WallpaperDisplayService displayService) {
        this.urlImagen = urlImagen;
        this.imageLoader = imageLoader;
        this.displayService = displayService;
        // Llama al método para iniciar la descarga cuando se crea el objeto.
        descargarWallpaper(urlImagen);
    }
    
    /**
     * Método privado que maneja la lógica de la descarga de la imagen desde la web.
     * @param urlImg 
     */
    private void descargarWallpaper (String urlImg){
        System.out.println("📥 Descargando imagen desde: " + urlImg);
        try {
            this.imagen = imageLoader.load(urlImg);
            System.out.println("Descarga completada correctamente.");
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            WallpaperErrorHandler.showError(
                    "No se pudo descargar la imagen desde Wallhaven.cc.",
                    "Error de descarga",
                    "No se pudo descargar la imagen desde Wallhaven.cc. Revisa tu conexión e inténtalo de nuevo.",
                    e);
        }
    }
    
    /**
     * Implementación del método de la interfaz. Se encarga de 
     * mostrar la imagen descargada en una ventana gráfica
     */
    @Override
    public void mostrar() {
        // Verifica que la imagen se haya descargado correctamente.
        if (imagen != null) {
            System.out.println("Mostrando wallpaper descargado de wallhaven.cc");
            displayService.show(imagen, urlImagen);
        } else {
            // Si el objeto imagen es nulo, significa que la descarga falló.
            WallpaperErrorHandler.showInfo(
                    "No hay imagen para mostrar porque la descarga falló.",
                    "Sin imagen",
                    "No hay imagen para mostrar porque la descarga falló.");
        }

    }
    
}
