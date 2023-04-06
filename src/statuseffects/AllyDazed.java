package statuseffects;

import control.Controller;
import project.PopText;

public class AllyDazed extends Status{
	public AllyDazed(int duration) {
		this.img = this.status_dazed;
		this.subtype = "dazed";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyDazed())) {
			new PopText("Dazed");
		}
	}
	
	public AllyDazed() {
		super();
		this.subtype = "dazed";
	}
}
