package statuseffects;

import control.Controller;
import project.PopText;

public class AllyDisarmed extends Status{
	public AllyDisarmed(int duration) {
		this.img = this.status_disarmed;
		this.subtype = "disarmed";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyDisarmed())) {
			new PopText("Disarmed");
		}
	}
	
	public AllyDisarmed() {
		super();
		this.subtype = "disarmed";
	}
}
