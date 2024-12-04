/*
 Proyecto automatas 2
 Equipo 11
 Integrantes 
  -Daniel arcangel Garrido hoil 
  -Angel ernesto Gonzalez tun
 */

package package1;

//librerias a usar
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class Compilador extends javax.swing.JFrame {
    //agregamos la clase numero de linea 
    NumeroLinea numeroLinea;
    //declaramos las variables para el modelo de las tablas 
    private final DefaultTableModel modelTableSymbol = new DefaultTableModel(); //modelo para la tabla de simbolos
    private final DefaultTableModel modelTableError = new DefaultTableModel(); // modelo para la tabla de errores 
   
    
    //----------------clase de nuestro frame----------------------
    public Compilador() {  
        setResizable(false);// bloquea el tamaño de la ventana 
        initComponents();
        
       //agregar numero de linea al text area
       numeroLinea = new NumeroLinea(jTextArea1);//AGREGAMOS La informacion al textarea
       jScrollPane2.setRowHeaderView(numeroLinea);//hacemos que se muestre la informacion
       
       NumeroLinea n1;
       
       //Agregar numero de linea al text area 2 (codigo optimizado)
       n1 = new NumeroLinea(jTextArea2);
       jScrollPane5.setRowHeaderView(n1);
      
        //this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);//centrar la interfaz
        this.setTitle("Compilador Autómatas 2");
       
        // diseño al panel 
        jPanel1.setBackground(new Color(60,60,60)); 
        jPanel2.setBackground(new Color(45,45,45)); // Panel menu
      
       
        jLabel1.setForeground(Color.WHITE);
        jLabel2.setForeground(Color.WHITE);
        jLabel3.setForeground(Color.WHITE);
        jLabel4.setForeground(Color.WHITE);
        jLabel5.setForeground(Color.white);


        jTextArea1.setForeground(Color.BLACK);
        jTextArea2.setForeground(Color.BLACK);
       
        
        //objetos de nuestras tablas de errores y de simbolos
        JTable tablaSimbolos = new JTable();
        JTable tablaErrores = new JTable();
       
        
        //agregar datos a la tabla de simbolos
        tablaSimbolos.setModel(modelTableSymbol);
        modelTableSymbol.addColumn("lexema");
        modelTableSymbol.addColumn("tipo de dato");
        jScrollPane1.setViewportView(tablaSimbolos);
        
        //Agregar datos a la tabla de errores
        tablaErrores.setModel(modelTableError);
        modelTableError.addColumn("Token");
        modelTableError.addColumn("Lexema");
        modelTableError.addColumn("Linea");
        modelTableError.addColumn("Descripcion");
        jScrollPane3.setViewportView(tablaErrores);
        
        //Agrega Datos a la tabla de Optimizacion
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        Btn_Salir = new javax.swing.JButton();
        Btn_Analizar = new javax.swing.JButton();
        Btn_Triplo = new javax.swing.JButton();
        Btn_Optimizar = new javax.swing.JButton();
        Btn_Modo_Dark = new javax.swing.JButton();
        Btn_Limpiar = new javax.swing.JButton();
        Btn_Modo_White = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jScrollPane1.setBackground(new java.awt.Color(46, 43, 87));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setBackground(new java.awt.Color(85, 85, 85));
        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(85, 85, 85));
        jLabel2.setText("Código Optimizado");

        jScrollPane3.setBackground(new java.awt.Color(46, 43, 87));

        jLabel3.setBackground(new java.awt.Color(85, 85, 85));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(85, 85, 85));
        jLabel3.setText("Tabla de Símbolos");

        jLabel4.setBackground(new java.awt.Color(85, 85, 85));
        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(85, 85, 85));
        jLabel4.setText("Código Original");

        jLabel5.setBackground(new java.awt.Color(85, 85, 85));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(85, 85, 85));
        jLabel5.setText("Tabla de errores");

        jTextArea2.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea2.setRows(5);
        jScrollPane5.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(336, 336, 336))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jLabel4)
                        .addGap(305, 305, 305)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel3)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(238, 238, 238)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3)
                                .addGap(336, 336, 336))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        Btn_Salir.setBackground(new java.awt.Color(69, 73, 74));
        Btn_Salir.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Btn_Salir.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Salir.setText("Salir");
        Btn_Salir.setToolTipText("");
        Btn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SalirActionPerformed(evt);
            }
        });

        Btn_Analizar.setBackground(new java.awt.Color(69, 73, 74));
        Btn_Analizar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Btn_Analizar.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Analizar.setText("Analizar");
        Btn_Analizar.setBorderPainted(false);
        Btn_Analizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AnalizarActionPerformed(evt);
            }
        });

        Btn_Triplo.setBackground(new java.awt.Color(69, 73, 74));
        Btn_Triplo.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Btn_Triplo.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Triplo.setText("Triplo");
        Btn_Triplo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_TriploActionPerformed(evt);
            }
        });

        Btn_Optimizar.setBackground(new java.awt.Color(69, 73, 74));
        Btn_Optimizar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Btn_Optimizar.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Optimizar.setText("Optimizar");
        Btn_Optimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_OptimizarActionPerformed(evt);
            }
        });

        Btn_Modo_Dark.setBackground(new java.awt.Color(69, 73, 74));
        Btn_Modo_Dark.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Btn_Modo_Dark.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Modo_Dark.setText("Modo Dark");
        Btn_Modo_Dark.setBorderPainted(false);
        Btn_Modo_Dark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Modo_DarkActionPerformed(evt);
            }
        });

        Btn_Limpiar.setBackground(new java.awt.Color(69, 73, 74));
        Btn_Limpiar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Btn_Limpiar.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Limpiar.setText("Limpiar");
        Btn_Limpiar.setToolTipText("");
        Btn_Limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_LimpiarActionPerformed(evt);
            }
        });

        Btn_Modo_White.setBackground(new java.awt.Color(69, 73, 74));
        Btn_Modo_White.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Btn_Modo_White.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Modo_White.setText("Modo White");
        Btn_Modo_White.setToolTipText("");
        Btn_Modo_White.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Modo_WhiteActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Menú");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Btn_Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Analizar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Triplo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Optimizar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Modo_White, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Modo_Dark, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Modo_White, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Btn_Modo_Dark, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Optimizar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Triplo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Analizar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 956, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //btn Analizar programa 
    private void Btn_AnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AnalizarActionPerformed
 
        String txtAnalizar = jTextArea1.getText();//texto ingresado en la interfaz
        if (txtAnalizar.isEmpty()) {//verificar que exista un codigo para analizar 
            JOptionPane.showMessageDialog(null, "Campo vacio.\nIngrese un codigo \npara analizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int linea = 0;

        //Instancia de la clase analizadorLexico
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(modelTableSymbol, modelTableError);
        analizadorLexico.analizarExpresiones(jTextArea1.getText(), linea);
        JOptionPane.showMessageDialog(null, "Analizado con éxito");  
  
       
        
    }//GEN-LAST:event_Btn_AnalizarActionPerformed

    //btn cerrar el programa 
    private void Btn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SalirActionPerformed

        int confirmarOpcion = JOptionPane.showConfirmDialog(null, "Desea salir del programa?", " ", JOptionPane.YES_NO_OPTION);

        //verificamos la respuesta 
        if (confirmarOpcion == JOptionPane.YES_OPTION) {
            //cerrar la interfaz al dar click
            System.exit(0);
        } else if (confirmarOpcion == JOptionPane.NO_OPTION) {
            // se mantiene sin hacer nada
        }
    }//GEN-LAST:event_Btn_SalirActionPerformed

    //btn limpiar el programa
    private void Btn_LimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_LimpiarActionPerformed
      jTextArea1.setText(null); //limpia el contenido del TEXTAREA 1
      jTextArea2.setText(null); //limpia el contenido del TEXTAREA 2
        
        if(modelTableSymbol.getRowCount() > 0 || modelTableError.getRowCount() > 0) {
           modelTableSymbol.setRowCount(0); // limpia los datos de la tabla de simbolos
           modelTableError.setRowCount(0); // limpia los datos de la tabla de errores
        }
    }//GEN-LAST:event_Btn_LimpiarActionPerformed

    //btn modo dark 
    private void Btn_Modo_DarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Modo_DarkActionPerformed
       jPanel1.setBackground(new Color(60,60,60));
       jLabel1.setForeground(Color.white);
       jLabel2.setForeground(Color.white);
       jLabel3.setForeground(Color.white);
       jLabel4.setForeground(Color.white);
       jLabel5.setForeground(Color.white);
      
    }//GEN-LAST:event_Btn_Modo_DarkActionPerformed

    //btn modo white 
    private void Btn_Modo_WhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Modo_WhiteActionPerformed
        jPanel1.setBackground(new Color(255, 255, 255)); 
        jLabel2.setForeground(Color.BLACK);
        jLabel3.setForeground(Color.BLACK);
        jLabel4.setForeground(Color.BLACK);
        jLabel5.setForeground(Color.BLACK);
    }//GEN-LAST:event_Btn_Modo_WhiteActionPerformed

    //btn Analizar Triplo 
    private void Btn_TriploActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_TriploActionPerformed
        String txtAnalizar = jTextArea1.getText();//texto ingresado en la interfaz
        
        if(txtAnalizar.isEmpty()){
           JOptionPane.showMessageDialog(null, "campo vacio, por favor ingrese \n un código para convertir a triplos");
        }
        else {
            JOptionPane.showMessageDialog(null, "Se ha exportado correctamente \n los archivos del triplo");
        }
        
        //instancia de la clase triplos
        Triplos triplos = new Triplos();
        try {
            triplos.AnalizarTriplo(txtAnalizar);
        } catch (IOException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_Btn_TriploActionPerformed

    //btn Optimizar codigo 
    private void Btn_OptimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_OptimizarActionPerformed
        String txtAnalizar = jTextArea1.getText();//texto ingresado en la interfaz  
        Optimizar opt = new Optimizar(); 
        opt.optimizarCodigo(txtAnalizar, jTextArea2);
        
        
        //realizar el triplo optimizado con el codigo optimizado
        String codigoOptimizado = opt.getCodigoOptimizado();
        Triplos triplos = new Triplos();
        try {
            if(!codigoOptimizado.isEmpty()){
            triplos.AnalizarTriplo(codigoOptimizado);
        }
        } catch (IOException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_Btn_OptimizarActionPerformed

 
    //metodo main 
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Compilador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Analizar;
    private javax.swing.JButton Btn_Limpiar;
    private javax.swing.JButton Btn_Modo_Dark;
    private javax.swing.JButton Btn_Modo_White;
    private javax.swing.JButton Btn_Optimizar;
    private javax.swing.JButton Btn_Salir;
    private javax.swing.JButton Btn_Triplo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}

//Elaborado por Daniel Garrido
// visita mi sitio web