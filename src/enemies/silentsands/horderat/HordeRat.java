package enemies.silentsands.horderat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import control.Controller;
import project.Area;
import project.Enemy;
import project.LootBag;
import project.LootRoll;
import project.Projectile;
import project.Target;

public class HordeRat extends Enemy{
	final static int HEALTH = 90;
	final static int DEF = 0;
	final static int SIZE = 32;
	final static double SPEED = 1.8;
	final static int SHOT_RANGE = 25;
	final static double SHOT_SPEED = 4.5;
	final static int DMG = 14;
	final static int MIN_FIRE_RATE = 20;
	final static int MAX_FIRE_RATE = 100;
	
	final static int ANIMATE_INTERVAL = 20;
	final static int MIN_MOVE_INTERVAL = 30;
	final static int MAX_MOVE_INTERVAL = 50;
	final static int XP = 8;

	final int CHASE_LIMIT = 1500;
	final int ATTRACT_RADIUS = 400;
	
	private int fireTicks, animate, moveCycle, initX, initY;
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public HordeRat(int x, int y) {
		super(x, y, HEALTH, DEF, "Horde Rat");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
				{lr("w1", 4, 80), lr("w2", 1, 80)},
				{lr("ab1", 5, 80)},
				{lr("ar1", 5, 80), lr("ar2", 5, 80)},
				{lr("r0", 3, 80)}
				};
	}
	
	public static void newHorde(int x, int y, int width, int height, int rats) {
		for(int i = 0; i < rats; i++) {
			new HordeRat(Controller.random.nextInt(x, x+width+1), Controller.random.nextInt(y, y+height+1));
		}
	}

	@Override
	public boolean inWall() {
		return super.inWall(0);
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
					int shiftX = Controller.random.nextInt(-100, 101);
					int shiftY = Controller.random.nextInt(-100, 101);
					this.go(Controller.chr.getX() + shiftX, Controller.chr.getY() + shiftY, SPEED);
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
						int shiftX = Controller.random.nextInt(-100, 101);
						int shiftY = Controller.random.nextInt(-100, 101);
						this.go(Controller.chr.getX() + shiftX, Controller.chr.getY() + shiftY, SPEED);
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
				if(this.x < this.initX - this.CHASE_LIMIT || this.x > this.initX + this.CHASE_LIMIT
								|| this.y < this.initY - this.CHASE_LIMIT || this.y > this.initY + this.CHASE_LIMIT) {
					this.moving = false;
					this.go(initX, initY, SPEED);
				}
				else {
					if(fireTicks <= 0){
						int offset = Controller.random.nextInt(-40, 41);
						new HordeRatShot((int)this.x, (int)this.y, (int)Controller.chr.getX(), (int)Controller.chr.getY(), offset);
						this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
					}
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
					return Target.IMGS_HORDERAT.get(i - 1);
				}
				else {
					return Target.IMGS_HORDERAT.get(i + 1);	
				}
			}
		}
		return Target.IMGS_HORDERAT.get(0);
	}
}
