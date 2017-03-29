package serial;

import java.io.Serializable;

public class Recogible implements Serializable{
	
	int receptor;

	public Recogible(int receptor) {
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
