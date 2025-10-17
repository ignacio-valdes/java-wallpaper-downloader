/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchwallpaper.proxy;

/**
 *
 * @author igxnxcio
 */
public class WallpaperProxy implements Wallpaper {

    // Almacena la referencia al objeto real. se inicializa en null y solo se crea
    // cuando el cliente llama al método mostrar() por primera vez.
    private WallpaperDescargar wallpaperDescargar;

    // Guarda la URL de la imagen que se deberá descargar.
    private String urlImagen;

    /**
     * Constructor del proxy.
     * Solo recibe y almacena la URL.
     *
     * @param urlImagen
     */
    public WallpaperProxy(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    /**
     * Implementación del método de la interfaz Wallpaper
     */
    @Override
    public void mostrar() {
        // Comprueba si el objeto real (WallpaperDescargar) ya se ha creado.
        if (wallpaperDescargar == null) {
            System.out.println("Proxy: El cliente necesita la imagen. Iniciando descargar...");
            System.out.println("--------------------------------------------------------------------");

            // Se crea la instancia del objeto real. Es en este momento que se
            // ejecuta el constructor de "WallpaperDescargar" y se produce la descarga.
            wallpaperDescargar = new WallpaperDescargar(urlImagen);
        }

        // Una vez que el objeto real existe,el proxy simplemente 
        // le delega la llamada para que muestre la imagen.
        wallpaperDescargar.mostrar();
    }

}
