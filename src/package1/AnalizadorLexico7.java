package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;

public class AnalizadorLexico7 {

    private final DefaultTableModel modelTableSymbol;
    private final DefaultTableModel modelTableError;
    private final ArrayList<String[]> rowsTableSymbol;
    private final ArrayList<String[]> rowsTableError;
    private final HashMap<String, String> valoresAsignados = new HashMap<>(); // Almacenar identificadores con tipos de datos
    private int contadorErroresSemanticos = 1; // Contador para los errores 
    private String identificadorLadoIzquierdo = ""; // Variable para almacenar el identificador del lado izquierdo
    private StringTokenizer st;  // Variable de instancia para ser usada en varios métodos
    private String ultimoIdentificador = ""; // Variable para identificar cual ha sido el ultimo valor agregado

    // Constructor
    public AnalizadorLexico7(DefaultTableModel modelTableToken, DefaultTableModel modelTableError) {
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
            numeroLinea++; // Aumentamos el contador de líneas
            identificadorLadoIzquierdo = ""; // Reiniciar el identificador del lado izquierdo al iniciar una nueva línea
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
        if (!lexemaYaAnalizado(lexema)) { // Verificar si los lexemas ya han sido registrados
            if (lexema.matches("EQ11[0-9]+")) { // Identificador
                if (identificadorLadoIzquierdo.isEmpty()) {
                    // Solo asignar si es el primer identificador en la línea antes del "="
                    identificadorLadoIzquierdo = lexema;
                }
                ultimoIdentificador = lexema;
                if (!valoresAsignados.containsKey(lexema)) {
                    rowsTableSymbol.add(new String[]{lexema, ""}); // Si el identificador no ha sido definido, lo agregamos con tipo vacío
                }
            } else if (lexema.equals("=")) { // Igualación
                String siguienteToken = obtenerSiguienteToken();
                verificarCompatibilidadTipos(linea, lexema, siguienteToken, identificadorLadoIzquierdo); // Pasamos el identificador del lado izquierdo
            } else if (lexema.matches("[0-9]+")) { // Números Entero
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
                String siguienteToken = obtenerSiguienteToken();
                verificarCompatibilidadTipos(linea, lexema, siguienteToken, identificadorLadoIzquierdo); // Pasamos el identificador del lado izquierdo
            }
        }
    }

    // Método para verificar la incompatibilidad de tipos de datos
    private void verificarCompatibilidadTipos(int linea, String operador, String operandoDerecho, String identificadorLadoIzquierdo) {
        String tipoIzquierdo = obtenerTipoDato(identificadorLadoIzquierdo); // Tipo del lado izquierdo
        String tipoDerecho = null;

        // Verificar el tipo de dato del operando derecho
        if (operandoDerecho != null && operandoDerecho.matches("EQ11[0-9]+")) {
            if (!valoresAsignados.containsKey(operandoDerecho)) {
                agregarErrorVariableIndefinida(operandoDerecho, linea); // Si no tiene valor asignado
                return;
            } else {
                tipoDerecho = obtenerTipoDato(operandoDerecho); // Detectar el tipo de dato del lado derecho
            }
        } else if (operandoDerecho != null && operandoDerecho.matches("[0-9]+")) { // Entero
            tipoDerecho = "Entero";
        } else if (operandoDerecho != null && operandoDerecho.matches("[0-9]+\\.[0-9]+")) { // Real
            tipoDerecho = "Real";
        } else if (operandoDerecho != null && operandoDerecho.matches("\"[A-Za-z]+\"")) { // Cadena
            tipoDerecho = "Cadena";
        }

        // Si no hay tipos de datos para comparar, no podemos verificar
        if (tipoIzquierdo == null || tipoDerecho == null) {
            return; // No verificamos la compatibilidad si falta algún tipo
        }

        // Verificar incompatibilidad de tipos
        if (!tipoIzquierdo.equals(tipoDerecho)) {
            // Agregamos el error con el identificador del lado izquierdo en la descripción
            agregarErrorSemantico(operandoDerecho, linea, "Incompatibilidad de tipos: " + identificadorLadoIzquierdo);
        }
    }

    // Método para obtener el siguiente token tras un operador aritmético
    private String obtenerSiguienteToken() {
        if (st.hasMoreTokens()) {
            return st.nextToken().trim(); // Ahora usa la variable de instancia `st`
        }
        return null;
    }

    private void actualizarTipoIdentificador(String identificador, String tipoDato) {
        for (int i = 0; i < rowsTableSymbol.size(); i++) {
            String[] row = rowsTableSymbol.get(i);
            if (row[0].equals(identificador)) {
                row[1] = tipoDato;
                rowsTableSymbol.set(i, row); // Agregar los nuevos datos a la tabla de símbolos
                return;
            }
        }
    }

    private String obtenerTipoDato(String identificador) {
        for (String[] row : rowsTableSymbol) {
            if (row[0].equals(identificador)) {
                return row[1];
            }
        }
        return null;
    }

    private void agregarErrorVariableIndefinida(String lexema, int linea) {
        agregarErrorSemantico(lexema, linea, "Variable indefinida: " + identificadorLadoIzquierdo);
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
                return true;
            }
        }
        return false;
    }

    // Método para verificar que los lexemas han sido revisados
    private boolean lexemaYaAnalizado(String lexema) {
        for (String[] row : rowsTableSymbol) {
            if (row[0].equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    // Agregar los datos a la tabla de símbolos y de errores
    private void agregarAlaTabla() {
        clearTable();
        for (String[] row : rowsTableSymbol) {
            modelTableSymbol.addRow(row);
        }
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
        }
    }

    // Limpiar las tablas
    private void clearTable() {
        for (int k = modelTableSymbol.getRowCount() - 1; k >= 0; k--) {
            modelTableSymbol.removeRow(k);
        }
        for (int k = modelTableError.getRowCount() - 1; k >= 0; k--) {
            modelTableError.removeRow(k);
        }
    }
}
