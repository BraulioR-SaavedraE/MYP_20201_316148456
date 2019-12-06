Para correr el proyecto Contador de Palabras basta con abrirlo desde la IDE IntelliJ IDEA,
configurar una compilación poniendo a la clase InterfazGrafica como la clase principal.
Sin embargo, también se puede correr posicionándose en la carpeta en donde se encuentran todas 
las clases y ejecutando el siguiente comando: "kotlinc *.kt -include-runtime -d InterfazGrafica.jar"
Posteriormente y sin cambiar la localización, ejecutamos "java -cp InterfazGrafica.jar Main.InterfazGrafica"