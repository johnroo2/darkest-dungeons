package enemies.wildwetlands.giantcrab;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import control.Controller;
import project.Area;
import project.Projectile;
import project.Rotate;

public class GiantCrabShot extends Projectile{
	
	int size, dmg;
	BufferedImage img;

	public GiantCrabShot(int x, int y, int targetX, int targetY, int size, double speed, int damage) {
		super(x, y, targetX, targetY, GiantCrab.SHOT_RANGE, speed);
		this.dmg = damage;
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle;
		
		this.size = (int) (size * Rotate.getSizeConstant(rotation+45));
		this.img = Rotate.rotateImage(Projectile.IMGS_GIANTCRAB_SHOTS, rotation+45);
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
			Controller.chr.damage("Giant Crab", this.dmg);
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
