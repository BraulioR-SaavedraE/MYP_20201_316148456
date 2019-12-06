package Main

import java.util.*

/**
 * Clase que crea un objeto llamado Palabra que tiene como atributos
 * una String que representa a la palabra, y un entero que equivale
 * a la ocurrencia de la palabra dentro de un archivo.
 */
class Palabra(var palabra: String, numero: Int) : Comparator<Palabra>, Comparable<Palabra> {
    var cantidad: Int = numero

    /**
     * Método que da la representación en cadena
     * de una palabra
     * @return String representación en cadena
     * de una palabra, esto es: la palabra concatenada
     * y separada por dos puntos de su ocurrencia.
     */
    override fun toString(): String {
        return "$palabra : $cantidad"
    }

    /**
     * Método que da la relación para comparar dos palabras.
     * En orden descendente.
     * @param other : palabra a comparar con this.
     * @return un entero que representa la relación entre las dos palabras
     */
    override fun compareTo(other: Palabra): Int {
        return if (this.cantidad > other.cantidad) {
            -1
        } else if (this.cantidad == other.cantidad) {
            0
        } else {
            1
        }
    }

    /**
     * Método que da la relación para comparar dos palabras.
     * @param a : palabra a comparar con b
     * @return un entero que representa la relación entre las dos palabras
     */
    override fun compare(a: Palabra, b: Palabra): Int {
        return b.cantidad - a.cantidad
    }

    /**
     * Método que da un entero representante de las ocurrencias de una palabra
     * en un archivo de texto.
     * @return la cantidad de veces que una palabra aparece en un archivo.
     */
    fun getCantidadX() : Int {
        return cantidad
    }
}
