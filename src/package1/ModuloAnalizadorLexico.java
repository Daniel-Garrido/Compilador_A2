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
    private HashMap<String, String> ValoresAsignados = new HashMap<>();
    private String UltimoIdentificador =""; //almacena el ultimo identificador actual 
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
                   analizarLexemas(lexema,linea);//mandamos a la siguiente funcion
               }
           }
       }
       agregarATabla(); // se agrega al metodo 
    } 
    
    //metodo para analizar los lexemas 
    private void analizarLexemas(String lexema, int linea){
        //si no se ha analizado ejecuta esto
        if(!LexemaAnalizado(lexema)){
            // tipos de datos
            String TiposdeDatos =("(Entero|Real|cadena)");//definir expresion regular
            Pattern pattern = Pattern.compile(TiposdeDatos);//compilar la expresion regular
            
            //verificar si coincide con la expresión regular
            if(pattern.matcher(lexema).matches()){
            rowsTableToken.add(new String[]{lexema,""});
            tipodeDato = lexema;// se le asigna un tipo de dato actual 
            return;
            }
            
            //identificadores
            String Identificadores = ("EQ11[0-9]+");//definir expresion regular
            pattern = Pattern.compile(Identificadores);//compilar la expresion regular
            
            //verificar que el lexema cumpla con un tipo de dato valido
            if(pattern.matcher(lexema).matches()){
                UltimoIdentificador = lexema;
                rowsTableToken.add(new String[]{lexema, ""});
                return;
            }
            
           //Verificar si una variable cumple Con tener la asignacion de un valor 
            /* if(pattern.matcher(lexema).matches()){
               
               if(tipodeDato.isEmpty()){
                   //agregar a la tabla de errores
                   rowsTableError.add(new String[] {"ErrSem" + contadorErroresSemanticos, lexema, Integer.toString(linea),"Variable indefinida"});
                   contadorErroresSemanticos ++; // se incrementa el valor del contador 
               }
               rowsTableToken.add(new String[]{lexema, tipodeDato}); //agregar a la tabla de errores a la columna token
               return;
           }*/
          
             //operadores de asignacion
            String OperacionesAsignacion = ("=");//definir expresion regular
            pattern = Pattern.compile(OperacionesAsignacion); //compilar la expresion regular
            
              if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, ""});// se agrega a la tabla de simbolo
                return;
            }
              
           //Numeros enteros
           String NumerosEnteros = ("[0-9]+"); //expresion regular para nuestros numeros enteros
           pattern = Pattern.compile(NumerosEnteros); // compilar expresion regular
           
           //verificamos si es un numero entero
           if(pattern.matcher(lexema).matches()){
               rowsTableToken.add(new String[] {lexema, "Entero"});
               ValoresAsignados.put(UltimoIdentificador, lexema);
               actualizarTipoIdentificador(UltimoIdentificador, "Entero");
               return;
           }
           
           //Numeros reales
           String NumerosReales = ("[0-9]+[.][0-9]+");//expresion regular para numeros reales
           pattern = Pattern.compile(NumerosReales);//compilar expresion regular
           
           //verificar si es un numero real 
           if(pattern.matcher(lexema).matches()){ 
               rowsTableToken.add(new String[] {lexema, "Real"});
                ValoresAsignados.put(UltimoIdentificador, lexema);
                actualizarTipoIdentificador(UltimoIdentificador, "Real");
               return;
           }
           
           //Cadenas
           String Cadenas =("[\"A-ZUa-zU0-9]+\""); //expresion regular para las cadenas de texto
           pattern = Pattern.compile(Cadenas);//compilar expresion regular
           
           //verificamos si es una cadena 
            if(pattern.matcher(lexema).matches()){
               rowsTableToken.add(new String[] {lexema, "Cadena"});//
                ValoresAsignados.put(UltimoIdentificador, lexema);//asigna el valor al identificador
                actualizarTipoIdentificador(UltimoIdentificador, "Cadena");
               return;
           }
            
            //operadores aritmeticos
            String OperadoresAritmeticos = ("[+][-][*][/][%]");
            pattern = Pattern.compile(OperadoresAritmeticos);//compilar los operadores
            
            // verificamos si el lexema es un OPA
               if (pattern.matcher(lexema).matches()) {
                // Verificamos si los identificadores que participan en la operación tienen valores asignados
                if (!ValoresAsignados.containsKey(UltimoIdentificador)) {
                    // Si el identificador no tiene valor asignado, es una variable indefinida
                    if (!errorVariableIndefinidaAgregado(UltimoIdentificador)) {  // Verificar si el error ya fue agregado
                        System.out.println("Variable indefinida: " + "Error Semantico :" + contadorErroresSemanticos + " " + UltimoIdentificador + " en la línea: " + linea);//probamos en consola
                        agregarErrorVariableIndefinida(UltimoIdentificador, linea);  // Agregar a la tabla de errores
                        contadorErroresSemanticos++; // se incrementa 
                    }
                }
                
                rowsTableToken.add(new String[]{lexema, ""});
            return;
        }

              //separadores
              String Separadores = ("[,;]");//expresion regular
              pattern  = Pattern.compile(Separadores);//compila la expresion regular
            
               if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, ""});//se agrega a la tabla de simbolos
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
    
    //metodo para enviar el error variable indefinida a la tabla de errores
    private void agregarErrorVariableIndefinida(String lexema, int linea){
         // se agrega a la tabla errores  (TOKEN-- Lexema-- Liena-- Descripcion)                                
         rowsTableError.add(new String[]{"ErrorSEM" +contadorErroresSemanticos , lexema, Integer.toString(linea), "Variable indefinida"});
         contadorErroresSemanticos ++;
    }
    
    //metodo para verificar si un errpr de variable indefinida ya ha sido agregado
    private boolean errorVariableIndefinidaAgregado(String lexema){
     for (String[] row : rowsTableError) {
        if (row[1].equals(lexema) && row[3].equals("Variable indefinida")) {
            return true;  // El error ya fue agregado
        }
    }
    return false;  // El error no ha sido agregado
        
    }
    
    //verifica que los lexemas ya han sido analizado y no se tengan que volver a analizar
    private boolean LexemaAnalizado(String lexema){     
       for (String[] row : rowsTableToken) {
           if (row[0].equals(lexema)) {
               return true;
           }
       }
       return false;
    }
    
    private void agregarATabla(){
       
        //prbar  el codigo en la consola
        String birote="Lexema,Tipo de Dato\n", pivote = "Token,Lexema,Linea,Descripcion\n";
        
        clearTable();
        
        for (String[] row : rowsTableToken) {
            modelTableToken.addRow(row);
        }
        
        for (String[] row : rowsTableError) {
            modelTableError.addRow(row);
        }
        
        
        /*for (String[] row : rowsTableToken) {
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
         System.out.println(birote);
         System.out.println(pivote);
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

