package enemies.wildwetlands.forbiddenfountain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.wildwetlands.fountainfairy.FountainFairy;
import project.Area;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyQuiet;

public class FFSpear extends Projectile{
	final int SIZE = 30;
	
	int size, dmg;
	BufferedImage img;

	public FFSpear(int x, int y, int ori) {
		super(x, y, (double)ori, ForbiddenFountain.SPEAR_RANGE, ForbiddenFountain.SPEAR_SPEED);
		this.dmg = ForbiddenFountain.SPEAR_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FORBIDDENFOUNTAIN_SHOTS.get(8), rotation+45);
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
		for(Area a: Area.list) {
			if(Controller.chr.collision(this)) {
				Controller.chr.damage("Forbidden Fountain", this.dmg);
				new AllyQuiet(167);
				return true;
			}
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < ForbiddenFountain.SPEAR_RANGE - ForbiddenFountain.SPEAR_DELAY) {
			multiplier = -ForbiddenFountain.SPEAR_ACCEL * (ForbiddenFountain.SPEAR_RANGE - ForbiddenFountain.SPEAR_DELAY - range);
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
