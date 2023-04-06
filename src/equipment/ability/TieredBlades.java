package equipment.ability;

import control.Controller;
import project.Equipment;

public class TieredBlades extends Ability{
	int tier, minDmg, maxDmg;
	int bounces = 2;
	double scaling;
	
	public TieredBlades(String title, String desc, int manaCost, int minDmg, int maxDmg, double scaling, int tier) {
		super(title, desc, manaCost);
		this.img = Equipment.ART_BLADES.get(tier); 
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.tier = tier;
		this.subtype = "blades";
		this.scaling = scaling;
		this.tierDisplay = "T"+tier;
		this.castRate = 20;
		
		this.desc = new String[] {"Used by Hunter", "Damage: " + minDmg + "-" + maxDmg, "Shots: 1", "Bounces: 2", "MP Cost: " + this.manaCost};
	}

	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TieredBladesShot(Controller.chr.getX(), Controller.chr.getY(), Controller.chr.getX() + Controller.mouseCoords[0] - 400, 
					Controller.chr.getY() + Controller.mouseCoords[1] - 400, 10, 70, tier, (int)(minDmg + scaling * 
					Controller.chr.getSpellsurge()), (int)(maxDmg + scaling * 
					Controller.chr.getSpellsurge()), bounces);	
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana() - this.manaCost);
		}	
	}
}
