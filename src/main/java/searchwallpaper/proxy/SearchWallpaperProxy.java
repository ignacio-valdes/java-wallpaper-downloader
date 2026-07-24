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
        System.out.println("Crédito: Imágenes provistas por Wallhaven.cc");

        String imgName;
        if (args != null && args.length > 0 && !args[0].isBlank()) {
            imgName = args[0].trim();
        } else {
            // Crea un objeto Scanner para poder leer lo que el usuario escribe en la consola.
            Scanner scn = new Scanner(System.in);
            System.out.print("Ingresa el wallpaper que quieres buscar: ");
            imgName = scn.nextLine().trim();
            scn.close();
        }

        // Llama al cliente REST de Wallhaven para obtener la primera imagen.
        String imagenUrl = WallpaperApiClient.findFirstWallpaper(imgName);

        // Comprueba si el cliente de API realmente encontró una URL.
        if (imagenUrl != null) {

            //Crea una intancia del Proxy, pasándole la URL directa de la imagen.
            Wallpaper imagen = new WallpaperProxy(imagenUrl);

            // Llama al método mostrar(). Es en este momento cuando el Proxy se activa,
            // crea el objeto real (WallpaperDescargar) y se realiza la descarga y visualización.
            imagen.mostrar();
        } else {

            // Si la API devolvió null, significa que no se encontró ninguna imagen.
            // Se muestra un mensaje de error al usuario.
            WallpaperErrorHandler.showInfo(
                    "No se pudo encontrar ninguna imagen para la búsqueda ingresada.",
                    "Sin resultados",
                    "No se pudo encontrar ninguna imagen para la búsqueda ingresada.");
        }

    }

}
