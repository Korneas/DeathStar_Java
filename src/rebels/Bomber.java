package rebels;

import processing.core.PApplet;
import processing.core.PImage;

public class Bomber extends Elemento {

	public Bomber(PApplet app, PImage elem) {
		super(app, elem);
		heal = 100;
	}

	@Override
	public void pintar() {
		app.imageMode(PApplet.CENTER);
		app.image(elem, pos.x, pos.y);
	}

}
