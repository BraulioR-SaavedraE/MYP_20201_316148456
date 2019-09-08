using NUnit.Framework;
using Filtros.Programa.Aplicación;
using System.Diagnostics;
using System.Drawing;
using System;

namespace PruebasRojas
{
    public class TestRojo
    {
        [Test]
        public void VerdeYAzulCero()
        {
            //Arrange
            string fuente = "C:\\Users\\LauraItzel\\Desktop\\RepoBuenisimo\\Filtros\\Pruebas Filtro Rojo\\Recursos\\pruebaRojo.jpg";
            FiltroRojo filtro = new FiltroRojo();
            Bitmap imagen = filtro.Copia(fuente);
            filtro.AplicaFiltro(filtro.Copia(fuente));
            bool aux = false;

            //Act
            for (int i = 0; i < imagen.Width; i++)
            {
                for (int j = 0; j < imagen.Height; j++)
                {
                    Color pixelColor = imagen.GetPixel(i, j);
                    if (pixelColor.B == 0 && pixelColor.G == 0)
                        aux = true;
                }
            }

            //Assert
            Assert.IsTrue(aux);
        }

        [Test]
        public void Tiempo()
        {
            //Arrange
            string fuente = "C:\\Users\\LauraItzel\\Desktop\\RepoBuenisimo\\Filtros\\Pruebas Filtro Rojo\\Recursos\\pruebaRojo2.jpg";
            FiltroRojo filtro = new FiltroRojo();

            Stopwatch Cronometro = new Stopwatch();

            //Act
            Cronometro.Start();
            filtro.AplicaFiltro(filtro.Copia(fuente));
            Cronometro.Stop();
            TimeSpan time = Cronometro.Elapsed;
            if (time.Minutes > 1)
            {
                Assert.Fail("Tu programa excede el tiempo limite");
            }


            //Assert
            Assert.Pass();
        }
    }
}