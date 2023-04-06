package equipment.weapon;

import control.Controller;
import project.Equipment;

public class TieredCrossbow extends Weapon{
	int tier, minDmg, maxDmg;
	
	public TieredCrossbow(String title, String desc, int minDmg, int maxDmg, int tier) {
		super(title, desc);
		this.img = Equipment.ART_CROSSBOW.get(tier);
		this.tier = tier;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.subtype = "bow";
		this.tierDisplay = "T"+tier;
		this.fireRate = 70;
		
		this.desc = new String[] {"Used by Archer, Hunter", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 70%", "Shots: 1"};
	}

	@Override
	public void active() {
		new TieredCrossbowShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 13, 20, tier,  convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()));
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
}
