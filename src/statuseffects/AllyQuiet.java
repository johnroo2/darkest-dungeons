package statuseffects;

import control.Controller;
import project.PopText;

public class AllyQuiet extends Status{
	public AllyQuiet(int duration) {
		this.img = this.status_quiet;
		this.subtype = "quiet";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllyQuiet())) {
			new PopText("Quiet");
		}
	}

	public AllyQuiet() {
		super();
		this.subtype = "quiet";
	}
}