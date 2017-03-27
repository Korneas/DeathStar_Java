package rebels;

import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Sound {

	private Minim minim;

	private AudioPlayer[] musica;
	private AudioSample[] samples;

	private AudioPlayer cancion;

	public Sound(PApplet app) {

		minim = new Minim(app);

		musica = new AudioPlayer[5];
		samples = new AudioSample[6];

		musica[0] = minim.loadFile("musica/themeSong.mp3", 1024);
		musica[1] = minim.loadFile("musica/rebelTheme.mp3", 1024);

		samples[0] = minim.loadSample("musica/xWingBlaster.wav", 1024);
		samples[1] = minim.loadSample("musica/yWingBlaster.wav", 1024);
		samples[2] = minim.loadSample("musica/r2-d2.wav", 1024);
		samples[3] = minim.loadSample("musica/pasoPantalla.wav", 1024);

		cancion = musica[0];

		cancion.setBalance(0);
	}

	public void reproducir() {
		if (!cancion.isPlaying()) {
			cancion.play();
		}

		if (cancion.position() == cancion.length()) {
			cancion.pause();
			cancion.rewind();
		}
	}

	public void parar() {
		cancion.pause();
		cancion.rewind();
	}

	public void disparoX() {
		samples[0].trigger();
	}

	public void disparoY() {
		samples[1].trigger();
	}

	public void r2d2() {
		samples[2].trigger();
	}

	public void tieBomb() {

	}
	
	public void pasoPantalla(){
		samples[3].trigger();
	}

	public void setCancion(int num) {
		cancion.pause();
		cancion.rewind();

		cancion = musica[num];
	}
}
