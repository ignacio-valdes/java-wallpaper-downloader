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
    
    private WallpaperDescargar wallpaperDescargar;
    private String urlImagen;
     
    public WallpaperProxy (String urlImagen) {
        this.urlImagen = urlImagen;
    }
     
    @Override
    public void mostrar() {
        if (wallpaperDescargar == null) {
            System.out.println("Proxy: El cliente necesita la imagen. Iniciando descargar...");
            System.out.println("--------------------------------------------------------------------");
            wallpaperDescargar = new WallpaperDescargar(urlImagen);
        }
        wallpaperDescargar.mostrar();
    }
    
}
