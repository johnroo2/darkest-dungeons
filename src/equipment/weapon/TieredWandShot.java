package equipment.weapon;

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

public class TieredWandShot extends Projectile{
	final int SIZE = 32;
	
	int type, size, minDmg, maxDmg;
	BufferedImage img;
	
	public TieredWandShot(int x, int y, int targetX, int targetY, double speed, int range, int type, int minDmg, int maxDmg) {
		super(x, y, targetX, targetY, range, speed);
		this.type = type;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle + 45;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.BULLETS_STAFF.get(type), rotation);
		Controller.allyShots.add(this);
	}
	
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(inWall()) {
			return true;
		}
		for(Area a: Area.list) {
			Iterator<Enemy> enemyLi = a.getEnemies().iterator();
			while(enemyLi.hasNext()) {
				Enemy e = enemyLi.next();
				if(e.collision(this)) {
					if(Controller.random.nextInt(0, 100) < Controller.chr.getCrit()) {
						if(e.damage(Controller.random.nextInt((int)(this.minDmg * critDamage), (int)(this.maxDmg * critDamage) + 1), true)) {			
							enemyLi.remove();
							Controller.removedEnemies.add(e);
							Controller.chr.kill(e.getName());
						}
					}
					else {
						if(e.damage(Controller.random.nextInt(this.minDmg, this.maxDmg+1), false)) {
							enemyLi.remove();
							Controller.removedEnemies.add(e);
							Controller.chr.kill(e.getName());
						}
					}
					return true;
				}
			}
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
