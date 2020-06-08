package problema;

import java.util.ArrayList;
import java.util.Random;
import busqueda.*;

//import busqueda.AlgAnchura;
//import busqueda.AlgBusqueda;
//import busqueda.AlgProfundidad;
//import busqueda.AEstrella;
//import busqueda.AlgoPrimeroMejor;

public class Practica1 {

	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);
		int ncoches = Integer.parseInt(args[1]);
		int seed = Integer.parseInt(args[2]);
		String alg = args[4];

		int[][] circuito = getProblemInstance(n, ncoches, seed);
		Problema problema = new Problema(circuito, n, ncoches, seed);

		System.out.println(" Estado Inicial ");
		printMaze(circuito);

		AlgBusqueda algBusq;
		if (null == alg) {
			System.out.println("Parametros mal introducidos");
			return;
		} else {
			switch (alg) {
			case "BreathFirst":
				algBusq = new AlgAnchura();
				algBusq.setProblema(problema);
				algBusq.busqueda();
				break;
			case "DepthFirst":
				algBusq = new AlgProfundidad();
				algBusq.setProblema(problema);
				algBusq.busqueda();
				break;
			case "AStar":
				algBusq = new AEstrella();
				algBusq.setProblema(problema);
				algBusq.busqueda();
				break;
			case "BestFirst":
				algBusq = new AlgPrimeroMejor();
				algBusq.setProblema(problema);
				algBusq.busqueda();
				break;
			default:
				System.out.println("Parametros mal introducidos");
				return;
			}
		}

		System.out.println("\t -> Tamaño matriz: " + n);
		System.out.println("\t -> Longitud Solucion:" + algBusq.getLongSol());
		System.out.println("\t -> Coste:" + algBusq.getCosteTotal());
		System.out.println("\t -> Nodos Generados: " + algBusq.getNodosGenerados());
		System.out.println("\t -> Nodos Expandidos: " + algBusq.getNodosExpandidos());
		System.out.println("\t -> Tiempo (milisegundos): " + algBusq.getTiempoBusqueda());

	}

	/**
	 * This method generates a new problem instance. Cells with value 0 means empty
	 * cells. Cells with value -1 are walls. Cells with value 1..n are occupied by
	 * the i-th car.
	 *
	 * @param n     size of the maze
	 * @param nCars number of cars
	 * @param seed  for the random generator
	 * @return a maze (problem instance)
	 *
	 */
	public static int[][] getProblemInstance(int n, int nCars, int seed) {
		int[][] maze = new int[n][n];
		Random gen = new Random(seed);
		// number of walls
		int nWalls = (int) (n * (n - 2) * 0.2);
		// placing walls
		for (int i = 0; i < nWalls; i++) {
			maze[gen.nextInt(n - 2) + 1][gen.nextInt(n)] = -1;
		}

		// placing cars, labelled as 1, 2, ..., nCars
		if (nCars > n) {
			System.out.println("\n** Error **, number of cars must be <= dimension of maze!!\n");
			System.exit(0);
		}
		ArrayList<Integer> list = new ArrayList<Integer>(n);
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		int pos;
		for (int c = 0; c < nCars; c++) {
			pos = list.remove(gen.nextInt(list.size()));
			maze[0][pos] = c + 1;
		}

		return maze;
	}

	public static void printMaze(int[][] maze) {

		int n = maze[0].length;

		// upper row
		System.out.println();
		for (int i = 0; i < n; i++) {
			System.out.print("--");
		}
		System.out.println("-");

		// maze content (by row)
		for (int j = 0; j < n; j++) {
			System.out.print("|");
			for (int i = 0; i < n; i++) {
				if (maze[j][i] == -1) {
					System.out.print("x|");
				} else if ((maze[j][i] != 0) && (maze[j][i] != -1)) {
					System.out.print(maze[j][i] + "|");
				} else {
					System.out.print(" |");
				}
			}
			System.out.println("");
		}
		// lower row
		for (int i = 0; i < n; i++) {
			System.out.print("--");
		}
		System.out.println("-");

	}

}
