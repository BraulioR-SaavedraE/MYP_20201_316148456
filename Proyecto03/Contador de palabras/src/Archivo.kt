package Main

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.StringTokenizer
import java.util.*
import java.util.LinkedList


class Archivo
/**
 * Constructor de la clase
 * @param ruta del documento
 */
    (ruta: String) {

    private var archivo: BufferedReader? = null
    /**
     * regresa una tabla Hash con las palabras que aparecen en el documento
     * y el numero de veces que aparecen
     * @return Hashtable
     */
    var tabla: Hashtable<String, Int>
    /**
     * Obtener el nombre del archivo
     * @return cadena con el nombre
     */
    var nombre: String

    init {
        try {
            this.archivo = BufferedReader(FileReader(ruta))
        } catch (e: Exception) {
            throw Exception()
        }

        this.tabla = this.tabla()
        val indice = ruta.lastIndexOf("/")
        nombre = ruta.substring(indice + 1)
    }


    /**
     * Metodo auxiliar que normaliza una cadena
     * @param cadena a normalizra
     * @return cadena normalizada
     */
    private fun normalizarString(cadena: String?): String? {
        var normal: String? = null
        if (cadena != null) {
            normal = cadena
            //Quitar caracteres no ASCII
            normal = normal.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            //poner en minusculas
            normal = normal.toLowerCase()
            //quitar puntuacion de la palabra
            normal = normal.replace("\\p{Punct}".toRegex(), "")
        }
        return normal
    }

    /**
     * Recorre el documento
     * las palabras que encuentra son las llaves de una tabla Hash
     * antes de agrgar una llave a la tabla la normaliza
     * el numero de veces que aparece la palabra es el elemento que guarda la llave
     * @return tabla Hash
     */
    private fun tabla(): Hashtable<String, Int> {
        val dicc = Hashtable<String, Int>()
        var linea: String? = null
        var palabra: String?
        var sToken: StringTokenizer
        var contador: Int?

        try{
            while ({ linea = archivo?.readLine(); linea }() != null) {
                sToken = StringTokenizer(linea)
                while (sToken.hasMoreTokens()) {
                    //leer por palaras
                    palabra = sToken.nextToken()
                    palabra = normalizarString(palabra)
                    if (dicc.containsKey(palabra)) {
                        contador = dicc[palabra]
                        dicc.replace(palabra, contador!! + 1)
                    } else {
                        dicc[palabra] = 1
                    }
                }
            }}
        catch (e: IOException) {
            println("Documento vacio")
        }

        return dicc
    }

    /**
     * Metodo auxiliar crea una lista de objeto Main.Palabra
     * Main.Palabra contiene un string con la palabra
     * y un int con el numero de veces que aparece esa palabra en el documento
     * @param tabla Hashtable
     * @return lista de objetos palabra en orden descendiente segun el entero que contiene el objeto Main.Palabra
     */
    fun listaOrdenada(tabla: Hashtable<String, Int>): LinkedList<Palabra> {
        val lista = LinkedList<Palabra>()

        tabla.forEach { k, v -> lista.add(Palabra(k, v.toInt())) }

        return lista
    }

    /**
     * Cuenta e numero de veces que aparecen las palabras en el documento
     * @return lista ordenada de manera descendiente segun el numero de veces que aparecen en el documento.
     */
    fun contador() : LinkedList<Palabra>{
        return listaOrdenada(tabla)
    }

    /**
     * Método que cuenta las palabras totales en un documento
     * æreturn cantidad de palabras totales en el documento.
     */
    fun totalPalabras(): Int {
        val iterador = contador().listIterator(0)
        var total = 0
        while(iterador.hasNext()) {
            var auxiliar = iterador.next().cantidad
            total.plus(auxiliar)
        }
        return total
    }
}