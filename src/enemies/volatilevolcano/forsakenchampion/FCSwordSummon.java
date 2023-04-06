package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.AoE;
import project.Area;
import project.Projectile;
import project.Rotate;

public class FCSwordSummon extends AoE{
	
	public FCSwordSummon(int x, int y) {
		super(x, y, 60, AoE.SWORDSUMMON, 120);
		Controller.enemyAoE.add(this);
	}
	
	@Override
	public void destroy() {
		new FCInSword((int)this.x, (int)this.y);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 0; i < 20; i ++) {
			if(this.range >= 114 - 6 * i) {
				return this.colour.get(i);
			}
		}
		return this.colour.get(0);
	}
	
	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.radius/2 - shiftX + 400, (int)this.y-this.radius-this.radius/2 - shiftY + 400, radius,
				2*radius+(int)(radius * 5.0/37.0), i);
		
	}
}

class FCInSword extends Projectile {
	final static int SIZE = 48;
	final static int ANIMATE_FRAMES = 20;
	
	int size;
	int turn;
	
	public FCInSword(int x, int y) {
		super(x, y, (double)0, ForsakenChampion.SWORD_LIFETIME, 0);
		this.size = SIZE;
		Controller.enemyShots.add(this);
		this.turn = Controller.random.nextInt(0, 60);
	}

	@Override
	public boolean ranged() {
		if(range <= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public void act() {
		for(int i = 0; i < 6; i++) {
			new FCSwordShot((int)this.x, (int)this.y, 60 * i + this.turn);
		}
		this.turn += Controller.random.nextInt(20, 41);
		this.turn = this.turn % 360;
	}

	@Override
	public void move() {
		if(this.range % ForsakenChampion.SWORD_FIRE == 0) {
			Controller.act(this);
			
		}
		this.range--;
		
	}
	
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(this.range % (4*ANIMATE_FRAMES) < i * ANIMATE_FRAMES) {
				return Projectile.IMGS_FORSAKENCHAMPION_INSWORD.get(i-1);
			}
		}
		return Projectile.IMGS_FORSAKENCHAMPION_INSWORD.get(0);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);		
	}	
}


class FCSwordShot extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	BufferedImage img;

	public FCSwordShot(int x, int y, double ori) {
		super(x, y, (double)ori, ForsakenChampion.SWORD_RANGE, ForsakenChampion.SWORD_SPEED);
		this.dmg = ForsakenChampion.SWORD_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_INSWORD_SHOTS, rotation+45);
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
				Controller.chr.damage("Forsaken Champion Sword", this.dmg);
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