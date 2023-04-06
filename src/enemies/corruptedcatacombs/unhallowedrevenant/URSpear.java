package enemies.corruptedcatacombs.unhallowedrevenant;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;

public class URSpear extends Projectile{
final int SIZE = 40;
	
	int size, dmg, initX, initY;
	BufferedImage img;

	public URSpear(int x, int y, int targetX, int targetY, int alt, int offset) {
		super(x, y, targetX, targetY, UnhallowedRevenant.SPEAR_RANGE, UnhallowedRevenant.SPEAR_SPEED);
		this.dmg = UnhallowedRevenant.SPEAR_DMG;
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle + alt;
		
		//double opposite = speed * range * Math.tan(Math.toRadians(Math.abs(alt)));
		double oppositeX = offset * Math.cos(Math.toRadians(angle+90));
		double oppositeY = offset * Math.sin(Math.toRadians(angle+90));
		this.initX += oppositeX;
		this.initY += oppositeY;
		this.x += oppositeX;
		this.y += oppositeY;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));
		this.img = Rotate.rotateImage(Projectile.IMGS_UNHALLOWEDREVENANT_SPEAR, rotation+45);
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
			Controller.chr.damage("Unhallowed Revenant", this.dmg);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < UnhallowedRevenant.SPEAR_RANGE) {
			multiplier = UnhallowedRevenant.SPEAR_ACCEL * (UnhallowedRevenant.SPEAR_RANGE - range);
		}
		this.x += this.xVel * multiplier;
		this.y += this.yVel * multiplier;
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
