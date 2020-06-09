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

public class AlgPrimeroMejor extends AlgBusqueda {

	@Override
	public void busqueda() {

		costeTotal = 0;
		nodosExpandidos = 0;
		nodosGenerados = 0;
		longSol = 0;
		tiempoBusqueda = System.currentTimeMillis();
		secuenciaAcciones = new ArrayList<Accion>();

		ArrayList<Nodo> cerrados = new ArrayList<Nodo>();
		Comparator<Nodo> comparator = Nodo.BY_HEURISTIC;
		PriorityQueue<Nodo> abiertos = new PriorityQueue<Nodo>(comparator);

		Nodo elegido;
		ArrayList<Nodo> sucesores;

		elegido = new Nodo(problema.estadoInicial());
		abiertos.add(elegido);

		do {

			if (abiertos.size() == 0) {
				return;
			}
			//la funci�n element() solo devuelve el primer nodo, pero no elimina de abiertos
			//por eso es necesario borrarlo despu�s.
			elegido = abiertos.element();
			abiertos.remove(); //borra la cabeza
			sucesores = getSucesores(elegido);

			while (!sucesores.isEmpty()) {
				//!abiertos.contains(sucesores.get(0)) &&
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

		// Una vez obtenida la lista de acciones, le da la vuelta
		Collections.reverse(secuenciaAcciones);
		System.out.println("****   SOLUCI�N ALGORITMO PRIMERO MEJOR - BEST FIRST  *****");
		for (int i = 0; i < secuenciaAcciones.size(); i++) {
			longSol++;
			System.out.println(secuenciaAcciones.get(i).toString());
		}
	}

}
