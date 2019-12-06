package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Conjunto;
import java.io.IOException;

public class Relaciona{

	public static Grafica<Documento> relaciona(Grafica<Documento> grafo){
		Grafica<Documento> auxiliar1 = new Grafica<Documento>();
		for(Documento i : grafo){
			auxiliar1.agrega(i);
		}
		for(Documento e : grafo){
			for(Documento o : grafo){
				if(e != o){
					try{
						if(comun(e.getWords(), o.getWords())){
							auxiliar1.conecta(e, o);
						}
					}catch(IllegalArgumentException u){
						;
					}
				}
			}
		}
		return auxiliar1;
	}

	public static boolean comun(Conjunto<String> a, Conjunto<String> b){
		Conjunto<String> interseccion = a.interseccion(b);
		for(String e : interseccion){
			String auxiliar = e;
			if(auxiliar.length() >= 7){
				return true;
			}
		}
		return false;
	}
}