using NUnit.Framework;
using System;
using System.Drawing;
using System.IO;
using Filtros.Programa.Aplicación;


namespace PruebasVerdes
{
    public class TestVerde
    {
        [Test]
        public void RojoYAzulCero()
        {
            FiltroVerde filtro = new FiltroVerde();
            Bitmap imagen = filtro.Copia(@"C:\Users\resea\Desktop\Repositorio\MYP_20201_316148456\Filtros\Pruebas Filtro Verde\Recursos\Bobby_Carrot.jpg");
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

        [Test]
        public void AplicaFiltroEnTiempo()
        {
            DateTime inicio = DateTime.UtcNow;
            FiltroVerde filtro = new FiltroVerde();
            filtro.AplicaFiltro(filtro.Copia(@"C:\Users\resea\Desktop\Repositorio\MYP_20201_316148456\Filtros\Pruebas Filtro Verde\Recursos\Bobby_Carrot.jpg"));
            DateTime fin = DateTime.UtcNow;
            int tiempoTranscurrido = fin.Minute - inicio.Minute;
            if (tiempoTranscurrido > 1)
                Assert.Fail();

            Assert.Pass();
        }
    }
}