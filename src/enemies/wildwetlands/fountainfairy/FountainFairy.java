package enemies.wildwetlands.fountainfairy;

import java.awt.image.BufferedImage;

import control.Controller;
import enemies.wildwetlands.fountainspirit.FountainSpiritShot;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class FountainFairy extends Enemy{
	final static int HEALTH = 150;
	final static int DEF = 2;
	final static int SIZE = 32;
	final static double SPEED = 1.5;
	final static int SHOT_RANGE = 150;
	final static double SHOT_SPEED = 1.5;
	final static int ACCEL_DELAY = 50;
	final static double ACCEL = 0.04;
	final static int DMG = 25;
	final static int FIRE_RATE = 25;
	final static int XP = 20;

	final static int MOVE_TIME = 140;
	
	final static int MIN_TRAVEL_RADIUS = 50;
	final static int MAX_TRAVEL_RADIUS = 100;
	
	final static double BOB_SPEED = Controller.random.nextInt(45, 65)/10.0;
	final static int BOB_AMP = 12;
	
	final int ATTRACT_RADIUS = 500;
	
	int fireTicks;
	int shotTurn = Controller.random.nextInt(0, 360);
	int moveTicker;
	double bob = Controller.random.nextInt(0, 360);
	int bobHeight;
	int initX, initY;
	
	public FountainFairy(int x, int y) {
		super(x, y, HEALTH, DEF, "Fountain Fairy");
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = FIRE_RATE;
		this.initX = x;
		this.initY = y;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
				{lr("w2", 4, 12), lr("w3", 1, 12)},
				{lr("ab1", 4, 50), lr("ab2", 1, 50)},
				{lr("ar2", 4, 50), lr("ar3", 2, 50)},
				{lr("r0", 4, 50)}
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
				new FountainFairyShot((int)this.x, (int)this.y, shotTurn);
				this.shotTurn += 40;
				this.shotTurn = this.shotTurn % 360;
				fireTicks = FIRE_RATE;
			}
			else {
				this.fireTicks--;
			}
			if(this.moving) {
				this.moveTicker++;
				if(!(Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= SPEED * 3)
						&& this.moveTicker < MOVE_TIME) {
					this.adjustBob();
					approach();
				}
				else {
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
	
	public void adjustBob() {
		this.x -= this.bobHeight;
		this.bob += BOB_SPEED;
		this.bob = this.bob % 360;
		this.bobHeight = (int)(BOB_AMP * Math.sin(Math.toRadians(this.bob)));
		this.x += this.bobHeight;
		if(this.inWall()) {
			this.x -= this.bobHeight;
			this.bobHeight = 0;
		}
	}
	
	public int[] pickCircle() {
		int[] out = new int[2];
		double angle = Controller.random.nextInt(0, 360);
		int radius = Controller.random.nextInt(MIN_TRAVEL_RADIUS, MAX_TRAVEL_RADIUS+1);
		out[0] = (int) (this.initX + Math.cos(angle) * radius);
		out[1] = (int) (this.initY + Math.sin(angle) * radius);
		return out;
	}

	@Override
	public BufferedImage getImage() {
		if(this.dir == 0) {
			return Target.IMGS_FOUNTAINFAIRY.get(1);
		}
		else {
			return Target.IMGS_FOUNTAINFAIRY.get(0);
		}
	}
}
