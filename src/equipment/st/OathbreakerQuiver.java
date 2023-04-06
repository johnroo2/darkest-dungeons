package equipment.st;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.ListIterator;

import control.Controller;
import equipment.ability.Ability;
import equipment.ability.TieredQuiverShot;
import project.Area;
import project.Enemy;
import project.Equipment;
import project.Projectile;
import project.Rotate;
import statuseffects.EnemySlow;

public class OathbreakerQuiver extends Ability{
	final static int minDmg = 75;
	final static int maxDmg = 125;
	final static int duration = 250;
	
	final static double scaling = 3;
	
	public OathbreakerQuiver() {
		super("Oathbreaker Quiver", "", 70);
		this.img = Equipment.ART_OATHBREAKERQUIVER;
		this.subtype = "quiver";
		this.tierDisplay = "ST";
		
		this.desc = new String[] {"Used by Archer", "Damage: " + minDmg + "-" + maxDmg, String.format("Inflicts Slowed for %.2f seconds", ((double)duration * 0.006)),
				"Shots: 2", "MP Cost: " + this.manaCost};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new OathbreakerQuiverShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 12, 48, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()), 12);	
			new OathbreakerQuiverShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 12, 48, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()), -12);	
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana() - this.manaCost);
		}	
	}
}

class OathbreakerQuiverShot extends Projectile{
final int SIZE = 35;
	
	int size, minDmg, maxDmg;
	BufferedImage img;
	
	public OathbreakerQuiverShot(int x, int y, int targetX, int targetY, double speed, int range, int minDmg, int maxDmg, int alt) {
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
		
		this.img = Rotate.rotateImage(Projectile.OATHBREAKERQUIVER_SHOT, rotation);
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
					if(e.damage(Controller.random.nextInt(this.minDmg, this.maxDmg+1), false)) {
						enemyLi.remove();
						Controller.removedEnemies.add(e);
						Controller.chr.kill(e.getName());
					}
					e.getEffects().add(new EnemySlow(e, OathbreakerQuiver.duration));
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
