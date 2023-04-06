package equipment.st;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.ListIterator;

import control.Controller;
import equipment.armour.Armour;
import project.Area;
import project.Enemy;
import project.Projectile;
import project.Rotate;

public class RoyalScorpionHide extends Armour{
	private static int COOLDOWN = 500;
	private int ticks = 0;
	
	public RoyalScorpionHide() {
		super("Royal Scorpion Hide", "");
		this.tierDisplay = "ST";
		this.img = ART_ROYALSCORPIONHIDE;
		this.subtype = "leather";
		
		this.desc = new String[] {"Used by Archer, Hunter", "+6 DEF", "Reactive Proc: Fires 12 Scorpion Eyes", 
				"Scorpion Eye Damage: 100", "Scorpion Eyes Pierces Armour"};
	}

	@Override
	public void passive(String event) {
		if(ticks > 0) {
			ticks--;
		}
		if(event.equals("hit") && ticks <= 0) {
			ticks = COOLDOWN;
			for(int i = 0; i < 12; i++) {
				new RoyalScorpionHideShot(Controller.chr.getX(), Controller.chr.getY(), 30 * i);
			}
		}
	}
	
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 6);
	}

	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 6);
	}
}

class RoyalScorpionHideShot extends Projectile{
	private final static int dmg = 100;
	private final static int dist = 50;
	private final static double speed = 4;
	final int SIZE = 20;
	
	int size, range;
	BufferedImage img;
	
	public RoyalScorpionHideShot(int x, int y, int ori) {
		super(x, y, (double)ori, dist, speed);
		
		this.range = dist;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		
		this.img = Rotate.rotateImage(Projectile.ROYALSCORPIONHIDE_SHOT, rotation);
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