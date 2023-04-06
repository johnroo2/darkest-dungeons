package equipment.st;

import control.Controller;
import equipment.ability.Ability;
import equipment.ability.TieredHelmShot;
import project.Equipment;
import statuseffects.AllyArmoured;
import statuseffects.AllyBerserk;

public class ShriekcallersVisage extends Ability{
	final static int duration = 450;
	double scaling = 7.5;
	
	public ShriekcallersVisage() {
		super("Shriekcaller's Visage", "", 90);
		this.img = Equipment.ART_SHRIEKCALLERSVISAGE;
		this.subtype = "helm";
		this.tierDisplay = "ST";
		this.castRate = 167;
		
		this.desc = new String[] {"Used by Warrior", String.format("Beserk for %.2f seconds", ((double)duration * 0.006)), 
				String.format("Armoured for %.2f seconds", ((double)duration * 0.006)), 
				"MP Cost: " + this.manaCost, "+4 DEF", "+25 HP"};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TieredHelmShot(Controller.chr.getX(), Controller.chr.getY(), 110);
			Controller.chr.getEffects().add(new AllyBerserk((int) (duration + this.scaling * Controller.chr.getSpellsurge())));
			Controller.chr.getEffects().add(new AllyArmoured((int) (duration + this.scaling * Controller.chr.getSpellsurge())));
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana()-this.manaCost);
		}
	}
	
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 4);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() + 25);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 4);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() - 25);
	}
}
