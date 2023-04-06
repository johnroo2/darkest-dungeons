package equipment.st;

import control.Controller;
import equipment.ability.Ability;
import project.AoE;
import project.Equipment;
import statuseffects.AllyFervor;

public class TomeOfClarity extends Ability{
	private static int healing = 75;
	private static double scaling = 2.5;
	private static int duration = 300;
	private static double conversion = 0.15;
	
	public TomeOfClarity() {
		super("Tome of Clarity", "", 70);
		this.subtype = "tome";
		this.tierDisplay = "ST";
		this.img = Equipment.ART_TOMEOFCLARITY;
		
		this.desc = new String[] {"Used by Paladin", "Healing: " + healing, String.format("Fervor for %.2f seconds", ((double)duration * 0.006)), "MP Cost: " + this.manaCost, 
				"Restores 15% Damage Taken as MP", "+5 WIS"};
	}
	
	@Override
	public void active() {
		if(Controller.chr.getCurrentMana() >= this.manaCost) {
			new TomeofClarityShot(Controller.chr.getX(), Controller.chr.getY(), 90);
			Controller.chr.heal((int) (healing + scaling * Controller.chr.getSpellsurge()));
			new AllyFervor(duration);
			Controller.chr.setCastTicks(castRate);
			Controller.chr.setCurrentMana(Controller.chr.getCurrentMana()-this.manaCost);
		}
	}
	
	@Override
	public void passive(String event) {
		if(event.startsWith("damaged")) {
			Controller.chr.manaHeal((int)((Double.valueOf(event.substring(7)) * conversion)));	
			new TomeofClarityShot(Controller.chr.getX(), Controller.chr.getY(), 70);
		}
	}
	
	@Override
	public void equip() {
		Controller.chr.setWisdom(Controller.chr.getWisdom() + 5);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setWisdom(Controller.chr.getWisdom() - 5);
	}
}

class TomeofClarityShot extends AoE {
	public TomeofClarityShot(int x, int y, int radius) {
		super(x, y, radius, AOE_TURQUOISE, 15);
		Controller.enemyAoE.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		return false;
	}
}
