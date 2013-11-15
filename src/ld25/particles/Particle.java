package ld25.particles;

import java.awt.Graphics2D;

import ld25.entities.Entity;
import ld25.levels.Level;

public class Particle extends Entity{
	int life;
	int timeAlive;
	public Particle(float x, float y, float z) {
		super(x, y, z);
		blocksMotion = false;
	}
	
	public void update(Level level) {
		super.update(level);
		move(level);
		timeAlive++;
		if (timeAlive > life) {
			dead = true;
		}
	}
	
	public void render(Graphics2D g) {
		super.render(g);
	}
}
