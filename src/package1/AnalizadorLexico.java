package package1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

public class AnalizadorLexico {

    private final DefaultTableModel modelTableSymbol; //modelo de tabla de simbolos
    private final DefaultTableModel modelTableError; //modelo de tabla de errores

    private final ArrayList<String[]> rowsTableSymbol; //lista de arreglos para la tabla de simbolos
    private final ArrayList<String[]> rowsTableError; //lista de arreglos para la tabla de errrores

    private final HashMap<String, String> identificadoresTipo; //almacena el tipo de dato del identificador
    private boolean asignacionRegistrada = false; //verificamos si una asignacion es registrada
    private boolean lexemaAgregado = false;
    private String tipoDato;//almacena el tipo de dato
    private String variableAsignacion = null;// inicalizamos la variable en vacio
    private String ladoIzquierdo;// determina el tipo de dato del operando izquierdo
    private int contadorErrorSemantico;//contador para los errores que encontremos 

    //Metodo constructor
    public AnalizadorLexico(DefaultTableModel modelTableToken, DefaultTableModel modelTableError) {

        this.modelTableSymbol = modelTableToken;
        this.modelTableError = modelTableError;

        rowsTableSymbol = new ArrayList<>();
        rowsTableError = new ArrayList<>();

        contadorErrorSemantico = 1; // INICIALIZA EL CONTADOR EN 1
        identificadoresTipo = new HashMap<>(); // almacena los tipos de datos de los identificadores
    }

    //Metodo para dividir las expresiones en tokens
    public void analizarExpresiones(String expreciones, int linea) {
        String[] expreciones1 = expreciones.split("\n");// buscamos un salto de linea
        linea = 0;//inicializar en cero
        for (String exprecion : expreciones1) {
            linea += 1;
            String expresion1 = exprecion.replace(";", " ; ").replace(",", " , ");//damos espacios a las , y ;
            StringTokenizer st = new StringTokenizer(expresion1);
            tipoDato = "";//dejamos vacio el tipo de dato por ahora

            //verificamos que se analicen todos los tokens
            while (st.hasMoreTokens()) {
                String lexema = st.nextToken();
                if (!lexema.equals("\n") && !lexema.equals("\t") && !lexema.equals(" ")) {
                    analizarLexema(lexema, linea, st);//llamamos al metodo analizar lexema y le pasamos los parametros 
                    // System.out.println(" Numero de linea :"+linea+ ", lexema :"+lexema+ " token : "+expresion1);
                }
            }
        }
        addTable();//agregamos los tokens a la tabla 
    }

    private void analizarLexema(String lexema, int linea, StringTokenizer st) {
        //se verifica si los lexemas no han sido analizazdos
        if (!lexemaYaAnalizado(lexema)) {
            //expresiones regulares de nuestro compilador 
            String Identificadores = "EQ11[0-9]+";
            String NumerosEnteros = "[0-9]+";
            String NumerosReales = "[0-9]+\\.[0-9]+";
            String Cadenas = "\"[^\"]*\"";
            String OperadoresAritmeticos = "[+\\-*/%]";
            String OperadoresRelacionales = ("<]|<=|>|>=|==|!=");
            String Asignacion = "=";
            String Separadores = "[(){},;]";
            Pattern pattern;

            // Verificación de identificadores
            pattern = Pattern.compile(Identificadores);
            if (pattern.matcher(lexema).matches()) {

                if (st.hasMoreTokens()) {
                    String nextToken = st.nextToken();

                    // Verifica si es un operador de asignación
                    if (nextToken.equals("=")) {
                        variableAsignacion = lexema;// guardamos los identificadores que estan antes del igual

                        if (!lexemaAgregado) {
                            rowsTableSymbol.add(new String[]{"=", ""});
                            lexemaAgregado = true; // Marcamos que ya se ha agregado
                        }
                        //System.out.println(" variable de asignacion :" +lexema);

                        // Obtiene el valor asignado
                        //String valorAsignado = capturarValorCompleto(st);
                        
                        String valorAsignado = st.hasMoreTokens() ? st.nextToken() : "";
                        ladoIzquierdo = valorAsignado;
                        System.out.println("Operando lado Izquierdo :" + ladoIzquierdo);

                        String tipoAsignado = determinarTipoPorValor(valorAsignado);

                        System.out.println("valor asignado :" + st.hasMoreTokens());
                        System.out.println("tipo asignado :" + tipoAsignado);

                        if (tipoAsignado != null) {
                            identificadoresTipo.put(lexema, tipoAsignado); // Asigna el tipo al identificador
                            rowsTableSymbol.add(new String[]{lexema, tipoAsignado});
                            rowsTableSymbol.add(new String[]{valorAsignado, tipoAsignado});

                        } else if (identificadoresTipo.containsKey(valorAsignado)) {
                            identificadoresTipo.put(lexema, identificadoresTipo.get(valorAsignado));
                            rowsTableSymbol.add(new String[]{lexema, identificadoresTipo.get(valorAsignado)});
                        } else {
                            // Variable indefinida
                            System.out.println("Variable indefinida " + contadorErrorSemantico + " valor asignado :" + valorAsignado + " linea :" + Integer.toString(linea) + " ");
                            rowsTableError.add(new String[]{"ErrorS" + contadorErrorSemantico, valorAsignado, Integer.toString(linea), "Variable indefinida"});
                            contadorErrorSemantico++;
                            
                            //Agregar la variable de asignacion sin tipo de dato 
                            identificadoresTipo.put(lexema, ""); 
                            rowsTableSymbol.add(new String[]{lexema, ""});
                        }
                        return;
                    } else {
                        // Verifica si el identificador ya tiene tipo asignado
                        if (!identificadoresTipo.containsKey(lexema)) {
                            System.out.println("Variable indefinida " + contadorErrorSemantico + " valor asignado :" + lexema + " linea :" + Integer.toString(linea) + " ");
                            
                            rowsTableError.add(new String[]{"ErrorS" + contadorErrorSemantico, lexema, Integer.toString(linea), "Variable indefinida"});
                            contadorErrorSemantico++;
                        } else {
                            System.out.println(" lexema :" + lexema + " identificaodres :" + identificadoresTipo.get(lexema));
                            rowsTableSymbol.add(new String[]{lexema, identificadoresTipo.get(lexema)});
                        }
                        analizarLexema(nextToken, linea, st); // Recursivamente analiza el siguiente token
                    }
                }

                return;
            }

            // Verifica si es un número entero
            pattern = Pattern.compile(NumerosEnteros);
            if (pattern.matcher(lexema).matches()) {
                rowsTableSymbol.add(new String[]{lexema, "Entero"});
                return;
            }

            // Verifica si es un número real
            pattern = Pattern.compile(NumerosReales);
            if (pattern.matcher(lexema).matches()) {
                rowsTableSymbol.add(new String[]{lexema, "Real"});
                return;
            }

            // Verifica si es una cadena
            pattern = Pattern.compile(Cadenas);
            if (pattern.matcher(lexema).matches()) {
                rowsTableSymbol.add(new String[]{lexema, "Cadena"});
                return;
            }

            // Verifica si es un operador aritmético
            pattern = Pattern.compile(OperadoresAritmeticos);
            if (pattern.matcher(lexema).matches()) {
                if (st.hasMoreTokens()) {

                    String operando1 = ladoIzquierdo;
                    System.out.println(" operando 1:" + operando1);
                    String tipoOperando1 = obtenerTipoOperando(operando1);

                    String operando2 = st.nextToken();
                    System.out.println(" operando 2:" + operando2);
                    String tipoOperando2 = obtenerTipoOperando(operando2);

                    if (tipoOperando1 == null || tipoOperando2 == null) {
                         //rowsTableError.add(new String[]{"ErrorS" + contadorErrorSemantico, operando1, Integer.toString(linea), "Variable idefinida"});
                         //contadorErrorSemantico++;
                    }
                    
                    
                    else if (!tiposCompatibles(tipoOperando1, tipoOperando2)) {
                        rowsTableError.add(new String[]{"ErrorS" + contadorErrorSemantico, operando1, Integer.toString(linea), "Incompatibilidad de tipos, " + (variableAsignacion != null ? variableAsignacion : "")});
                        contadorErrorSemantico++;
                       
                    
                        // se actualiza el valor de la variable de asignacion a "vacio"
                        if (variableAsignacion != null) {
                            identificadoresTipo.put(variableAsignacion, "");
                            agregarOActualizarSimbolo(variableAsignacion, "");
                        }
                    }
                    

                }
                rowsTableSymbol.add(new String[]{lexema, ""}); //agregar los operadores aritmeticos a la tabla 
                return;
            }

            // Verifica si es un operador de asignación
            pattern = Pattern.compile(Asignacion);
            if (pattern.matcher(lexema).matches()) {
                if (!lexemaAgregado) {
                    rowsTableSymbol.add(new String[]{lexema, ""});
                    lexemaAgregado = true;
                }
                return;
            }

            // Verifica si es un separador
            pattern = Pattern.compile(Separadores);
            if (pattern.matcher(lexema).matches()) {
                rowsTableSymbol.add(new String[]{lexema, ""});
                return;
            }

            // Verifica si es una operación relacional
            pattern = Pattern.compile(OperadoresRelacionales);
            if (pattern.matcher(lexema).matches()) {
                rowsTableSymbol.add(new String[]{lexema, ""});
                return;
            }

        }
    }

    // Método para obtener el tipo de un operando, considerando identificadores y valores
    private String obtenerTipoOperando(String operando) {
        if (identificadoresTipo.containsKey(operando)) {
            return identificadoresTipo.get(operando);
        } else {
            return determinarTipoPorValor(operando);
        }
    }

    // Método auxiliar para determinar si dos tipos son compatibles
    private boolean tiposCompatibles(String tipo1, String tipo2) {
        if (tipo1.equals(tipo2)) {
            return true; // Mismo tipo
        }
        // Permitimos operaciones entre enteros y reales
        return (tipo1.equals("Entero") && tipo2.equals("Real")) || (tipo1.equals("Real") && tipo2.equals("Entero"));
    }

    //metodo auxiliar para determinar el valor de los tipos de datos
    private String determinarTipoPorValor(String valor) {
        if (valor.matches("[0-9]+")) {
            return "Entero";
        } else if (valor.matches("[0-9]+\\.[0-9]+")) {
            return "Real";
        } else if (valor.matches("\"[^\"]*\"")) {
            return "Cadena";
        }
        return null;
    }

    //Metodo para verificar que los lexemas ya hayan sido analizados 
    private boolean lexemaYaAnalizado(String lexema) {
        for (String[] row : rowsTableSymbol) {
            if (row[0].equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    //METODO PARA ACTUALIZAR LA TABLA DE SIMBOLO
    private void agregarOActualizarSimbolo(String variable, String tipo) {
    boolean encontrado = false;
    for (String[] row : rowsTableSymbol) {
        if (row[0].equals(variable)) {
            row[1] = tipo; // Actualiza el tipo existente
            encontrado = true;
            break;
        }
    }
    if (!encontrado) {
        rowsTableSymbol.add(new String[]{variable, tipo}); // Agrega si no existe
    }
}
    
  
    
    private void addTable() {//metodo para agregar los lexemas a la tablas
        clearTable();
        for (String[] row : rowsTableSymbol) {
            modelTableSymbol.addRow(row);
        }
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
        }
    }
    
    private void clearTable() {//metodo para limpiar las tablas
        for (int k = modelTableSymbol.getRowCount() - 1; k >= 0; k -= 1) {
            modelTableSymbol.removeRow(k);
        }
        for (int k = modelTableError.getRowCount() - 1; k >= 0; k -= 1) {
            modelTableError.removeRow(k);
        }
    }
}
