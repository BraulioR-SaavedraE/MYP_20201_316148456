import java.util.*

class Palabra : Comparator<Palabra>, Comparable<Palabra> {
    val palabra: String
    val cantidad: Int

    internal constructor() {}

    constructor(palabra: String, numero: Int) {
        this.palabra = palabra
        this.cantidad = numero
    }

    fun toString(): String {
        return "$palabra : $cantidad"
    }

    //orden descendiente segun la cantidad
    @Override
    fun compareTo(p: Palabra): Int {
        return if (this.cantidad > p.cantidad) {
            -1
        } else if (this.cantidad == p.cantidad) {
            0
        } else {
            1
        }
    }

    //orden descendiente segun la cantidad
    @Override
    fun compare(a: Palabra, b: Palabra): Int {
        return b.cantidad - a.cantidad
    }
}
