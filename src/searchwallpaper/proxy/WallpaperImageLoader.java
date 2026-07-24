package searchwallpaper.proxy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import javax.imageio.ImageIO;

/**
 * Encapsula la descarga de la imagen para que el sujeto real no mezcle
 * la responsabilidad de red con la lógica de presentación.
 */
public class WallpaperImageLoader {

    private static final Duration TIMEOUT = Duration.ofSeconds(20);

    public BufferedImage load(String imageUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageUrl))
                .timeout(TIMEOUT)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36")
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Respuesta HTTP inesperada al descargar la imagen: " + response.statusCode());
        }

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(response.body()));

        if (image == null) {
            throw new IOException("El contenido descargado no es una imagen válida.");
        }

        return image;
    }
}