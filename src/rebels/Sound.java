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

		musica = new AudioPlayer[10];
		samples = new AudioSample[10];

		musica[0] = minim.loadFile("musica/themeSong.mp3", 1024);
		musica[1] = minim.loadFile("musica/rebelTheme.mp3", 1024);

		samples[0] = minim.loadSample("musica/xWingBlaster.wav", 1024);
		samples[1] = minim.loadSample("musica/yWingBlaster.wav", 1024);
		samples[2] = minim.loadSample("musica/aWingBlaster.mp3", 1024);
//		samples[3] = minim.loadSample("musica/eWingBlaster.wav", 1024);
		samples[4] = minim.loadSample("musica/r2-d2.wav", 1024);
		samples[5] = minim.loadSample("musica/pasoPantalla.wav", 1024);

		cancion = musica[0];

		cancion.setBalance(0);
		cancion.setGain(-40);
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
	
	public void triggerSample(int num){
		samples[num].trigger();
		//System.out.println("Trigger "+num);
	}

	public void setCancion(int num) {
		cancion.pause();
		cancion.rewind();

		cancion = musica[num];
	}
}
