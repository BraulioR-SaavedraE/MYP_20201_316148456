using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    /// <summary>
    /// Clase que crea un filtro azul aplicable a una imagen.
    /// </summary>
    public class FiltroAzul : Filtros
    {
        /// <summary>
        /// Método que aplica un filtro azul a una imagen dada.
        /// </summary>
        /// <param name="imagen">Representación Bitmap de una imagen.</param>
        /// <exception cref="System.NullReferenceException">Lanza una excepción en caso
        /// de que la altura y anchura de la imagen sean menores o iguales a cero.</exception>
        override
         public void AplicaFiltro(Bitmap imagen)
        {
            int x, y;
            if (imagen.Width > 0 && imagen.Height > 0)
            {
                for (x = 0; x < imagen.Width; x++)
                {
                    for (y = 0; y < imagen.Height; y++)
                    {
                        Color pixelC = imagen.GetPixel(x, y);
                        
                        Color newColor = Color.FromArgb(0, 0, pixelC.B);
                        imagen.SetPixel(x, y, newColor);
                    }
                }                
            }
            else
            {
                throw new System.NullReferenceException();
            }
    }
}
}
