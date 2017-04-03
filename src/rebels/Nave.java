package rebels;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Nave {

	private PApplet app;
	private PVector pos, vel, a;
	private int num;
	private float x, y, topVel;
	private PImage nav;
	private float uniVel;

	public Nave(PApplet app, PImage nav, int num) {
		super();
		this.app = app;
		this.nav = nav;
		this.num = num;

		x = app.width / 2;
		y = 300;

		topVel = 4;
		uniVel = 1;

		pos = new PVector(x, y);
		vel = new PVector(0, 0);
	}

	public void pintar() {
		app.imageMode(PApplet.CENTER);
		app.image(nav, pos.x, pos.y);
	}

	public void update(PVector pos2) {

		a = PVector.sub(pos2, pos);
		a.setMag((float) 0.2);

		vel.add(a);
		vel.limit(topVel);
		pos.add(vel);

		if (PVector.dist(pos, pos2) <= 3) {
			vel.setMag(0);
		}

	}

	public void validar(float x2, float y2) {

	}

	public void setPos(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
	}

	public float getPosX() {
		return pos.x;
	}

	public float getPosY() {
		return pos.y;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public float getUniVel() {
		return uniVel;
	}

	public void aumentarVel(float suma) {
		uniVel += suma;
	}

}
