package Main

import java.io.*

/**
 * Clase para generar archivos HTML dada una gráfica de pastel, una de barras y un directorio.
 */
object GeneradorHTML {

    private val header = ("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n"
            + "<title>TITULO</title>\n"
            + "<meta charset=\"UTF-8\">"
            + "</head>\n" + "<h1> NOMBRE </h1>\n" + "<body>\n")
    private val footer = "</body>\n" + "</html>"
    private val headerSVG = "\t<svg viewBox=\"-200 -200 800 LARGO \">\n"
    private val rotationSVG = "\t\t<g transform=\"rotate(-90)\">\n"
    private val footerSVG = "\t</svg>\n"

    /*
     * Método que incrusta la gráfica de pastel al código HTML.
     * @param pastel : la gráfica pastel.
     * @param posicion1 : la posición con respecto a SVG en la que se
     * va a insertar la gráfica.
     * @return el código HTML con la gráfica incrustada.
     */
    private fun incrustaPastel(pastel: GraficaPastel, posicion1: Int): String {
        pastel.posicion = posicion1

        return pastel.makePie()
    }

    /*
     * Método para agregar la gráfica de barras al código HTML.
     * @param barras : gráfica de barras que se va a agregar.
     * @param posicion1 : posición de la coordenada Y con respecto a SVG
     * en donde se quiere insertar la gráfica.
     * @return string con la gráfica anexada.
     */
    private fun incrustaBarras(barras: GraficaBarras, posicion1: Int): String {
        barras.posicion = posicion1

        return barras.creaGrafica()
    }

    /**
     * Método que genera un archivo HTML.
     * @param pastel : gráfica de pastel que se va a inscrustar.
     * @param barras : gráfica de barras que se va a inscrustar.
     * @param directorio : directorio en donde se va a guardar el archivo HTML.
     * @oaram nombre : nombre del archivo que se va a abrir para obtener la información sobre
     * sus palabras.
     */
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

    /*
     * Método que guarda un documento HTML en un directorio.
     * @param directorio : carpeta en donde se va a guardar el
     * archivo HTML.
     * @param nombre : nombre del archivo HTML que se va a crear.
     * @param html : código HTML.
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