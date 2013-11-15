package ld25.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import ld25.Art;
import ld25.InputHandler;
import ld25.Sound;

public class YouLostMenu extends Menu {
	public YouLostMenu(InputHandler input) {
		super(input);
		Sound.lose.play();
	}

	public YouLostMenu() {
		super(null);
	}

	int delay = 0;
	public boolean dead;

	public void update() {
		if (delay++ > 30) {
			if (input.keys[KeyEvent.VK_SPACE]) {
				dead = true;
			}
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(Art.background, 0, 0, null);
		g.setColor(Color.white);
		g.drawString("Too bad, you lost", 100, 50);
		g.drawString("Press space to try again", 100, 65);
	}
}
