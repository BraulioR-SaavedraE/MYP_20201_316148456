using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Filtros.Programa.Aplicación
{
    public partial class InterfazGrafica : Form
    {
        public InterfazGrafica()
        {
            InitializeComponent();
        }

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

       
        private void Button5_Click(object sender, EventArgs e)
        {
            try
            { 
                string imagenOriginal = openFileDialog1.FileName;
                FiltroAzul image = new FiltroAzul();
                Bitmap filtro = image.Copia(imagenOriginal);            
                Bitmap imagenSalida = image.AplicaFiltro(filtro);
                pictureBox2.Image = imagenSalida;
            }
            catch (ArgumentException)
            {
                MessageBox.Show("Hubo un error" +
                    "Revisa la ruta de la imagen");
            }
            catch(NullReferenceException)
            {
                MessageBox.Show("La imagen es vacia");
            }

           
        }

        private void Button4_Click(object sender, EventArgs e)
        {
            try
            {
                string imagenOriginal = openFileDialog1.FileName;
                FiltroRojo image = new FiltroRojo();
                Bitmap filtro = image.Copia(imagenOriginal);
                Bitmap imagenSalida = image.AplicaFiltro(filtro);

                pictureBox2.Image = imagenSalida;
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

        private void Button3_Click(object sender, EventArgs e)
        {
            try
            {
                string imagenOriginal = openFileDialog1.FileName;
                FiltroVerde image = new FiltroVerde();
                Bitmap filtro = image.Copia(imagenOriginal);
                Bitmap imagenSalida = image.AplicaFiltro(filtro);

                pictureBox2.Image = imagenSalida;
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

        private void Button2_Click(object sender, EventArgs e)
        {
            try
            {
                string imagenOriginal = openFileDialog1.FileName;
                FiltroMosaico image = new FiltroMosaico();
                Bitmap filtro = image.copia(imagenOriginal);
                Bitmap imagenSalida = image.AplicaFiltro(filtro);

                pictureBox2.Image = imagenSalida;
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
