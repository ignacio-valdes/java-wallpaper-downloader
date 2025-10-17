/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchwallpaper.proxy;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase se encarga de hacer "web scraping" en la página de Wallhaven.
 * Su única responsabilidad es tomar una URL de búsqueda y extraer la URL directa
 * de la primera imagen que aparece en los resultados.
 * 
 * @author igxnxcio
 */
// ImageScraper.java
public class WallpaperScraper {

    /**
     * Busca en una URL de Wallhaven y devuelve el enlace directo a la primera imagen.
     * @param targetUrl
     * @return 
     */
    public static String findFirstWallpaperOnWallhaven(String targetUrl) {
        try {
            // 1. Configura y realiza la petición HTTP para obtener el código HTML de la página.
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    // Se añade un "User-Agent" para simular ser un navegador y evitar bloqueos.
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36")
                    .build();

            System.out.println("--------------------------Mensajes log-----------------------------");
            System.out.println("Descargando HTML desde: " + targetUrl);
            // Envía la petición y guarda la respuesta (el HTML de la página) como un String.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String htmlBody = response.body();

            // 2. Define la expresión regular (regex) para encontrar el enlace de la primera imagen.
            // Busca una etiqueta <a> con class"preview" y captura el contenido de su atributo href.
            String regex = "class=\"preview\" href=\"([^\"]*)\"";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(htmlBody);

            // 3. Intenta encontrar la primera coincidencia del patrón en el HTML.
            if (matcher.find()) {
                // Si se encuentra, extrae la URL capturada.
                String imagePageUrl = matcher.group(1);
                // Divide la URL por "/" para poder extraer el ID de la imagen.
                String[] parts = imagePageUrl.split("/");
                // El ID es siempre el último segmento de la URL.
                String imageId = parts[parts.length - 1];
                System.out.println("ID de imagen encontrado: " + imageId);

                // 4. Aísla el bloque de HTML correspondiente solo a la primera imagen para un análisis preciso.
                // Busca hacia atrás desde la posición del enlace para encontrar el inicio de su contenedor <figure>.
                int blockStartIndex = htmlBody.lastIndexOf("<figure ", matcher.start());

                // Verifica si encontró el inicio del bloque para evitar errores.
                if (blockStartIndex != -1) {
                    // Busca hacia delante para encontrar el final del bloque </figure>.
                    int blockEndIndex = htmlBody.indexOf("</figure>", matcher.end());
                    // "Recorta" el HTML para obtener solo el bloque de la primera imagen.
                    String firstImageBlockHtml = htmlBody.substring(blockStartIndex, blockEndIndex);

                    // 5. Determina la extensión del archivo (.jpg o .png) analizando el bloque aislado.
                    String extension = ".jpg";
                    // si el bloque contiene la etiqueta span específica para PNG, cambia la extensión.
                    if (firstImageBlockHtml.contains("<span class=\"png\">")) {
                        System.out.println("Es una imagen PNG.");
                        extension = ".png";
                    } else {
                        System.out.println("Es una imagen JPG.");
                    }

                    // 6. Construye la URL de descarga final usando el patrón de Wallhaven
                    String firstTwoChars = imageId.substring(0, 2);
                    String finalImageUrl = String.format("https://w.wallhaven.cc/full/%s/wallhaven-%s%s", firstTwoChars, imageId, extension);

                    System.out.println("URL final obtenida: " + finalImageUrl);
                    // Devuelve la URL final para que el cliente la pueda usar.
                    return finalImageUrl;
                }
            }

        } catch (Exception e) {
            // Si ocurre cualquier error, se informa en la consola.
            System.out.println("--------------------------------------------------------------------");
            System.err.println("No se puedo completar la búsqueda: " + e.getMessage());
            e.printStackTrace();
        }

        // Si el código llega hasta aquí, significa que no se encontró ninguna imagen.
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Lo sentimos, la búsqueda no arrojó ningún resultado.");
        // Devuelve null para indicar al cliente que la operación falló.
        return null;
    }
}
