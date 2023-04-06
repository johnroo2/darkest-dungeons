package enemies.wildwetlands.aquamage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySlow;

public class AquaMagePulse extends Projectile{
	final int SIZE = 28;
	
	int size, dmg;
	BufferedImage img;

	public AquaMagePulse(int x, int y, int ori) {
		super(x, y, (double)ori, AquaMage.SHOT_RANGE_PULSE, AquaMage.SHOT_SPEED_PULSE);
		this.dmg = AquaMage.DMG_PULSE;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));		
		this.img = Rotate.rotateImage(Projectile.IMGS_AQUAMAGE_SHOTS, rotation);
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
			Controller.chr.damage("Aqua Mage", this.dmg);
			new AllySlow(167); //1 seconds
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
