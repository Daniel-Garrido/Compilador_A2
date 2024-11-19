package package1;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Triplos2 {

    public void AnalizarTriplo(String codigo) throws IOException {
        System.out.println("triplos 2");
        String[] operadoresRelacionales = {"==", "<", ">", ">=", "<="};
        String[] operadoresAritmeticos = {"+", "-", "*", "/", "%"};

        String For = "for";
        String T = "T";
        String jr = "JR";
        String code = codigo;

        String tablaTriplo = "linea,Dato Objeto,Dato Fuente,Operador\n";
        String noRelacional = "", expresion = "", intervalo = "";

        boolean dentroFOR = false;
        String simboloActual = "";

        int NUMERO = 1, numeroLinea = 1, primerTrue = 0, finFor = 0, fin = 0, second = 0;
        int lineaCondicionFor = 0;
        String[] partes2 = {};

        String[] lineas = code.split("\n");
        List<Integer> lineasAReemplazar = new ArrayList<>();

        for (String linea : lineas) {
            System.out.println("Triplos 2");
            System.out.println("| " + linea + " |");

            if (linea.contains(For)) {
                dentroFOR = true;
                String contenidoFor = linea.replace(For, "").replace("(", "").replace(")", "").replace("{", "");

                if (contenidoFor.contains(";")) {
                    String[] partesDelFor = contenidoFor.split(";");

                    String inicializacion = partesDelFor[0].trim();
                    NUMERO=1;
                    if (inicializacion.contains("=")) {
                        String[] partesInicializacion = inicializacion.split("=");
                        tablaTriplo += numeroLinea + ",T1," + partesInicializacion[1].trim() + ",=\n"; // Usar T1 aquí
                        numeroLinea++;
                        tablaTriplo += numeroLinea + "," + partesInicializacion[0].trim() + ",T1,=\n"; // Asignar a variable
                        numeroLinea++;
                        NUMERO++;
                    }
                    

                    String condicion = partesDelFor[1].trim();
                    lineaCondicionFor = numeroLinea;
                    String[] relacion = {};

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
                                String[] partes = relacion[0].split(simboloRelacional);
                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[0].trim() + "," + "=\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[1].trim() + "," + simboloActual + "\n";
                                numeroLinea++;

                                tablaTriplo += numeroLinea + "," + jr + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                numeroLinea++;
                                lineasAReemplazar.add(numeroLinea);  // Añadir línea a lista para reemplazo
                                tablaTriplo += numeroLinea + "," + jr + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
                                numeroLinea++;
                            }
                        }

                        for (String simboloRelacional2 : operadoresRelacionales) {
                            if (relacion[1].contains(simboloRelacional2)) {
                                simboloActual = simboloRelacional2;
                                partes2 = relacion[1].split(simboloRelacional2);

                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes2[0].trim() + "," + "=\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes2[1].trim() + "," + simboloActual + "\n";
                                numeroLinea++;

                                tablaTriplo += numeroLinea + "," + jr + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                numeroLinea++;
                                lineasAReemplazar.add(numeroLinea);  // Añadir línea a lista para reemplazo
                                tablaTriplo += numeroLinea + "," + jr + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
                                numeroLinea++;
                            }
                        }

                        intervalo = partesDelFor[2].trim();

                    } else {
                        for (String simbolo : operadoresRelacionales) {
                            condicion = condicion.trim();
                            if (condicion.contains(simbolo)) {
                                simboloActual = simbolo;
                                String[] partes = condicion.split(simbolo);
                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[0].trim() + "," + "=\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + partes[1].trim() + "," + simboloActual + "\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + jr + NUMERO + "," + "TRUE" + "," + (numeroLinea + 2) + "\n";
                                numeroLinea++;
                                lineasAReemplazar.add(numeroLinea);  // Añadir línea a lista para reemplazo
                                tablaTriplo += numeroLinea + "," + jr + NUMERO + "," + "FALSE" + "," + "REEMPLAZAR" + "\n";
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
                String[] partido = linea.replace(";", "").trim().split("=");
                String ladoIzquierdo = partido[0].trim();
                String ladoDerecho = partido[1].trim();

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
                NUMERO=1;
                while (!operadores.isEmpty()) {
                    int idx = operadores.indexOf("*");
                    if (idx == -1) idx = operadores.indexOf("/");

                    if (idx == -1) idx = operadores.indexOf("+");
                    if (idx == -1) idx = operadores.indexOf("-");

                    String op1 = tokens.remove(idx);
                    String op2 = tokens.remove(idx);
                    String operador = operadores.remove(idx);

                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + op1 + "," + "=\n";
                    numeroLinea++;
                    
                    tablaTriplo += numeroLinea + "," + T + NUMERO + "," + op2 + "," + operador + "\n";
                    tokens.add(idx, T + NUMERO);
                    NUMERO++;
                    numeroLinea++;
                    
                }
                
                tablaTriplo += numeroLinea + "," + T+NUMERO + "," + tokens.get(0) + "," + "hola=\n";
                numeroLinea++;
                
                tablaTriplo += numeroLinea + "," + ladoIzquierdo + "," + T+(NUMERO-1) + "," + "hola1=\n";
                numeroLinea++;
            } else if (linea.contains("}")) {
                if (!intervalo.isEmpty()) {
                    if (intervalo.contains("=")) {
                        String[] partesIntervalo = intervalo.split("=");
                        String variable = partesIntervalo[0].trim();
                        expresion = partesIntervalo[1].trim();

                        for (String OPA : operadoresAritmeticos) {
                            if (expresion.contains(OPA)) {
                                String[] operandoPartes = expresion.split("\\" + OPA);
                                tablaTriplo += numeroLinea + "," + T + 1 + "," + operandoPartes[1].trim() + "," + "hola2=\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + T + NUMERO + "," + variable.trim() + "," + "hola3=\n";
                                numeroLinea++;
                                tablaTriplo += numeroLinea + "," + variable + "," + T + NUMERO + "," + OPA + "\n";
                                numeroLinea++;
                            }
                        }
                    }
                }
            }
        }

        // Reemplazo de REEMPLAZAR con la línea de salto correspondiente
        for (Integer linea : lineasAReemplazar) {
            tablaTriplo = tablaTriplo.replace("REEMPLAZAR", String.valueOf(numeroLinea));
        }

        Exportar imp = new Exportar();
        imp.Imprimir("./Triplos.csv", tablaTriplo);

        InterfazTriplos mp = new InterfazTriplos();
        mp.mostrarArch("./Triplos.csv");
    }
}
