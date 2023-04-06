package equipment.ring;

import control.Controller;

public class SpeedRing extends Ring{
	int stat, health, tier;
	
	public SpeedRing(String title, String desc, int health, int stat, int tier) {
		super(title, desc);
		this.stat = stat;
		this.health = health;
		this.tier = tier;
		this.img = ART_SPEEDRING.get(tier);
		this.tierDisplay = "T" + this.tier;
		
		this.desc = new String[] {"+"+stat+" SPD", "+"+health+" HP"};
	}

	@Override
	public void equip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()+this.health);
		Controller.chr.setSpeed(Controller.chr.getSpeed()+this.stat);
		
	}

	@Override
	public void dequip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()-this.health);
		Controller.chr.setSpeed(Controller.chr.getSpeed()-this.stat);
		
	}
}