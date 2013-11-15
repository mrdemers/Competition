package ld25;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ld25.entities.Player;
import ld25.levels.CastleLevel;
import ld25.levels.Level;
import ld25.levels.StartingLevel;
import ld25.menu.Menu;
import ld25.menu.PrologueMenu;
import ld25.menu.StartMenu;
import ld25.menu.YouLostMenu;
import ld25.menu.YouWonMenu;

public class Component extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320, HEIGHT = 240, SCALE = 2;
	private Thread t;
	private boolean running = false;
	private VolatileImage image;
	private InputHandler input = new InputHandler();
	Player p = new Player(0, 0, 0);
	public Level l;
	public Menu menu;
	public static Level nextLevel;
	public static Menu nextMenu;
	int switchTime = 0;

	public Component() {
		addKeyListener(input);
		addFocusListener(input);
		p.addInput(input);
		//menu = new StartMenu(input);
		l = new CastleLevel();
		Level.addEntity(p);
	}

	public static void switchLevel(Level level) {
		nextLevel = level;
	}

	public static void switchMenu(Menu menu) {
		nextMenu = menu;
	}

	public void update() {
		if (nextLevel != null) {
			l = nextLevel;
			if (l instanceof StartingLevel) {
				p.hasBoot = p.hasClub = p.hasShark = false;
				p.health = 30;
				p.dead = false;
			}
			Level.addEntity(p);
			switchTime = 40;
			Sound.switchlevel.play();
			nextLevel = null;
		}
		if (nextMenu != null) {
			if (nextMenu instanceof YouWonMenu)
				nextMenu = new YouWonMenu(input);
			if (nextMenu instanceof YouLostMenu) {
				nextMenu = new YouLostMenu(input);
			}
			menu = nextMenu;
			nextMenu = null;
		}
		if (switchTime > 0) {
			switchTime--;
		}
		if (menu != null) {
			if (menu != null) {
				menu.update();
				if (menu instanceof StartMenu) {
					if (((StartMenu) menu).dead) {
						stop();
						System.exit(0);
					}
				} else if (menu instanceof PrologueMenu) {
					if (((PrologueMenu)menu).dead) {
						Component.switchLevel(new StartingLevel());
						menu = null;
					}
				} else if (menu instanceof YouLostMenu) {
					if (((YouLostMenu)menu).dead) {
						Component.switchLevel(new StartingLevel());
						menu = null;
					}
				}
			}
		}
		if (l != null && menu==null)
			l.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		if (image == null) {
			image = createVolatileImage(WIDTH, HEIGHT);
		}
		{
			Graphics2D g = image.createGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			if (switchTime > 0) {
				g.setColor(Color.white);
				g.drawString("Going to the " + l.name, WIDTH / 2 - l.name.length() * 9, HEIGHT / 2 - 4);
			} else if (l != null && menu== null) {
				l.render(g);
			} else if (menu != null) {
				menu.render(g);
			}
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
		bs.show();
	}

	public void run() {
		long lastTime = System.currentTimeMillis();
		double unprocessed = 0;
		double secondsPerUpdate = 1 / 60.0;
		int updates = 0;
		int fps = 0;
		while (running) {
			long currentTime = System.currentTimeMillis();
			long passed = currentTime - lastTime;
			lastTime = currentTime;
			unprocessed += passed / 1000.0;
			boolean updated = false;
			while (unprocessed > secondsPerUpdate) {
				update();
				updated = true;
				unprocessed -= secondsPerUpdate;
				if (updates >= 60) {
					System.out.println("Updates: " + updates + ", FPS: " + fps);
					updates = 0;
					fps = 0;
				}
			}
			render();
			fps++;
			if (updated) {
				updates++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void start() {
		if (running)
			return;
		t = new Thread(this);
		t.start();
		running = true;
	}

	public void stop() {
		if (!running)
			return;
		try {
			t.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		running = false;
	}

	public static void main(String[] args) {
		Component game = new Component();

		JFrame frame = new JFrame("Caluminator");
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(game, BorderLayout.CENTER);

		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		frame.setLocationRelativeTo(null);
		frame.requestFocus();
		frame.setVisible(true);
		game.start();
	}

}
