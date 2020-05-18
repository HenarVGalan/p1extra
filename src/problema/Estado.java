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

	// Recorrer matriz con for (cloneCircuito)
	public int[][] cloneCircuito(int[][] c) {
		for (int i = 0; i < this.circuito.length; i++) {
			for (int j = 0; j < this.circuito.length; j++) {
				c[i][j] = this.circuito[i][j];
			}
		}
		return c;
	}

	@Override
	public boolean equals(Object otroEstado) {
		
		if (!(otroEstado instanceof Estado)) {
			System.out.println("----> ���� Estas intentado comparar objetos DIFERENTES !!!! <----");
			return false;
		}

		if (Arrays.deepEquals(this.getCircuito(), ((Estado) otroEstado).getCircuito())) {
			 System.out.println("----> ������ Circuitos IGUALES !!!!!! <----");
			return true;
		} else
			return false;
	}

}