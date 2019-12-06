package Main

import java.util.LinkedList

/**
 * Clase que crea una gráfica de pastel dada una lista de palabras, el número de
 * rebanadas y la posición Y en donde se quiere insertar la gráfica según SVG.
 */
class GraficaPastel @Throws(NumeroPalabrasInvalidoException::class)
constructor(arbolito: LinkedList<Palabra>, palabras: Int, posicionY: Int) : Grafica(arbolito, palabras, posicionY) {

    /*
     * Método que crea una rebanada de una gráfica de pastel.
     * @param angle : ángulo total de la rebanada.
     * @param currentX : actual posicion X que equivale al punto de la norma
     * en donde terminó la rebanada anterior.
     * @param currentY : posición actual de la coordenada Y, que equivale al
     * punto en donde terminó la rebanada anterior.
     * @param oldAngle : ángulo de la rebanada anterior para saber
     * qué tan grande será la nueva.
     * @return String con el código de la nueva rebanada.
     */
    private fun makeSlice(angle: Double, currentX: Double, currentY: Double, oldAngle: Double): String {
        var pieCode = "\t\t<path d=\"M0,0 LEQUIS,LLE A100,100 0 0,1 EQUIS1,LLE2 Z\" fill=\"COLOR\" />\n"
        pieCode = pieCode.replaceFirst("EQUIS".toRegex(), java.lang.Double.toString(currentX))
        pieCode = pieCode.replaceFirst("LLE".toRegex(), java.lang.Double.toString(currentY))
        pieCode = pieCode.replaceFirst("EQUIS1".toRegex(), java.lang.Double.toString(calculateX(angle)))
        pieCode = pieCode.replaceFirst("LLE2".toRegex(), java.lang.Double.toString(calculateY(angle)))

        if (Math.abs(oldAngle - angle) > Math.PI) /* cuando el ángulo total es mayor que pi,
            la rebanada no "gira" y se traza una línea recta, haciendo que la gráfica
            parezca un cucurucho */
            pieCode = pieCode.replaceFirst("A100,100 0 0,1".toRegex(), "A100,100 0 1,1")

        return pieCode
    }

    /*
     * Método que calcula la coordenada x de la norma de la nueva
     * rebanada del pastel.
     * @param angle : ángulo total de la nueva rebanada.
     * @return double con la coordenada x de la norma de donde
     * terminará la nueva rebanada.
     */
    private fun calculateX(angle: Double): Double {
        return Math.cos(angle) * 100.0
    }

    /*
     * Método que calcula la coordenada y de la norma de la nueva
     * rebanada del pastel.
     * @param angle : ángulo total de la nueva rebanada.
     * @return double con la coordenada y de la norma de donde
     * terminará la nueva rebanada.
     */
    private fun calculateY(angle: Double): Double {
        return Math.sin(angle) * 100.0
    }

    /**
     * Método que crea código SVG para una gráfica de pastel
     * @return String con el código SVG necesario para dibujar una
     * gráfica de pastel.
     */
    fun makePie(): String {
        var pie = ""
        pie += setSlices()
        pie += "\t</g>\n"
        pie += super.makeMarginalNotes()
        pie = pie.replaceFirst("LARGO".toRegex(), Integer.toString(posicion + 400))

        return pie
    }

    /*
     * Método que une las rebanadas de una gráfica de pastel.
     * @return String con el código SVG de las rebanadas
     * de pastel juntas.
     */
    private fun setSlices(): String {
        var pie = ""
        var cumulated = 0
        var currentX = 100.0
        var currentY = 0.0
        var angle = 0.0

        if (super.palabrasTotal == 1) {
            /* Por el sistema que usamos, es necesario considerar
                este caso único, ya que una sola rebanada regresa de las mismas
                coordenadas de las que vino, lo cual SVG lo traduce como no dibujar
                absolutamente nada
             */
            pie += "\t\t<circle r=\"100\" fill=\"COLOR\"/>\n"
            pie = pie.replaceFirst("COLOR".toRegex(), super.nextColorRGB)
            super.posicion += 20
            return pie
        } else {    // Para la rebanada que representa las palabras restantes.
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
            /* Crear en caso necesario la rebanada correspondiente a
                las palabras restantes */
            val auxiliarAngulo = angle
            angle = calculateAngle(super.palabrasTotal - cumulated, cumulated)
            val auxiliar = makeSlice(angle, currentX, currentY, auxiliarAngulo)
            pie += auxiliar
            pie = pie.replaceFirst("COLOR".toRegex(), nextColorRGB)
            cumulated += super.palabrasTotal - cumulated
        }

        super.posicion += 20
        return pie
    }

    /*
     * Método que calcula el ángulo que tendrá una rebanada-
     * @param percent : porcentaje de ocurrencia de una palabra.
     * @param cumulated : número acumulado de palabras contadas
     * hasta el momento.
     * @return double que representa el ángulo de la nueva rebanada.
     */
    private fun calculateAngle(percent: Int, cumulated: Int): Double {
        val total = super.palabrasTotal

        return 2.0 * Math.PI * ((cumulated.toDouble() + percent.toDouble() + 0.0) / total)
    }
}