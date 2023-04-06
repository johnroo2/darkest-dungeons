package enemies.corruptedcatacombs.lifecaster;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyCurse;

public class LifecasterShot extends Projectile{
	final int SIZE = 32;
	int size, dmg, initX, initY, alt;
	double angle;
	BufferedImage img;

	public LifecasterShot(int x, int y, double ori, int alt) {
		super(x, y, ori, Lifecaster.SHOT_RANGE, Lifecaster.SHOT_SPEED);
		//System.out.println(ori);
		this.initX = x;
		this.initY = y;
		this.dmg = Lifecaster.DMG;
		this.angle = ori;
		double rotation = ori;
		this.alt = alt;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation + 45));
		this.img = Rotate.rotateImage(Projectile.IMGS_LIFECASTER_SHOTS, rotation + 45);
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
			Controller.chr.damage("Lifecaster", this.dmg);
			new AllyCurse(333);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		int t = (Lifecaster.SHOT_RANGE - range);
		double sint = Lifecaster.SHOT_AMP*Math.sin(2*Math.PI*t/Lifecaster.SHOT_PERIOD);
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		if(this.alt == 0) {
			this.x = Lifecaster.SHOT_SPEED*(t*cosa-sint*sina) + initX;
			this.y = Lifecaster.SHOT_SPEED*(t*sina+sint*cosa) + initY;
		}
		else {
			this.x = Lifecaster.SHOT_SPEED*(t*cosa+sint*sina) + initX;
			this.y = Lifecaster.SHOT_SPEED*(t*sina-sint*cosa) + initY;
		}
		range--;
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
