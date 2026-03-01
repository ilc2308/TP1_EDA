import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ListaDobleReservas lista = new ListaDobleReservas();

        int opcion = -1; // Inicializamos con un valor no válido

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Insertar reserva");
            System.out.println("2. Buscar reserva (por ID)");
            System.out.println("3. Eliminar reserva (por ID)");
            System.out.println("4. Actualizar reserva");
            System.out.println("5. Mostrar todas las reservas");
            System.out.println("6. Ver estadísticas");
            System.out.println("7. Ordenar lista");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            // Verificamos que el usuario ingrese un número
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
            } else {
                System.out.println("Error: debe ingresar un número.");
                sc.next(); // Limpiar la entrada incorrecta
                continue;  // Volver a mostrar el menú
            }

            switch (opcion) {

                case 1: // Insertar
                    sc.nextLine(); // limpiar buffer
                    System.out.print("Nombre del cliente: ");
                    String cliente = sc.nextLine();

                    System.out.print("ID de la reserva: ");
                    int id = sc.nextInt();

                    System.out.print("Costo: ");
                    double costo = sc.nextDouble();

                    Reserva nueva = new Reserva(cliente, id, costo);
                    lista.insertar(nueva);
                    break;

                case 2: // Buscar por ID
                    System.out.print("Ingrese el ID a buscar: ");
                    int idBuscar = sc.nextInt();
                    Nodo encontrado = lista.buscar(idBuscar);
                    if (encontrado != null) {
                        System.out.println("Reserva encontrada:");
                        encontrado.dato.mostrar();
                    } else {
                        System.out.println("No existe una reserva con ese ID.");
                    }
                    break;

                case 3: // Eliminar por ID
                    System.out.print("Ingrese el ID a eliminar: ");
                    int idEliminar = sc.nextInt();
                    if (lista.eliminar(idEliminar)) {
                        System.out.println("Reserva eliminada.");
                    }
                    break;

                case 4: // Actualizar
                    System.out.print("ID de la reserva a actualizar: ");
                    int idAct = sc.nextInt();
                    sc.nextLine(); // limpiar buffer
                    System.out.print("Nuevo nombre del cliente: ");
                    String nuevoCliente = sc.nextLine();
                    System.out.print("Nuevo costo: ");
                    double nuevoCosto = sc.nextDouble();
                    lista.actualizar(idAct, nuevoCliente, nuevoCosto);
                    break;

                case 5: // Mostrar todas
                    lista.mostrar();
                    break;

                case 6: // Estadísticas
                    lista.estadisticas();
                    break;

                case 7: // Submenú de ordenamiento
                    System.out.println("\n--- ORDENAR RESERVAS ---");
                    System.out.println("1. Por nombre del cliente");
                    System.out.println("2. Por costo");
                    System.out.print("Seleccione criterio: ");
                    int criterio = sc.nextInt();
                    System.out.print("¿Ascendente (A) o Descendente (D)? ");
                    char dir = sc.next().toUpperCase().charAt(0);
                    boolean ascendente = (dir == 'A');

                    if (criterio == 1) {
                        lista.ordenarPorCliente(ascendente);
                        System.out.println("Lista ordenada por cliente (" + (ascendente ? "ascendente" : "descendente") + ").");
                    } else if (criterio == 2) {
                        lista.ordenarPorCosto(ascendente);
                        System.out.println("Lista ordenada por costo (" + (ascendente ? "ascendente" : "descendente") + ").");
                    } else {
                        System.out.println("Criterio no válido.");
                    }
                    break;

                case 0: // Salir
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0);

        sc.close();
    }
}