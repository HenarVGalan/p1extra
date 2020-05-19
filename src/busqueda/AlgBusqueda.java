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

	public boolean nodoInicial(Nodo nodo) {
		return problema.estadoInicial().equals(nodo.getEstado());
	}

	public ArrayList<Nodo> getSucesores(Nodo nodo) {
		ArrayList<Nodo> sucesores = new ArrayList<Nodo>();

		ArrayList<Accion> acciones = this.problema.posiblesAcciones(nodo.getEstado());

		for (int i = 0; i < acciones.size(); i++) {
			Estado estadoNuevo = this.problema.aplicarAccion(nodo.getEstado(), acciones.get(i));
			Nodo nodoNuevo = new Nodo(estadoNuevo);
			nodoNuevo.setPadre(nodo);
			nodoNuevo.setAccion(acciones.get(i));

			double costeAccion = problema.coste(nodo.getEstado(), acciones.get(i));
			nodoNuevo.setCoste(nodo.getCoste() + costeAccion);

			nodoNuevo.setHeuristica(problema.heuristica(estadoNuevo));
			nodoNuevo.setProfundidad(nodo.getProfundidad() + 1);
			sucesores.add(nodoNuevo);

			nodosGenerados++;
		}

		nodosExpandidos++;
		return sucesores;
	}
}
