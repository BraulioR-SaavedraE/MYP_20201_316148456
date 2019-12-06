package Main

import java.util.LinkedList

/**
 * Clase que construye una gráfica de barras dada una
 * lista de palabras, la cantidad de barras y la posición
 * Y en donde se quiere insertar la gráfica según SVG.
 */
class GraficaBarras @Throws(NumeroPalabrasInvalidoException::class)
constructor(arbolito: LinkedList<Palabra>, cantidad: Int, posicion: Int) : Grafica(arbolito, cantidad, posicion) {

    /**
     * Método que crea una gráfica de barras.
     * @return código SVG de la gráfica de barras.
     */
    fun creaGrafica(): String {
        var barra = creaBarras()
        barra += makeMarginalNotes()
        return barra
    }

    /*
     * Método auxiliar que las barras de una gráfica que representan el porcentaje
     * de veces que aparece cada palabra.
     * @return String con el código SVG de las barras.
     */
    private fun creaBarras(): String {
        var barras = ""
        var acumulado = 0
        var coordenadaX = -200
        val coordenadaY = posicion - 10
        val tamano = Math.floor(600.0 / palabrasDiferentes.size).toInt()
        var cuadro =
            "\t\t<rect x='%d' y='%d' height='%d' width='ANCHO' fill='%s' stroke='transparent' stroke-width='2'/>\n"
        cuadro = cuadro.replaceFirst("ANCHO".toRegex(), Integer.toString(tamano))

        for (x in palabrasDiferentes) {
            val altura = Math.floor((x.cantidad + 0.0) / Contador.totalPalabras(super.palabrasCompletas) * 200.0).toInt()
            barras += String.format(cuadro, coordenadaX, coordenadaY - altura, altura, nextColorRGB)
            coordenadaX += tamano
            acumulado += x.cantidad
        }

        if (palabras < Contador.totalPalabras(super.palabrasCompletas)) {
            val altura =
                Math.floor((Contador.totalPalabras(super.palabrasCompletas) - acumulado + 0.0) / Contador.totalPalabras(
                    super.palabrasCompletas
                ) * 200.0)
                    .toInt()
            barras += String.format(cuadro, coordenadaX, coordenadaY - altura, altura, nextColorRGB)
            coordenadaX += tamano
        }

        return barras
    }
}