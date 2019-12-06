package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        // Aquí va su código.
        int residuo = llave.length % 4;
        int limite = llave.length / 4;
        int corredor = 0;
        int entero = 0;
        for(int i = 0; i < limite * 4; i = i){
            entero ^= combina(llave[i], llave[i+1], llave[i+2], llave[i+3]);
            i += 4;
            corredor = i;
         }
         int sobrante = 0;
         byte nuevo = 0;
         switch(residuo){
            case 0:
            ;
            break;

            case 1:
            sobrante |= combina(llave[corredor], nuevo, nuevo, nuevo);
            entero ^= sobrante;
            break;

            case 2:
            sobrante |= combina(llave[corredor], llave[corredor+1], nuevo, nuevo);
            entero ^= sobrante;
            break;

            case 3:
            sobrante |= combina(llave[corredor], llave[corredor+1], llave[corredor+2], nuevo);
            entero ^= sobrante;
            break;
         }
         return entero;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.
        int n = llave.length;
                int a,b,c,l;
                l = n;
                a = b = 0x9e3779b9;
                c = 0xffffffff;
                int i = 0;
                while (l >= 12){
                    a += ((llave[i] & 0xFF)   + ((llave[i+1] & 0xFF) << 8) + ((llave[i+2] & 0xFF)  << 16) + ((llave[i+3] & 0xFF) << 24));
                    b += ((llave[i+4] & 0xFF) + ((llave[i+5] & 0xFF) << 8) + ((llave[i+6] & 0xFF)  << 16) + ((llave[i+7] & 0xFF) << 24));
                    c += ((llave[i+8] & 0xFF) + ((llave[i+9] & 0xFF) << 8) + ((llave[i+10] & 0xFF) << 16) + ((llave[i+11] & 0xFF) << 24));

                    a -= b; a -= c; a ^= (c >>> 13);
                    b -= c; b -= a; b ^= (a <<  8);
                    c -= a; c -= b; c ^= (b >>> 13);
                    a -= b; a -= c; a ^= (c >>> 12);
                    b -= c; b -= a; b ^= (a <<  16);
                    c -= a; c -= b; c ^= (b >>> 5);
                    a -= b; a -= c; a ^= (c >>> 3);
                    b -= c; b -= a; b ^= (a <<  10);
                    c -= a; c -= b; c ^= (b >>> 15);
                    
                    i += 12;
                    l -=12;
                }
                c += n;
                switch (l) {
                    case 11: c += ((llave[i+10] & 0xFF) << 24);
                    case 10: c += ((llave[i+9] & 0xFF)  << 16);
                    case  9: c += ((llave[i+8] & 0xFF)  << 8);
                
                    case  8: b += ((llave[i+7] & 0xFF)  << 24);
                    case  7: b += ((llave[i+6] & 0xFF)  << 16);
                    case  6: b += ((llave[i+5] & 0xFF)  << 8);
                    case  5: b +=  llave[i+4] & 0xFF;
                        
                    case  4: a += ((llave[i+3] & 0xFF)  << 24);
                    case  3: a += ((llave[i+2] & 0xFF)  << 16);
                    case  2: a += ((llave[i+1] & 0xFF)  << 8);
                    case  1: a += llave[i] & 0xFF;
                }

                a -= b; a -= c; a ^= (c >>> 13);
                b -= c; b -= a; b ^= (a <<  8);
                c -= a; c -= b; c ^= (b >>> 13);
                a -= b; a -= c; a ^= (c >>> 12);
                b -= c; b -= a; b ^= (a <<  16);
                c -= a; c -= b; c ^= (b >>> 5);
                a -= b; a -= c; a ^= (c >>> 3);
                b -= c; b -= a; b ^= (a <<  10);
                c -= a; c -= b; c ^= (b >>> 15);
                return c;
    }

    
    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
        int hash = 5381;
        for (int i = 0; i < llave.length; i++) {
            hash += (hash << 5) + (llave[i] & 0XFF);
        }
        return hash;
    }

    /*
     * Método auxiliar que combina bytes para formar un entero de 32 bytes.
     * El método evita que se prendan bytes que no deben prenderse al hacerse la mezcla
     * haciéndoles and con 0xFF
     * @param a -byte número 1 que se desplazará 24 bits a la izquierda
     * @param b -byte número 2 que se desplazará 16 bits a la izquierda
     * @param c -byte número 3 que se desplazará 8 bits a la izquierda
     * @param d -byte que no se desplazará
     * @return int -entero de 32 bits resultante de la combinación.
     */
    private static int combina(byte a, byte b, byte c, byte d){
        return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | ((d & 0xFF));
    }
}