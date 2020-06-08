package problema;

import java.util.ArrayList;

import problema.Practica1;

public class Problema {

	protected Estado circuito;
	protected int n, nCoches, seed;

	public Problema(int[][] circuito, int n, int nCoches, int seed) {

		this.circuito = new Estado(circuito);
		this.n = n;
		this.nCoches = nCoches;
		this.seed = seed;
	}

	public Estado estadoInicial() {
		return new Estado(Practica1.getProblemInstance(this.n, this.nCoches, this.seed));
	}

	public Estado aplicarAccion(Estado estado, Accion accion) {

		int[][] circuitoNuevo = new int[this.n][this.n];
		estado.cloneCircuito(circuitoNuevo);
		int idCoche = accion.coche;
		int x = 0;
		int y = 0;

		// Recorremos el circuito hasta dar con el coche ( id coche = accion.coche )
		// Este es el coche al que queremos aplicar la acción
		for (int i = 0; i < circuitoNuevo.length; i++) {
			for (int j = 0; j < circuitoNuevo.length; j++) {
				if (circuitoNuevo[i][j] == idCoche) {
					y = i;
					x = j;
				}
			}
		}

		// Movemos al agente. Cambiamos sus coordenadas.
		switch (accion.id) {
		case IZQUIERDA:
			x--; // movemos al agente
			circuitoNuevo[y][x + 1] = 0; // la posición que ocupaba la ponemos a 0
			circuitoNuevo[y][x] = idCoche; // la posición que va a ocupar ponemos el id del coche.
			break;
		case DERECHA:
			x++;
			circuitoNuevo[y][x - 1] = 0;
			circuitoNuevo[y][x] = idCoche;
			break;
		case ARRIBA:
			y--;
			circuitoNuevo[y + 1][x] = 0;
			circuitoNuevo[y][x] = idCoche;
			break;
		case ABAJO:
			y++;
			circuitoNuevo[y - 1][x] = 0;
			circuitoNuevo[y][x] = idCoche;
			break;
		}

		return new Estado(circuitoNuevo);

		// Comprobacion de que no se sale de los limites del circuito
		// Pero creo que no hace falta porque ya lo considero en posiblesAcciones
		/*
		 * if ((y < 0) || (y > circuitoNuevo.length - 1) || (x < 0) || (x >
		 * circuitoNuevo.length - 1)) { return null; }
		 */

		// Como hemos movido el coche,
		// la casilla que anteriormente ocupaba tenemos que dejarla vacía.
		// Aqui filtramos, por si la casilla que acabo de querer posicionar el agente,
		// está ocupada,
		// tampoco sería necesario porque lo tengo en cuenta en posiblesAcciones.

		/*
		 * if ((circuitoNuevo[y][x]) == 0) { switch (accion.id) { case IZQUIERDA:
		 * circuitoNuevo[y][x + 1] = 0; circuitoNuevo[y][x] = accion.coche; break;
		 * 
		 * case DERECHA: circuitoNuevo[y][x - 1] = 0; circuitoNuevo[y][x] =
		 * accion.coche; break;
		 * 
		 * case ARRIBA: circuitoNuevo[y + 1][x] = 0; circuitoNuevo[y][x] = accion.coche;
		 * break;
		 * 
		 * case ABAJO: circuitoNuevo[y - 1][x] = 0; circuitoNuevo[y][x] = accion.coche;
		 * break; }
		 * 
		 * return new Estado(circuitoNuevo); }
		 * 
		 * return null;
		 */

	}

	public ArrayList<Accion> posiblesAcciones(Estado estado) {

		int[][] circuito = new int[this.n][this.n];
		estado.cloneCircuito(circuito);
		ArrayList<Accion> acciones = new ArrayList<Accion>();

		// El orden en el que determino explorar las posibilidades influirá en el número
		// de nodos expandidos, explorados
		// la primera opción que considero es ir hacia abajo, por lo tanto , si es
		// posible la añado
		// y se situará en la primera posición luego en aplicar
		// FIFO

		for (int i = 0; i < circuito.length; i++) {
			for (int j = 0; j < circuito.length; j++) {

				if ((circuito[i][j] != 0) && (circuito[i][j] != -1)) {

					// i nos marca la altura, mientras sea menor que circuito.length - 1 podrá bajar
					if (i < circuito.length - 1) {
						// Comprobamos justo que la casilla hacia abajo esté libre == 0
						if (circuito[i + 1][j] == 0) {
							acciones.add(new Accion(Accion.Movimiento.ABAJO, circuito[i][j]));
						}
					}

					if (i > 0) {
						if (circuito[i - 1][j] == 0) {
							acciones.add(new Accion(Accion.Movimiento.ARRIBA, circuito[i][j]));
						}
					}

					if (j < circuito.length - 1) {
						if (circuito[i][j + 1] == 0) {
							acciones.add(new Accion(Accion.Movimiento.DERECHA, circuito[i][j]));
						}
					}

					if (j > 0) {
						if (circuito[i][j - 1] == 0) {
							acciones.add(new Accion(Accion.Movimiento.IZQUIERDA, circuito[i][j]));
						}
					}

				}
			}

		}

		return acciones;
	}

	public double coste(Estado estado, Accion accion) {
		int x = 0;
		int y = 0;
		int[][] circuitoNuevo = new int[this.n][this.n];
		estado.cloneCircuito(circuitoNuevo);

		for (int i = 0; i < circuitoNuevo.length; i++) {
			for (int j = 0; j < circuitoNuevo.length; j++) {
				if (circuitoNuevo[i][j] == accion.coche) {
					y = i;
					x = j;
				}
			}

		}

		switch (accion.id) {
		case IZQUIERDA:
			x--;
			break;
		case DERECHA:
			x++;
			break;
		case ARRIBA:
			y--;
			break;
		case ABAJO:
			y++;
			break;
		}

		if ((y < 0) || (y > circuitoNuevo.length - 1) || (x < 0) || (x > circuitoNuevo.length - 1)) {
			return Double.POSITIVE_INFINITY;
		}

		if (circuitoNuevo[y][x] == 0) {
			return 1.0;
		}

		return Double.POSITIVE_INFINITY;
	}

	public boolean comprobarFinal(Estado estado) {

		int[][] circuitoNuevo = new int[this.n][this.n];
		estado.cloneCircuito(circuitoNuevo);
		int nCochesFinal = 0;
		// int nCoches = 0;

		for (int j = 0; j < circuitoNuevo.length; j++) {
			for (int i = (circuitoNuevo.length - 1); i < circuitoNuevo.length; i++) {

				if ((circuitoNuevo[i][j] != 0) && (circuitoNuevo[i][j] != -1))
					nCochesFinal++;
				if (nCochesFinal == this.nCoches)
					return true;
			}
		}

		return false;

	}

	public double heuristica(Estado estado) {

		// Calculamos la suma de las distancias en linea recta de cada coche para llegar
		// a la ultima fila
		int[][] circuitoNuevo = estado.getCircuito();

		double distancia = 0;

		for (int i = 0; i < circuitoNuevo.length; i++) {
			for (int j = 0; j < circuitoNuevo.length; j++) {
				if ((circuitoNuevo[i][j] != 0) && (circuitoNuevo[i][j] != -1)) {
					distancia = distancia + ((circuitoNuevo.length) - 1 - i);
				}
			}
		}
		// System.out.println("----------------------------------> Distancia Heuristica
		// 1 : " + distancia);
		return distancia;
	}

}
