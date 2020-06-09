/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import busqueda.Nodo;
import java.util.ArrayList;
import problema.Accion;
import problema.Estado;
import problema.Problema;

public abstract class AlgBusqueda {

	protected Problema problema;

	// lista de acciones solución
	protected ArrayList<Accion> secuenciaAcciones;
	protected double costeTotal;
	// Nodos expandidos durante la búsqueda
	protected long nodosExpandidos;
	// Nodos generados durante la búsqueda
	protected long nodosGenerados;
	protected long tiempoBusqueda;
	protected long longSol; // Será lo mismo que el coste, porque todas las acciones tienen coste 1.

	/* 
	 * de esta clase heredan cada uno de los algoritmos de búsqueda, por lo tanto,
	 * este método lo extenderán e implementarán según la estrategia llevada a cabo.
	 */
	public abstract void busqueda();

	public void setProblema(Problema problema) {
		this.problema = problema;
	}

	public double getCosteTotal() {
		return costeTotal;
	}

	public long getNodosExpandidos() {
		return nodosExpandidos;
	}

	public long getNodosGenerados() {
		return nodosGenerados;
	}

	public long getTiempoBusqueda() {
		return tiempoBusqueda;
	}

	public long getLongSol() {
		return longSol;
	}

	public ArrayList<Accion> result() {
		return secuenciaAcciones;
	}

	// Comprueba que el estado asociado al nodo es el estado inicial
	public boolean nodoInicial(Nodo nodo) {
		// equals de la clase estado. Comparamos estados.
		return problema.estadoInicial().equals(nodo.getEstado());
	}

	public ArrayList<Nodo> getSucesores(Nodo nodo) {
		ArrayList<Nodo> sucesores = new ArrayList<Nodo>();

		ArrayList<Accion> acciones = this.problema.posiblesAcciones(nodo.getEstado());

		for (int i = 0; i < acciones.size(); i++) {
			// Al aplicar la accion obtenemos un nuevo estado, ya que hemos movido el
			// agente.
			Estado estadoNuevo = this.problema.aplicarAccion(nodo.getEstado(), acciones.get(i));

			// Al tener nuevo estado tenemos que actualizar el nodo.
			// Actualizamos todos los atributos que describen al nodo.
			Nodo nodoNuevo = new Nodo(estadoNuevo);

			nodoNuevo.setPadre(nodo);
			nodoNuevo.setAccion(acciones.get(i));

			double costeAccion = problema.coste(nodo.getEstado(), acciones.get(i));
			nodoNuevo.setCoste(nodo.getCoste() + costeAccion);
			nodoNuevo.setHeuristica(problema.heuristica(estadoNuevo,problema.nHeuristica));
			nodoNuevo.setProfundidad(nodo.getProfundidad() + 1);

			sucesores.add(nodoNuevo);

			nodosGenerados++;
		}

		nodosExpandidos++;
		return sucesores;
	}
}
