Compilador Autómatas 2
Este proyecto es una aplicación gráfica desarrollada en Java para analizar código fuente. El compilador realiza un análisis léxico del código proporcionado por el usuario y muestra los resultados en una tabla de símbolos y una tabla de errores. El proyecto está diseñado como parte de la materia de Autómatas 2.

Características
Análisis léxico: Permite analizar el código ingresado en busca de tokens y errores léxicos.
Tabla de símbolos: Muestra los lexemas y sus tipos de datos.
Tabla de errores: Muestra los errores encontrados en el código, incluyendo la línea y una breve descripción.
Interfaz gráfica: El usuario puede interactuar con el compilador mediante una GUI creada con Swing.
Modos de color: La interfaz tiene un modo oscuro y un modo claro, que el usuario puede alternar con botones.
Instalación
Clonar el repositorio en tu máquina local:

bash
Copiar código
git clone https://github.com/tu-repositorio/compilador-automatas2.git
Abrir el proyecto en tu IDE favorito (por ejemplo, IntelliJ IDEA o NetBeans).

Asegurarse de tener instalada la versión de Java 15.0.2 o superior.

Ejecutar la clase Compilador para abrir la interfaz gráfica.

Uso
Ingresar código: Escribe el código que quieres analizar en el área de texto.
Análisis: Haz clic en el botón "Analizar" para procesar el código. Los resultados se mostrarán en las tablas de símbolos y errores.
Eliminar contenido: Puedes limpiar el área de texto y las tablas haciendo clic en "Eliminar".
Cambiar modo de visualización: Puedes alternar entre los modos claro y oscuro con los botones "Modo Dark" y "Modo White".
Cerrar la aplicación: Haz clic en "Cerrar" para salir de la aplicación.
Estructura del Proyecto
El proyecto está dividido en varias clases, siendo la principal Compilador. A continuación, se describen brevemente las funciones más importantes:

Clase Compilador
La clase Compilador es la interfaz gráfica principal. Sus principales funciones son:

Constructor: Inicializa los componentes gráficos de la interfaz, como las tablas de símbolos y errores, y configura el diseño de la ventana.
Botón "Analizar": Inicia el proceso de análisis léxico del código ingresado en el área de texto.
Botón "Eliminar": Limpia el contenido del área de texto y las tablas.
Botón "Modo Dark" / "Modo White": Alterna el color del fondo y texto entre modo oscuro y modo claro.
Interacción con el usuario: Utiliza JOptionPane para mostrar mensajes emergentes de error o éxito.
Clase NumeroLinea
Esta clase se utiliza para mostrar el número de línea al lado del área de texto, facilitando la identificación de errores.

Tabla de Símbolos
Se utiliza para almacenar y mostrar los lexemas y su tipo de dato correspondiente. Los lexemas pueden ser identificadores, números o cadenas.

Tabla de Errores
Se utiliza para registrar y mostrar los errores léxicos detectados. Cada error incluye:

El token problemático.
El lexema asociado.
La línea donde ocurrió.
Una descripción del error.
Ejemplo de uso
Supón que ingresas el siguiente código en el área de texto:

java
Copiar código
EQ111 = 1;
EQ112 = 3.5;
EQ114 = "Hola";
EQ117 = 5;
EQ115 = EQ111 + EQ112 - EQ117;
Luego de hacer clic en "Analizar", verás que la tabla de símbolos contendrá los lexemas y sus tipos de datos (por ejemplo, EQ111 como Entero, EQ112 como Real, etc.). Si hay errores, estos se mostrarán en la tabla de errores.

Requisitos
Java 15.0.2 o superior.
Contribuir
Haz un fork del repositorio.

Crea una nueva rama para tus cambios:

bash
Copiar código
git checkout -b mi-nueva-funcionalidad
Realiza tus cambios y haz un commit:

bash
Copiar código
git commit -m "Añadir nueva funcionalidad"
Sube tus cambios al repositorio:

bash
Copiar código
git push origin mi-nueva-funcionalidad
Abre un pull request para revisión.

Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más información.
