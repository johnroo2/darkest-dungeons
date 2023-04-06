package enemies.wildwetlands.forbiddenfountain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.AoE;
import project.Projectile;
import project.Rotate;

public class FFBubbleSpawn extends Projectile{
final int SIZE = 28;
	
	int size, ori;

	public FFBubbleSpawn(int x, int y, int ori) {
		//96 = 8 * 12
		super(x, y, (double)ori, 96, 0);
		this.ori = ori;
		this.size = SIZE;

		Controller.enemyShots.add(this);
	}
	
	@Override
	public void destroy() {
		new FFBubble((int)this.x, (int)this.y, ori + 180); 
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(inWall()) {
			return true;
		}
		return false;
	}

	public BufferedImage getImage() {
		for(int i = 0; i < 12; i ++) {
			if(this.range >= 88 - 8 * i) {
				return Projectile.IMGS_FORBIDDENFOUNTAIN_BUBBLE.get(i);
			}
		}
		return Projectile.IMGS_FORBIDDENFOUNTAIN_BUBBLE.get(0);
	}

	@Override
	public void move() {
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}

class FFBubble extends Projectile{
final int SIZE = 28;
	
	int size, dmg;
	double initXVel;
	double initYVel;
	BufferedImage img;

	public FFBubble(int x, int y, int ori) {
		super(x, y, (double)ori, ForbiddenFountain.BUBBLE_RANGE, ForbiddenFountain.BUBBLE_SPEED);
		this.dmg = ForbiddenFountain.BUBBLE_DMG;
		double rotation = ori;
		this.initXVel = xVel;
		this.initYVel = yVel;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));	
		this.img = Rotate.rotateImage(Projectile.IMGS_FORBIDDENFOUNTAIN_BUBBLE.get(12), rotation);
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
			Controller.chr.damage("Forbidden Fountain", this.dmg, true);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < ForbiddenFountain.BUBBLE_RANGE) {
			multiplier = ForbiddenFountain.BUBBLE_ACCEL * (ForbiddenFountain.BUBBLE_RANGE - range);
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
