package rebels;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
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
	private PImage[] naves;
	private PImage[] fondo;
	private float[] posFondo;

	private int pantalla;
	private int score;
	private boolean start, apodoEscrito;
	private PFont fuenteBold, fuenteLight;
	private String apodo;
	private int animacionMensaje;

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
				c.enviar(new Message(id, "comenzar"), GROUP_ADDRESS);
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
		naves[2] = app.loadImage("");

		apodo = "";

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
		posFondo[0] += .5;
		posFondo[3] += .5;
		if (posFondo[0] >= 1950) {
			posFondo[0] = -3050;
		}
		if (posFondo[3] >= 1950) {
			posFondo[3] = -3050;
		}

		// Estrellas pequeñas
		app.image(fondo[4], app.width / 2, posFondo[1]);
		app.image(fondo[4], app.width / 2, posFondo[4]);
		posFondo[1] += 1;
		posFondo[4] += 1;
		if (posFondo[1] >= 1950) {
			posFondo[1] = -3050;
		}
		if (posFondo[4] >= 1950) {
			posFondo[4] = -3050;
		}

		// Estrellas grandes
		app.image(fondo[5], app.width / 2, posFondo[2]);
		app.image(fondo[5], app.width / 2, posFondo[5]);
		posFondo[2] += 2;
		posFondo[5] += 2;
		if (posFondo[2] >= 1950) {
			posFondo[2] = -3050;
		}
		if (posFondo[5] >= 1950) {
			posFondo[5] = -3050;
		}
	}

	public void inicio() {
		app.image(fondo[0], app.width / 2, 100);
		app.textAlign(PApplet.CENTER);
		if (!start) {
			app.textFont(fuenteLight);
			app.textSize(20);
			app.fill(255);
			app.text("Esperando a los\ndemás jugadores", app.width / 2, 400);
		} else {
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

				app.tint(255, 255);
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
	}

	public void game() {

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
							nav = new Nave(app, naves[0]);
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
						snd.pasoPantalla();
						pantalla = 1;
					} else if (app.key == 'f' && animacionMensaje < 170) {
						animacionMensaje = 170;
					}

				}
				break;
			case 1:
				if (app.key == 'f') {
					snd.pasoPantalla();
					pantalla = 2;
				}
				break;
			case 2:
				if (app.key == 'f') {

				}

				if (app.keyCode == PApplet.RIGHT) {

				}

				if (app.keyCode == PApplet.LEFT) {

				}
				break;
			}

		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Message) {
			Message mensaje = (Message) arg;

			if (mensaje.getEmisor() == 3) {
				if (mensaje.getMsg().contains("comenzar")) {
					start = true;
				}
			}
		}
	}

}
