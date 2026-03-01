import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ListaDobleReservas lista = new ListaDobleReservas();

        int opcion;

        do {
            System.out.println("\n===== MENU RESERVAS =====");
            System.out.println("1. Insertar Reserva");
            System.out.println("2. Buscar Reserva");
            System.out.println("3. Eliminar Reserva");
            System.out.println("4. Mostrar Reserva");
            System.out.println("5. Estadisticas");
            System.out.println("6. Ordenar por cliente");
            System.out.println("7. Ordenar por costo");
            System.out.println("0. Salir");
            System.out.print("Seleccione opcion: ");

            opcion = sc.nextInt();

            switch (opcion) {

                case 1:
                    sc.nextLine();

                    System.out.print("Cliente: ");
                    String cliente = sc.nextLine();

                    System.out.print("ID Reserva: ");
                    int id = sc.nextInt();

                    System.out.print("Costo: ");
                    double costo = sc.nextDouble();

                    Reserva nuevo = new Reserva(cliente, id, costo);
                    lista.insertar(nuevo);

                    System.out.println("Reserva Insertada");
                    break;

                case 2:
                    System.out.print("ID a buscar: ");
                    Nodo encontrado = lista.buscar(sc.nextInt());

                    if (encontrado != null)
                        encontrado.dato.mostrar();
                    else
                        System.out.println("Pedido no encontrado.");
                    break;

                case 3:
                    System.out.print("ID a eliminar: ");
                    if (lista.eliminar(sc.nextInt()))
                        System.out.println("Pedido eliminado.");
                    else
                        System.out.println("No existe ese pedido.");
                    break;

                case 4:
                    lista.mostrar();
                    break;

                case 5:
                    lista.estadisticas();
                    break;

                case 6:
                    lista.ordenarPorCliente();
                    System.out.println("Ordenado por cliente.");
                    break;

                case 7:
                    lista.ordenarPorCosto();
                    System.out.println("Ordenado por costo.");
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}