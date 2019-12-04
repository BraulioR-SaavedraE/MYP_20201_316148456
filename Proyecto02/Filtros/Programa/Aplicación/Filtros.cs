using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    /*
    Clase abstracta para filtros.
    */
    /// <summary>
    /// Contiene un método para Copiar una imagen ya implementado y otro para aplicar
    /// filtros que es abstracto.
    /// </summary>
    public abstract class Filtros
    {
        /// <summary>
        /// Copia una imagen-
        /// </summary>
        /// <param name="archivo">Ruta de localización de la imagen</param>
        /// <exception cref="System.ArgumentException">Arroja una excepción cuando 
        /// encuentra un problema al intentar abrir el archivo.</exception>
        /// <returns>Imagen representada en un objeto Bitmap</returns>
        public Bitmap Copia(string archivo)
        {
            if(archivo != null)
            {
                Bitmap imagen1 = new Bitmap(archivo);
                Bitmap imagen2 = new Bitmap(imagen1);

                return imagen2;   
            }
            else
            {
                throw new System.ArgumentException();
            }
        }

        /// <summary>
        /// Método abstracto para aplicarle un filtro a una imagen.
        /// </summary>
        /// <param name="imagen">Representación de una imagen con un objeto Bitmap.</param>
        abstract public void AplicaFiltro(Bitmap imagen);
    }
}
