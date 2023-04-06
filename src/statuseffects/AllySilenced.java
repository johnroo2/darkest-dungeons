package statuseffects;

import control.Controller;
import project.PopText;

public class AllySilenced extends Status{
	public AllySilenced(int duration) {
		this.img = this.status_silenced;
		this.subtype = "silenced";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllySilenced())) {
			new PopText("Silenced");
		}
	}
	
	public AllySilenced() {
		super();
		this.subtype = "silenced";
	}
}
