package rebels;

import processing.core.PApplet;

public class Tiempo {

	private PApplet app;
	int comenzar = 0, parar = 0;
	boolean reproducir = false;

	/*
	 * Objeto para medir el tiempo con millis, utilizando PApplet
	 */
	public Tiempo(PApplet app) {
		this.app = app;
	}

	/*
	 * Empezar el conteo - Vuelve a empezar el conteo
	 */
	void empezar() {
		comenzar = app.millis();
		reproducir = true;
	}

	/*
	 * Detiene el reloj
	 */
	void detener() {
		parar = app.millis();
		reproducir = false;
	}

	/*
	 * Muestra el tiempo que esta pasando en el momento
	 */
	int timepoReproducido() {
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
	int second() {
		return (timepoReproducido() / 1000) % 60;
	}

	/*
	 * Devuelve los minutos reproducidos
	 */
	int minute() {
		return (timepoReproducido() / (1000 * 60)) % 60;
	}
}
