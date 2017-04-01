package rebels;

import java.io.Serializable;

public class Message implements Serializable {
	
	public int emisor;
	public String msg;
	
	public Message(int emisor, String msg) {
		super();
		this.emisor = emisor;
		this.msg = msg;
	}

	public int getEmisor() {
		return emisor;
	}

	public void setEmisor(int emisor) {
		this.emisor = emisor;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
