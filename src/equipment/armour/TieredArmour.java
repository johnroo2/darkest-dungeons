package equipment.armour;

import control.Controller;
import project.Equipment;

public class TieredArmour extends Armour{
	int def, health, tier;

	public TieredArmour(String title, String desc, int def, int health, int tier) {
		super(title, desc);
		this.img = Equipment.ART_ARMOUR.get(tier);
		this.health = health;
		this.def = def;
		this.tier = tier;
		this.tierDisplay = "T"+this.tier;
		this.subtype = "heavy";
		if(this.tier > 0) {
			this.desc = new String[] {"Used by Warrior, Paladin", "+"+def+" DEF", "+"+health+" HP"};
		}
		else {
			this.desc = new String[] {"Used by Warrior, Paladin", "+"+def+" DEF"};
		}
	}
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + this.def);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() + this.health);
	}

	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - this.def);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() - this.health);
	}
}
