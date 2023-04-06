package enemies.volatilevolcano.flamespecter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;

public class FlameSpecterOrange extends Projectile{
final int SIZE = 28;
	
	int size, dmg;
	BufferedImage img;

	public FlameSpecterOrange(int x, int y, int ori) {
		super(x, y, (double)ori, FlameSpecter.ORANGE_RANGE, FlameSpecter.ORANGE_SPEED);
		this.dmg = FlameSpecter.ORANGE_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FLAMESPECTER_SHOTS.get(0), rotation+45);
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
			Controller.chr.damage("Flaming Specter", this.dmg, true);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < FlameSpecter.ORANGE_RANGE) {
			multiplier = -FlameSpecter.ORANGE_ACCEL * (FlameSpecter.ORANGE_RANGE - range);
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
