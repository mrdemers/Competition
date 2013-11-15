package ld25.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import ld25.Sound;
import ld25.levels.Level;
import ld25.particles.DamageParticle;

public class Mob extends Entity {
	public final int groundY = 175;
	float speed = 0.3f;
	float maxSpeed = 1.0f;
	public int health;
	int hurtTime;
	int walkTime = 0;

	public Mob(float x, float y, float z) {
		super(x, y, z);
		blocksMotion = false;
	}

	public void update(Level level) {
		super.update(level);
		if (move(level)) {
			
		} else {
			
		}
		if (dx > .2 || dx < -.2 || dz > .2 || dz < -.2) {
			walkTime++;
		} else {
			walkTime = 0;
		}
		if (dx > .1) {
			flip = false;
		} else if (dx < -.1) {
			flip = true;
		}
		dy += 0.1f;
		if (y + h >= 175) {
			y = 175 - h;
			dy = 0;
			grounded = true;
		}

		if (z > 30) {
			z = 30;
			dz = 0;
		} else if (z < -30) {
			z = -30;
			dz = 0;
		}
		if (hurtTime > 0)
			hurtTime--;
	}

	public boolean move(Level level) {
		super.move(level);
		for (Entity e : level.entities) {
			if (e.blocksMotion) {
				int myY = (int) (y + h);
				int eY = (int) (e.y+e.h);
				if (dist2D(e) < w / 2 + e.w / 2 && (myY < (int)e.y || (int)y > eY-e.h/2)) {
					x -= dx;
					y -= dy;
					z -= dz;
					return false;
				}
			}
		}
		return true;
	}

	public void render(Graphics2D g) {
		super.render(g);
		g.setColor(new Color(0, 0, 0, .5f));
		int distFromGround = groundY - (int) y - h;
		double percent = 1 - distFromGround / 75.0;
		int width = (int) (w * percent);
		int height = (int) (w / 2 * percent);
		int xDisplace = w / 2 - width / 2;
		int yDisplace = w / 2 - height / 2;
		g.clipRect(0, groundY - 30, 320, 60);
		g.fillOval((int) x + 6 + xDisplace - xScroll, groundY - 10 - (int) z + yDisplace - yScroll, width, height);
		g.setClip(null);
	}

	public boolean dead() {
		return dead;
	}

	public boolean hurt(int dmg) {
		if (hurtTime > 0)
			return false;
		this.health -= dmg;
		hurtTime = 30;
		Level.addEntity(new DamageParticle(x, y, z, dmg));
		if (health <= 0) {
			dead = true;
		}
		if (dmg == 1) {
			Sound.jumpedon.play();
		}
		if (this instanceof Player) {
			Sound.hurt.play();
		}
		return true;
	}

}
