import Main.Archivo
import Main.Colores
import Main.Contador
import Main.Grafica
import Main.NumeroPalabrasInvalidoException
import Main.Palabra

import java.util.LinkedList
import java.util.Random
import javax.swing.JOptionPane

/*
 * Clase para probar el Graficador de Palabras
 */
object Test {
    private var aciertos = 0
    private val random = Random()
    private val RGBTOTAL = 16777216    // Es el número de colores RGB que existen (256^3)

    /*
	 * La prueba pretende corroborar que al pedir un número negativo de colores
	 * se reporte un error
	 */
    private fun coloresNumeroInvalido() {

        val negativo = -1 * random.nextInt(200000)

        try {
            val colores = Colores(negativo)
        } catch (e: IllegalArgumentException) {
            aciertos++
        }

    }

    /*
	 * La prueba pretende pedir un número de rebanadas inválido a una gráfica
	 * ya sea un número negativo o un número que rebase la cantidad de palabras
	 * diferentes encontradas en un documento que son proporcionales a la cantidad
	 * de rebanadas
	 */
    private fun graficaNumeroInvalido() {

        var aleatorio = random.nextInt(2000) + 1
        val lista = LinkedList<Palabra>()

        for (i in 0 until aleatorio)
            lista.add(Palabra("hola", 1))

        if (aleatorio % 2 == 0)
            aleatorio = -1 * aleatorio
        else
            aleatorio *= 2

        try {
            val pastel = Grafica(lista, aleatorio, 0)
        } catch (ex: NumeroPalabrasInvalidoException) {
            aciertos++
        }

    }

    /*
	 * La prueba pretende verificar que siempre se da un color aunque el método dameColor()
	 * ya nos haya dado una cantidad de colores igual o mayor a la que guarda el HashSet
	 * de la instancia de Colores.
	 */
    private fun masColores() {
        val veces = random.nextInt(10) + 1
        val numeroColoresNecesario = veces * RGBTOTAL
        val numeroColores = random.nextInt(RGBTOTAL + 1)
        val colores = Colores(numeroColores)

        try {
            for (i in 0 until numeroColoresNecesario)
                colores.dameColor()

            aciertos++
        } catch (e: Exception) {
        }

    }

    /*
	 * La prueba pretende corroborar que no se cree una instancia de Archivo si
	 * se le da una ruta inexistente.
	 */
    private fun rutaInexistenteArchivo() {
        val aleatorio = random.nextInt()
        val falsa = Integer.toString(aleatorio)

        try {
            val archivo = Archivo(falsa)
        }catch(e:Exception) {
            aciertos++
        }
    }

    /*
	 * La prueba verifica que el contador cuenta exactamente
	 * el total de palabras en un archivo.
	 */
    private fun cuentaPalabras() {
        val aleatorio = random.nextInt(2000)
        val lista = LinkedList<Palabra>()

        var contador = 0
        var i = 0
        while (i < aleatorio) {
            lista.add(Palabra("hola", i))
            contador += i
            i++
        }

        if (Contador.totalPalabras(lista) == contador) {
            aciertos++
        }
    }

    /*
	 * Método main que corre las cinco pruebas unitarias preparadas en esta clase y
	 * reporta el número de ellas pasadas, esto mediante el despliegue de una ventana
	 */
    @JvmStatic
    fun main(args: Array<String>) {
        coloresNumeroInvalido()
        graficaNumeroInvalido()
        masColores()
        rutaInexistenteArchivo()
        cuentaPalabras()
        JOptionPane.showMessageDialog(null, String.format("Se pasaron %d pruebas unitarias de 5", aciertos))
    }
}