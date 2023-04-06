package enemies.corruptedcatacombs.unhallowedrevenantshade;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import project.Target;

public class UnhallowedRevenantShade extends Target{
	final static int SIZE = 42;
	final static int OFFSET = 410;
	final static int ANIMATE_FRAMES = 10;
	final static double ROTATE_SPEED = 0.2;
	
	final static int DMG = 125;
	final static double SHOT_SPEED = 0.85;
	final static int SHOT_RANGE = 1500;
	
	public static int FRAMES = 1;
	
	int animate = ANIMATE_FRAMES * 5;
	double rotation;
	int centerX, centerY;
	public UnhallowedRevenantShade(int centerX, int centerY, double rotation) {
		super((int)(centerX + 400 * Math.cos(Math.toRadians(rotation))), (int)(centerY + 400 * Math.sin(Math.toRadians(rotation))), 1, 0);
		this.rotation = rotation;
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	public void shootSpinner(int x, int y) {
		new UnhallowedRevenantShadeShot((int)this.x, (int)this.y, x, y);
	}
	
	public BufferedImage getImage() {
		if(this.animate > 0) {
			animate -= 1;
		}
		else {
			animate = ANIMATE_FRAMES * 5;
		}
		for(int i = 4; i >= 0; i--) {
			if(animate > ANIMATE_FRAMES * i) {
				return Target.IMGS_UNHALLOWEDREVENANTSHADE.get(i);
			}
		}
		return Target.IMGS_UNHALLOWEDREVENANTSHADE.get(0);
	}
	
	public void move(int direction) {
		rotation += direction * ROTATE_SPEED;
		this.x = (int)(centerX + 400 * Math.cos(Math.toRadians(rotation)));
		this.y = (int)(centerY + 400 * Math.sin(Math.toRadians(rotation)));
	}
	
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(getImage(), (int)this.x-SIZE/2 - shiftX + 400, (int)this.y-SIZE/2 - shiftY + 400, SIZE, SIZE, i);
	}
}
