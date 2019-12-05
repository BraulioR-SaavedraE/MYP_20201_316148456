import java.awt.Color
import java.util.LinkedList

class GraficaPastel @Throws(NumeroPalabrasInvalidoException::class)
constructor(arbolito: LinkedList<Palabra>, palabras: Int, posicionY: Int) : Grafica(arbolito, palabras, posicionY) {


    private fun makeSlice(angle: Double, currentX: Double, currentY: Double, oldAngle: Double): String {
        var pieCode = "\t\t<path d=\"M0,0 LEQUIS,LLE A100,100 0 0,1 EQUIS1,LLE2 Z\" fill=\"COLOR\" />\n"
        pieCode = pieCode.replaceFirst("EQUIS", Double.toString(currentX))
        pieCode = pieCode.replaceFirst("LLE", Double.toString(currentY))
        pieCode = pieCode.replaceFirst("EQUIS1", Double.toString(calculateX(angle)))
        pieCode = pieCode.replaceFirst("LLE2", Double.toString(calculateY(angle)))

        if (Math.abs(oldAngle - angle) > Math.PI)
            pieCode = pieCode.replaceFirst("A100,100 0 0,1", "A100,100 0 1,1")

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
        pie = pie.replaceFirst("LARGO", Integer.toString(getPosicion() + 400))

        return pie
    }

    private fun setSlices(): String {
        var pie = ""
        var cumulated = 0
        var currentX = 100.0
        var currentY = 0.0
        var angle = 0.0

        if (getPalabrasTotal() === 1) {
            pie += "\t\t<circle r=\"100\" fill=\"COLOR\"/>\n"
            pie = pie.replaceFirst("COLOR", super.getNextColorRGB())
            super.posicionY += 20
            return pie
        } else {
            for (x in super.arbolito) {
                val auxiliar = angle
                angle = calculateAngle(x.getCantidad(), cumulated)
                pie += makeSlice(angle, currentX, currentY, auxiliar)
                pie = pie.replaceFirst("COLOR", getNextColorRGB())
                currentX = calculateX(angle)
                currentY = calculateY(angle)
                cumulated += x.getCantidad()

            }
        }

        if (super.arbolito.size() < super.getPalabrasTotal()) {
            val auxiliarAngulo = angle
            angle = calculateAngle(super.getPalabrasTotal() - cumulated, cumulated)
            val auxiliar = makeSlice(angle, currentX, currentY, auxiliarAngulo)
            pie += auxiliar
            pie = pie.replaceFirst("COLOR", getNextColorRGB())
            currentX = calculateX(angle)
            currentY = calculateY(angle)
            cumulated += super.getPalabrasTotal() - cumulated
        }

        super.posicionY += 20
        return pie
    }

    private fun calculateAngle(percent: Int, cumulated: Int): Double {
        val total = super.getPalabrasTotal()

        return 2.0 * Math.PI * ((cumulated.toDouble() + percent.toDouble() + 0.0) / total)
    }
}