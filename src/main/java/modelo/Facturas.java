
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Facturas {

    public static ResultSet CargarRegistros(){
        PreparedStatement ps;
        Connection con;
        try {
                con = ModelConnection.getConnection();
                ps = con.prepareStatement("Select * FROM facturas");
                ResultSet respuesta = ps.executeQuery();
                return respuesta;
            } catch (Exception e) {
                return null;
            }
    }
    
    public static ResultSet BuscarRegistro(int IdFactura){
        PreparedStatement ps;
        Connection con;
        try {
                con = ModelConnection.getConnection();
                ps = con.prepareStatement("Select * FROM facturas WHERE idfactura = ?");
                ps.setInt(1, IdFactura);
                ResultSet respuesta = ps.executeQuery();
                return respuesta;
            } catch (Exception e) {
                return null;
            }
    }
    
    public static ResultSet CargarUltimoRegistro(){
        PreparedStatement ps;
        Connection con;
        try {
                con = ModelConnection.getConnection();
                ps = con.prepareStatement("Select TOP 1 idfactura FROM facturas ORDER BY idfactura DESC");
                ResultSet respuesta = ps.executeQuery();
                return respuesta;
            } catch (Exception e) {
                return null;
            }
    }
    
    public static boolean CrearFactura (int idFactura, int NumeroFactura, String Fecha, String TipodePago, int DocumentoCliente, String NombreCliente, int Subtotal, int Descuento, int IVA, int TotalDescuento, int TotalImpuesto, int Total){
        PreparedStatement ps;
        boolean retorno = false;
        try {
                Connection con = ModelConnection.getConnection();
                ps = con.prepareStatement("INSERT INTO facturas (idfactura,numerofactura,fecha,tipodepago,documentocliente,nombrecliente,subtotal,descuento,iva,totaldescuento,totalimpuesto,total) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, idFactura);
                ps.setInt(2, NumeroFactura);
                ps.setString(3, Fecha);
                ps.setString(4, TipodePago);
                ps.setInt(5, DocumentoCliente);
                ps.setString(6, NombreCliente);
                ps.setInt(7, Subtotal);
                ps.setInt(8, Descuento);
                ps.setInt(9, IVA);
                ps.setInt(10, TotalDescuento);
                ps.setInt(11, TotalImpuesto);
                ps.setInt(12, Total);
                retorno = Boolean.parseBoolean(Integer.toString(ps.executeUpdate()));
                JOptionPane.showMessageDialog(null, "Factura Registrada");
                return retorno;
            } catch (Exception e) {
                return retorno;
            }
    }
    
    public static boolean ActualizarFactura (int idFactura, int NumeroFactura, String Fecha, String TipodePago, int DocumentoCliente, String NombreCliente, int Subtotal, int Descuento, int IVA, int TotalDescuento, int TotalImpuesto, int Total){
        PreparedStatement ps;
        boolean retorno = false;
        try {
                Connection con = ModelConnection.getConnection();
                ps = con.prepareStatement("UPDATE facturas SET numerofactura='"+ NumeroFactura + "', fecha='"+ Fecha + "', tipodepago='"+ TipodePago + "', documentocliente='"+ DocumentoCliente + "', nombrecliente='"+ NombreCliente + "', subtotal='"+ Subtotal + "', descuento='"+ Descuento + "', iva='"+ IVA + "', totaldescuento='"+ TotalDescuento + "', totalimpuesto='"+ TotalImpuesto + "', total='"+ Total + "' WHERE idfactura='"+ idFactura + "'");
                String texto = "UPDATE facturas SET numerofactura='"+ NumeroFactura + "', fecha='"+ Fecha + "', tipodepago='"+ TipodePago + "', documentocliente='"+ DocumentoCliente + "', nombrecliente='"+ NombreCliente + "', subtotal='"+ Subtotal + "', descuento='"+ Descuento + "', iva='"+ IVA + "', totaldescuento='"+ TotalDescuento + "', totalimpuesto='"+ TotalImpuesto + "', total='"+ Total + "' WHERE idfactura='"+ idFactura + "'";
                System.out.println(texto);
                retorno = Boolean.parseBoolean(Integer.toString(ps.executeUpdate()));
                JOptionPane.showMessageDialog(null, "Factura Actualizada");
                return retorno;
            } catch (Exception e) {
                return retorno;
            }
    }
    public static boolean Eliminar(int idFactura){
        boolean retorno = false;
        
        try {
            Connection con = ModelConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM facturas WHERE idfactura='" + idFactura + "'");
            retorno = Boolean.parseBoolean(Integer.toString(ps.executeUpdate()));
            JOptionPane.showMessageDialog(null, "Registro eliminado");
            return retorno;
        } catch (Exception e) {
            return retorno;
        }
    }
    
    
}
