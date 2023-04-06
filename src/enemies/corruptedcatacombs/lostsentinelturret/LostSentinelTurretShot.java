package enemies.corruptedcatacombs.lostsentinelturret;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySilenced;

public class LostSentinelTurretShot extends Projectile{
	final int SIZE = 32;
	int size, dmg;
	int lifetime;
	BufferedImage img;

	public LostSentinelTurretShot(int x, int y) {
		super(x, y, 90.0, LostSentinelTurret.SHOT_RANGE, LostSentinelTurret.SHOT_SPEED);
		this.dmg = LostSentinelTurret.DMG;
		double rotation = 90 + 45;
		this.lifetime = this.range;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		this.img = Rotate.rotateImage(Projectile.IMGS_LOSTSENTINELTURRET_SHOTS, rotation);
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
