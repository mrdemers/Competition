package ld25.entities;

import java.awt.Graphics2D;

import ld25.levels.Level;

public class Entity implements Comparable{
	public float x, y, z, dx, dy, dz;
	public int w, h;
	public boolean flip;
	public boolean grounded;
	public boolean dead;
	public int xScroll=-10000, yScroll;
	public boolean blocksMotion = false;
	public Entity(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void update(Level level) {
		//move(level);
		xScroll = level.xScroll;
		yScroll = level.yScroll;
	}
	
	public boolean move(Level level) {
		x += dx;
		y += dy;
		z += dz;
		return true;
	}
	
	public void render(Graphics2D g) {
		
	}
	
	public double dist(Entity other) {
		if (other == this) return 10000;
		int xDist = (int) ((x+w/2) - (other.x+other.w/2));
		int yDist = (int) ((y+h/2) - (other.y+other.h/2));
		int zDist = (int) (z - other.z);
		return Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist); 
	}
	
	public double dist2D(Entity other) {
		if (other == this) return 1000;
		int xDist = (int) ((x+w/2) - (other.x+other.w/2));
		int zDist = (int) (z - other.z);
		return Math.sqrt(xDist * xDist + zDist * zDist);
	}

	public int compareTo(Object o) {
		if (o == null) return -1;
		if (o instanceof Entity) {
			Entity e = (Entity)o;
			if (z > e.z) return -1;
			else if (z < e.z) return 1; 
		}
		return 0;
	}
}
