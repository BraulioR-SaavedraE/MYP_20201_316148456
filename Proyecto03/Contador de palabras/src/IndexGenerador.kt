package Main

import java.io.*
import java.util.LinkedList

class IndexGenerador(private val nombres: LinkedList<String>) {

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