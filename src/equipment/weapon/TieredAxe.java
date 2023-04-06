package equipment.weapon;

import control.Controller;
import project.Equipment;

public class TieredAxe extends Weapon{
	int tier, minDmg, maxDmg;
	int alt = 1;
	
	public TieredAxe(String title, String desc, int minDmg, int maxDmg, int tier) {
		super(title, desc);
		this.img = Equipment.ART_AXE.get(tier);
		this.tier = tier;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.subtype = "sword";
		this.tierDisplay = "T"+tier;
		this.fireRate = 120;
		
		this.desc = new String[] {"Used by Warrior, Paladin", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 120%", "Shots: 1"};
	}

	@Override
	public void active() {
		new TieredAxeShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 8, 22, tier,  convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), alt * 8);
		this.alt *= -1;
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
}