package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public class Barras{

	private static final String ABRE = "<?xml version='1.0' encoding='UTF-8' ?>\n";
	private static final String ALTURA_ANCHO = "<svg height='1000' with='ancho'>\n" + "<g>\n";
	private static final String RECTANGULO = "<rect x='equis' y='ye' height='alturilla' width='45' fill='colorillo' stroke='transparent' stroke-width='2'/>\n";
	private static final String ACABA = "</g>\n" + "</svg>"; 
	private static final String [] COLORES = {"#2E64FE","#B40404","#088A08","#DBA901","#7401DF","#424242","#E3F6CE","#FF4000",
	"#04B4AE","#2A0A12","#D8D8D8","#151515","#01DF74","#0B2161","#3ADF00"};
	private static final String CUADRO = "<rect x='10' y='yede' height='20' width='20' fill='colorillox' stroke='transparent' stroke-width='2'/>\n";
	private static final String TEXTO = "<text x='40' y='yequis' fill='black' font-family='sans-serif' font-size='10' </text>textin</text>";
	private static int coordenadaEquis = 10;
	private static int coordenadaYe = 550;

	public static String barras(Lista<Palabra> cadabra, int numero){
		Lista<Palabra> palabras = cadabra.copia();
		palabras = Lista.mergeSort(palabras);
		palabras = palabras.reversa();
		String barrilla = "";
		int color = 0;
		barrilla += ABRE;
		barrilla += ALTURA_ANCHO;
		if(palabras.getLongitud() > 6){
			barrilla = barrilla.replaceFirst("ancho", "900");
		}else{
			barrilla = barrilla.replaceFirst("ancho", String.valueOf(palabras.getLongitud() * 150));
		}
		double unidad = 500.00 / numero;
		double altura = 0;
		int contador = 0;
		double suma = 0;
		int palas = 0;
		double porcentaje = 0;
		String auxilio = "";
		for(Palabra e : palabras){
			if(contador == 5){
				e.ponPalabra("otras");
				altura = (unidad * (numero - palas));
				porcentaje = 100.00 - suma;
			}
			barrilla += RECTANGULO;
			barrilla = barrilla.replaceFirst("equis", String.valueOf(coordenadaEquis));
			coordenadaEquis += 45;
			if(contador == 5){
				altura = (unidad * (numero - palas));
				porcentaje = 100.00 - suma;
			}else{
				altura = (unidad * e.getConteo());
				porcentaje = (e.getConteo() * 100.00) /  numero;
			}
			
			palas += e.getConteo();
			double auxiliar = 500.0 - altura;
			
			suma += porcentaje;
			barrilla = barrilla.replaceFirst("alturilla", String.valueOf(altura));
			barrilla = barrilla.replaceFirst("ye", String.valueOf(auxiliar));
			barrilla = barrilla.replaceFirst("colorillo", COLORES[contador]);
			barrilla += acota(e.getPalabra(), COLORES[contador], porcentaje);
			if(contador == 5){
				break;
			}
			contador++;
		}
		barrilla += ACABA;
		return barrilla;
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