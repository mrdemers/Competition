package ld25.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import ld25.levels.Level;
import ld25.particles.DamageParticle;

public class Health extends Entity {
	public Health(float x, float y, float z) {
		super(x, y, z);
	}
	
	public void update(Level level) {
		if (dead) return;
		super.update(level);
		for (Entity e : level.entities) {
			if (e instanceof Player) {
				Player p = (Player)e;
				if (dist(p) < 15) {
					this.dead = true;
					p.health+=10;
					if (p.health > 30) p.health = 30;
					Level.addEntity(new DamageParticle(p.x, p.y, p.z, -10));
				}
			}
		}
	}
	
	public void render(Graphics2D g) {
		if (dead) return;
		super.render(g);
		int xDraw = (int)x - xScroll;
		int yDraw = (int)(y-z);
		g.setColor(Color.white);
		g.fillRect(xDraw, yDraw, 10, 10);
		g.setColor(Color.red);
		g.drawLine(xDraw+5, yDraw, xDraw+5, yDraw+10);
		g.drawLine(xDraw, yDraw+5, xDraw+10, yDraw+5);
	}
}
