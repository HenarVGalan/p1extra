package busqueda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import problema.Accion;

public class AlgProfundidad extends AlgBusqueda {

	@Override
	public void busqueda() {
		// Inicializamos las variables
		costeTotal = 0;
		nodosExpandidos = 0;
		nodosGenerados = 0;
		longSol = 0;
		tiempoBusqueda = System.currentTimeMillis();
		secuenciaAcciones = new ArrayList<Accion>();

		//Estructura de datos 
		//LIFO. Last IN , First OUT
		Stack<Nodo> abiertos = new Stack<Nodo>();
		ArrayList<Nodo> cerrados = new ArrayList<Nodo>();

		Nodo elegido;
		ArrayList<Nodo> sucesores;

		elegido = new Nodo(problema.estadoInicial());

		abiertos.push(elegido);

		do {

			if (abiertos.size() == 0) {
				return;
			}

			elegido = abiertos.pop();
			sucesores = getSucesores(elegido);

			while (!sucesores.isEmpty()) {
				if (!abiertos.contains(sucesores.get(0)) && !cerrados.contains(sucesores.get(0))) {
					abiertos.push(sucesores.get(0));
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
		System.out.println("****   SOLUCIÓN ALGORITMO PROFUNDIDAD - DepthFirst *****");
		for (int i = 0; i < secuenciaAcciones.size(); i++) {
			longSol++;
			System.out.println(secuenciaAcciones.get(i).toString());
		}
	}
}
