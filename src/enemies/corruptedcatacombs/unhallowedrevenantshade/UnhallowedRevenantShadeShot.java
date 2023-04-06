package enemies.corruptedcatacombs.unhallowedrevenantshade;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import project.Projectile;
import statuseffects.AllyArmourBreak;

public class UnhallowedRevenantShadeShot extends Projectile{
	final int SIZE = 35;
	int size, dmg;
	ArrayList<BufferedImage> imgs;

	public UnhallowedRevenantShadeShot(int x, int y, int targetX, int targetY) {
		super(x, y, targetX, targetY, UnhallowedRevenantShade.SHOT_RANGE, UnhallowedRevenantShade.SHOT_SPEED);
		this.dmg = UnhallowedRevenantShade.DMG;
		
		this.size = SIZE;
		this.imgs = Projectile.IMGS_UNHALLOWEDREVENANTSHADE_SHOTS;
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
			Controller.chr.damage("Unhallowed Revenant", this.dmg);
			new AllyArmourBreak(400);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		this.range--;	
		this.x += this.xVel;
		this.y += this.yVel;
	}
	
	public BufferedImage getImage() {
		return this.imgs.get((this.range/2) % 9);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
