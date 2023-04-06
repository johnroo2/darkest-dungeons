package equipment.ring;

import control.Controller;

public class AttackRing extends Ring{
	int stat, health, tier;
	
	public AttackRing(String title, String desc, int health, int stat, int tier) {
		super(title, desc);
		this.stat = stat;
		this.health = health;
		this.tier = tier;
		this.img = ART_ATTACKRING.get(tier);
		this.tierDisplay = "T" + this.tier;
		
		this.desc = new String[] {"+"+stat+" ATK", "+"+health+" HP"};
	}
	@Override
	public void equip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()+this.health);
		Controller.chr.setAttack(Controller.chr.getAttack()+this.stat);
		
	}

	@Override
	public void dequip() {
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth()-this.health);
		Controller.chr.setAttack(Controller.chr.getAttack()-this.stat);
		
	}
}
