package ld25.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import ld25.Art;
import ld25.Component;
import ld25.InputHandler;
import ld25.Sound;

public class YouWonMenu extends Menu {
	public YouWonMenu(InputHandler input) {
		super(input);
		Sound.win.play();
	}
	
	public YouWonMenu() {
		super(null);
		//Only to be used to switch the menu
	}
	
	int delay = 0;
	
	public void update() {
		if (delay++ > 60) {
			if (input.keys[KeyEvent.VK_SPACE]) {
				Component.switchMenu(new StartMenu(input));
			}
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(Art.background, 0, 0, null);
		g.setColor(Color.white);
		g.drawString("Congratulations, you won!", 100, 60);
		g.drawString("Press space to return to main menu", 100, 90);
	}
}
