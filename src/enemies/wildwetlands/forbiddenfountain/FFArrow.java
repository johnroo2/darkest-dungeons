package enemies.wildwetlands.forbiddenfountain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;

public class FFArrow extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	double initXVel;
	double initYVel;
	boolean pierce;
	BufferedImage img;

	public FFArrow(int x, int y, int ori, boolean pierce) {
		super(x, y, (double)ori, ForbiddenFountain.ARROW_RANGE, ForbiddenFountain.ARROW_SPEED);
		this.pierce = pierce;
		this.dmg = ForbiddenFountain.ARROW_DMG;
		double rotation = ori;
		this.initXVel = xVel;
		this.initYVel = yVel;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));	
		if(!pierce) {
			this.img = Rotate.rotateImage(Projectile.IMGS_FORBIDDENFOUNTAIN_SHOTS.get(1), rotation+45);
		}
		else {
			this.img = Rotate.rotateImage(Projectile.IMGS_FORBIDDENFOUNTAIN_SHOTS.get(2), rotation+45);
		}
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
			Controller.chr.damage("Forbidden Fountain", this.dmg, pierce);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < ForbiddenFountain.ARROW_RANGE) {
			multiplier = ForbiddenFountain.ARROW_ACCEL * (ForbiddenFountain.ARROW_RANGE - range);
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
