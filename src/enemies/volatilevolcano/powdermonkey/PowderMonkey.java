package enemies.volatilevolcano.powdermonkey;

import java.awt.image.BufferedImage;

import control.Controller;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class PowderMonkey extends Enemy{
	final static int HEALTH = 200;
	final static int DEF = 5;
	final static int SIZE = 32;
	final static double SPEED = 2.4;
	final static int SHOT_RANGE = 50;
	final static int SHOT_PERIOD = 30;
	final static int SHOT_AMP = 5;
	final static double SHOT_SPEED = 3.5;
	final static int DMG = 40;
	final static int MIN_FIRE_RATE = 200;
	final static int MAX_FIRE_RATE = 240;
	
	final static int ANIMATE_INTERVAL = 12;
	final static int MIN_MOVE_INTERVAL = 25;
	final static int MAX_MOVE_INTERVAL = 45;
	final static int XP = 15;

	final int CHASE_LIMIT = 1500;
	final int ATTRACT_RADIUS = 400;
	
	private int fireTicks, animate, moveCycle, initX, initY;
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public PowderMonkey(int x, int y) {
		super(x, y, HEALTH, DEF, "Powder Monkey");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w3", 3, 80), lr("w4", 4, 80)},
			{lr("ab1", 3, 80), lr("ab2", 4, 80)},
			{lr("ar3", 3, 80), lr("ar4", 4, 80)},
			{lr("r1", 5, 80)}
				};
	}
	
	public static void newHorde(int x, int y, int width, int height, int monkeys) {
		for(int i = 0; i < monkeys; i++) {
			new PowderMonkey(Controller.random.nextInt(x, x+width+1), Controller.random.nextInt(y, y+height+1));
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
				if(this.animate == 5 * ANIMATE_INTERVAL) {
					animate = 0;
				}
				if(this.moveCycle >= this.moveInterval) {
					int shiftX = Controller.random.nextInt(-150, 151);
					int shiftY = Controller.random.nextInt(-150, 151);
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
						int shiftX = Controller.random.nextInt(-150, 151);
						int shiftY = Controller.random.nextInt(-150, 151);
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
					if(fireTicks == 0 || fireTicks == 40){
						int offset = Controller.random.nextInt(-20, 21);
						new PowderMonkeyShot((int)this.x, (int)this.y, (int)Controller.chr.getX(), (int)Controller.chr.getY(), offset);
						if(fireTicks == 0) {
							this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
						}
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
		for(int i = 1; i < 6; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_POWDERMONKEY.get(i - 1);
				}
				else {
					return Target.IMGS_POWDERMONKEY.get(i + 1);	
				}
			}
		}
		return Target.IMGS_POWDERMONKEY.get(0);
	}
}
