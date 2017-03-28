package serial;

import java.io.Serializable;

public class Resultados implements Serializable {
	public int puntuacion;
	public int recogibles;
	public int tiempo;
	public int obstaculos;
	public int disparos;
	public int velMax;

	public Resultados(int puntuacion, int recogibles, int tiempo, int obstaculos, int disparos, int velMax) {
		super();
		this.puntuacion = puntuacion;
		this.recogibles = recogibles;
		this.tiempo = tiempo;
		this.obstaculos = obstaculos;
		this.disparos = disparos;
		this.velMax = velMax;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public int getRecogibles() {
		return recogibles;
	}

	public void setRecogibles(int recogibles) {
		this.recogibles = recogibles;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
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