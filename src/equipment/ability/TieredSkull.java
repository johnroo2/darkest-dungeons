package equipment.ability;

import control.Controller;
import project.Equipment;
import project.Projectile;

public class TieredSkull extends Ability{
	int dmg, healing, tier;
	double scaling;
	
	public TieredSkull(String title, String desc, int manaCost, 
			int dmg, int healing, double scaling, int tier) {
		super(title, desc, manaCost);
		this.img = Equipment.ART_SKULL.get(tier);
		this.dmg = dmg;
		this.healing = healing;
		this.tier = tier;
		this.scaling = scaling;
		this.subtype = "skull";
		this.tierDisplay = "T"+tier;
		
		this.desc = new String[] {"Used by Necromancer", "Damage: " + dmg, "Healing: " + healing + " per target", "MP Cost: " + this.manaCost};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			if((Controller.mouseCoords[0]-400)*(Controller.mouseCoords[0]-400) + (Controller.mouseCoords[1]-400)*(Controller.mouseCoords[1]-400) < 90000) {
				new TieredSkullShot(Controller.chr.getX() + Controller.mouseCoords[0] - 400, Controller.chr.getY() + Controller.mouseCoords[1] - 400, 
						(int)(this.dmg + scaling * 
						Controller.chr.getSpellsurge()), this.healing);
				Controller.chr.setCastTicks(castRate);
				Controller.chr.setCurrentMana(Controller.chr.getCurrentMana()-this.manaCost);
			}
		}
	}
}
