package equipment.armour;

import control.Controller;
import project.Equipment;

public class TieredLeather extends Armour{
	int dex, def, vit, tier;

	public TieredLeather(String title, String desc, int dex, int def, int vit, int tier) {
		super(title, desc);
		this.img = Equipment.ART_LEATHER.get(tier);
		this.dex = dex;
		this.def = def;
		this.vit = vit;
		this.tier = tier;
		this.tierDisplay = "T"+this.tier;
		this.subtype = "leather";
		
		if(this.tier > 1) {
			this.desc = new String[] {"Used by Archer, Hunter", "+"+dex+" DEX", "+"+def+" DEF", "+"+vit+" VIT"};
		}
		else if(this.tier == 1) {
			this.desc = new String[] {"Used by Archer, Hunter", "+"+def+" DEF", "+"+vit+" VIT"};
		}
		else {
			this.desc = new String[] {"Used by Archer, Hunter", "+"+def+" DEF"};
		}
	}
	@Override
	public void equip() {
		Controller.chr.setDexterity(Controller.chr.getDexterity() + this.dex);
		Controller.chr.setDefense(Controller.chr.getDefense() + this.def);
		Controller.chr.setVitality(Controller.chr.getVitality() + this.vit);
	}

	@Override
	public void dequip() {
		Controller.chr.setDexterity(Controller.chr.getDexterity() - this.dex);
		Controller.chr.setDefense(Controller.chr.getDefense() - this.def);
		Controller.chr.setVitality(Controller.chr.getVitality() - this.vit);
	}
}
