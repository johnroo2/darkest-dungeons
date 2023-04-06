package equipment.weapon;

import control.Controller;
import project.Equipment;

public class TieredBow extends Weapon{
	int tier, minDmg, maxDmg;
	
	public TieredBow(String title, String desc, int minDmg, int maxDmg, int tier) {
		super(title, desc);
		this.img = Equipment.ART_BOW.get(tier);
		this.tier = tier;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.subtype = "bow";
		this.tierDisplay = "T"+tier;
		this.fireRate = 100;
		
		this.desc = new String[] {"Used by Archer, Hunter", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 100%", "Shots: 2"};
	}

	@Override
	public void active() {
		new TieredBowShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 11, 25, tier, convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), -5);
		new TieredBowShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 11, 25, tier,  convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), 5);
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
}
