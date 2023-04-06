package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import project.Projectile;
import project.Rotate;

public class FCBounce extends Projectile{
final int SIZE = 36;
	
	int size, dmg;
	double initXVel;
	double initYVel;
	ArrayList<BufferedImage> img = new ArrayList<BufferedImage>();

	public FCBounce(int x, int y, int ori) {
		super(x, y, (double)ori, ForsakenChampion.BOUNCE_RANGE, ForsakenChampion.BOUNCE_SPEED);
		this.dmg = ForsakenChampion.BOUNCE_DMG;
		double rotation = ori;
		this.initXVel = xVel;
		this.initYVel = yVel;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation + 45));	
		this.img.add(Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_BOUNCE.get(0), rotation + 45));
		this.img.add(Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_BOUNCE.get(1), rotation + 45));
		this.img.add(Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_BOUNCE.get(2), rotation + 45));
		this.img.add(Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_BOUNCE.get(3), rotation + 45));
		this.img.add(Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_BOUNCE.get(4), rotation + 45));
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
			Controller.chr.damage("Forsaken Champion", this.dmg);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < ForsakenChampion.BOUNCE_RANGE - ForsakenChampion.BOUNCE_DELAY) {
			multiplier = ForsakenChampion.BOUNCE_ACCEL * (ForsakenChampion.BOUNCE_RANGE - ForsakenChampion.BOUNCE_DELAY-range);
		}
		this.x += this.xVel * multiplier;
		this.y += this.yVel * multiplier;
		this.range--;	
	}
	
	public BufferedImage getImage() {
		if(range > (int)((ForsakenChampion.BOUNCE_RANGE - ForsakenChampion.BOUNCE_DELAY) * 1.0)) {
			return this.img.get(0);
		}
		else if(range > (int)((ForsakenChampion.BOUNCE_RANGE - ForsakenChampion.BOUNCE_DELAY) * 0.8)) {
			return this.img.get(1);
		}
		else if(range > (int)((ForsakenChampion.BOUNCE_RANGE - ForsakenChampion.BOUNCE_DELAY) * 0.6)) {
			return this.img.get(2);
		}
		else if(range > (int)((ForsakenChampion.BOUNCE_RANGE - ForsakenChampion.BOUNCE_DELAY) * 0.4)) {
			return this.img.get(3);
		}
		else {
			return this.img.get(4);
		}
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
