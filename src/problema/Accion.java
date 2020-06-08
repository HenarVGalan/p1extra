package problema;

public class Accion {

	protected int coche;
	protected Movimiento id;

	public enum Movimiento {

		// Tipos de movimientos
		DERECHA("DERECHA"), IZQUIERDA("IZQUIERDA"), ARRIBA("ARRIBA"), ABAJO("ABAJO");

		private final String id;

		// Constructor
		Movimiento(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
	}

	Accion(Movimiento id, int coche) {
		this.id = id;
		this.coche = coche;
	}

	public String toString() {
		// return("( "+this.coche +" , "+ this.id+ " )");
		return ("- Coche:" + this.coche + "- Accion:" + this.id);
	}
}
