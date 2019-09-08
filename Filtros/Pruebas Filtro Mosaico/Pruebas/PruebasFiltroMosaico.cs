using NUnit.Framework;
using Filtros.Programa.Aplicación;
using System.Diagnostics;
using System.Drawing;
using System;


namespace PruebasMosaicos
{
    public class TestMosaico
    {
        [Test]
        public void MismoColor()
        {
            //Arrange
            string fuente = "C:\\Users\\LauraItzel\\Desktop\\RepoBuenisimo\\Filtros\\Pruebas Filtro Mosaico\\Recursos\\pruebaMosaico.jpg";
            FiltroMosaico filtro = new FiltroMosaico();
            Bitmap imagen = filtro.Copia(fuente);
            filtro.AplicaFiltro(imagen);
            bool aux = false;

            //Act
            for (int i = 0; i < imagen.Width; i++)
            {
                for (int j = 0; j < imagen.Height; j++)
                {
                    Color pixelColor = imagen.GetPixel(i, j);
                    Color pixelSiguienteColor1 = imagen.GetPixel(i + 1, j);
                    Color pixelSiguienteColor2 = imagen.GetPixel(i, j + 1);
                    Color pixelSiguienteColor3 = imagen.GetPixel(i + 1, j + 1);
                    if (pixelColor != pixelSiguienteColor1)
                    {
                        aux = true;
                    }
                    else
                    {
                        if (pixelColor != pixelSiguienteColor2)
                        {
                            aux = true;
                        }
                        else
                        {
                            if (pixelColor != pixelSiguienteColor3)
                                aux = true;
                        }
                    }
                    j += 1;
                }
                i += 1;
            }

            //Assert
            Assert.IsFalse(aux);
        }


        [Test]
        public void Tiempo()
        {
            //Arrange
            string fuente = "C:\\Users\\LauraItzel\\Desktop\\RepoBuenisimo\\Filtros\\Pruebas Filtro Mosaico\\Recursos\\pruebaMosaico.jpg";
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