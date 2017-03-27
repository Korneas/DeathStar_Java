package rebels;

import processing.core.PApplet;
import processing.core.PImage;

public class Nave {

	private PApplet app;
	private float x, y, vel;
	private PImage nav;

	public Nave(PApplet app, PImage nav) {
		super();
		this.app = app;
		this.nav = nav;

		x = app.width / 2;
		y = 300;
	}

	public void pintar() {
		app.imageMode(PApplet.CENTER);
		app.image(nav, x, y);
	}

	public void mover() {

	}

	public void validar(float x2, float y2) {

	}
}
