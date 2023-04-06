package enemies.corruptedcatacombs.icesprite;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class IceSprite extends Enemy{
	final static int HEALTH = 300;
	final static int DEF = 0;
	final static int SIZE = 32;
	final static double SPEED = 3.5;
	final static int SHOT_RANGE = 200;
	final static double SHOT_SPEED = 1.25;
	final static int DMG = 50;
	final static int MIN_FIRE_RATE = 75;
	final static int MAX_FIRE_RATE = 85;
	final static int XP = 40;
	
	final static int ANIMATE_INTERVAL = 45;
	final static int MIN_MOVE_INTERVAL = 80;
	final static int MAX_MOVE_INTERVAL = 120;
	final static int MOVE_TIME = 75;
	
	final static int MIN_TRAVEL_RADIUS = 100;
	final static int MAX_TRAVEL_RADIUS = 200;
	
	final int ATTRACT_RADIUS = 500;
	final int WANDER_LIMIT = 400;
	
	int fireTicks;
	int animate;
	int moveCycle;
	int moveTicker;
	int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	int initX, initY;
	
	public IceSprite(int x, int y) {
		super(x, y, HEALTH, DEF, "Ice Sprite");
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.initX = x;
		this.initY = y;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w5", 2, 40), lr("w6", 4, 40)},
			{lr("ab3", 5, 40)},
			{lr("ar5", 2, 40), lr("ar6", 4, 40)},
			{lr("r2", 4, 40)},
				};
	}

	@Override
	public boolean inWall() {
		return super.inWall(0);
	}

	@Override
	public void move() {
		if(this.active) {
			if(this.fireTicks <= 0) {
				double angle = this.getAngle((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY());
				new IceSpriteShot((int)this.x, (int)this.y, angle + Controller.random.nextInt(-30, 31));
				this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
			}
			else {
				this.fireTicks--;
			}
			if(this.moving) {
				this.moveTicker++;
				this.animate++;
				if(this.animate == 4 * ANIMATE_INTERVAL) {
					animate = 0;
				}
				if(!(Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= SPEED * 3)
						&& this.moveTicker < MOVE_TIME) {
					approach();
				}
				else {
					this.moving = false;
					moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
					this.moveCycle = moveInterval;
				}
			}
			else {
				if(this.moveCycle == 0) { //start movement
					int[] spot = this.pickCircle();
					this.go(spot[0], spot[1], SPEED);
					this.moveTicker = 0;
					if(this.xVel < 0) {
						this.dir = 0;
					}
					else {
						this.dir = 1;
					}
				}
				else {
					this.moveCycle -= 1;
				}
			}
		}
		else {
			if(Controller.chr.getArea() == this.area ||  this.currentHealth < this.maxHealth ||
					(Controller.chr.getX() > this.x - this.ATTRACT_RADIUS && Controller.chr.getX() < this.x + this.ATTRACT_RADIUS
							&& Controller.chr.getY() > this.y - this.ATTRACT_RADIUS && Controller.chr.getY() < this.y + this.ATTRACT_RADIUS)) {
				this.active = true;
				int[] spot = this.pickCircle();
				this.go(spot[0], spot[1], SPEED);
				if(this.xVel < 0) {
					this.dir = 0;
				}
				else {
					this.dir = 1;
				}
			}
		}
	}
	
	public int[] pickCircle() {
		int[] out = new int[2];
		double angle;
		int iter = 0;
		do {
			angle = Controller.random.nextInt(0, 360);
			int radius = Controller.random.nextInt(MIN_TRAVEL_RADIUS, MAX_TRAVEL_RADIUS+1);
			out[0] = (int) (this.x + Math.cos(angle) * radius);
			out[1] = (int) (this.y + Math.sin(angle) * radius);
			iter++;
			if(iter > 5) {
				out[0] = this.initX;
				out[1] = this.initY;
				break;
			}
		}
		while(!(out[0] > this.initX - this.WANDER_LIMIT && out[0] < this.initX + this.WANDER_LIMIT
							&& out[1] > this.initY - this.WANDER_LIMIT && out[1] < this.initY + this.WANDER_LIMIT));
		return out;
		
	}

	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_ICESPRITE.get(i - 1);
				}
				else {
					return Target.IMGS_ICESPRITE.get(i + 3);	
				}
			}
		}
		return Target.IMGS_ICESPRITE.get(0);
	}
}
