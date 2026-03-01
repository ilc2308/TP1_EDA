public class ListaDobleReservas {

    Nodo head;
    Nodo tail;

    public ListaDobleReservas() {
        head = null;
        tail = null;
    }

    /* ================= INSERTAR ================= */
    public boolean insertar(Reserva r) {
        // Primero verificamos que no exista una reserva con el mismo ID
        if (buscar(r.idReserva) != null) {
            System.out.println("Ya existe una reserva con ese ID. No se puede duplicar.");
            return false;
        }

        Nodo nuevo = new Nodo(r);

        if (head == null) {
            // Lista vacía: el nuevo nodo es cabeza y cola
            head = tail = nuevo;
        } else {
            // Insertamos al final: enganchamos el nuevo después de la cola actual
            tail.siguiente = nuevo;
            nuevo.anterior = tail;
            tail = nuevo;
        }
        return true;
    }

    /* ================= BUSCAR ================= */
    public Nodo buscar(int id) {
        Nodo actual = head;
        while (actual != null) {
            if (actual.dato.idReserva == id)
                return actual;  // encontrado
            actual = actual.siguiente;
        }
        return null; // no existe
    }

    /* ================= ELIMINAR ================= */
    public boolean eliminar(int id) {
        Nodo eliminar = buscar(id);
        if (eliminar == null) {
            System.out.println("No se encontró ninguna reserva con ese ID.");
            return false;
        }

        // Si es la cabeza, movemos la cabeza al siguiente
        if (eliminar == head)
            head = head.siguiente;

        // Si es la cola, movemos la cola al anterior
        if (eliminar == tail)
            tail = tail.anterior;

        // Conectamos los nodos vecinos entre sí (si existen)
        if (eliminar.anterior != null)
            eliminar.anterior.siguiente = eliminar.siguiente;

        if (eliminar.siguiente != null)
            eliminar.siguiente.anterior = eliminar.anterior;

        System.out.println("Reserva eliminada correctamente.");
        return true;
    }

    /* ================= ACTUALIZAR ================= */
    public boolean actualizar(int id, String nuevoCliente, double nuevoCosto) {
        Nodo nodo = buscar(id);
        if (nodo == null) {
            System.out.println("No existe reserva con ese ID.");
            return false;
        }
        // Validamos que el costo no sea negativo (igual que en el constructor)
        if (nuevoCosto < 0) {
            System.out.println("El costo no puede ser negativo. Se asignará 0.");
            nuevoCosto = 0;
        }
        nodo.dato.cliente = nuevoCliente;
        nodo.dato.costo = nuevoCosto;
        System.out.println("Reserva actualizada correctamente.");
        return true;
    }

    /* ================= MOSTRAR ================= */
    public void mostrar() {
        if (head == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo actual = head;
        while (actual != null) {
            actual.dato.mostrar();
            actual = actual.siguiente;
        }
    }

    /* ================= ESTADISTICAS ================= */
    public void estadisticas() {
        if (head == null) {
            System.out.println("No hay reservas para calcular estadísticas.");
            return;
        }

        Nodo actual = head;
        int conteo = 0;
        double suma = 0;
        double min = head.dato.costo;
        double max = head.dato.costo;

        // Primer recorrido: conteo, suma, min, max
        while (actual != null) {
            double costo = actual.dato.costo;
            suma += costo;
            conteo++;
            if (costo < min) min = costo;
            if (costo > max) max = costo;
            actual = actual.siguiente;
        }

        double promedio = suma / conteo;
        double rango = max - min;

        // Cálculo de la mediana (asume que la lista está ordenada por costo)
        // Si no lo está, el valor puede no ser correcto. Se recomienda ordenar antes.
        Nodo medio = head;
        for (int i = 0; i < conteo / 2; i++)
            medio = medio.siguiente;

        double mediana;
        if (conteo % 2 == 1) {
            mediana = medio.dato.costo;
        } else {
            mediana = (medio.dato.costo + medio.anterior.dato.costo) / 2;
        }

        // Cálculo de la moda (frecuencia de costos)
        double moda = head.dato.costo;
        int frecuenciaMax = 0;

        Nodo externo = head;
        while (externo != null) {
            int frecuencia = 0;
            Nodo interno = head;
            while (interno != null) {
                if (interno.dato.costo == externo.dato.costo)
                    frecuencia++;
                interno = interno.siguiente;
            }
            if (frecuencia > frecuenciaMax) {
                frecuenciaMax = frecuencia;
                moda = externo.dato.costo;
            }
            externo = externo.siguiente;
        }

        System.out.println("\n--- ESTADISTICAS ---");
        System.out.println("Total reservas: " + conteo);
        System.out.println("Promedio: " + promedio);
        System.out.println("Mínimo: " + min);
        System.out.println("Máximo: " + max);
        System.out.println("Rango: " + rango);
        System.out.println("Mediana: " + mediana + " (calculada sobre el orden actual; si no está ordenada por costo, puede no ser correcta)");
        System.out.println("Moda: " + moda + " (frecuencia " + frecuenciaMax + ")");
    }

    /* =====================================================
                        ORDENAR (MERGE SORT)
       ===================================================== */

    // Ordena la lista por nombre del cliente (ascendente o descendente según el parámetro)
    public void ordenarPorCliente(boolean ascendente) {
        head = mergeSortCliente(head, ascendente);
        actualizarTail();
    }

    // Ordena la lista por costo, y en caso de empate por nombre (ascendente o descendente)
    public void ordenarPorCosto(boolean ascendente) {
        head = mergeSortCosto(head, ascendente);
        actualizarTail();
    }

    /* ===== Obtener el nodo que está en la mitad (para dividir la lista) ===== */
    private Nodo obtenerMitad(Nodo inicio) {
        Nodo lento = inicio;
        Nodo rapido = inicio.siguiente;
        // técnica de "puntero lento y rápido": cuando rápido llegue al final, lento está en la mitad
        while (rapido != null && rapido.siguiente != null) {
            lento = lento.siguiente;
            rapido = rapido.siguiente.siguiente;
        }
        return lento;
    }

    /* ===== MergeSort para cliente ===== */
    private Nodo mergeSortCliente(Nodo inicio, boolean ascendente) {
        // caso base: lista vacía o de un solo elemento
        if (inicio == null || inicio.siguiente == null)
            return inicio;

        Nodo mitad = obtenerMitad(inicio);
        Nodo segundaParte = mitad.siguiente;

        // Dividimos la lista: cortamos el enlace entre la primera y segunda mitad
        mitad.siguiente = null;
        if (segundaParte != null)
            segundaParte.anterior = null;

        Nodo izquierda = mergeSortCliente(inicio, ascendente);
        Nodo derecha = mergeSortCliente(segundaParte, ascendente);

        return mergeCliente(izquierda, derecha, ascendente);
    }

    private Nodo mergeCliente(Nodo izq, Nodo der, boolean ascendente) {
        if (izq == null) return der;
        if (der == null) return izq;

        // Comparamos según el orden deseado (ascendente o descendente)
        int cmp = izq.dato.cliente.compareToIgnoreCase(der.dato.cliente);
        boolean condicion = ascendente ? cmp <= 0 : cmp >= 0;

        if (condicion) {
            izq.siguiente = mergeCliente(izq.siguiente, der, ascendente);
            if (izq.siguiente != null)
                izq.siguiente.anterior = izq;
            izq.anterior = null;
            return izq;
        } else {
            der.siguiente = mergeCliente(izq, der.siguiente, ascendente);
            if (der.siguiente != null)
                der.siguiente.anterior = der;
            der.anterior = null;
            return der;
        }
    }

    /* ===== MergeSort para costo ===== */
    private Nodo mergeSortCosto(Nodo inicio, boolean ascendente) {
        if (inicio == null || inicio.siguiente == null)
            return inicio;

        Nodo mitad = obtenerMitad(inicio);
        Nodo segundaParte = mitad.siguiente;

        mitad.siguiente = null;
        if (segundaParte != null)
            segundaParte.anterior = null;

        Nodo izquierda = mergeSortCosto(inicio, ascendente);
        Nodo derecha = mergeSortCosto(segundaParte, ascendente);

        return mergeCosto(izquierda, derecha, ascendente);
    }

    private Nodo mergeCosto(Nodo izq, Nodo der, boolean ascendente) {
        if (izq == null) return der;
        if (der == null) return izq;

        // Comparamos primero por costo; si iguales, por cliente (siempre alfabético, independientemente de dirección)
        boolean condicion;
        if (ascendente) {
            condicion = izq.dato.costo < der.dato.costo ||
                       (izq.dato.costo == der.dato.costo &&
                        izq.dato.cliente.compareToIgnoreCase(der.dato.cliente) <= 0);
        } else {
            condicion = izq.dato.costo > der.dato.costo ||
                       (izq.dato.costo == der.dato.costo &&
                        izq.dato.cliente.compareToIgnoreCase(der.dato.cliente) >= 0);
        }

        if (condicion) {
            izq.siguiente = mergeCosto(izq.siguiente, der, ascendente);
            if (izq.siguiente != null)
                izq.siguiente.anterior = izq;
            izq.anterior = null;
            return izq;
        } else {
            der.siguiente = mergeCosto(izq, der.siguiente, ascendente);
            if (der.siguiente != null)
                der.siguiente.anterior = der;
            der.anterior = null;
            return der;
        }
    }

    /* ===== Actualizar la cola después de ordenar ===== */
    private void actualizarTail() {
        Nodo actual = head;
        tail = null;
        while (actual != null) {
            tail = actual;
            actual = actual.siguiente;
        }
    }
}

   