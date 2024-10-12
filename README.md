# Clase `Compilador`

La clase `Compilador` es la encargada de la interfaz gráfica de usuario del compilador desarrollado en Java. Permite al usuario ingresar código fuente, analizarlo y visualizar los resultados en una **tabla de símbolos** y una **tabla de errores**.

## Características

- **Análisis léxico**: Permite analizar el código fuente ingresado y obtener los tokens válidos y los errores léxicos.
- **Interfaz gráfica**: Utiliza la biblioteca `Swing` para proporcionar una interfaz amigable donde el usuario puede interactuar con el compilador.
- **Modos de color**: Permite cambiar entre modo claro y modo oscuro para mejorar la experiencia de usuario.
- **Visualización de tablas**: Muestra la tabla de símbolos y la tabla de errores, actualizándose dinámicamente después del análisis.

## Atributos

- `NumeroLinea numeroLinea`: Permite mostrar el número de líneas en el área de texto donde el usuario ingresa el código fuente.
- `DefaultTableModel modelTableSymbol`: Modelo de la tabla de símbolos donde se muestran los lexemas y tipos de datos.
- `DefaultTableModel modelTableError`: Modelo de la tabla de errores donde se registran los tokens inválidos, errores léxicos, y errores semánticos.
- `ArrayList<String[]> rowsTableSymbol`: Lista que almacena los lexemas y tipos de datos antes de mostrarlos en la tabla de símbolos.
- `ArrayList<String[]> rowsTableError`: Lista que almacena los errores antes de mostrarlos en la tabla de errores.

## Métodos

### `Compilador()`

Este es el **constructor** de la clase que inicializa los componentes gráficos de la interfaz. Configura la ventana, establece los modelos de las tablas y ajusta el estilo visual para que la interfaz sea amigable.

### `void jButton1ActionPerformed(java.awt.event.ActionEvent evt)`

Este método se activa cuando el usuario hace clic en el botón "Analizar". Verifica si el área de texto contiene código, y si es así, inicia el proceso de análisis léxico del código fuente ingresado.

### `void jButton2ActionPerformed(java.awt.event.ActionEvent evt)`

Este método se activa cuando el usuario hace clic en el botón "Cerrar". Muestra un cuadro de diálogo para confirmar si el usuario desea salir del programa. Si el usuario confirma, la aplicación se cierra.

### `void jButton3ActionPerformed(java.awt.event.ActionEvent evt)`

Este método se activa cuando el usuario hace clic en el botón "Eliminar". Limpia el área de texto, así como las tablas de símbolos y de errores.

### `void jButton4ActionPerformed(java.awt.event.ActionEvent evt)`

Este método activa el **modo oscuro**. Cambia el fondo de la interfaz y el color de los textos a colores apropiados para un modo oscuro.

### `void jButton5ActionPerformed(java.awt.event.ActionEvent evt)`

Este método activa el **modo claro**. Cambia el fondo de la interfaz y el color de los textos a colores claros.

## Uso

1. **Ingresar código**: Escribe el código que deseas analizar en el área de texto.
2. **Analizar**: Haz clic en el botón "Analizar" para procesar el código. Los resultados aparecerán en las tablas de símbolos y errores.
3. **Limpiar datos**: Puedes limpiar el contenido del área de texto y las tablas haciendo clic en el botón "Eliminar".
4. **Cambiar de modo**: Cambia entre los modos claro y oscuro con los botones "Modo Dark" y "Modo White".
5. **Cerrar la aplicación**: Haz clic en el botón "Cerrar" para salir de la aplicación.

## Estructura de la interfaz

- **Área de texto**: Donde el usuario ingresa el código fuente.
- **Tabla de símbolos**: Muestra los tokens válidos junto con su tipo de dato (entero, real, cadena, etc.).
- **Tabla de errores**: Muestra los errores léxicos y semánticos encontrados durante el análisis.
- **Botones**: Botones para analizar, limpiar, cambiar de modo (oscuro/claro) y cerrar la aplicación.

## Ejemplo de uso




## Requisitos

- **Java 15.0.2** o superior.
- Requiere las bibliotecas de **Swing** para la interfaz gráfica.

## Contribuciones

Si deseas contribuir al proyecto, sigue estos pasos:

1. Haz un **fork** del repositorio.

2. Crea una nueva rama para tu funcionalidad:

   
   `git checkout -b mi-nueva-funcionalidad`


# Clase `AnalizadorLexico`

La clase `AnalizadorLexico` es la encargada de realizar el **análisis léxico** del código fuente en el proyecto del compilador. Esta clase descompone el código en tokens y los clasifica según su tipo, además de detectar y registrar cualquier error léxico o semántico.

## Responsabilidades

- **Análisis léxico**: Descomposición del código en tokens y clasificación de cada uno.
- **Relleno de la tabla de símbolos**: Guarda lexemas válidos y sus tipos de datos.
- **Detección de errores**: Identificación y registro de errores léxicos y semánticos en la tabla de errores.
- **Gestión de tablas**: Rellena y limpia tanto la tabla de símbolos como la tabla de errores.

## Atributos

- `modelTableSymbol`: Modelo de la tabla que contiene los **lexemas** y sus **tipos de datos**.
- `modelTableError`: Modelo de la tabla que contiene los **tokens**, **lexemas**, **líneas** y **descripción del error**.
- `rowsTableSymbol`: Lista temporal que almacena los lexemas y sus tipos de datos antes de ser agregados a la tabla de símbolos.
- `rowsTableError`: Lista temporal que almacena los errores antes de ser agregados a la tabla de errores.
- `identificadoresTipo`: Mapa que asocia identificadores con sus tipos de datos.
- `variableAsignacion`: Almacena la variable del lado izquierdo de una asignación.
- `contadorErrorSemantico`: Contador para llevar el control de los errores semánticos detectados.

## Métodos

### `AnalizadorLexico(DefaultTableModel modelTableSymbol, DefaultTableModel modelTableError)`

Constructor de la clase que inicializa los modelos de las tablas de símbolos y errores, y las estructuras de datos necesarias para realizar el análisis léxico.

### `void analizarExpresiones(String expresiones, int linea)`

Este método procesa el código fuente línea por línea, descomponiéndolo en tokens y enviándolos al método `analizarLexema` para su clasificación.

### `void analizarLexema(String lexema, int linea, StringTokenizer st)`

Este método clasifica cada token según su tipo. Dependiendo del lexema, se verifica si es un **identificador**, **número entero**, **número real**, **cadena**, **operador aritmético**, **operador de asignación** o **separador**.

### `void addTable()`

Este método agrega el contenido de `rowsTableSymbol` y `rowsTableError` a las tablas de símbolos y errores. Se limpia la tabla antes de agregar los nuevos datos.

### `void clearTable()`

Limpia el contenido de las tablas de símbolos y errores.

### `boolean lexemaYaAnalizado(String lexema)`

Verifica si un lexema ya ha sido procesado y agregado a la tabla de símbolos para evitar duplicados.

### `String determinarTipoPorValor(String valor)`

Determina el tipo de dato de un valor. Puede ser **Entero**, **Real** o **Cadena** dependiendo del formato del valor.

### `boolean tiposCompatibles(String tipo1, String tipo2)`

Determina si dos tipos de datos son compatibles para realizar operaciones aritméticas. Los **enteros** y **reales** son compatibles entre sí.

### `String obtenerTipoOperando(String operando)`

Obtiene el tipo de dato de un operando, ya sea verificando en los **identificadores** previamente declarados o determinando su tipo a partir del valor literal.

## Errores detectados

- **Errores léxicos**: Se registran cuando un lexema no cumple con las reglas sintácticas, como un identificador mal formado o una cadena incompleta.
- **Errores semánticos**: Se producen cuando se intenta realizar operaciones con tipos de datos incompatibles o cuando se usa una variable que no ha sido definida.

## Ejemplo de uso

Supón que se analiza el siguiente código:

```java
EQ111 = 10;
EQ112 = 5.5;
EQ114 = "Hola";
EQ115 = EQ111 + EQ112;

