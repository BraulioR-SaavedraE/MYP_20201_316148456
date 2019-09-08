using NUnit.Framework;
using System.Drawing;
using Filtros.Programa.Aplicación;

namespace PruebasVerdes
{
    public class TestVerde
    {
        [Test]
        public void RojoYAzulCero()
        {
            
        }

        [Test]
        public void Test1()
        {
            FiltroVerde filtro = new FiltroVerde();
            Bitmap imagen = filtro.Copia();
            filtro.AplicaFiltro(imagen);

            for (int i = 0; i < imagen.Width; i++)
            {
                for (int j = 0; j < imagen.Height; j++)
                {
                    Color pixelColor = imagen.GetPixel(i, j);
                    if (pixelColor.R != 0 || pixelColor.B != 0)
                        Assert.Fail();
                }
            }
            Assert.Pass();
        }
    }
}