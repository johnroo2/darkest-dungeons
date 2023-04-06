package statuseffects;

import project.Enemy;
import project.PopText;

public class EnemyExposed extends Status{
	public EnemyExposed(Enemy e, int duration) {
		this.img = this.status_exposed;
		this.subtype = "exposed";
		this.duration = duration;
		
		this.unique = e.getEffects().add(this);
		
		if(e.ExposedImmune()) {
			new PopText((int)e.getX(), (int)e.getY(), "Immune");
			this.duration = 0;
		}
		else {
			if(e.getEffects().contains(new EnemyExposed()) && unique) {
				new PopText((int)e.getX(), (int)e.getY(), "Exposed");
			}
		}
	}
	
	public EnemyExposed() {
		super();
		this.subtype = "exposed";
	}
}
