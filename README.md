# Proyecto Autómatas 2 
Este proyecto es parte de la materia de lenguajes y autómatas 2 que tiene la finalidad del desarrollo de un compilador para detectar el analisis léxico y semantico de acuerdo a nuestras reglas establecidas. Actualmente su función es el de analizar datos que se le ingresen por medio de una interfaz de usuario y cumpliendo con las reglas establecidas, posteriormente pasa al llenado de la tabla de simbolos y la tabla de errores.

## Instrucciones 

<p>
  Estas reglas determinan cuándo un identificador o un literal debe agregarse a la tabla de símbolos.
</p>

#### a) **Identificadores válidos**
- Un identificador válido **debe comenzar con** `EQ11`, seguido de un número del 0 al 9. 
  - Ejemplo: `EQ113`, `EQ110`, etc.
- Si el identificador cumple con esta regla, se agrega a la tabla de símbolos con su **lexema** y su **tipo de dato**.

#### b) **Tipos de datos permitidos**
Los tipos de datos válidos son:
- **Entero**: Una secuencia de dígitos (0-9).
  - Ejemplo: `500`, `123`.
- **Real**: Un número con un punto decimal, con al menos un dígito antes y después del punto.
  - Ejemplo: `123.45`, `5.5`.
- **Cadena**: Texto entre comillas dobles, compuesto por letras mayúsculas y minúsculas (A-Z, a-z).
  - Ejemplo: `"Hola"`, `"Mundo"`.

## Uso

1. **Ingresar código**: Escribe el código que deseas analizar en el área de texto.
2. **Analizar**: Haz clic en el botón "Analizar" para procesar el código. Los resultados aparecerán en las tablas de símbolos y errores.
3. **Limpiar datos**: Puedes limpiar el contenido del área de texto y las tablas haciendo clic en el botón "Eliminar".
4. **Cambiar de modo**: Cambia entre los modos claro y oscuro con los botones "Modo Dark" y "Modo White".
5. **Cerrar la aplicación**: Haz clic en el botón "Cerrar" para salir de la aplicación.

## Ejemplo de uso

- **Código de ejemplo sin errores**

EQ110 = 100; <br>
EQ112 = 50; <br>
EQ113 = 4.4;<br>
EQ114 = 5.5;<br>
EQ115 = "HOLA";<br>
EQ116 = "mundo";<br>
EQ117 = 7.5 + 50;<br>
EQ118 = EQ114 + EQ112;<br>

- **Código de ejemplo con errores**

EQ117 = EQ118; <br>
EQ110 = 100; <br>
EQ112 = 5.0; <br>
EQ113 = "Hola" - 5.0; <br>
EQ114 = 10.5 + 10; <br>
EQ115 = EQ110 / EQ113; <br>
EQ116 = "mundo" * 20.5; <br>
EQ119 = 11 / EQ1199 + 5.0; <br>
EQ1110 = EQ1112 * 50; <br>

## Interfaz

![WhatsApp Image 2024-10-15 at 10 31 52 PM](https://github.com/user-attachments/assets/6a0a783d-2f13-473e-806c-c3ed8cee283b)

## Requisitos
- **Java 15.0.2** o superior.
- Requiere las bibliotecas de **Swing** para la interfaz gráfica.


# 1. Clase `Compilador`
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

## Estructura de la interfaz

- **Área de texto**: Donde el usuario ingresa el código fuente.
- **Tabla de símbolos**: Muestra los tokens válidos junto con su tipo de dato (entero, real, cadena, etc.).
- **Tabla de errores**: Muestra los errores léxicos y semánticos encontrados durante el análisis.
- **Botones**: Botones para analizar, limpiar, cambiar de modo (oscuro/claro) y cerrar la aplicación.


# 2. Clase `AnalizadorLexico`

La clase `AnalizadorLexico` es la encargada de realizar el **análisis léxico** del código fuente en el proyecto del compilador. Esta clase descompone el código en tokens y los clasifica según su tipo, además de detectar y registrar cualquier error léxico o semántico.

## Responsabilidades

- **Análisis léxico**: Descomposición del código en tokens y clasificación de cada uno.
- **Llenado de la tabla de símbolos**: Guarda lexemas válidos y sus tipos de datos.
- **Detección de errores**: Identificación y registro de errores léxicos y semánticos en la tabla de errores.
- **Gestión de tablas**: Rellena y limpia tanto la tabla de símbolos como la tabla de errores.

## Atributos![Uploading WhatsApp Image 2024-10-15 at 10.31.52 PM.jpeg…]()


- `modelTableSymbol`: Modelo de la tabla que contiene los **lexemas** y sus **tipos de datos**.
- `modelTableError`: Modelo de la tabla que contiene los **tokens**, **lexemas**, **líneas** y **descripción del error**.
- `rowsTableSymbol`: Lista temporal que almacena los lexemas y sus tipos de datos antes de ser agregados a la tabla de símbolos.
- `rowsTableError`: Lista temporal que almacena los errores antes de ser agregados a la tabla de errores.
- `identificadoresTipo`: Mapa que asocia identificadores con sus tipos de datos.
- `variableAsignacion`: Almacena la variable del lado izquierdo de una asignación.
- `contadorErrorSemantico`: Contador para llevar el control de los errores semánticos detectados.

## Errores detectados

- **Errores semánticos**: Se producen cuando se intenta realizar operaciones con tipos de datos incompatibles o cuando se usa una variable que no ha sido definida.


