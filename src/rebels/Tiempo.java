package rebels;

import processing.core.PApplet;

public class Tiempo {

	private PApplet app;
	private int comenzar = 0, parar = 0;
	private boolean reproducir = false;

	/*
	 * Objeto para medir el tiempo con millis, utilizando PApplet
	 */
	public Tiempo(PApplet app) {
		this.app = app;
	}

	/*
	 * Empezar el conteo - Vuelve a empezar el conteo
	 */
	public void empezar() {
		comenzar = app.millis();
		reproducir = true;
	}

	/*
	 * Detiene el reloj
	 */
	public void detener() {
		parar = app.millis();
		reproducir = false;
	}

	/*
	 * Muestra el tiempo que esta pasando en el momento
	 */
	public int timepoReproducido() {
		int tiempo;
		if (reproducir) {
			tiempo = (app.millis() - comenzar);
		} else {
			tiempo = (parar - comenzar);
		}
		return tiempo;
	}

	/*
	 * Devuelve los segundos reproducidos
	 */
	public int second() {
		return (timepoReproducido() / 1000) % 60;
	}

	/*
	 * Devuelve los minutos reproducidos
	 */
	public int minute() {
		return (timepoReproducido() / (1000 * 60)) % 60;
	}
}
