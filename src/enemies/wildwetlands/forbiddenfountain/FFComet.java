package enemies.wildwetlands.forbiddenfountain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.AoE;
import project.Projectile;
import project.Rotate;

public class FFComet extends AoE{
	
	public FFComet(int x, int y) {
		super(x, y, 60, AoE.WATERCOMET, 120);
		if(!Controller.enemyAoE.add(this)){
			System.out.println(Controller.enemyAoE.contains(this));
		}
	}
	
	@Override 
	public void destroy() {
		for(int i = 0; i < 6; i++) {
			new FFCometShot((int)this.x, (int)this.y+this.radius/2-20, 60 * i);
		}
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 0; i < 20; i ++) {
			if(this.range >= 114 - 6 * i) {
				return this.colour.get(i);
			}
		}
		return this.colour.get(0);
	}
	
//	public boolean inRadius(int x, int y, int rad) {
//		return (this.x-x)*(this.x-x) + (this.y+this.radius/2-20-y)*(this.y+this.radius/2-20-y) <= rad * rad;
//	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.radius/2 - shiftX + 400, (int)this.y-this.radius-this.radius/2 - shiftY + 400, radius,
				2*radius+(int)(radius * 5.0/37.0), i);
		
	}
}

class FFCometShot extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	BufferedImage img;

	public FFCometShot(int x, int y, int ori) {
		super(x, y, (double)ori, ForbiddenFountain.COMET_RANGE, ForbiddenFountain.COMET_SPEED);
		this.dmg = ForbiddenFountain.COMET_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FORBIDDENFOUNTAIN_SHOTS.get(7), rotation+45);
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
			Controller.chr.damage("Forbidden Fountain Comet", this.dmg);
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
