package equipment.ring;

import control.Controller;

public class VitRing extends Ring{
	int stat, health, tier;
	
	public VitRing(String title, String desc, int health, int stat, int tier) {
		super(title, desc);
		this.stat = stat;
		this.health = health;
		this.tier = tier;
		this.img = ART_VITRING.get(tier);
		this.tierDisplay = "T" + this.tier;
		
		this.desc = new String[] {"+"+stat+" VIT", "+"+health+" HP"};
	}

	@Override
	public void equip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()+this.health);
		Controller.chr.setVitality(Controller.chr.getVitality()+this.stat);
		
	}

	@Override
	public void dequip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()-this.health);
		Controller.chr.setVitality(Controller.chr.getVitality()-this.stat);
		
	}
}