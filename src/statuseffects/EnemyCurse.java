package statuseffects;

import project.Enemy;
import project.PopText;

public class EnemyCurse extends Status{
	public EnemyCurse(Enemy e, int duration) {
		this.img = this.status_curse;
		this.subtype = "curse";
		this.duration = duration;
		
		this.unique = e.getEffects().add(this);
		
		if(e.CurseImmune()) {
			new PopText((int)e.getX(), (int)e.getY(), "Immune");
			this.duration = 0;
		}
		else {
			if(e.getEffects().contains(new EnemyCurse()) && unique) {
				new PopText((int)e.getX(), (int)e.getY(), "Curse");
			}
		}
	}
	
	public EnemyCurse() {
		super();
		this.subtype = "curse";
	}
}