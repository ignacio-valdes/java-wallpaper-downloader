package searchwallpaper.proxy;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Cliente REST para consultar la API de Wallhaven y extraer la URL directa de
 * la primera imagen disponible.
 */
public final class WallpaperApiClient {

    private WallpaperApiClient() {
    }

    public static String findFirstWallpaper(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            WallpaperErrorHandler.showInfo(
                    "No se proporcionó un término de búsqueda válido.",
                    "Búsqueda vacía",
                    "No se proporcionó un término de búsqueda válido.");
            return null;
        }

        try {
            String encodedTerm = URLEncoder.encode(searchTerm.trim(), StandardCharsets.UTF_8);
            
            // Aquí está la URL limpia y con el sorting correcto
            String apiUrl = "https://wallhaven.cc/api/v1/search?q=" + encodedTerm + "&sorting=toplist";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36")
                    .build();

            System.out.println("--------------------------Mensajes log-----------------------------");
            System.out.println("Consultando API de Wallhaven: " + apiUrl);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IOException("Respuesta HTTP inesperada de Wallhaven: " + response.statusCode());
            }

            JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray data = root.getAsJsonArray("data");

            if (data == null || data.isEmpty()) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("Lo sentimos, la búsqueda no arrojó ningún resultado.");
                return null;
            }

            JsonElement firstElement = data.get(0);
            if (!firstElement.isJsonObject()) {
                return null;
            }

            JsonObject firstWallpaper = firstElement.getAsJsonObject();
            JsonElement pathElement = firstWallpaper.get("path");

            if (pathElement == null || pathElement.isJsonNull()) {
                return null;
            }

            String imageUrl = pathElement.getAsString();
            System.out.println("URL final obtenida: " + imageUrl);
            return imageUrl;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            WallpaperErrorHandler.showError(
                    "No se pudo completar la búsqueda en Wallhaven.cc.",
                    "Error de búsqueda",
                    "No se pudo completar la búsqueda en Wallhaven.cc. Revisa tu conexión e inténtalo de nuevo.",
                    e);
            return null;
        } catch (RuntimeException e) {
            WallpaperErrorHandler.showError(
                    "No se pudo interpretar la respuesta de la API de Wallhaven.",
                    "Error de parsing",
                    "No se pudo interpretar la respuesta de la API de Wallhaven.",
                    e);
            return null;
        }
    }
}