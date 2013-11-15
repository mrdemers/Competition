package ld25.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import ld25.Art;
import ld25.Component;
import ld25.InputHandler;
import ld25.Sound;

public class StartMenu extends Menu {
	public String[] options = {"Play", "About", "Exit"};
	int selected = 0;
	boolean upPressed = false;
	boolean downPressed = false;
	public boolean dead;
	int delayTime = 30;
	public StartMenu(InputHandler input) {
		super(input);
	}
	
	public void update() {
		if (delayTime-- > 0) return;
		if ((input.keys[KeyEvent.VK_W] || input.keys[KeyEvent.VK_UP]) && !upPressed) {
			selected--;
			if (selected < 0) selected = 2;
			upPressed = true;
			Sound.hit.play();
		}
		if ((input.keys[KeyEvent.VK_S] || input.keys[KeyEvent.VK_DOWN]) && !downPressed) {
			selected++;
			if (selected > 2) selected = 0;
			downPressed = true;
			Sound.hit.play();
		}
		
		if (!(input.keys[KeyEvent.VK_W] || input.keys[KeyEvent.VK_UP])) {
			upPressed = false;
		}
		if (!(input.keys[KeyEvent.VK_S] || input.keys[KeyEvent.VK_DOWN])) {
			downPressed = false;
		}
		
		if (input.keys[KeyEvent.VK_SPACE]) {
			if (selected == 0)
				Component.switchMenu(new PrologueMenu(input));
			else if (selected == 1) {
				Component.switchMenu(new AboutMenu(input));
			} else if (selected == 2) {
				dead = true;
			}
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(Art.startScreen, 0, 0, null);
		for (int i = 0; i < options.length; i++) {
			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial", 0, 10));
			g.fillRect(Component.WIDTH/2-options[i].length()*5-8, 100 + i * 20-10, options[i].length()*10, 15);
			g.setColor(Color.white);
			if (i == selected) {
				g.setColor(Color.red);
			}
			g.drawString(options[i], Component.WIDTH/2-options[i].length()*5, 100 + i * 20);
			g.setColor(Color.white);
			g.setFont(new Font("Times New Roman", 0, 9));
			g.drawString("Controls:", 246, 50);
			g.drawString("WASD to move", 235, 60);
			g.drawString("Space to jump", 238, 70);
			g.drawString("E to use", 250, 80);
		}
	}
}
