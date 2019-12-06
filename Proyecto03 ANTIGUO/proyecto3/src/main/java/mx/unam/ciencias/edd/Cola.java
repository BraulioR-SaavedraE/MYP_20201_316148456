package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        // Aquí va su código.
        String cola = "";
        Nodo auxiliar = cabeza;
        while(auxiliar != null){
            cola += auxiliar.elemento + ",";
            auxiliar = auxiliar.siguiente;
        }
        return cola;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();

        Nodo mete = new Nodo(elemento);
        if(esVacia())
            cabeza = rabo = mete;
        else
            rabo.siguiente = mete;
            rabo = mete;
    }
}
