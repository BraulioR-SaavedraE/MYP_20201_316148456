package mx.unam.ciencias.edd.proyecto3;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.Diccionario;

public class Documento{
	Lista<String> palabras;
	Lista<Palabra> palabrilla; //lista de todas las palabras
	Conjunto<String> words;
	Diccionario<String, Integer> diccionario; //diccionario con las palabras sin repetir
	String nombre;

	public Documento(String nombre){
		palabras = new Lista<String>();
		palabrilla = new Lista<Palabra>();
		words = new Conjunto<String>();
		diccionario = new Diccionario<String, Integer>();
		this.nombre = nombre;
		llenaLista();
		for(String e : palabras){
			words.agrega(e);
			if(diccionario.contiene(e)){
				diccionario.agrega(e, diccionario.get(e) + 1);
			}else{
				diccionario.agrega(e, 1);
			}
		}
		palabrilla = Contador.cuenta(palabras, diccionario, words);
		Barras.barras(palabrilla, palabras.getLongitud());
		Pastel.generarPastel(200.0, 200.0, 200.0, palabrilla, palabras.getLongitud());
	}

	public void llenaLista(){
		String auxiliar;
		Lista<String> lineas = new Lista<String>();
		try(BufferedReader lector = new BufferedReader(new FileReader(nombre))){
			while((auxiliar = lector.readLine()) != null){
				lineas.agrega(auxiliar.trim().toLowerCase());
				}
		}catch(IOException e){
			System.err.println("Verifique los nombres de los archivos dados");
			System.exit(-1);
		}
		while(!lineas.esVacia()){
			String [] arreglo = lineas.eliminaPrimero().toString().split(" ");
			for(int i = 0; i < arreglo.length; i ++){
				palabras.agregaFinal(Normalizador.normaliza(arreglo[i]));
			}
		}
	}

	public Conjunto<String> getWords(){
		return words;
	}

	public String getNombre(){
		return nombre;
	}

	public int getTotal(){
		return palabras.getLongitud();
	}

	public Lista<Palabra> sinRepetir(){
		return palabrilla;
	}


}