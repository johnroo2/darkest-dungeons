package enemies.volatilevolcano.daimanbrute;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class DaimanBrute extends Enemy{
	final static int HEALTH = 700;
	final static int DEF = 12;
	final static int SIZE = 38;
	final static double SPEED = 1.7;
	final static int SHOT_RANGE = 150;
	final static double SHOT_SPEED = 2;
	final static int DMG = 80;
	final static int MIN_FIRE_RATE = 180;
	final static int MAX_FIRE_RATE = 280;
	
	final static int ANIMATE_INTERVAL = 25;
	final static int MIN_MOVE_INTERVAL = 60;
	final static int MAX_MOVE_INTERVAL = 100;

	final int CHASE_LIMIT = 600;
	final int ATTRACT_RADIUS = 400;
	
	private int fireTicks, animate, moveCycle, initX, initY;
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public DaimanBrute(int x, int y) {
		super(x, y, HEALTH, DEF, "Daiman Brute");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = 50;
		
		this.table = new LootRoll[][] 
				{
			{lr("w4", 4, 35), lr("w5", 3, 35)},
			{lr("ab2", 3, 35), lr("ab3", 4, 35)},
			{lr("ar4", 3, 35), lr("ar5", 4, 35)},
			{lr("r1", 5, 35), lr("r2", 2, 35)},
			{lr(Armour.Lifeforge_Chainmail, 1, 11)}
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
					int shiftX = Controller.random.nextInt(-200, 201);
					int shiftY = Controller.random.nextInt(-200, 201);
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
						int shiftX = Controller.random.nextInt(-200, 201);
						int shiftY = Controller.random.nextInt(-200, 201);
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
						for(int i = -1; i < 2; i++) {
							new DaimanBruteShot((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY(), 28 * i);
						}
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
				if(this.dir == 1) {
					return Target.IMGS_DAIMANBRUTE.get(i - 1);
				}
				else {
					return Target.IMGS_DAIMANBRUTE.get(i + 1);	
				}
			}
		}
		return Target.IMGS_DAIMANBRUTE.get(0);
	}
}
