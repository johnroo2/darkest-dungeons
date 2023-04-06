package statuseffects;

import control.Controller;
import project.PopText;

public class AllyParalyze extends Status{
	public AllyParalyze(int duration) {
		this.img = this.status_paralyze;
		this.subtype = "paralyze";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyParalyze())) {
			new PopText("Paralyze");
		}
	}

	public AllyParalyze() {
		super();
		this.subtype = "paralyze";
	}
}
