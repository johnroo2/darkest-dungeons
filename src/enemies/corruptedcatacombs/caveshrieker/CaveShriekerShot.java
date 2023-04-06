package enemies.corruptedcatacombs.caveshrieker;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;

public class CaveShriekerShot extends Projectile{
	final int SIZE = 28;
	
	int size, dmg;
	BufferedImage img;

	public CaveShriekerShot(int x, int y, double ori, int damage) {
		super(x, y, (double)ori, CaveShrieker.SHRIEK_RANGE, CaveShrieker.SHRIEK_SPEED);
		this.dmg = damage;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_CAVESHRIEKER_SHOTS.get(0), rotation+45);
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
			Controller.chr.damage("Cave Shrieker", this.dmg);
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
