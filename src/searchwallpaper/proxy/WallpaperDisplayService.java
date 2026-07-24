package searchwallpaper.proxy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Encapsula la presentación Swing y el reescalado dinámico manteniendo la
 * relación de aspecto.
 */
public class WallpaperDisplayService {

    private static final String CREDIT_TEXT = "Imágenes provistas por Wallhaven.cc";
    private static final int FOOTER_HEIGHT = 72;

    public void show(BufferedImage image) {
        if (image == null) {
            WallpaperErrorHandler.showInfo(
                    "No hay imagen para mostrar.",
                    "Sin imagen",
                    "No hay imagen para mostrar.");
            return;
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int availableWidth = Math.max(1, screenSize.width);
        int availableHeight = Math.max(1, screenSize.height - FOOTER_HEIGHT);

        double scale = Math.min(
                (double) availableWidth / image.getWidth(),
                (double) availableHeight / image.getHeight());

        int scaledWidth = Math.max(1, (int) Math.round(image.getWidth() * scale));
        int scaledHeight = Math.max(1, (int) Math.round(image.getHeight() * scale));

        Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel creditLabel = new JLabel(CREDIT_TEXT, SwingConstants.CENTER);
        creditLabel.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));

        JFrame frame = new JFrame("Visor de Imagen");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(creditLabel, BorderLayout.SOUTH);
        frame.setSize(scaledWidth, scaledHeight + FOOTER_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}