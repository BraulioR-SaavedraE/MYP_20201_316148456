package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Grafica;

public class Lector{

	public static void verifica(String [] args){
		if(args.length == 0){
			System.err.println("Necesito argumentos");
			System.exit(-1);
		}
		Lista<String> archivos = new Lista<String>();
		boolean banderaO = false;
		boolean repeticion = true;
		String direc = "";
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-o")){
				banderaO = true;
				if(i + 1 < args.length && repeticion){
					creaDirectorio(args[i + 1]);
					direc = args[i + 1];
					i += 2;
					repeticion = false;
				}else{
					System.err.println("Necesito un nombre para el directorio");
					System.exit(-1);
				}
			}else{
				archivos.agregaFinal(args[i]);
			}
		}
		if(!banderaO){
			System.err.println("No recibí ninguna bandera");
			System.exit(-1);
		}
		analizaArchivos(archivos, direc);
	}

	public static void analizaArchivos(Lista<String> nombres, String direc){
		Lista<String> auxiliar = nombres.copia();
		Grafica<Documento> auxilio = new Grafica<Documento>();
		while(!auxiliar.esVacia()){
			String cadena = auxiliar.eliminaPrimero();
			try(BufferedReader lector = new BufferedReader(new FileReader(cadena))){
				auxilio.agrega(new Documento(cadena));
				lector.close();
			}catch(IOException e){
				System.err.println("Verifique los nombres de archivos dados");
				System.exit(-1);
			}catch(IllegalArgumentException i){
				System.err.println("Se ha intentado agregar dos archivos con el mismo nombre");
				System.exit(-1);
			}
		}
		auxilio = Relaciona.relaciona(auxilio);
		Creador.crea(auxilio, direc);
	}

	public static void creaDirectorio(String nombre){
		File directorio = new File(nombre);
		if(directorio.isFile()){
			System.err.println("El \"directorio\" brindado es un archivo");
			System.exit(-1);
		}
		if(!directorio.exists()){
			directorio.mkdir();
		}
	}


}