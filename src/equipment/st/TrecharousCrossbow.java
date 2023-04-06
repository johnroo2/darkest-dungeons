package equipment.st;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.ListIterator;

import control.Controller;
import equipment.weapon.Weapon;
import project.Area;
import project.Enemy;
import project.Equipment;
import project.Projectile;
import project.Rotate;

public class TrecharousCrossbow extends Weapon{
	final static int minDmg = 70;
	final static int maxDmg = 110;
	int alt = 1;
	
	public TrecharousCrossbow() {
		super("Trecharous Crossbow", "");
		this.img = Equipment.ART_TRECHAROUSCROSSBOW;
		this.subtype = "bow";
		this.tierDisplay = "ST";
		this.fireRate = 110;
		
		this.desc = new String[] {"Used by Archer, Hunter", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 110%", "Shots: 1"};
	}

	@Override
	public void active() {
		if(Controller.random.nextInt(0, 4) < 3) {
			alt *= -1;
		}
			
		new TrecharousCrossbowShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 15, 15,  convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), 15 * alt);
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
}

class TrecharousCrossbowShot extends Projectile{
	final int SIZE = 32;
	
	int size, minDmg, maxDmg;
	BufferedImage img;
	
	public TrecharousCrossbowShot(int x, int y, int targetX, int targetY, double speed, int range, int minDmg, int maxDmg, int alt) {
		super(x, y, targetX, targetY, range, speed);
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle + 45;
		
		//double opposite = speed * range * Math.tan(Math.toRadians(Math.abs(alt)));
		double oppositeX = alt * Math.cos(Math.toRadians(angle+90));
		double oppositeY = alt * Math.sin(Math.toRadians(angle+90));
		this.x += oppositeX;
		this.y += oppositeY;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.TRECHAROUSCROSSBOW_SHOT, rotation);
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
