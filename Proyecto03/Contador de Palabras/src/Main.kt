import java.awt.EventQueue
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.*
import javax.swing.*
import java.util.*
import java.util.Scanner
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter
import javax.swing.filechooser.FileNameExtensionFilter


class InterfazGrafica : JFrame() {

    private val contentPane: JPanel
    private val textField: JTextField
    private val textArea: JTextArea
    private var nombres: LinkedList<String>? = null

    /**
     * Create the frame.
     */
    init {

        //Parametros asociados a la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setBounds(100, 100, 600, 300)
        contentPane = JPanel()
        contentPane.setLayout(null)
        setContentPane(contentPane)

        textField = JTextField()
        textField.setToolTipText("Inserta la ruta del fichero de audio")
        textField.setBounds(52, 26, 209, 20)
        contentPane.add(textField)
        textField.setColumns(10)

        val btnSeleccionar = JButton("Seleccionar...")
        btnSeleccionar.setBounds(288, 25, 109, 23)
        contentPane.add(btnSeleccionar)

        val bContar = JButton("Graficar")
        bContar.setBounds(410, 25, 109, 23)
        contentPane.add(bContar)

        textArea = JTextArea()
        textArea.setLineWrap(true)
        textArea.setWrapStyleWord(true)
        textArea.setBounds(52, 76, 300, 156)

        val scroll = JScrollPane(textArea)
        scroll.setBounds(52, 76, 360, 156)
        contentPane.add(scroll)

        /*
	 *Acciones del boton "Seleccionar"
	 *abre un buscador de archivos, permite la seleccion multiple de archivos
	 *y muestra su ruta total en un area de texto
	 */
        btnSeleccionar.addActionListener(object : ActionListener() {
            fun actionPerformed(e: ActionEvent) {
                //Scanner entrada = null;
                //Se crea el JFileChooser. Se le indica que la ventana se abra en el directorio actual
                val fileChooser = JFileChooser(".")
                //Se crea el filtro. El primer parámetro es el mensaje que se muestra,
                //el segundo es la extensión de los ficheros que se van a mostrar
                val filtro = FileNameExtensionFilter("Archivos txt (.txt)", "txt")
                //Se le asigna al JFileChooser el filtro
                fileChooser.setFileFilter(filtro)
                //se muestra la ventana
                fileChooser.setMultiSelectionEnabled(true)
                val valor = fileChooser.showOpenDialog(fileChooser)
                if (valor == JFileChooser.APPROVE_OPTION) {
                    val files = fileChooser.getSelectedFiles()
                    val fileNames = LinkedList<String>()
                    for (file in files) {
                        fileNames.add(file.getAbsolutePath())
                    }
                    nombres = fileNames
                    textArea.setText("Archivo(s) seleccionados:  \n" + fileNames.toString())
                } else {
                    System.out.println("No se ha seleccionado ningún fichero")
                }
            }
        })

        /*
	 *Acciones del boton "Graficar"
	 *por el momento solo imprime la listaOrdenada de palabras de cada archivo
	 */
        bContar.addActionListener(object : ActionListener() {
            fun actionPerformed(e: ActionEvent) {
                //prueba de la interfaz con la clase Archivo
                val direc = Directorio(nombres)
                val l = direc.contador()
                //prueba de la interfaz con la clase Directorio
                val archivos = LinkedList<Archivo>()
                val palabras = LinkedList<Palabra>()

                for (s in nombres!!) {
                    archivos.add(Archivo(s))
                }

                try {
                    for (a in archivos) {
                        val pastel = GraficaPastel(a.contador(), 26, 0)
                        val barra = GraficaBarras(a.contador(), 26, 0)
                        GeneradorHTML.generaHTML(pastel, barra, direc.getRuta(), a.getNombre())
                    }

                    val index = IndexGenerador(nombres)
                    index.generaIndex(direc.getRuta())
                } catch (exc: Exception) {
                    System.out.println(exc)
                    System.exit(0)
                }

            }
        })
    }

    companion object {

        /**
         * Launch the application.
         */
        fun main(args: Array<String>) {
            EventQueue.invokeLater(object : Runnable() {
                fun run() {
                    try {
                        val frame = InterfazGrafica()
                        frame.setVisible(true)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            })
        }
    }
}
