package enemies.corruptedcatacombs.cryoadept;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyCurse;

public class CryoAdeptShot extends Projectile{
	final int SIZE = 32;
	
	int size, dmg;
	double rotate;
	double speed;
	double angle;
	int initX;
	int initY;
	int lifetime;
	int alt;
	
	ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();

	public CryoAdeptShot(int x, int y, double ori, int alt) {
		super(x, y, (double)ori, CryoAdept.SHOT_RANGE, CryoAdept.SHOT_SPEED);
		this.dmg = CryoAdept.DMG;
		double rotation = ori;
		this.rotate = 0;
		this.angle = ori;
		this.initX = x;
		this.initY = y;
		this.alt = alt;
		this.lifetime = range;
		this.speed = CryoAdept.SHOT_SPEED;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));	
		if(this.alt == 1) {
			for(int i = 45; i <= 135; i+=5) {
				this.imgs.add(Rotate.rotateImage(Projectile.IMGS_CRYOADEPT_SHOTS, rotation+i));
			}
		}
		else {
			for(int i = 45; i >= -45; i-=5) {
				this.imgs.add(Rotate.rotateImage(Projectile.IMGS_CRYOADEPT_SHOTS, rotation+i));
			}
		}
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
			Controller.chr.damage("Cryo Adept", this.dmg);
			new AllyCurse(167);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		int t = this.lifetime - range;
		this.x = this.initX + t * this.speed * Math.cos(Math.toRadians(rotate + angle));
		this.y = this.initY + t * this.speed * Math.sin(Math.toRadians(rotate + angle));;
		this.rotate += CryoAdept.SHOT_ROTVEL * this.alt;
		this.size = (int) (SIZE * Rotate.getSizeConstant(angle+45+rotate));		
		this.range--;	
	}
	
	public BufferedImage getImage() {
		if(this.alt == 1) {
			for(int i = 1; i < 20; i++) {
				if(rotate <= i * 5) {
					return this.imgs.get(i);
				}
			}
		}
		else {
			for(int i = 1; i < 20; i++) {
				if(-rotate <= i * 5) {
					return this.imgs.get(i);
				}
			}
		}
		return this.imgs.get(0);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
