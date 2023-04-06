package equipment.ring;

import control.Controller;

public class DefenseRing extends Ring{
	int stat, health, tier;
	
	public DefenseRing(String title, String desc, int health, int stat, int tier) {
		super(title, desc);
		this.stat = stat;
		this.health = health;
		this.tier = tier;
		this.img = ART_DEFENSERING.get(tier);
		this.tierDisplay = "T" + this.tier;
		
		this.desc = new String[] {"+"+stat+" DEF", "+"+health+" HP"};
	}

	@Override
	public void equip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()+this.health);
		Controller.chr.setDefense(Controller.chr.getDefense()+this.stat);
		
	}

	@Override
	public void dequip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()-this.health);
		Controller.chr.setDefense(Controller.chr.getDefense()-this.stat);
		
	}
}