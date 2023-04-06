package equipment.ability;

import control.Controller;
import project.AoE;

public class TieredHelmShot extends AoE{
	public TieredHelmShot(int x, int y, int radius) {
		super(x, y, radius, AOE_PINK, 12);
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
