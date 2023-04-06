package equipment.ability;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import control.Controller;
import project.AoE;
import project.Area;
import project.Enemy;

public class TieredSkullShot extends AoE{
	int dmg, healing;
	private ArrayList<Enemy> enemiesHit = new ArrayList<>();
	
	public TieredSkullShot(int x, int y, int dmg, int healing) {
		super(x, y, 80, AOE_RED, 15);
		this.dmg = dmg;
		this.healing = healing;
		Controller.allyAoE.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		int hits = 0;
		for(Area a: Area.list) {
			Iterator<Enemy> enemyLi = a.getEnemies().iterator();
			while(enemyLi.hasNext()) {
				Enemy e = enemyLi.next();
				if(e.inRadius((int)this.x, (int)this.y, this.radius) && !this.enemiesHit.contains(e)) {
					this.enemiesHit.add(e);
					hits++;
					if(e.damage(this.dmg, false)) {
						enemyLi.remove();
						Controller.removedEnemies.add(e);
						Controller.chr.kill(e.getName());
					}
				}
			}
		}
		Controller.chr.heal(this.healing * hits);
		return false;
	}
}
