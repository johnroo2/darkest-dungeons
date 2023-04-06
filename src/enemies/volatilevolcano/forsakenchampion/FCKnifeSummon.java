package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import project.AoE;
import project.Area;
import project.Projectile;
import project.Rotate;

public class FCKnifeSummon extends AoE{
	
	public FCKnifeSummon(int x, int y) {
		super(x, y, 60, AoE.KNIFESUMMON, 120);
		Controller.enemyAoE.add(this);
	}
	
	@Override
	public void destroy() {
		new FCInKnife((int)this.x, (int)this.y);
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

class FCInKnife extends Projectile{
	final static int SIZE = 48;
	final static int ANIMATE_FRAMES = 20;
	
	int size;
	int turn;
	
	public FCInKnife(int x, int y) {
		super(x, y, (double)0, ForsakenChampion.KNIFE_LIFETIME, 0);
		this.size = SIZE;
		Controller.enemyShots.add(this);
		if(Controller.coinflip()) {
			this.turn = 0;
		}
		else {
			this.turn = 45;
		}
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
		for(int i = 0; i < 4; i++) {
			new FCKnifeShot((int)this.x, (int)this.y, 90 * i + this.turn);
		}
		this.turn += 45;
		this.turn = this.turn % 360;
	}

	@Override
	public void move() {
		if(this.range % ForsakenChampion.KNIFE_FIRE == 0) {
			Controller.act(this);
			
		}
		this.range--;
		
	}
	
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(this.range % (4*ANIMATE_FRAMES) < i * ANIMATE_FRAMES) {
				return Projectile.IMGS_FORSAKENCHAMPION_INKNIFE.get(i-1);
			}
		}
		return Projectile.IMGS_FORSAKENCHAMPION_INKNIFE.get(0);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);		
	}	
}

class FCKnifeShot extends Projectile{
	final int SIZE = 28;
	
	int size, dmg;
	double rotate;
	double speed;
	double angle;
	int initX;
	int initY;
	int lifetime;
	
	ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();

	public FCKnifeShot(int x, int y, double ori) {
		super(x, y, (double)ori, ForsakenChampion.KNIFE_RANGE, ForsakenChampion.KNIFE_SPEED);
		this.dmg = ForsakenChampion.KNIFE_DMG;
		double rotation = ori;
		this.rotate = 0;
		this.angle = ori;
		this.initX = x;
		this.initY = y;
		this.lifetime = range;
		this.speed = ForsakenChampion.KNIFE_SPEED;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		for(int i = 45; i <= 135; i+=5) {
			this.imgs.add(Rotate.rotateImage(Projectile.IMGS_FORSAKENCHAMPION_INKNIFE_SHOTS, rotation+i));
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
		for(Area a: Area.list) {
			if(Controller.chr.collision(this)) {
				Controller.chr.damage("Forsaken Champion Knife", this.dmg);
				return true;
			}
		}
		return false;
	}

	@Override
	public void move() {
		int t = this.lifetime - range;
		this.x = this.initX + t * this.speed * Math.cos(Math.toRadians(rotate + angle));
		this.y = this.initY + t * this.speed * Math.sin(Math.toRadians(rotate + angle));;
		this.rotate += ForsakenChampion.KNIFE_ROTVEL;
		this.size = (int) (SIZE * Rotate.getSizeConstant(angle+45+rotate));		
		this.range--;	
	}
	
	public BufferedImage getImage() {
		for(int i = 1; i < 20; i++) {
			if(rotate <= i * 5) {
				return this.imgs.get(i);
			}
		}
		return this.imgs.get(0);
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
