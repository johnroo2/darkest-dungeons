package equipment.st;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Iterator;

import control.Controller;
import equipment.weapon.Weapon;
import project.Area;
import project.Enemy;
import project.Equipment;
import project.Projectile;
import project.Rotate;

public class WandOfTheFallen extends Weapon{
	private static int minDmg = 120;
	private static int maxDmg = 150;
	
	int alt = -1;
	
	public WandOfTheFallen() {
		super("Wand Of The Fallen", "");
		this.subtype = "staff";
		this.fireRate = 90;
		this.tierDisplay = "ST";
		this.img = Equipment.ART_WANDOFTHEFALLEN;
		
		this.desc = new String[] {"Used by Sorcerer, Necromancer", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 90%", "Shots: 1", "+4 VIT"};
	}
	
	@Override
	public void active() {
		new WandOfTheFallenShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 5, 65, convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), alt);
		alt *= -1;
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
	
	@Override
	public void equip() {
		Controller.chr.setVitality(Controller.chr.getVitality() + 4);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setVitality(Controller.chr.getVitality() - 4);
	}
}

class WandOfTheFallenShot extends Projectile{
	final int SIZE = 32;
	
	int size, minDmg, maxDmg;
	int lifetime;
	double angle;
	double speed;
	int alt, initX, initY;
	BufferedImage img;
	
	final static double PERIOD = 70;
	
	public WandOfTheFallenShot(int x, int y, int targetX, int targetY, double speed, int range, int minDmg, int maxDmg, int alt) {
		super(x, y, targetX, targetY, range, speed);
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.lifetime = range;
		this.speed = speed;
		this.alt = alt;
		this.initX = x;
		this.initY = y;
		
		angle = getAngle(x, y, targetX, targetY);
		double rotation = angle + 45;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		if(alt == -1) {
			this.img = Rotate.rotateImage(Projectile.WANDOFTHEFALLEN_SHOTS.get(0), rotation);
		}
		else {
			this.img = Rotate.rotateImage(Projectile.WANDOFTHEFALLEN_SHOTS.get(1), rotation);
		}
		angle = Math.toRadians(angle);
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
		double sint = 3*Math.sin(4*Math.PI*t/PERIOD);
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		this.x = speed*(t*cosa-sint*sina) + initX;
		this.y = speed*(t*sina+sint*cosa) + initY;
		range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);
	}
}