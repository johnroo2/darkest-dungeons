package enemies.wildwetlands.fountainfairy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.wildwetlands.fountainspirit.FountainSpirit;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyDisarmed;
import statuseffects.AllySlow;

public class FountainFairyShot extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	double initXVel;
	double initYVel;
	BufferedImage img;

	public FountainFairyShot(int x, int y, int ori) {
		super(x, y, (double)ori, FountainFairy.SHOT_RANGE, FountainFairy.SHOT_SPEED);
		this.dmg = FountainFairy.DMG;
		double rotation = ori;
		this.initXVel = xVel;
		this.initYVel = yVel;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FOUNTAINFAIRY_SHOTS, rotation+45);
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
			Controller.chr.damage("Fountain Fairy", this.dmg, true);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < FountainFairy.SHOT_RANGE - FountainFairy.ACCEL_DELAY) {
			multiplier = FountainFairy.ACCEL * (FountainFairy.SHOT_RANGE - FountainFairy.ACCEL_DELAY - range);
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
