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
            Bitmap imagen = filtro.Copia(@"C:\Users\resea\Desktop\Repositorio\MYP_20201_316148456\Filtros\Pruebas Filtro Azul\Recursos\Bobby_Carrot.JPG");
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
            FiltroAzul filtro = new FiltroAzul();
            filtro.AplicaFiltro(filtro.Copia(@"C:\Users\resea\Desktop\Repositorio\MYP_20201_316148456\Filtros\Pruebas Filtro Azul\Recursos\Bobby_Carrot.JPG"));
            DateTime fin = DateTime.UtcNow;
            int tiempoTranscurrido = fin.Minute - inicio.Minute;
            if (tiempoTranscurrido > 1)
                Assert.Fail();
        }
    }
}