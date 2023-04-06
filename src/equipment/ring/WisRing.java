package equipment.ring;

import control.Controller;

public class WisRing extends Ring{
	int stat, health, tier;
	
	public WisRing(String title, String desc, int health, int stat, int tier) {
		super(title, desc);
		this.stat = stat;
		this.health = health;
		this.tier = tier;
		this.img = ART_WISRING.get(tier);
		this.tierDisplay = "T" + this.tier;
		
		this.desc = new String[] {"+"+stat+" WIS", "+"+health+" HP"};
	}

	@Override
	public void equip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()+this.health);
		Controller.chr.setWisdom(Controller.chr.getWisdom()+this.stat);
		
	}

	@Override
	public void dequip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()-this.health);
		Controller.chr.setWisdom(Controller.chr.getWisdom()-this.stat);
		
	}
}