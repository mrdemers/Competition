package ld25.entities;

import java.awt.Graphics2D;

import ld25.Art;
import ld25.levels.Level;

public class TestMob extends Mob {
	public TestMob(float x, float y, float z) {
		super(x, y, z);
		w = 20;
		h = 14;
		maxSpeed = 0.6f;
		health = 10;
	}

	public void update(Level level) {
		super.update(level);
		for (Entity e : level.entities) {
			if (e instanceof Player) {
				if (dist2D(e) < 75) {
					double dir = Math.atan2(z - e.z, x - e.x);
					dx = (float) Math.cos(dir) * -maxSpeed;
					dz = (float) Math.sin(dir) * -maxSpeed;
				} else {
					dx = 0;
					dz = 0;
				}
			}
		}
	}

	public void render(Graphics2D g) {
		super.render(g);
		int xDraw = (int) x;
		int yDraw = (int) (groundY - h - (groundY - (y+h)) - z);
		if (!flip) {
			g.drawImage(Art.get(0 + 10), xDraw, yDraw, null);
		} else {
			g.drawImage(Art.getFlipped(0 + 10), xDraw, yDraw, null);
		}
	}
}
