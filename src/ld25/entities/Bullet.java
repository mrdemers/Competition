package ld25.entities;

import java.awt.Graphics2D;

import ld25.levels.Level;

public class Bullet extends Entity{
	int damage;
	Mob owner;
	int radius = 10;
	public Bullet(float x, float y, float z, float dx, Mob owner) {
		super(x, y, z);
		this.dx = dx;
		this.owner = owner;
		w = 10;
		h = 2;
	}
	
	public void update(Level level) {
		if (dead) return;
		if (x > 1500 || x < -100) dead = true;
		super.update(level);
		move(level);
		for (Entity e : level.entities) {
			if (e == owner) break;
			if (e instanceof Mob) {
				if (dist(e) < radius) {
					this.dead = true;
					((Mob)e).hurt(damage);
					e.dx = Math.signum(dx);
					e.dz = Math.signum(dz);
					break;
				}
			}
		}
	}
	
	public void render(Graphics2D g) { 
		super.render(g);
	}
	
}
