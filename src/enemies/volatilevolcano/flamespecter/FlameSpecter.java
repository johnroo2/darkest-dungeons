package enemies.volatilevolcano.flamespecter;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import equipment.weapon.Weapon;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class FlameSpecter extends Enemy{
	final static int HEALTH = 500;
	final static int DEF = 0;
	final static int SIZE = 40;
	final static double SPEED = 3.5;
	final static int ORANGE_RANGE = 120;
	final static double ORANGE_SPEED = -1;
	final static double ORANGE_ACCEL = 0.04;
	final static int ORANGE_DMG = 25;
	final static int YELLOW_RANGE = 110;
	final static double YELLOW_SPEED = 5;
	final static int YELLOW_DMG = 45;
	final static double YELLOW_ACCEL = 0.008;
	final static int FIRE_RATE = 40;
	
	final static int ANIMATE_INTERVAL = 8;
	final static int MIN_MOVE_INTERVAL = 20;
	final static int MAX_MOVE_INTERVAL = 30;
	
	final static int MIN_PHASE_RATE = 275;
	final static int MAX_PHASE_RATE = 350;

	final int CHASE_LIMIT = 600;
	final int ATTRACT_RADIUS = 400;
	
	private int fireTicks, animate, moveCycle, initX, initY;
	private int shotTurn;
	private int phaseTicks;
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public FlameSpecter(int x, int y) {
		super(x, y, HEALTH, DEF, "Flaming Specter");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = FIRE_RATE;
		this.phaseTicks = Controller.random.nextInt(MIN_PHASE_RATE, MAX_PHASE_RATE+1);
		this.xp = 100;
		
		this.table = new LootRoll[][] 
				{
			{lr("w4", 3, 28), lr("w5", 3, 28), lr("w6", 1, 28)},
			{lr("ab2", 2, 28), lr("ab3", 7, 28)},
			{lr("ar4", 1, 28), lr("ar5", 4, 28), lr("ar6", 2, 28)},
			{lr("r1", 2, 28), lr("r2", 5, 28)},
			{lr(Weapon.Spectral_Fire_Wand, 1, 5)}
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
				if(this.x < this.initX - this.CHASE_LIMIT || this.x > this.initX + this.CHASE_LIMIT
								|| this.y < this.initY - this.CHASE_LIMIT || this.y > this.initY + this.CHASE_LIMIT) {
					this.moving = false;
					this.go(initX, initY, SPEED);
					this.moveCycle = 0;
					this.moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
					this.phaseTicks = Controller.random.nextInt(MIN_PHASE_RATE, MAX_PHASE_RATE+1);
					approach();
				}
				else {
					if(this.phaseTicks < 200 && this.phaseTicks > 0) {
						if(this.fireTicks > 0) {
							this.fireTicks -= 1;
						}
						else{
							for(int i = 0; i < 6; i++) {
								new FlameSpecterYellow((int)this.x, (int)this.y, 60 * i + shotTurn);
								new FlameSpecterOrange((int)this.x, (int)this.y, 30 + 60 * i + shotTurn);
							}
							this.shotTurn += 15;
							this.shotTurn = this.shotTurn % 360;
							this.fireTicks = FIRE_RATE;
						}
						this.phaseTicks -= 1;
					}
					else if(this.phaseTicks > 0) {
						this.phaseTicks -= 1;
						approach();
					}
					else {
						this.phaseTicks = Controller.random.nextInt(MIN_PHASE_RATE, MAX_PHASE_RATE+1);
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
				if(this.dir == 1) {
					return Target.IMGS_FLAMESPECTER.get(i - 1);
				}
				else {
					return Target.IMGS_FLAMESPECTER.get(i + 3);	
				}
			}
		}
		return Target.IMGS_FLAMESPECTER.get(0);
	}
}
