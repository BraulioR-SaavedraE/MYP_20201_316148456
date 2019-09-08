using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace Filtros.Programa.Aplicación
{
    public class FiltroMosaico : Filtros
    {
        override
        public void AplicaFiltro(Bitmap image)
        {
            for (int i = 0; i < image.Height && i < image.Height; i += 4)
            {
                for (int j = 0; j < image.Width && j < image.Width; j += 4)
                {
                    List<Color> colorMatriz = new List<Color>();

                    for (int k = i; k < i + 4 && k < image.Height; k++)
                    {
                        for (int l = j; l < j + 4 && l < image.Width; l++)
                        {
                            colorMatriz.Add(image.GetPixel(l, k));
                        }
                    }

                    int rojoPromedio = colorMatriz.Aggregate(0, (current, color) => current + color.R) / colorMatriz.Count;
                    int verdePromedio = colorMatriz.Aggregate(0, (current, color) => current + color.G) / colorMatriz.Count;
                    int azulPromedio = colorMatriz.Aggregate(0, (current, color) => current + color.B) / colorMatriz.Count;

                    Color colorPromedio = Color.FromArgb(rojoPromedio, verdePromedio, azulPromedio);

                    for (int k = i; k < i + 4 && k < image.Height; k++)
                    {
                        for (int l = j; l < j + 4 && l < image.Width; l++)
                        {
                            image.SetPixel(l, k, colorPromedio);
                        }
                    }
                }
            }
        }
    }
}
