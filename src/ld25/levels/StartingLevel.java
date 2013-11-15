package ld25.levels;

import java.awt.Color;
import java.awt.Graphics2D;

import ld25.Art;
import ld25.Component;
import ld25.Sound;

public class StartingLevel extends Level {
	boolean doorAlive = true;
	boolean standing = false;
	public StartingLevel() {
		super(640, 240);
		startX = 30;
		startY = 175;
		name = "castle";
	}
	
	public void update() {
		super.update();
		if (xScroll > 320) {
			xScroll = 320;
		}
		if (p.x > 545 && p.use && p.hasClub && doorAlive) {
			doorAlive = false;
			Sound.doorbreak.play();
		}
		if (doorAlive) {
			if (p.x > 550) {
				p.x = 550;
			}
		} else {
			if (p.x > 560) {
				Component.switchLevel(new IceLevel());
			}
		}
		if (p.x > 295 && p.x < 305 && p.z > -5 && p.z < 5 && !p.hasClub) {
			standing = true;
			if (p.use) {
				p.hasClub = true;
				Sound.pickup.play();
			}
		} else {
			standing = false;
		}
	}

	public void render(Graphics2D g) {
		if (p == null) return;
		g.drawImage(Art.start, -xScroll, 0, null);
		if (doorAlive) {
			g.drawImage(Art.door, -xScroll, 0, null);
		}
		super.render(g);
		if (!p.hasClub)
			g.drawImage(Art.get(10 * 3), -xScroll + 300, (int)(150 + Math.sin(System.nanoTime()/500000000.0) * 5), null);
		if (standing) {
			g.setColor(Color.white);
			g.drawString("Press e to pick up club", p.x-xScroll-50, p.y - 10);
		}
		g.drawImage(Art.bigWall, -xScroll, 0, null);
	}
}
