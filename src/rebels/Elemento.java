package rebels;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public abstract class Elemento {

	protected PApplet app;
	protected PVector pos, dir, vel, a;
	protected float x, topVel;
	protected PImage elem;

	public Elemento(PApplet app, PImage elem) {
		this.app = app;
		this.elem = elem;

		x = app.random(40, app.height - 40);

		pos = new PVector(x, -80);
		vel = new PVector(0, 0);
		dir = new PVector(x, app.height + 100);

		topVel = 4;
	}

	public abstract void pintar();

	public void mover() {
		a = PVector.sub(dir, pos);
		a.setMag((float) 0.2);

		vel.add(a);
		vel.limit(topVel);
		pos.add(vel);
	}

	public boolean colision(PVector pos2) {
		if (PVector.dist(pos, pos2) <= 10) {
			return true;
		}
		return false;
	}

	public float getPosY() {
		return pos.y;
	}

}
