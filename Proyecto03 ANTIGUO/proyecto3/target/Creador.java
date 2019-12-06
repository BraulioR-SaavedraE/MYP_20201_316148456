package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Grafica;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Creador{
	public static int contador = 0;

	public static void crea(Grafica<Documento> grafo, String directorio){
		String auxiliar = "";
		File dir = new File(directorio);
		if(!dir.exists()){
			dir.mkdir();
		}
		for(Documento e : grafo){
			auxiliar = GeneraHTML.genera(e);
			try{
				String s = "archivo" + (contador++) + ".html";
			File archivo = new File(directorio, s);
				BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo));
				escritor.write(GeneraHTML.genera(e));
				escritor.close();
			}catch(IOException u){
				System.err.println("El archivo no se ha podido leer");
				System.exit(-1);
			}
		}
	}
}