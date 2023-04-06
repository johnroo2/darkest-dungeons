package equipment.ability;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

import control.Controller;
import project.Area;
import project.Enemy;
import project.Projectile;
import project.Rotate;
import statuseffects.*;

public class TieredBladesShot extends Projectile{
	final int SIZE = 24;
	
	int type, minDmg, maxDmg, size, bounces;
	double angle;
	Enemy lastHit = null;
	BufferedImage img;

	public TieredBladesShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int type, int minDmg, int maxDmg, int bounces) {
		super(x, y, targetX, targetY, range, speed);
		this.type = type;
		this.size = SIZE;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.bounces = bounces;
		
		angle = getAngle(x, y, targetX, targetY);
		double rotation = angle + 135;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.BULLETS_BLADES.get(type), rotation);
		Controller.allyShots.add(this);
	}
	
	public boolean contact() {
		if(inWall()) {
			return true;
		}
		for(Area a: Area.list) {
			Iterator<Enemy> enemyLi = a.getEnemies().iterator();
			while(enemyLi.hasNext()) {
				Enemy e = enemyLi.next();
				if(e.collision(this) && this.lastHit != e) {
					this.lastHit = e;
					if(e.damage(Controller.random.nextInt(this.minDmg, this.maxDmg+1), false)) {
						enemyLi.remove();
						Controller.removedEnemies.add(e);
						Controller.chr.kill(e.getName());
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(this.bounces < 0) {
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		boolean xBounce = false;
		boolean yBounce = false;
		this.x += this.xVel;
		if(this.contact()) {
			xBounce = true;
			this.x -= this.xVel;
		}
		this.y += this.yVel;
		if(this.contact()) {
			yBounce = true;
			this.y -= this.yVel;
		}
		if(xBounce || yBounce) {
			this.bounces -= 1;
			this.range = 70;
		}
		if(xBounce) {
			this.xVel *= -1;
			this.angle = 180 - angle;
			this.img = Rotate.rotateImage(Projectile.BULLETS_BLADES.get(type), angle + 135);
		}
		else if(yBounce) {
			this.yVel *= -1;
			this.angle = 360 - angle;
			this.img = Rotate.rotateImage(Projectile.BULLETS_BLADES.get(type), angle + 135);
		}
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
