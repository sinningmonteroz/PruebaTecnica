
package controlador;

import java.sql.ResultSet;
import modelo.Producto;

public class ControllerProductos {
    public static int idproducto;
    public static String producto;

    public ControllerProductos() {
    }
    
    public static ResultSet CargarProductos(){
        return Producto.CargarRegistros();
    }

    public static int getIdproducto() {
        return idproducto;
    }

    public static void setIdproducto(int idproducto) {
        ControllerProductos.idproducto = idproducto;
    }

    public static String getProducto() {
        return producto;
    }

    public static void setProducto(String producto) {
        ControllerProductos.producto = producto;
    }
 
    
}
