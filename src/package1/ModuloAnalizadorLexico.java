package package1;
//importamos las clases a usar
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

public class ModuloAnalizadorLexico {
   
    private final DefaultTableModel modelTableToken; //almacena los datos de la tabla de tokens
    private final DefaultTableModel modelTableError; //almacena los errores semanticos
    
    //matrices
    private final ArrayList<String[]> rowsTableToken; // Lista de matrices para la tabla de tokens
    private final ArrayList<String[]> rowsTableError; // Lsita de matrices para la tabla de errores
    
    private ArrayList<String[]> rowsError;
    private ArrayList<String[]> tipos;
   
    private int contadorErroresSemanticos;//variable para contar los erres 
    private String tipodeDato;//indentifica el tipo de dato (Entero Decimal Cadena)
    
    // constructor
    public ModuloAnalizadorLexico(DefaultTableModel modelTableToken,DefaultTableModel modelTableError){
       this.modelTableToken = modelTableToken;
       this.modelTableError = modelTableError;
       
       rowsTableToken = new ArrayList<>();
       rowsTableError = new ArrayList<>();
       
       this.rowsError = new ArrayList<>();
       this.tipos = new ArrayList<>();
       
       contadorErroresSemanticos = 1; //los errores semanticos se iniciara en 1
    }
    
    //-----------creacion de nuestros metodos---------------------------
    
    //metodo para encontrar espacios y saltos de linea
    public void analizarExpresiones(String expresiones, int linea){
       String[] Expresiones = expresiones.split("\n");//dividir en lineas
       linea =0;//inicializar en cero
       
       //recorrrer cada linea de Expresiones
       for(String expresion : Expresiones){
           linea +=1;
           String expresion1 = expresion.replace(";", " ; ").replace(",", " ; ");//reemplazamos 
       
           //divir la expresion en token usando delimitadores los espacioes en blanco
           String[] tokens = expresion1.split("\\s+"); //uno o mas espacios en blanco
           tipodeDato = "";
           
           for(String lexema : tokens){
               //verificar que token no este vacio
               if(!lexema.trim().isEmpty()){
                   analizarLexemas(lexema,linea);//mandamos a la siguiente funcion
               }
           }
       }
       agregarATabla(); // se agrega al metodo
    }
    
    //metodo para analizar los lexemas 
    private void analizarLexemas(String lexema, int linea){
        //
        if(!LexemaAnalizado(lexema)){
            // tipos de datos
            String TiposdeDatos =("(Entero|Real|cadena)");//definir expresion regular
            Pattern pattern = Pattern.compile(TiposdeDatos);//compilar la expresion regular
            
            //verificar si coincide con la expresión regular
            if(pattern.matcher(lexema).matches()){
            rowsTableToken.add(new String[]{lexema, ""});
            tipodeDato = lexema;
            return;
            }
            
            //identificadores
            String Identificadores = ("EQ11[0-9]+");//definir expresion regular
            pattern = Pattern.compile(Identificadores);//compilar la expresion regular
            
           //
           if(pattern.matcher(lexema).matches()){
               if(tipodeDato.isEmpty()){
                   //agregar a la tabla de errores
                   rowsTableError.add(new String[] {"ErrSem" + contadorErroresSemanticos, lexema, Integer.toString(linea),"Variable indefinida"});
                   contadorErroresSemanticos ++; // se incrementa el valor del contador 
               }
               rowsTableToken.add(new String[]{lexema, tipodeDato}); //agregar a la tabla de errores a la columna token
               return;
           }
           
           //Numeros enteros
           String NumerosEnteros = ("[0-9]+"); //expresion regular para nuestros numeros enteros
           pattern = Pattern.compile(NumerosEnteros); // compilar expresion regular
           
           //
           if(pattern.matcher(lexema).matches()){
               rowsTableToken.add(new String[] {lexema, "Entero"});
               return;
           }
           
           //Numeros reales
           String NumerosReales = ("[0-9]+[.][0-9]+");//expresion regular para numeros reales
           pattern = Pattern.compile(NumerosReales);//compilar expresion regular
           
           //
           if(pattern.matcher(lexema).matches()){
               rowsTableToken.add(new String[] {lexema, "Real"});
               return;
           }
           
           //Cadenas
           String Cadenas =("[\"A-ZUa-zU0-9]+\""); //expresion regular para las cadenas de texto
           pattern = Pattern.compile(Cadenas);//compilar expresion regular
           
           //
            if(pattern.matcher(lexema).matches()){
               rowsTableToken.add(new String[] {lexema, "Cadena"});
               return;
           }
            
            //operadores aritmeticos
            String OperadoresAritmeticos = ("[+][-][*][/][%]");
            pattern = Pattern.compile(OperadoresAritmeticos);
            
            if(pattern.matcher(lexema).matches()){
               rowsTableToken.add(new String[] {lexema, ""});
               return;
           }
            
            //operadores de asignacion
            String OperacionesAsignacion = ("=");
            pattern = Pattern.compile(OperacionesAsignacion);
            
              if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, ""});
                return;
            }
              
              //separadores
              String Separadores = ("[,;]");
              pattern  = Pattern.compile(Separadores);
            
               if (pattern.matcher(lexema).matches()) {
                rowsTableToken.add(new String[]{lexema, ""});
                return;
            }
           
        }
    }
    
   
    private boolean LexemaAnalizado(String lexema){     
       for (String[] row : rowsTableToken) {
           if (row[0].equals(lexema)) {
               return true;
           }
       }
       return false;
    }
    
    public void setTipo(String[] tipo){
        tipos.add(tipo); /* PERMITE AGREGAR LOS TIPOS DE DATOS ENCONTRADOS EN EL CODIGO */
    }
    public void analizarTipos(int linea){
         if (this.tipos.size() >= 1 && ((String[])this.tipos.get(0)).length > 1) {
            String tipo = tipos.get(0)[1];
            
            for (int k = 1; k < tipos.size(); k += 1) {
                if (!tipo.equals(tipos.get(k)[1])) {
                    rowsError.add(new String[]{"ErrSem", tipos.get(k)[0], Integer.toString(linea), "Incompatibilidad de Tipos, " + tipo});
                    contadorErroresSemanticos ++;
                }
            }
        }
    }
    
    public void clearData() {
        tipos.clear();/* PERMITE BORRAR TODOS LOS ELEMENTOS DE LA LISTA DE LA TABLA DE ERRORES */
    }
    
    private void agregarATabla(){
       
        //prograr el codigo en la consola
        String birote="Lexema,Tipo de Dato\n", pivote = "Token,Lexema,Linea,Descripcion\n";
        clearTable();
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
        }
         System.out.println(birote);
         System.out.println(pivote);
    }

    //metodo para eliminar los datos ingresados a las tablas
    private void clearTable() {
         for (int k = modelTableToken.getRowCount() - 1; k >= 0; k -= 1) {
           modelTableToken.removeRow(k);
        }
        
        for (int k = modelTableError.getRowCount() - 1; k >= 0; k -= 1) {
            modelTableError.removeRow(k);
        }
    }
}
