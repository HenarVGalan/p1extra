package busqueda;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import problema.Accion;

/**
 * Extends the abstract class SearchAlgorithm and implements a template
 * algorithm.
 */
public class AEstrella extends AlgBusqueda {

// Search
    /**
     * Implements a template search algorithm.
     */
    @Override
    public void busqueda() {
       
        costeTotal = 0;
        nodosExpandidos = 0;
        nodosGenerados = 0;
        longSol = 0;
        tiempoBusqueda = System.currentTimeMillis();        
        secuenciaAcciones = new ArrayList<Accion>();
        
        ArrayList<Nodo> cerrados = new ArrayList<Nodo>();
        Comparator<Nodo> comparator = Nodo.BY_EVALUATION;     
        PriorityQueue<Nodo> abiertos = new PriorityQueue<Nodo>(comparator);

        Nodo elegido;
        ArrayList<Nodo> sucesores;
      
        elegido = new Nodo(problema.estadoInicial());      
        sucesores = getSucesores(elegido);
        abiertos.add(elegido);
        // Bucle principal
        do {
           
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

        tiempoBusqueda = System.currentTimeMillis() - tiempoBusqueda;
        Collections.reverse(secuenciaAcciones);
        System.out.println("****   SOLUCIÓN ALGORITMO A*  *****");
        for (int i = 0; i < secuenciaAcciones.size(); i++) {
            longSol++;
            System.out.println(secuenciaAcciones.get(i).toString());
        }

    }

}
