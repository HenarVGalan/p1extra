/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

/**
 *
 * @author Jose
 */
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
		Queue<Nodo> abiertos = new LinkedList<Nodo>();
		Queue<Nodo> cerrados = new LinkedList<Nodo>();

		Nodo elegido;
		ArrayList<Nodo> sucesores;
		elegido = new Nodo(problema.estadoInicial());
		
		abiertos.add(elegido);
		
		do {

			if (abiertos.isEmpty()) {
				return;
			}

			// Coge la cabeza abiertos y la borra
			elegido = abiertos.poll();
			sucesores = getSucesores(elegido);
			
			// Si el nodo sucesor no se encuentra abierto y tampoco cerrado, lo guardamos en
			// abierto
			for (int i = 0; i < sucesores.size(); i++) {
				
				if (!abiertos.contains(sucesores.get(i)) && !cerrados.contains(sucesores.get(i))) {
					abiertos.add(sucesores.get(i));
					sucesores.remove(i);
				} else {
					
					sucesores.remove(i);
				}
			}

			cerrados.add(elegido);

		} while (!problema.comprobarFinal(elegido.getEstado()));	
		
		
		
		while (!nodoInicial(elegido)) {

			costeTotal = costeTotal + problema.coste(elegido.getPadre().getEstado(), elegido.getAccion());
			secuenciaAcciones.add(elegido.getAccion());
			elegido = elegido.getPadre();
			//Practica1.printMaze(elegido.getEstado().getCircuito());

		}

		// Calcula el tiempo de bÃºsqueda
		tiempoBusqueda = System.currentTimeMillis() - tiempoBusqueda;
		Collections.reverse(secuenciaAcciones);
		System.out.println("****   SOLUCIÓN ALGORITMO ANCHURA - BreathFirst *****");
		for (int i = 0; i < secuenciaAcciones.size(); i++) {
			longSol++;
			System.out.println(secuenciaAcciones.get(i).toString());
		}
			
	}
}
