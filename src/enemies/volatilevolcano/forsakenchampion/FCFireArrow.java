package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Area;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyArmourBreak;

public class FCFireArrow extends Projectile{
final int SIZE = 35;
	
	int size, dmg;
	BufferedImage img;

	public FCFireArrow(int x, int y, double ori) {
		super(x, y, (double)ori, ForsakenChampion.FIREARROW_RANGE, ForsakenChampion.FIREARROW_SPEED);
		this.dmg = ForsakenChampion.FIREARROW_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_ARROW.get(1), rotation+45);
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
				Controller.chr.damage("Forsaken Champion", this.dmg);
				new AllyArmourBreak(167);
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
