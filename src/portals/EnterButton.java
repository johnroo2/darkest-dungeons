package portals;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Button;
import project.Portal;

public class EnterButton extends Button{
	Portal p;
	
	public EnterButton(Portal portal) {
		super(140, 40, 880, 710);
		this.p = portal;
		
	}

	@Override
	public void performAction() {
		Controller.chr.setDungeon(this.p.getDestination());
		Controller.chr.getDungeon().init();
		if(Controller.chr.levelReached < this.p.getDestination().getLevel()) {
			Controller.chr.levelReached = this.p.getDestination().getLevel();
		}
	}

	@Override
	public void draw(Graphics g, ImageObserver i) {
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.setColor(new Color(50, 50, 50));
		g.fillRect(this.x, this.y, width, height);
		if(this.isMouseHover()) {
			g.setColor(new Color(255, 255, 255));
		}
		else {
			g.setColor(new Color(180, 180, 180));
		}
		FontMetrics fm = g.getFontMetrics();
		g.drawString("Enter", 950 - fm.stringWidth("Enter")/2, this.y + this.height/2 + 5);
	}
}
