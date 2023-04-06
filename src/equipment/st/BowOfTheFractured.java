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

public class BowOfTheFractured extends Weapon{
	int minDmg = 60;
	int maxDmg = 95;
	int dmgSide = 35;
	
	public BowOfTheFractured() {
		super("Bow Of The Fractured", "");
		this.img = Equipment.ART_BOWOFTHEFRACTURED;
		this.subtype = "bow";
		this.tierDisplay = "ST";
		this.fireRate = 100;
		
		this.desc = new String[] {"Used by Archer, Hunter", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 100%", "Shots: 1", 
				"On Shoot: Fires 2 Fracture Bolts", "Fracture Bolt Damage: 35", "Fracture Bolts Pierce Armour", "+3 DEF"};
	}

	@Override
	public void active() {
		new BowOfTheFracturedShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 11, 27, convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()));
		new BowOfTheFracturedSideShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 8, 34, dmgSide, -18);
		new BowOfTheFracturedSideShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 8, 34, dmgSide, 18);
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
	
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 3);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 3);
	}
}

class BowOfTheFracturedShot extends Projectile{
	final int SIZE = 32;
	
	int minDmg, maxDmg, size;
	BufferedImage img;

	public BowOfTheFracturedShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int minDmg, int maxDmg) {
		super(x, y, targetX, targetY, range, speed);
		this.size = SIZE;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));
		
		this.img = Rotate.rotateImage(Projectile.BOWOFTHEFRACTURED_SHOTS.get(0), rotation+45);
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

class BowOfTheFracturedSideShot extends Projectile{
	final int SIZE = 20;
	
	int size;
	int dmg;
	BufferedImage img;

	public BowOfTheFracturedSideShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int dmg, int alt) {
		super(x, y, targetX, targetY, range, speed);
		this.size = SIZE;
		this.dmg = dmg;
		
		double angle = getAngle(x, y, targetX, targetY) + alt;
		double rotation = angle;
		
		double tempX = this.xVel;
		double tempY = this.yVel;
		double sina = Math.sin(Math.toRadians(alt));
		double cosa = Math.cos(Math.toRadians(alt));
		
		this.xVel = tempX * cosa - tempY * sina;
		this.yVel = tempX * sina + tempY * cosa;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));
		
		this.img = Rotate.rotateImage(Projectile.BOWOFTHEFRACTURED_SHOTS.get(1), rotation+45);
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
				if(e.collision(this)) {
					if(e.damage(dmg, false, true)) {
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
