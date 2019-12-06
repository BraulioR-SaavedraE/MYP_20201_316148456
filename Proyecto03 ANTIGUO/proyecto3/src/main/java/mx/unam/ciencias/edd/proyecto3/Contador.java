package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Conjunto;

public class Contador{

	public static Lista<Palabra> cuenta(Lista<String> lista, Diccionario<String, Integer> diccionario, Conjunto<String> conjunto){
		Lista<Palabra> auxilia = new Lista<Palabra>();
		for(String o : conjunto){
			auxilia.agrega(new Palabra(o, diccionario.get(o)));
		}
		return auxilia;
	}
}