package serial;

import java.io.Serializable;

public class MessageSerial implements Serializable {

	public int emisor;
	public int receptor;
	public String msg;

	public MessageSerial(int emisor, int receptor, String msg) {
		super();
		this.emisor = emisor;
		this.receptor = receptor;
		this.msg = msg;
	}

	public int getEmisor() {
		return emisor;
	}

	public void setEmisor(int emisor) {
		this.emisor = emisor;
	}

	public int getReceptor() {
		return receptor;
	}

	public void setReceptor(int receptor) {
		this.receptor = receptor;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}