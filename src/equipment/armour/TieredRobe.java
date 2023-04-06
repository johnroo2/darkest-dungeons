package equipment.armour;

import control.Controller;
import project.Equipment;

public class TieredRobe extends Armour{
	int atk, def, wis, mana, tier;

	public TieredRobe(String title, String desc, int atk, int def, int wis, int mana, int tier) {
		super(title, desc);
		this.img = Equipment.ART_ROBE.get(tier);
		this.atk = atk;
		this.def = def;
		this.wis = wis;
		this.mana = mana;
		this.tier = tier;
		this.tierDisplay = "T"+this.tier;
		this.subtype = "robe";
		
		if(this.tier > 1) {
			this.desc = new String[] {"Used by Sorcerer, Necromancer", "+"+atk+" ATK", "+"+def+" DEF", "+"+wis+" WIS", "+"+mana+" MP"};
		}
		else if(this.tier == 1) {
			this.desc = new String[] {"Used by Sorcerer, Necromancer", "+"+def+" DEF", "+"+wis+" WIS"};
		}
		else {
			this.desc = new String[] {"Used by Sorcerer, Necromancer", "+"+def+" DEF"};
		}
	}

	@Override
	public void equip() {
		Controller.chr.setAttack(Controller.chr.getAttack() + this.atk);
		Controller.chr.setDefense(Controller.chr.getDefense() + this.def);
		Controller.chr.setWisdom(Controller.chr.getWisdom() + this.wis);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() + this.mana);
	}

	@Override
	public void dequip() {
		Controller.chr.setAttack(Controller.chr.getAttack() - this.atk);
		Controller.chr.setDefense(Controller.chr.getDefense() - this.def);
		Controller.chr.setWisdom(Controller.chr.getWisdom() - this.wis);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() - this.mana);
	}
}
