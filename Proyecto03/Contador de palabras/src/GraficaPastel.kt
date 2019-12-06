import java.awt.Color
import java.util.LinkedList

class GraficaPastel @Throws(NumeroPalabrasInvalidoException::class)
constructor(arbolito: LinkedList<Palabra>, palabras: Int, posicionY: Int) : Grafica(arbolito, palabras, posicionY) {


    private fun makeSlice(angle: Double, currentX: Double, currentY: Double, oldAngle: Double): String {
        var pieCode = "\t\t<path d=\"M0,0 LEQUIS,LLE A100,100 0 0,1 EQUIS1,LLE2 Z\" fill=\"COLOR\" />\n"
        pieCode = pieCode.replaceFirst("EQUIS".toRegex(), java.lang.Double.toString(currentX))
        pieCode = pieCode.replaceFirst("LLE".toRegex(), java.lang.Double.toString(currentY))
        pieCode = pieCode.replaceFirst("EQUIS1".toRegex(), java.lang.Double.toString(calculateX(angle)))
        pieCode = pieCode.replaceFirst("LLE2".toRegex(), java.lang.Double.toString(calculateY(angle)))

        if (Math.abs(oldAngle - angle) > Math.PI)
            pieCode = pieCode.replaceFirst("A100,100 0 0,1".toRegex(), "A100,100 0 1,1")

        return pieCode
    }

    private fun calculateX(angle: Double): Double {
        return Math.cos(angle) * 100.0
    }

    private fun calculateY(angle: Double): Double {
        return Math.sin(angle) * 100.0
    }

    fun makePie(): String {
        var pie = ""
        pie += setSlices()
        pie += "\t</g>\n"
        pie += super.makeMarginalNotes()
        pie = pie.replaceFirst("LARGO".toRegex(), Integer.toString(posicion + 400))

        return pie
    }

    private fun setSlices(): String {
        var pie = ""
        var cumulated = 0
        var currentX = 100.0
        var currentY = 0.0
        var angle = 0.0

        if (super.palabrasTotal === 1) {
            pie += "\t\t<circle r=\"100\" fill=\"COLOR\"/>\n"
            pie = pie.replaceFirst("COLOR".toRegex(), super.nextColorRGB)
            super.posicion += 20
            return pie
        } else {
            for (x in super.palabrasDiferentes) {
                val auxiliar = angle
                angle = calculateAngle(x.cantidad, cumulated)
                pie += makeSlice(angle, currentX, currentY, auxiliar)
                pie = pie.replaceFirst("COLOR".toRegex(), nextColorRGB)
                currentX = calculateX(angle)
                currentY = calculateY(angle)
                cumulated += x.cantidad

            }
        }

        if (super.palabras < super.palabrasTotal) {
            val auxiliarAngulo = angle
            angle = calculateAngle(super.palabrasTotal - cumulated, cumulated)
            val auxiliar = makeSlice(angle, currentX, currentY, auxiliarAngulo)
            pie += auxiliar
            pie = pie.replaceFirst("COLOR".toRegex(), nextColorRGB)
            currentX = calculateX(angle)
            currentY = calculateY(angle)
            cumulated += super.palabrasTotal - cumulated
        }

        super.posicion += 20
        return pie
    }

    private fun calculateAngle(percent: Int, cumulated: Int): Double {
        val total = super.palabrasTotal

        return 2.0 * Math.PI * ((cumulated.toDouble() + percent.toDouble() + 0.0) / total)
    }
}