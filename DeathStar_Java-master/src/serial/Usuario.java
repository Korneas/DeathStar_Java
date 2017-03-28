package serial;

import java.io.Serializable;

public class Usuario implements Serializable {
	
	private int emisor;
	private String name;

	public Usuario(int emisor,String name) {
		super();
		this.emisor = emisor;
		this.name = name;
	}
	
	public int getEmisor() {
		return emisor;
	}

	public void setEmisor(int emisor) {
		this.emisor = emisor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}