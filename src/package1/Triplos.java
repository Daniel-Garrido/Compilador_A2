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
        int NumeroTemporal;
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

                        tablaTriplo += numeroLinea + "," + partesInicializacion[0].trim() + "," + T + NUMERO + "," + "=\n";
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
            } 

            //-------- PROCESAR  Triplos para las operaciones aritmeticas ------ 
            
            else if (linea.contains("=")) {

                NumeroTemporal = 1;
                String[] partido = linea.replace(";", "").trim().split("=");
                String ladoIzquierdo = partido[0].trim();
                String ladoDerecho = partido[1].trim();

                // Verificar si el lado derecho es una asignación simple (sin operadores)
                if (ladoDerecho.matches("\\d+\\.?\\d*|[a-zA-Z][a-zA-Z0-9_]*")) {
                    // Generar triplo para asignación simple
                    tablaTriplo +=numeroLinea+ "," +T + NumeroTemporal + "," + ladoDerecho + ","+ "=\n";
                    numeroLinea++;

                    tablaTriplo +=numeroLinea +","+ ladoIzquierdo + ",T" + NumeroTemporal + "," +"=\n";
                    numeroLinea++;
  
                } 
                
                else {
                    // Procesamiento habitual para expresiones complejas
                    List<String> tokens = new ArrayList<>();
                    List<String> operadores = new ArrayList<>();
                    StringBuilder sb = new StringBuilder();

                    for (char c : ladoDerecho.toCharArray()) {
                        if (Character.isDigit(c) || Character.isLetter(c) || c == '.') {
                            sb.append(c);
                        } else if ("+-*/%".indexOf(c) >= 0) {
                            tokens.add(sb.toString());
                            operadores.add(String.valueOf(c));
                            sb.setLength(0);
                        }
                    }
                    tokens.add(sb.toString());

                    while (!operadores.isEmpty()) {
                        int idx = operadores.indexOf("*");
                        if (idx == -1) {
                            idx = operadores.indexOf("/");
                        }
                        if (idx == -1) {
                            idx = operadores.indexOf("+");
                        }
                        if (idx == -1) {
                            idx = operadores.indexOf("-");
                        }

                        String op1 = tokens.remove(idx);
                        String op2 = tokens.remove(idx);
                        String operador = operadores.remove(idx);

                        tablaTriplo += numeroLinea + "," + "T" + NumeroTemporal + "," + op1 + "," + "=\n";
                       

                        tablaTriplo += numeroLinea + "," + "T" + NumeroTemporal + "," + op2 + "," + operador + "\n";
                        tokens.add(idx, "T" + NumeroTemporal);

                        NumeroTemporal++;
                        numeroLinea++;
                    }

                    tablaTriplo += numeroLinea + "," + ladoIzquierdo + "," + "T" + (NumeroTemporal - 1) + "," + "=\n";
                    numeroLinea++;
                }
            }
            
            //---- Buscar el fin del ciclo for---------- 
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

                            }
                        }
                    }
                }

                //---Agregar un salto de linea al termino del ciclo for------- 
                tablaTriplo += numeroLinea + ",," + lineaCondicionFor + "," + jr + "\n";
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
