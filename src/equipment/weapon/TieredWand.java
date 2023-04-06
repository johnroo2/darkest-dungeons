package equipment.weapon;

import control.Controller;
import project.Equipment;

public class TieredWand extends Weapon{
	int tier, minDmg, maxDmg;
	
	public TieredWand(String title, String desc, int minDmg, int maxDmg, int tier) {
		super(title, desc);
		this.img = Equipment.ART_WAND.get(tier);
		this.tier = tier;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.subtype = "staff";
		this.tierDisplay = "T"+tier;
		this.fireRate = 100;
		
		this.desc = new String[] {"Used by Sorcerer, Necromancer", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 100%", "Shots: 1"};
	}

	@Override
	public void active() {
		new TieredWandShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 12, 29, tier, convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()));
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
}
