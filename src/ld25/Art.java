package ld25;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	public static BufferedImage[][] spriteSheet = loadAndCut("/sprites.png", 32, 32);
	public static BufferedImage ice = load("/icelevel.png");
	public static BufferedImage start = load("/startlevel.png");
	public static BufferedImage door = load("/door.png");
	public static BufferedImage bigWall = load("/bigwall.png");
	public static BufferedImage otherWall = load("/otherwall.png");
	public static BufferedImage background = load("/background.png");
	public static BufferedImage castle = load("/castle.png");
	public static BufferedImage moat = load("/moat.png");
	public static BufferedImage tree = load("/tree.png");
	public static BufferedImage forest = load("/forest.png");
	public static BufferedImage startScreen = load("/startscreen.png");
	public static BufferedImage water = load("/water.png");
	public static BufferedImage castleRoom = load("/castleroom.png");
	
	public static BufferedImage load(String fileName) {
		try {
			return ImageIO.read(Art.class.getResource(fileName));
		} catch (IOException e) {
			System.out.println("Couldnt read " + fileName);
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage[][] loadAndCut(String fileName, int xCut, int yCut) {
		BufferedImage total = load(fileName);
		int w = total.getWidth();
		int h = total.getHeight();
		BufferedImage[][] result = new BufferedImage[w / xCut][h / yCut];
		for (int x = 0; x < w / xCut; x++) {
			for (int y = 0; y < h / yCut; y++) {
				result[x][y] = total.getSubimage(x * xCut, y * yCut, xCut, yCut);
			}
		}
		return result;
	}

	public static BufferedImage get(int i) {
		int y = i / spriteSheet.length;
		int x = i - y * spriteSheet.length;
		return spriteSheet[x][y];
	}

	public static BufferedImage get(int x, int y) {
		return spriteSheet[x][y];
	}

	/*
	 * Returns flipped copy with xFlip and not yFlip
	 */
	public static BufferedImage getFlipped(int i) {
		return getFlipped(i, true, false);
	}
	public static BufferedImage getFlipped(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				result.setRGB(x, y, img.getRGB(w - x - 1, y));
			}
		}
		return result;
	}
	
	public static BufferedImage getFlipped(int i, boolean xFlip, boolean yFlip) {
		BufferedImage orig = get(i);
		int w = orig.getWidth();
		int h = orig.getHeight();
		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		if (xFlip && !yFlip) {
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					result.setRGB(x, y, orig.getRGB(w - x - 1, y));
				}
			}
		} else if (yFlip && !xFlip) {
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					result.setRGB(x, y, orig.getRGB(x, h - y - 1));
				}
			}
		} else if (xFlip && yFlip) {
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					result.setRGB(x, y, orig.getRGB(w - x - 1, h - y - 1));
				}
			}
		} else {
			return orig;
		}
		return result;
	}
}
