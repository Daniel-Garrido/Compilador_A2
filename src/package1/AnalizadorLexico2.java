package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

public class AnalizadorLexico2 {
    private final DefaultTableModel modelTableToken;
    private final ArrayList<String[]> rowsTableToken; // LISTA DE MATRICES PARA DATOS DE LA TABLA DE TOKENS
    private final DefaultTableModel modelTableError;
    private final ArrayList<String[]> rowsTableError; // LISTA DE MATRICES PARA DATOS DE LA TABLA DE ERRORES
    private HashMap<String, String> tablaSimbolos = new HashMap<>();
    private String ultimoIdentificador = ""; // Variable para almacenar el último identificador visto
    private int contadorErroresSemanticos; // variable para contar los errores
    private HashMap<String, String> valoresAsignados = new HashMap<>();
    private String tipoDato;

    public AnalizadorLexico2(DefaultTableModel modelTableToken, DefaultTableModel modelTableError) {
        this.modelTableToken = modelTableToken;
        this.modelTableError = modelTableError;
        rowsTableToken = new ArrayList<>(); // lista de arreglo para la tabla de tokens
        rowsTableError = new ArrayList<>(); // lista de arreglos para la tabla de error
        contadorErroresSemanticos = 1;
    }

    // METODO PARA VERIFICAR LA ENTRADA DE EXPRESIONES
    public void analizarExpreciones(String expresiones, int linea) {
        String[] expresiones1 = expresiones.split("\n");  // Procesar cada línea del código
        linea = 0;
        for (String exprecion : expresiones1) {
            linea += 1;
            String exprecion1 = exprecion.replace(";", " ; ").replace(",", " , ");  // Reemplazar separadores
            StringTokenizer st = new StringTokenizer(exprecion1);
            tipoDato = "";  // Restablecer el tipo de dato

            // Procesar cada lexema dentro de la expresión
            while (st.hasMoreTokens()) {
                String lexema = st.nextToken();
                if (!lexema.equals("\n") && !lexema.equals("\t") && !lexema.equals(" ")) {
                    // Enviar cada token (lexema) al método analizarLexema
                    analizarLexema(lexema, linea);  // Aquí ya estás pasando cada token
                }
            }
        }
        // Finalmente, añadir los datos a las tablas visuales
        addTable();
    }

    /* Método para analizar cada lexema */
    private void analizarLexema(String lexema, int linea) {
        if (!lexemaYaAnalizado(lexema)) {

            /* TIPOS DE DATOS */
            String TD = "(Entero|Real|Cadena)";
            Pattern pattern = Pattern.compile(TD);

            // Si el lexema coincide con un tipo de dato (Entero, Real, Cadena)
            if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, ""});
                tipoDato = lexema;  // Guarda el tipo de dato actual
                return;
            }

            /* IDENTIFICADORES */
            String IDF = "(EQ11[0-9]+)";
            pattern = Pattern.compile(IDF);

            // Si el lexema coincide con un identificador válido
            if (pattern.matcher(lexema).matches()) {
                ultimoIdentificador = lexema;  // Guarda el último identificador encontrado
                rowsTableToken.add(new String[]{lexema, ""});  // Agrega el identificador a la tabla de tokens
                return;
            }

            /* ASIGNACIÓN (VALOR DESPUÉS DEL SIGNO =) */
            String AS = "=";
            pattern = Pattern.compile(AS);

            // Si el lexema es el signo de asignación '='
            if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, ""});
                return;  // Detectamos la asignación, no hacemos nada aquí
            }

            /* NÚMEROS ENTEROS */
            String NE = "[0-9]+";
            pattern = Pattern.compile(NE);

            // Si el lexema es un número entero
            if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, "Entero"});  // Agrega el número entero a la tabla de tokens
                valoresAsignados.put(ultimoIdentificador, lexema);  // Asigna el valor al identificador
                actualizarTipoIdentificador(ultimoIdentificador, "Entero");
                return;
            }

            /* NÚMEROS REALES */
            String NPC = "[0-9]+[.][0-9]+";
            pattern = Pattern.compile(NPC);

            // Si el lexema es un número real
            if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, "Entero"});  // Agrega el número entero a la tabla de tokens
                valoresAsignados.put(ultimoIdentificador, lexema);  // Asigna el valor al identificador
                actualizarTipoIdentificador(ultimoIdentificador, "Entero");
                return;
            }

            /* CADENA */
            String Cadena = "\"[A-Za-z0-9]+\"";
            pattern = Pattern.compile(Cadena);

            // Si el lexema es una cadena de texto
            if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, "Cadena"});  // Agrega la cadena a la tabla de tokens
                valoresAsignados.put(ultimoIdentificador, lexema);  // Asigna el valor al identificador
                actualizarTipoIdentificador(ultimoIdentificador, "Cadena");
                return;
            }

            // OPERADORES ARITMÉTICOS
            String OPA = "[+]|[-]|[*]|[/]|[%]";
            pattern = Pattern.compile(OPA);

            // Si el lexema es un operador aritmético, verifica los operandos
            if (pattern.matcher(lexema).matches()) {
                verificarIdentificadoresEnOperacion(linea);  // Nuevo método para verificar los identificadores en la operación
                rowsTableToken.add(new String[]{lexema, ""});
                return;
            }

            // SEPARADORES (COMA, PUNTO Y COMA)
            String SP = "[,;]";
            pattern = Pattern.compile(SP);

            // Si el lexema es un separador
            if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, ""});
                return;
            }
        }
    }
    private void verificarIdentificadoresEnOperacion(int linea) {
    // Recorremos los tokens hacia atrás para obtener los dos últimos identificadores
    String identificador1 = null;  // Identificador del lado izquierdo (EQ111)
    String identificador2 = null;  // Identificador del lado derecho (EQ112)
    boolean esOperacionAritmetica = true;

    for (int i = rowsTableToken.size() - 1; i >= 0; i--) {
        String[] token = rowsTableToken.get(i);
        String lexema = token[0];

        // Verificamos si el lexema es un identificador
        if (lexema.matches("EQ11[0-9]+")) {  // Si es un identificador
            if (identificador2 == null) {
                identificador2 = lexema;  // El segundo operando (EQ112, lado derecho)
            } else {
                identificador1 = lexema;  // El primer operando (EQ111, lado izquierdo)
                break;  // Ya tenemos ambos identificadores
            }
        }
    }

    // Verificamos si el identificador2 (lado derecho) tiene un valor asignado
    if (identificador2 != null && !valoresAsignados.containsKey(identificador2)) {
        // Si el identificador2 no tiene un valor asignado, es indefinido
        if (!errorVariableIndefinidaAgregado(identificador2)) {
            System.out.println("Variable indefinida: identificador 2 (lado derecho) :" + identificador2 + " en la línea: " + linea);
            agregarErrorVariableIndefinida(identificador2, linea);
        }
    }
    if (identificador1 != null && !valoresAsignados.containsKey(identificador1) ){
        if(!errorVariableIndefinidaAgregado(identificador1)){
              System.out.println("Variable indefinida: identificador 1 (lado izquierso) :" + identificador1 + " en la línea: " + linea);
            agregarErrorVariableIndefinida(identificador1, linea);
        }
    }

  
}


    private void actualizarTipoIdentificador(String identificador, String tipoDato) {
        // Recorre la tabla de tokens para encontrar el identificador y actualizar su tipo de dato
        for (int i = 0; i < rowsTableToken.size(); i++) {
            String[] row = rowsTableToken.get(i);
            if (row[0].equals(identificador)) {
                row[1] = tipoDato;  // Asigna el tipo de dato al identificador
                rowsTableToken.set(i, row);  // Actualiza la tabla de tokens
                break;
            }
        }
    }
    
    private void agregarErrorVariableIndefinida(String lexema, int linea) { 
        rowsTableError.add(new String[]{"ErrorSEM" + contadorErroresSemanticos, lexema, Integer.toString(linea), "Variable indefinida"});
        contadorErroresSemanticos++;
    }

    // Verifica si un error de variable indefinida ya ha sido agregado
    private boolean errorVariableIndefinidaAgregado(String lexema) {
        for (String[] row : rowsTableError) {
            if (row[1].equals(lexema) && row[3].equals("Variable indefinida")) {
                System.out.println("Verificar Error");
                return true;  // El error ya fue agregado
            }
        }
        return false;  // El error no ha sido agregado
    }

    /* Método para verificar si el lexema ya ha sido analizado */
    private boolean lexemaYaAnalizado(String lexema) {
        for (String[] row : rowsTableToken) {
            if (row[0].equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    /* Método para agregar los lexemas a la tabla de símbolos */
    private void addTable() {
        clearTable();
        for (String[] row : rowsTableToken) {
            modelTableToken.addRow(row);
        }
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
        }
    }

    /* Método para limpiar las tablas de símbolos */
    private void clearTable() {
        for (int k = modelTableToken.getRowCount() - 1; k >= 0; k -= 1) {
            modelTableToken.removeRow(k);
        }
        for (int k = modelTableError.getRowCount() - 1; k >= 0; k -= 1) {
            modelTableError.removeRow(k);
        }
    }
}

