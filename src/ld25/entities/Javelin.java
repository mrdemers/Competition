package ld25.entities;

import java.awt.Graphics2D;

import ld25.Art;

public class Javelin extends Bullet {

	public Javelin(float x, float y, float z, float dx, float dz, Mob owner) {
		super(x, y, z, dx, owner);
		this.dz = dz;
		w = 30;
		h = 2;
		damage = 10;
		radius = 40;
	}

	public void render(Graphics2D g) {
		int xDraw = (int) x - xScroll;
		int yDraw = (int) (y - z);
		if (dx > 0)
			g.drawImage(Art.get(34), xDraw, yDraw, null);
		else
			g.drawImage(Art.getFlipped(34), xDraw, yDraw, null);
	}
}
