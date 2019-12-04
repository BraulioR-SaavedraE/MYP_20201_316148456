using System;
using System.Drawing;
using System.Windows.Forms;

namespace Filtros.Programa.Aplicación
{
    /// <summary>
    /// Interfaz gráfica para filtros.
    /// </summary>
    public partial class InterfazGrafica : Form
    {
        /// <summary>
        /// Constructor que da inicio a los componentes de la interfaz.
        /// </summary>
        public InterfazGrafica()
        {
            InitializeComponent();
        }

        /* Botón para cargar una imagen. */
        private void Button1_Click(object sender, EventArgs e)
        {

            using (OpenFileDialog dlg = new OpenFileDialog())
            {
                try
                {
                    if (openFileDialog1.ShowDialog() == DialogResult.OK)
                    {
                        string imagen = openFileDialog1.FileName;
                        pictureBox1.Image = Image.FromFile(imagen);
                    }   
                }
                catch (Exception)
                {
                    MessageBox.Show("El archivo seleccionado no es un tipo de imagen válido");
                }
            }
        }

        /* Botón para aplicar un filtro a una imagen y la deja pixeleada. */
        private void Button2_Click(object sender, EventArgs e)
        {
            try
            {
                string imagenOriginal = openFileDialog1.FileName;
                FiltroMosaico image = new FiltroMosaico();
                Bitmap filtro = image.Copia(imagenOriginal);
                image.AplicaFiltro(filtro);

                pictureBox2.Image = filtro;
            }

            catch (ArgumentException)
            {
                MessageBox.Show("Hubo un error" +
                    "Revisa la ruta de la imagen");
            }
            catch (NullReferenceException)
            {
                MessageBox.Show("La imagen es vacia");
            }
        }

        /* Botón para aplicar un filtro verde a una iamgen. */
        private void Button3_Click(object sender, EventArgs e)
        {
            try
            {
                string imagenOriginal = openFileDialog1.FileName;
                FiltroVerde image = new FiltroVerde();
                Bitmap filtro = image.Copia(imagenOriginal);
                image.AplicaFiltro(filtro);

                pictureBox2.Image = filtro;
            }
            catch (ArgumentException)
            {
                MessageBox.Show("Hubo un error" +
                    "Revisa la ruta de la imagen");
            }
            catch (NullReferenceException)
            {
                MessageBox.Show("La imagen es vacia");
            }
        }

        /* Botón para aplicar un filtro rojo a una imagen. */
        private void Button4_Click(object sender, EventArgs e)
        {
            try
            {
                string imagenOriginal = openFileDialog1.FileName;
                FiltroRojo image = new FiltroRojo();
                Bitmap filtro = image.Copia(imagenOriginal);
                image.AplicaFiltro(filtro);

                pictureBox2.Image = filtro;
            }

            catch (ArgumentException)
            {
                MessageBox.Show("Hubo un error" +
                    "Revisa la ruta de la imagen");
            }
            catch (NullReferenceException)
            {
                MessageBox.Show("La imagen es vacia");
            }
        }

        /* Botón para aplicarle un filtro azul a una imagen. */
        private void Button5_Click(object sender, EventArgs e)
        {
            try
            {
                string imagenOriginal = openFileDialog1.FileName;
                FiltroAzul image = new FiltroAzul();
                Bitmap filtro = image.Copia(imagenOriginal);
                image.AplicaFiltro(filtro);
                pictureBox2.Image = filtro;
            }
            catch (ArgumentException)
            {
                MessageBox.Show("Hubo un error" +
                    "Revisa la ruta de la imagen");
            }
            catch (NullReferenceException)
            {
                MessageBox.Show("La imagen es vacia");
            }
        }
    }
}
