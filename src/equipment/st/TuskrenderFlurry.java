package equipment.st;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Iterator;

import control.Controller;
import equipment.ability.Ability;
import project.Area;
import project.Enemy;
import project.Equipment;
import project.Projectile;
import project.Rotate;

public class TuskrenderFlurry extends Ability{
	static int minDmg = 250;
	static int maxDmg = 300; 
	static int minDmgSide = 75;
	static int maxDmgSide = 125;
	static double scaling = 4.5;
	static double scalingSide = 1.75;
	final static int duration = 250;

	public TuskrenderFlurry() {
		super("Tuskrender Flurry", "", 40);
		this.castRate = 40;
		this.subtype = "blades";
		this.tierDisplay = "ST";
		this.img = Equipment.ART_TUSKRENDERFLURRY;
		
		this.desc = new String[] {"Used by Hunter", "Damage: " + minDmg + "-" + maxDmg, 
				"Shots: 1", "Bounces: 0", "MP Cost: " + this.manaCost, "Active: Fires 4 Side Blades", 
				"Side Blade Damage: " + minDmgSide + "-" + maxDmgSide,"+2 ATK", "+2 DEX"};
	}
	
	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TuskrenderFlurryShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 12, 36, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()));
			new TuskrenderFlurrySideShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 2, 85, (int)(minDmgSide + scalingSide * 
							Controller.chr.getSpellsurge()), (int)(maxDmgSide + scalingSide * 
									Controller.chr.getSpellsurge()), -5, 35, 30);	
			new TuskrenderFlurrySideShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 2, 85, (int)(minDmgSide + scalingSide * 
							Controller.chr.getSpellsurge()), (int)(maxDmgSide + scalingSide * 
									Controller.chr.getSpellsurge()), 5, 35, 30);	
			new TuskrenderFlurrySideShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 2, 110, (int)(minDmgSide + scalingSide * 
							Controller.chr.getSpellsurge()), (int)(maxDmgSide + scalingSide * 
									Controller.chr.getSpellsurge()), -2.5, 18, 60);	
			new TuskrenderFlurrySideShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 2, 110, (int)(minDmgSide + scalingSide * 
							Controller.chr.getSpellsurge()), (int)(maxDmgSide + scalingSide * 
									Controller.chr.getSpellsurge()), 2.5, 18, 60);	
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana() - this.manaCost);
		}	
	}
	
	@Override
	public void equip() {
		Controller.chr.setAttack(Controller.chr.getAttack() + 2);
		Controller.chr.setDexterity(Controller.chr.getDexterity() + 2);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setAttack(Controller.chr.getAttack() - 2);
		Controller.chr.setDexterity(Controller.chr.getDexterity() - 2);
	}
}

class TuskrenderFlurryShot extends Projectile{
	final int SIZE = 32;
	
	int type, minDmg, maxDmg, size;
	BufferedImage img;

	public TuskrenderFlurryShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int minDmg, int maxDmg) {
		super(x, y, targetX, targetY, range, speed);
		this.size = SIZE;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle + 45;

		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.TUSKRENDERFLURRY_SHOTS.get(0), rotation);
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

class TuskrenderFlurrySideShot extends Projectile{
	final int SIZE = 24;
	
	int type, minDmg, maxDmg, size, lifetime, delay;
	BufferedImage img;

	public TuskrenderFlurrySideShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int minDmg, int maxDmg, double alt, int spread, int delay) {
		super(x, y, targetX, targetY, range, speed);
		this.size = SIZE;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.lifetime = range;
		this.delay = delay;
		
		double angle = getAngle(x, y, targetX, targetY) + alt;
		double rotation = angle + 45;
		
		//double opposite = speed * range * Math.tan(Math.toRadians(Math.abs(alt)));
		double oppositeX = spread * Math.cos(Math.toRadians(angle+90));
		double oppositeY = spread * Math.sin(Math.toRadians(angle+90));
		if(alt < 0) {
			this.x += oppositeX;
			this.y += oppositeY;
		}
		else {
			this.x -= oppositeX;
			this.y -= oppositeY;
		}
		
		double tempX = this.xVel;
		double tempY = this.yVel;
		double sina = Math.sin(Math.toRadians(alt));
		double cosa = Math.cos(Math.toRadians(alt));
		
		this.xVel = tempX * cosa - tempY * sina;
		this.yVel = tempX * sina + tempY * cosa;

		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.TUSKRENDERFLURRY_SHOTS.get(1), rotation);
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
	public void move() {
		double multiplier = 1;
		if(this.range < this.lifetime - delay) {
			multiplier = 0.1 * (lifetime - range);
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
