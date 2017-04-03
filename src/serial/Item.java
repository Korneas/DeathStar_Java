package serial;

import java.io.Serializable;

public class Item implements Serializable {
	
	private int receptor;
	private int tipo;

	public Item(int receptor,int tipo) {
		super();
		this.receptor = receptor;
		this.tipo = tipo;
	}

	public int getReceptor() {
		return receptor;
	}

	public void setReceptor(int receptor) {
		this.receptor = receptor;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}