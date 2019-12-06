package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return this.altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
            return super.toString() + " " + this.altura + "/" + balance(this);
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
            return (this.altura == vertice.altura && super.equals(objeto));
        }
    }

    /* Convierte el vértice a VerticeAVL */
    private VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice) {
        return (VerticeAVL)vertice;
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        balancea((VerticeAVL)super.ultimoAgregado.padre);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        VerticeAVL e = (VerticeAVL)(busca(raiz,elemento));
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
    }

    /*
     * Método auxiliar que elimina un vértice que no tiene hijos
     * @param v -vértice a eliminar
     */
    private void eliminaHoja(VerticeAVL v) {
        if (v == raiz) {
            raiz = null;
            elementos--;
            return;
        }
        VerticeAVL papa = verticeAVL(v.padre);
        if(v.padre.izquierdo == v)
            v.padre.izquierdo = null;
        else
            v.padre.derecho = null;
        balancea(papa);
        elementos--;
    }

    /**
    * Método auxiliar para eliminar un vértice con un hijo izquierdo.
    * @param el vértice a eliminar
    */
    private void eliminarConHijoIzquierdo(VerticeAVL v) {
        if (v == raiz) {
            raiz = v.izquierdo;
            v.izquierdo.padre = null;
            elementos--;
            return;
        }
        VerticeAVL papa = verticeAVL(v.padre);
        if (v.padre.izquierdo == v) {
        v.padre.izquierdo = v.izquierdo;
        v.izquierdo.padre = v.padre;
        } else {
            v.padre.derecho = v.izquierdo;
            v.izquierdo.padre = v.padre;
        }
        v = null;
        balancea(papa);
        elementos--;
    }

    /**
    * Método auxiliar para eliminar un vértice con hijo derecho.
    * @param el vértice a eliminar
    */
    private void eliminarConHijoDerecho(VerticeAVL v) {
        if (v == raiz) {
            raiz = v.derecho;
            v.derecho.padre = null;
            elementos--;
            return;
        }
        VerticeAVL papa = verticeAVL(v.padre);
        if (v.padre.derecho == v) {
        v.padre.derecho = v.derecho;
        v.derecho.padre = v.padre;
        } else {
            v.padre.izquierdo = v.derecho;
            v.derecho.padre = v.padre;
        }
        v = null;
        balancea(papa);
        elementos--;
    }

    /**
    * Método auxiliar para eliminar un vértice con dos hijos.
    * @param el vértice a eliminar
    */
    private void eliminarConDosHijos(VerticeAVL v) {
        VerticeAVL maximoIzq = verticeAVL(maximoEnSubArbol(v.izquierdo));
        v.elemento = maximoIzq.elemento;
        if (maximoIzq.izquierdo == null)
            eliminaHoja(maximoIzq);
        else eliminarConHijoIzquierdo(maximoIzq);
    }

    /*
     * Método auxiliar que rebalancea un árbolAVL después de aplicar giros sobre el mismo
     * @param rebalancearlo -vértice sobre el cual se empezará el rebalanceo
     */
    private void balancea(VerticeAVL rebalancearlo){
        if(rebalancearlo == null)
            return;

        asignaAltura(rebalancearlo);

        if(balance(rebalancearlo) == -2){
            if(balance((VerticeAVL)rebalancearlo.derecho) == 1){
                giraDerecha((VerticeAVL)rebalancearlo.derecho);
            }
            giraIzquierda((VerticeAVL)rebalancearlo);
        }else if(balance(rebalancearlo) == 2){
            if(balance((VerticeAVL)rebalancearlo.derecho) == -1){
                giraIzquierda((VerticeAVL)rebalancearlo.derecho);
            }
            giraDerecha(rebalancearlo);
        }
        balancea((VerticeAVL)rebalancearlo.padre);
    }

    /*
     * Método que hace girar un árbolAVL hacia la izquierda
     * @param girado -vértice sobre el cual se girará
     */
    private void giraIzquierda(VerticeAVL girado){
        super.giraIzquierda(girado);
        asignaAltura((VerticeAVL)girado);
        asignaAltura((VerticeAVL)girado.padre);
    }

    /*
     * Método que hace girar un árbolALV hacia la derecha
     * @param girado -vértice sobre el cual se girará
     */
    private void giraDerecha(VerticeAVL girado){
        super.giraDerecha(girado);
        asignaAltura((VerticeAVL)girado);
        asignaAltura((VerticeAVL)girado.padre);
    }

    /*
     * Método auxiliar que "intercambia" de lugar a un vértice padre con su hijo para 
     * después hacer una eliminación
     * @param padre vértice padre que ocupará el lugar de su hijo
     * @param hijo vértice hijo que ocupará el lugar de su padre
     */
    private void subir(Vertice vertice){
            if (vertice.hayIzquierdo()){
                if(!vertice.hayPadre()){
                    raiz = vertice.izquierdo;
                    raiz.padre = null;
                }else{
                    vertice.izquierdo.padre = vertice.padre;
                    if(vertice.padre.izquierdo == vertice){
                        vertice.padre.izquierdo = vertice.izquierdo;
                    }else{
                        vertice.padre.derecho = vertice.izquierdo;
                    }
                }
            }else if(!vertice.hayPadre()){
                raiz = raiz.derecho;
                raiz.padre = null;
            }else{
                vertice.derecho.padre = vertice.padre;
                if(vertice.padre.izquierdo == vertice){
                    vertice.padre.izquierdo = vertice.derecho;
                }else{
                    vertice.padre.derecho = vertice.derecho;
                }
            }
    }

    /*
     * Método que calcula el balance de un vértice a partir de la altura de sus hijos
     * @param balanceable -vértice del cual se quiere conocer el balance
     * @return int -balance del vértice
     */
    private int balance(VerticeAVL balanceable){
        if(balanceable == null)
            return 0;

        return calculaAltura(balanceable.izquierdo) - calculaAltura(balanceable.derecho);
    }

    /*
     * Método que le asigna una nueva altura a un vértice después de hacer giros
     * @param vertice -vértice al cual se le quiere asignar una nueva altura
     */
    private void asignaAltura(VerticeAVL vertice){
        vertice.altura = calculaAltura(vertice);
    }

    /*
     * Método que calcula de altura de un vértice
     * @param vertice -vértice del cual se quiere conocer la altura
     * @return int -altura del vértice
     */
    public int calculaAltura(Vertice vertice){
        if(vertice == null)
                return -1;
            
            if(vertice.izquierdo != null && vertice.derecho != null){
                return 1 + Math.max(vertice.izquierdo.altura(), vertice.derecho.altura());
            }else if(vertice.izquierdo != null && vertice.derecho == null){
                return 1 + (vertice.izquierdo.altura());
            }else if(vertice.izquierdo == null && vertice.derecho != null){
                return 1 + (vertice.derecho.altura());
            }else{
                return 0;
            }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
