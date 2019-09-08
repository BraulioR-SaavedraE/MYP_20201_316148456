using NUnit.Framework;
using System.Drawing;

namespace PruebasAzules
{
    public class TestAzul
    {

        [Test]
        public void RojoYVerdeCero
        {
            FiltroAzul filtro = new FiltroAzul();
            Bitmap imagen = filtro.Copia();
            filtro.AplicaFiltro(imagen);

            for (int i = 0; i < imagen.Width; i++)
            {
                for (int j = 0; j < imagen.Height; j++)
                {
                    Color pixelColor = imagen.GetPixel(i, j);
                    if (pixelColor.R != 0 || pixelColor.G != 0)
                        Assert.Fail();
                }
            }
            Assert.Pass();
        }
    }
}