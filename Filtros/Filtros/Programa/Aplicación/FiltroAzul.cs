using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Threading.Tasks;

namespace Filtros.Programa.Aplicación
{
    public class FiltroAzul : Filtros
    {
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
