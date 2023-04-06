package statuseffects;

import control.Controller;
import project.PopText;

public class AllyExposed extends Status{
	public AllyExposed(int duration) {
		this.img = this.status_exposed;
		this.subtype = "exposed";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyExposed())) {
			new PopText("Exposed");
		}
	}

	public AllyExposed() {
		super();
		this.subtype = "exposed";
	}
}