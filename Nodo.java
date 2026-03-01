public class Nodo {

    Reserva dato;
    Nodo siguiente;
    Nodo anterior;

    public Nodo(Reserva dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}