package mx.unam.ciencias.edd.proyecto3;

public class SVGCodigo{

	public String path(double x1, double y1, double x2, double y2, double cx, double cy, double radio, String color, String extra) {
        String path = "<path ";
        path += "d='M" + cx + ", " + cy + " L" + x1 + "," + y1 + " A" + radio + "," + radio;
        path += " 0 0, 1 " + x2 + "," + y2 + " z' fill='"+ color +"' " + extra + ">";
        return path + "</path>";
    }
}