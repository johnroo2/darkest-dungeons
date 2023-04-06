package enemies.corruptedcatacombs.lostsentineltower;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySilenced;

public class LostSentinelTowerShot extends Projectile{
	final int SIZE = 32;
	int size, dmg;
	int lifetime;
	BufferedImage img;

	public LostSentinelTowerShot(int x, int y, double ori) {
		super(x, y, ori, LostSentinelTower.SHOT_RANGE, LostSentinelTower.SHOT_SPEED);
		this.dmg = LostSentinelTower.DMG;
		double rotation = ori + 45;
		this.lifetime = this.range;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		this.img = Rotate.rotateImage(Projectile.IMGS_LOSTSENTINELTOWER_SHOTS, rotation);
		Controller.enemyShots.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(inWall() && this.range < this.lifetime - 50) {
			return true;
		}
		if(Controller.chr.collision(this)) {
			Controller.chr.damage("Lost Sentinel", this.dmg, true);
			new AllySilenced(417); //2.5 seconds
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		this.x += this.xVel;
		this.y += this.yVel;
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
