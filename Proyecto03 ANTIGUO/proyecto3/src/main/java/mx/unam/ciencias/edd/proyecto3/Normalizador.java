package mx.unam.ciencias.edd.proyecto3;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class Normalizador{

	public static String normaliza(String cadena){
		cadena = Normalizer.normalize(cadena, Normalizer.Form.NFD);
		cadena = cadena.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return cadena;
	}
}