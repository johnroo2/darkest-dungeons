package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.AoE;
import project.Projectile;

public class FCSpearSummon extends AoE{
	public FCSpearSummon(int x, int y) {
		super(x, y, 60, AoE.SPEARSUMMON, 120);
		Controller.enemyAoE.add(this);
	}
	
	@Override
	public void destroy() {
		new FCInSpear((int)this.x, (int)this.y);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
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

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.radius/2 - shiftX + 400, (int)this.y-this.radius-this.radius/2 - shiftY + 400, radius,
				2*radius+(int)(radius * 5.0/37.0), i);
		
	}
}

class FCInSpear extends Projectile{
	final static int SIZE = 48;
	final static int ANIMATE_FRAMES = 20;
	
	int size;
	int turn;
	int castDist;
		
	public FCInSpear(int x, int y) {
		super(x, y, (double)0, ForsakenChampion.SPEAR_LIFETIME, 0);
		this.size = SIZE;
		Controller.enemyShots.add(this);
		if(Controller.coinflip()) {
			this.turn = 0;
		}
		else {
			this.turn = 45;
		}
	}

	@Override
	public boolean ranged() {
		if(range <= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public void act() {
		this.castDist += ForsakenChampion.SPEAR_CASTPLUS;
		for(int i = 0; i < 4; i++) {
			new FCSpearSpike((int)(this.x + castDist * Math.cos(Math.toRadians(turn + 90 * i))), 
					(int)(this.y + castDist * Math.sin(Math.toRadians(turn + 90 * i))));
		}
	}
	
	@Override
	public void move() {
		if(this.range % ForsakenChampion.SPEAR_FIRE == 0) {
			Controller.act(this);
			
		}
		this.range--;
		
	}
	
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(this.range % (4*ANIMATE_FRAMES) < i * ANIMATE_FRAMES) {
				return Projectile.IMGS_FORSAKENCHAMPION_INSPEAR.get(i-1);
			}
		}
		return Projectile.IMGS_FORSAKENCHAMPION_INSPEAR.get(0);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);		
	}	
}

class FCSpearSpike extends AoE{
	int dmg;
	boolean hit = false;
	
	public FCSpearSpike(int x, int y) {
		super(x, y, 27, AoE.SPEARSPIKE, 40);
		this.dmg = ForsakenChampion.SPEAR_DMG;
		Controller.enemyAoE.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(!hit && this.inRadius(Controller.chr.getX(), Controller.chr.getY(), 35) && this.range >= 16 && this.range <= 24) {
			hit = true;
			Controller.chr.damage("Forsaken Champion Spear", this.dmg);
		}
		return false;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 0; i < 20; i ++) {
			if(this.range >= 40 - 2 * i) {
				return this.colour.get(i);
			}
		}
		return this.colour.get(0);
	}
	
	public boolean inRadius(int x, int y, int rad) {
		return (this.x-x)*(this.x-x) + (this.y+this.radius/2-y)*(this.y+this.radius/2-y) <= rad * rad;
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.radius/2 - shiftX + 400, (int)this.y-this.radius/2 - shiftY + 400, radius,
				radius, i);
		
	}
}


