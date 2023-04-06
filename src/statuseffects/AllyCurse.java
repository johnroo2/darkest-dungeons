package statuseffects;

import control.Controller;
import project.PopText;

public class AllyCurse extends Status{
	public AllyCurse(int duration) {
		this.img = this.status_curse;
		this.subtype = "curse";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyCurse())) {
			new PopText("Curse");
		}
	}

	public AllyCurse() {
		super();
		this.subtype = "curse";
	}
}
