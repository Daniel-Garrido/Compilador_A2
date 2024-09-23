package package1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;

public class AnalizadorLexico3 {

    // Modelo de la tabla de símbolos
    private final DefaultTableModel modelTableSymbol;
    private final ArrayList<String[]> rowsTableSymbol;
    private final HashSet<String> operadoresAgregados; // Para evitar operadores duplicados
    private StringTokenizer st; // Para tokenizar las expresiones
    private String ultimoIdentificador; // El último identificador visto

    public AnalizadorLexico3(DefaultTableModel modelTableSymbol) {
        this.modelTableSymbol = modelTableSymbol;
        this.rowsTableSymbol = new ArrayList<>();
        this.operadoresAgregados = new HashSet<>();
    }

    // Método para analizar el código
    public void analizarCodigo(String codigo) {
        // Dividir el código en líneas
        String[] lineas = codigo.split("\n");
        
        for (String linea : lineas) {
            st = new StringTokenizer(linea, " =;+-/*", true); // Tokenizar con operadores
            while (st.hasMoreTokens()) {
                String token = st.nextToken().trim();
                if (!token.isEmpty()) {
                    procesarToken(token);
                }
            }
        }
        
        // Añadir los datos procesados a la tabla de símbolos
        actualizarTablaSimbolos();
    }

    // Procesar cada token individualmente
    private void procesarToken(String token) {
        if (token.matches("EQ11[0-9]+")) {
            // Identificador válido
            ultimoIdentificador = token;
            agregarATablaSimbolos(token, ""); // Se agregará el tipo después de asignar valor
        } else if (token.equals("=")) {
            // El signo de igualación se añade solo una vez
            agregarUnicoOperador(token);
        } else if (token.equals(";")) {
            // El punto y coma se añade solo una vez
            agregarUnicoOperador(token);
        } else if (token.matches("[+\\-*/]")) {
            // Operadores aritméticos: +, -, *, /
            agregarUnicoOperador(token);
        } else if (token.matches("[0-9]+")) {
            // Entero
            actualizarTipoIdentificador(ultimoIdentificador, "ENTERO");
            agregarATablaSimbolos(token, "ENTERO");
        } else if (token.matches("[0-9]+\\.[0-9]+")) {
            // Real
            actualizarTipoIdentificador(ultimoIdentificador, "REAL");
            agregarATablaSimbolos(token, "REAL");
        } else if (token.matches("\"[A-Za-z]+\"")) {
            // Cadena
            actualizarTipoIdentificador(ultimoIdentificador, "CADENA");
            agregarATablaSimbolos(token, "CADENA");
        }
    }

    // Añadir operadores únicos
    private void agregarUnicoOperador(String operador) {
        if (!operadoresAgregados.contains(operador)) {
            operadoresAgregados.add(operador);
            agregarATablaSimbolos(operador, "");
        }
    }

    // Actualizar el tipo de dato de un identificador
    private void actualizarTipoIdentificador(String identificador, String tipoDato) {
        for (String[] row : rowsTableSymbol) {
            if (row[0].equals(identificador)) {
                row[1] = tipoDato;
                return; // Actualizado, salir del bucle
            }
        }
    }

    // Agregar a la tabla de símbolos
    private void agregarATablaSimbolos(String lexema, String tipoDato) {
        rowsTableSymbol.add(new String[]{lexema, tipoDato});
    }

    // Actualizar la tabla de símbolos visual
    private void actualizarTablaSimbolos() {
        // Limpiar la tabla de símbolos actual
        while (modelTableSymbol.getRowCount() > 0) {
            modelTableSymbol.removeRow(0);
        }
        
        // Añadir las filas procesadas
        for (String[] row : rowsTableSymbol) {
            modelTableSymbol.addRow(row);
        }
    }
}
