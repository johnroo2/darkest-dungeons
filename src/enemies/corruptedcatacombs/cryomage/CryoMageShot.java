package enemies.corruptedcatacombs.cryomage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySlow;

public class CryoMageShot extends Projectile{
	final int SIZE = 32;
	int size, dmg, initX, initY, alt;
	double angle;
	BufferedImage img;

	public CryoMageShot(int x, int y, double ori) {
		super(x, y, ori, CryoMage.SHOT_RANGE, CryoMage.SHOT_SPEED);
		this.initX = x;
		this.initY = y;
		this.dmg = CryoMage.DMG;
		this.angle = ori;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation + 45));
		this.img = Rotate.rotateImage(Projectile.IMGS_CRYOMAGE_SHOTS, rotation + 45);
		Controller.enemyShots.add(this);
		this.angle = Math.toRadians(angle);
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
			Controller.chr.damage("Cryo Mage", this.dmg, true);
			new AllySlow(125);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		int t = (CryoMage.SHOT_RANGE - range);
		double sint = CryoMage.SHOT_AMP*Math.sin(2*Math.PI*t/CryoMage.SHOT_PERIOD);
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		this.x = CryoMage.SHOT_SPEED*(t*cosa-sint*sina) + initX;
		this.y = CryoMage.SHOT_SPEED*(t*sina+sint*cosa) + initY;
		range--;
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
