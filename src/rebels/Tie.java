package rebels;

import processing.core.PApplet;
import processing.core.PImage;

public class Tie extends Elemento {

	public Tie(PApplet app, PImage elem) {
		super(app, elem);
		heal = 60;
	}

	@Override
	public void pintar() {
		app.imageMode(PApplet.CENTER);
		app.image(elem, pos.x, pos.y);
	}

}
