package enemies.corruptedcatacombs.mammothhunter;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.ability.Ability;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class MammothHunter extends Enemy{
	final static int HEALTH = 800;
	final static int DEF = 5;
	final static int SIZE = 60;
	final static double SPEED = 2.4;
	final static int SHOT_RANGE = 600;
	final static double SHOT_SPEED = 3;
	final static int DMG = 90;
	final static int MIN_FIRE_RATE = 350;
	final static int MAX_FIRE_RATE = 500;
	
	final static int CAST_RATE = 120;
	final static int CAST_RANGE = 400;
	final static int CAST_RADIUS = 90;
	final static int CAST_DMG = 70;
	
	final static int ANIMATE_INTERVAL = 35;
	final static int MIN_MOVE_INTERVAL = 120;
	final static int MAX_MOVE_INTERVAL = 150;

	final int CHASE_LIMIT = 800;
	final int ATTRACT_RADIUS = 500;
	 
	private int fireTicks, castTicks, animate, moveCycle, initX, initY;
	int shotTurn = Controller.random.nextInt(0, 360);
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public MammothHunter(int x, int y) {
		super(x, y, HEALTH, DEF, "Mammoth Hunter");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1)/3;
		this.xp = 75;
		
		this.table = new LootRoll[][] 
				{
			{lr("w6", 4, 45), lr("w7", 3, 45)},
			{lr("ab3", 4, 45), lr("ab4", 3, 45)},
			{lr("ar6", 4, 45), lr("ar7", 3, 45)},
			{lr("r2", 6, 45)},
			{lr(Ability.Tuskrender_Flurry, 1, 8)}
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
				if(this.animate == 4 * ANIMATE_INTERVAL) {
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
				if(this.castTicks > 0) {
					this.castTicks -= 1;
				}
				if(this.x < this.initX - this.CHASE_LIMIT || this.x > this.initX + this.CHASE_LIMIT
								|| this.y < this.initY - this.CHASE_LIMIT || this.y > this.initY + this.CHASE_LIMIT) {
					this.moving = false;
					this.go(initX, initY, SPEED);
				}
				else {
					if(fireTicks <= 0){
						for(int i = 0; i < 5; i++) {
							new MammothHunterShot((int)this.x, (int)this.y, 72 * i + shotTurn);
						}
						shotTurn += Controller.random.nextInt(25, 35);
						shotTurn = shotTurn % 360;
						this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
					}
					if(castTicks <= 0 && this.inRadius(Controller.chr.getX(), Controller.chr.getY(), CAST_RANGE)) {
						int offset = Controller.random.nextInt(-55, 56);
						new MammothHunterBomb((int)this.x, (int)this.y, Controller.random.nextInt(200, 400), 
								(int)this.getAngle((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY()) + offset);
						this.castTicks = CAST_RATE;
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
		for(int i = 1; i < 5; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_MAMMOTHHUNTER.get(i - 1);
				}
				else {
					return Target.IMGS_MAMMOTHHUNTER.get(i + 3);	
				}
			}
		}
		return Target.IMGS_MAMMOTHHUNTER.get(0);
	}
}
