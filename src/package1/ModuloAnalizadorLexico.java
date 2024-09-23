package package1;

//importamos las clases a usar
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

public class ModuloAnalizadorLexico {
    private final DefaultTableModel modelTableToken; //almacena la tabla de simbolos
    private final DefaultTableModel modelTableError; //almacena los errores semanticos
    
    //Arreglos
    private final ArrayList<String[]> rowsTableToken; // Lista de arreglos para la tabla de simbolos
    private final ArrayList<String[]> rowsTableError; // Lsita de arreglos para la tabla de errores
   
    private HashMap<String, String> TablaSimbolos = new HashMap<>();
    private HashMap<String, String> valoresAsignados = new HashMap<>();
    private String ultimoIdentificador =""; //almacena el ultimo identificador actual 
    private int contadorErroresSemanticos;//variable para contar los errores 
    private String tipodeDato;//indentifica el tipo de dato (Entero Real Cadena)
    
    // constructor
    public ModuloAnalizadorLexico(DefaultTableModel modelTableToken,DefaultTableModel modelTableError){
       this.modelTableToken = modelTableToken;
       this.modelTableError = modelTableError;
       rowsTableToken = new ArrayList<>();
       rowsTableError = new ArrayList<>();
       contadorErroresSemanticos = 1; //los errores semanticos se iniciara en 1
    }
    
    //--------------creacion de nuestros metodos---------------------------
    
    //metodo para encontrar espacios y saltos de linea
    public void analizarExpresiones(String expresiones, int linea){
       String[] Expresiones = expresiones.split("\n");//dividir en lineas
       linea =0;//inicializar en cero
       
       //recorrrer cada linea de Expresiones
       for(String expresion : Expresiones){
           linea +=1;
           String expresion1 = expresion.replace(";", " ; ").replace(",", " , ");// se agrega espacios 
       
           //divir la expresion en token usando delimitadores los espacioes en blanco
           // [EQ11, =, 4.5, ;]
           String[] tokens = expresion1.split("\\s+"); //uno o mas espacios en blanco
           tipodeDato = "";//restablecer el tipo de dato
           
           //procesar cada lexema dentro de la expresion
           for(String lexema : tokens){
               //verificar que token no este vacio
               if(!lexema.trim().isEmpty()){//elimna los espacios en linea y verifica si esta vacio
                   analizarLexema(lexema,linea);//mandamos a la siguiente funcion
               }
           }
       }
       agregarATabla(); // se agrega al metodo 
    } 
    
    //metodo para analizar los lexemas 
    // Método para analizar lexemas
    private void analizarLexema(String lexema, int linea) {
    if (!lexemaYaAnalizado(lexema)) {

        /* TIPOS DE DATOS */
        String TD = "(Entero|Real|Cadena)";
        Pattern pattern = Pattern.compile(TD);

        // Si el lexema coincide con un tipo de dato (Entero, Real, Cadena)
        if (pattern.matcher(lexema).matches()) {
            rowsTableToken.add(new String[]{lexema, ""});
            tipodeDato = lexema;  // Guarda el tipo de dato actual
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
            rowsTableToken.add(new String[]{lexema, ""});  // Agregar el signo de asignación a la tabla de tokens
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
            rowsTableToken.add(new String[]{lexema, "Real"});  // Agrega el número real a la tabla de tokens
            valoresAsignados.put(ultimoIdentificador, lexema);  // Asigna el valor al identificador
            actualizarTipoIdentificador(ultimoIdentificador, "Real");
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

        // Si el lexema es un operador aritmético
        if (pattern.matcher(lexema).matches()) {
            verificarCompatibilidadTipos(linea, lexema);  // Verificar la compatibilidad de tipos en la operación
            rowsTableToken.add(new String[]{lexema, ""});  // Agregar el operador a la tabla de tokens
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

    
    //metodo para actualizar el valor de los identificadores
    private void  actualizarTipoIdentificador(String identificador, String tipoDato){
    // Recorre la tabla de simbolos para encontrar el identificador y actualizar su tipo de dato
    for (int i = 0; i < rowsTableToken.size(); i++) {
        String[] row = rowsTableToken.get(i);
        if (row[0].equals(identificador)) {
            row[1] = tipoDato;  // Asigna el tipo de dato al identificador
            rowsTableToken.set(i, row);  // Actualiza la tabla de tokens
            break;
        }
    }
    
    }
    
    
    private String obtenerTipoDato(String identificador) {
    // Recorre la tabla de símbolos (rowsTableToken) para encontrar el identificador
    for (String[] row : rowsTableToken) {
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
    for (int i = rowsTableToken.size() - 1; i >= 0; i--) {
        String[] token = rowsTableToken.get(i);
        String lexema = token[0];

        // Buscar el primer operando (derecho)
        if (tipoDerecho == null && lexema.matches("EQ11[0-9]+")) {
            identificadorDerecho = lexema;
            tipoDerecho = obtenerTipoDato(lexema);
            System.out.println("identificador Derecho  :"+identificadorIzquierdo);
            if (tipoDerecho == null) {  // Verificar si no tiene tipo de dato
            }
        }

        // Buscar el segundo operando (izquierdo)
        else if (tipoIzquierdo == null && lexema.matches("EQ11[0-9]+")) {
        
            identificadorIzquierdo = lexema;
            tipoIzquierdo = obtenerTipoDato(lexema);
            System.out.println("identificador Izquierdo :"+identificadorIzquierdo);
        }

        // Si ya tenemos ambos operandos, salimos del ciclo
        if (tipoIzquierdo != null && tipoDerecho != null) {
            break;
        }
    }

    // Verificar si ambos operandos tienen tipo de dato asignado
    if (tipoIzquierdo != null && tipoDerecho != null) {
        if (!tipoIzquierdo.equals(tipoDerecho)) {
            System.out.println("tipo de dato de lado izquierdo :"+tipoIzquierdo);
            System.out.println("tipo de dato de lado derecho :"+tipoDerecho);
            
            // Si los tipos son diferentes, registrar error de incompatibilidad de tipos
            System.out.println("Error: Incompatibilidad de tipos: " + identificadorIzquierdo + " en la línea :" + linea + " :");
            rowsTableError.add(new String[]{"ErrorSEM"+contadorErroresSemanticos, identificadorIzquierdo, Integer.toString(linea), "Incompatibilidad de tipos:"   });
            contadorErroresSemanticos++;
        }
    }
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
    
    /*Metodo para llenar las tablas*/
    private void agregarATabla(){
       
        //prbar  el codigo en la consola
        //String birote="Lexema,Tipo de Dato\n", pivote = "Token,Lexema,Linea,Descripcion\n";
        
        clearTable();
        
        
        for (String[] row : rowsTableToken) {
            modelTableToken.addRow(row);
        }
        
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
        }
        
        /*
        for (String[] row : rowsTableToken) {
           modelTableToken.addRow(row);
           
           if(row[1]!=null) {
        	   row[0]=row[0].trim();
        	   row[1]=row[1].trim();
        	   if(row[0].contains(",")) {row[0]=row[0].replace(",", "\",\"");} else if (row[1].contains(",")) { row[1]=row[1].replace(",", "\",\""); }
        	   birote+=row[0]+","+row[1]+"\n";
           } else {
        	   row[0]=row[0].trim();
        	   if(row[0].contains(",")) {row[0]=row[0].replace(",", "\",\"");}
        	   birote+=row[0]+"\n";
           }      
        }
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
            for (String linea : row) {
            	if(linea.contains("Error Lexico")) {
            	pivote+=linea+"\n";
            	} else {
    			pivote+=linea+",";
    			}
    		}
        }*/
         //System.out.println(birote);
         //System.out.println(pivote);
    }

    //metodo para eliminar los datos ingresados a las tablas
    private void clearTable() {
        //recorre las tablas y elimina las filas de final a principio
         for (int k = modelTableToken.getRowCount() - 1; k >= 0; k -= 1) {
           modelTableToken.removeRow(k);
        }
        
        for (int k = modelTableError.getRowCount() - 1; k >= 0; k -= 1) {
            modelTableError.removeRow(k);
        }
    }
}

