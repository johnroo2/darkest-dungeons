package statuseffects;

import project.Enemy;
import project.PopText;

public class EnemyParalyze extends Status{
	public EnemyParalyze(Enemy e, int duration) {
		this.img = this.status_paralyze;
		this.subtype = "paralyze";
		this.duration = duration;
		
		this.unique = e.getEffects().add(this);
		
		if(e.ParalyzeImmune()) {
			new PopText((int)e.getX(), (int)e.getY(), "Immune");
			this.duration = 0;
		}
		else {
			if(e.getEffects().contains(new EnemyParalyze()) && unique) {
				new PopText((int)e.getX(), (int)e.getY(), "Paralyze");
			}
		}
	}
	
	public EnemyParalyze() {
		super();
		this.subtype = "paralyze";
	}
}