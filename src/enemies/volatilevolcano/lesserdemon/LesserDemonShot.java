package enemies.volatilevolcano.lesserdemon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;

public class LesserDemonShot extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	double initXVel;
	double initYVel;
	BufferedImage img;

	public LesserDemonShot(int x, int y, int ori) {
		super(x, y, (double)ori, LesserDemon.SHOT_RANGE, LesserDemon.SHOT_SPEED);
		this.dmg = LesserDemon.DMG;
		double rotation = ori;
		this.initXVel = xVel;
		this.initYVel = yVel;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));	
		this.img = Rotate.rotateImage(Projectile.IMGS_LESSERDEMON_SHOTS, rotation);
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
			Controller.chr.damage("Lesser Demon", this.dmg, true);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < LesserDemon.SHOT_RANGE - LesserDemon.SHOT_DELAY) {
			multiplier = LesserDemon.SHOT_ACCEL * (LesserDemon.SHOT_RANGE - LesserDemon.SHOT_DELAY-range);
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
