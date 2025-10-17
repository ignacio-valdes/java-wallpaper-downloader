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
 * Esta es la clase "RealSubject" en el patrón de diseño Proxy.
 * 
 * @author igxnxcio
 */
public class WallpaperDescargar implements Wallpaper {
    
    // Almacena la URL de la imagen que se ha descargado.
    private String urlImagen;
    
    // Contiene la imagen descargada y lista para ser mostrada. Es un componente de Swing.
    private ImageIcon imagenIcono;
    
    /**
     * Constructor de la clase. Esta es la operación "costosa" que el Proxy retrasa.
     * Al crear una instancia de esta clase, la descarga de la imagen se inicia.
     * @param urlImagen 
     */
    public WallpaperDescargar (String urlImagen){
        
        this.urlImagen = urlImagen;
        //Llama al método para iniciar la descarga cuando se crea el objeto.
        descargarWallpaper(urlImagen);
    }
    
    /**
     * Método privado que maneja la lógica de la descarga de la imagen desde la web.
     * @param urlImg 
     */
    private void descargarWallpaper (String urlImg){
        System.out.println("📥 Descargando imagen desde: " + urlImg);
        try {
            // Crea un objeto URL a partir del String.
            URL url = new URL(urlImg);
            // ImagenIcon puede cargar una imagen directamente desde una URL.
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
    
    /**
     * Implementación del método de la interfaz. Se encarga de 
     * mostrar la imagen descargada en una ventana gráfica
     */
    @Override
    public void mostrar() {
        // Verifica que la imagen se haya descargado correctamente.
        if (imagenIcono != null) {
            System.out.println("Mostrando wallpaper descargado de wallhaven.cc");
            
            // Obtiene el objeto Image del ImageIcon para poder manipularlo.
            Image imagenOriginal = imagenIcono.getImage();
            
            // Crea una nueva version de la imagen, reescalada a un tamaño fijo de 1920x1080.
            // Image.SCALE_SMOOTH se usa para que el redimensionado tenga mayor calidad.
            Image imagenReescalada = imagenOriginal.getScaledInstance(1920,1080,Image.SCALE_SMOOTH);
            
            // Crea una nueva ImageIcon a partir de la imagen reescalada.
            ImageIcon imagenAjustada = new ImageIcon(imagenReescalada);
            
            // Crea una etiqueta de Swing para contener la imagen ajustada.
            JLabel etiquetaImagen = new JLabel(imagenAjustada);

            // Crea la ventana principal.
            JFrame frame = new JFrame("Visor de Imagen");
            // Configura la ventana para que se cierre sin termina la aplicación.
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // Añade la etiqueta con la imagen al contenedor de la ventana.
            frame.getContentPane().add(etiquetaImagen);

            // Fija el tamaño de la ventana.
            frame.setSize(1920,1080);
            // Centra la ventana en la pantalla
            frame.setLocationRelativeTo(null);
            // Hace que la ventana sea visible para el usuario.
            frame.setVisible(true);       
        } else {
            // Si el objeto imagenIcono es nulo, significa que la descarga falló.
            System.out.println("No hay imagen para mostrar");
        }

    }
    
}
