package statuseffects;

import control.Controller;
import project.PopText;

public class AllyArmourBreak extends Status{
	public AllyArmourBreak(int duration) {
		this.img = this.status_armourbreak;
		this.subtype = "armourbreak";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyArmourBreak())) {
			new PopText("Armour Broken");
		}
	}
	
	public AllyArmourBreak() {
		super();
		this.subtype = "armourbreak";
	}
}
