package ld25;

import java.applet.Applet;
import java.awt.BorderLayout;

public class CaluminatorApplet extends Applet{
	private static final long serialVersionUID = 1L;
	Component game = new Component();
	
	public void init() {
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
	}
	
	public void start() {
		game.start();
	}
	
	public void stop() {
		game.stop();
	}
}
