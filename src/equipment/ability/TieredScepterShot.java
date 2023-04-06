package equipment.ability;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

import control.Controller;
import project.Area;
import project.Enemy;
import project.Projectile;
import project.Rotate;

import java.util.ArrayList;

public class TieredScepterShot extends Projectile {
	final int SIZE = 40;
	
	int type, dmg, descent, targets, size;
	BufferedImage img;
	ArrayList<Enemy> enemiesHit = new ArrayList<>();
	ArrayList<Integer> xCoords = new ArrayList<Integer>();
	ArrayList<Integer> yCoords = new ArrayList<Integer>();
	
	public TieredScepterShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int type, int dmg, int descent, int targets) {
		super(x, y, targetX, targetY, range, speed);
		this.type = type;
		this.dmg = dmg;
		this.descent = descent;
		this.targets = targets;
		
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle + 45;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.BULLETS_SCEPTER.get(type), rotation);
		Controller.allyShots.add(this);
	}

	@Override
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
				if(e.collision(this) && !this.enemiesHit.contains(e)) {
					this.enemiesHit.add(e);
					this.dmg -= this.descent;
					this.targets--;
					if(this.targets == 0) {
						return true;
					}
			
					if(e.damage(this.dmg, false)) {
						enemyLi.remove();
						Controller.removedEnemies.add(e);
						Controller.chr.kill(e.getName());
					}
				}
			}
		}
		return false;
	}

	@Override
	public void move() {
		this.x += this.xVel;
		this.y += this.yVel;
		this.xCoords.add((int)this.x);
		this.yCoords.add((int)this.y);
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		for(int q = this.xCoords.size()-10; q < this.xCoords.size(); q++) {
			if(q >= 0) {
				g.drawImage(this.img, this.xCoords.get(q)-this.size/2 - shiftX + 400, this.yCoords.get(q)-this.size/2 - shiftY + 400, size, size, i);
			}
		}
	}
}
