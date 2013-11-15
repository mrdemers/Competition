package ld25.levels;

import java.awt.Graphics2D;

import ld25.Art;
import ld25.Component;
import ld25.entities.Dog;
import ld25.entities.Health;
import ld25.entities.Shark;

public class ForestLevel extends Level {
	int checkpoint = 0;

	public ForestLevel() {
		super(960, 240);
		startX = 30;
		startY = 175;
		name = "forest";
		numKilled = 0;
		Level.addEntity(new Dog(320, 175, 0));
		Level.addEntity(new Dog(330, 175, -6));
		Level.addEntity(new Dog(310, 175, 9));
		Level.addEntity(new Dog(320, 175, 15));
		Level.addEntity(new Dog(310, 175, -25));
		Level.addEntity(new Health(350, 175, 30));
	}

	boolean added1;

	public void update() {
		super.update();
		if (numKilled > 4 && checkpoint == 0) {
			checkpoint = 1;
			Level.addEntity(new Dog(550, 175, 0));
			Level.addEntity(new Dog(540, 175, 20));
			Level.addEntity(new Dog(550, 175, -10));
			Level.addEntity(new Dog(570, 175, 10));
			Level.addEntity(new Dog(545, 175, -30));
		}
		if (numKilled > 11 && checkpoint == 1) {
			checkpoint = 2;
			Level.addEntity(new Health(870, 175, 30));
			Level.addEntity(new Shark(880, 150, 0));
		}
		if (checkpoint == 1) {
			if (xScroll > 280) xScroll = 280;
			if (p.x > 600) p.x = 600;
			if (p.x > 500 && !added1) {
				added1 = true;
				Level.addEntity(new Dog(400, 175, 10));
				Level.addEntity(new Dog(410, 175, -10));
			}
		}

		if (checkpoint == 0) {
			if (xScroll > 80) xScroll = 80;
			if (p.x > 400) p.x = 400;
		}
		
		if (checkpoint == 2) {
			if (xScroll > 640) xScroll = 640;
			if (p.x > 930) 
				Component.switchLevel(new WaterLevel());
		}
		if (p.hasShark) {
			if (p.x < 885) p.x = 885;
		} else if (p.x > 870) p.x = 870;
	}

	public void render(Graphics2D g) {
		g.drawImage(Art.forest, -xScroll, 0, null);
		super.render(g);
	}
}
