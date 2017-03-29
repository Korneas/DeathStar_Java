package rebels;

import processing.core.PApplet;
import processing.core.PVector;

public class Bullet {

	private PApplet app;
	private PVector pos, dir, vel, a;
	private float x, y, topVel;
	private int[] col;

	public Bullet(PApplet app, float x, float y, int[] col) {
		this.app = app;
		this.x = x;
		this.y = y;
		this.col = col;

		topVel = 15;

		pos = new PVector(x, y);
		vel = new PVector(0, 0);
		dir = new PVector(x, -50);
	}

	public void pintar() {
		app.stroke(col[0], col[1], col[2]);
		app.noFill();
		app.line(pos.x, pos.y + 3, pos.x, pos.y - 3);
		app.noStroke();
		app.fill(255);
	}

	public void move() {
		a = PVector.sub(dir, pos);
		a.setMag((float) 0.2);

		vel.add(a);
		vel.limit(topVel);
		pos.add(vel);
	}

	public float getPosY() {
		return pos.y;
	}
}
