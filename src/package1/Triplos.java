package package1;
import java.io.IOException;
public class Triplos  {

    //metodo para realizar los triplos 
    public void AnalizarTriplo(String codigo) throws IOException {
        
        //---------VARIABLES GENERALES--------
        String[] operadoresRelacionales = {"==", "<", ">", ">=", "<="};
        char[] operadoresAritmeticos = {'+', '-', '/', '*', '%'};

        String For = "for", finCicloFor = "else";
        String T = "T"; // variable temporal 
        String jr = "JR"; // variable para los saltos del for 
        String code = codigo; // Para el CODIGO

        String tablaTriplo = "linea ,Dato Objeto,Dato Fuente,Operador\n";
        String noRelacional = "", expresion = "", intervalo = "";

        boolean dentroFOR = false, ahora = false;
        String simboloActual = "";

        int NUMERO = 1, numeroLinea = 1, primerTrue = 0, finFor = 0, fin = 0, second = 0;
        String[] partes2 = {};

        //---------logica del programa------------
        String[] lineas = code.split("\n");

        for (String linea : lineas) { // Recorre linea por linea el codigo ingresado
            System.out.println("| " + linea + " |");

            //-------------- ENTRA AL CICLO FOR 
            if (linea.contains(For)) {
                dentroFOR = true;//valida que esta dentro del ciclo for 
                String contenidoFor = linea.replace(For, "").replace("(", "").replace(")", "").replace("{", "");//eliminamos los parentesis y corchetes

                // Si existe una inicialización (parte antes del primer ';')
                if (contenidoFor.contains(";")) {
                    String[] partesDelFor = contenidoFor.split(";");

                    //------- 1.- Procesamos la inicialización
                    String inicializacion = partesDelFor[0].trim();
                    System.out.println("inicializacion :" + inicializacion);

                    //si existe un operador de "="
                    if (inicializacion.contains("=")) {
                        String[] partesInicializacion = inicializacion.split("=");

                        tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partesInicializacion[0].trim() + "," + "=\n";
                        numeroLinea++;

                        tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partesInicializacion[1].trim() + "," + "=\n";
                        numeroLinea++;
                    }

                    //-------- 2.- Procesar la condición
                    String condicion = partesDelFor[1].trim();

                    String[] relacion = {};
                    System.out.println(" condicion :" + condicion);

                    //verificar si la operacion contiene operadores logicos
                    if (condicion.contains("&&") || condicion.contains("||") || condicion.contains("OR")) {

                        if (condicion.contains("&&")) {
                            relacion = condicion.split("&&");

                        }
                        if (condicion.contains("||")) {
                            relacion = condicion.split("||");

                        }
                        if (condicion.contains("OR")) {
                            relacion = condicion.split("OR");

                        }

                        //-------- PRIMER OPERADOR RELACIONAL ---------------------------
                        for (String simboloRelacional : operadoresRelacionales) {

                            if (relacion[0].contains(simboloRelacional)) {
                                simboloActual = simboloRelacional;
                                System.out.println("simbolo actual " + simboloActual);

                                // se genera el triplo de la primera parte de la condicion 
                                String[] partes = relacion[0].split(simboloRelacional); //divide donde encuentre el primer simbolo relacional {<, <= , >, >=, ==}

                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[0].trim() + "," + "=\n";
                                numeroLinea++;

                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[1].trim() + "," + simboloActual + "\n";
                                numeroLinea++;

                                // verificacioms si contiene un operador Logico AND
                                if (condicion.contains("&&")) {

                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
                                    numeroLinea++;
                                }

                                //verificamos si la condicion contiene un operador logico OR 
                                if (condicion.contains("||") || condicion.contains("OR")) {

                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "TRUE" + "," + (numeroLinea + 6) + "\n";
                                    primerTrue = numeroLinea + 2;
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "FALSE" + "," + (numeroLinea + 1) + "\n";
                                    numeroLinea++;
                                }
                            }
                        }

                        //------- SEGUNDO OPERADOR RELACIONAL -----------------------
                        for (String simboloRelacional2 : operadoresRelacionales) {
                            if (relacion[1].contains(simboloRelacional2)) {
                                simboloActual = simboloRelacional2;
                                System.out.println("simbolo actual 2 " + simboloActual);

                                partes2 = relacion[1].split(simboloRelacional2);

                                //Verificamos si existe un operador logico OR
                                if (condicion.trim().contains("||") || condicion.trim().contains("OR")) {

                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes2[0].trim() + "," + "=\n";
                                    numeroLinea++;
                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes2[1].trim() + "," + simboloActual + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                    numeroLinea++;
                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
                                    numeroLinea++;
                                }

                                //verificamos si existe un operador logico AND
                                if (condicion.contains("&&")) {

                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes2[0].trim() + "," + "=\n";
                                    numeroLinea++;
                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes2[1].trim() + "," + simboloActual + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                    numeroLinea++;
                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
                                    numeroLinea++;
                                }
                            }
                        }

                        //capturar el intervalo x++
                        intervalo = partesDelFor[2].trim();
                        System.out.println("intervalo " + intervalo);

                    } else {

                        // ---- SI NO HAY OPERADORES OR Y AND se realiza este    
                        for (String simbolo : operadoresRelacionales) {
                            condicion = condicion.trim();
                            System.out.println("simbolo fUERA DEL OR Y AND:" + simbolo);

                            if (condicion.contains(simbolo)) {

                                simboloActual = simbolo;
                                String[] partes = condicion.split(simbolo); // divide donde encuentre un simobolo relacional

                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[0].trim() + "," + "=\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[1].trim() + "," + simboloActual + "\n";
                                numeroLinea++;
                                //---------------------------------------------------------------------------------------------
                                tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
                                numeroLinea++;
                            }
                        }

                        if (intervalo.contains("=")) {

                            // Divide la expresión en torno al signo '='
                            String[] partesIntervalo = intervalo.split("=");
                            expresion = partesIntervalo[1].trim();

                        }

                    }

                }

            } //----------- INSTRUCCION PARA REALIZAR TRIPLOS DE OPERACIONES ARITMETICAS ------
            else if (linea.contains("=")) {
                for (char OPA : operadoresAritmeticos) {

                    if (linea.contains("" + OPA)) {

                        String[] partido = linea.replace(";", "").trim().split("=");
                        String[] parts = partido[1].trim().split("\\" + OPA);

                        tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parts[0] + "," + "=\n";
                        numeroLinea++;

                        tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parts[1] + "," + OPA + "\n";
                        numeroLinea++;

                        tablaTriplo += numeroLinea + "," + partido[0] + "," + T + NUMERO + "," + "=\n";
                        numeroLinea++;
                    }
                }

                linea = linea.trim();

                //------ INSTRUCCION PARA REALIZAAR OPERACIONES DE ASIGNACION --------------------------
                if (!linea.contains("+") && !linea.contains("-") && !linea.contains("/") && !linea.contains("%") && !linea.contains("*")) {
                    String[] par = linea.replace(";", "").split("=");

                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + par[1] + "," + "=\n";
                    numeroLinea++;

                    tablaTriplo += numeroLinea + "," + par[0] + "," + T + NUMERO + "," + "=\n";
                    numeroLinea++;
                }

                //------ INSTRUCCION PARA REALIZAAR LA OPERACION DEL INTERVALO --------------------------
                String incremento = intervalo;
                if (incremento.contains("=")) {

                    // Divide la expresión en torno al signo '='
                    String[] partesIntervalo = intervalo.split("=");
                    intervalo = partesIntervalo[1].trim();

                    // Busca un operador aritmético dentro de la expresión
                    for (char OPA : operadoresAritmeticos) {

                        if (intervalo.contains("" + OPA)) {

                            String[] operandoPartes = intervalo.split("\\" + OPA);

                            tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partesIntervalo[0] + "," + "=\n";
                            numeroLinea++;

                            tablaTriplo += numeroLinea + "," + T + NUMERO + "," + operandoPartes[1] + "," + OPA + "\n";
                            numeroLinea++;

                            tablaTriplo += numeroLinea + "," + partesIntervalo[0] + "," + T + NUMERO + "," + "=\n";
                            numeroLinea++;

                        }
                    }
                }

            } 

            //----- FUERA DEL FOR ------------------------
            else if (linea.contains("}")) {

                // SE DA UN SALTO CUANDO EL CICLO FOR ALLA FINALIZADO
                tablaTriplo += numeroLinea + "," + "," + "," + jr + "\n";
               

                // Verificar dentro del for
                if (dentroFOR) {
                    finFor = numeroLinea + 1;
                    if (code.contains(finCicloFor)) {
                        tablaTriplo += numeroLinea + "," + "," + "FINELSE" + "," + jr + "\n";
                    }
                    numeroLinea++;
                    dentroFOR = false;
                    ahora = true;
                } else {
                    fin = numeroLinea;
                }
            }
        }

        //Reemplazamos los valores por los correctos 
        tablaTriplo = tablaTriplo.replace("REEMPLAZAR", "" + finFor).replace("SEGUNDO", "" + second).replace("PRIMTRU", "" + primerTrue);

        if (fin < 1) {
        } else {
            tablaTriplo = tablaTriplo.replace("FINELSE", "" + fin);
        }

        //-------Agregar los triplos al archivo csv -----------------------
        Exportar imp = new Exportar();
        imp.Imprimir("./Triplos.csv", tablaTriplo);

        InterfazTriplos mp = new InterfazTriplos();
        mp.mostrarArch("./Triplos.csv");
    }
}
