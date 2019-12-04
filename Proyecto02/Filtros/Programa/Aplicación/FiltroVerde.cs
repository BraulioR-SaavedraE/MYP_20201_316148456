using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    /// <summary>
    /// Crea objetos para aplicar filtros verdes a imágenes.
    /// </summary>
    public class FiltroVerde : Filtros
    {
        /// <summary>
        /// Método que aplica un filtro verde a una imagen.
        /// </summary>
        /// <param name="imagen">Imagen representada en un objeto Bitmap</param>
        override
         public void AplicaFiltro(Bitmap imagen)
        {
            int x, y;
            for (x = 0; x < imagen.Width; x++)
            {
                for (y = 0; y < imagen.Height; y++)
                {
                    Color pixelC = imagen.GetPixel(x, y);
                    Color newColor = Color.FromArgb(0, pixelC.G, 0);
                    imagen.SetPixel(x, y, newColor);
                }
            }
        }
    }
}
