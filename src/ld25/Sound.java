package ld25;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	public static Sound jump = load("/snd/jump.wav");
	public static Sound kick = load("/snd/kick.wav");
	public static Sound hit = load("/snd/hit.wav");
	public static Sound laser = load("/snd/laser.wav");
	public static Sound hurt = load("/snd/hurt.wav");
	public static Sound jumpedon = load("/snd/jupmedon.wav");
	public static Sound pickup = load("/snd/pickup.wav");
	public static Sound waterball = load("/snd/waterball.wav");
	public static Sound doorbreak = load("/snd/doorbreak.wav");
	public static Sound heal = load("/snd/heal.wav");
	public static Sound win = load("/snd/win.wav");
	public static Sound lose = load("/snd/lose.wav");
	public static Sound switchlevel = load("/snd/switch.wav");
	
	public static Sound load(String fileName) {
		Sound sound = new Sound();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
			sound.clip = AudioSystem.getClip();
			sound.clip.open(ais);
		} catch (Exception e) {
			System.out.println("Couldnt read sound: " + fileName);
			e.printStackTrace();
		}
		return sound;
	}

	private Clip clip;

	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
