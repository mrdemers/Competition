package ld25.levels;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ld25.Component;
import ld25.entities.Entity;
import ld25.entities.Mob;
import ld25.entities.Player;
import ld25.menu.YouLostMenu;

public class Level {
	public List<Entity> entities = new ArrayList<Entity>();
	public static List<Entity> toAddEntities = new ArrayList<Entity>();
	public String name;
	int width;
	int height;
	public int xScroll, yScroll;
	Player p;
	int numKilled = 0;
	float startX, startY;
	private boolean killed;
	private int deadTime =0;
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public static void addEntity(Entity e) {
		toAddEntities.add(e);
	}
	
	@SuppressWarnings("unchecked")
	public void update() {
		if (killed) {
			deadTime++;
			if (deadTime == 120) {
				System.out.println("Dead");
				Component.switchMenu(new YouLostMenu());
				killed = false;
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update(this);
			if (e.dead) {
				entities.remove(i);
				if (e instanceof Player) {
					killed = true;
				}
				if (e instanceof Mob)
					numKilled++;
			}
		}
		for (int i = 0; i < toAddEntities.size(); i++) {
			Entity e = toAddEntities.remove(i--);
			if (e instanceof Player) {
				p = (Player)e;
				p.x = startX;
				p.y = startY;
				p.z = 0;
			}
			entities.add(e);
		}
		if (p != null) {
			int distFromCorrectScroll = (int)p.x-Component.WIDTH/2 - xScroll;
			xScroll += 1 * Math.signum(distFromCorrectScroll);
					
			if (xScroll < 0) {
				xScroll = 0;
			}
		}
		Collections.sort(entities);
	}
	
	public void render(Graphics2D g) {
		for (Entity e : entities) {
			e.render(g);
		}
	}
}
