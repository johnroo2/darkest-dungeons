package enemies.wildwetlands.greatdragonfly;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.ring.Ring;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class GreatDragonfly extends Enemy{
	final static int HEALTH = 800;
	final static int DEF = 0;
	final static int SIZE = 52;
	final static double SPEED = 2.1;
	final static int SHOT_RANGE = 70;
	final static int SHOT_RANGE_SPLIT = 60;
	final static double SHOT_SPEED = 2.5;
	final static double SHOT_SPEED_SPLIT = 3.5;
	final static int DMG = 45;
	final static int DMG_SPLIT = 25; 
	final static int DMG_AOE = 12;
	final static int MIN_FIRE_RATE = 110;
	final static int MAX_FIRE_RATE = 160;
	final static int AOE_FIRE_RATE = 50;
	final static int AOE_RADIUS = 80;
	
	final static int ANIMATE_INTERVAL = 15;
	final static int MIN_MOVE_INTERVAL = 30;
	final static int MAX_MOVE_INTERVAL = 50;
	final static int XP = 80;
		
	final int CHASE_LIMIT = 800;
	final int ATTRACT_RADIUS = 500;
	
	private int fireTicks, animate, moveCycle, initX, initY, aoeTicks;
	private int shotTurn = Controller.random.nextInt(0, 360);
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public GreatDragonfly(int x, int y) {
		super(x, y, HEALTH, DEF, "Great Dragonfly");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w2", 2, 20), lr("w3", 3, 20), lr("w4", 4, 20)},
			{lr("ab1", 2, 20), lr("ab2", 3, 20)},
			{lr("ar2", 2, 20), lr("ar3", 3, 20), lr("ar4", 4, 20)},
			{lr("r0", 3, 20), lr("r1", 2, 20)},
			{lr(Ring.Lakestrider_Ring, 1, 5)}
				};
	}

	@Override
	public boolean inWall() {
		return super.inWall(10);
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
					int shiftX = Controller.random.nextInt(-350, 351);
					int shiftY = Controller.random.nextInt(-350, 351);
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
						int shiftX = Controller.random.nextInt(-350, 351);
						int shiftY = Controller.random.nextInt(-350, 351);
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
				if(this.aoeTicks > 0) {
					this.aoeTicks -= 1;
				}
				if(this.x < this.initX - this.CHASE_LIMIT || this.x > this.initX + this.CHASE_LIMIT
								|| this.y < this.initY - this.CHASE_LIMIT || this.y > this.initY + this.CHASE_LIMIT) {
					this.moving = false;
					this.go(initX, initY, SPEED);
				}
				else {
					if(fireTicks <= 0){
						for(int i = 0; i < 8; i++) {
							new GreatDragonflyShot((int)this.x, (int)this.y, 45 * i + shotTurn);
						}
						this.shotTurn += Controller.random.nextInt(15, 25);
						this.shotTurn = this.shotTurn % 360;
						this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
					}
					if(aoeTicks <= 0) {
						new GreatDragonflySlam((int)this.x, (int)this.y);
						this.aoeTicks = AOE_FIRE_RATE;
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
					return Target.IMGS_GREATDRAGONFLY.get(i - 1);
				}
				else {
					return Target.IMGS_GREATDRAGONFLY.get(i + 3);	
				}
			}
		}
		return Target.IMGS_GREATDRAGONFLY.get(0);
	}
}
