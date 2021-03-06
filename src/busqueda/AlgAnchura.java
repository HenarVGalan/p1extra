package busqueda;

import busqueda.AlgBusqueda;
import busqueda.Nodo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import problema.Accion;

public class AlgAnchura extends AlgBusqueda {

	@Override
	public void busqueda() {
		// Inicializamos las variables
		costeTotal = 0;
		nodosExpandidos = 0;
		nodosGenerados = 0;
		longSol = 0;
		tiempoBusqueda = System.currentTimeMillis();
		secuenciaAcciones = new ArrayList<Accion>();

		// estructura de datos necesarias para la b�squeda
		// FIFO
		Queue<Nodo> abiertos = new LinkedList<Nodo>();
		Queue<Nodo> cerrados = new LinkedList<Nodo>();

		Nodo elegido;
		ArrayList<Nodo> sucesores;
		elegido = new Nodo(problema.estadoInicial());

		abiertos.add(elegido);

		do {

			if (abiertos.size()==0) {
				return;
			}

			// Coge la cabeza abiertos y la borra
			elegido = abiertos.poll();
			sucesores = getSucesores(elegido);

			// Si el nodo sucesor no se encuentra abierto y tampoco cerrado, lo guardamos en
			// abierto
			while (!sucesores.isEmpty()) {
				if (!abiertos.contains(sucesores.get(0)) && !cerrados.contains(sucesores.get(0))) {
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

			//costeTotal = costeTotal + problema.coste(elegido.getPadre().getEstado(), elegido.getAccion());
			secuenciaAcciones.add(elegido.getAccion());
			elegido = elegido.getPadre();
			

		}

		tiempoBusqueda = System.currentTimeMillis() - tiempoBusqueda;
		Collections.reverse(secuenciaAcciones);
		System.out.println("****   SOLUCI�N ALGORITMO ANCHURA - BreathFirst *****");
		for (int i = 0; i < secuenciaAcciones.size(); i++) {
			longSol++;
			System.out.println(secuenciaAcciones.get(i).toString());
		}

	}
}
