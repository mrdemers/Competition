package ld25.entities;

import java.awt.Graphics2D;

import ld25.Art;
import ld25.levels.Level;

public class Shark extends Mob {
	public Shark(float x, float y, float z) {
		super(x, y, z);
		w = 30;
		h = 12;
	}
	
	public void update(Level level) {
		super.update(level);
	}
	
	public void render(Graphics2D g) {
		int xDraw = (int)x - xScroll;
		int yDraw = (int)(y-z);
		g.drawImage(Art.get(50), xDraw, yDraw, null);
	}
}
