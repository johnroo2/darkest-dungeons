package enemies.corruptedcatacombs.tenescowrifleshtracker;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import project.Projectile;
import statuseffects.AllySilenced;

public class TenescowriFleshtrackerShot extends Projectile{
	final int SIZE = 40;
	int size, dmg;
	ArrayList<BufferedImage> imgs;

	public TenescowriFleshtrackerShot(int x, int y) {
		super(x, y, 0.0, TenescowriFleshtracker.SHOT_RANGE, 0);
		this.dmg = TenescowriFleshtracker.DMG;
		
		this.size = SIZE;
		this.imgs = Projectile.IMGS_TENESCOWRIFLESHTRACKER_SHOTS;
		Controller.enemyShots.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(inWall()) {
			return true;
		}
		if(Controller.chr.collision(this)) {
			Controller.chr.damage("Tenescowri Fleshtracker", this.dmg);
			new AllySilenced(333);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		this.range--;	
	}
	
	public BufferedImage getImage() {
		return this.imgs.get((this.range/2) % 9);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}

}
