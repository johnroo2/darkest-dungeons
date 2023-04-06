package equipment.ring;

import control.Controller;

public class CritRing extends Ring{
	int stat, health, tier;
	
	public CritRing(String title, String desc, int health, int stat, int tier) {
		super(title, desc);
		this.stat = stat;
		this.health = health;
		this.tier = tier;
		this.img = ART_CRITRING.get(tier);
		this.tierDisplay = "T" + this.tier;
		
		this.desc = new String[] {"+"+stat+" CRT", "+"+health+" HP"};
	}

	@Override
	public void equip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()+this.health);
		Controller.chr.setCrit(Controller.chr.getCrit()+this.stat);
		
	}

	@Override
	public void dequip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()-this.health);
		Controller.chr.setCrit(Controller.chr.getCrit()-this.stat);
		
	}
}