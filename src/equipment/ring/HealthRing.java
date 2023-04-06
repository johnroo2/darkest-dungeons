package equipment.ring;

import control.Controller;

public class HealthRing extends Ring{
	int health, tier;
	
	public HealthRing(String title, String desc, int health, int tier) {
		super(title, desc);
		this.health = health;
		this.tier = tier;
		this.img = ART_HEALTHRING.get(tier);
		this.tierDisplay = "T" + this.tier;
		
		this.desc = new String[] {"+"+health+" HP"};
	}

	@Override
	public void equip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()+this.health);
		
	}

	@Override
	public void dequip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()-this.health);
		
	}
}
