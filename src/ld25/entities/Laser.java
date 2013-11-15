package ld25.entities;

import java.awt.Graphics2D;

import ld25.Art;

public class Laser extends Bullet {
	public Laser(float x, float y, float z, float dx, Mob owner) {
		super(x, y, z, dx, owner);
		damage = 10;
	}
	
	public void render(Graphics2D g) {
		int xDraw = (int)x - xScroll;
		int yDraw = (int)(y-z);
		if (dx > 0)
			g.drawImage(Art.get(32), xDraw, yDraw, null);
		else 
			g.drawImage(Art.getFlipped(32), xDraw, yDraw, null);
	}
	
}
