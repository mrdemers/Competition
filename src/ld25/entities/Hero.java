package ld25.entities;

import java.awt.Graphics2D;

import ld25.Art;
import ld25.levels.Level;

public class Hero extends Mob {

	public Hero(float x, float y, float z) {
		super(x, y, z);
		flip = true;
		w = 20;
		h = 24;
		health = 30;
	}

	int time = 0;
	boolean runAway;
	int runTime = 0;
	int attackTime = 0;

	public void update(Level level) {
		super.update(level);
		time++;
		if (time > 500) {
			attackTime--;
			if (hurtTime > 0 && !runAway) {
				runAway = true;
			}
			for (Entity e : level.entities) {
				if (e instanceof Player) {
					double dir = Math.atan2(z - e.z, x+w/2 - (e.x+e.w/2));
					if (!runAway) {
						if (dist2D(e) > 3 && hurtTime == 0) {
							dx = -(float) Math.cos(dir);
							dz = -(float) Math.sin(dir);
						} else if (attackTime <= 0 && hurtTime == 0 && dist2D(e) < 3) {
							((Player) e).hurt(5);
							attackTime = 200;
						}
					} else {
						if (hurtTime == 0) {
							runTime++;
							if (runTime == 50) {
								float dxx = -(float) (Math.cos(dir) * 5);
								float dzz = -(float) (Math.sin(dir) * 5);
								Level.addEntity(new Javelin(x, y, z, dxx, dzz, this));
								runTime = 0;
								runAway = false;
							} else {
								if (dist2D(e) < 10) {
									dx = -(float) Math.cos(dir);
									dz = -(float) Math.sin(dir);
								}
							}
						}
					}
				}
			}
		}
	}

	public void render(Graphics2D g) {
		super.render(g);
		int xDraw = (int) x - xScroll;
		int yDraw = (int) (groundY - h - (groundY - (y + h)) - z) + yScroll;
		int animFrame = 0;
		if (flip) {
			g.drawImage(Art.getFlipped(60 + animFrame), xDraw, yDraw, null);
		} else {
			g.drawImage(Art.get(60 + animFrame), xDraw, yDraw, null);
		}
	}
}
