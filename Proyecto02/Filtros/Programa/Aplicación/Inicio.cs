using System;
using System.Windows.Forms;

namespace Filtros.Programa.Aplicación
{
    /// <summary>
    /// Clase principal del Programa Filtros.
    /// Se encarga de hacer correr la interfaz gráfica.
    /// </summary>
    static class Inicio
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new InterfazGrafica());
        }
    }
}
