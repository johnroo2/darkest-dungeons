package enemies.wildwetlands.forbiddenfountain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.AoE;
import statuseffects.AllySlow;

public class FFWaterbeam extends AoE{
	int dmg;
	boolean hit = false;
	
	public FFWaterbeam(int x, int y) {
		super(x, y, 60, AoE.WATERBEAM, 120);
		this.dmg = ForbiddenFountain.BEAM_DMG;
		Controller.enemyAoE.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(!hit && this.inRadius(Controller.chr.getX(), Controller.chr.getY(), 48) && this.range >= 12  && this.range <= 66) {
			hit = true;
			Controller.chr.damage("Forbidden Fountain", this.dmg, true);
			new AllySlow(167);
		}
		return false;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 0; i < 20; i ++) {
			if(this.range >= 114 - 6 * i) {
				return this.colour.get(i);
			}
		}
		return this.colour.get(0);
	}
	
	public boolean inRadius(int x, int y, int rad) {
		return (this.x-x)*(this.x-x) + (this.y+this.radius/2-20-y)*(this.y+this.radius/2-20-y) <= rad * rad;
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.radius/2 - shiftX + 400, (int)this.y-this.radius-this.radius/2 - shiftY + 400, radius,
				2*radius+(int)(radius * 5.0/37.0), i);
		
	}
}
