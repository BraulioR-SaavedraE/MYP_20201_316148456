import java.util.LinkedList

object Contador {

    /**
     * Crea una cadena con las palabras
     * y el numero de veces que parecen en el archivo
     * @return cadena con las palabras en orden descendiente segun el numero de veces que aparecen
     */
    fun reporte(lista: LinkedList<Palabra>): String {
        var reporte = "Reporte del conteo de palabras\n"
        reporte += "El total de palabras en el archivo es de " + totalPalabras(lista) + "\n"

        for (x in lista) {
            val enunciado = "La palabra \""
            reporte += enunciado + x.palabra + "\" aparece " + x.cantidad + " veces\n"
        }

        return reporte
    }

    fun totalPalabras(lista: LinkedList<Palabra>): Int {
        var total = 0

        for (x in lista)
            total += x.cantidad

        return total
    }
}