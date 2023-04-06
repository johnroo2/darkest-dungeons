package enemies.corruptedcatacombs.cryowarlock;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySlow;

public class CryoWarlockPortal extends Projectile{
	final static int SIZE = 60;
	
	final static double BLUE_SPEED = 2.8;
	final static int BLUE_RANGE = 125;
	final static int BLUE_DMG = 120;
	
	final static double PURPLE_SPEED = 2.5;
	final static int PURPLE_RANGE = 175;
	final static int PURPLE_DMG = 80;

	int size;
	int shotTurn;
	ArrayList<BufferedImage> imgs = new ArrayList<>();

	public CryoWarlockPortal(int x, int y) {
		super(x, y, Controller.chr.getX(), Controller.chr.getY(), CryoWarlock.PORTAL_LIFETIME, CryoWarlock.PORTAL_SPEED);
		this.size = SIZE;
		this.imgs = Projectile.IMGS_CRYOWARLOCK_PORTAL;
		Controller.enemyShots.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(this.inWall()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void act() {
		for(int i = 0; i < 2; i++) {
			new CryoWarlockPortalShotBlue((int)this.x, (int)this.y, 180 * i + shotTurn);
			new CryoWarlockPortalShotPurple((int)this.x, (int)this.y, 90 + 180 * i + shotTurn);
		}
		shotTurn += Controller.random.nextInt(28, 35);
		shotTurn = shotTurn % 360;
	}

	@Override
	public void move() {
		this.x += this.xVel;
		this.y += this.yVel;
		if(this.range % CryoWarlock.PORTAL_RATE == 0) {
			Controller.addList.add(this);
		}
		if(this.range % 50 == 0) {
			if(x == Controller.chr.getX()) {
				this.xVel = 0.0;
				if(y > Controller.chr.getY()) {
					this.yVel = -CryoWarlock.PORTAL_SPEED;
				}
				else {
					this.yVel = CryoWarlock.PORTAL_SPEED;
				}
			}
			else {
				double slope = (double)(Controller.chr.getY()-y)/(double)(Controller.chr.getX()-x);
				if(x > Controller.chr.getX()) {
					this.xVel = -(CryoWarlock.PORTAL_SPEED)/Math.sqrt(slope*slope+1);
				}
				else {
					this.xVel = (CryoWarlock.PORTAL_SPEED)/Math.sqrt(slope*slope+1);
				}
				if(y > Controller.chr.getY()) {
					this.yVel = -(CryoWarlock.PORTAL_SPEED)*Math.sqrt((slope*slope)/(slope*slope+1));
				}
				else {
					this.yVel = (CryoWarlock.PORTAL_SPEED)*Math.sqrt((slope*slope)/(slope*slope+1));
				}
			}
		}
		range--;	
	}
	
	public BufferedImage getImage() {
		return this.imgs.get((this.range/10) % 3);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}

class CryoWarlockPortalShotBlue extends Projectile{
	final int SIZE = 28;
	
	int size, dmg;
	BufferedImage img;

	public CryoWarlockPortalShotBlue(int x, int y, double ori) {
		super(x, y, (double)ori, CryoWarlockPortal.BLUE_RANGE, CryoWarlockPortal.BLUE_SPEED);
		this.dmg = CryoWarlockPortal.BLUE_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_CRYOWARLOCK_PORTALSHOTS.get(0), rotation+45);
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
			Controller.chr.damage("Cryo Warlock Portal", this.dmg);
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

class CryoWarlockPortalShotPurple extends Projectile{
final int SIZE = 28;
	
	int size, dmg;
	BufferedImage img;

	public CryoWarlockPortalShotPurple(int x, int y, double ori) {
		super(x, y, (double)ori, CryoWarlockPortal.PURPLE_RANGE, CryoWarlockPortal.PURPLE_SPEED);
		this.dmg = CryoWarlockPortal.PURPLE_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_CRYOWARLOCK_PORTALSHOTS.get(1), rotation+45);
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
			Controller.chr.damage("Cryo Warlock Portal", this.dmg, true);
			new AllySlow(167);
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
