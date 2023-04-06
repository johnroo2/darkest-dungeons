package statuseffects;

import control.Controller;
import project.PopText;

public class AllyArmoured extends Status{
	public AllyArmoured(int duration) {
		this.img = this.status_armoured;
		this.subtype = "armoured";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyArmoured())) {
			new PopText("Armoured");
		}
	}

	public AllyArmoured() {
		super();
		this.subtype = "armoured";
	}
}
