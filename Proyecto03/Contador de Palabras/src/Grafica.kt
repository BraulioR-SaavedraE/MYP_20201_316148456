import java.awt.Color
import java.util.LinkedList

class Grafica @Throws(NumeroPalabrasInvalidoException::class)
protected constructor(protected var palabrasCompletas: LinkedList<Palabra>, palabras: Int, posicionY: Int) {
    protected var colores: Colores
    protected var palabrasDiferentes: LinkedList<Palabra>
    protected var palabras = 0
    protected var posicion = 0

    val nextColorRGB: String
        get() {
            val color = colores.dameColor()
            val red = color.getRed()
            val green = color.getGreen()
            val blue = color.getBlue()

            return String.format("rgb(%d, %d, %d)", red, green, blue)
        }

    protected val palabrasTotal: Int
        get() = Contador.totalPalabras(palabrasCompletas)

    init {
        if (palabras <= 0 || palabras > Contador.totalPalabras(palabrasCompletas))
            throw NumeroPalabrasInvalidoException("Dame un número de palabras válido")
        this.palabras = palabras
        this.posicion = posicionY
        this.palabrasDiferentes = LinkedList<Palabra>()

        if (palabras < Contador.totalPalabras(palabrasCompletas))
            colores = Colores(palabras + 1)
        else
            colores = Colores(palabras)

        var contador = 0

        for (x in palabrasCompletas) {
            if (contador < palabras)
                this.palabrasDiferentes.add(x)
            contador++
        }
    }

    fun makeMarginalNotes(): String {
        var marginalNotes = ""
        val texto = "\t\t<text x='%d' y='%d' fill='black' font-family='sans-serif' font-size='10' </text>%s</text>\n"
        val cuadro = "\t\t<rect x='%d' y='%d' height='10' width='10' fill='%s' stroke='transparent' stroke-width='2'/>\n"
        var coordenadaX = -200
        var coordenadaY = posicion
        var acumulado = 0

        for (x in palabrasDiferentes) {
            if ((x.getPalabra().length() + 7) * 10 + coordenadaX + 10 > 400) {
                coordenadaX = -200
                coordenadaY += 20
            }
            marginalNotes += String.format(cuadro, coordenadaX, coordenadaY, nextColorRGB)
            val wordDetails = makeWordDetails(x)
            marginalNotes += String.format(texto, coordenadaX + 10, coordenadaY + 10, wordDetails)
            coordenadaX += wordDetails.length() * 10

            if (getPercent(x.getCantidad()) >= 10)
                coordenadaX -= 10

            acumulado += x.getCantidad()
        }

        if (palabras < Contador.totalPalabras(palabrasCompletas)) {
            if (11 * 10 + coordenadaX + 10 > 400) {
                coordenadaX = -200
                coordenadaY += 20
            }
            marginalNotes += String.format(cuadro, coordenadaX, coordenadaY, nextColorRGB)
            val otras = Contador.totalPalabras(palabrasCompletas) - acumulado
            val wordDetails = String.format("Otras: %d", getPercent(otras)) + "%"
            marginalNotes += String.format(texto, coordenadaX + 10, coordenadaY + 10, wordDetails)
            coordenadaX += wordDetails.length() * 10

            if (getPercent(otras) >= 10)
                coordenadaX -= 10
        }

        posicion = coordenadaY

        return marginalNotes
    }

    private fun makeWordDetails(word: Palabra): String {
        val percent = getPercent(word.getCantidad())

        return String.format("\"%s\" : %d", word.getPalabra(), percent) + "%"
    }

    private fun getPercent(cantidad: Int): Int {
        return Math.round((cantidad + 0.0) / Contador.totalPalabras(palabrasCompletas) * 100.0)
    }

    protected fun ponPosicionY(posicionY: Int) {
        this.posicion = posicionY
    }
}