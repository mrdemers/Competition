package ld25.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import ld25.Art;
import ld25.InputHandler;

public class PrologueMenu extends Menu {
	String[] text = { "You are an evil villain,", "living in an evil castle,", "surrounded by an evil moat of lava." };
	String line1 = "", line2 = "", line3 = "";
	int place = 0;
	int updates = 0;
	int time = 0;
	int frame = 0;

	public PrologueMenu(InputHandler input) {
		super(input);
	}

	public void update() {
		if (frame == 0 || frame == 1 || frame == 2) {
			if (place < 100 && updates == 0) {
				
				place++;
				if (place >= text[0].length())
					line1 = text[0];
				else
					line1 = text[0].substring(0, place);
				if (place >= text[0].length() + text[1].length())
					line2 = text[1];
				else if (line1.length() == text[0].length())
					line2 = text[1].substring(0, place - text[0].length());
				if (place >= text[0].length() + text[1].length() + text[2].length())
					line3 = text[2];
				else if (line2.length() > 0 && line2.length() == text[1].length())
					line3 = text[2].substring(0, place - text[0].length() - text[1].length());
			}
		}
		updates++;
		if (frame == 1) {
			if (line2.length() == text[1].length() && line3.length() == 0) {
				if (updates == 60) {
					updates = 0;
				}
			} else if (updates == 2) {
				updates = 0;
			}
			if (line3.length() == text[2].length()) {
				if (!stolen)
					heroX--;
				else
					heroX++;
				if (heroX == 210) stolen = true;
				if (stolen && heroX > 320) {
					frame = 2;
					text[0] = "Now you must seek vengance";
					text[1] = "";
					text[2] = "";
					line1 = "";
					line2 = "";
					line3 = "";
					place = 0;
				}
			}
		} else if (updates == 2) {
			updates = 0;
		}
		
		if (frame == 2) {
			lastTime++;
			if (lastTime > 120) {
				dead = true;
			}
		}
		
		if (line3.length() == text[2].length() && frame == 0) {
			time++;
			if (time > 120) {
				frame = 1;
				text = new String[] { "You were about", "to marry a lovely princess", "when this \"hero\" stole her" };
				place = 0;
				line1 = "";
				line2 = "";
				line3 = "";
			}
		}
	}
	public boolean dead;
	int heroX = 320;
	boolean stolen;
	int lastTime = 0;
	public void render(Graphics2D g) {
		if (frame == 0) {
			g.drawImage(Art.background, 0, 0, null);
			if (line2.length() == text[1].length()) {
				g.drawImage(Art.castle, 0, 0, null);
			}
			if (line3.length() == text[2].length()) {
				g.drawImage(Art.moat, 0, 0, null);
			}
		} else if (frame == 1 || frame == 2) {
			g.drawImage(Art.start, 0, 0, null);
			g.drawImage(Art.get(0), 180, 175, null);
			if (line2.length() == text[1].length() && frame == 1)
				if (stolen)
					g.drawImage(Art.get(3, 1), heroX-20, 175, null);
				else
					g.drawImage(Art.get(3, 1), 200, 175, null);
			if (line3.length() == text[2].length() && frame == 1)
				if (!stolen)
					g.drawImage(Art.getFlipped(60 + heroX/10%4), heroX, 175,null);
				else
				g.drawImage(Art.get(60+heroX/10%4), heroX, 175, null);
		}
		g.setColor(Color.black);
		g.setFont(new Font("Arial", 0, 13));
		int x = 30;
		if (line1 != null) {
			g.drawString(line1, x, 160);
		}
		if (line2 != null) {
			g.drawString(line2, x, 180);
		}
		if (line3 != null) {
			g.drawString(line3, x, 200);
		}
	}
}
