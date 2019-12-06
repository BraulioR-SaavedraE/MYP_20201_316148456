package mx.unam.ciencias.edd.proyecto3;

public class Palabra implements Comparable<Palabra>{
	String palabra;
	int conteo;

	public Palabra(String palabra, int conteo){
		this.palabra = palabra;
		this.conteo = conteo;
	}

	public String getPalabra(){
		return palabra;
	}

	public int getConteo(){
		return conteo;
	}

	public int compareTo(Palabra palabra){
		return getConteo() - palabra.getConteo();
	}

	public void ponPalabra(String palabra){
		this.palabra = palabra;
	}
}