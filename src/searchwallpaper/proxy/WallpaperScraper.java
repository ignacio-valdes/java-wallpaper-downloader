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
 *
 * @author igxnxcio
 */
// ImageScraper.java
public class WallpaperScraper {

    public static String findFirstWallpaperOnWallhaven(String targetUrl) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36")
                    .build();

            System.out.println("--------------------------Mensajes log-----------------------------");
            System.out.println("🔎Descargando HTML desde: " + targetUrl);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String htmlBody = response.body();

            String regex = "class=\"preview\" href=\"([^\"]*)\"";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(htmlBody);

            if (matcher.find()) {
                String imagePageUrl = matcher.group(1);
                String[] parts = imagePageUrl.split("/");
                String imageId = parts[parts.length - 1];
                System.out.println("ID de imagen encontrado: " + imageId);

                int blockStartIndex = htmlBody.lastIndexOf("<figure ", matcher.start());

                if (blockStartIndex != -1) {
                    int blockEndIndex = htmlBody.indexOf("</figure>", matcher.end());
                    String firstImageBlockHtml = htmlBody.substring(blockStartIndex, blockEndIndex);

                    String extension = ".jpg";
                    if (firstImageBlockHtml.contains("<span class=\"png\">")) {
                        System.out.println("Es una imagen PNG.");
                        extension = ".png";
                    } else {
                        System.out.println("Es una imagen JPG.");
                    }

                    String firstTwoChars = imageId.substring(0, 2);
                    String finalImageUrl = String.format("https://w.wallhaven.cc/full/%s/wallhaven-%s%s", firstTwoChars, imageId, extension);

                    System.out.println("🎯URL final obtenida: " + finalImageUrl);
                    return finalImageUrl;
                }
            }

        } catch (Exception e) {
            System.out.println("--------------------------------------------------------------------");
            System.err.println("No se puedo completar la busqueda: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("--------------------------------------------------------------------");
        System.out.println("🚫Lo sentimos, la búsqueda no arrojo ningun resultado.");
        return null;
    }
}
