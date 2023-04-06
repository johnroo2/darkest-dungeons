package enemies.silentsands.sandshaman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import project.AoE;
import statuseffects.AllyArmourBreak;
import statuseffects.AllySlow;

public class Sandbeam extends AoE{
	int dmg;
	boolean hit = false;
	
	public Sandbeam(int x, int y) {
		super(x, y, 60, AoE.SANDBEAM, 100);
		this.dmg = SandShaman.DMG;
		if(!Controller.enemyAoE.add(this)){
			System.out.println(Controller.enemyAoE.contains(this));
		}
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(!hit && this.inRadius(Controller.chr.getX(), Controller.chr.getY(), 48) && this.range >= 10  && this.range <= 55) {
			hit = true;
			Controller.chr.damage("Sand Shaman", this.dmg, true);
			new AllySlow(83);
		}
		return false;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 0; i < 20; i ++) {
			if(this.range >= 95 - 5 * i) {
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
