import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.StringTokenizer
import java.util.*

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
    val tabla: Hashtable<String, Integer>
    /**
     * Obtener el nombre del archivo
     * @return cadena con el nombre
     */
    val nombre: String

    init {
        try {
            this.archivo = BufferedReader(FileReader(ruta))
        } catch (e: Exception) {
            System.out.println("No se encontro el archivo")
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
            normal = normal.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
            //poner en minusculas
            normal = normal.toLowerCase()
            //quitar puntuacion de la palabra
            normal = normal.replaceAll("\\p{Punct}", "")
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
    private fun tabla(): Hashtable<String, Integer> {
        val dicc = Hashtable<String, Integer>()
        var linea: String? = null
        var palabra: String? = null
        var sToken: StringTokenizer
        var contador: Integer
        try {
            while ((linea = archivo!!.readLine()) != null) {
                sToken = StringTokenizer(linea)
                while (sToken.hasMoreTokens()) {
                    //leer por palaras
                    palabra = sToken.nextToken()
                    palabra = normalizarString(palabra)
                    if (dicc.containsKey(palabra)) {
                        contador = dicc.get(palabra)
                        dicc.replace(palabra, contador + 1)
                    } else {
                        dicc.put(palabra, 1)
                    }
                }
            }
        } catch (e: IOException) {
            System.out.println("Documento vacio")
        }

        return dicc
    }

    /**
     * Metodo auxiliar crea una lista de objeto Palabra
     * Palabra contiene un string con la palabra
     * y un int con el numero de veces que aparece esa palabra en el documento
     * @param tabla Hashtable
     * @return lista de objetos palabra en orden descendiente segun el entero que contiene el objeto Palabra
     */
    private fun listaOrdenada(tabla: Hashtable<String, Integer>): LinkedList<Palabra> {
        val lista = LinkedList<Palabra>()
        val llave = tabla.keys()
        var cadena: String
        while (llave.hasMoreElements()) {
            cadena = llave.nextElement()
            val palabra = Palabra(cadena, tabla.get(cadena))
            lista.add(palabra)

        }
        Collections.sort(lista, Palabra())
        return lista
    }

    /**
     * Cuenta e numero de veces que aparecen las palabras en el documento
     * @retunr lista ordenada de manera descendiente segun el numero de veces que aparecen en el documento
     */
    fun contador(): LinkedList<Palabra> {
        return listaOrdenada(tabla)
    }

    fun totalPalabras(): Int {
        val llave = this.tabla.keys()
        var contador = 0
        while (llave.hasMoreElements()) {
            contador += this.tabla.get(llave.nextElement())
        }
        return contador
    }
}
