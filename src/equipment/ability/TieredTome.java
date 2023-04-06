package equipment.ability;

import control.Controller;
import project.Equipment;
import statuseffects.AllyFervor;

public class TieredTome extends Ability{
	int healing, tier, duration;
	double scaling;
	
	public TieredTome(String title, String desc, int manaCost, 
			int healing, int duration, double scaling, int tier) {
		super(title, desc, manaCost);
		this.img = Equipment.ART_TOME.get(tier);
		this.healing = healing;
		this.tier = tier;
		this.duration = duration;
		this.scaling = scaling;
		this.subtype = "tome";
		this.tierDisplay = "T"+tier;
		
		this.desc = new String[] {"Used by Paladin", "Healing: " + healing, String.format("Fervor for %.2f seconds", ((double)duration * 0.006)), "MP Cost: " + this.manaCost};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TieredTomeShot(Controller.chr.getX(), Controller.chr.getY(), 80);
			Controller.chr.heal((int) (this.healing + this.scaling * Controller.chr.getSpellsurge()));
			new AllyFervor(duration);
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana()-this.manaCost);
		}
	}
}