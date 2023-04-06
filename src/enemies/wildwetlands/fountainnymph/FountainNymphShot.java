package enemies.wildwetlands.fountainnymph;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyDisarmed;


public class FountainNymphShot extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	BufferedImage img;

	public FountainNymphShot(int x, int y, int ori) {
		super(x, y, (double)ori, FountainNymph.SHOT_RANGE, FountainNymph.SHOT_SPEED);
		this.dmg = FountainNymph.DMG;
		double rotation = ori;
		this.range += FountainNymph.SHOT_STALL;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FOUNTAINNYMPH_SHOTS, rotation+45);
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
			Controller.chr.damage("Fountain Nymph", this.dmg);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		if(this.range == FountainNymph.SHOT_STALL) {
			this.xVel = 0;
			this.yVel = 0;
		}
		this.x += this.xVel;
		this.y += this.yVel;
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
