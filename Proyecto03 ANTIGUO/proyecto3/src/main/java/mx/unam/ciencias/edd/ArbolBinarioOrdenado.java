package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            // Aquí va su código.
            pila = new Pila<Vertice>();
            Vertice izquierdo = raiz;
            while(izquierdo != null){
                pila.mete(izquierdo);
                izquierdo = izquierdo.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !pila.esVacia();

        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            // Aquí va su código.
            Vertice next = pila.saca();
            T siguiente = next.elemento;

            if(next.hayDerecho()){
                next = next.derecho;
                pila.mete(next);
                while(next.hayIzquierdo()){
                    pila.mete(next.izquierdo);
                    next = next.izquierdo;
                }
            }
            return siguiente;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();

        elementos++;
        ultimoAgregado = nuevoVertice(elemento);

        if(raiz == null){
            raiz = nuevoVertice(elemento);
            ultimoAgregado = nuevoVertice(elemento);
            return;
        }else{
            agrega(raiz, nuevoVertice(elemento));
        }
    }

    /*
     * Método auxiliar para agregar un vértice en el orden correcto
     * @param actual vértice actual distinto de vacío
     * @param agregado vértice a agregar
     */
    private void agrega(Vertice actual, Vertice agregado){
        if(agregado.get().compareTo(actual.get()) <= 0){
            if(actual.izquierdo == null){
                actual.izquierdo = agregado;
                agregado.padre = actual;
                ultimoAgregado = agregado;
                return;
            }agrega(actual.izquierdo, agregado);
        }else{
            if(actual.derecho == null){
                actual.derecho = agregado;
                agregado.padre = actual;
                ultimoAgregado = agregado;
                return;
            }agrega(actual.derecho, agregado);
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.

        Vertice e = busca(raiz,elemento);
        if(e == null)
            return;
        if (e.izquierdo == null && e.derecho == null)
            eliminaHoja(e);
        else if(e.izquierdo == null && e.derecho != null)
            eliminarConHijoDerecho(e);
        else if (e.izquierdo != null && e.derecho == null)
                eliminarConHijoIzquierdo(e);
        else if (e.izquierdo != null && e.derecho != null)
                eliminarConDosHijos(e);
        return;
    }
    /**
    * Método auxiliar para eliminar una hoja
    * @param el vertice a eliminar
    */
    private void eliminaHoja(Vertice v) {
        if (v == raiz) {
            raiz = null;
            elementos--;
            return;
        }
        if(v.padre.izquierdo == v){
            v.padre.izquierdo = null;
            v = null;
        } else {
            v.padre.derecho = null;
            v = null;
        }
        elementos--;
    }

    /**
    * Método auxiliar para eliminar un vértice con un hijo izquierdo.
    * @param el vértice a eliminar
    */
    private void eliminarConHijoIzquierdo(Vertice v) {
        if (v == raiz) {
            raiz = v.izquierdo;
            v.izquierdo.padre = null;
            elementos--;
            return;
        }

        if (v.padre.izquierdo == v) {
        v.padre.izquierdo = v.izquierdo;
        v.izquierdo.padre = v.padre;
        v = null;
        } else {
            v.padre.derecho = v.izquierdo;
            v.izquierdo.padre = v.padre;
            v = null;
        }
        elementos--;
    }

    /**
    * Método auxiliar para eliminar un vértice con hijo derecho.
    * @param el vértice a eliminar
    */
    private void eliminarConHijoDerecho(Vertice v) {
        if (v == raiz) {
            raiz = v.derecho;
            v.derecho.padre = null;
            elementos--;
            return;
        }
        if (v.padre.derecho == v) {
        v.padre.derecho = v.derecho;
        v.derecho.padre = v.padre;
        v = null;
        } else {
            v.padre.izquierdo = v.derecho;
            v.derecho.padre = v.padre;
            v = null;
        }
        elementos--;
    }

    /**
    * Método auxiliar para eliminar un vértice con dos hijos.
    * @param el vértice a eliminar
    */
    private void eliminarConDosHijos(Vertice v) {
        Vertice maximoIzq = maximoEnSubArbol(v.izquierdo);
        v.elemento = maximoIzq.elemento;
        if (maximoIzq.izquierdo == null)
            eliminaHoja(maximoIzq);
        else eliminarConHijoIzquierdo(maximoIzq);
    }
    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.
        Vertice intercambio = maximoEnSubArbol(vertice);
        T elem = intercambio.elemento;
        intercambio.elemento = vertice.elemento;
        vertice.elemento = elem;
        return maximoEnSubArbol(vertice);
    }

    /*
     * Método auxiliar que regresa el vértice máximo de un subárbol
     * @param vertice v1 vértice por el cual se empezará
     * @return Vertice vertice máximo
     */
    protected Vertice maximoEnSubArbol(Vertice vertice) {
        Vertice v = vertice;
        while (v.derecho != null) {
            v = v.derecho;
        }
        return v;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.
        if(!vertice.hayPadre()){
            raiz = null;
            elementos--;
        }else if(vertice.padre.izquierdo == vertice){
            vertice.padre.izquierdo = null;
            vertice = null;
            elementos--;
        }else{
            vertice.padre.derecho = null; 
            vertice = null;
            elementos--;
        }
        if (vertice.padre.izquierdo == vertice) {
        vertice.padre.izquierdo = vertice.izquierdo;
        vertice.izquierdo.padre = vertice.padre;
        vertice = null;
        elementos--;
        } else {
            vertice.padre.derecho = vertice.izquierdo;
            vertice.izquierdo.padre = vertice.padre;
            vertice = null;
            elementos--;
        }
        if (vertice.padre.derecho == vertice) {
        vertice.padre.derecho = vertice.derecho;
        vertice.derecho.padre = vertice.padre;
        vertice = null;
        elementos--;
        } else {
            vertice.padre.izquierdo = vertice.derecho;
            vertice.derecho.padre = vertice.padre;
            vertice = null;
            elementos--;
        }
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
        return busca(raiz, elemento);
    }

    /*
     * Método auxiliar que busca un elemento y nos regresa el vértice en el que está en caso de encontrarse
     * @param v1 vértice sobre el cual se empieza la búsqueda
     * @param elemento elemento a buscar
     */
    protected Vertice busca(Vertice vertice, T elemento){
       if(vertice == null)
            return null;

        if(vertice.elemento.equals(elemento)){
            return vertice;
        }else if(elemento.compareTo(vertice.elemento) < 0){
                return busca(vertice.izquierdo, elemento);
        }else{
            return busca(vertice.derecho, elemento);
        }
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if (vertice == null || !vertice.hayIzquierdo())
            return;

        Vertice vertices = (Vertice)vertice;
        Vertice izquierda = vertices.izquierdo;

        izquierda.padre = vertices.padre;
        if(vertices.hayPadre())
            if(vertices.padre.izquierdo == vertices)
                vertices.padre.izquierdo = izquierda;
            else
                vertices.padre.derecho = izquierda;
        else
            raiz = izquierda;

        vertices.izquierdo = izquierda.derecho;
        if(izquierda.hayDerecho())
            izquierda.derecho.padre = vertices;

        izquierda.derecho = vertices;
        vertices.padre = izquierda;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        Vertice recibido = (Vertice)vertice;
        if(recibido.derecho == null)
            return;

        Vertice derecha = recibido.derecho;
        Vertice izquierdoDerecho = derecha.izquierdo;
        recibido.derecho = izquierdoDerecho;
        derecha.izquierdo = recibido;
        derecha.padre = recibido.padre;
        if (recibido.derecho != null)
            recibido.derecho.padre = recibido;
        if (derecha.padre == null) {
            raiz = derecha;
            recibido.padre = derecha;
            return;
        }
        if (recibido.padre.derecho == recibido)
            recibido.padre.derecho = derecha;
        else recibido.padre.izquierdo = derecha;
        recibido.padre = derecha;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPreOrder(accion, raiz);
    }

    /*
     * Método auxiliar recursivo que recorre un árbol en DFS pre-order para aplicar
     * una acción a cada uno de los vértices
     * @param accion acción que se aplicará a los vértices
     * @param vertice vértice desde el cual se empieza método
     */ 
    private void dfsPreOrder(AccionVerticeArbolBinario<T> accion, Vertice v){
        if(v == null)
            return;

        accion.actua(v);
        dfsPreOrder(accion, v.izquierdo);
        dfsPreOrder(accion, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsInOrder(accion, raiz);
    }

    /*
     * Método auxiliar recursivo que aplica una acción a cada uno de los vértices
     * de un árbol en DFS in-order
     * @param accion acción que se aplicará a cada vértice del en el árbol
     * @param v vértice con el cual el método empieza su trabajo
     */  
    private void dfsInOrder(AccionVerticeArbolBinario<T> accion, Vertice v){
        if(v == null)
            return;

        dfsInOrder(accion, v.izquierdo);
        accion.actua(v);
        dfsInOrder(accion, v.derecho);
    }


    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPostOrder(accion, raiz);
    }

    private void dfsPostOrder(AccionVerticeArbolBinario<T> accion, Vertice v){
        if(v == null)
            return;

        dfsPostOrder(accion, v.izquierdo);
        dfsPostOrder(accion, v.derecho);
        accion.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
