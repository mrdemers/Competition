package ld25.levels;

import java.awt.Graphics2D;

import ld25.Art;
import ld25.Component;
import ld25.Sound;
import ld25.entities.Health;
import ld25.entities.Seal;

public class IceLevel extends Level {
	int checkpoint = 0;

	public IceLevel() {
		super(1280, 240);
		Level.addEntity(new Seal(400, 150, 0));
		Level.addEntity(new Seal(390, 150, -20));
		Level.addEntity(new Seal(420, 150, 20));
		startX = 50;
		startY = 175;
		name = "ice lands";
	}

	boolean addedPoint1;
	boolean addedPoint2;
	boolean standing;
	public void update() {
		super.update();
		if (checkpoint == 0) {
			if (xScroll > 120)
				xScroll = 120;
			if (p.x > 416) {
				p.x = 416;
			}
		} else if (checkpoint == 1) {
			if (p.x > 420 && !addedPoint1) {
				Level.addEntity(new Seal(400, 150, -30));
				Level.addEntity(new Seal(390, 150, 20));
				Level.addEntity(new Seal(380, 150, 0));
				addedPoint1 = true;
			}
			if (xScroll > 240)
				xScroll = 240;
			if (p.x > 660)
				p.x = 660;
		} else if (checkpoint == 2) {
			if (p.x > 750 && !addedPoint2) {
				addedPoint2 = true;
				Level.addEntity(new Seal(720, 50, 0));
				Level.addEntity(new Seal(780, 20, -10));
				Level.addEntity(new Seal(750, 0, 10));
				Level.addEntity(new Health(800, 175, 30));
				if (xScroll > 580) {
					xScroll = 580;
				}
				if (p.x > 800) {
					p.x = 800;
				}
			}
		}

		if (checkpoint == 0 && numKilled > 2) {
			checkpoint = 1;
			Level.addEntity(new Seal(500, 150, -30));
			Level.addEntity(new Seal(490, 150, 20));
			Level.addEntity(new Seal(480, 150, 0));
		}
		if (checkpoint == 1 && numKilled > 8) {
			checkpoint = 2;
			Level.addEntity(new Seal(800, 175, 5));
			Level.addEntity(new Seal(720, 175, 30));
			Level.addEntity(new Seal(650, 175, -20));
			Level.addEntity(new Seal(740, 175, -10));
		}
		if (checkpoint == 2 && numKilled > 14) {
			checkpoint = 3;
		}
		if (xScroll > 960) {
			xScroll = 960;
		}
		if (!p.hasBoot && p.x > 995 && p.x < 1005 && p.z > -5 && p.z < 5) {
			standing = true;
			if (p.use) {
				Sound.pickup.play();
				p.hasBoot = true;
				p.hasClub = false;
				p.use = false;
			}
		} else {
			standing = false;
		}
		if (p.x > 1200) {
			Component.switchLevel(new ForestLevel());
		}
		if (p.x < 55)
			p.x = 55;
	}

	public void render(Graphics2D g) {
		if (p == null) return;
		g.drawImage(Art.ice, -xScroll, 0, null);
		if (!p.hasBoot)
			g.drawImage(Art.get(1 + 10 * 3), 1002-xScroll, 150, null);
		if (standing) {
			g.drawString("Press e to take Das Boot", p.x-xScroll-50, p.y-p.z);
		}
		super.render(g);
		g.drawImage(Art.otherWall, -xScroll, 0, null);
		g.drawImage(Art.tree, -xScroll, 0, null);
	}
}
