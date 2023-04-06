package enemies.wildwetlands.greatdragonfly;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;

public class GreatDragonflyShot extends Projectile{
	final int SIZE = 28;
	
	int size, dmg;
	
	int ori;
	BufferedImage img;

	public GreatDragonflyShot(int x, int y, int ori) {
		super(x, y, (double)ori, GreatDragonfly.SHOT_RANGE, GreatDragonfly.SHOT_SPEED);
		this.dmg = GreatDragonfly.DMG;
		double rotation = ori;
		this.ori = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_GREATDRAGONFLY_SHOTS.get(0), rotation+45);
		Controller.enemyShots.add(this);
	}
	
	@Override
	public void destroy() {
		if(this.range <= 0) {
			new GreatDragonflySplitshot((int)this.x, (int)this.y, this.ori + 11);
			new GreatDragonflySplitshot((int)this.x, (int)this.y, this.ori - 11);
		}
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
			Controller.chr.damage("Great Dragonfly", this.dmg, true);
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


class GreatDragonflySplitshot extends Projectile{
	final int SIZE = 28;
	int size, dmg;
	BufferedImage img;

	public GreatDragonflySplitshot(int x, int y, int ori) {
		super(x, y, (double)ori, GreatDragonfly.SHOT_RANGE_SPLIT, GreatDragonfly.SHOT_SPEED_SPLIT);
		this.dmg = GreatDragonfly.DMG_SPLIT;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_GREATDRAGONFLY_SHOTS.get(1), rotation+45);
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
			Controller.chr.damage("Great Dragonfly", this.dmg);
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

