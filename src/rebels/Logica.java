package rebels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import serial.Item;
import serial.MessageSerial;
import serial.Usuario;

public class Logica implements Observer {

	private PApplet app;
	private Comunicacion c;
	private String GROUP_ADDRESS;
	private String ANDROID_ADDRESS;
	private int id;

	private Tiempo time;
	private Sound snd;
	private Nave nav;
	private PImage[] elementos;
	private PImage[] asteroides;
	private PImage[] naves;
	private PImage[] fondo;
	private float[] posFondo;

	private int pantalla;
	private int score;
	private boolean start, startAndroid, apodoEscrito, race;
	private PFont fuenteBold, fuenteLight;
	private String apodo;
	private int animacionMensaje;
	private int opaMen = -50;

	private PVector pos;
	private float sigX, vel;
	private float distance, maxDistance;
	private boolean der, izq;

	private ArrayList<Elemento> elem;
	private ArrayList<Asteroide> ast;
	private ArrayList<Bullet> gun;
	private int[][] colors;

	public Logica(PApplet app) {
		super();
		this.app = app;

		c = new Comunicacion();
		Thread nt = new Thread(c);
		nt.start();

		c.addObserver(this);

		time = new Tiempo(app);
		snd = new Sound(app);

		fuenteBold = app.loadFont("Montserrat-SemiBold-36.vlw");
		fuenteLight = app.loadFont("Montserrat-Light-36.vlw");

		GROUP_ADDRESS = c.getGroupAddress();
		id = c.getId();

		if (id == 3) {
			try {
				c.enviar(new MessageSerial(id, 0, "comenzar"), GROUP_ADDRESS);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		fondo = new PImage[10];

		fondo[0] = app.loadImage("Logo.png");
		fondo[1] = app.loadImage("ApodoWriter.png");
		fondo[2] = app.loadImage("Continue.png");
		fondo[3] = app.loadImage("Nebulosa.jpg");
		fondo[4] = app.loadImage("BasicStars.png");
		fondo[5] = app.loadImage("BiggerStars.png");
		fondo[6] = app.loadImage("Leia.png");
		fondo[7] = app.loadImage("Instrucciones.png");

		posFondo = new float[6];

		posFondo[0] = -550;
		posFondo[1] = -550;
		posFondo[2] = -550;
		posFondo[3] = -3050;
		posFondo[4] = -3050;
		posFondo[5] = -3050;

		naves = new PImage[4];

		naves[0] = app.loadImage("xWing.png");
		naves[1] = app.loadImage("yWing.png");
		naves[2] = app.loadImage("aWing.png");

		elementos = new PImage[4];

		elementos[0] = app.loadImage("TIE.png");
		elementos[1] = app.loadImage("Bomber.png");
		elementos[2] = app.loadImage("R2D2.png");
		elementos[3] = app.loadImage("Strom.png");

		asteroides = new PImage[4];

		asteroides[0] = app.loadImage("Asteroide1.png");
		asteroides[1] = app.loadImage("Asteroide2.png");
		asteroides[2] = app.loadImage("Asteroide3.png");
		asteroides[3] = app.loadImage("Asteroide4.png");

		apodo = "";

		sigX = app.width / 2;
		vel = 5;
		pos = new PVector(sigX, 600);

		elem = new ArrayList<Elemento>();
		ast = new ArrayList<Asteroide>();
		gun = new ArrayList<Bullet>();

		colors = new int[5][3];

		colors[0][0] = 255;
		colors[0][1] = 0;
		colors[0][2] = 0;
		colors[1][0] = 255;
		colors[1][1] = 255;
		colors[1][2] = 0;
		colors[2][0] = 0;
		colors[2][1] = 255;
		colors[2][2] = 0;

		start = true;

	}

	public void ejecutar() {

		app.imageMode(PApplet.CENTER);

		fondoAnimado();

		switch (pantalla) {
		case 0:
			inicio();
			break;
		case 1:
			instrucciones();
			break;
		case 2:
			game();
			break;
		case 3:
			results();
			break;
		}
	}

	public void fondoAnimado() {
		// ANIMACION EN LOOP
		// Nebulosa
		app.image(fondo[3], app.width / 2, posFondo[0]);
		app.image(fondo[3], app.width / 2, posFondo[3]);
		if (pantalla == 2) {
			posFondo[0] += nav.getUniVel() / 2;
			posFondo[3] += nav.getUniVel() / 2;
		} else {
			posFondo[0] += .5;
			posFondo[3] += .5;
		}
		if (posFondo[0] >= 1950) {
			posFondo[0] = -3050;
		}
		if (posFondo[3] >= 1950) {
			posFondo[3] = -3050;
		}

		// Estrellas pequeñas
		app.image(fondo[4], app.width / 2, posFondo[1]);
		app.image(fondo[4], app.width / 2, posFondo[4]);
		if (pantalla == 2) {
			posFondo[1] += nav.getUniVel();
			posFondo[4] += nav.getUniVel();
		} else {
			posFondo[1] += 1;
			posFondo[4] += 1;
		}
		if (posFondo[1] >= 1950) {
			posFondo[1] = -3050;
		}
		if (posFondo[4] >= 1950) {
			posFondo[4] = -3050;
		}

		// Estrellas grandes
		app.image(fondo[5], app.width / 2, posFondo[2]);
		app.image(fondo[5], app.width / 2, posFondo[5]);
		if (pantalla == 2) {
			posFondo[2] += (nav.getUniVel() * 2);
			posFondo[5] += (nav.getUniVel() * 2);
		} else {
			posFondo[2] += 2;
			posFondo[5] += 2;
		}

		if (posFondo[2] >= 1950) {
			posFondo[2] = -3050;
		}
		if (posFondo[5] >= 1950) {
			posFondo[5] = -3050;
		}

		if (pantalla == 2) {
			if (app.frameCount % 360 == 0) {
				if (nav.getUniVel() == 1) {
					nav.aumentarVel((float) 1);
				} else {
					nav.aumentarVel((float) 2);
				}
				System.out.println(nav.getUniVel());
			}
		}
	}

	public void inicio() {
		app.image(fondo[0], app.width / 2, 100);
		app.textAlign(PApplet.CENTER);
		if (!startAndroid) {
			app.textFont(fuenteLight);
			app.textSize(20);
			app.fill(255);
			app.text("Esperando al administrador\nen Android", app.width / 2, 450);
		}
		if (!start) {
			app.text("Esperando a los\ndemás jugadores", app.width / 2, 350);
		} else if (start && startAndroid) {
			if (!apodoEscrito) {
				app.textFont(fuenteBold);
				app.textSize(36);
				app.fill(230, 50, 120);
				app.text(apodo, app.width / 2, 470);
				app.image(fondo[1], app.width / 2, 500);
				app.textSize(26);
				app.fill(255);
				app.text("Pulsa ENTER para confirmar", app.width / 2, 650);
			} else {
				app.textFont(fuenteLight);
				app.textSize(20);
				app.textAlign(PApplet.LEFT);
				app.fill(230, 50, 120);
				app.text("Bienvenido, Capitán " + apodo, (app.width / 2) - 225, 200);
				app.fill(255);

				// ANIMACION MENSAJE DE LEIA

				String mensajeLeia = "En una galaxia muy lejana\nnecesitamos su ayuda\ncon el fin de destruir\nla Estrella de la Muerte\nnosotros los rebeldes lo\nayudaremos. Acompañenos\na ver los datos de su nave";
				char[] msgLeia = mensajeLeia.toCharArray();
				String mensajeAnimado = "";
				for (int i = 0; i < animacionMensaje; i++) {
					mensajeAnimado += msgLeia[i];
				}

				app.text(mensajeAnimado, (app.width / 2) - 200, 250);

				if (app.frameCount % 2 == 0 && animacionMensaje < mensajeLeia.toCharArray().length) {
					animacionMensaje++;
				}

				app.image(fondo[6], 450, 480);

				if (opaMen < 255) {
					opaMen += 1;
				}

				app.tint(255, opaMen);
				app.image(fondo[2], app.width / 2, 645);
				app.tint(255, 255);
				app.textAlign(PApplet.CENTER);
			}
		}
	}

	public void instrucciones() {
		app.imageMode(PApplet.CENTER);
		app.image(fondo[7], app.width / 2, app.height / 2);
		nav.pintar();
		String[] puntos = new String[4];
		puntos[0] = "-50 pts";
		puntos[1] = "-200 pts";
		puntos[2] = "+250 pts";
		puntos[3] = "+25 pts";

		app.textAlign(PApplet.CENTER);
		app.textFont(fuenteLight);
		app.textSize(20);

		for (int i = 0; i < elementos.length; i++) {
			app.image(elementos[i], 150 + (100 * i), 520);
			app.text(puntos[i], 150 + (100 * i), 580);
		}

		if (opaMen < 255) {
			opaMen += 1;
		}

		app.tint(255, opaMen);
		app.image(fondo[2], app.width / 2, 645);
		app.tint(255, 255);

	}

	public void game() {

		for (int i = 0; i < gun.size(); i++) {
			gun.get(i).pintar();
			gun.get(i).move();

			if (gun.get(i).getPosY() <= -50) {
				gun.remove(i);
			}
		}

		for (int i = 0; i < elem.size(); i++) {
			if (elem.size() > 0) {
				elem.get(i).pintar();
				elem.get(i).mover();

				for (int j = 0; j < gun.size() - 1; j++) {
					if (elem.get(i).colision(gun.get(j).getPos())) {
						elem.get(i).restarHeal(gun.get(j).getDamage());
						gun.remove(j);
					}
				}

				if (!(elem.get(i) instanceof R2D2) && !(elem.get(i) instanceof Strom)) {
					if (elem.get(i).getHeal() <= 0) {
						if (elem.get(i) instanceof Tie) {
							score += 50;
						} else if (elem.get(i) instanceof Bomber) {
							score += 150;
						}
						elem.remove(i);
					}
				} else if (elem.get(i).getPosY() > app.height + 100) {
					elem.remove(i);
				}
			}
		}

		if (app.frameCount % 240 == 0) {
			ast.add(new Asteroide(app, asteroides[(int) app.random(4)]));
		}

		for (int i = 0; i < ast.size(); i++) {
			ast.get(i).pintar();
			ast.get(i).mover();

			for (int j = 0; j < gun.size(); j++) {
				if (ast.size() > 0) {
					if (ast.get(i).colision(gun.get(j).getPos())) {
						ast.get(i).restarHeal(gun.get(j).getDamage());
						score += 5;
						gun.remove(j);
					}
				}
			}
			
			if (ast.get(i).getPosY() > app.height + 100) {
				ast.remove(i);
			}

			if (ast.get(i).getHeal() <= 0) {
				ast.remove(i);
			}

		}

		nav.pintar();
		nav.update(pos);

		if (der)

		{
			pos.x += vel;
		} else if (izq) {
			pos.x -= vel;
		}

		app.textAlign(PApplet.LEFT);
		app.text("Puntuacion: " + PApplet.nf(score, 4), 20, 30);
		app.strokeWeight(1);
		app.stroke(230, 50, 120);
		app.noFill();
		app.line(20, 35, 200, 35);

		app.textAlign(PApplet.RIGHT);
		app.text(time.minute() + ":" + PApplet.nf(time.second(), 2), app.width - 20, 30);
		app.line(app.width - 100, 35, app.width - 20, 35);
		app.noStroke();
		app.textAlign(PApplet.CENTER);

	}

	public void results() {

	}

	public void tecla() {
		if (app.keyPressed) {

			switch (pantalla) {
			case 0:
				if (!apodoEscrito) {

					if (app.key == PApplet.BACKSPACE) {
						if (apodo.length() > 0) {
							apodo = apodo.substring(0, apodo.length() - 1);
						}
					} else if (apodo.length() < 8) {
						if (app.key != PApplet.CODED) {
							apodo += app.key;
						}
					}

					if (app.key == PApplet.ENTER) {
						apodoEscrito = true;
						snd.reproducir();

						if (apodo.contains("Han Solo")) {

						} else {
							int r = (int) app.random(0, 3);
							nav = new Nave(app, naves[r], r);
						}

						try {
							c.enviar(new Usuario(id, apodo), ANDROID_ADDRESS);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				if (apodoEscrito) {

					if (app.key == 'f' && animacionMensaje >= 170) {
						snd.triggerSample(5);
						pantalla = 1;
						opaMen = -50;
					} else if (app.key == 'f' && animacionMensaje < 170) {
						animacionMensaje = 170;
						opaMen = 255;
					}

				}
				break;
			case 1:
				if (app.key == 'f') {
					snd.triggerSample(5);
					nav.setPos(app.width / 2, 600);
					ast.add(new Asteroide(app, asteroides[(int) app.random(4)]));
					time.empezar();
					pantalla = 2;
				}
				break;
			case 2:
				if (app.key == 'f') {
					switch (nav.getNum()) {
					case 0:
						gun.add(new Bullet(app, nav.getPosX() - 28, nav.getPosY() - 3, colors[nav.getNum()], 5));
						gun.add(new Bullet(app, nav.getPosX() + 28, nav.getPosY() - 3, colors[nav.getNum()], 5));
						gun.add(new Bullet(app, nav.getPosX() - 33, nav.getPosY() - 9, colors[nav.getNum()], 5));
						gun.add(new Bullet(app, nav.getPosX() + 33, nav.getPosY() - 9, colors[nav.getNum()], 5));
						break;
					case 1:
						gun.add(new Bullet(app, nav.getPosX() - 3, nav.getPosY() - 54, colors[nav.getNum()], 10));
						gun.add(new Bullet(app, nav.getPosX() + 3, nav.getPosY() - 54, colors[nav.getNum()], 10));
						break;
					case 2:
						gun.add(new Bullet(app, nav.getPosX() - 34, nav.getPosY() - 18, colors[nav.getNum()], 10));
						gun.add(new Bullet(app, nav.getPosX() + 34, nav.getPosY() - 18, colors[nav.getNum()], 10));
						break;
					}
					snd.triggerSample(nav.getNum());
				}

				if (app.keyCode == PApplet.RIGHT && pos.x < app.width) {
					der = true;
				}

				if (app.keyCode == PApplet.LEFT && pos.x > 0) {
					izq = true;
				}

				break;
			}

		}
	}

	public void release() {
		if (pantalla == 2) {
			if (app.keyCode == PApplet.RIGHT) {
				der = false;
			}

			if (app.keyCode == PApplet.LEFT) {
				izq = false;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof MessageSerial) {
			MessageSerial mensaje = (MessageSerial) arg;
			if (mensaje.getEmisor() == 3) {
				if (mensaje.getMsg().contains("comenzar")) {
					start = true;
				}
			}

			if (mensaje.getEmisor() == 10) {
				if (mensaje.getMsg().contains("online")) {
					String android = c.getAndroidAddress();
					System.out.println("Mensaje de " + android);
					ANDROID_ADDRESS = c.getAndroidAddress();
					startAndroid = true;
				}
			}
		}

		if (arg instanceof Item) {
			Item item = (Item) arg;
			if (item.getReceptor() == id) {
				switch (item.getTipo()) {
				case 1:
					elem.add(new R2D2(app, elementos[2]));
					break;
				case 2:
					elem.add(new Tie(app, elementos[0]));
					break;
				case 3:
					elem.add(new Bomber(app, elementos[1]));
					break;
				case 4:
					elem.add(new Strom(app, elementos[3]));
					break;
				}

			}
		}
	}

}
