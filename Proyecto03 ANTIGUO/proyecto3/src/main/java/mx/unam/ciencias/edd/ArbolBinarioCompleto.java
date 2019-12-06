package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
            // Aquí va su código.
            cola = new Cola<Vertice>();
            if(raiz != null)
                cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
            Vertice siguiente = cola.saca();

            if(siguiente.hayIzquierdo())
                cola.mete(siguiente.izquierdo);
            
            if(siguiente.hayDerecho())
                cola.mete(siguiente.derecho);
            
            return siguiente.get();
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();

        Vertice auxiliar = nuevoVertice(elemento);
        if (raiz == null)
            raiz = auxiliar;
        else {
            Vertice auxiliar2 = raiz;
            Cola<Vertice> cola = new Cola<Vertice>();
            cola.mete(auxiliar2);
            while (!cola.esVacia()) {
                auxiliar2 = cola.saca();
                if (!auxiliar2.hayIzquierdo() || !auxiliar2.hayDerecho()) {
                    auxiliar.padre = auxiliar2;
                    if (!auxiliar2.hayIzquierdo())
                        auxiliar2.izquierdo = auxiliar;
                    else if (!auxiliar2.hayDerecho())
                        auxiliar2.derecho = auxiliar;
                    break;
                }
                cola.mete(auxiliar2.izquierdo);
                cola.mete(auxiliar2.derecho);
            }
        }
        elementos++;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();
        
        Vertice morir = (Vertice) busca(elemento);
        if(morir == null)
            return;

        elementos--;
        if(elementos == 0){
            raiz = null;
        }else{
            Vertice ultimos = ultimoVertice();
            T cambiado = ultimos.elemento;
            ultimos.elemento = morir.elemento;
            morir.elemento = cambiado;
            
            if(ultimos.padre.izquierdo == ultimos){
                ultimos.padre.izquierdo = null;
                ultimos = null;
            }else{
                ultimos.padre.derecho = null;
                ultimos = null;
            }
        }
    }

    /*
     * Método auxiliar para conseguir el último vértice de un árbol aplicando BFS
     * @return Vertice último vértice del árbol
     */
    private Vertice ultimoVertice(){
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);
        Vertice ultimo = null;
        while(!cola.esVacia()){
            Vertice vertice = cola.saca();
            if(vertice.izquierdo != null)
                cola.mete(vertice.izquierdo);
            if(vertice.derecho != null)
                cola.mete(vertice.derecho);
            ultimo = vertice;
        }
        return ultimo;
    }
    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.
        if(esVacia())
            return -1;
        
        return (int)(Math.floor(Math.log(getElementos()) / Math.log(2)));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código. 
        if(esVacia())
            return;

        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);

        while(!cola.esVacia()){
            Vertice auxiliar = cola.saca();
            accion.actua(auxiliar);
            if(auxiliar.hayIzquierdo() && auxiliar.hayDerecho()){
                cola.mete(auxiliar.izquierdo);
                cola.mete(auxiliar.derecho);
            }
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}