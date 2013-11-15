package ld25.entities;

import java.awt.Graphics2D;

import ld25.Art;
import ld25.levels.Level;

public class Dog extends Mob {
	public Dog(float x, float y, float z) {
		super(x, y, z);
		w = 20;
		h = 12;
		maxSpeed = 1.2f;
		health = 14;
	}
	
	public void update(Level level) {
		super.update(level);
		for (Entity e : level.entities) {
			if (e instanceof Player) {
				double dist = dist2D(e);
				if (dist2D(e) < 200 && hurtTime == 0) {
					double dir = Math.atan2(z - e.z, x - e.x);
					dx = (float) Math.cos(dir) * -maxSpeed;
					dz = (float) Math.sin(dir) * -maxSpeed;
					if (dist < 10 && e.y + e.h > y + h -3) {
						((Player) e).hurt(3);
						e.dx = 1.5f * dx;
						e.dz = 1.5f * dz;
						e.dy = -1;
					}
				} else if (hurtTime==0){
					dx = 0;
					dz = 0;
				}
			}
		}
	}
	
	public void render(Graphics2D g) {
		super.render(g);
		int xDraw = (int) x - xScroll;
		int yDraw = (int) (groundY - h - (groundY - (y+h)) - z)+yScroll;
		int animFrame = walkTime/10%4;
		if (!flip) {
			if (hurtTime > 0) {
				g.drawImage(Art.get(4 + 10 * 4), xDraw, yDraw, null);
			} else {
				g.drawImage(Art.get(animFrame + 10 * 4), xDraw, yDraw, null);
			}
		} else {
			if (hurtTime > 0) {
				g.drawImage(Art.getFlipped(4+10*4), xDraw, yDraw, null);
			} else {
				g.drawImage(Art.getFlipped(animFrame+10*4), xDraw, yDraw, null);
			}
		}
	}
}
