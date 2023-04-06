package project;

import control.Controller;

public class LootRoll {
	private Equipment item;
	private int prob;
	private int roll;

	public LootRoll(Equipment item, int prob, int roll) {
		this.item = item;
		this.prob = prob;
		this.roll = roll;
	}
	
	public LootRoll(String item, int prob, int roll) {
		if(item.startsWith("w")) {
			this.item = Equipment.getTieredWeapon(Integer.valueOf(item.substring(1)));
		}
		else if(item.startsWith("ab")) {
			this.item = Equipment.getTieredAbility(Integer.valueOf(item.substring(2)));
		}
		else if(item.startsWith("ar")) {
			this.item = Equipment.getTieredArmour(Integer.valueOf(item.substring(2)));
		}
		else if(item.startsWith("r")) {
			this.item = Equipment.getTieredRing(Integer.valueOf(item.substring(1)));
		}
		this.prob = prob;
		this.roll = roll;
	}
	
	public boolean run(int culm) {
		if(Controller.random.nextInt(0, roll) < prob + culm) {
			return true;
		}
		return false;
	}
	
	public Equipment getItem() {return this.item;}
	public int getProb() {return this.prob;}
	public int getRoll() {return this.roll;}
}
