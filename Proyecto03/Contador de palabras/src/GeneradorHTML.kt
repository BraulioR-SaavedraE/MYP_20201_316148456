import java.*
import java.util.LinkedList
import java.io.*

object GeneradorHTML {

    private val header = ("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n"
            + "<title>TITULO</title>\n"
            + "<meta charset=\"UTF-8\">"
            + "</head>\n" + "<h1> NOMBRE </h1>\n" + "<body>\n")
    private val footer = "</body>\n" + "</html>"
    private val headerSVG = "\t<svg viewBox=\"-200 -200 800 LARGO \">\n"
    private val rotationSVG = "\t\t<g transform=\"rotate(-90)\">\n"
    private val footerSVG = "\t</svg>\n"

    private fun incrustaPastel(pastel: GraficaPastel, posicion1: Int): String {
        pastel.posicion = posicion1

        return pastel.makePie()
    }

    private fun incrustaBarras(barras: GraficaBarras, posicion1: Int): String {
        barras.posicion = posicion1

        return barras.creaGrafica()
    }

    fun generaHTML(pastel: GraficaPastel, barras: GraficaBarras, directorio: String, nombre: String): String {
        var html = header
        html += headerSVG
        html += rotationSVG
        html += incrustaPastel(pastel, 110)
        html += incrustaBarras(barras, pastel.posicion + 300)
        html += footerSVG
        html += Contador.reporte(pastel.palabrasCompletas)
        html += footer
        html = html.replaceFirst(
            "LARGO".toRegex(),
            Integer.toString(barras.posicion + 400 + pastel.palabrasCompletas.size)
        )
        html = html.replaceFirst("TITULO".toRegex(), nombre)
        html = html.replaceFirst("NOMBRE".toRegex(), nombre)

        guardaHTML(directorio, nombre, html)
        return html
    }

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