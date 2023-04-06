package statuseffects;

import project.Enemy;
import project.PopText;

public class EnemySlow extends Status{
	public EnemySlow(Enemy e, int duration) {
		this.img = this.status_slow;
		this.subtype = "slow";
		this.duration = duration;
		
		this.unique = e.getEffects().add(this);
		
		if(e.SlowImmune()) {
			new PopText((int)e.getX(), (int)e.getY(), "Immune");
			this.duration = 0;
		}
		else {
			if(e.getEffects().contains(new EnemySlow()) && unique ) {
				new PopText((int)e.getX(), (int)e.getY(), "Slowed");
			}
		}
	}
	
	public EnemySlow() {
		super();
		this.subtype = "slow";
	}
}
