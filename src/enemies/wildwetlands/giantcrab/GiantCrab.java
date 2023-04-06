package enemies.wildwetlands.giantcrab;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import control.Controller;
import equipment.armour.Armour;
import project.Area;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class GiantCrab extends Enemy{
	final static int HEALTH = 600;
	final static int DEF = 5;
	final static int SIZE = 56;
	final static double SPEED = 2;
	final static int SHOT_RANGE = 100;
	final static int DMG_1 = 35;
	final static int DMG_2 = 25;
	final static int DMG_3 = 15;
	final static int MIN_FIRE_RATE = 100;
	final static int MAX_FIRE_RATE = 200;
	final static int XP = 60;
	
	final static int ANIMATE_INTERVAL = 35;
	final static int MIN_MOVE_INTERVAL = 30;
	final static int MAX_MOVE_INTERVAL = 45;
	
	final int ATTRACT_RADIUS = 250;
	final int CHASE_LIMIT = 1000;
	
	int fireTicks;
	int animate;
	int moveCycle;
	int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public GiantCrab(int x, int y) {
		super(x, y, HEALTH, DEF, "Giant Crab");
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
				{lr(Armour.Crab_Carapace, 1, 7)}
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
			if(this.moveCycle == 4 * moveInterval) {
				double[] tracking = this.relativeTrack(0.4);
				new GiantCrabShot((int)this.x, (int)this.y, 
						(int)(Controller.chr.getX() + tracking[0]), 
						(int)(Controller.chr.getY() + tracking[1]), 30, 3.5, DMG_1);	
				new GiantCrabShot((int)this.x, (int)this.y,
						(int)(Controller.chr.getX() + tracking[0]), 
						(int)(Controller.chr.getY() + tracking[1]), 25, 3, DMG_2);
				new GiantCrabShot((int)this.x, (int)this.y, 
						(int)(Controller.chr.getX() + tracking[0]), 
						(int)(Controller.chr.getY() + tracking[1]), 20, 2.5, DMG_3);
				moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
				this.moveCycle = 0;
			}
			if(this.moving) {
				this.animate++;
				if(this.animate == 2 * ANIMATE_INTERVAL) {
					animate = 0;
				}
				if(!(Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= SPEED * 8)) {
					approach();
				}
				if(this.moveCycle == 3*moveInterval) { //stop movement
					this.moving = false;
				}
			}
			else {
				if(this.moveCycle == 0) { //start movement
					int shiftX = Controller.random.nextInt(-120, 121);
					int shiftY = Controller.random.nextInt(-120, 121);
					this.go(Controller.chr.getX() + shiftX, Controller.chr.getY() + shiftY, SPEED);
					if(this.xVel < 0) {
						this.dir = 0;
					}
					else {
						this.dir = 1;
					}
				}
			}
			if(Controller.chr.getX() < this.x - this.CHASE_LIMIT && Controller.chr.getX() > this.x + this.CHASE_LIMIT
							&& Controller.chr.getY() < this.y - this.CHASE_LIMIT && Controller.chr.getY() > this.y + this.CHASE_LIMIT) {
				this.active = false;
			}
		}
		else {
			if(Controller.chr.getArea() == this.area ||  this.currentHealth < this.maxHealth ||
					(Controller.chr.getX() > this.x - this.ATTRACT_RADIUS && Controller.chr.getX() < this.x + this.ATTRACT_RADIUS
							&& Controller.chr.getY() > this.y - this.ATTRACT_RADIUS && Controller.chr.getY() < this.y + this.ATTRACT_RADIUS)) {
				this.active = true;
				int shiftX = Controller.random.nextInt(-120, 121);
				int shiftY = Controller.random.nextInt(-120, 121);
				this.go(Controller.chr.getX() + shiftX, Controller.chr.getY() + shiftY, SPEED);
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
					return Target.IMGS_GIANTCRAB.get(i - 1);
				}
				else {
					return Target.IMGS_GIANTCRAB.get(i + 1);	
				}
			}
		}
		return Target.IMGS_GIANTCRAB.get(0);
	}
}
