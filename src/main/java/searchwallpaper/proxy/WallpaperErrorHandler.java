package searchwallpaper.proxy;

import java.awt.GraphicsEnvironment;
import javax.swing.JOptionPane;

/**
 * Centraliza el reporte de errores y mensajes informativos para evitar
 * capturas silenciosas.
 */
public final class WallpaperErrorHandler {

    private WallpaperErrorHandler() {
    }

    public static void showError(String consoleMessage, String dialogTitle, String dialogMessage, Throwable throwable) {
        if (consoleMessage != null && !consoleMessage.isBlank()) {
            System.err.println(consoleMessage);
        }

        if (throwable != null) {
            System.err.println(throwable.getClass().getSimpleName() + ": " + throwable.getMessage());
        }

        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, dialogMessage, dialogTitle, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showInfo(String consoleMessage, String dialogTitle, String dialogMessage) {
        if (consoleMessage != null && !consoleMessage.isBlank()) {
            System.out.println(consoleMessage);
        }

        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, dialogMessage, dialogTitle, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}