package Main

import java.util.*

class Palabra : Comparator<Palabra>, Comparable<Palabra> {
    var palabra: String = ""
    var cantidad: Int = 0




    constructor(palabra: String, numero: Int) {
        this.palabra = palabra
        this.cantidad = numero
    }


    override fun toString(): String {
        return "$palabra : $cantidad"
    }

    //orden descendiente segun la cantidad
    override fun compareTo(p: Palabra): Int {
        return if (this.cantidad > p.cantidad) {
            -1
        } else if (this.cantidad == p.cantidad) {
            0
        } else {
            1
        }
    }

    //orden descendiente segun la cantidad
    override fun compare(a: Palabra, b: Palabra): Int {
        return b.cantidad - a.cantidad
    }

    fun getCantidadX() : Int {
        return cantidad
    }

}
