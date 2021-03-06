package rebels;

import processing.core.PApplet;
import processing.core.PImage;

public class Strom extends Elemento{
	
	private float angulo;

	public Strom(PApplet app, PImage elem) {
		super(app, elem);
	}

	@Override
	public void pintar() {
		app.pushMatrix();
		app.translate(pos.x, pos.y);
		angulo+=0.02;
		app.rotate(angulo);
		app.imageMode(PApplet.CENTER);
		app.image(elem, 0, 0);
		app.popMatrix();
	}

}
