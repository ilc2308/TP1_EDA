public class ListaDobleReservas {

    Nodo head;
    Nodo tail;

    public ListaDobleReservas() {
        head = null;
        tail = null;
    }

    /* ================= INSERTAR ================= */
    public boolean insertar(Reserva r) {

        if (buscar(r.idReserva) != null) {
            System.out.println("Identificación de Reserva ya existente");
            return false;
        }

        Nodo nuevo = new Nodo(r);

        if (head == null) {
            head = tail = nuevo;
        } else {
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
                return actual;

            actual = actual.siguiente;
        }

        return null;
    }

    /* ================= ELIMINAR ================= */
    public boolean eliminar(int id) {

        Nodo eliminar = buscar(id);

        if (eliminar == null)
            return false;

        if (eliminar == head)
            head = head.siguiente;

        if (eliminar == tail)
            tail = tail.anterior;

        if (eliminar.anterior != null)
            eliminar.anterior.siguiente = eliminar.siguiente;

        if (eliminar.siguiente != null)
            eliminar.siguiente.anterior = eliminar.anterior;

        return true;
    }

    /* ================= MOSTRAR ================= */
    public void mostrar() {

        Nodo actual = head;

        while (actual != null) {
            actual.dato.mostrar();
            actual = actual.siguiente;
        }
    }

    /* ================= ESTADISTICAS ================= */
    public void estadisticas() {

        if (head == null) {
            System.out.println("No hay pedidos.");
            return;
        }

        Nodo actual = head;

        int conteo = 0;
        double suma = 0;

        double min = head.dato.costo;
        double max = head.dato.costo;

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

        Nodo medio = head;
        for (int i = 0; i < conteo / 2; i++)
            medio = medio.siguiente;

        double mediana;
        if (conteo % 2 == 1)
            mediana = medio.dato.costo;
        else
            mediana = (medio.dato.costo + medio.anterior.dato.costo) / 2;

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
        System.out.println("Total pedidos: " + conteo);
        System.out.println("Promedio: " + promedio);
        System.out.println("Minimo: " + min);
        System.out.println("Maximo: " + max);
        System.out.println("Rango: " + rango);
        System.out.println("Mediana: " + mediana);
        System.out.println("Moda: " + moda + " (frecuencia " + frecuenciaMax + ")");
    }

    /* =====================================================
                        ORDENAR (MERGE SORT)
       ===================================================== */

    public void ordenarPorCliente() {
        head = mergeSortCliente(head);
        actualizarTail();
    }

    public void ordenarPorCosto() {
        head = mergeSortCosto(head);
        actualizarTail();
    }

    /* ===== Obtener mitad ===== */
    private Nodo obtenerMitad(Nodo inicio) {

        Nodo lento = inicio;
        Nodo rapido = inicio.siguiente;

        while (rapido != null && rapido.siguiente != null) {
            lento = lento.siguiente;
            rapido = rapido.siguiente.siguiente;
        }

        return lento;
    }

    /* ===== MergeSort Cliente ===== */
    private Nodo mergeSortCliente(Nodo inicio) {

        if (inicio == null || inicio.siguiente == null)
            return inicio;

        Nodo mitad = obtenerMitad(inicio);
        Nodo segundaParte = mitad.siguiente;

        mitad.siguiente = null;
        if (segundaParte != null)
            segundaParte.anterior = null;

        Nodo izquierda = mergeSortCliente(inicio);
        Nodo derecha = mergeSortCliente(segundaParte);

        return mergeCliente(izquierda, derecha);
    }

    private Nodo mergeCliente(Nodo izquierda, Nodo derecha) {

        if (izquierda == null) return derecha;
        if (derecha == null) return izquierda;

        if (izquierda.dato.cliente
                .compareToIgnoreCase(derecha.dato.cliente) <= 0) {

            izquierda.siguiente =
                    mergeCliente(izquierda.siguiente, derecha);

            if (izquierda.siguiente != null)
                izquierda.siguiente.anterior = izquierda;

            izquierda.anterior = null;
            return izquierda;

        } else {

            derecha.siguiente =
                    mergeCliente(izquierda, derecha.siguiente);

            if (derecha.siguiente != null)
                derecha.siguiente.anterior = derecha;

            derecha.anterior = null;
            return derecha;
        }
    }

    /* ===== MergeSort Costo ===== */
    private Nodo mergeSortCosto(Nodo inicio) {

        if (inicio == null || inicio.siguiente == null)
            return inicio;

        Nodo mitad = obtenerMitad(inicio);
        Nodo segundaParte = mitad.siguiente;

        mitad.siguiente = null;
        if (segundaParte != null)
            segundaParte.anterior = null;

        Nodo izquierda = mergeSortCosto(inicio);
        Nodo derecha = mergeSortCosto(segundaParte);

        return mergeCosto(izquierda, derecha);
    }

    private Nodo mergeCosto(Nodo izquierda, Nodo derecha) {

        if (izquierda == null) return derecha;
        if (derecha == null) return izquierda;

        boolean menor =
                izquierda.dato.costo < derecha.dato.costo ||
               (izquierda.dato.costo == derecha.dato.costo &&
                izquierda.dato.cliente.compareToIgnoreCase(
                        derecha.dato.cliente) <= 0);

        if (menor) {

            izquierda.siguiente =
                    mergeCosto(izquierda.siguiente, derecha);

            if (izquierda.siguiente != null)
                izquierda.siguiente.anterior = izquierda;

            izquierda.anterior = null;
            return izquierda;

        } else {

            derecha.siguiente =
                    mergeCosto(izquierda, derecha.siguiente);

            if (derecha.siguiente != null)
                derecha.siguiente.anterior = derecha;

            derecha.anterior = null;
            return derecha;
        }
    }

    /* ===== Actualizar tail ===== */
    private void actualizarTail() {

        Nodo actual = head;
        tail = null;

        while (actual != null) {
            tail = actual;
            actual = actual.siguiente;
        }
    }
}

   