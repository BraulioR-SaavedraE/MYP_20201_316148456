using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    /// <summary>
    /// Clase que crea filtros rojos para aplicarlos en una imagen.
    /// </summary>
    public class FiltroRojo : Filtros
    {
        /// <summary>
        /// Método que aplica un filtro rojo a una imagen.
        /// </summary>
        /// <param name="imagen">Imagen representada por un objeto Bitmap</param>
        override
        public void AplicaFiltro(Bitmap imagen)
        {

            for (int x = 0; x < imagen.Width; x++)
            {
                for (int y = 0; y < imagen.Height; y++)
                {

                    Color pixelColor = imagen.GetPixel(x, y);
                    Color newColor = Color.FromArgb(pixelColor.R, 0, 0);
                    imagen.SetPixel(x, y, newColor);
                }
            }            
        }
    }
}
