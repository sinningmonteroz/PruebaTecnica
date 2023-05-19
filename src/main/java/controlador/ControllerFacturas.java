
package controlador;

import java.sql.ResultSet;
import modelo.Facturas;

public class ControllerFacturas {
    public static int idFactura;
    public static int NumeroFactura;
    public static String Fecha;
    public static String TipodePago;
    public static int DocumentoCliente;
    public static String NombreCliente;
    public static int Subtotal;
    public static int Descuento;
    public static int IVA;
    public static int TotalDescuento;
    public static int TotalImpuesto;
    public static int Total;

    public ControllerFacturas(int idFactura, int NumeroFactura, String Fecha, String TipodePago, int DocumentoCliente, String NombreCliente, int Subtotal, int Descuento, int IVA, int TotalDescuento, int TotalImpuesto, int Total) {
        this.idFactura = idFactura;
        this.NumeroFactura = NumeroFactura;
        this.Fecha = Fecha;
        this.TipodePago = TipodePago;
        this.DocumentoCliente = DocumentoCliente;
        this.NombreCliente = NombreCliente;
        this.Subtotal = Subtotal;
        this.Descuento = Descuento;
        this.IVA = IVA;
        this.TotalDescuento = TotalDescuento;
        this.TotalImpuesto = TotalImpuesto;
        this.Total = Total;
    }
    
    public static ResultSet CargarFacturas(){
        return Facturas.CargarRegistros();
    }
    
    public static ResultSet BuscarRegistro(int Id){
        return Facturas.BuscarRegistro(Id);
    }
    
    public static ResultSet CargarUltimoRegistro(){
        return Facturas.CargarUltimoRegistro();
    }
    
    public static boolean CrearFactura(){
        return Facturas.CrearFactura(idFactura, NumeroFactura, Fecha, TipodePago, DocumentoCliente, NombreCliente, Subtotal, Descuento, IVA, TotalDescuento, TotalImpuesto, Total);
    }
    
    public static boolean ActualizarFactura(){
        return Facturas.ActualizarFactura(idFactura, NumeroFactura, Fecha, TipodePago, DocumentoCliente, NombreCliente, Subtotal, Descuento, IVA, TotalDescuento, TotalImpuesto, Total);
    }
    
    public static boolean EliminarRegistro(int Id){
        return Facturas.Eliminar(Id);
    }

    public int getIdFactura() {
        return idFactura;
    }
    
    
}
