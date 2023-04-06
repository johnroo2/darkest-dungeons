package enemies.silentsands.spikedturtle;

import control.Controller;
import project.AoE;
import statuseffects.AllyQuiet;

public class SpikedTurtleShot extends AoE{
	int dmg;
	boolean hit = false;
	
	public SpikedTurtleShot(int x, int y) {
		super(x, y, SpikedTurtle.SHOT_RANGE, AOE_DARKGREEN, 15);
		this.dmg = SpikedTurtle.DMG;
		Controller.enemyAoE.add(this);
	}
	
	public boolean inRadius(int x, int y, int radius) {
		return (this.x-x)*(this.x-x) + (this.y-y)*(this.y-y) <= radius * radius;
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(!hit && this.inRadius(Controller.chr.getX(), Controller.chr.getY(), SpikedTurtle.SHOT_RANGE)) {
			hit = true;
			Controller.chr.damage("Spiked Turtle", this.dmg, true);
			new AllyQuiet(333);
		}
		return false;
	}
}
