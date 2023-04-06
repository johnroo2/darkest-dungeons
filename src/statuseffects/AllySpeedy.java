package statuseffects;

import control.Controller;
import project.PopText;

public class AllySpeedy extends Status{
	public AllySpeedy(int duration) {
		this.img = this.status_speedy;
		this.subtype = "speedy";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllySpeedy())) {
			new PopText("Speedy");
		}
	}

	public AllySpeedy() {
		super();
		this.subtype = "speedy";
	}
}
