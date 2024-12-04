package package1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InterfazTriplos {
    public void mostrarArch(String archivo) {
        try {
            String ruta = archivo;
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Linea");
            tableModel.addColumn("Dato Objeto");
            tableModel.addColumn("Dato Fuente");
            tableModel.addColumn("Operador");
            
            try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
                String line;
                while ((line = reader.readLine()) != null) {
                	if(line.contains("Dato")) {continue;}
                    String[] rowData = line.split(",");
                    tableModel.addRow(rowData);
                }
            }
            JTable table = new JTable(tableModel);
            JOptionPane.showMessageDialog(null, new JScrollPane(table), "Tabla Triplos", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

