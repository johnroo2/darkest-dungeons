package equipment.ability;

import control.Controller;
import project.AoE;

public class TieredTomeShot extends AoE{
	public TieredTomeShot(int x, int y, int radius) {
		super(x, y, radius, AOE_LIME, 12);
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
