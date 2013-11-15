package ld25.particles;

import java.awt.Color;
import java.awt.Graphics2D;

import ld25.Sound;
import ld25.levels.Level;

public class DamageParticle extends Particle {

	int dmg;
	public DamageParticle(float x, float y, float z, int amt) {
		super(x, y, z);
		dmg = amt;
		dy = -2;
		dx = (float)Math.random();
		life = 30;
		if (amt < 0) Sound.heal.play();
	}
	
	public void update(Level level) {
		super.update(level);
		dy += .1f;
	}

	public void render(Graphics2D g) {
		super.render(g);
		g.setColor(Color.red);
		if (dmg < 0) g.setColor(Color.green);
		g.drawString("" + Math.abs(dmg), x-xScroll, y-yScroll);
	}
}
