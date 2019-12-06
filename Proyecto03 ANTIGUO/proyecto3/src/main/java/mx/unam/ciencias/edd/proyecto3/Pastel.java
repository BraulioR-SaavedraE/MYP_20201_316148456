package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;

public class Pastel {
    private static final String [] colores = {"#2E64FE","#B40404","#088A08","#DBA901","#7401DF","#424242","#E3F6CE","#FF4000",
    "#04B4AE","#2A0A12","#D8D8D8","#151515","#01DF74","#0B2161","#3ADF00"};
    private static final String CUADRO = "<rect x='10' y='yede' height='20' width='20' fill='colorillox' stroke='transparent' stroke-width='2'/>\n";
    private static final String TEXTO = "<text x='40' y='yequis' fill='black' font-family='sans-serif' font-size='10' </text>textin</text>";
    private static int coordenadaEquis = 10;
    private static int coordenadaYe = 400;

    private static double[] getCoordenadas(double cx, double cy, double radio, double angulo) {
        double[] coordenadas = new double[2];
        angulo = Math.toRadians(angulo);
        coordenadas[0] = cx + radio * Math.cos(angulo);
        coordenadas[1] = cy + radio * Math.sin(angulo);
        return coordenadas;
    }

    private static String getPedazo(double angulo1, double angulo2, double cx, double cy, double radio, String color) {
        SVGCodigo svgUtil = new SVGCodigo();
        double[] co1 = getCoordenadas(cx, cy, radio, angulo1);
        double[] co2 = getCoordenadas(cx, cy, radio, angulo2);
        String extra = "stroke='#fff' stroke-width='2'";
        return svgUtil.path(co1[0], co1[1], co2[0], co2[1], cx, cy, radio, color, extra);
    }

    public static String generarPastel(double cx, double cy, double radio, Lista<Palabra> col, int total) {
        int i = 0;
        double porcentaje, angulo, anguloi = 0, angulof = 0;
        String svg = "";
        int corredor = 0;
        double porciento = 0;
        boolean confirmo = true;
        svg += "<svg width='500' height='800'>";
        for (Palabra palabra: col) {
            if(corredor >= 5){
                i = 14;
            }else{
                i++;
            }
            porciento += palabra.getConteo() * 100.00 / total;
            porcentaje = palabra.getConteo() * 100.00 / total;
            angulo = porcentaje*360/100;
            anguloi = angulof;
            angulof += angulo;
            svg += getPedazo(anguloi, angulof, cx, cy, radio, colores[i]);
            corredor++;
            if(i == 13){
                i = 0;
            }
            if(corredor < 5){
                svg += acota(palabra.getPalabra(), colores[i], porcentaje);
            }else if(confirmo){
                svg += acota("otras", colores[14], (100.00 - porciento));
                confirmo = false;
            }
        }
        return svg + "</svg>";
    }

    private static String acota(String s, String color, double porcentaje){
        String acota = "";
        acota += CUADRO;
        acota = acota.replaceFirst("yede" , String.valueOf(coordenadaYe));
        acota += TEXTO;
        acota = acota.replaceFirst("yequis", String.valueOf(coordenadaYe + 12));
        acota = acota.replaceFirst("textin", (s + " : " + String.valueOf(porcentaje) + "%"));
        acota = acota.replaceFirst("colorillox", color);
        coordenadaYe += 30;
        return acota;
    }
}