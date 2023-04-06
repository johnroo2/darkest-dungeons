package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import control.Driver;

public class ClassSelectBar extends Button{
	private final static int WIDTH = 700;
	private final static int HEIGHT = 100;
	private final static Font BAR_FONT = new Font("Courier New", Font.BOLD, 16);
	private String classType;
	private String desc;
	private BufferedImage img;
	
	public ClassSelectBar(int x, int y, String classType, String desc, BufferedImage img) {
		super(WIDTH, HEIGHT, x, y);
		this.img = img;
		this.desc = desc;
		this.classType = classType;
	}
	
	public void drawString(Graphics g, String s, int x, int y) {
		for(String line: s.split("\n")) {
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}
	
	@Override
	public void performAction() {
		if(Driver.gameState.equals("select")) {
			try {
				Driver.startGame(classType);
			} catch (IOException e) {}
		}
	}
	
	@Override
	public void draw(Graphics g, ImageObserver i) {
		g.setFont(BAR_FONT);
		g.setColor(new Color(20, 20, 20));
		g.fillRect(this.x, this.y, WIDTH, HEIGHT);
		g.drawImage(this.img, this.x + 20, this.y + 22, 56, 56, i);
		if(this.isMouseHover()) {
			g.setColor(new Color(255, 255, 255));
		}
		else {
			g.setColor(new Color(180, 180, 180));
		}
		drawString(g, this.desc, this.x + 100, this.y + 20);
	}
}
