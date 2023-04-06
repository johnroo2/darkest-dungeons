package enemies.volatilevolcano.firesprite;

import java.awt.image.BufferedImage;

import control.Controller;
import project.Target;

import project.Enemy;
import project.LootRoll;

public class FireSprite extends Enemy{
	final static int HEALTH = 180;
	final static int DEF = 0;
	final static int SIZE = 28;
	final static double SPEED = 2;
	final static int SHOT_RANGE = 50;
	final static int SHOT_SPEED = 3;
	final static int DMG = 50;
	final static int MIN_FIRE_RATE = 150;
	final static int MAX_FIRE_RATE = 200;
	final static int XP = 30;
	
	final static int ANIMATE_INTERVAL = 40;
	final static int MIN_MOVE_INTERVAL = 30;
	final static int MAX_MOVE_INTERVAL = 90;
	final static int MOVE_TIME = 400;
	
	final static int MIN_TRAVEL_RADIUS = 150;
	final static int MAX_TRAVEL_RADIUS = 400;
	
	final int ATTRACT_RADIUS = 500;
	final int WANDER_LIMIT = 800;
	
	int fireTicks;
	int animate;
	int moveCycle;
	int moveTicker;
	int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	int initX, initY;
	
	public FireSprite(int x, int y) {
		super(x, y, HEALTH, DEF, "Fire Sprite");
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.initX = x;
		this.initY = y;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w3", 3, 28), lr("w4", 4, 28)},
			{lr("ab1", 3, 28), lr("ab2", 4, 28)},
			{lr("ar3", 3, 28), lr("ar4", 4, 28)},
			{lr("r1", 5, 28)}
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
				for(int i = 0; i < 8; i++) {
					new FireSpriteShot((int)this.x, (int)this.y, 45 * i);
				}
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
					return Target.IMGS_FIRESPRITE.get(i - 1);
				}
				else {
					return Target.IMGS_FIRESPRITE.get(i + 3);	
				}
			}
		}
		return Target.IMGS_FIRESPRITE.get(0);
	}
}
