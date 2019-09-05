using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    public abstract class Filtros
    {
        public Bitmap Copia(string archivo)
        {
            Bitmap imagen1 = new Bitmap(archivo);
            Bitmap imagen2 = (Bitmap)imagen1.Clone();

            return imagen2;
        }

        abstract public void AplicaFiltro(Bitmap imagen);
    }
}
