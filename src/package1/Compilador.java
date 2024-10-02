/*
 Proyecto automatas 2
 Equipo 11
 Integrantes 
  -Daniel arcangel Garrido hoil 
  -Angel ernesto Gonzalez tun
  -Enrique Absalon Huchim Cano 
 */

package package1;

//librerias a usar
import java.awt.Color;
import package1.AnalizadorLexico4;
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
      
        //this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);//centrar la interfaz
        this.setTitle("Proyecto compilador automatas 2 Equipo 11 Final");
       
        // diseño al panel 
        jPanel1.setBackground(new Color(51,51,50));
        
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
         
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ingresar Codigo");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tabla de símbolos");

        jButton1.setBackground(new java.awt.Color(30, 169, 5));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Analizar");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tabla de errores");

        jButton3.setBackground(new java.awt.Color(159, 34, 7));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Eliminar");
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(12, 12, 12)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel1)))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel2)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addComponent(jLabel3)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(29, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3)
                            .addComponent(jButton2))
                        .addGap(63, 63, 63))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //btn Analizar programa 
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 
        String txtAnalizar = jTextArea1.getText();
        if (txtAnalizar.isEmpty()) {//verificar que exista un codigo para analizar 
            JOptionPane.showMessageDialog(null, "Campo vacio.\nIngrese un codigo \npara analizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int linea = 0;
        

//        AnalizadorLexico2 analizadorLexico = new AnalizadorLexico2(modelTableSymbol, modelTableError);
//        analizadorLexico.analizarExpreciones(jTextArea1.getText(), linea);
//        JOptionPane.showMessageDialog(null, "Analizador Lexico 2.");

        AnalizadorLexico4 analizadorLexico = new AnalizadorLexico4(modelTableSymbol, modelTableError);
        analizadorLexico.analizarExpresiones(jTextArea1.getText());
        JOptionPane.showMessageDialog(null, "AnalizadorLexico4");  
//        
//            AnalizadorLexico5 analizadorLexico = new AnalizadorLexico5(modelTableSymbol, modelTableError);
//        analizadorLexico.analizarExpresiones(jTextArea1.getText());
//        JOptionPane.showMessageDialog(null, "AnalizadorLexico 5");  
    }//GEN-LAST:event_jButton1ActionPerformed

    //btn cerrar el programa 
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int confirmarOpcion = JOptionPane.showConfirmDialog(null, "Desea salir del programa?", " ", JOptionPane.YES_NO_OPTION);

        //verificamos la respuesta 
        if (confirmarOpcion == JOptionPane.YES_OPTION) {
            //cerrar la interfaz al dar click
            System.exit(0);
        } else if (confirmarOpcion == JOptionPane.NO_OPTION) {
            // se mantiene sin hacer nada
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    //btn limpiar el programa
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      jTextArea1.setText(null); //limpia el contenido del TEXTAREA 
        
        if(modelTableSymbol.getRowCount() > 0 || modelTableError.getRowCount() > 0) {
           modelTableSymbol.setRowCount(0); // limpia los datos de la tabla de simbolos
           modelTableError.setRowCount(0); // limpia los datos de la tabla de errores
        }
    }//GEN-LAST:event_jButton3ActionPerformed
  
    //metodo main 
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Compilador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
