import java.awt.Color
import java.util.HashSet
import java.util.Random

class Colores(numero: Int) {
    private val colores: HashSet<Color>
    private var iterador: Iterator<Color>? = null

    init {
        if (numero <= 0)
            throw IllegalArgumentException("Tienes que darme un número válido para la cantidad de colores")

        colores = HashSet()
        iterador = colores.iterator()

        val randomGen = Random()
        var randomRed = 0
        var randomGreen = 0
        var randomBlue = 0

        var i = 0
        while (i < numero && i < 16777216) {
            randomRed = randomGen.nextInt(256)
            randomGreen = randomGen.nextInt(256)
            randomBlue = randomGen.nextInt(256)
            val colorcillo = Color(randomRed, randomGreen, randomBlue)

            if (!colores.contains(colorcillo)) {
                colores.add(colorcillo)
                i++
            }
        }

    }

    fun dameColor(): Color {
        if (iterador!!.hasNext())
            return iterador!!.next()

        iterador = colores.iterator()

        return iterador!!.next()
    }
}