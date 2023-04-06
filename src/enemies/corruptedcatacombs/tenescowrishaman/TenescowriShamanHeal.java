package enemies.corruptedcatacombs.tenescowrishaman;

import control.Controller;
import project.AoE;

public class TenescowriShamanHeal extends AoE{
	public TenescowriShamanHeal(int x, int y) {
		super(x, y, 90, AOE_GREEN, 15);
		Controller.enemyAoE.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		return false;
	}
}
