package equipment.st;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import control.Controller;
import equipment.ability.Ability;
import project.Area;
import project.Enemy;
import project.Equipment;
import project.Projectile;
import project.Rotate;

public class Rivermaker extends Ability{
	private static int dmg = 125;
	private static int descent = 20;
	private static int targets = 4;
	private static double scaling = 2;

	public Rivermaker() {
		super("Rivermaker", "", 70);
		this.subtype = "scepter";
		this.tierDisplay = "ST";
		this.img = Equipment.ART_RIVERMAKER;
		
		this.desc = new String[] {"Used by Sorcerer", "Damage: " + dmg, "Targets: " + targets, "-" + descent + " per subsequent target", 
				"Shots: 4", "MP Cost: " + this.manaCost,
				"+4 SPD"};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new RivermakerShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 15, 16, (int)(dmg + scaling * 
					Controller.chr.getSpellsurge()), descent, targets, 4);	
			new RivermakerShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 15, 16, (int)(dmg + scaling * 
					Controller.chr.getSpellsurge()), descent, targets, -4);	
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana() - this.manaCost);
		}
	}
	
	@Override
	public void equip() {
		Controller.chr.setSpeed(Controller.chr.getSpeed() + 4);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setSpeed(Controller.chr.getSpeed() - 4);
	}
}

class RivermakerShot extends Projectile{
	final int SIZE = 32;
	BufferedImage img;
	
	int dmg, descent, targets, size;
	double angle;
	ArrayList<Enemy> enemiesHit = new ArrayList<>();
	ArrayList<Integer> xCoords = new ArrayList<Integer>();
	ArrayList<Integer> yCoords = new ArrayList<Integer>();
	
	public RivermakerShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int dmg, int descent, int targets, int alt) {
		super(x, y, targetX, targetY, range, speed);
		this.dmg = dmg;
		this.descent = descent;
		this.targets = targets;
		
		this.angle = getAngle(x, y, targetX, targetY) + alt;
		double rotation = this.angle + 45;
		
		double tempX = this.xVel;
		double tempY = this.yVel;
		double sina = Math.sin(Math.toRadians(alt));
		double cosa = Math.cos(Math.toRadians(alt));
		
		this.xVel = tempX * cosa - tempY * sina;
		this.yVel = tempX * sina + tempY * cosa;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.RIVERMAKER_SHOT, rotation);
		Controller.allyShots.add(this);
	}

	@Override
	public void destroy() {
		new RivermakerSplitshot((int)(this.x-this.xVel), (int)(this.y-this.yVel), this.angle + 6, 15, 16, this.dmg, this.descent, this.targets);
		new RivermakerSplitshot((int)(this.x-this.xVel), (int)(this.y-this.yVel), this.angle - 6, 15, 16, this.dmg, this.descent, this.targets);
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

class RivermakerSplitshot extends Projectile {
	final int SIZE = 32;
	
	int dmg, descent, targets, size;
	BufferedImage img;
	ArrayList<Enemy> enemiesHit = new ArrayList<>();
	ArrayList<Integer> xCoords = new ArrayList<Integer>();
	ArrayList<Integer> yCoords = new ArrayList<Integer>();
	
	public RivermakerSplitshot(int x, int y, double ori, double speed, int range, 
			int dmg, int descent, int targets) {
		super(x, y, ori, range, speed);
		this.dmg = dmg;
		this.descent = descent;
		this.targets = targets;
		
		double rotation = ori + 45;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.RIVERMAKER_SHOT, rotation);
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
		for(int q = this.xCoords.size()-10; q < this.xCoords.size()-1; q+=2) {
			if(q >= 0) {
				g.drawImage(this.img, this.xCoords.get(q)-this.size/2 - shiftX + 400, this.yCoords.get(q)-this.size/2 - shiftY + 400, size, size, i);
			}
		}
	}
}
