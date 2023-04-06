package enemies.corruptedcatacombs.tenescowrifleshtracker;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import equipment.ring.Ring;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class TenescowriFleshtracker extends Enemy{
	final static int HEALTH = 750;
	final static int DEF = 0;
	final static int SIZE = 42;
	final static double SPEED = 3.2;
	final static int SHOT_RANGE = 250;
	final static int DMG = 140;
	final static int MIN_FIRE_RATE = 60;
	final static int MAX_FIRE_RATE = 80;
	
	final static int ANIMATE_INTERVAL = 20;
	final static int MIN_MOVE_INTERVAL = 50;
	final static int MAX_MOVE_INTERVAL = 120;

	final int ATTRACT_RADIUS = 500;
	 
	private int fireTicks, animate, moveCycle, initX, initY;
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public TenescowriFleshtracker(int x, int y) {
		super(x, y, HEALTH, DEF, "Tenescowri Fleshtracker");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = 50;
		
		this.table = new LootRoll[][] 
				{
			{lr("w6", 4, 70), lr("w7", 3, 70)},
			{lr("ab3", 4, 70), lr("ab4", 3, 70)},
			{lr("ar6", 4, 70), lr("ar7", 3, 70)},
			{lr("r2", 6, 70)},
			{lr(Ring.Lawless_Amulet, 1, 25)}
				};
	}

	@Override
	public boolean inWall() {
		return super.inWall(2);
	}

	@Override
	public void move() {
		if(this.active) {
			this.moveCycle++;
			if(this.moving) {
				this.animate++;
				if(this.animate == 2 * ANIMATE_INTERVAL) {
					animate = 0;
				}
				if(this.moveCycle >= this.moveInterval) {
					double angle = Math.toRadians(this.getAngle((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY()));
					int targetX = Controller.chr.getX() + (int)(200 * Math.cos(angle));
					int targetY = Controller.chr.getY() + (int)(200 * Math.sin(angle));
					this.go(targetX, targetY, SPEED);
					if(this.xVel < 0) {
						this.dir = 0;
					}
					else {
						this.dir = 1;
					}
					this.moveCycle = 0;
					this.moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
					
				}
				if(Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= SPEED * 8) {
					if(this.targetX == this.initX && this.targetY == this.initY) {
						this.active = false;
						return;
					}
					else {
						this.moving = false;
						double angle = Math.toRadians(this.getAngle((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY()));
						int targetX = Controller.chr.getX() + (int)(200 * Math.cos(angle));
						int targetY = Controller.chr.getY() + (int)(200 * Math.sin(angle));
						this.go(targetX, targetY, SPEED);
						if(this.xVel < 0) {
							this.dir = 0;
						}
						else {
							this.dir = 1;
						}
						this.moveCycle = 0;
						this.moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
					}
				}
				else {
					approach();
				}
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				else{
					new TenescowriFleshtrackerShot((int)this.x, (int)this.y);
					this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
				}
			}
			else {
				if(this.moveCycle == 0) { //start movements
					this.go(Controller.chr.getX(), Controller.chr.getY(), SPEED);
					if(this.xVel < 0) {
						this.dir = 0;
					}
					else {
						this.dir = 1;
					}
				}
			}
		}
		else {
			if(Controller.chr.getArea() == this.area || (Controller.chr.getX() > this.x - this.ATTRACT_RADIUS && Controller.chr.getX() < this.x + this.ATTRACT_RADIUS
							&& Controller.chr.getY() > this.y - this.ATTRACT_RADIUS && Controller.chr.getY() < this.y + this.ATTRACT_RADIUS)) {
				this.active = true;
				this.go(Controller.chr.getX(), Controller.chr.getY(), SPEED);
				if(this.xVel < 0) {
					this.dir = 0;
				}
				else {
					this.dir = 1;
				}
			}
		}
	}

	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 3; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_TENESCOWRIFLESHTRACKER.get(i - 1);
				}
				else {
					return Target.IMGS_TENESCOWRIFLESHTRACKER.get(i + 1);	
				}
			}
		}
		return Target.IMGS_TENESCOWRIFLESHTRACKER.get(0);
	}
}
