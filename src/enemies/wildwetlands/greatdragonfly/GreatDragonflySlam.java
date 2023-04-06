package enemies.wildwetlands.greatdragonfly;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.AoE;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySilenced;

public class GreatDragonflySlam extends AoE{
	int dmg;
	boolean hit = false;
	
	public GreatDragonflySlam(int x, int y) {
		super(x, y, GreatDragonfly.AOE_RADIUS, AOE_VIOLET, 8);
		this.dmg = GreatDragonfly.DMG_AOE;
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
		if(!hit && this.inRadius(Controller.chr.getX(), Controller.chr.getY(), (int)(GreatDragonfly.AOE_RADIUS * 1.5))) {
			hit = true;
			Controller.chr.damage("Great Dragonfly Bomb", this.dmg, true);
			new AllySilenced(83); //0.5 seconds
		}
		return false;
	}
}
