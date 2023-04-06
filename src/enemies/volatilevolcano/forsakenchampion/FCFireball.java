package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Area;
import project.Projectile;
import project.Rotate;
import statuseffects.AllyParalyze;

public class FCFireball extends Projectile{
final int SIZE = 24;
final static int ANIMATE_TICKS = 15;
	
	int size, dmg;
	int animate;
	BufferedImage img1;
	BufferedImage img2;
	BufferedImage img3;

	public FCFireball(int x, int y, double ori) {
		super(x, y, (double)ori, ForsakenChampion.FIREBALL_RANGE, ForsakenChampion.FIREBALL_SPEED);
		this.dmg = ForsakenChampion.FIREBALL_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation));		
		this.img1 = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_SHOTS.get(3), rotation);
		this.img2 = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_SHOTS.get(4), rotation);
		this.img3 = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_SHOTS.get(5), rotation);
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
				new AllyParalyze(83);
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
	
	public BufferedImage getImage() {
		if(animate >= 4 * ANIMATE_TICKS) {
			animate = 0;
		}
		else {
			animate++;
		}
		
		if(animate < ANIMATE_TICKS) {
			return this.img1;
		}
		else if(animate < 2 * ANIMATE_TICKS) {
			return this.img2;
		}
		else if(animate < 3 * ANIMATE_TICKS) {
			return this.img3;
		}
		else {
			return this.img2;
		}
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
