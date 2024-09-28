package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;

public class AnalizadorLexico6 {

    private final DefaultTableModel modelTableSymbol;
    private final DefaultTableModel modelTableError;
    private final ArrayList<String[]> rowsTableSymbol;
    private final ArrayList<String[]> rowsTableError;
    private final HashMap<String, String> valoresAsignados = new HashMap<>(); // Almacenar identificadores con tipos de datos
    private int contadorErroresSemanticos = 1; // Contador para los errores
    private String identificadorLadoIzquierdo = ""; // Variable para almacenar el identificador del lado izquierdo
    private StringTokenizer st;  // Variable de instancia para ser usada en varios métodos
    private String ultimoIdentificador = ""; // Variable para identificar cuál ha sido el último valor agregado

    // Constructor
    public AnalizadorLexico6(DefaultTableModel modelTableToken, DefaultTableModel modelTableError) {
        this.modelTableSymbol = modelTableToken;
        this.modelTableError = modelTableError;
        rowsTableSymbol = new ArrayList<>();
        rowsTableError = new ArrayList<>();
    }

    // Convierte el código de entrada en tokens
    public void analizarExpresiones(String expresiones) {
        String[] lineas = expresiones.split("\n"); // Dividir por cada salto de línea
        int numeroLinea = 0;

        for (String linea : lineas) {
            numeroLinea++; // Aumentamos el contador
            st = new StringTokenizer(linea, "=;+-/*", true); // Tokenizamos buscando los delimitadores
            while (st.hasMoreTokens()) {
                String lexema = st.nextToken().trim();
                if (!lexema.isEmpty()) { // Si el lexema no está vacío se procesa
                    procesarLexema(lexema, numeroLinea);
                }
            }
        }
        agregarAlaTabla(); // Agregar los lexemas a la tabla
    }

    private void procesarLexema(String lexema, int linea) {
        if (!lexemaYaAnalizado(lexema)) { // Verificar si el lexema ya ha sido procesado antes
            if (lexema.matches("EQ11[0-9]+")) { // Identificador
                identificadorLadoIzquierdo = lexema;  // Guardar el lado izquierdo (identificador)
                ultimoIdentificador = lexema;
                if (!valoresAsignados.containsKey(lexema)) {
                    rowsTableSymbol.add(new String[]{lexema, ""});  // Si no está asignado, agregar a la tabla de símbolos con tipo vacío
                }

            } else if (lexema.equals("=")) { // Asignación
                rowsTableSymbol.add(new String[]{lexema, ""});
                String siguienteToken = obtenerSiguienteToken(); // Obtener el lado derecho de la asignación
                verificarVariableIndefinida(siguienteToken, linea); // Verificar si la variable del lado derecho está indefinida

            } else if (lexema.matches("[0-9]+")) { // Números Enteros
                actualizarTipoIdentificador(ultimoIdentificador, "Entero");
                valoresAsignados.put(ultimoIdentificador, "Entero");
                rowsTableSymbol.add(new String[]{lexema, "Entero"});

            } else if (lexema.matches("[0-9]+\\.[0-9]+")) { // Números Reales
                actualizarTipoIdentificador(ultimoIdentificador, "Real");
                valoresAsignados.put(ultimoIdentificador, "Real");
                rowsTableSymbol.add(new String[]{lexema, "Real"});

            } else if (lexema.matches("\"[A-Za-z]+\"")) { // Cadenas
                actualizarTipoIdentificador(ultimoIdentificador, "Cadena");
                valoresAsignados.put(ultimoIdentificador, "Cadena");
                rowsTableSymbol.add(new String[]{lexema, "Cadena"});

            } else if (lexema.matches("[+\\-*/]")) { // Operadores aritméticos
                rowsTableSymbol.add(new String[]{lexema, ""});
                String siguienteToken = obtenerSiguienteToken();
                verificarCompatibilidadTipos(linea, lexema, siguienteToken, identificadorLadoIzquierdo); // Verificar compatibilidad de tipos
            }
        }
    }

    // Método para verificar si una variable es indefinida en asignaciones
    private void verificarVariableIndefinida(String ladoDerecho, int linea) {
        if (ladoDerecho != null && ladoDerecho.matches("EQ11[0-9]+")) { // Verificar si el lado derecho es un identificador
            if (!valoresAsignados.containsKey(ladoDerecho)) { // Si no ha sido asignado previamente
                System.out.println(" lado derecho "+ladoDerecho);
                agregarErrorVariableIndefinida(ladoDerecho, linea); // Agregar a la tabla de errores como indefinida
            }
        }
    }

    // Método mejorado para verificar incompatibilidad de tipos en operaciones aritméticas
    private void verificarCompatibilidadTipos(int linea, String operador, String operandoDerecho, String identificadorLadoIzquierdo) {
        String tipoIzquierdo = obtenerTipoDato(identificadorLadoIzquierdo); // Obtener tipo del lado izquierdo
        String tipoDerecho = null;

        // Verificar el tipo de dato del operando derecho
        if (operandoDerecho != null && operandoDerecho.matches("EQ11[0-9]+")) { // Si es un identificador
            if (!valoresAsignados.containsKey(operandoDerecho)) {
                agregarErrorVariableIndefinida(operandoDerecho, linea); // Variable indefinida
                return;
            } else {
                tipoDerecho = obtenerTipoDato(operandoDerecho); // Detectar el tipo del lado derecho
            }
        } else if (operandoDerecho.matches("[0-9]+")) { // Si es un número entero
            tipoDerecho = "Entero";
        } else if (operandoDerecho.matches("[0-9]+\\.[0-9]+")) { // Si es un número real
            tipoDerecho = "Real";
        } else if (operandoDerecho.matches("\"[A-Za-z]+\"")) { // Si es una cadena
            tipoDerecho = "Cadena";
        }

        // Si uno de los dos tipos es nulo, no se puede verificar la compatibilidad
        if (tipoIzquierdo == null || tipoDerecho == null) {
            return; // No verificamos si falta alguno de los tipos
        }

        // Verificar si hay incompatibilidad de tipos
        if (!tipoIzquierdo.equals(tipoDerecho)) {
            // Si hay incompatibilidad, agregar el error a la tabla de errores
            agregarErrorSemantico(operandoDerecho, linea, "Incompatibilidad de tipos: " + identificadorLadoIzquierdo + " (" + tipoIzquierdo + ") y " + operandoDerecho + " (" + tipoDerecho + ")");
        }
    }

    // Método para obtener el operando derecho tras un operador aritmético
    private String obtenerSiguienteToken() {
        if (st.hasMoreTokens()) {
            return st.nextToken().trim();  // Ahora usa la variable de instancia st
        }
        return null;
    }

    private void actualizarTipoIdentificador(String identificador, String tipoDato) {
        for (int i = 0; i < rowsTableSymbol.size(); i++) {
            String[] row = rowsTableSymbol.get(i);
            if (row[0].equals(identificador)) {
                row[1] = tipoDato; // Asignar el tipo de dato al identificador
                rowsTableSymbol.set(i, row); // Actualizar los datos en la tabla de símbolos
                return;
            }
        }
    }

    private String obtenerTipoDato(String identificador) {
        for (String[] row : rowsTableSymbol) {
            if (row[0].equals(identificador)) {
                return row[1]; // Devolver el tipo de dato asociado al identificador
            }
        }
        return null; // Si no se encuentra, devolver null
    }

    private void agregarErrorVariableIndefinida(String lexema, int linea) {
        agregarErrorSemantico(lexema, linea, "Variable indefinida");
    }

    // Método para evitar errores duplicados en la tabla de errores
    private void agregarErrorSemantico(String lexema, int linea, String descripcion) {
        if (!valorYaRegistrado(lexema)) {
            rowsTableError.add(new String[]{"ErrorS" + contadorErroresSemanticos, lexema, Integer.toString(linea), descripcion});
            contadorErroresSemanticos++;
        }
    }

    // Método para verificar si un error ya ha sido registrado
    private boolean valorYaRegistrado(String lexema) {
        for (String[] row : rowsTableError) {
            if (row[1].equals(lexema)) {
                return true; // El error ya está registrado
            }
        }
        return false; // El error no está registrado
    }

    // Método para verificar que los lexemas han sido revisados
    private boolean lexemaYaAnalizado(String lexema) {
        for (String[] row : rowsTableSymbol) {
            if (row[0].equals(lexema)) {
                return true; // El lexema ya ha sido analizado
            }
        }
        return false; // El lexema no ha sido analizado aún
    }

    // Método para agregar los datos a la tabla de símbolos y errores
    private void agregarAlaTabla() {
        clearTable();
        for (String[] row : rowsTableSymbol) {
            modelTableSymbol.addRow(row);
        }
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
        }
    }

    // Método para limpiar las tablas
    private void clearTable() {
        for (int k = modelTableSymbol.getRowCount() - 1; k >= 0; k--) {
            modelTableSymbol.removeRow(k);
        }
        for (int k = modelTableError.getRowCount() - 1; k >= 0; k--) {
            modelTableError.removeRow(k);
        }
    }
}

