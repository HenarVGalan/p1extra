package problema;

import java.util.Arrays;

public class Estado {

	protected int[][] circuito;

	public Estado(int[][] circuito) {
		this.circuito = circuito;
	}

	public int[][] getCircuito() {
		return this.circuito;
	}

	public int[][] cloneCircuito(int[][] c) {
		for (int i = 0; i < this.circuito.length; i++) {
			for (int j = 0; j < this.circuito.length; j++) {
				c[i][j] = this.circuito[i][j];
			}
		}
		return c;
	}

	public boolean equals(Object otroEstado) {
		
		if (!(otroEstado instanceof Estado)) {
			//System.out.println("----> ï¿½ï¿½ï¿½ï¿½ Estas intentado comparar objetos DIFERENTES !!!! <----");
			return false;
		}

		if (Arrays.deepEquals(this.getCircuito(), ((Estado) otroEstado).getCircuito())) {
			 //System.out.println("----> ¡¡¡¡¡¡ Circuitos IGUALES !!!!!! <----");
			return true;
		} else
			return false;
	}

}
