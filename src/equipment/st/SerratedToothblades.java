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
import statuseffects.EnemySlow;

public class SerratedToothblades extends Ability{
	static int minDmg = 100;
	static int maxDmg = 150; 
	static double scaling = 3;
	final static int duration = 250;

	public SerratedToothblades() {
		super("Serrated Toothblades", "", 40);
		this.castRate = 40;
		this.subtype = "blades";
		this.tierDisplay = "ST";
		this.img = Equipment.ART_SERRATEDTOOTHBLADES;
		
		this.desc = new String[] {"Used by Hunter", "Damage: " + minDmg + "-" + maxDmg, 
				String.format("Inflicts Slowed for %.2f seconds", ((double)duration * 0.006)), 
				"Shots: 2", "Bounces: 0", "MP Cost: " + this.manaCost};
	}
	
	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new SerratedToothbladesShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 8, 60, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()), 12);
			new SerratedToothbladesShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 8, 60, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()), -12);	
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana() - this.manaCost);
		}	
	}
}

class SerratedToothbladesShot extends Projectile{
	final int SIZE = 32;
	
	int type, minDmg, maxDmg, size;
	BufferedImage img;

	public SerratedToothbladesShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int minDmg, int maxDmg, int alt) {
		super(x, y, targetX, targetY, range, speed);
		this.size = SIZE;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		
		double angle = getAngle(x, y, targetX, targetY) + alt;
		double rotation = angle + 45;
		
		double tempX = this.xVel;
		double tempY = this.yVel;
		double sina = Math.sin(Math.toRadians(alt));
		double cosa = Math.cos(Math.toRadians(alt));
		
		this.xVel = tempX * cosa - tempY * sina;
		this.yVel = tempX * sina + tempY * cosa;

		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.SERRATEDTOOTHBLADES_SHOT, rotation);
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
					e.getEffects().add(new EnemySlow(e, SerratedToothblades.duration));
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
