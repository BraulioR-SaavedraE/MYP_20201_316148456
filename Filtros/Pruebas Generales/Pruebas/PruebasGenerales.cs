using NUnit.Framework;
using System;
using Filtros.Programa.Aplicación;

namespace PruebasGenerales
{
    public class TestGeneral
    {
        [Test]
        public void RutaEquivocada()
        {
            try
            {
                FiltroRojo filtro = new FiltroRojo();
                filtro.Copia("RUTAINEXISTENTE");
            }
            catch (ArgumentException)
            {
                Assert.Pass();
            }
            Assert.Fail();
        }

        [Test]
        public void ArchivoInvalido()
        {
            try
            {
                FiltroRojo filtro = new FiltroRojo();
                filtro.Copia(@"C:\Users\resea\Desktop\Repositorio\Filtros\Pruebas Generales\Recursos\alerta_sismica.mp3");
            }
            catch (ArgumentException)
            {
                Assert.Pass();
            }
            Assert.Fail();
        }
    }
}