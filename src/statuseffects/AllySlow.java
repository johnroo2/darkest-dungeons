package statuseffects;

import control.Controller;
import project.PopText;

public class AllySlow extends Status{
	public AllySlow(int duration) {
		this.img = this.status_slow;
		this.subtype = "slow";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllySlow())) {
			new PopText("Slowed");
		}
	}
	
	public AllySlow() {
		super();
		this.subtype = "slow";
	}
}
