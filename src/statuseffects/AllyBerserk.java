package statuseffects;

import control.Controller;
import project.PopText;

public class AllyBerserk extends Status{
	public AllyBerserk(int duration) {
		this.img = this.status_berserk;
		this.subtype = "berserk";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyBerserk())) {
			new PopText("Berserk");
		}
	}

	public AllyBerserk() {
		super();
		this.subtype = "berserk";
	}
}
