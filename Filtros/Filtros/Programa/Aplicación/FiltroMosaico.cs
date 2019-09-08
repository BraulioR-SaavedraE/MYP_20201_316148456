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


            // Loop through the image in 4x4 cells.
            for (var i = 0; i < image.Height && i < image.Height; i += 4)
            {
                for (var j = 0; j < image.Width && j < image.Width; j += 4)
                {
                    var cellColors = new List<Color>();

                    // Store each color from the 4x4 cell into cellColors.
                    for (var k = i; k < i + 4 && k < image.Height; k++)
                    {
                        for (var l = j; l < j + 4 && l < image.Width; l++)
                        {
                            cellColors.Add(image.GetPixel(l, k));
                        }
                    }

                    // Get the average red, green, and blue values.
                    var averageRed = cellColors.Aggregate(0, (current, color) => current + color.R) / cellColors.Count;
                    var averageGreen = cellColors.Aggregate(0, (current, color) => current + color.G) / cellColors.Count;
                    var averageBlue = cellColors.Aggregate(0, (current, color) => current + color.B) / cellColors.Count;

                    var averageColor = Color.FromArgb(averageRed, averageGreen, averageBlue);

                    // Go BACK over the 4x4 cell and set each pixel to the average color.
                    for (var k = i; k < i + 4 && k < image.Height; k++)
                    {
                        for (var l = j; l < j + 4 && l < image.Width; l++)
                        {
                            image.SetPixel(l, k, averageColor);
                        }
                    }
                }
            }
        }
    }
}
