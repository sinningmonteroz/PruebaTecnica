
package modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Detalles {
    

    public static ResultSet BuscarRegistro(int IdDetalle){
        PreparedStatement ps;
        Connection con;
        try {
                con = ModelConnection.getConnection();
                ps = con.prepareStatement("Select * FROM detalles WHERE iddetalle = ?");
                ps.setInt(1, IdDetalle);
                ResultSet respuesta = ps.executeQuery();
                return respuesta;
            } catch (Exception e) {
                return null;
            }
    }
    
    public static ResultSet CargarRegistros(int IdFactura){
        PreparedStatement ps;
        Connection con;
        try {
                con = ModelConnection.getConnection();
                ps = con.prepareStatement("Select * FROM detalles WHERE idfactura=?");
                ps.setInt(1, IdFactura);
                ResultSet respuesta = ps.executeQuery();
                return respuesta;
            } catch (Exception e) {
                return null;
            }
    }
    
    public static boolean CrearDetalle (int iddetalle,int idfactura,int idproducto,int cantidad,int precio){
        PreparedStatement ps;
        boolean retorno = false;
            try {
                Connection con = ModelConnection.getConnection();
                ps = con.prepareStatement("INSERT INTO detalles (iddetalle,idfactura,idproducto,cantidad,preciounitario) VALUES (?,?,?,?,?)");
                ps.setInt(1, iddetalle);
                ps.setInt(2, idfactura);
                ps.setInt(3, idproducto);
                ps.setInt(4, cantidad);
                ps.setInt(5, precio);
                retorno = Boolean.parseBoolean(Integer.toString(ps.executeUpdate()));
                JOptionPane.showMessageDialog(null, "Objeto agregado");
                return retorno;
            } catch (Exception e) {
                return retorno;
            }
    }
    
    public static boolean ActualizarDetalle (int iddetalle, int idFactura, int idproducto, int cantidad, int precio){
        PreparedStatement ps;
        boolean retorno = false;
        try {
                Connection con = ModelConnection.getConnection();
                ps = con.prepareStatement("UPDATE detalles SET idfactura='"+ idFactura + "', idproducto='" + idproducto +"', cantidad='" + cantidad + "', precio='" + precio +"' WHERE iddetalle='"+ iddetalle + "'");
                String texto = "UPDATE detalles SET idfactura='"+ idFactura + "', idproducto='" + idproducto +"', cantidad='" + cantidad + "', precio='" + precio +"' WHERE iddetalle='"+ iddetalle + "'";
                System.out.println(texto);
                retorno = Boolean.parseBoolean(Integer.toString(ps.executeUpdate()));
                JOptionPane.showMessageDialog(null, "Factura Actualizada");
                return retorno;
            } catch (Exception e) {
                return retorno;
            }
    }
    
    public static ResultSet CargarUltimoRegistro(){
        PreparedStatement ps;
        Connection con;
        try {
                con = ModelConnection.getConnection();
                ps = con.prepareStatement("Select TOP 1 iddetalle FROM detalles ORDER BY iddetalle DESC");
                ResultSet respuesta = ps.executeQuery();
                return respuesta;
            } catch (Exception e) {
                return null;
            }
    }
    
    public static boolean Eliminar(int idDetalle){
        boolean retorno = false;
        
        try {
            Connection con = ModelConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM detalles WHERE iddetalle='" + idDetalle + "'");
            retorno = Boolean.parseBoolean(Integer.toString(ps.executeUpdate()));
            JOptionPane.showMessageDialog(null, "Registro eliminado");
            return retorno;
        } catch (Exception e) {
            return retorno;
        }
    }
    
    
}
