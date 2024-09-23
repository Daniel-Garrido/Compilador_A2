package package1;
//importar librerias 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

public class AnalizadorLexico {

    private final DefaultTableModel modelTableSymbol;//Modelo de tabla para la tabla de simbolo
    private final DefaultTableModel modelTableError;//Modelo de tabla para las tablas de error
    private final ArrayList<String[]> rowsTableSymbol;//Lista de arreglo para la tabla de simbolos
    private final ArrayList<String[]> rowsTableError;//Lista de arreglos para la tabla de errores

    private String ultimoIdentificador = "";  // Variable para almacenar el último identificador visto
    private StringTokenizer st;  // Variable de instancia

    private int contadorErroresSemanticos;//variable para contar los errores semanticos 
    private boolean despuesDeAsignacion = false;
    private HashMap<String, String> valoresAsignados = new HashMap<>();//arreglo para detectar si un valor a sido asignado 
    private String tipoDato;// identificar tipo de dato 

    public AnalizadorLexico(DefaultTableModel modelTableToken, DefaultTableModel modelTableError) {
        this.modelTableSymbol = modelTableToken;
        this.modelTableError = modelTableError;
        rowsTableSymbol = new ArrayList<>();//lista de arreglo para la tabla de tokens
        rowsTableError = new ArrayList<>();//lista de arreglos para la tabla de error
        contadorErroresSemanticos = 1;//iniciamos el contador en 1 
    }

//Metodo para verificar el analisis de nuestras expresiones    
    public void analizarExpreciones(String expresiones, int linea) {
        System.out.println("Metodo para analizar expresiones");
        String[] expresiones1 = expresiones.split("\n");  // las expresiones se van a dividir al dar un salto de linea
        linea = 0;// la linea es igual a cero
        for (String exprecion : expresiones1) {
            linea += 1;
            String exprecion1 = exprecion.replace(";", " ; "); // Reemplazar separadores
            st = new StringTokenizer(exprecion1);  // Inicializamos el StringTokenizer como una variable de instancia
            tipoDato = "";  // Restablecer el tipo de dato por ahora no esta asignado 
            
            // Procesar cada lexema dentro de la expresión
            while (st.hasMoreTokens()) {
                String lexema = st.nextToken();
                if (!lexema.equals("\n") && !lexema.equals("\t") && !lexema.equals(" ")) {
                    analizarLexema(lexema, linea);  // Aquí ya estás pasando cada token
                    System.out.println("lexema :"+lexema +" linea :" +linea);
                }
            }
        }
        addTable();  // añadir los datos a las tablas
    }

    private void analizarLexema(String lexema, int linea) {
        System.out.println("Analizando lexemas:");

        if (!lexemaYaAnalizado(lexema)) { // Si el lexema no ha sido analizado, lo analizamos

            /* TIPOS DE DATOS */
            String TD = "(Entero|Real|Cadena)"; // Expresión regular para tipos de datos
            Pattern pattern = Pattern.compile(TD); // Compilar la expresión regular

            // Si el lexema coincide con un tipo de dato (Entero, Real, Cadena)
            if (pattern.matcher(lexema).matches()) {
                System.out.println("Lexema (Tipo de Dato): " + lexema);
                if (pattern.matcher(lexema).matches()) {
                    rowsTableSymbol.add(new String[]{lexema, ""});
                    tipoDato = lexema;  // Guarda el tipo de dato actual
                    return;
                }
            }

            /*----------- IDENTIFICADORES -----------------*/
            String IDF = "(EQ11[0-9]+)";
            pattern = Pattern.compile(IDF);

            // Si el lexema coincide con un identificador válido
            if (pattern.matcher(lexema).matches()) {
                if (pattern.matcher(lexema).matches()) {
                    ultimoIdentificador = lexema;  // Guarda el último identificador encontrado
                    rowsTableSymbol.add(new String[]{lexema, ""});  // Agrega el identificador a la tabla de tokens
                    return;
                }
                
            }

            //---------- Operación de asignación ------------------------------------ 
            String AS = "="; // Expresión regular para asignación
            pattern = Pattern.compile(AS); // Compilar la expresión regular

            if (pattern.matcher(lexema).matches()) {
                System.out.println("Lexema (Asignación): " + lexema);
                rowsTableSymbol.add(new String[]{lexema, ""});
                return;
            }

            /*-------------------- NÚMEROS ENTEROS -------------------------------*/
            String NE = "[0-9]+"; // Expresión regular para números enteros
            pattern = Pattern.compile(NE); // Compilar la expresión regular

            // Si el lexema es un número entero
            if (pattern.matcher(lexema).matches()) {
                System.out.println("Lexema (Entero): " + lexema);

                actualizarTipoIdentificador(ultimoIdentificador, "Entero"); // Actualizar el identificador con tipo Entero
                valoresAsignados.put(ultimoIdentificador, lexema);  // Asignar el valor al identificador
                rowsTableSymbol.add(new String[]{lexema, "Entero"});
                return;
            }

            /*----------- NÚMEROS REALES -------------------*/
            String NPC = "[0-9]+[.][0-9]+"; // Expresión regular para números reales
            pattern = Pattern.compile(NPC);

            // Si el lexema es un número real
            if (pattern.matcher(lexema).matches()) {
                System.out.println("Lexema (Real): " + lexema);
                actualizarTipoIdentificador(ultimoIdentificador, "Real"); // Actualizar el identificador con tipo Real
                valoresAsignados.put(ultimoIdentificador, lexema);  // Asignar el valor al identificador
                rowsTableSymbol.add(new String[]{lexema, "Real"});
                return;
            }

            /*------------------------ CADENA -----------------*/
            String Cadena = "\"[A-Za-z0-9]+\""; // Expresión regular para cadenas
            pattern = Pattern.compile(Cadena);

            // Si el lexema es una cadena de texto
            if (pattern.matcher(lexema).matches()) {
                System.out.println("Lexema (Cadena): " + lexema);
                actualizarTipoIdentificador(ultimoIdentificador, "Cadena"); // Actualizar el identificador con tipo Cadena
                valoresAsignados.put(ultimoIdentificador, lexema);  // Asignar el valor al identificador
                rowsTableSymbol.add(new String[]{lexema, "Cadena"});
                return;
            }

            /*---------------- OPERADORES ARITMÉTICOS ----------------*/
            String OPA = "[+]|[-]|[*]|[/]|[%]"; // Expresión regular para operadores aritméticos
            pattern = Pattern.compile(OPA); // Compilar la expresión regular

            // Si el lexema es un operador aritmético
            if (pattern.matcher(lexema).matches()) {
                verificarCompatibilidadTipos(linea, lexema);
                System.out.println("Operador Aritmético: " + lexema);

                // Verificamos si el último identificador tiene un tipo de dato asignado
                if (!valoresAsignados.containsKey(ultimoIdentificador)) {
                    // Si el identificador no tiene valor asignado, es una variable indefinida
                    if (!errorVariableIndefinidaAgregado(ultimoIdentificador)) {
                        agregarErrorVariableIndefinida(ultimoIdentificador, linea);  // Agregar a la tabla de errores
                        limpiarVariableIndefinida(ultimoIdentificador);  // Limpiar la variable indefinida para que no se le asigne un valor
                    }
                }

                rowsTableSymbol.add(new String[]{lexema, ""});//agregar operadores a la tabla de simbolos
                return;
            }

            /*---------------- SEPARADORES ----------------*/
            String SP = "[;]";
            pattern = Pattern.compile(SP);

            // Si el lexema es un separador
            if (pattern.matcher(lexema).matches()) {
                System.out.println("Lexema (Separador): " + lexema);
                rowsTableSymbol.add(new String[]{lexema, ""}); // Agregar separador a la tabla de símbolos
            }
        }
    }

// Método auxiliar para actualizar el tipo de dato del identificador
    private void actualizarTipoIdentificador(String identificador, String tipoDato) {
        // Recorre la tabla de símbolos y actualiza el tipo de dato del identificador
        for (int i = 0; i < rowsTableSymbol.size(); i++) {
            String[] row = rowsTableSymbol.get(i);
            if (row[0].equals(identificador)) {
                row[1] = tipoDato;  // Asigna el tipo de dato correcto al identificador
                rowsTableSymbol.set(i, row);  // Actualiza la tabla de símbolos

            }
        }
        // Si no está en la tabla, lo agregamos ahora que tiene un tipo de dato
        // rowsTableSymbol.add(new String[]{identificador, tipoDato});
    }

    private boolean errorVariableIndefinidaAgregado(String lexema) {
        for (String[] row : rowsTableError) {
            if (row[0].equals(lexema) && row[2].equals("Variable Indefinida")) {
                return true;  // El error ya fue agregado
            }
        }
        return false;  // El error no ha sido agregado
    }

    public void agregarErrorVariableIndefinida(String lexema, int linea) {
        //se manda el error a a la tabla de simbolos 
        // [Token, lexema, linea, descripcion] 
        rowsTableError.add(new String[]{"ErrorSem" + contadorErroresSemanticos, lexema, Integer.toString(linea), "Variable indefinida"});
        contadorErroresSemanticos++;
    }
    // Método auxiliar para limpiar variables indefinidas

    private void limpiarVariableIndefinida(String identificador) {
        valoresAsignados.remove(identificador); // Limpiar cualquier valor asignado a una variable indefinida
    }

    private String obtenerTipoDato(String identificador) {
        // Recorre la tabla de símbolos (rowsTableToken) para encontrar el identificador
        for (String[] row : rowsTableSymbol) {
            // Si encontramos el identificador en la tabla de símbolos
            if (row[0].equals(identificador)) {
                // Devolvemos el tipo de dato asignado (Entero, Real, Cadena)
                return row[1];  // Tipo de dato
            }
        }
        // Si no se encuentra el identificador, devolvemos null (sin tipo de dato)
        return null;

    }

    //metodo para verificar compatibilidad de tipos
    private void verificarCompatibilidadTipos(int linea, String operador) {
        System.out.println("metodo para verificar la compatibilidad de tipos");
        String tipoIzquierdo = null;
        String tipoDerecho = null;
        String identificadorIzquierdo = null;
        String identificadorDerecho = null;

        // Recorrer los tokens hacia atrás para obtener los dos operandos
        for (int i = rowsTableSymbol.size() - 1; i >= 0; i--) {
            String[] token = rowsTableSymbol.get(i);
            String lexema = token[0];

            // Buscar el primer operando (derecho)
            if (tipoDerecho == null && lexema.matches("EQ11[0-9]+")) {
                identificadorDerecho = lexema;
                tipoDerecho = obtenerTipoDato(lexema);
                System.out.println("vamos a ver que imprime en tipo derecho :" + tipoDerecho);
                if (tipoDerecho == null) {  // Verificar si no tiene tipo de dato
                }
            } // Buscar el segundo operando (izquierdo)
            else if (tipoIzquierdo == null && lexema.matches("EQ11[0-9]+")) {
                identificadorIzquierdo = lexema;
                tipoIzquierdo = obtenerTipoDato(lexema);
            }

            // Si ya tenemos ambos operandos, salimos del ciclo
            if (tipoIzquierdo != null && tipoDerecho != null) {
                break;
            }
        }

        // Verificar si ambos operandos tienen tipo de dato asignado
        if (tipoIzquierdo != null && tipoDerecho != null) {
            if (!tipoIzquierdo.equals(tipoDerecho)) {
                System.out.println("tipo de dato de lado izquierdo :" + tipoIzquierdo);
                System.out.println("tipo de dato de lado derecho :" + tipoDerecho);

                // Si los tipos son diferentes, registrar error de incompatibilidad de tipos
                System.out.println("Error: Incompatibilidad de tipos: " + tipoIzquierdo + " y " + tipoDerecho + " en la línea " + linea + " :" + ultimoIdentificador);
                rowsTableError.add(new String[]{"ErrorSEM" + contadorErroresSemanticos, identificadorIzquierdo, Integer.toString(linea), "Incompatibilidad de tipos:"+tipoIzquierdo});
                contadorErroresSemanticos++;
            }
        }
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

    /* Método para agregar los lexemas a la tabla de símbolos */
    private void addTable() {
        clearTable();
        for (String[] row : rowsTableSymbol) {
            modelTableSymbol.addRow(row);
        }
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
        }

        // Imprimir tabla de símbolos en consola
        System.out.println("\nTabla de Símbolos:");
        System.out.println("Lexema\tTipo de Dato");
        for (String[] row : rowsTableSymbol) {
            System.out.println(row[0] + "\t" + row[1]);
        }

        // Imprimir tabla de errores en consola
        System.out.println("\nTabla de Errores:");
        System.out.println("Token\tLexema\tLínea\tDescripción");
        for (String[] row : rowsTableError) {
            System.out.println(row[0] + "\t" + row[1] + "\t" + row[2] + "\t" + row[3]);
        }
    }

    /* Método para limpiar las tablas de símbolos */
    private void clearTable() {
        for (int k = modelTableSymbol.getRowCount() - 1; k >= 0; k -= 1) {
            modelTableSymbol.removeRow(k);
        }
        for (int k = modelTableError.getRowCount() - 1; k >= 0; k -= 1) {
            modelTableError.removeRow(k);
        }
    }
}
