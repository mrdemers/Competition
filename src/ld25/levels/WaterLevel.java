package ld25.levels;

import java.awt.Graphics2D;

import ld25.Art;
import ld25.Component;
import ld25.entities.Dolphin;
import ld25.entities.Health;

public class WaterLevel extends Level {

	public WaterLevel() {
		super(1280, 240);
		name = "river";
		startX = 30;
		startY = 175;
		numKilled = 0;
		Level.addEntity(new Dolphin(200, 175, 0));
		Level.addEntity(new Dolphin(180, 175, 30));
	}
	int checkpoint = 0;
	boolean added1 = false;
	public void update() {
		super.update();
		if (numKilled > 1) {
			checkpoint = 1;
		}
		
		if (checkpoint == 1 && p.x > 300 && !added1) {
			Level.addEntity(new Dolphin(600, 175, -20));
			Level.addEntity(new Dolphin(650, 175, 20));
			Level.addEntity(new Dolphin(10, 175, 0));
			Level.addEntity(new Health(1200, 175, 30));
			added1 = true;
		}
		
		if (p.x > 1170 && p.hasShark) {
			p.hasShark = false;
			p.hasBoot = true;
			p.x = 1180;
		}
		if (p.x < 1180 && !p.hasShark) p.x = 1180;
		if (xScroll > 960) xScroll = 960;
		if (p.x > 1250) Component.switchLevel(new CastleLevel());
		
	}
	
	public void render(Graphics2D g) {
		g.drawImage(Art.water, -xScroll, 0, null);
		super.render(g);
	}
}
