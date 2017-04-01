package rebels;

import java.io.Serializable;

public class MensajeID implements Serializable {
	public String data;

	public MensajeID(String data) {
		this.data = data;
	}

	public String getContenido() {
		return data;
	}

	public void setContenido(String data) {
		this.data = data;
	}
}

