package statuseffects;

import project.Enemy;
import project.PopText;

public class EnemyArmourBreak extends Status{
	public EnemyArmourBreak(Enemy e, int duration) {
		this.img = this.status_armourbreak;
		this.subtype = "armourbreak";
		this.duration = duration;
		
		this.unique = e.getEffects().add(this);
		
		if(e.ArmourBreakImmune()) {
			new PopText((int)e.getX(), (int)e.getY(), "Immune");
			this.duration = 0;
		}
		else {
			if(e.getEffects().contains(new EnemyArmourBreak()) && unique) {
				new PopText((int)e.getX(), (int)e.getY(), "Armour Broken");
			}
		}
	}
	
	public EnemyArmourBreak() {
		super();
		this.subtype = "armourbreak";
	}
}
