import java.util.*

class Directorio
/**
 * Constructor de la clase
 * @param lista de cadenas con las rutas de cada archivo
 */
(rutas: LinkedList<String>?) {

    /**
     * Obtener la lista de archivos
     * @return lista de archivos
     */
    var listaArchivos: LinkedList<Archivo>? = null
        private set
    /**
     * Obtener la ruta del directorio donde estan los archivos
     * @retunr cadena con la ruta del directorio
     */
    var ruta: String? = null
        private set

    init {
        if (rutas != null) {
            val iterador = rutas!!.iterator()
            val listaA = LinkedList<Archivo>()
            var elemento: String
            var doc: Archivo
            var indice = 0
            while (iterador.hasNext()) {
                elemento = iterador.next()
                doc = Archivo(elemento)
                listaA.add(doc)
            }
            indice = rutas!!.element().lastIndexOf("/")

            this.ruta = rutas!!.element().substring(0, indice)
            this.listaArchivos = listaA
        } else {
            System.out.println("Cadena vacia")
        }
    }


    /**
     * Cuenta las palabras que estan en cada archivo
     * @return lista de listas de palabras
     * cada lista pertenece a un archivo
     */
    fun contador(): LinkedList<???> {
        val listaFinal = LinkedList<LinkedList<Palabra>>()
        val iterador = listaArchivos!!.iterator()
        var doc: Archivo
        while (iterador.hasNext()) {
            doc = iterador.next() as Archivo
            listaFinal.add(doc.contador())
        }
        return listaFinal
    }
}

