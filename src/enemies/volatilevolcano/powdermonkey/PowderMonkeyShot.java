package enemies.volatilevolcano.powdermonkey;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyDisarmed;

public class PowderMonkeyShot extends Projectile{
	final int SIZE = 28;
	int size, dmg, initX, initY;
	double angle;
	BufferedImage img;

	public PowderMonkeyShot(int x, int y, int targetX, int targetY, int alt) {
		super(x, y, targetX, targetY, PowderMonkey.SHOT_RANGE, PowderMonkey.SHOT_SPEED);
		this.initX = x;
		this.initY = y;
		this.dmg = PowderMonkey.DMG;
		angle = getAngle(x, y, targetX, targetY) + alt;
		double rotation = angle;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));
		this.img = Rotate.rotateImage(Projectile.IMGS_POWDERMONKEY_SHOTS, rotation);
		Controller.enemyShots.add(this);
		angle = Math.toRadians(angle);
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
			Controller.chr.damage("Powder Monkey", this.dmg);
			new AllyDisarmed(50); //0.3 seconds
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		int t = (PowderMonkey.SHOT_RANGE - range);
		double sint = PowderMonkey.SHOT_AMP*Math.sin(2*Math.PI*t/PowderMonkey.SHOT_PERIOD);
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		this.x = PowderMonkey.SHOT_SPEED*(t*cosa-sint*sina) + initX;
		this.y = PowderMonkey.SHOT_SPEED*(t*sina+sint*cosa) + initY;
		range--;
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
