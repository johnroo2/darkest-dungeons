package enemies.corruptedcatacombs.cryomage;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class CryoMage extends Enemy{
	final static int HEALTH = 900;
	final static int DEF = 8;
	final static int SIZE = 42;
	final static double SPEED = 0.85;
	final static double SHOT_SPEED = 2;
	final static int SHOT_RANGE = 250;
	final static double SHOT_AMP = 8;
	final static int SHOT_PERIOD = 30;
	final static int DMG = 80;
	final static int MIN_FIRE_RATE = 150;
	final static int MAX_FIRE_RATE = 200;
	final static int XP = 50;
	
	final static int ANIMATE_INTERVAL = 15;
	
	final int WANDER_RADIUS = 30;
	final int ATTRACT_RADIUS = 450;
	
	double[] positions = new double[18];
	int fireTicks;
	int animate;
	
	public CryoMage(int x, int y) {
		super(x, y, HEALTH, DEF, "Cryo Mage");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
			{
			{lr("w6", 4, 45), lr("w7", 3, 45)},
			{lr("ab3", 4, 45), lr("ab4", 3, 45)},
			{lr("ar6", 4, 45), lr("ar7", 3, 45)},
			{lr("r2", 6, 45)},
			{lr(Armour.Frostsworn_Robe, 1, 25)}
			};
	}

	@Override
	public boolean inWall() {
		return super.inWall(0);
	}

	@Override
	public void move() {
		if(this.active) {
			if(this.moving) {
				this.animate++;
				if(this.animate == 4 * ANIMATE_INTERVAL) {
					animate = 0;
				}
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				else {
					double[] tracking = this.relativeTrack(0.25);
					int offset = Controller.random.nextInt(-5, 6);
					double angle = this.getAngle((int)this.x, (int)this.y, (int)(Controller.chr.getX() + tracking[0]), 
							(int)(Controller.chr.getY() + tracking[1])) + offset;
					new CryoMageShot((int)this.x, (int)this.y, angle);
					this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
				}
				
				wander(this.positions, SPEED);
			}
		}
		else {
			if(Controller.chr.getArea() == this.area ||  this.currentHealth < this.maxHealth ||
					(Controller.chr.getX() > this.x - this.ATTRACT_RADIUS && Controller.chr.getX() < this.x + this.ATTRACT_RADIUS
							&& Controller.chr.getY() > this.y - this.ATTRACT_RADIUS && Controller.chr.getY() < this.y + this.ATTRACT_RADIUS)) {
				this.active = true;
				int space = Controller.random.nextInt(0, 9);
				this.go((int)this.positions[2 * space], (int)this.positions[2 * space + 1], SPEED);
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
					return Target.IMGS_CRYOMAGE.get(i - 1);
				}
				else {
					return Target.IMGS_CRYOMAGE.get(i + 3);	
				}
			}
		}
		return Target.IMGS_CRYOMAGE.get(0);
	}
}
