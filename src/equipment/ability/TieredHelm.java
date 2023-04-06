package equipment.ability;

import control.Controller;
import project.Equipment;
import statuseffects.*;

public class TieredHelm extends Ability{
	int duration, tier, basedef;
	double scaling;
	
	public TieredHelm(String title, String desc, int manaCost, 
			int duration, int basedef, double scaling, int tier) {
		super(title, desc, manaCost);
		this.img = Equipment.ART_HELM.get(tier);
		this.duration = duration;
		this.tier = tier;
		this.basedef = basedef;
		this.scaling = scaling;
		this.subtype = "helm";
		this.tierDisplay = "T"+tier;
		this.castRate = 167;
		
		this.desc = new String[] {"Used by Warrior", String.format("Beserk for %.2f seconds", ((double)duration * 0.006)), 
				String.format("Speedy for %.2f seconds", ((double)duration * 0.006)), "MP Cost: " + this.manaCost, 
				"+"+basedef+" DEF"};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TieredHelmShot(Controller.chr.getX(), Controller.chr.getY(), 100);
			Controller.chr.getEffects().add(new AllyBerserk((int) (this.duration + this.scaling * Controller.chr.getSpellsurge())));
			Controller.chr.getEffects().add(new AllySpeedy((int) (this.duration + this.scaling * Controller.chr.getSpellsurge())));
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana()-this.manaCost);
		}
	}
	
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + basedef);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - basedef);
	}
}
