import java.awt.Color
import java.util.LinkedList

class GraficaBarras @Throws(NumeroPalabrasInvalidoException::class)
constructor(arbolito: LinkedList<Palabra>, cantidad: Int, posicion: Int) : Grafica(arbolito, cantidad, posicion) {

    fun creaGrafica(): String {
        var barra = creaBarras()
        barra += makeMarginalNotes()
        return barra
    }

    private fun creaBarras(): String {
        var barras = ""
        var acumulado = 0
        var coordenadaX = -200
        val coordenadaY = posicionY - 10
        val tamano = Math.floor(600.0 / arbolito.size()) as Int
        var cuadro = "\t\t<rect x='%d' y='%d' height='%d' width='ANCHO' fill='%s' stroke='transparent' stroke-width='2'/>\n"
        cuadro = cuadro.replaceFirst("ANCHO", Integer.toString(tamano))

        for (x in arbolito) {
            val altura = Math.floor((x.getCantidad() + 0.0) / Contador.totalPalabras(super.completas) * 200.0) as Int
            barras += String.format(cuadro, coordenadaX, coordenadaY - altura, altura, getNextColorRGB())
            coordenadaX += tamano
            acumulado += x.getCantidad()
        }

        if (palabras < Contador.totalPalabras(super.completas)) {
            val altura = Math.floor((Contador.totalPalabras(super.completas) - acumulado + 0.0) / Contador.totalPalabras(super.completas) * 200.0) as Int
            barras += String.format(cuadro, coordenadaX, coordenadaY - altura, altura, getNextColorRGB())
            coordenadaX += tamano
        }

        return barras
    }
}