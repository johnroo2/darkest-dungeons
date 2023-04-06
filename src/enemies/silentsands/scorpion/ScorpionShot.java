package enemies.silentsands.scorpion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import control.Controller;
import project.Area;
import project.Projectile;
import project.Rotate;

public class ScorpionShot extends Projectile{
	final int SIZE = 28;
	
	int size, dmg;
	BufferedImage img;

	public ScorpionShot(int x, int y, int targetX, int targetY) {
		super(x, y, targetX, targetY, Scorpion.SHOT_RANGE, Scorpion.SHOT_SPEED);
		this.dmg = Scorpion.DMG;
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));
		this.img = Rotate.rotateImage(IMGS_SCORPION_SHOTS, rotation+45);
		Controller.enemyShots.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(inWall()) {
			return true;
		}
		if(Controller.chr.collision(this)) {
			Controller.chr.damage("Scorpion", this.dmg);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		this.x += this.xVel;
		this.y += this.yVel;
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
