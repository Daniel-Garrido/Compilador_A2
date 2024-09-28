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
    private final HashMap<String, String> valoresAsignados = new HashMap<>(); // Almacenar identificadores con tipos de datos
    private int contadorErroresSemanticos = 1;// contador para los errores 
    private String identificadorLadoIzquierdo = ""; // Variable para almacenar el identificador del lado izquierdo
    private String variableAsignacion ="";
    private StringTokenizer st;  // Variable de instancia para ser usada en varios métodos
    private String ultimoIdentificador = "";// variable para identificar cual ha sido el ultimo valor agregado 
    private String identificadorAsignacion = null; 
    //constructor
    public AnalizadorLexico4(DefaultTableModel modelTableToken, DefaultTableModel modelTableError) {
        this.modelTableSymbol = modelTableToken;
        this.modelTableError = modelTableError;
        rowsTableSymbol = new ArrayList<>();
        rowsTableError = new ArrayList<>();
    }

    //convierte el codigo de entrada en tokens
    public void analizarExpresiones(String expresiones) {
      //  System.out.println("Verificar metodo analizar Expresiones");
        String[] lineas = expresiones.split("\n");//dividir por cada salto de linea 
        int numeroLinea = 0;

        for (String linea : lineas) {
            numeroLinea++;// aumentamos el contador 
            st = new StringTokenizer(linea, "=;+-/*", true); // tokenizamos buscando los delimitadores
            while (st.hasMoreTokens()) {
                String lexema = st.nextToken().trim();
                if (!lexema.isEmpty()) {// si el lexema no esta vacio se procesa 
                    procesarLexema(lexema, numeroLinea);
                   // System.out.println("Numero de linea :("+numeroLinea+ ") lexema :"+lexema);//depurar
                }
            }
        }
        agregarAlaTabla();// aregar los lexemas a la tabla 
    }

//Metodo para analizar y verificar los lexemas 
// Nueva variable auxiliar


// Método para analizar y verificar los lexemas
private void procesarLexema(String lexema, int linea) {
    if (!lexemaYaAnalizado(lexema)) {
        if (lexema.matches("EQ11[0-9]+")) { // Identificador
            ultimoIdentificador = lexema;  // Se actualiza el último identificador
            if (identificadorAsignacion == null) {
                // Si es el primer identificador, lo tomamos como el del lado izquierdo
                identificadorAsignacion = lexema;
            }

            if (!valoresAsignados.containsKey(lexema)) {
                rowsTableSymbol.add(new String[]{lexema, ""});  // Si el identificador no ha sido definido, lo agregamos con tipo vacío
            }
        } else if (lexema.equals("=")) { // Operación de asignación
            identificadorLadoIzquierdo = identificadorAsignacion; // Guardamos el identificador del lado izquierdo antes de la operación
            identificadorAsignacion = null; // Reiniciamos para la siguiente operación

        } else if (lexema.matches("[0-9]+")) { // Número Entero
            actualizarTipoIdentificador(ultimoIdentificador, "Entero");
            valoresAsignados.put(ultimoIdentificador, "Entero");
            rowsTableSymbol.add(new String[]{lexema, "Entero"});

        } else if (lexema.matches("[0-9]+\\.[0-9]+")) { // Número Real
            actualizarTipoIdentificador(ultimoIdentificador, "Real");
            valoresAsignados.put(ultimoIdentificador, "Real");
            rowsTableSymbol.add(new String[]{lexema, "Real"});

        } else if (lexema.matches("\"[A-Za-z]+\"")) { // Cadenas
            actualizarTipoIdentificador(ultimoIdentificador, "Cadena");
            valoresAsignados.put(ultimoIdentificador, "Cadena");
            rowsTableSymbol.add(new String[]{lexema, "Cadena"});

        } else if (lexema.matches("[+\\-*/]")) { // Operadores aritméticos
            // Antes de verificar, asegurarnos de que el identificador izquierdo es correcto
            if (identificadorLadoIzquierdo == null) {
                identificadorLadoIzquierdo = ultimoIdentificador;  // Asegurarse que está actualizado
            }

            // Obtener el siguiente token (operando derecho)
            String siguienteToken = obtenerSiguienteToken();
            verificarCompatibilidadTipos(linea, lexema, siguienteToken, identificadorLadoIzquierdo);
        }
    }
}

//metodo para verificar la incompatiblidad de tipos de datos 
private void verificarCompatibilidadTipos(int linea, String operador, String operandoDerecho, String identificadorLadoIzquierdo) {
    System.out.println("Verificación de incompatibilidad de tipos en línea " + linea);

    // Obtener el tipo del identificador del lado izquierdo (antes del '=')
    String tipoIzquierdo = obtenerTipoDato(identificadorLadoIzquierdo);
    System.out.println("Tipo del operando izquierdo (" + identificadorLadoIzquierdo + "): " + tipoIzquierdo);
    
    // Inicializar el tipo del lado derecho
    String tipoDerecho = null;

    // Verificar si el operando derecho es un identificador EQ11 que ya tiene un valor asignado
    if (operandoDerecho != null && operandoDerecho.matches("EQ11[0-9]+")) {
        if (!valoresAsignados.containsKey(operandoDerecho)) {
            agregarErrorVariableIndefinida(operandoDerecho, linea);  // Variable indefinida en el lado derecho
            return;
        } else {
            tipoDerecho = obtenerTipoDato(operandoDerecho);
            System.out.println("Tipo del operando derecho (" + operandoDerecho + "): " + tipoDerecho);
        }
    } else if (operandoDerecho != null && operandoDerecho.matches("[0-9]+")) {  // Caso entero
        tipoDerecho = "Entero";
    } else if (operandoDerecho != null && operandoDerecho.matches("[0-9]+\\.[0-9]+")) {  // Caso real
        tipoDerecho = "Real";
    } else if (operandoDerecho != null && operandoDerecho.matches("\"[A-Za-z]+\"")) {  // Caso cadena
        tipoDerecho = "Cadena";
    }

    // Si uno de los tipos es nulo, no podemos continuar con la verificación
    if (tipoIzquierdo == null || tipoDerecho == null) {
        System.out.println("No se puede verificar la compatibilidad. Alguno de los tipos es nulo.");
        return;
    }

    // Verificar la compatibilidad de tipos
    if (!tipoIzquierdo.equals(tipoDerecho)) {
        System.out.println("Incompatibilidad de tipos detectada en línea " + linea);
        System.out.println("Operando izquierdo: " + identificadorLadoIzquierdo + " (" + tipoIzquierdo + ")");
        System.out.println("Operando derecho: " + operandoDerecho + " (" + tipoDerecho + ")");

        // Registrar el error de incompatibilidad de tipos
        agregarErrorSemantico(operandoDerecho, linea, "Incompatibilidad de tipos en asignación: " + identificadorLadoIzquierdo + " es de tipo " + tipoIzquierdo);
    } else {
        System.out.println("Compatibilidad de tipos correcta en línea " + linea);
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
                rowsTableSymbol.set(i, row);//agregar los nuevos datos a la tabla de simbolos
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
    String token = "ErrorS" + (contadorErroresSemanticos++);  // Genera un identificador único para cada error
    rowsTableError.add(new String[]{token, lexema, String.valueOf(linea), descripcion});
}


    //metoo para verificar si un error ya ha sido registrado 
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
