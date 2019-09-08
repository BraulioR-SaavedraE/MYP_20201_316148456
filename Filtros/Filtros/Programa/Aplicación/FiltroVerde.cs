using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    public class FiltroVerde : Filtros
    {
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
