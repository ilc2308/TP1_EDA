public class Reserva {

    String cliente;
    int idReserva;
    double costo;

    public Reserva(String cliente, int idReserva, double costo) {

        if (costo < 0) {
            System.out.println("Costo invalido");
            costo = 0;
        }

        this.cliente = cliente;
        this.idReserva = idReserva;
        this.costo = costo;
    }

    public void mostrar() {
        System.out.println(
            "ID: " + idReserva +
            " | Cliente: " + cliente +
            " | Costo: " + costo
        );
    }
} 
