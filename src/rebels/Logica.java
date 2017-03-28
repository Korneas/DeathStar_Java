package rebels;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
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
	private boolean start, apodoEscrito, race;
	private PFont fuenteBold, fuenteLight;
	private String apodo;
	private int animacionMensaje;
	private int opaMen = -50;

	private PVector pos;
	private float sigX;
	private boolean der, izq;

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
		naves[2] = app.loadImage("aWing.png");

		elementos = new PImage[4];

		elementos[0] = app.loadImage("TIE.png");
		elementos[1] = app.loadImage("Bomber.png");
		elementos[2] = app.loadImage("R2D2.png");
		elementos[3] = app.loadImage("Strom.png");

		apodo = "";

		start = true;

		sigX = app.width / 2;
		pos = new PVector(sigX, 600);
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

		nav.pintar();
		nav.update(pos);

		if (der) {
			pos.x += 10;
		} else if (izq) {
			pos.x -= 10;
		}

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
							nav = new Nave(app, naves[(int) app.random(0, 3)]);
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
						snd.triggerSample(3);
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
					snd.triggerSample(3);
					nav.setPos(app.width / 2, 600);
					pantalla = 2;
				}
				break;
			case 2:
				if (app.key == 'f') {

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
	
	public void release(){
		if(pantalla==2){
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
