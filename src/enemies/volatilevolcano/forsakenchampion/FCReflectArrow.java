package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ListIterator;

import control.Controller;
import project.Area;
import project.Enemy;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySilenced;

public class FCReflectArrow extends Projectile{
	final int SIZE = 35;
	
	int size, dmg;
	int bounces = 3;
	double angle;
	BufferedImage img;

	public FCReflectArrow(int x, int y, double ori) {
		super(x, y, (double)ori, ForsakenChampion.REFLECTARROW_RANGE, ForsakenChampion.REFLECTARROW_SPEED);
		this.dmg = ForsakenChampion.REFLECTARROW_DMG;
		double rotation = ori;
		angle = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_ARROW.get(2), rotation+45);
		Controller.enemyShots.add(this);
	}
	
	public boolean contact() {
		if(inWall()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(this.bounces < 0) {
			return true;
		}
		if(Controller.chr.collision(this)) {
			Controller.chr.damage("Forsaken Champion", this.dmg);
			return true;
		}
		return false;
	}
	
	@Override
	public void move() {
		boolean xBounce = false;
		boolean yBounce = false;
		this.x += this.xVel;
		if(this.contact()) {
			xBounce = true;
			this.x -= this.xVel;
		}
		this.y += this.yVel;
		if(this.contact()) {
			yBounce = true;
			this.y -= this.yVel;
		}
		if(xBounce || yBounce) {
			this.bounces -= 1;
			this.range = 1000;
		}
		if(xBounce) {
			this.xVel *= -1;
			this.angle = 180 - angle;
			this.img = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_ARROW.get(2), angle + 45);
		}
		else if(yBounce) {
			this.yVel *= -1;
			this.angle = 360 - angle;
			this.img = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_ARROW.get(2), angle + 45);
		}
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
