import java.awt.EventQueue
import java.util.*
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter


class InterfazGrafica:JFrame() {

private val contentPane:JPanel
private val textField:JTextField
private val textArea:JTextArea
private var nombres:LinkedList<String>? = null
/**
 * Create the frame.
 */
    init{

 //Parametros asociados a la ventana
    defaultCloseOperation = JFrame.EXIT_ON_CLOSE
setBounds(100, 100, 600, 300)
contentPane = JPanel()
    contentPane.layout = null
setContentPane(contentPane)

textField = JTextField()
    textField.toolTipText = "Dame el número de rebanadas para las gráficas"
textField.setBounds(52, 26, 209, 20)
contentPane.add(textField)
    textField.columns = 10

val btnSeleccionar = JButton("Seleccionar...")
btnSeleccionar.setBounds(288, 25, 109, 23)
contentPane.add(btnSeleccionar)

val bContar = JButton("Graficar")
bContar.setBounds(410, 25, 109, 23)
contentPane.add(bContar)

textArea = JTextArea()
    textArea.lineWrap = true
    textArea.wrapStyleWord = true
textArea.setBounds(52, 76, 300, 156)

val scroll = JScrollPane(textArea)
scroll.setBounds(52, 76, 360, 156)
contentPane.add(scroll)

 /*
	 *Acciones del boton "Seleccionar"
	 *abre un buscador de archivos, permite la seleccion multiple de archivos
	 *y muestra su ruta total en un area de texto
	 */
        btnSeleccionar.addActionListener {
            //Scanner entrada = null;
            //Se crea el JFileChooser. Se le indica que la ventana se abra en el directorio actual
            val fileChooser = JFileChooser(".")
            //Se crea el filtro. El primer parámetro es el mensaje que se muestra,
            //el segundo es la extensión de los ficheros que se van a mostrar      
            val filtro = FileNameExtensionFilter("Archivos txt (.txt)", "txt")
            //Se le asigna al JFileChooser el filtro
            fileChooser.fileFilter = filtro
            //se muestra la ventana
            fileChooser.isMultiSelectionEnabled = true
            val valor = fileChooser.showOpenDialog(fileChooser)
            if (valor == JFileChooser.APPROVE_OPTION) {
                val files = fileChooser.selectedFiles
                val fileNames = LinkedList<String>()
                for (file in files) {
                    fileNames.add(file.absolutePath)
                }
                nombres = fileNames
                textArea.text = "Archivo(s) seleccionados:  \n$fileNames"
            } else {
                println("No se ha seleccionado ningún fichero")
            }
        }

    /*
        *Acciones del boton "Graficar"
        *por el momento solo imprime la listaOrdenada de palabras de cada archivo
        */
	bContar.addActionListener {
        //prueba de la interfaz con la clase Archivo
        val direc = Directorio(nombres)
        //prueba de la interfaz con la clase Directorio
        val archivos = LinkedList<Archivo>()
        val palabras = LinkedList<Palabra>()

        for (s in nombres!!) {
            archivos.add(Archivo(s))
        }

        try {
            for (a in archivos) {
                var numero = 0

                if (archivos.size == 1)
                    try{
                        numero = Integer.parseInt(textField.getText())
                    }catch(error:NumberFormatException) {
                        System.out.println("¡Inserte un número!")
                    }
                else
                    numero = ((a.listaOrdenada(a.tabla).size.toDouble() / 2.toDouble()) + 1.toDouble()).toInt()

                val pastel = GraficaPastel(a.contador(), numero, 0)
                val barra = GraficaBarras(a.contador(), numero, 0)
                direc.ruta?.let { it1 -> GeneradorHTML.generaHTML(pastel, barra, it1, a.nombre) }
                JOptionPane.showMessageDialog(null, "Tus archivos han sido generados con éxito");
            }

            val index = IndexGenerador(nombres!!)
            direc.ruta?.let { it1 -> index.generaIndex(it1) }
        } catch (exc:Exception) {
            JOptionPane.showMessageDialog(null, exc);
        }
    }
}

companion object {

/**
 * Launch the application.
 */
    @JvmStatic  fun main(args:Array<String>) {
EventQueue.invokeLater(object:Runnable {
override fun run() {
try
{
val frame = InterfazGrafica()
    frame.isVisible = true
}
catch (e:Exception) {
e.printStackTrace()
}

}
})
}
}
}

