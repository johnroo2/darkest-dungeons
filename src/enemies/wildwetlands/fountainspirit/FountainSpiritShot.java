package enemies.wildwetlands.fountainspirit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyDisarmed;

public class FountainSpiritShot extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	boolean bounce = false;
	BufferedImage img;

	public FountainSpiritShot(int x, int y, int ori) {
		super(x, y, (double)ori, FountainSpirit.SHOT_RANGE, FountainSpirit.SHOT_SPEED);
		this.dmg = FountainSpirit.DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FOUNTAINSPIRIT_SHOTS, rotation+45);
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
				this.range = FountainSpirit.SHOT_RANGE;
			}
		}
		if(inWall()) {
			return true;
		}
		if(Controller.chr.collision(this)) {
			Controller.chr.damage("Fountain Spirit", this.dmg);
			new AllyDisarmed(167);
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
