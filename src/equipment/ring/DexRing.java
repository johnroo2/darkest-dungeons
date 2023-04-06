package equipment.ring;

import control.Controller;

public class DexRing extends Ring{
	int stat, health, tier;
	
	public DexRing(String title, String desc, int health, int stat, int tier) {
		super(title, desc);
		this.stat = stat;
		this.health = health;
		this.tier = tier;
		this.img = ART_DEXRING.get(tier);
		this.tierDisplay = "T" + this.tier;
		
		this.desc = new String[] {"+"+stat+" DEX", "+"+health+" HP"};
	}


	@Override
	public void equip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()+this.health);
		Controller.chr.setDexterity(Controller.chr.getDexterity()+this.stat);
		
	}

	@Override
	public void dequip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()-this.health);
		Controller.chr.setDexterity(Controller.chr.getDexterity()-this.stat);
		
	}
}