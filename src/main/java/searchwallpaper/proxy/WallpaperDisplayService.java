package searchwallpaper.proxy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Encapsula la presentación Swing y el reescalado dinámico manteniendo la
 * relación de aspecto.
 */
public class WallpaperDisplayService {

    private static final String CREDIT_TEXT = "Imágenes provistas por Wallhaven.cc";
    private static final int FOOTER_HEIGHT = 88;

    private final WallpaperSaveService saveService;

    public WallpaperDisplayService() {
        this(new WallpaperSaveService());
    }

    public WallpaperDisplayService(WallpaperSaveService saveService) {
        this.saveService = saveService;
    }

    public void show(BufferedImage image, String sourceUrl) {
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

        JButton saveButton = new JButton("Descargar Fondo");
        saveButton.setFocusPainted(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        saveButton.setOpaque(true);
        saveButton.setBackground(new java.awt.Color(34, 139, 230));
        saveButton.setForeground(java.awt.Color.WHITE);
        saveButton.setFont(saveButton.getFont().deriveFont(java.awt.Font.BOLD));

        JFrame frame = new JFrame("Visor de Imagen");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(imageLabel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout(12, 0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        footerPanel.add(saveButton, BorderLayout.WEST);
        footerPanel.add(creditLabel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(event -> {
            Cursor previousCursor = frame.getCursor();
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            saveButton.setEnabled(false);

            try {
                String savedPath = saveService.save(image, sourceUrl);
                if (!java.awt.GraphicsEnvironment.isHeadless()) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Fondo guardado en:\n" + savedPath,
                            "Descarga completada",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                WallpaperErrorHandler.showError(
                        "No se pudo guardar la imagen en el disco local.",
                        "Error al guardar",
                        "No se pudo guardar la imagen en el disco local. Revisa permisos o espacio disponible.",
                        e);
            } finally {
                frame.setCursor(previousCursor);
                saveButton.setEnabled(true);
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}