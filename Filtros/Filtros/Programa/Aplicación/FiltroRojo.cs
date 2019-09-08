using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    public class FiltroRojo : Filtros
    {

        override
        public void AplicaFiltro(Bitmap imagen)
        {

            for (int x = 0; x < imagen.Width; x++)
            {
                for (int y = 0; y < imagen.Height; y++)
                {

                    Color pixelColor = imagen.GetPixel(x, y);
                    //if(pixelColor.R == 0)
                    //  throw new ArgumentException();

                    Color newColor = Color.FromArgb(pixelColor.R, 0, 0);
                    imagen.SetPixel(x, y, newColor);
                }
            }            
        }
    }
}
