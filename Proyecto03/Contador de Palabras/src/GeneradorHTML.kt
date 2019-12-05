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

    private fun incrustaPastel(pastel: GraficaPastel, posicion: Int): String {
        pastel.ponPosicionY(posicion)

        return pastel.makePie()
    }

    private fun incrustaBarras(barras: GraficaBarras, posicion: Int): String {
        barras.ponPosicionY(posicion)

        return barras.creaGrafica()
    }

    fun generaHTML(pastel: GraficaPastel, barras: GraficaBarras, directorio: String, nombre: String): String {
        var html = header
        html += headerSVG
        html += rotationSVG
        html += incrustaPastel(pastel, 110)
        html += incrustaBarras(barras, pastel.getPosicion() + 300)
        html += footerSVG
        html += Contador.reporte(pastel.getPalabrasCompletas())
        html += footer
        html = html.replaceFirst("LARGO", Integer.toString(barras.getPosicion() + 400 + pastel.getPalabrasCompletas().size()))
        html = html.replaceFirst("TITULO", nombre)
        html = html.replaceFirst("NOMBRE", nombre)

        guardaHTML(directorio, nombre, html)
        return html
    }

    private fun guardaHTML(directorio: String, nombre: String, html: String) {
        val path = "$directorio/$nombre.html"
        try {
            val myObj = File(path)
            myObj.createNewFile()

        } catch (e: IOException) {
            System.out.println(e)
        }

        try {
            val myWriter = FileWriter(path)
            myWriter.write(html)
            myWriter.close()
        } catch (e: IOException) {
            System.out.println(e)
        }

    }
}