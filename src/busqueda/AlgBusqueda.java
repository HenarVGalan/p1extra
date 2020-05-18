/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import problema.*;
import busqueda.Nodo;
import java.util.ArrayList;
import problema.Accion;
import problema.Estado;
import problema.Problema;

/**
 *
 * @author Jose
 */
public abstract class AlgBusqueda {

	protected Problema problema;
	protected ArrayList<Accion> secuenciaAcciones;
	protected double costeTotal;
	protected long nodosExpandidos;
	protected long tiempoBusqueda;
	protected long nodosGenerados;
	protected long longSol;

	/**
	 * Carries out the search and returns the the result.
	 */
	public abstract void busqueda();

	/**
	 * Sets the search problem.
	 */
	public void setProblema(Problema problema) {
		this.problema = problema;
	}

	/**
	 * Returns the cost of the solution.
	 */
	public double getCosteTotal() {
		return costeTotal;
	}

	/**
	 * Returns the number of expanded nodes.
	 */
	public long getNodosExpandidos() {
		return nodosExpandidos;
	}

	public long getNodosGenerados() {
		return nodosGenerados;
	}

	/**
	 * Returns the search time.
	 */
	public long getTiempoBusqueda() {
		return tiempoBusqueda;
	}

	public long getLongSol() {
		return longSol;
	}

	/**
	 * Returns the solution to the problem.
	 */
	public ArrayList<Accion> result() {
		return secuenciaAcciones;
	}

	// Some methods useful for the implementation.
	/**
	 * Checks if the node contains the initial state.
	 */
	public boolean nodoInicial(Nodo nodo) {		
		return problema.estadoInicial().equals(nodo.getEstado());
	}

	/**
	 * Return the successors of a given node. It is necessary if besides the states,
	 * some other information, such as actions, costs, etc., is needed. This
	 * function corresponds to EXPAND seen in class.
	 */
	public ArrayList<Nodo> getSucesores(Nodo nodo) {
		ArrayList<Nodo> sucesores = new ArrayList<Nodo>();
		
		ArrayList<Accion> acciones = this.problema.posiblesAcciones(nodo.getEstado());

		for (int i = 0; i < acciones.size(); i++) { 
			Estado estadoNuevo = this.problema.aplicarAccion(nodo.getEstado(), acciones.get(i));
			
			Nodo nodoNuevo = new Nodo(estadoNuevo);

			nodoNuevo.setPadre(nodo);
			// Fixes the action used to create the new node.
			nodoNuevo.setAccion(acciones.get(i));
			// Calculates the cost.
			double costeAccion = problema.coste(nodo.getEstado(), acciones.get(i));
			nodoNuevo.setCoste(nodo.getCoste() + costeAccion);
			// Adds the heuristic
			nodoNuevo.setHeuristica(problema.heuristica(estadoNuevo));
			// Updates its depth.
			nodoNuevo.setProfundidad(nodo.getProfundidad() + 1);
			// Adds it to the list.
			sucesores.add(nodoNuevo);
			nodosGenerados++;
		}

		nodosExpandidos++;
		return sucesores;
	}
}
