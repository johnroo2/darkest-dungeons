package statuseffects;

import control.Controller;
import project.PopText;

public class AllyFervor extends Status{
	public AllyFervor(int duration) {
		this.img = this.status_fervor;
		this.subtype = "fervor";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyFervor())) {
			new PopText("Fervor");
		}
	}

	public AllyFervor() {
		super();
		this.subtype = "fervor";
	}
}