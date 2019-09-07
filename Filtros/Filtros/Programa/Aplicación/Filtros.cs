using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    public abstract class Filtros
    {
        public Bitmap Copia(string archivo)

        {
            if(archivo != null)
            {
                Bitmap imagen1 = new Bitmap(archivo);
                Bitmap imagen2 = (Bitmap)imagen1.Clone();

                return imagen2;
                
            }
            else
            {
                throw new System.ArgumentException();
            }
           
            
            
        }

        abstract public Bitmap AplicaFiltro(Bitmap imagen);
    }
}
