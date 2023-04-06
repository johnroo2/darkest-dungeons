package statuseffects;

import control.Controller;
import project.PopText;

public class AllySick extends Status{
	public AllySick(int duration) {
		this.img = this.status_sick;
		this.subtype = "sick";
		this.duration = duration;
		
		if(Controller.chr != null && Controller.chr.getEffects().add(this) && Controller.chr.getEffects().contains(new AllySick())) {
			new PopText("Sick");
		}
	}
	
	public AllySick() {
		super();
		this.subtype = "sick";
	}
}
