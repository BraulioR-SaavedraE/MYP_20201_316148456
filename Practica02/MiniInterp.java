import java.util.Stack;
import java.util.LinkedList;
import java.util.Scanner;
public class MiniInterp{

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		String x = scan.nextLine();
		System.out.println(calcula(separa(x)));
	}

	private static int calcula(LinkedList<String> valores) {
		Stack<Integer> pila = new Stack<Integer>();
		int resultado = 0;
		int multiplicandos = 0;
		int producto = 1;
		boolean multiplicando = true;
		int corredor = 0;

		for(String x : valores) {
			corredor++;
			if(!(x.equals("+")) && !(x.equals("*"))) {
				pila.push(Integer.parseInt(x));
			}else if(x.equals("*")) {
				if(multiplicando) {
					multiplicandos++;
					multiplicando = false;
				}
				multiplicandos += 1;
			}
			if(x.equals("+") || corredor == valores.size()) {
				if(multiplicandos != 0)
					while(multiplicandos > 0) {
						producto *= pila.pop();
						multiplicandos -= 1;
						if(multiplicandos == 0) {
							resultado += producto;
							producto = 1;
							multiplicando = true;
						}
					}
			}
		}

		resultado += suma(pila);
		return resultado;
	}

	private static LinkedList<String> separa(String s) {
		LinkedList<String> lista = new LinkedList<String>();

		for(int i = 0; i < s.length(); i++){
			String numero = new String();
			char car = s.charAt(i);
			if(Character.isDigit(car)) {
				numero += s.charAt(i);
				while((i+1) < s.length() && Character.isDigit(s.charAt(i+1)))
					numero += s.charAt(++i);

				lista.add(numero);
			}else
				lista.add("" + car);
		}
		return lista;
	}

	private static int suma(Stack<Integer> numeros) {
		int resultado = 0;

		for(Integer x : numeros)
			resultado += x;

		return resultado;
	}
}