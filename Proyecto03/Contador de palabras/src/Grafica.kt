import java.util.LinkedList

open class Grafica @Throws(NumeroPalabrasInvalidoException::class)
protected constructor(var palabrasCompletas: LinkedList<Palabra>, palabras: Int, posicionY: Int) {
    protected var colores: Colores
    protected var palabrasDiferentes: LinkedList<Palabra>
    protected var palabras = 0
    var posicion = 0

    val nextColorRGB: String
        get() {
            val color = colores.dameColor()
            val red = color.red
            val green = color.green
            val blue = color.blue

            return String.format("rgb(%d, %d, %d)", red, green, blue)
        }

    protected val palabrasTotal: Int
        get() = Contador.totalPalabras(palabrasCompletas)

    init {
        if (palabras <= 0)
            throw NumeroPalabrasInvalidoException("Dame un número de palabras válido")
        else if(palabras > palabrasCompletas.size)
            throw NumeroPalabrasInvalidoException("Tu documento tiene menos palabras diferentes que las rebanadas que pides")

        this.palabrasCompletas = palabrasCompletas
        this.palabras = palabras
        this.posicion = posicionY
        this.palabrasDiferentes = LinkedList()

        var contador = 0

        for (x in palabrasCompletas) {
            if (contador < palabras) {
                this.palabrasDiferentes.add(x)
                contador++
            }
        }

        if (palabras < Contador.totalPalabras(palabrasCompletas))
            colores = Colores(palabras + 1)
        else
            colores = Colores(palabrasCompletas.size)

    }

    fun makeMarginalNotes(): String {
        var marginalNotes = ""
        val texto = "\t\t<text x='%d' y='%d' fill='black' font-family='sans-serif' font-size='10' </text>%s</text>\n"
        val cuadro =
            "\t\t<rect x='%d' y='%d' height='10' width='10' fill='%s' stroke='transparent' stroke-width='2'/>\n"
        var coordenadaX = -200
        var coordenadaY = posicion
        var acumulado = 0

        for (x in palabrasDiferentes) {
            if ((x.palabra.length + 7) * 10 + coordenadaX + 10 > 400) {
                coordenadaX = -200
                coordenadaY += 20
            }
            marginalNotes += String.format(cuadro, coordenadaX, coordenadaY, nextColorRGB)
            val wordDetails = makeWordDetails(x)
            marginalNotes += String.format(texto, coordenadaX + 10, coordenadaY + 10, wordDetails)
            coordenadaX += wordDetails.length * 10

            if (getPercent(x.cantidad) >= 10)
                coordenadaX -= 10

            acumulado += x.cantidad
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
            coordenadaX += wordDetails.length * 10

            if (getPercent(otras) >= 10)
                coordenadaX -= 10
        }

        posicion = coordenadaY

        return marginalNotes
    }

    private fun makeWordDetails(word: Palabra): String {
        val percent = getPercent(word.cantidad)

        return String.format("\"%s\" : %d", word.palabra, percent) + "%"
    }

    private fun getPercent(cantidad: Int): Int {
        return Math.round((cantidad + 0.0) / Contador.totalPalabras(palabrasCompletas) * 100.0).toInt()
    }

    protected fun ponPosicionY(posicionY: Int) {
        this.posicion = posicionY
    }
}