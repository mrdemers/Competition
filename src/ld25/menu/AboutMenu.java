package ld25.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import ld25.Art;
import ld25.Component;
import ld25.InputHandler;

public class AboutMenu extends Menu {
	int delayTime = 100;
	public String[] lines = {"Caluminator was made by Matthew Demers", "in 48 hours from scratch", "for the 25th Ludum Dare compo"};
	public AboutMenu(InputHandler input) {
		super(input);
	}
	
	public void update() {
		if (delayTime-- <= 0 && input.keys[KeyEvent.VK_SPACE]) {
			Component.switchMenu(new StartMenu(input));
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(Art.background, 0, 0, null);
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", 0, 12));
		for (int i = 0; i < lines.length; i++) {
			g.drawString(lines[i], 60, 50 + i * 20);
		}
	}
}
