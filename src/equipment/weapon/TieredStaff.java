package equipment.weapon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Equipment;

public class TieredStaff extends Weapon{
	int tier, minDmg, maxDmg;
	
	public TieredStaff(String title, String desc, int minDmg, int maxDmg, int tier) {
		super(title, desc);
		this.img = Equipment.ART_STAFF.get(tier);
		this.tier = tier;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.subtype = "staff";
		this.tierDisplay = "T"+tier;
		this.fireRate = 100;
		
		this.desc = new String[] {"Used by Sorcerer, Necromancer", "Damage: " + minDmg + "-" + maxDmg, "Rate of Fire: 100%", "Shots: 2"};
	}

	@Override
	public void active() {
		new TieredStaffShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 10, 32, tier, convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), 0);
		new TieredStaffShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
				Controller.chr.getY() + Controller.mouseCoords[1] - 400, 10, 32, tier, convert(minDmg, Controller.chr.getAttack()), 
				convert(maxDmg, Controller.chr.getAttack()), 1);
		Controller.chr.setFireTicks(this.getFireCooldown(Controller.chr.getDexterity()));
	}
}
