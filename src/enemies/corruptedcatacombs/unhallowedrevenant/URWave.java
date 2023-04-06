package enemies.corruptedcatacombs.unhallowedrevenant;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.silentsands.goblinmage.GoblinMage;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySlow;

public class URWave extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	BufferedImage img;

	public URWave(int x, int y, int ori) {
		super(x, y, (double)ori, UnhallowedRevenant.WAVE_RANGE, UnhallowedRevenant.WAVE_SPEED);
		this.dmg = UnhallowedRevenant.WAVE_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_UNHALLOWEDREVENANT_SHOTS.get(0), rotation+45);
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
			Controller.chr.damage("Unhallowed Revenant", this.dmg);
			new AllySlow(333);
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
