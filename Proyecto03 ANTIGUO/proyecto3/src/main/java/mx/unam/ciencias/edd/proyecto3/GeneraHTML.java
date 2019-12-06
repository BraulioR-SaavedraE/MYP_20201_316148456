package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public class GeneraHTML{


	private static final String ABRE_HTML = "<!DOCTYPE html>\n"
											+ "<html>\n"
											+ "<head>\n"
											+ "<title>%s</title>\n"
											+ "<meta charset=\"UTF-8\">"
											+ "</head>\n"
											+ "<body>\n";
	private static final String H1 = "\t<h1>%s</h1>\n";
	private static final String PAR = "\t<p>%s</h1>\n";
	private static final String CIERRA_HTML = "</body>\n"
												+ "</html>";
    

	public static String genera(Documento r) {
		StringBuffer s = new StringBuffer();
		//Graficador g = new Graficador();
		s.append(String.format(ABRE_HTML, r.getNombre()));
		s.append(String.format(H1, "Texto original."));
		s.append(String.format(PAR,"<i>Total de palabras:</i> " + r.getTotal()));
		s.append(String.format(H1, "Coincidencias de palabras"));
		StringBuffer c = new StringBuffer();
		for (Palabra p : r.sinRepetir())
			c.append("\t" + ("la palabra " + p.getPalabra() + " se repite " + p.getConteo() + " veces ") + "<br>\n");
		s.append(String.format(PAR,c.toString()));
		s.append(String.format(H1, "Gráfica de barras."));
		s.append(Barras.barras(r.sinRepetir(), r.getTotal()));
		s.append(Pastel.generarPastel(200.0, 200.0, 200.0, r.sinRepetir(), r.getTotal()));
		s.append(CIERRA_HTML);
		System.out.println(s.toString());
		return s.toString();
	}

}