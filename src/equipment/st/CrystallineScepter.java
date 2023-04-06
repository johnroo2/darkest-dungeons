package equipment.st;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;

import control.Controller;
import equipment.ability.Ability;
import project.Area;
import project.Enemy;
import project.Equipment;
import project.Projectile;
import project.Rotate;
import statuseffects.EnemySlow;

public class CrystallineScepter extends Ability{
	private static int dmg = 180;
	private static int descent = 15;
	private static int targets = 6;
	private static double scaling = 3.75;
	
	static final int duration = 250;

	public CrystallineScepter() {
		super("Crystalline Scepter", "", 85);
		this.subtype = "scepter";
		this.tierDisplay = "ST";
		this.img = Equipment.ART_CRYSTALLINESCEPTER;
		
		this.desc = new String[] {"Used by Sorcerer", "Damage: " + dmg, "Targets: " + targets, "-" + descent + " per subsequent target", 
				String.format("Inflicts Slowed for %.2f seconds", ((double)duration * 0.006)), "MP Cost: " + this.manaCost,
				"+3 ATK"};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new CrystallineScepterShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 22, 24, (int)(dmg + scaling * 
					Controller.chr.getSpellsurge()), descent, targets);	
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana() - this.manaCost);
		}
	}
	
	@Override
	public void equip() {
		Controller.chr.setAttack(Controller.chr.getAttack() + 3);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setAttack(Controller.chr.getAttack() - 3);
	}
}

class CrystallineScepterShot extends Projectile{
	final int SIZE = 32;
	BufferedImage img;
	
	int dmg, descent, targets, size;
	double angle;
	ArrayList<Enemy> enemiesHit = new ArrayList<>();
	ArrayList<Integer> xCoords = new ArrayList<Integer>();
	ArrayList<Integer> yCoords = new ArrayList<Integer>();
	
	public CrystallineScepterShot(int x, int y, int targetX, int targetY, double speed, int range, 
			int dmg, int descent, int targets) {
		super(x, y, targetX, targetY, range, speed);
		this.dmg = dmg;
		this.descent = descent;
		this.targets = targets;
		
		this.angle = getAngle(x, y, targetX, targetY);
		double rotation = this.angle + 45;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.CRYSTALLINESCEPTER_SHOT, rotation);
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
					new EnemySlow(e, CrystallineScepter.duration);
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
