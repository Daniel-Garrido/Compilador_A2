package package1; 
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.util.logging.Logger;
import java.util.HashMap;

public class Optimizar {
    // Variable global para almacenar el código optimizado
    private String codigoOptimizado = "";
    
    // HashMap para almacenar el valor de las variables y sus valores
    private HashMap<String, String> variables = new HashMap<String, String>();

    public String getCodigoOptimizado() {
        return this.codigoOptimizado;
    }

    public void optimizarCodigo(String code, JTextArea textArea) {
        //verificar que haya algo para optimizar 
        if(code.isEmpty()) {
            JOptionPane.showMessageDialog(textArea, "No se ha ingresado código", "Error", 0);
            return;
        }

        //verificacion en la consola 
        Logger logger = Logger.getLogger(Optimizar.class.getName());
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info(String.format("Entrada: %s%n%n%s", code, "*".repeat(70)));
        }
    
        // Paso 1: Limpieza básica del código
        code = code.replaceAll("^[ \t]+", ""); // Quitar espacios/tabs al inicio
        code = code.replaceAll("[ \t]+$", ""); // Quitar espacios/tabs al final
        code = code.replaceAll("[ \t]+", " "); // Quitar múltiples espacios
        code = code.replace("\r\n", "\n").replace("\n\r", "\n").replace("\r", "\n");
        code = code.replaceAll("\n+", "\n");  // Quitar múltiples saltos de línea
        code = code.trim(); // Quitar espacios al inicio y final
    
        // Paso 2: Identificar y almacenar variables constantes
        String[] lines = code.split("\n"); // Dividir el código en líneas
        for (String line : lines) {
            line = line.trim();
            // Buscar asignaciones del tipo EQ11(0-9) = 0 o EQ11(0-9) = 1
            if (line.matches("([a-zA-Z0-9_]+)\\s*=\\s*(0|1);")) {
                String[] parts = line.split("\\s*=\\s*");
                String variable = parts[0].trim();
                String value = parts[1].replace(";", "").trim();
                variables.put(variable, value); // Guardar en el HashMap
            }
        }
    
        // Paso 3: Reemplazar variables constantes y optimizar operaciones aritméticas
        StringBuilder codigoOptimizado = new StringBuilder();
        for (String line : lines) {
            line = line.trim();
            String lineaOptimizada = line;
    
            // Evitar modificar estructuras de control como "for"
            if (line.trim().startsWith("for")) {
                codigoOptimizado.append(line).append("\n");
                continue;
            }
    
            // No reemplazar la asignación inicial de las constantes
            if (line.matches("([a-zA-Z0-9_]+)\\s*=\\s*(0|1);")) {
                codigoOptimizado.append(line).append("\n");
                continue;
            }
         
            // Reemplazo de constantes
            for (String variable : variables.keySet()) {
                String value = variables.get(variable);

                // Ignorar identificadores dentro de comillas
                if (line.matches(".*\".*\\b" + variable + "\\b.*\".*")) {
                    continue; // Saltar si el identificador está dentro de comillas

                } else if (line.matches(".*[\\+\\-\\*/]\\s*\\b" + variable + "\\b.*")) {
                    continue; // Saltar esta variable si forma parte de una operación
                }

                lineaOptimizada = lineaOptimizada.replaceAll("\\b" + variable + "\\b", value);
            }

            // Simplificación de operaciones aritméticas triviales
            lineaOptimizada = lineaOptimizada.replaceAll("\\b1\\s*\\*\\s*([a-zA-Z0-9_]+)", "$1"); // 1 * X -> X
            lineaOptimizada = lineaOptimizada.replaceAll("\\b([a-zA-Z0-9_]+)\\s*\\*\\s*1", "$1"); // X * 1 -> X
            lineaOptimizada = lineaOptimizada.replaceAll("\\b([a-zA-Z0-9_]+)\\s*/\\s*1", "$1");  // X / 1 -> X
            lineaOptimizada = lineaOptimizada.replaceAll("\\b([a-zA-Z0-9_]+)\\s*\\+\\s*0", "$1"); // X + 0 -> X
            lineaOptimizada = lineaOptimizada.replaceAll("\\b0\\s*\\+\\s*([a-zA-Z0-9_]+)", "$1"); // 0 + X -> X
            lineaOptimizada = lineaOptimizada.replaceAll("\\b([a-zA-Z0-9_]+)\\s*-\\s*0", "$1");  // X - 0 -> X
    
            codigoOptimizado.append(lineaOptimizada.trim()).append("\n");
        }
    
        // Paso 4: Actualizar el código optimizado
        this.codigoOptimizado = codigoOptimizado.toString().trim();
        textArea.setText(this.codigoOptimizado);
    
        // Mostrar mensaje de optimización correcta
        JOptionPane.showMessageDialog(textArea, "Se ha optimizado correctamente", "Optimizado correcto", 3);
    }    
}

