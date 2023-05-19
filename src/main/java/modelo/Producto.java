package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Producto {
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public static ResultSet BuscarRegistro(int IdProducto){
        PreparedStatement ps;
        Connection con;
        try {
                con = ModelConnection.getConnection();
                ps = con.prepareStatement("Select * FROM detalles WHERE iddetalle = ?");
                ps.setInt(1, IdProducto);
                ResultSet respuesta = ps.executeQuery();
                return respuesta;
            } catch (Exception e) {
                return null;
            }
    }
    
    public static ResultSet CargarRegistros(){
        PreparedStatement ps;
        Connection con;
        try {
                con = ModelConnection.getConnection();
                ps = con.prepareStatement("Select * FROM productos");
                ResultSet respuesta = ps.executeQuery();
                return respuesta;
            } catch (Exception e) {
                return null;
            }
    }
}
