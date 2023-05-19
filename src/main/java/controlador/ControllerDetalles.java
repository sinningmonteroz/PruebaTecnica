
package controlador;

import java.sql.ResultSet;
import modelo.Detalles;

public class ControllerDetalles {
    public static int idDetalle;
    public static int idFactura;
    public static int idProducto;
    public static int Cantidad;
    public static int PrecioUnitario;

    public ControllerDetalles(int idDetalle, int idFactura, int idProducto, int Cantidad, int PrecioUnitario) {
        this.idDetalle = idDetalle;
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.Cantidad = Cantidad;
        this.PrecioUnitario = PrecioUnitario;
    }

    public static ResultSet CargarDetalles(int factura){
        return Detalles.CargarRegistros(factura);
    }
    
    public static ResultSet BuscarRegistro(int Id){
        return Detalles.BuscarRegistro(Id);
    }
    
    public static ResultSet CargarUltimoRegistro(){
        return Detalles.CargarUltimoRegistro();
    }
    
    public static boolean CrearDetalle(){
        return Detalles.CrearDetalle(idDetalle,idFactura, idProducto, Cantidad, PrecioUnitario);
    }
    
    public static boolean ActualizarDetalle(){
        return Detalles.ActualizarDetalle(idDetalle,idFactura, idProducto, Cantidad, PrecioUnitario);
    }
    
    public static boolean EliminarRegistro(int Id){
        return Detalles.Eliminar(Id);
    }
    
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public int getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(int PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
    }
    
    
}
