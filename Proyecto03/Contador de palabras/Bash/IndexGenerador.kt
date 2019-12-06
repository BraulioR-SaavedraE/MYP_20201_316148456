package Main

import java.io.*
import java.util.LinkedList

/**
 * Clase que genera un Index.html para un conjunto de archivos HTML
 * que contienen gráficas de barra, pastel y más información acerca
 * de un documento y sus palabras.
 */
class IndexGenerador(private val nombres: LinkedList<String>) {

    /**
     * Método que genera el código necesario para crear un HTML
     * que contiene hiperreferencias a otros archivos HTML.
     * @param directorio : directorio en donde se quiere guardar el index.
     */
    fun generaIndex(directorio: String) {
        var index = HEADER

        for (x in nombres) {
            val nombre = x.substring(x.lastIndexOf("/") + 1, x.length)
            index += REF
            index = index.replaceFirst("ARCHIVO".toRegex(), "$x.html")
            index = index.replaceFirst("NOMBRE".toRegex(), nombre + " ")
        }

        index += FOOTER

        guardaHTML(directorio, "index", index)
    }

    companion object {
        private val HEADER = ("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n"
                + "<title>Index</title>\n"
                + "<meta charset=\"UTF-8\">")
        private val FOOTER = "</html>"
        private val REF = "<a href=\"ARCHIVO\">NOMBRE</a>"

        /*
         * Método auxiliar que crea un archivo HTML en un directorio.
         * @oaram directorio : carpeta en donde se quiere guardar
         * el archivo HTML.
         * @param nombre : nombre del archivo HTML.
         * @param String : código HTML.
         */
        private fun guardaHTML(directorio: String, nombre: String, html: String) {
            val path = "$directorio/$nombre.html"
            try {
                val myObj = File(path)
                myObj.createNewFile()

            } catch (e: IOException) {
                println(e)
            }

            try {
                val myWriter = FileWriter(path)
                myWriter.write(html)
                myWriter.close()
            } catch (e: IOException) {
                println(e)
            }
        }
    }
}