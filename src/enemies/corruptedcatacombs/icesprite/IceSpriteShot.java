package enemies.corruptedcatacombs.icesprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySlow;

public class IceSpriteShot extends Projectile{
	final int SIZE = 20;
	
	int size, dmg, initX, initY;
	BufferedImage img;

	public IceSpriteShot(int x, int y, double ori) {
		super(x, y, ori, IceSprite.SHOT_RANGE, IceSprite.SHOT_SPEED);
		this.dmg = IceSprite.DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));
		this.img = Rotate.rotateImage(Projectile.IMGS_ICESPRITE_SHOTS.get(Controller.random.nextInt(0, 3)), rotation+45);
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
			Controller.chr.damage("Ice Sprite", this.dmg);
			new AllySlow(167);
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
