package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.volatilevolcano.powdermonkey.PowderMonkey;
import project.Projectile;
import project.Rotate;

public class FCWhip extends Projectile{
	final static int SIZE = 24;
	int size, dmg;
	double speed;
	double angle;
	double initX;
	double initY;
	double period;
	int alt;
	BufferedImage img;
	
	public FCWhip(int x, int y, double ori, double speed, double period, int alt) {
		super(x, y, ori, ForsakenChampion.WHIP_RANGE, speed);
		double rotation = ori;
		this.initX = x;
		this.initY = y;
		this.angle = Math.toRadians(ori);
		this.speed = speed;
		this.alt = alt;
		this.period = period;
		this.range += ForsakenChampion.WHIP_STALL;
		this.dmg = ForsakenChampion.WHIP_DMG;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));	
		this.img = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_SHOTS.get(0), rotation);
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
			Controller.chr.damage("Forsaken Champion", this.dmg, true);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		int t = (ForsakenChampion.WHIP_RANGE + ForsakenChampion.WHIP_STALL - range);
		double sint = ForsakenChampion.WHIP_AMP*Math.sin(2*Math.PI*t/period);
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		if(alt < 0) {
			if(t < ForsakenChampion.WHIP_RANGE) {
				this.x = speed*(t*cosa-sint*sina) + initX;
				this.y = speed*(t*sina+sint*cosa) + initY;
			}
			else {
				this.x = speed*(ForsakenChampion.WHIP_RANGE*cosa-sint*sina) + initX;
				this.y = speed*(ForsakenChampion.WHIP_RANGE*sina+sint*cosa) + initY;
			}
		}
		else {
			if(t < ForsakenChampion.WHIP_RANGE) {
				this.x = speed*(t*cosa+sint*sina) + initX;
				this.y = speed*(t*sina-sint*cosa) + initY;
			}
			else {
				this.x = speed*(ForsakenChampion.WHIP_RANGE*cosa+sint*sina) + initX;
				this.y = speed*(ForsakenChampion.WHIP_RANGE*sina-sint*cosa) + initY;
			}
		}
		range--;
		
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
		
	}
}
