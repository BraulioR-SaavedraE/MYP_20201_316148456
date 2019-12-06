package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            // Aquí va su código.
            String s = "";
            if(esRojo(this)){
                s += "R{" + this.elemento.toString() + "}";
            }else{
                s += "N{" + this.elemento.toString() + "}";
            }
            return s;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            // Aquí va su código.
                return(color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        VerticeRojinegro roji = (VerticeRojinegro)vertice;
        return roji.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        VerticeRojinegro agregado = (VerticeRojinegro)ultimoAgregado;
        agregado.color = Color.ROJO;
        rebalanceo(agregado);
    }

    /*
     * Método auxiliar que rebalancea un árbol rojinegro
     * @param vertice vertice a partir del cual se hace el rebalanceo
     */
    private void rebalanceo(VerticeRojinegro vertice){
        if(!vertice.hayPadre()){ //Caso 1
            raiz = vertice;
            vertice.color = Color.NEGRO;
            return;
        }
        VerticeRojinegro p = (VerticeRojinegro)vertice.padre;
        if(!esRojo(p)){ //Caso 2
            return;
        }
        VerticeRojinegro a = (VerticeRojinegro)p.padre;
        VerticeRojinegro t = tio(vertice);
        if(esRojo(t)){ //Caso 3
            t.color = Color.NEGRO;
            p.color = Color.NEGRO;
            a.color = Color.ROJO;
            rebalanceo(a);
            return;
        }
        if(estanCruzados(vertice, p)){ //Caso 4
            if(esIzquierdo(p)){
                super.giraIzquierda(p);
            }else if(esDerecho(p)){
                super.giraDerecha(p);
            }
            VerticeRojinegro auxiliar = (VerticeRojinegro)vertice;
            vertice = p;
            p = auxiliar;
        }
        p.color = Color.NEGRO;
        a.color = Color.ROJO;
        if(esIzquierdo(vertice)){
            super.giraDerecha(a);
        }else if(esDerecho(vertice)){
            super.giraIzquierda(a);
        }
    }

    /* Método auxiliar que verifica si un vértice es rojo
     * @param vertice vertice a revisar su color
     * @return boolean true si el vértice es rojo, false en otro caso
     */
    private boolean esRojo(VerticeRojinegro vertice){
        return(vertice != null && vertice.color == Color.ROJO);
    }

    /* Método auxiliar que regresa el tío de un vértice
     * @param sobrino vértice del cual se quiere conocer el tío
     * @return VerticeRojinegro tío del vértice
     */
    private VerticeRojinegro tio(VerticeRojinegro sobrino){
        if(!sobrino.hayPadre() || !sobrino.padre.hayPadre()){
            return null;
        }
        VerticeRojinegro padre = (VerticeRojinegro)sobrino.padre;
        if(esIzquierdo(padre)){
            return(VerticeRojinegro)sobrino.padre.padre.derecho;
        }else{
            return(VerticeRojinegro)sobrino.padre.padre.izquierdo;
        }
    }

    /*
     * Método auxiliar para saber si un vértice es hijo izquierdo
     * @param vertice vértice sobre el cuál se quiere saber
     * @return boolean true si el vértice es hijo izquierdo; false en otro caso
     */
    private boolean esIzquierdo(Vertice vertice){
        if(vertice.padre.izquierdo == vertice)
            return true;

        return false;
    }

    /*
     * Método auxiliar para saber si un vértice es hijo derecho
     * @param vertice vértice sobre el cual se quiere saber
     * @return boolean true si el vértice es hijo derecho; false en otro caso
     */
    private boolean esDerecho(VerticeRojinegro vertice){
        if(vertice.padre.derecho == vertice)
            return true;

        return false;
    }

    /*
     * Método auxiliar que nos dice si dos vértices están cruzados
     * @param hijo uno de los vértices a revisar
     * @param padre el otro de los vértices a revisar
     */
    private boolean estanCruzados(VerticeRojinegro hijo, VerticeRojinegro padre){
        return (esIzquierdo(hijo) && esDerecho(padre) || esDerecho(hijo) && esIzquierdo(padre));
    }
    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        VerticeRojinegro eliminado = (VerticeRojinegro)super.busca(raiz, elemento);
        VerticeRojinegro hijo, fantasma = null;

        if(eliminado == null)
            return;

        if(eliminado.hayIzquierdo()) {
            VerticeRojinegro auxiliar = eliminado;
            eliminado = (VerticeRojinegro)maximoEnSubArbol(eliminado.izquierdo);
            auxiliar.elemento = eliminado.elemento;
        }
        if(!eliminado.hayIzquierdo() && !eliminado.hayDerecho()){
            fantasma = (VerticeRojinegro)nuevoVertice(null);
            fantasma.color = Color.NEGRO;
            fantasma.padre = eliminado;
            eliminado.izquierdo = fantasma;
        }
        hijo = hijo(eliminado);
        subir(eliminado);
        if(!esRojo(eliminado) && !esRojo(hijo)){
            hijo.color = Color.NEGRO;
            rebalanceoElimina(hijo);
        }else 
            hijo.color = Color.NEGRO;

        eliminaFantasma(fantasma);

        elementos--;
    }

    /*
     * Método auxiliar que rebalancea un árbol roji-negro después de eliminar un elemento
     * @param vertice vértice sobre el cual se va a empezar el balanceo
     */
    private void rebalanceoElimina(VerticeRojinegro vertice){
        if(!vertice.hayPadre()){ // Caso 1
            vertice.color = Color.NEGRO;
            raiz = vertice;
            return;
        }

        VerticeRojinegro p = (VerticeRojinegro)vertice.padre;
        VerticeRojinegro h = hermano(vertice);
        if(esRojo(h)){ // Caso 2
            p.color = Color.ROJO;
            h.color = Color.NEGRO;
            if(esIzquierdo(vertice)){
                super.giraIzquierda(p);
            }else if(esDerecho(vertice)){
                super.giraDerecha(p);
            }
        }
        p = (VerticeRojinegro)vertice.padre;
        h = hermano(vertice); 
        VerticeRojinegro hi = (VerticeRojinegro)h.izquierdo;
        VerticeRojinegro hd = (VerticeRojinegro)h.derecho;
        if(!esRojo(p) && !esRojo(h) && !esRojo(hi) && !esRojo(hd)){ // Caso 3
            h.color = Color.ROJO;
            rebalanceoElimina(p);
            return;
        }
        if(!esRojo(h) && !esRojo(hi) && !esRojo(hd) && esRojo(p)){ // Caso 4
            h.color = Color.ROJO;
            p.color = Color.NEGRO;
            return;
        }
        if(esIzquierdo(vertice) && esRojo(hi) && !esRojo(hd) || esDerecho(vertice) && !esRojo(hi) && esRojo(hd)){ // Caso 5
            h.color = Color.ROJO;
            if(esRojo(hi)){
                hi.color = Color.NEGRO;
            }else if(esRojo(hd)){
                hd.color = Color.NEGRO;
            }
            h.color = Color.ROJO;
            if(esIzquierdo(vertice)){
                super.giraDerecha(h);
            }else if(esDerecho(vertice)){
                super.giraIzquierda(h);
            }
        }
        h = hermano(vertice); 
        hi = (VerticeRojinegro)h.izquierdo;
        hd = (VerticeRojinegro)h.derecho;
        h.color = p.color;
        p.color = Color.NEGRO;
        if(esIzquierdo(vertice)){ // Caso 6
            hd.color = Color.NEGRO;
        }else if(esDerecho(vertice)){
            hi.color = Color.NEGRO;
        }
        if(esIzquierdo(vertice)){
            super.giraIzquierda(p);
        }else if(esDerecho(vertice)){
            super.giraDerecha(p);
        }
    }

    /*
     * Método auxiliar que regresa el vértice hermano de cierto vértice
     * @param vertice vértice del cual se quiere conocer el hermano
     * @return VerticeRojinegro vértice hermano del que recibe el método
     */ 
    private VerticeRojinegro hermano(Vertice vertice){
        if(!vertice.hayPadre())
            return null;

        if(esIzquierdo(vertice))
            return (VerticeRojinegro)vertice.padre.derecho;

        return (VerticeRojinegro)vertice.padre.izquierdo;  
    }

    /*
     * Método auxiliar encargado de regresar el primer hijo que se encuentre de un vértice
     * @param vertice posible hijo del vértice padre
     * @return hijo el hijo del vértice 
     */
    private VerticeRojinegro hijo(Vertice vertice) {
        if(vertice.hayIzquierdo())
            return (VerticeRojinegro)vertice.izquierdo;

        return (VerticeRojinegro)vertice.derecho;
    }

    /*
     * Método auxiliar que "intercambia" de lugar a un vértice padre con su hijo para 
     * después hacer una eliminación
     * @param padre vértice padre que ocupará el lugar de su hijo
     * @param hijo vértice hijo que ocupará el lugar de su padre
     */
    private void subir(Vertice vertice) {
            if (vertice.hayIzquierdo()){
                if(!vertice.hayPadre()){
                    raiz = vertice.izquierdo;
                    raiz.padre = null;
                }else{
                    vertice.izquierdo.padre = vertice.padre;
                    if(esIzquierdo(vertice)){
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
                if(esIzquierdo(vertice)){
                    vertice.padre.izquierdo = vertice.derecho;
                }else{
                    vertice.padre.derecho = vertice.derecho;
                }
            }
    }

    /* Método auxiliar para eliminar un vértice roji-negro fantasma
     * @param fantasma vértice a eliminar
     */
    private void eliminaFantasma(VerticeRojinegro fantasma){
        if(fantasma != null)
            if(!fantasma.hayPadre()){
                raiz = fantasma = null; //revisar después si es necesario actualizar la variable ultimoAgregado.
            }else if(esIzquierdo(fantasma)){
                    fantasma.padre.izquierdo = null;
                }else{
                    fantasma.padre.derecho = null;
                }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
