package equipment.ability;

import control.Controller;
import project.Equipment;

public class TieredQuiver extends Ability{
	int tier, minDmg, maxDmg;
	double scaling;
	
	public TieredQuiver(String title, String desc, int manaCost, int minDmg, int maxDmg, double scaling, int tier) {
		super(title, desc, manaCost);
		this.img = Equipment.ART_QUIVER.get(tier);
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.tier = tier;
		this.scaling = scaling;
		this.subtype = "quiver";
		this.tierDisplay = "T"+tier;
		
		this.desc = new String[] {"Used by Archer", "Damage: " + minDmg + "-" + maxDmg, "Shots: 3", "MP Cost: " + this.manaCost};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TieredQuiverShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 15, 40, tier, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()), -15);	
			new TieredQuiverShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 15, 40, tier, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()), 0);	
			new TieredQuiverShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 15, 40, tier, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()), 15);	
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana() - this.manaCost);
		}	
	}
}
