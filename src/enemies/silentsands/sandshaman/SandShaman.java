package enemies.silentsands.sandshaman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import control.Controller;
import equipment.ring.Ring;
import project.Area;
import project.Enemy;
import project.Equipment;
import project.LootBag;
import project.LootRoll;
import project.Projectile;
import project.Target;
import project.Tile;
import statuseffects.EnemyShield;

public class SandShaman extends Enemy{
	final static int HEALTH = 450;
	final static int DEF = 2;
	final static int SIZE = 40;
	final static double SPEED = 0.8;
	final static int DMG = 25;
	final static int MIN_FIRE_RATE = 150;
	final static int MAX_FIRE_RATE = 225;
	final static int XP = 30;
	
	final int WANDER_RADIUS = 40;
	final int ATTRACT_RADIUS = 400;
	final int ANIMATE_INTERVAL = 50;
	final int CAST_ANIMATE_INTERVAL = 20;
	final int CAST_DELAY = 35;
	final int CAST_MAX = 8;
	
	private int animate;
	private boolean casting = false;
	private int castTicks = CAST_DELAY/3;
	private int castDist = 1;
	private int alt = 2*Controller.random.nextInt(0, 2)-1;
	private boolean[] actives = new boolean[] {true, true, true, true};
	
	double[] positions = new double[18];
	int fireTicks;
	
	public SandShaman(int x, int y) {
		super(x, y, HEALTH, DEF, "Sand Shaman");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1)/3;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
				{lr("w1", 3, 20), lr("w2", 4, 20)},
				{lr("ab1", 5, 20)},
				{lr("ar1", 1, 20), lr("ar2", 2, 20), lr("ar3", 3, 20)},
				{lr("r0", 4, 20)},
				{lr(Ring.Bone_Ring, 1, 10)}			
				};
	}

	@Override
	public void move() {
		if(this.active) {
			this.animate++;
			if(this.casting) {
				if(this.animate == 4 * CAST_ANIMATE_INTERVAL) {
					this.animate = 0;
				}
				if(this.alt == 1) {
					if(this.castTicks == 0) {
						if(this.actives[0]) {
							new Sandbeam((int)this.x, (int)this.y - 50 * castDist);
						}
						if(this.actives[1]) {
							new Sandbeam((int)this.x - 50 * castDist, (int)this.y);
						}
						if(this.actives[2]) {
							new Sandbeam((int)this.x, (int)this.y + 50 * castDist);
						}
						if(this.actives[3]) {
							new Sandbeam((int)this.x + 50 * castDist, (int)this.y);
						}
						this.castTicks = CAST_DELAY;
						castDist++;
					}
					else {
						this.castTicks--;
					}
					if(posInWall((int)this.x, (int)this.y - 50 * castDist, 20)) {
						this.actives[0] = false;
					}
					if(posInWall((int)this.x - 50 * castDist, (int)this.y, 20)) {
						this.actives[1] = false;
					}
					if(posInWall((int)this.x, (int)this.y + 50 * castDist, 20)) {
						this.actives[2] = false;
					}
					if(posInWall((int)this.x + 50 * castDist, (int)this.y, 20)) {
						this.actives[3] = false;
					}
				}
				else {
					if(this.castTicks == 0) {
						if(this.actives[0]) {
							new Sandbeam((int)this.x + 35 * castDist, (int)this.y + 35 * castDist);
						}
						if(this.actives[1]) {
							new Sandbeam((int)this.x - 35 * castDist, (int)this.y + 35 * castDist);
						}
						if(this.actives[2]) {
							new Sandbeam((int)this.x - 35 * castDist, (int)this.y - 35 * castDist);
						}
						if(this.actives[3]) {
							new Sandbeam((int)this.x + 35 * castDist, (int)this.y - 35 * castDist);
						}
						this.castTicks = CAST_DELAY;
						castDist++;
					}
					else {
						this.castTicks--;
					}
					if(posInWall((int)this.x + 35 * castDist, (int)this.y + 35 * castDist, 20)) {
						this.actives[0] = false;
					}
					if(posInWall((int)this.x - 35 * castDist, (int)this.y + 35 * castDist, 20)) {
						this.actives[1] = false;
					}
					if(posInWall((int)this.x - 35 * castDist, (int)this.y - 35 * castDist, 20)) {
						this.actives[2] = false;
					}
					if(posInWall((int)this.x + 35 * castDist, (int)this.y - 35 * castDist, 20)) {
						this.actives[3] = false;
					}
				}
				
				if(castDist > CAST_MAX || !(this.actives[0] || this.actives[1] || this.actives[2] || this.actives[3])) {
					this.animate = 0;
					this.casting = false;
				}
			}
			else {
				if(this.animate == 2 * ANIMATE_INTERVAL) {
					this.animate = 0;
				}
				if(this.moving) {
					if(this.fireTicks > 0) {
						this.fireTicks -= 1;
					}
					else {
						this.casting = true;
						this.castDist = 1;
						this.castTicks = CAST_DELAY/3;
						this.actives = new boolean[] {true, true, true, true};
						this.alt *= -1;
						this.animate = 0;
						this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
					}
					
					wander(this.positions, SPEED);
				}
			}
		}
		else {
			if(Controller.chr.getArea() == this.area || this.currentHealth < this.maxHealth ||
					(Controller.chr.getX() > this.x - this.ATTRACT_RADIUS && Controller.chr.getX() < this.x + this.ATTRACT_RADIUS
							&& Controller.chr.getY() > this.y - this.ATTRACT_RADIUS && Controller.chr.getY() < this.y + this.ATTRACT_RADIUS)) {
				this.active = true;
				int space = Controller.random.nextInt(0, 9);
				this.go((int)this.positions[2 * space], (int)this.positions[2 * space + 1], SPEED);
				if(this.xVel < 0) {
					this.dir = 0;
				}
				else {
					this.dir = 1;
				}
			}
		}
	}
	
	public boolean posInWall(int x, int y, int margin) {
		for(Area a: Area.list) {
			Tile t1 = a.get((int)((x+margin/2-a.getX())/Area.BLOCK_SIZE),
					(int)((y+margin/2-a.getY())/Area.BLOCK_SIZE));
			Tile t2 = a.get((int)((x-margin/2-a.getX())/Area.BLOCK_SIZE),
					(int)((y+margin/2-a.getY())/Area.BLOCK_SIZE));
			Tile t3 = a.get((int)((x+margin/2-a.getX())/Area.BLOCK_SIZE),
					(int)((y-margin/2-a.getY())/Area.BLOCK_SIZE));
			Tile t4 = a.get((int)((x-margin/2-a.getX())/Area.BLOCK_SIZE),
					(int)((y-margin/2-a.getY())/Area.BLOCK_SIZE));
			if(t1 != null && t1.isWall()) {
				return true;
			}
			if(t2 != null && t2.isWall()) {
				return true;
			}
			if(t3 != null && t3.isWall()) {
				return true;
			}
			if(t4 != null && t4.isWall()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean inWall() {
		return super.inWall(0);
	}

	@Override
	public BufferedImage getImage() {
		if(!this.casting) {
			for(int i = 1; i < 3; i++) {
				if(this.animate <= ANIMATE_INTERVAL * i) {
					if(this.dir == 0) {
						return Target.IMGS_SANDSHAMAN.get(i - 1);
					}
					else {
						return Target.IMGS_SANDSHAMAN.get(i + 1);	
					}
				}
			}
			return Target.IMGS_SANDSHAMAN.get(0);
		}
		else {
			for(int i = 1; i < 5; i++) {
				if(this.animate <= CAST_ANIMATE_INTERVAL * i) {
					return Target.IMGS_SANDSHAMAN.get(i + 3);
				}
			}
			return Target.IMGS_SANDSHAMAN.get(4);
		}
	}
}
