
package vista;

import controlador.*;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class NuevaFactura extends javax.swing.JFrame {
    public ControllerFacturas objFactura;
    public ControllerDetalles objDetalles;
    public int IDFacturacion;
    public int IDPuntero;
    DefaultTableModel Modelo;
    DefaultTableModel Modelo2;
    public NuevaFactura() {
        initComponents();
        
        //Tabla Productos
        String[] titulos = {"Id Producto", "Producto"};
        Modelo = new DefaultTableModel (null, titulos);
        jTable1.setModel(Modelo);
        
        //Tabla Detalles
        String[] titulos2 = {"Id","Id Producto", "Cantidad","Precio Unitario"};
        Modelo2 = new DefaultTableModel (null, titulos2);
        ListaCompra.setModel(Modelo2);
        
        if (IDFacturacion >= 1){
            CargarFactura();
            
        }else{
            LocalDate fechaActual = LocalDate.now();
            fecha.setText(fechaActual.toString());
        }
        
    }
    //Tabla productos
    void CargarTabla1(){
        while(Modelo.getRowCount() > 0){
            Modelo.removeRow(0);
        }
        try {
               ResultSet rs = ControllerProductos.CargarProductos();
               while(rs.next()){
                   Object[] pValores = {rs.getString("idproducto"), rs.getString("producto")};
                   Modelo.addRow(pValores);
                   
               }
            } catch (Exception e) {
            }
    }
    //Tabla detalles de factura
    void CargarTabla2(){
        
        while(Modelo2.getRowCount() > 0){
            Modelo2.removeRow(0);
        }
        try {
                int subtotal=0;
                int iva=Integer.parseInt(tIVA.getText());
                int descuento=Integer.parseInt(tdescuento.getText());
               ResultSet rs = ControllerDetalles.CargarDetalles(IDFacturacion);
               while(rs.next()){
                   Object[] pValores2 = {rs.getString("iddetalle"), rs.getString("idproducto"), rs.getString("cantidad"),rs.getString("preciounitario")};
                   Modelo2.addRow(pValores2);
                   subtotal = subtotal + rs.getInt("preciounitario");
               }
               subTotal.setText(String.valueOf(subtotal)) ;
               int ivaTotal = (subtotal*iva)/100;
               valorImpuesto.setText(String.valueOf(ivaTotal)) ;
               int descuentototal = (subtotal*descuento)/100;
               valorDescuento.setText(String.valueOf(descuentototal));
               int totalPagar = subtotal+ivaTotal-descuento;
               vTotal.setText(String.valueOf(totalPagar));
               
            } catch (Exception e) {
            }
    }
    
    void CrearFactura (){
        CargarUltimoIDFactura();
        int idFactura = IDFacturacion;
        int NumeroFactura = Integer.parseInt(numFactura.getText());
        String Fecha = fecha.getText();
        String TipodePago = (String) tipoPago.getSelectedItem();
        int DocumentoCliente = Integer.parseInt(IDPersona.getText());
        String NombreCliente = nombre.getText();;
        int Subtotal = Integer.parseInt(subTotal.getText());
        int Descuento = Integer.parseInt(tdescuento.getText());
        int IVA = Integer.parseInt(tIVA.getText());
        int TotalDescuento = Integer.parseInt(valorDescuento.getText());
        int TotalImpuesto = Integer.parseInt(valorImpuesto.getText());
        int Total = Integer.parseInt(vTotal.getText());
        
        objFactura = new ControllerFacturas(idFactura,NumeroFactura,Fecha,TipodePago,DocumentoCliente,NombreCliente,Subtotal,Descuento,IVA,TotalDescuento,TotalImpuesto,Total);
        boolean respuesta = objFactura.CrearFactura();
        if (respuesta==true){
            JOptionPane.showMessageDialog(null, "Error al ingresar datos, verifique la información");
        }else{
            DeshabilitarCampos();
        }
    }
    void CrearDetalle (){
        CargarUltimoIDDetalle();
        int idDetalle = IDPuntero;
        int idFactura = IDFacturacion;
        int Fila = jTable1.getSelectedRow();
        int idProducto = Integer.parseInt(jTable1.getValueAt(Fila, 0).toString());
        int Cantidad = Integer.parseInt(cantidadArticulos.getText());
        int Precio = Integer.parseInt(PrecioUnitario.getText());
        int i = JOptionPane.showConfirmDialog(null, "Desea agregar el articulo " + idProducto + " por "+ Cantidad+ " unidades por el precio de " + Precio);
        if (i==0){
            objDetalles = new ControllerDetalles(idDetalle, idFactura, idProducto, Cantidad, Precio);
        boolean respuesta = objDetalles.CrearDetalle();
        if (respuesta==true){
            JOptionPane.showMessageDialog(null, "Error al ingresar datos, verifique la información");
        }else{
            DeshabilitarCampos();
        }
        }
    }
    
    void ActualizarFactura (){
        int idFactura = IDFacturacion;
        int NumeroFactura = Integer.parseInt(numFactura.getText());
        LocalDate fechaActual = LocalDate.now();
        fecha.setText(fechaActual.toString());
        String Fecha = fecha.getText();
        String TipodePago = (String) tipoPago.getSelectedItem();
        int DocumentoCliente = Integer.parseInt(IDPersona.getText());
        String NombreCliente = nombre.getText();;
        int Subtotal = Integer.parseInt(subTotal.getText());
        int Descuento = Integer.parseInt(tdescuento.getText());
        int IVA = Integer.parseInt(tIVA.getText());
        int TotalDescuento = Integer.parseInt(valorDescuento.getText());
        int TotalImpuesto = Integer.parseInt(valorImpuesto.getText());
        int Total = Integer.parseInt(vTotal.getText());
        
        objFactura = new ControllerFacturas(idFactura,NumeroFactura,Fecha,TipodePago,DocumentoCliente,NombreCliente,Subtotal,Descuento,IVA,TotalDescuento,TotalImpuesto,Total);
        boolean respuesta = objFactura.ActualizarFactura();
        if (respuesta==true){
            JOptionPane.showMessageDialog(null, "Error al ingresar datos, verifique la información");
        }
    }
    
    void DeshabilitarCampos(){
        numFactura.setEnabled(false);
        fecha.setEnabled(false);
        tipoPago.setEnabled(false);
        IDPersona.setEnabled(false);
        nombre.setEnabled(false);
        //descuento.setEnabled(false);
        //tIVA.setEnabled(false);
        jButton3.setEnabled(false);
    }
    void HabilitarCampos(){
        numFactura.setEnabled(true);
        fecha.setEnabled(true);
        tipoPago.setEnabled(true);
        IDPersona.setEnabled(true);
        nombre.setEnabled(true);
        tdescuento.setEnabled(true);
        tIVA.setEnabled(true);
        jButton3.setEnabled(true);
    }
    
    void CargarUltimoIDFactura(){
        try {
               ResultSet rs = objFactura.CargarUltimoRegistro();
               while(rs.next()){
                   IDFacturacion = Integer.parseInt(rs.getString("idfactura")) +1;
                   System.out.println(IDFacturacion);
               }  
            } catch (Exception e) {
            }
    }
    void CargarUltimoIDDetalle(){
        try {
               ResultSet rs = objDetalles.CargarUltimoRegistro();
               while(rs.next()){
                   IDPuntero = Integer.parseInt(rs.getString("iddetalle")) +1;
                   System.out.println(IDPuntero);
               }  
            } catch (Exception e) {
            }
    }
    
    void CargarFactura(){
        try {
               ResultSet rs = ControllerFacturas.BuscarRegistro(IDFacturacion);
               while(rs.next()){
                   numFactura.setText(rs.getString("numerofactura"));
                    fecha.setText(rs.getString("fecha"));
                    tipoPago.setSelectedItem(rs.getString("tipodepago"));
                    IDPersona.setText(rs.getString("documentocliente"));
                    nombre.setText(rs.getString("nombrecliente"));
                    tdescuento.setText(rs.getString("descuento"));
                    tIVA.setText(rs.getString("iva"));
                    subTotal.setText(rs.getString("subtotal"));
                    valorImpuesto.setText(rs.getString("totalimpuesto"));
                    valorDescuento.setText(rs.getString("totaldescuento"));
                    vTotal.setText(rs.getString("total"));
                    CargarTabla1();
                    jButton3.setEnabled(false);
                    CargarTabla2();
                    numFactura.setEnabled(false);
               }
            } catch (Exception e) {
            }
    }
    
    void Eliminar(){
        int Fila = ListaCompra.getSelectedRow();
        int idD = Integer.parseInt(ListaCompra.getValueAt(Fila, 0).toString());
        
        int i = JOptionPane.showConfirmDialog(null, "Desea eliminar la factura?");
        if (i==0){
            ControllerDetalles.EliminarRegistro(idD);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        numFactura = new javax.swing.JTextField();
        fecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        IDPersona = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cantidadArticulos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListaCompra = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        subTotal = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tIVA = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        valorImpuesto = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        vTotal = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tdescuento = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        valorDescuento = new javax.swing.JLabel();
        tipoPago = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        PrecioUnitario = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Nueva Factura");

        jLabel2.setText("Número Factura:");

        fecha.setEnabled(false);

        jLabel3.setText("Tipo Pago:");

        jLabel4.setText("Fecha:");

        jLabel5.setText("Identificación:");

        jLabel6.setText("Nombre:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID Producto", "Producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Agregar producto");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Cantidad");

        cantidadArticulos.setText("1");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Lista completa:");

        ListaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ID Producto", "Cantidad", "Precio Unitario"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ListaCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListaCompraMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(ListaCompra);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Tu lista:");

        jButton2.setText("Eliminar producto");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setText("Crear factura");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setText("Subtotal:");

        subTotal.setText("0");

        jLabel12.setText("IVA:");

        tIVA.setText("20");

        jLabel13.setText("Impuesto:");

        valorImpuesto.setText("0");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Total a pagar");

        vTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        vTotal.setText("0");

        jLabel14.setText("Descuento:");

        tdescuento.setText("0");

        jLabel16.setText("Descuento");

        valorDescuento.setText("0");

        tipoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contado", "Credito" }));

        jLabel11.setText("%");

        jLabel17.setText("%");

        jLabel18.setText("Precio");

        PrecioUnitario.setText("0");
        PrecioUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrecioUnitarioActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setText("Cerrar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(numFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(IDPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel9))
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(tdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel17)))
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(tIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cantidadArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(PrecioUnitario)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(370, 370, 370)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel16)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(valorDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(jLabel13)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(valorImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(18, 18, 18)
                                                .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(vTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(160, 160, 160)
                                        .addComponent(jButton2)))
                                .addContainerGap(37, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(numFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(IDPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(tIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(tipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(tdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jButton3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cantidadArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(PrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(subTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(valorDescuento))
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(valorImpuesto))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(vTotal)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Eliminar();
        CargarTabla2();
        ActualizarFactura();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(numFactura.getText()!="" && IDPersona.getText()!="" && nombre.getText()!="" && tIVA.getText()!="" && tdescuento.getText()!=""){
            CrearFactura();
            CargarTabla1();
        }else{
            JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos");
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int cant = Integer.parseInt(cantidadArticulos.getText());
        int val = Integer.parseInt(PrecioUnitario.getText());
        if (cant <= 0 || val <= 0){
            JOptionPane.showMessageDialog(null, "Por favor modifica la cantidad o el precio, los valores negativos no se permiten");
        }else{
            CrearDetalle ();
            CargarTabla2();
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void PrecioUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrecioUnitarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrecioUnitarioActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        ActualizarFactura ();
        Principal nuevaVentana = new Principal();
        nuevaVentana.setVisible(true);
        
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        jButton1.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void ListaCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaCompraMouseClicked
        // TODO add your handling code here:
        jButton2.setEnabled(true);
    }//GEN-LAST:event_ListaCompraMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NuevaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NuevaFactura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IDPersona;
    private javax.swing.JTable ListaCompra;
    private javax.swing.JTextField PrecioUnitario;
    private javax.swing.JTextField cantidadArticulos;
    private javax.swing.JTextField fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField numFactura;
    private javax.swing.JLabel subTotal;
    private javax.swing.JTextField tIVA;
    private javax.swing.JTextField tdescuento;
    private javax.swing.JComboBox<String> tipoPago;
    private javax.swing.JLabel vTotal;
    private javax.swing.JLabel valorDescuento;
    private javax.swing.JLabel valorImpuesto;
    // End of variables declaration//GEN-END:variables
}
