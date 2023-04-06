package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;

public class PopText {
	String text;
	double x, y;
	double xVel, yVel;
	int mode;
	double accel;
	Color c;
	
	public static ArrayList<PopText> list = new ArrayList<>();
	
	final static Font POP_FONT = new Font("Courier New", Font.BOLD, 21);
	final static Font POP_FONT_CRIT = new Font("Courier New", Font.BOLD, 27);
	final static Color POP_RED = new Color(240, 60, 60);
	final static Color POP_PURPLE = new Color(150, 60, 240);
	final static Color POP_MAROON = new Color(220, 60, 60);
	public final static Color POP_GREEN = new Color(60, 240, 60);
	public final static Color POP_BLUE = new Color(120, 120, 220);
	
	final int XMIN = -5;
	final int XMAX = 5;
	final int YMIN = -2;
	final int YMAX = 8;
	final double INIT_VELOCITY = 3;
	final double ACCELERATION = 0.2;
	
	//char
	public PopText(int damage) {
		this.x = Controller.chr.getX() + Controller.random.nextInt(XMIN, XMAX+1);
		this.y = Controller.chr.getY() + Controller.random.nextInt(YMIN, YMAX+1);
		this.xVel = -0.5 + Controller.random.nextDouble();
		this.yVel = INIT_VELOCITY;
		this.text = "-" + damage;
		this.mode = 0;
		this.accel = ACCELERATION;
		PopText.list.add(this);
	}
	
	//char, healing/manahealing
	public PopText(Color c, int damage) {
		this.x = Controller.chr.getX() + Controller.random.nextInt(XMIN, XMAX+1);
		this.y = Controller.chr.getY() + Controller.random.nextInt(YMIN, YMAX+1);
		this.xVel = -0.5 + Controller.random.nextDouble();
		this.yVel = INIT_VELOCITY * 1.5;
		this.text = "+" + damage;
		this.mode = 3;
		this.accel = ACCELERATION * 1.5;
		this.c = c;
		PopText.list.add(this);
	}
	
	//char, pierce
	public PopText(int damage, boolean pierce) {
		this.x = Controller.chr.getX() + Controller.random.nextInt(XMIN, XMAX+1);
		this.y = Controller.chr.getY() + Controller.random.nextInt(YMIN, YMAX+1);
		this.xVel = -0.5 + Controller.random.nextDouble();
		this.yVel = INIT_VELOCITY;
		this.text = "-" + damage;
		if(pierce) {
			this.mode = 1;
		}
		else {
			this.mode = 0;
		}
		this.accel = ACCELERATION;
		PopText.list.add(this);
	}
	
	//enemy, crit
		public PopText(boolean crit, int x, int y, int damage) {
			this.x = x + Controller.random.nextInt(XMIN, XMAX+1);
			this.y = y + Controller.random.nextInt(YMIN, YMAX+1);
			this.xVel = -2 + 4*Controller.random.nextDouble();
			this.yVel = INIT_VELOCITY * 1.25;
			this.text = "-" + damage + "!";
			this.mode = 4;
			this.accel = ACCELERATION * 1.25;
			PopText.list.add(this);
		}
	
	//enemy, crit, pierce
	public PopText(boolean crit, int x, int y, int damage, boolean pierce) {
		this.x = x + Controller.random.nextInt(XMIN, XMAX+1);
		this.y = y + Controller.random.nextInt(YMIN, YMAX+1);
		this.xVel = -2 + 4*Controller.random.nextDouble();
		this.yVel = INIT_VELOCITY * 1.25;
		this.text = "-" + damage + "!";
		if(pierce) {
			this.mode = 5;
		}
		else {
			this.mode = 4;
		}
		this.accel = ACCELERATION * 1.25;
		PopText.list.add(this);
	}
	
	//enemy
	public PopText(int x, int y, int damage) {
		this.x = x + Controller.random.nextInt(XMIN, XMAX+1);
		this.y = y + Controller.random.nextInt(YMIN, YMAX+1);
		this.xVel = -1.5 + 3*Controller.random.nextDouble();
		this.yVel = INIT_VELOCITY * 1.5;
		this.text = "-" + damage;
		this.mode = 0;
		this.accel = ACCELERATION * 1.5;
		PopText.list.add(this);
	}
	
	//enemy, pierce
	public PopText(int x, int y, int damage, boolean pierce) {
		this.x = x + Controller.random.nextInt(XMIN, XMAX+1);
		this.y = y + Controller.random.nextInt(YMIN, YMAX+1);
		this.xVel = -1.5 + 3*Controller.random.nextDouble();
		this.yVel = INIT_VELOCITY * 1.5;
		this.text = "-" + damage;
		if(pierce) {
			this.mode = 1;
		}
		else {
			this.mode = 0;
		}
		this.accel = ACCELERATION * 1.5;
		PopText.list.add(this);
	}
	
	//status
	public PopText(String text) {
		this.text = text;
		this.x = Controller.chr.getX() + Controller.random.nextInt(XMIN, XMAX+1);
		this.y = Controller.chr.getY() + 2 * Controller.random.nextInt(YMIN+4, YMAX+1);
		this.xVel = -1 + 2*Controller.random.nextDouble();
		this.yVel = INIT_VELOCITY*1.25;
		this.mode = 2;
		this.accel = ACCELERATION*1.25;
		PopText.list.add(this);
	}
	
	//status, enemy
	public PopText(int x, int y, String text) {
		this.text = text;
		this.x = x + Controller.random.nextInt(XMIN, XMAX+1);
		this.y = y + Controller.random.nextInt(YMIN, YMAX+1);
		this.xVel = -1 + 2*Controller.random.nextDouble();
		this.yVel = INIT_VELOCITY*1.25;
		if(this.text.equals("Immune")) {
			this.mode = 0;
		}
		else {
			this.mode = 2;
		}
		this.accel = ACCELERATION*1.25;
		PopText.list.add(this);
	}
	
	public boolean move() {
		this.x += xVel;
		this.y -= yVel;
		this.yVel -= ACCELERATION;
		if(-this.yVel > this.INIT_VELOCITY) {
			return true;
		}
		return false;
	}

	public void draw(Graphics g, int shiftX, int shiftY) {
		g.setFont(POP_FONT);
		if(mode == 0) {
			g.setColor(POP_RED);
		}
		else if(mode == 1) {
			g.setColor(POP_PURPLE);
		}
		else if(mode == 2){
			g.setColor(POP_MAROON);
		}
		else if(mode == 3) {
			g.setColor(c);
		}
		else if(mode == 4) {
			g.setFont(POP_FONT_CRIT);
			g.setColor(POP_RED);
		}
		else if(mode == 5) {
			g.setFont(POP_FONT_CRIT);
			g.setColor(POP_PURPLE);
		}
		if(this.mode < 4) {
			g.drawString(this.text, (int)this.x - 7 * this.text.length() - shiftX + 400, (int)this.y - 10 - shiftY + 400);
		}
		else {
			g.drawString(this.text, (int)this.x - 9 * this.text.length() - shiftX + 400, (int)this.y - 10 - shiftY + 400);
		}
	}
}
