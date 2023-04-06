package equipment.st;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.ListIterator;

import control.Controller;
import equipment.ability.Ability;
import equipment.ability.TieredHelmShot;
import equipment.ability.TieredQuiverShot;
import project.Area;
import project.Enemy;
import project.Equipment;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyBerserk;
import statuseffects.AllySpeedy;

public class ChampionsHelm extends Ability{
	final static int duration = 400;
	double scaling = 7.25;
	final static int dmg = 200;
	
	public ChampionsHelm() {
		super("Champion's Helm", "", 80);
		this.img = Equipment.ART_CHAMPIONSHELM;
		this.subtype = "helm";
		this.tierDisplay = "ST";
		this.castRate = 167;
		
		this.desc = new String[] {"Used by Warrior", String.format("Beserk for %.2f seconds", ((double)duration * 0.006)), 
				"Active: Fires 5 Crownshots", "Crownshots Damage: " + dmg, "Crownshots Pierce Armour", "MP Cost: " + this.manaCost, 
				"+5 DEX", "+5 SPD"};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TieredHelmShot(Controller.chr.getX(), Controller.chr.getY(), 100);
			Controller.chr.getEffects().add(new AllyBerserk((int) (duration + this.scaling * Controller.chr.getSpellsurge())));
			for(int i = -2; i < 3; i++) {
				new ChampionsHelmShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
						Controller.chr.getY() + Controller.mouseCoords[1] - 400, 6, 30, dmg, 15 * i);	
			}
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana()-this.manaCost);
		}
	}
	
	@Override
	public void equip() {
		Controller.chr.setDexterity(Controller.chr.getDexterity() + 5);
		Controller.chr.setSpeed(Controller.chr.getSpeed() + 5);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setDexterity(Controller.chr.getDexterity() - 5);
		Controller.chr.setSpeed(Controller.chr.getSpeed() - 5);
	}
}

class ChampionsHelmShot extends Projectile{
final int SIZE = 32;
	
	int dmg, size;
	BufferedImage img;

	public ChampionsHelmShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int dmg, int alt) {
		super(x, y, targetX, targetY, range, speed);
		this.size = SIZE;
		this.dmg = dmg;
		
		double angle = getAngle(x, y, targetX, targetY) + alt;
		double rotation = angle + 45;
		
		double tempX = this.xVel;
		double tempY = this.yVel;
		double sina = Math.sin(Math.toRadians(alt));
		double cosa = Math.cos(Math.toRadians(alt));
		
		this.xVel = tempX * cosa - tempY * sina;
		this.yVel = tempX * sina + tempY * cosa;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.CHAMPIONSHELM_SHOT, rotation);
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
