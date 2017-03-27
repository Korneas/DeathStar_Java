package rebels;

import java.io.*;
import java.net.*;
import java.util.Observable;

public class Comunicacion extends Observable implements Runnable {

	public MulticastSocket mSocket;
	private final int PORT = 5000;
	private final String GROUP_ADDRESS = "226.24.6.8";
	private boolean life = true;
	private boolean identificado;
	private int id;
	private String ANDROID_ADDRESS;

	public Comunicacion() {

		try {
			mSocket = new MulticastSocket(PORT);
			InetAddress host = InetAddress.getByName(GROUP_ADDRESS);
			mSocket.joinGroup(host);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			autoID();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo para hacer autoidentificacion en un grupo Multicast
	 * 
	 * @throws IOException
	 */
	private void autoID() throws IOException {
		try {
			enviar(new MensajeID("Hola soy nuevo"), GROUP_ADDRESS);
			mSocket.setSoTimeout(500);
			while (!identificado) {
				DatagramPacket dPacket = recibir();
				if (dPacket != null) {
					MensajeID msg = (MensajeID) deserialize(dPacket.getData());
					String contenido = msg.getContenido();

					if (contenido.contains("soy:")) {
						String[] division = contenido.split(":");
						int idLimite = Integer.parseInt(division[1]);
						if (idLimite >= id) {
							id = idLimite + 1;
						}
					}
				}
			}
		} catch (SocketTimeoutException e) {
			if (id == 0) {
				id = 1;
			}
			identificado = true;
			System.out.println("Mi id es:" + id);
			mSocket.setSoTimeout(0);
		}
	}

	/**
	 * Metodo para enviar paquetes mediante conexion UDP
	 * 
	 * @param info
	 *            Object
	 * @param ipAdrs
	 *            String
	 * @throws IOException
	 */
	public void enviar(Object info, String ipAdrs) throws IOException {
		byte[] data = serialize(info);
		InetAddress host = InetAddress.getByName(ipAdrs);
		DatagramPacket dPacket = new DatagramPacket(data, data.length, host, PORT);

		mSocket.send(dPacket);
	}

	/**
	 * Metodo para recibir paquetes mediante conexion UDP y retornar el paquete
	 * recibido
	 * 
	 * @return DatagramPacket
	 * @throws IOException
	 */
	private DatagramPacket recibir() throws IOException {
		byte[] data = new byte[1024];
		DatagramPacket dPacket = new DatagramPacket(data, data.length);
		mSocket.receive(dPacket);
		return dPacket;
	}

	/**
	 * Metodo para serializar un objeto
	 * 
	 * @param o
	 *            Object
	 * @return byte[]
	 */
	private byte[] serialize(Object o) {
		byte[] info = null;
		try {
			ByteArrayOutputStream baOut = new ByteArrayOutputStream();
			ObjectOutputStream oOut = new ObjectOutputStream(baOut);
			oOut.writeObject(o);
			info = baOut.toByteArray();

			oOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * Metodo para deserializar un byte array
	 * 
	 * @param b
	 *            byte[]
	 * @return Object
	 */
	private Object deserialize(byte[] b) {
		Object data = null;
		try {
			ByteArrayInputStream baOut = new ByteArrayInputStream(b);
			ObjectInputStream oOut = new ObjectInputStream(baOut);
			data = oOut.readObject();

			oOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public void run() {
		while (life) {
			if (mSocket != null) {
				try {
					if (!mSocket.isClosed()) {
						DatagramPacket dPacket = recibir();
						if (dPacket != null) {
							if (deserialize(dPacket.getData()) instanceof MensajeID) {
								MensajeID msg = (MensajeID) deserialize(dPacket.getData());
								String contenido = msg.getContenido();

								if (contenido.contains("soy nuevo")) {
									// Responder
									enviar(new MensajeID("soy:" + id), GROUP_ADDRESS);
								}
							}
							
							if (!dPacket.getAddress().toString().contains(GROUP_ADDRESS)) {
								ANDROID_ADDRESS = dPacket.getAddress().toString();
							}

							if (!(deserialize(dPacket.getData()) instanceof MensajeID)) {
								setChanged();
								notifyObservers(deserialize(dPacket.getData()));
								clearChanged();
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int getId() {
		return id;
	}

	public String getGroupAddress() {
		return GROUP_ADDRESS;
	}

	public String getAndroidAddress() {
		return ANDROID_ADDRESS;
	}
}