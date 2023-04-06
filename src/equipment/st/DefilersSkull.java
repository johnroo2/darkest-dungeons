package equipment.st;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import control.Controller;
import equipment.ability.Ability;
import project.AoE;
import project.Area;
import project.Enemy;
import statuseffects.EnemyCurse;

public class DefilersSkull extends Ability{
	final static int dmg = 100;
	final static int healing = 45;
	final static double scaling = 5.5;
	final static int duration = 250;
	
	public DefilersSkull() {
		super("Defiler's Skull", "", 90);
		this.img = ART_DEFILERSKULL;
		this.subtype = "skull";
		this.tierDisplay = "ST";
		
		this.desc = new String[] {"Used by Necromancer", "Damage: " + dmg, "Healing: " + healing + " per target", 
				String.format("Inflicts Curse for %.2f seconds", ((double)duration * 0.006)), "MP Cost: " + this.manaCost, "+5 ATK"};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			if((Controller.mouseCoords[0]-400)*(Controller.mouseCoords[0]-400) + (Controller.mouseCoords[1]-400)*(Controller.mouseCoords[1]-400) < 90000) {
				new DefilersSkullShot(Controller.chr.getX() + Controller.mouseCoords[0] - 400, Controller.chr.getY() + Controller.mouseCoords[1] - 400, 
						dmg, healing);
				Controller.chr.setCastTicks(castRate);
				Controller.chr.setCurrentMana(Controller.chr.getCurrentMana()-this.manaCost);
			}
		}
	}
	
	@Override
	public void equip() {
		Controller.chr.setAttack(Controller.chr.getAttack() + 5);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setAttack(Controller.chr.getAttack() - 5);
	}
}

class DefilersSkullShot extends AoE{
	int dmg, healing;
	private ArrayList<Enemy> enemiesHit = new ArrayList<>();
	
	public DefilersSkullShot(int x, int y, int dmg, int healing) {
		super(x, y, 60, AOE_GRAY, 15);
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
					e.getEffects().add(new EnemyCurse(e, DefilersSkull.duration + (int)(Controller.chr.getSpellsurge() * DefilersSkull.scaling)));
				}
			}
		}
		Controller.chr.heal(this.healing * hits);
		return false;
	}
}
