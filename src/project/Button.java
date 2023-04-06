package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;

import control.Controller;
import control.Driver;

public abstract class Button {
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	private boolean mouseHover = false;
	
	public final static Button deathButton = new NextButton();
	public final static Button submitButton = new EnterButton();
	public final static Button lbButton = new LBButton();
	
	public Button(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public void clicked() throws IOException {
		if(this.isMouseHover()) {
			this.performAction();
			this.setMouseHover(false);
		}
	}
	
	public void run() {
		if(Controller.mouseCoords[0] > this.x && Controller.mouseCoords[0] < this.x + this.width &&
				Controller.mouseCoords[1] > this.y && Controller.mouseCoords[1] < this.y + this.height) {
			this.setMouseHover(true);
		}
		else{
			this.setMouseHover(false);
		}
	}
	
	public abstract void performAction() throws IOException;
	public abstract void draw(Graphics g, ImageObserver i);

	public boolean isMouseHover() {
		return mouseHover;
	}

	public void setMouseHover(boolean mouseHover) {
		this.mouseHover = mouseHover;
	}
}

class NextButton extends Button{

	public NextButton() {
		super(200, 50, 450, 670);
	}
	
	@Override
	public void performAction() throws IOException{
		if(Controller.chr.win) {
			Leaderboard.insert(Controller.chr.classType, Controller.name, Controller.getTimerText(), Controller.getTicks());
			Leaderboard.write();
		}
		Controller.deathArgs = null;
		Driver.resetGame();
		
	}
	
	@Override
	public void draw(Graphics g, ImageObserver i) {
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.setColor(new Color(20, 20, 20));
		g.fillRect(this.x, this.y, width, height);
		if(this.isMouseHover()) {
			g.setColor(new Color(255, 255, 255));
		}
		else {
			g.setColor(new Color(180, 180, 180));
		}
		FontMetrics fm = g.getFontMetrics();
		g.drawString("New Character", 550 - fm.stringWidth("New Character")/2, this.y + this.height/2 + 2);
	}
}

class LBButton extends Button{

	public LBButton() {
		super(200, 50, 450, 620);
	}
	
	@Override
	public void performAction() throws IOException{
		Controller.deathArgs = null;
		Driver.resetGame();
		
	}
	
	@Override
	public void draw(Graphics g, ImageObserver i) {
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.setColor(new Color(20, 20, 20));
		g.fillRect(this.x, this.y, width, height);
		if(this.isMouseHover()) {
			g.setColor(new Color(255, 255, 255));
		}
		else {
			g.setColor(new Color(180, 180, 180));
		}
		FontMetrics fm = g.getFontMetrics();
		g.drawString("New Character", 550 - fm.stringWidth("New Character")/2, this.y + this.height/2 + 2);
	}
}

class EnterButton extends Button{

	public EnterButton() {
		super(200, 50, 450, 610);
	}
	
	@Override
	public void performAction() throws IOException{
		if(Controller.chr.win) {
			Leaderboard.insert(Controller.chr.classType, Controller.name, Controller.getTimerText(), Controller.getTicks());
			Leaderboard.write();
		}
		Controller.deathArgs = null;
		Driver.gameState = "leaderboard";	
	}
	
	@Override
	public void draw(Graphics g, ImageObserver i) {
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.setColor(new Color(20, 20, 20));
		g.fillRect(this.x, this.y, width, height);
		if(this.isMouseHover()) {
			g.setColor(new Color(255, 255, 255));
		}
		else {
			g.setColor(new Color(180, 180, 180));
		}
		FontMetrics fm = g.getFontMetrics();
		g.drawString("Leaderboard", 550 - fm.stringWidth("Leaderboard")/2, this.y + this.height/2 + 2);
	}
}