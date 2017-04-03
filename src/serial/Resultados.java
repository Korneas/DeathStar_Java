package serial;

import java.io.Serializable;

public class Resultados implements Serializable {
	public int emisor;
	public int puntuacion;
	public int distance;
	public int obstaculos;
	public int disparos;
	public int velMax;

	public Resultados(int emisor, int puntuacion, int distance, int obstaculos, int velMax) {
		super();
		this.emisor = emisor;
		this.puntuacion = puntuacion;
		this.distance = distance;
		this.obstaculos = obstaculos;
		this.velMax = velMax;
	}

	public int getEmisor() {
		return emisor;
	}

	public void setEmisor(int emisor) {
		this.emisor = emisor;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getObstaculos() {
		return obstaculos;
	}

	public void setObstaculos(int obstaculos) {
		this.obstaculos = obstaculos;
	}

	public int getDisparos() {
		return disparos;
	}

	public void setDisparos(int disparos) {
		this.disparos = disparos;
	}

	public int getVelMax() {
		return velMax;
	}

	public void setVelMax(int velMax) {
		this.velMax = velMax;
	}

}