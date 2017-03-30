package rebels;

import processing.core.PApplet;
import processing.core.PImage;

public class Asteroide extends Elemento {

	private float angulo;

	public Asteroide(PApplet app, PImage elem) {
		super(app, elem);
		heal = 40;
	}

	@Override
	public void pintar() {
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		angulo += 0.02;
		app.rotate(angulo);
		app.imageMode(PApplet.CENTER);
		app.image(elem, 0, 0);
		app.popMatrix();
	}
}
