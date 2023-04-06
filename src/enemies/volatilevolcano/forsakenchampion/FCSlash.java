package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Area;
import project.Projectile;
import project.Rotate;

public class FCSlash extends Projectile{
	final int SIZE = 28;
	
	int size, dmg;
	BufferedImage img;

	public FCSlash(int x, int y, double ori) {
		super(x, y, (double)ori, ForsakenChampion.SLASH_RANGE, ForsakenChampion.SLASH_SPEED);
		this.dmg = ForsakenChampion.SLASH_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_SHOTS.get(2), rotation+45);
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
		for(Area a: Area.list) {
			if(Controller.chr.collision(this)) {
				Controller.chr.damage("Forsaken Champion", this.dmg, true);
				return true;
			}
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
