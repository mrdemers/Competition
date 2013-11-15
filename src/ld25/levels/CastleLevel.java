package ld25.levels;

import java.awt.Font;
import java.awt.Graphics2D;

import ld25.Art;
import ld25.Component;
import ld25.entities.Hero;
import ld25.menu.YouWonMenu;

public class CastleLevel extends Level {
	String[] dialogue = {"Give me back my princess!", "Be gone you evil fiend!", "I have saved this princess", "Actually I wanted to be with him", "Well I am still going to kill him"};
	String currText = "";
	public CastleLevel() {
		super(360, 240);
		numKilled = 0;
		startX = 0;
		startY = 175;
		name = "castle";
		Level.addEntity(new Hero(200, 175, 0));
	}

	int talkTime = 0;
	int currChar = 0;
	int textPosX = 0;
	int textPosY = 20;
	public void update() {
		super.update();
		if (xScroll > 0) xScroll = 0;
		if (p.x < 50 && talkTime == 0) p.x++;
		else if (talkTime++ < 450) {
			if (p.x != 50) p.x = 50;
			if (p.z != 0) p.z = 0;
			int l1 = dialogue[0].length();
			int l2 = dialogue[1].length();
			int l3 = dialogue[2].length();
			int l4 = dialogue[3].length();
			int l5 = dialogue[4].length();
			if (talkTime <= l1) {
				currText += dialogue[0].charAt(currChar++);
				textPosX = 100 - (currText.length() * 7)/2;
				textPosY = 140;
			} else if (talkTime < l1+60) {
				//Do nothing
			} else if (talkTime == l1 + 60) {
				currText = "";
				currChar = 0;
			} else if (talkTime <= l1 + l2 + 60) {
				currText += dialogue[1].charAt(currChar++);
				textPosX = 250 - (currText.length() * 7) /2;
				textPosY = 140;
			} else if (talkTime < l1 + l2 + 120) {
				//Do nothing
			} else if (talkTime == l1 + l2 + 120) {
				currText = "";
				currChar = 0;
			} else if (talkTime <= l1 + l2 + l3 + 120) {
				currText += dialogue[2].charAt(currChar++);
				textPosX = 250 - (currText.length() * 7) /2;
				textPosY = 140;
			} else if (talkTime < l1 + l2 + l3 + 180) {
				//Do nothing
			} else if (talkTime == l1 + l2 + l3 + 180) {
				currText = "";
				currChar = 0;
			} else if (talkTime <= l1 + l2 + l3 + l4 + 180) {
				currText += dialogue[3].charAt(currChar++);
				textPosX = 280 - (currText.length() * 7) /2;
				textPosY = 155;
			} else if (talkTime < l1 + l2 + l3 + l4 + 240) {
				//Do nothing
			} else if (talkTime == l1 + l2 + l3 + l4 + 240) {
				currText ="";
				currChar = 0;
			} else if (talkTime <= l1 + l2 + l3 + l4 + l5 + 240) {
				currText += dialogue[4].charAt(currChar++);
				textPosX = 250 - (currText.length() * 7) /2;
				textPosY = 140;
			}
		} 
		
		if (numKilled > 0) {
			if (deadTime++ > 120)
				Component.switchMenu(new YouWonMenu());
		}
	}
	int deadTime = 0;
	public void render(Graphics2D g) {
		g.drawImage(Art.castleRoom, 0, 0, null);
		super.render(g);
		g.drawImage(Art.get(13), 280, 175, null);
		if (talkTime < 450) {
			g.setFont(new Font("Arial", 0, 9));
			g.drawString(currText, textPosX, textPosY);
		}
	}
}
