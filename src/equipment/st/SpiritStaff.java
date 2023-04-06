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

public class SpiritStaff extends Weapon{
	private final static int minDmg = 25;
	private final static int maxDmg = 35;
	
	public SpiritStaff() {
		super("Spirit Staff", "");
		this.tierDisplay = "ST";
		this.img = Equipment.ART_SPIRITSTAFF;
		this.fireRate = 150;
		this.subtype = "staff";
		
		this.desc = new String[] {"Used by Sorcerer, Necromancer", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 150%", "Shots: 2"};
	}

	@Override
	public void active() {
		new SpiritStaffShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 8, 35, convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), 0);
		new SpiritStaffShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 8, 35, convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), 1);
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
}

class SpiritStaffShot extends Projectile{
	final int SIZE = 24;
	final double PERIOD = 50;
	
	int type, size, minDmg, maxDmg, alt, lifetime;
	int initX, initY, speed;
	double angle;
	BufferedImage img;
	
	public SpiritStaffShot(int x, int y, int targetX, int targetY, double speed, int range, int minDmg, int maxDmg, int alt) {
		super(x, y, targetX, targetY, range, speed);
		this.lifetime = range;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.alt = alt;
		this.initX = x;
		this.initY = y;
		this.speed = (int)speed;
		
		double angle = getAngle(x, y, targetX, targetY);
		this.angle = Math.toRadians(angle);
		double rotation = angle + 45;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.SPIRITSTAFF_SHOT, rotation);
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
		int t = (lifetime - range);
		double sint = 6*Math.sin(4*Math.PI*t/PERIOD);
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		if(alt == 0) {
			this.x = speed*(t*cosa-sint*sina) + initX;
			this.y = speed*(t*sina+sint*cosa) + initY;
		}
		else {
			this.x = speed*(t*cosa+sint*sina) + initX;
			this.y = speed*(t*sina-sint*cosa) + initY;
		}
		range--;
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);
	}
}