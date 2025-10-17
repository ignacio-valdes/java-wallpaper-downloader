/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package searchwallpaper.proxy;
import java.util.Scanner;

/**
 *
 * @author igxnxcio
 */
public class SearchWallpaperProxy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Ingresa el wallpaper que quieres buscar: ");
        
        String imgName = scn.nextLine();
        System.out.println(imgName);
        
        String url = "https://wallhaven.cc/search?q=" + imgName.replace(" ", "+");
        
        String imagenUrl = WallpaperScraper.findFirstWallpaperOnWallhaven(url);
        
        if (imagenUrl != null) {
            Wallpaper imagen = new WallpaperProxy(imagenUrl);
        
            imagen.mostrar();
        } else {
            System.out.println("No se pudo encontrat ninguna imagen.");
        }
        
        
        scn.close();

    }
    
}
