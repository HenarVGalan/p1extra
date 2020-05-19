package problema;

import java.util.ArrayList;


import problema.Practica1;

public class Problema {

	protected Estado circuito;
	protected int n, nCars, seed;	

	public Problema(int[][] circuito, int n, int nCars, int seed) {

		this.circuito = new Estado(circuito);
		this.n = n;
		this.nCars = nCars;
		this.seed = seed;
	}

	public Estado estadoInicial() {
		return new Estado(Practica1.getProblemInstance(this.n, this.nCars, this.seed));
	}

	public Estado aplicarAccion(Estado estado, Accion accion) {

		int[][] circuitoNuevo = new int[this.n][this.n];
		estado.cloneCircuito(circuitoNuevo);
		int id = accion.coche;
		int x = 0;
		int y = 0;

		// Obtenemos la posicion del coche
		// al que se le quiere aplicar la accion
		for (int i = 0; i < circuitoNuevo.length; i++) {
			for (int j = 0; j < circuitoNuevo.length; j++) {
				if (circuitoNuevo[i][j] == id) {
					x = i;
					y = j;
				}
			}
		}
		
		switch (accion.id) {
		case IZQUIERDA:
			y--;
			break;
		case DERECHA:
			y++;
			break;
		case ARRIBA:
			x--;
			break;
		case ABAJO:
			x++;
			break;
		}

		// Comprobacion de que no se sale de los limites del circuito
		if ((x < 0) || (x > circuitoNuevo.length - 1) || (y < 0) || (y > circuitoNuevo.length - 1)) {
			return null;
		}

		// Comprobacion de si esa casilla esta disponible en el circuito
		if ((circuitoNuevo[x][y]) == 0) {
			switch (accion.id) {
			case IZQUIERDA:
				circuitoNuevo[x][y + 1] = 0;
				circuitoNuevo[x][y] = accion.coche;
				break;

			case DERECHA:
				circuitoNuevo[x][y - 1] = 0;
				circuitoNuevo[x][y] = accion.coche;
				break;

			case ARRIBA:
				circuitoNuevo[x + 1][y] = 0;
				circuitoNuevo[x][y] = accion.coche;
				break;

			case ABAJO:
				circuitoNuevo[x - 1][y] = 0;
				circuitoNuevo[x][y] = accion.coche;
				break;
			}

			return new Estado(circuitoNuevo);
		}
		

		return null;

	}

	public ArrayList<Accion> posiblesAcciones(Estado estado) {

		int[][] circuito = new int[this.n][this.n];
		estado.cloneCircuito(circuito);
		ArrayList<Accion> acciones = new ArrayList<Accion>();

		for (int i = 0; i < circuito.length; i++) {
			for (int j = 0; j < circuito.length; j++) {

				if ((circuito[i][j] != 0) && (circuito[i][j] != -1)) {

					
					if (i < circuito.length - 1) {
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

		// Obtenemos la posicion del coche al que se la aplica la accion
		for (int i = 0; i < circuitoNuevo.length; i++) {
			for (int j = 0; j < circuitoNuevo.length; j++) {
				if (circuitoNuevo[i][j] == accion.coche) {
					x = i;
					y = j;
				}
			}

		}

		switch (accion.id) {
		case IZQUIERDA:
			y--;
			break;
		case DERECHA:
			y++;
			break;
		case ARRIBA:
			x--;
			break;
		case ABAJO:
			x++;
			break;
		}

		if ((x < 0) || (x > circuitoNuevo.length - 1) || (y < 0) || (y > circuitoNuevo.length - 1)) {
			return Double.POSITIVE_INFINITY;
		}

		if (circuitoNuevo[x][y] == 0) {
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
				if (nCochesFinal == this.nCars)
					return true;

				// Comprueba que en dicha posicion haya un coche
				// if ((circuitoNuevo[i][j] != 0) && (circuitoNuevo[i][j] != -1)) {
				// Contamos los coches que hay
				// antes -> nCoches++;
				// System.out.println( nCoches + " : Coches NOFINAL ");

				// si la i (filas) es igual a la longitud de la matriz, el coche estara en la
				// ultima fila

//				if (i == (circuitoNuevo.length) - 1) {
//					nCochesFinal++;
//					System.out.println(nCochesFinal + " : Coches FINAL   ");
//					// antes if (nCochesFinal == nCoches) {
//					if (nCochesFinal == this.nCars) {
//						return true;
//					}
//				}
				// }
			}
		}
		// Si la cuenta de los coches que han llegado a la ultima fila es igual al
		// numero total de coches, habremos llegado al estado final.
		return false;

	}

	public double heuristica(Estado estado) {

		// Calculamos la suma de las distancias en linea recta de cada coche para llegar
		// a la ultima fila
		int[][] circuitoNuevo = new int[this.n][this.n];
		estado.cloneCircuito(circuitoNuevo);
		double distancia = 0;

		for (int i = 0; i < circuitoNuevo.length; i++) {
			for (int j = 0; j < circuitoNuevo.length; j++) {
				if ((circuitoNuevo[i][j] != 0) && (circuitoNuevo[i][j] != -1)) {
					distancia = distancia + ((circuitoNuevo.length) - i);
				}
			}
		}
		System.out.println("----------------------------------> Distancia Heuristica 1 : " + distancia);
		return distancia;
	}

}
