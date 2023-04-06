package equipment.ability;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Equipment;

public class TieredScepter extends Ability{
	int tier, dmg, descent, targets;
	double scaling;

	public TieredScepter(String title, String desc, int manaCost, 
			int dmg, int descent, int targets, double scaling, int tier) {
		super(title, desc, manaCost);
		this.img = Equipment.ART_SCEPTER.get(tier);
		this.dmg = dmg;
		this.descent = descent;
		this.targets = targets;
		this.tier = tier;
		this.scaling = scaling;
		this.subtype = "scepter";
		this.tierDisplay = "T"+tier;
		
		this.desc = new String[] {"Used by Sorcerer", "Damage: " + dmg, "Targets: " + targets, "-" + descent + " per subsequent target", "MP Cost: " + this.manaCost};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TieredScepterShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 25, 20, tier, (int)(dmg + scaling * 
					Controller.chr.getSpellsurge()), descent, targets);	
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana() - this.manaCost);
		}
	}
}
