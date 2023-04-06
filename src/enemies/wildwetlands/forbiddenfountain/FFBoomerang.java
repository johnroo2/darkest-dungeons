package enemies.wildwetlands.forbiddenfountain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.wildwetlands.fountainspirit.FountainSpirit;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyDisarmed;

public class FFBoomerang extends Projectile{
	final int SIZE = 32;
	
	int size, dmg;
	boolean bounce = false;
	BufferedImage img;

	public FFBoomerang(int x, int y, int ori) {
		super(x, y, (double)ori, ForbiddenFountain.BOOMERANG_RANGE, ForbiddenFountain.BOOMERANG_SPEED);
		this.dmg = ForbiddenFountain.BOOMERANG_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FORBIDDENFOUNTAIN_SHOTS.get(0), rotation+45);
		Controller.enemyShots.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			if(bounce) {
				return true;
			}
			else {
				bounce = true;
				this.xVel *= -1;
				this.yVel *= -1;
				this.img = Rotate.rotateImage(this.img, 180);
				this.range = ForbiddenFountain.BOOMERANG_RANGE;
			}
		}
		if(Controller.chr.collision(this)) {
			Controller.chr.damage("Forbidden Fountain", this.dmg);
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
