package ld25.entities;

import java.awt.Graphics2D;

import ld25.Art;
import ld25.Sound;
import ld25.levels.Level;

public class Dolphin extends Mob {

	public Dolphin(float x, float y, float z) {
		super(x, y, z);
		w = 20;
		h = 12;
		maxSpeed = 0.8f;
		health = 20;
	}
	int shootTime = 100;
	public void update(Level level) {
		super.update(level);
		for (Entity e : level.entities) {
			if (e instanceof Player) {
				if (e.x < x) flip = true;
				if (Math.abs(e.x-x) > 150 && hurtTime == 0) 
					dx = Math.signum(e.x-x);
				else if (hurtTime == 0) dx = 0;
				else flip = false;
				if (shootTime == 0 && hurtTime == 0) {
					shootTime = 60 + (int)(Math.random() * 60);
					Sound.waterball.play();
					Level.addEntity(new WaterBullet(x,y,z,(flip?-5:5),this));
				}
			}
		}
		if (shootTime > 0)
			shootTime--;
	}
	
	public void render(Graphics2D g) {
		int xDraw = (int)x - xScroll;
		int yDraw = (int)(y-z) + (int)(Math.sin(System.currentTimeMillis()/130)*2);
		if (flip) 
			g.drawImage(Art.getFlipped(52), xDraw, yDraw, null);
		else
			g.drawImage(Art.get(52), xDraw, yDraw, null);
	}
}
