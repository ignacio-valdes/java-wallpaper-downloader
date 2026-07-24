package searchwallpaper.proxy;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;

/**
 * Servicio dedicado exclusivamente al guardado local de fondos de pantalla.
 */
public class WallpaperSaveService {

    private static final Path DEFAULT_DIRECTORY = Paths.get(
            System.getProperty("user.home"),
            "Pictures",
            "Wallpapers");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public String save(BufferedImage image, String sourceUrl) throws IOException {
        if (image == null) {
            throw new IOException("No hay imagen disponible para guardar.");
        }

        Files.createDirectories(DEFAULT_DIRECTORY);

        String extension = resolveExtension(sourceUrl);
        String fileName = "wallpaper-" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + extension;
        Path outputPath = DEFAULT_DIRECTORY.resolve(fileName);

        String formatName = extension.substring(1).toLowerCase();
        if (!ImageIO.write(image, formatName, outputPath.toFile())) {
            throw new IOException("No se pudo escribir la imagen en formato " + formatName + ".");
        }

        return outputPath.toAbsolutePath().toString();
    }

    private String resolveExtension(String sourceUrl) {
        if (sourceUrl == null || sourceUrl.isBlank()) {
            return ".png";
        }

        String sanitizedUrl = sourceUrl.split("\\?")[0].toLowerCase();
        if (sanitizedUrl.endsWith(".jpg") || sanitizedUrl.endsWith(".jpeg")) {
            return ".jpg";
        }

        return ".png";
    }
}