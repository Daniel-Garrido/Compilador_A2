package package1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;

public class AnalizadorLexico4 {

    private final DefaultTableModel modelTableSymbol;
    private final DefaultTableModel modelTableError;
    private final ArrayList<String[]> rowsTableSymbol;
    private final ArrayList<String[]> rowsTableError;
    private final HashMap<String, String> valoresAsignados = new HashMap<>(); // Para almacenar identificadores con tipo
    private int contadorErroresSemanticos = 1;
    private String identificadorLadoIzquierdo = ""; // Variable para almacenar el identificador del lado izquierdo
    private StringTokenizer st;  // Variable de instancia para ser usada en varios métodos
    private String ultimoIdentificador = "";

    public AnalizadorLexico4(DefaultTableModel modelTableToken, DefaultTableModel modelTableError) {
        this.modelTableSymbol = modelTableToken;
        this.modelTableError = modelTableError;
        rowsTableSymbol = new ArrayList<>();
        rowsTableError = new ArrayList<>();
    }

    public void analizarExpresiones(String expresiones) {
        System.out.println("metodo analizar Expresiones");
        String[] lineas = expresiones.split("\n");
        int numeroLinea = 0;

        for (String linea : lineas) {
            numeroLinea++;
            st = new StringTokenizer(linea, "=;+-/*", true); // Inicializamos `st` aquí, para ser usado globalmente
            while (st.hasMoreTokens()) {
                String lexema = st.nextToken().trim();
                if (!lexema.isEmpty()) {
                    procesarLexema(lexema, numeroLinea);
                    System.out.println("lexema :"+lexema+"Numero linea :"+numeroLinea);
                }
            }
        }
        agregarAlaTabla();// aregar los lexemas a la tabla 
    }

//Metodo para analizar y verificar los lexemas 
private void procesarLexema(String lexema, int linea) {
        if (!lexemaYaAnalizado(lexema)) {
            if (lexema.matches("EQ11[0-9]+")) { // Identificador
                ultimoIdentificador = lexema;
                if (!valoresAsignados.containsKey(lexema)) {
                    rowsTableSymbol.add(new String[]{lexema, ""});  // Si el identificador no ha sido definido, lo agregamos con tipo vacío
                }
            } else if (lexema.equals("=")) {
                identificadorLadoIzquierdo = lexema; // Capturamos el identificador del lado izquierdo aquí
                rowsTableSymbol.add(new String[]{lexema, ""});
                // El igual indica el inicio de una asignación, no hacemos nada aquí
            } else if (lexema.matches("[0-9]+")) { // Entero
                actualizarTipoIdentificador(ultimoIdentificador, "Entero");
                valoresAsignados.put(ultimoIdentificador, "Entero");
                rowsTableSymbol.add(new String[]{lexema, "Entero"});
            } else if (lexema.matches("[0-9]+\\.[0-9]+")) { // Real
                actualizarTipoIdentificador(ultimoIdentificador, "Real");
                valoresAsignados.put(ultimoIdentificador, "Real");
                rowsTableSymbol.add(new String[]{lexema, "Real"});
            } else if (lexema.matches("\"[A-Za-z]+\"")) { // Cadena
                actualizarTipoIdentificador(ultimoIdentificador, "Cadena");
                valoresAsignados.put(ultimoIdentificador, "Cadena");
                rowsTableSymbol.add(new String[]{lexema, "Cadena"});
            } else if (lexema.matches("[+\\-*/]")) { // Operadores aritméticos
                // Obtener el siguiente token (operando derecho)
                rowsTableSymbol.add(new String[]{lexema, ""});
                String siguienteToken = obtenerSiguienteToken();
                verificarCompatibilidadTipos(linea, lexema, siguienteToken, identificadorLadoIzquierdo); // Pasamos el identificador del lado izquierdo
            }
        }

    }

//metodo para verificar la incompatiblidad de tipos de datos 
private void verificarCompatibilidadTipos(int linea, String operador, String operandoDerecho, String identificadorLadoIzquierdo) {
    String tipoIzquierdo = obtenerTipoDato(identificadorLadoIzquierdo); // Tipo del operando izquierdo (el antes del `=`)
    String tipoDerecho = null;

    // Verificar si el operando derecho es un identificador y si tiene valor asignado
    if (operandoDerecho != null && operandoDerecho.matches("EQ11[0-9]+")) {
        if (!valoresAsignados.containsKey(operandoDerecho)) {
            agregarErrorVariableIndefinida(operandoDerecho, linea); // Si el operando derecho no tiene valor asignado
            return;
        } else {
            tipoDerecho = obtenerTipoDato(operandoDerecho);
        }
    } else if (operandoDerecho != null && operandoDerecho.matches("[0-9]+")) { // Entero
        tipoDerecho = "Entero";
    } else if (operandoDerecho != null && operandoDerecho.matches("[0-9]+\\.[0-9]+")) { // Real
        tipoDerecho = "Real";
    } else if (operandoDerecho != null && operandoDerecho.matches("\"[A-Za-z]+\"")) { // Cadena
        tipoDerecho = "Cadena";
    }

    // Si uno de los dos tipos es nulo, significa que no podemos verificar la compatibilidad
    if (tipoIzquierdo == null || tipoDerecho == null) {
        return; // No verificamos la compatibilidad si falta alguno de los tipos
    }

    // Si ambos operandos tienen tipos y no son compatibles, agregamos un error de incompatibilidad de tipo
    if (!tipoIzquierdo.equals(tipoDerecho)) {
        // Incluir el identificador del lado izquierdo en el mensaje de error
        System.out.println(operandoDerecho + " linea :" + linea + " incompatibilidad de tipo: " + identificadorLadoIzquierdo);
        agregarErrorSemantico(operandoDerecho, linea, "Incompatibilidad de tipos: " + tipoIzquierdo + " y " + tipoDerecho + " en la asignación a " + identificadorLadoIzquierdo);
    }
}


    // Método para obtener el operando derecho tras un operador aritmético
    private String obtenerSiguienteToken() {
        if (st.hasMoreTokens()) {
            return st.nextToken().trim();  // Ahora usa la variable de instancia `st`
        }
        return null;
    }

    private void actualizarTipoIdentificador(String identificador, String tipoDato) {
        for (int i = 0; i < rowsTableSymbol.size(); i++) {
            String[] row = rowsTableSymbol.get(i);
            if (row[0].equals(identificador)) {
                row[1] = tipoDato;
                rowsTableSymbol.set(i, row);
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
        agregarErrorSemantico(lexema, linea, "Variable indefinida");
    }

     //metodo para  Evitar errores duplicados en la tabla de errores
    private void agregarErrorSemantico(String lexema, int linea, String descripcion) {
        if (!valorYaRegistrado(lexema)) {
            rowsTableError.add(new String[]{"ErrorSEM" + contadorErroresSemanticos, lexema, Integer.toString(linea), descripcion});
            contadorErroresSemanticos++;
        }
    }

    private boolean valorYaRegistrado(String lexema) {
        for (String[] row : rowsTableError) {
            if (row[1].equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    /*metodo para verificar que los lexemaas han sido revisados*/
    private boolean lexemaYaAnalizado(String lexema) {
        for (String[] row : rowsTableSymbol) {
            if (row[0].equals(lexema)) {
                return true;
            }
        }
        return false;
    }


    //agregar los datos a la tabla de simbolo y de errores
    private void agregarAlaTabla() {
        clearTable();
        for (String[] row : rowsTableSymbol) {
            modelTableSymbol.addRow(row);
        }
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
        }
    }

    //limpiar las tablas 
    private void clearTable() {
        for (int k = modelTableSymbol.getRowCount() - 1; k >= 0; k--) {
            modelTableSymbol.removeRow(k);
        }
        for (int k = modelTableError.getRowCount() - 1; k >= 0; k--) {
            modelTableError.removeRow(k);
        }
    }
}
