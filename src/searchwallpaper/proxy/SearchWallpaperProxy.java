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
        // Crea un objeto Scanner para poder leer lo que el usuario escribe en la consola.
        Scanner scn = new Scanner(System.in);

        System.out.print("Ingresa el wallpaper que quieres buscar: ");

        // Lee la línea completa que el usuario escribió y la guarda en la variable "imgName".
        String imgName = scn.nextLine();

        // Construye la URL de búsqueda para Wallhaven.
        // Reemplaza los espacion en blanco (" ") con el simbolo "+" para que la URL sea válida.        
        String url = "https://wallhaven.cc/search?q=" + imgName.replace(" ", "+");

        // Llama al método estático Scraper para que analice la página web
        // y devuelva la URL directa de la primera imagen encontrada.
        String imagenUrl = WallpaperScraper.findFirstWallpaperOnWallhaven(url);

        // Comprueba si el scraper realmente encontró una URL.
        if (imagenUrl != null) {

            //Crea una intancia del Proxy, pasándole la URL directa de la imagen.
            Wallpaper imagen = new WallpaperProxy(imagenUrl);

            // Llama al método mostrar(). Es en este momento cuando el Proxy se activa,
            // crea el objeto real (WallpaperDescargar) y se realiza la descarga y visualización.
            imagen.mostrar();
        } else {

            // Si el scraper devolvió null, significa que no se encontró ninguna imagen.
            // Se muestra un mensaje de error al usuario.
            System.out.println("No se pudo encontrar ninguna imagen.");
        }

        // Cierra el Scanner.
        scn.close();

    }

}
