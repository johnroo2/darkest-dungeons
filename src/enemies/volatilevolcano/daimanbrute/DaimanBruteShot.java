package enemies.volatilevolcano.daimanbrute;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;

public class DaimanBruteShot extends Projectile{
	final int SIZE = 24;
	int size, dmg;
	BufferedImage img;

	public DaimanBruteShot(int x, int y, int targetX, int targetY, int alt) {
		super(x, y, targetX, targetY, DaimanBrute.SHOT_RANGE, DaimanBrute.SHOT_SPEED);
		this.dmg = DaimanBrute.DMG;
		double angle = getAngle(x, y, targetX, targetY) + alt;
		double rotation = angle + 45;
		
		double tempX = this.xVel;
		double tempY = this.yVel;
		double sina = Math.sin(Math.toRadians(alt));
		double cosa = Math.cos(Math.toRadians(alt));
		
		this.xVel = tempX * cosa - tempY * sina;
		this.yVel = tempX * sina + tempY * cosa;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		this.img = Rotate.rotateImage(Projectile.IMGS_DAIMANBRUTE_SHOTS, rotation);
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
			Controller.chr.damage("Daiman Brute", this.dmg);
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
