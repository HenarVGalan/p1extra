/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import problema.Accion;

/**
 *
 * @author Jose
 */
public class AlgoPrimeroMejor extends AlgBusqueda {

// Search
    /**
     * Implements a template search algorithm.
     */
    @Override
    public void busqueda() {
        // Inits performance variables
        // Inicializamos las variables
        costeTotal = 0;
        nodosExpandidos = 0;
        nodosGenerados = 0;
        longSol = 0;
        tiempoBusqueda = System.currentTimeMillis();

        // Crea el array para la secuencia de acciones
        secuenciaAcciones = new ArrayList<Accion>();

        // Crea las estructuras necesarias para implementar el algoritmo:
        ArrayList<Nodo> cerrados = new ArrayList<Nodo>();
        Comparator<Nodo> comparator = Nodo.BY_HEURISTIC;
        PriorityQueue<Nodo> abiertos = new PriorityQueue<Nodo>(comparator);

        // Variables auxiliares
        Nodo elegido;
        ArrayList<Nodo> sucesores;

        // Crea la raíz del árbol
        elegido = new Nodo(problema.estadoInicial());

       
        sucesores = getSucesores(elegido);

        abiertos.add(elegido);
        // Bucle principal
        do {
            // Si no quedan nodos abiertos, retorna
            if (abiertos.size() == 0) {
                return;
            }

            elegido = abiertos.element();
            abiertos.remove();
            sucesores = getSucesores(elegido);
            while (!sucesores.isEmpty()) {
                if (!cerrados.contains(sucesores.get(0))) {
                    abiertos.add(sucesores.get(0));
                    sucesores.remove(0);

                } else {
                    sucesores.remove(0);
                }
            }
            cerrados.add(elegido);

        } while (!problema.comprobarFinal(elegido.getEstado()));
        costeTotal = elegido.getCoste();
        while (!nodoInicial(elegido)) {
            secuenciaAcciones.add(elegido.getAccion());

            elegido = elegido.getPadre();
        }

        // Calculates the search time.
        tiempoBusqueda = System.currentTimeMillis() - tiempoBusqueda;

        // Una vez obtenida la lista de acciones, le da la vuelta
        Collections.reverse(secuenciaAcciones);
        System.out.println("SOLUCIÓN:");
        for(int i=0;i<secuenciaAcciones.size();i++){
            longSol++;
            System.out.println(secuenciaAcciones.get(i).toString());
        }
    }

}
