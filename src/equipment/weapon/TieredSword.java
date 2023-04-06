package equipment.weapon;

import control.Controller;
import project.Equipment;

public class TieredSword extends Weapon{
	int tier, minDmg, maxDmg;
	
	public TieredSword(String title, String desc, int minDmg, int maxDmg, int tier) {
		super(title, desc);
		this.img = Equipment.ART_SWORD.get(tier);
		this.tier = tier;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.subtype = "sword";
		this.tierDisplay = "T"+tier;
		this.fireRate = 100;
		
		this.desc = new String[] {"Used by Warrior, Paladin", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 100%", "Shots: 1"};
	}

	@Override
	public void active() {
		new TieredSwordShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 7, 23, tier,  convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()));
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
}
