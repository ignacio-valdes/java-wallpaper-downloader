/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package searchwallpaper.proxy;

/**
 * Define el contrato común para todos los objetos que pueden ser un wallpapper.
 * En el patrón de diseño Proxy, esta interfaz es el "Subject".
 * @author igxnxcio
 */
public interface Wallpaper {
    
    /**
     * Define el método que todas las clases de tipo Wallpaper deben implementar.
     * Su responsabilidad es mostrar el wallpaper en la pantalla.
     */
    void mostrar();
}
