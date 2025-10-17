/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchwallpaper.proxy;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.net.URL;
import java.awt.Image;

/**
 *
 * @author igxnxcio
 */
public class WallpaperDescargar implements Wallpaper {
    
    private String urlImagen;
    private ImageIcon imagenIcono;
    
    public WallpaperDescargar (String urlImagen){
        
        this.urlImagen = urlImagen;
        descargarWallpaper(urlImagen);
    }
    
    private void descargarWallpaper (String urlImg){
        System.out.println("📥 Descargando imagen desde: " + urlImg);
        try {
            URL url = new URL(urlImg);
            this.imagenIcono = new ImageIcon(url);
            Thread.sleep(1000);
            System.out.print(".");
            Thread.sleep(1000);
            System.out.print(".");
            Thread.sleep(1000);
            System.out.println(".");
            Thread.sleep(1000);
            System.out.println("Listo");
        } catch(Exception e) {
        }
    }
    
    @Override
    public void mostrar() {
        if (imagenIcono != null) {
            System.out.println("Mostrando wallpaper descargado de wallhaven.cc");
            
            Image imagenOriginal = imagenIcono.getImage();
            
            Image imagenReescalada = imagenOriginal.getScaledInstance(1920,1080,Image.SCALE_SMOOTH);
            
            ImageIcon imagenAjustada = new ImageIcon(imagenReescalada);
            
            JLabel etiquetaImagen = new JLabel(imagenAjustada);

            JFrame frame = new JFrame("Visor de Imagen");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(etiquetaImagen);

            frame.setSize(1920,1080);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);       
        } else {
            System.out.println("No hay imagen para mostrar");
        }

    }
    
}
