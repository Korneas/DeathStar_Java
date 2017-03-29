package serial;

import java.io.Serializable;

public class Obstaculo implements Serializable {
	
	int receptor;

	public Obstaculo(int receptor) {
		super();
		this.receptor = receptor;
	}

	public int getReceptor() {
		return receptor;
	}

	public void setReceptor(int receptor) {
		this.receptor = receptor;
	}

}
