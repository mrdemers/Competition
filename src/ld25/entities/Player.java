package ld25.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import ld25.Art;
import ld25.InputHandler;
import ld25.Sound;
import ld25.levels.Level;

public class Player extends Mob {
	private InputHandler input;
	private int coolDown;
	public boolean use = false, hasClub = false, hasBoot = true, hasShark = false;
	private int maxHealth = 3000000;

	public Player(float x, float y, float z) {
		super(x, y, z);
		w = 20;
		h = 24;
		health = 3000000;
		blocksMotion = false;
	}

	public void addInput(InputHandler in) {
		input = in;
	}
	boolean gotHurt;
	public void update(Level level) {
		boolean up = input.keys[KeyEvent.VK_UP] || input.keys[KeyEvent.VK_W];
		boolean down = input.keys[KeyEvent.VK_DOWN] || input.keys[KeyEvent.VK_S];
		boolean left = input.keys[KeyEvent.VK_LEFT] || input.keys[KeyEvent.VK_A];
		boolean right = input.keys[KeyEvent.VK_RIGHT] || input.keys[KeyEvent.VK_D];
		boolean jump = input.keys[KeyEvent.VK_SPACE];
		boolean useItem = input.keys[KeyEvent.VK_E];

		if (up && !down) {
			dz += speed;
			dz = Math.min(maxSpeed, dz);
		} else if (down && !up) {
			dz -= speed;
			dz = Math.max(-maxSpeed, dz);
		} else {
			if (hurtTime == 0)
				dz = 0;
		}
		if (right && !left) {
			dx += speed;
			dx = Math.min(maxSpeed, dx);
		} else if (left && !right) {
			dx -= speed;
			dx = Math.max(-maxSpeed, dx);
		} else {
			if (hurtTime == 0)
				dx = 0;
		}
		if (jump && grounded) {
			dy = -20;
			grounded = false;
			Sound.jump.play();
		}
		if (hurtTime > 0) {
			gotHurt = true;
		} else {
			if (gotHurt) {
				flip = !flip;
			}
			gotHurt = false;
		}
		if (!grounded && dy > .5) {
			for (Entity e : level.entities) {
				if (e instanceof Mob && !(e == this)) {
					Mob m = (Mob) e;
					if (m instanceof Shark && dist2D(e) < 20) {
						m.dead = true;
						hasShark = true;
						hasBoot = false;
						hasClub = false;
					} else if (y + h > e.y && dist2D(e) < 10) {
						if (m.hurt(1))
							dy = -1f;
						System.out.println("HI");
					}
				}
			}
		}
		if (useItem && coolDown == 0) {
			if (hasClub)
				coolDown = 10;
			else if (hasBoot)
				coolDown = 10;
			else if (hasShark)
				coolDown = 10;
			this.use = true;
			if (hasShark) {
				Sound.laser.play();
				Level.addEntity(new Laser(x, y+20, z, (flip?-5:5), this));
			} else {
				for (Entity e : level.entities) {
					if (e instanceof Mob && !(e == this)) {
						Mob m = (Mob) e;
						boolean inFront = false;
						if (flip && m.x < x) inFront = true;
						if (!flip && m.x > x) inFront = true;
						if (dist2D(e) < 30 && inFront) {
							double dir = Math.atan2(m.z-z, m.x - x);
							if (hasClub) {
								Sound.hit.play();
								if (m.hurt(3000)) {
									m.dx = (float) (Math.cos(dir) * 1.5);
									m.dz = (float) (Math.sin(dir) * 1.5);
									m.dy = -1;
								}
							} else if (hasBoot) {
								Sound.kick.play();
								if (m.hurt(5000)) {
									double amt = 2.5;
									if (m instanceof Hero) amt = 1.5;
									m.dx = (float) (Math.cos(dir) * amt);
									m.dz = (float) (Math.sin(dir) * amt);
									m.dy = -2;
								}
							}
						}
					}
				}
			}
		}
		if (coolDown > 0) {
			coolDown--;
			if (coolDown == 0) {
				this.use = false;
			}
		}
		super.update(level);
	}

	public void render(Graphics2D g) {
		super.render(g);
		g.setColor(Color.red);
		g.fillRect(50, 207, (int) (health / (double) maxHealth * 100), 10);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", 0, 10));
		g.drawString("Health: ", 10, 215);
		g.drawRect(50, 207, 100, 10);
		int xDraw = (int) x - xScroll;
		int yDraw = (int) (groundY - h - (groundY - (y + h)) - z) - yScroll;
		int animFrame = 0;
		if (use && hasClub) {
			animFrame = (30 - coolDown) / 10 % 3;
			animFrame += 10;
		} else if (use && hasBoot) {
			animFrame = 2 - ((45 - coolDown) / 15 % 3);
			animFrame += 16;
		} else if (hasShark) {
			animFrame = 51;
		} else {
			animFrame = walkTime / 10 % 6;
			if (!grounded)
				animFrame = 6;
		}

		if (!flip) {
			if (hurtTime > 0 && !hasShark) {
				g.drawImage(Art.getFlipped(5 + 10), xDraw, yDraw, null);
			} else {
				g.drawImage(Art.get(animFrame), xDraw, yDraw, null);
			}
		} else {
			if (hurtTime > 0 && !hasShark) {
				g.drawImage(Art.get(5 + 10), xDraw, yDraw, null);
			} else {
				g.drawImage(Art.getFlipped(animFrame), xDraw, yDraw, null);
			}
		}
	}
}
