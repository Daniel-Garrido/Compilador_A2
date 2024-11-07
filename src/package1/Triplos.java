package package1;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Triplos {

    // Método para realizar los triplos
    public void AnalizarTriplo(String codigo) throws IOException {

        // --------- VARIABLES GENERALES --------
        String[] operadoresRelacionales = {"==", "<", ">", ">=", "<="};
        String[] operadoresAritmeticos = {"+", "-", "*", "/", "%"}; // Ahora como String

        String For = "for", finCicloFor = "else";
        String T = "T"; // Variable temporal
        String jr = "JR"; // Variable para los saltos del for
        String code = codigo; // Para el código

        String tablaTriplo = "linea,Dato Objeto,Dato Fuente,Operador\n";
        String noRelacional = "", expresion = "", intervalo = "";

        boolean dentroFOR = false, ahora = false;
        String simboloActual = "";

        int NUMERO = 1, numeroLinea = 1, primerTrue = 0, finFor = 0, fin = 0, second = 0;
        int lineaCondicionFor = 0;
        String[] partes2 = {};

        // --------- Lógica del programa ------------
        String[] lineas = code.split("\n");

        for (String linea : lineas) { // Recorre línea por línea el código ingresado
            System.out.println("| " + linea + " |");

            //-------------- ENTRA AL CICLO FOR
            if (linea.contains(For)) {
                dentroFOR = true; // Valida que está dentro del ciclo for
                String contenidoFor = linea.replace(For, "").replace("(", "").replace(")", "").replace("{", ""); // Eliminamos los paréntesis y corchetes

                if (contenidoFor.contains(";")) {
                    String[] partesDelFor = contenidoFor.split(";");

                    // 1.- Procesamos la inicialización
                    String inicializacion = partesDelFor[0].trim();
                    System.out.println("inicializacion :" + inicializacion);

                    if (inicializacion.contains("=")) {
                        String[] partesInicializacion = inicializacion.split("=");

                        tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partesInicializacion[1].trim() + "," + "=\n";
                        numeroLinea++;

                        tablaTriplo += numeroLinea + "," + partesInicializacion[0].trim()  + ","+ T + NUMERO + "," + "=\n";
                        numeroLinea++;
                    }

                    // 2.- Procesar la condición
                    String condicion = partesDelFor[1].trim();
                    lineaCondicionFor = numeroLinea;
                    String[] relacion = {};
                    System.out.println("condicion :" + condicion);

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

                        for (String simboloRelacional : operadoresRelacionales) {
                            if (relacion[0].contains(simboloRelacional)) {
                                simboloActual = simboloRelacional;
                                System.out.println("simbolo actual " + simboloActual);

                                String[] partes = relacion[0].split(simboloRelacional);

                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[0].trim() + "," + "=\n";
                                numeroLinea++;

                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[1].trim() + "," + simboloActual + "\n";
                                numeroLinea++;

                                if (condicion.contains("&&")) {
                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
                                    numeroLinea++;
                                }

                                if (condicion.contains("||") || condicion.contains("OR")) {
                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "TRUE" + "," + (numeroLinea + 6) + "\n";
                                    primerTrue = numeroLinea + 2;
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "FALSE" + "," + (numeroLinea + 1) + "\n";
                                    numeroLinea++;
                                }
                            }
                        }

                        for (String simboloRelacional2 : operadoresRelacionales) {
                            if (relacion[1].contains(simboloRelacional2)) {
                                simboloActual = simboloRelacional2;
                                System.out.println("simbolo actual 2 " + simboloActual);

                                partes2 = relacion[1].split(simboloRelacional2);

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

                        //------Procesamos el intervalo
                        intervalo = partesDelFor[2].trim();
                        System.out.println("intervalo " + intervalo);

                    } else {
                        for (String simbolo : operadoresRelacionales) {
                            condicion = condicion.trim();
                            System.out.println("simbolo fUERA DEL OR Y AND:" + simbolo);

                            if (condicion.contains(simbolo)) {
                                simboloActual = simbolo;
                                String[] partes = condicion.split(simbolo);

                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[0].trim() + "," + "=\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[1].trim() + "," + simboloActual + "\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + "R" + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
                                numeroLinea++;
                            }
                        }

                        if (intervalo.contains("=")) {
                            String[] partesIntervalo = intervalo.split("=");
                            expresion = partesIntervalo[1].trim();
                        }
                    }
                }
            } else if (linea.contains("=")) {
                for (String operador : operadoresAritmeticos) {

                    if (linea.contains(operador)) {
                        String[] partido = linea.replace(";", "").trim().split("=");
                        String ladoIzquierdo = partido[0].trim();
                        String ladoDerecho = partido[1].trim();

                        int contadorOperadores = 0;

                        for (String op : operadoresAritmeticos) {
                            if (ladoDerecho.contains(op)) {
                                contadorOperadores++;
                            }
                        }

                        if (contadorOperadores == 2) {
                            List<Integer> posiciones = new ArrayList<>();

                            List<String> operadoresEncontrados = new ArrayList<>();
                            for (String op : operadoresAritmeticos) {
                                int pos = ladoDerecho.indexOf(op);
                                while (pos != -1) {
                                    posiciones.add(pos);
                                    System.out.println("posiciones +" + posiciones);
                                    operadoresEncontrados.add(op);
                                    pos = ladoDerecho.indexOf(op, pos + 1);
                                }
                            }

                            if (posiciones.size() == 2) {
                                int pos1 = posiciones.get(0);
                                int pos2 = posiciones.get(1);
                                String operador1 = operadoresEncontrados.get(0);
                                String operador2 = operadoresEncontrados.get(1);

                                String parte1 = ladoDerecho.substring(0, pos1).trim();
                                String parte2 = ladoDerecho.substring(pos1 + operador1.length(), pos2).trim();
                                String parte3 = ladoDerecho.substring(pos2 + operador2.length()).trim();

                                System.out.println("parte 1: " + parte1);
                                System.out.println("parte 2: " + parte2);
                                System.out.println("parte 3: " + parte3);
                                System.out.println("operador1: " + operador1);
                                System.out.println("operador2: " + operador2);

                                if ((operador1.equals("*") || operador1.equals("/")) && (operador2.equals("+") || operador2.equals("-"))) {
                                    System.out.println("bloque 1");
                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parte1 + "," + "=\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parte2 + "," + operador1 + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + (NUMERO + 1) + "," + parte3 + "," + "=\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + (NUMERO + 1) + "," + T + NUMERO + "," + operador2 + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + ladoIzquierdo + "," + T + (NUMERO + 1) + "," + "=\n";
                                    numeroLinea++;

                                    break;

                                } else if ((operador2.equals("*") || operador2.equals("/")) && (operador1.equals("+") || operador1.equals("-"))) {
                                    System.out.println("bloque 2");
                                    
                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parte2 + "," + "=\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parte3 + "," + operador2 + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + (NUMERO+1) + "," + parte1 + "," + "=\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + (NUMERO + 1) + "," + T + NUMERO + "," + operador1 + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + ladoIzquierdo + "," + T + (NUMERO + 1) + "," + "=\n";
                                    numeroLinea++;

                                    break;
                                } else {
                                    System.out.println("bloque 3");
                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parte1 + "," + "=\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parte2 + "," + operador1 + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + (NUMERO + 1) + "," + parte3 + "," + "=\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + (NUMERO + 1) + "," + T + NUMERO + "," + operador2 + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + ladoIzquierdo + "," + T + (NUMERO + 1) + "," + "=\n";
                                    numeroLinea++;
                                    break;
                                }
                            }

                        } else if (contadorOperadores == 1) {

                            for (String operadorUnico : operadoresAritmeticos) {
                                if (ladoDerecho.contains(operadorUnico)) {
                                    String[] parts = ladoDerecho.split("\\" + operadorUnico);

                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parts[0].trim() + "," + "=\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + parts[1].trim() + "," + operadorUnico + "\n";
                                    numeroLinea++;

                                    tablaTriplo += numeroLinea + "," + ladoIzquierdo + "," + T + NUMERO + "," + "=\n";
                                    numeroLinea++;
                                    break;
                                }
                            }
                        }
                    }
                }
                
                //------ Triplo para las variables de asignacion --------
                linea = linea.trim();
                if (!linea.contains("+") && !linea.contains("-") && !linea.contains("/") && !linea.contains("%") && !linea.contains("*")) {
                    String[] par = linea.replace(";", "").split("=");

                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + par[1] + "," + "=\n";
                    numeroLinea++;

                    tablaTriplo += numeroLinea + "," + par[0] + "," + T + NUMERO + "," + "=\n";
                    numeroLinea++;
                }

                
                
            } 
            //----buscar el fin del ciclo for---------- 
            else if (linea.contains("}")) {
                
                 //------Procesamos el intervalo al final de cada iteración antes del cierre `}`
                if (!intervalo.isEmpty()) {
                    
                    if (intervalo.contains("=")) {
                        String[] partesIntervalo = intervalo.split("=");
                        String variable = partesIntervalo[0].trim();
                        expresion = partesIntervalo[1].trim();

                        // Generamos el triplo del incremento del ciclo for
                        for (String OPA : operadoresAritmeticos) {
                            if (expresion.contains(OPA)) {
                                String[] operandoPartes = expresion.split("\\" + OPA);
                                tablaTriplo += numeroLinea + "," + T + 1 + "," + operandoPartes[0].trim() + "," + "=\n";
                                numeroLinea++;
                                
                                tablaTriplo += numeroLinea + "," + T + 1 + "," + operandoPartes[1].trim() + "," + OPA + "\n";
                                numeroLinea++;
                                
                                tablaTriplo += numeroLinea + "," + variable + "," + T + 1 + "," + "=\n";
                                numeroLinea++;
                                break;
                            }
                        }
                    }
                }
                
                //---Agregar un salto de linea al termino del ciclo for------- 
                tablaTriplo += numeroLinea + ",,"+lineaCondicionFor+","+jr+"\n";
                if (dentroFOR) {
                    finFor = numeroLinea + 1;
                    if (code.contains(finCicloFor)) {
                        tablaTriplo += numeroLinea + ",,FINELSE,JR\n";
                    }
                    
                    numeroLinea++;
                    dentroFOR = false;
                    ahora = true;
                } else {
                    fin = numeroLinea;
                }
            }
        }

        tablaTriplo = tablaTriplo.replace("REEMPLAZAR", "" + finFor).replace("SEGUNDO", "" + second).replace("PRIMTRU", "" + primerTrue);

        if (fin < 1) {
        } else {
            tablaTriplo = tablaTriplo.replace("FINELSE", "" + fin);
        }

        //--------Almacenamos el triplo en un archivo csv 
        Exportar imp = new Exportar();
        imp.Imprimir("./Triplos.csv", tablaTriplo);

        InterfazTriplos mp = new InterfazTriplos();
        mp.mostrarArch("./Triplos.csv");
    }
}
