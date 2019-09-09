using NUnit.Framework;
using System;
using System.Drawing;
using Filtros.Programa.Aplicación;


namespace PruebasAzules
{
    public class TestAzul
    {
        [Test]
        public void RojoYVerdeCero()
        {
            FiltroAzul filtro = new FiltroAzul();
            Bitmap imagen = filtro.Copia(@"C:\Users\resea\Desktop\Repositorio\Filtros\Pruebas Filtro Azul\Recursos\Bobby_Carrot.jpg");
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

        [Test]
        public void AplicaFiltroEnTiempo()
        {
            DateTime inicio = DateTime.UtcNow;
            FiltroVerde filtro = new FiltroVerde();
            filtro.AplicaFiltro(filtro.Copia(@"C:\Users\resea\Desktop\Repositorio\Filtros\Pruebas Filtro Azul\Recursos\Bobby_Carrot_Forever2.png"));
            DateTime fin = DateTime.UtcNow;
            int tiempoTranscurrido = fin.Minute - inicio.Minute;
            if (tiempoTranscurrido > 1)
                Assert.Fail();

            Assert.Pass();
        }
    }
}