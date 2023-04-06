package enemies.corruptedcatacombs.cryowarlock;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import project.Projectile;

public class CryoWarlockShot extends Projectile{
final int SIZE = 32;
	
	int size, dmg;
	ArrayList<BufferedImage> imgs = new ArrayList<>();

	public CryoWarlockShot(int x, int y, int ori) {
		super(x, y, (double)ori, CryoWarlock.SHOT_RANGE, CryoWarlock.SHOT_SPEED);
		this.dmg = CryoWarlock.DMG;
		this.range += CryoWarlock.SHOT_STALL;
		
		this.size = SIZE;
		this.imgs = Projectile.IMGS_CRYOWARLOCK_SHOTS;
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
			Controller.chr.damage("Cryo Warlock", this.dmg);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		if(this.range == CryoWarlock.SHOT_STALL) {
			this.xVel = 0;
			this.yVel = 0;
		}
		this.x += this.xVel;
		this.y += this.yVel;
		this.range--;	
	}
	
	public BufferedImage getImage() {
		return this.imgs.get((this.range/2) % 18);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
