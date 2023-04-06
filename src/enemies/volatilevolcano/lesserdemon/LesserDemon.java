package enemies.volatilevolcano.lesserdemon;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import equipment.ring.Ring;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class LesserDemon extends Enemy{
	final static int HEALTH = 1500;
	final static int DEF = 0;
	final static int SIZE = 64;
	final static double SPEED = 1.1;
	final static double SHOT_SPEED = 0.25;
	final static double SHOT_ACCEL = 0.2;
	final static int SHOT_DELAY = 20;
	final static int SHOT_RANGE = 150;
	final static int DMG = 40;
	final static int MIN_FIRE_RATE = 100;
	final static int MAX_FIRE_RATE = 150;
	final static int XP = 120;
	
	final static int ANIMATE_INTERVAL = 25;
	final int WANDER_RADIUS = 40;
	final int ATTRACT_RADIUS = 250;
	int animate;
	
	double[] positions = new double[18];
	int fireTicks;
	
	public LesserDemon(int x, int y) {
		super(x, y, HEALTH, DEF, "Lesser Demon");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w4", 2, 28), lr("w5", 3, 28), lr("w6", 3, 28)},
			{lr("ab2", 2, 28), lr("ab3", 6, 28)},
			{lr("ar4", 1, 28), lr("ar5", 3, 28), lr("ar6", 2, 28)},
			{lr("r1", 2, 20), lr("r2", 5, 20)},
			{lr(Ring.Ring_Of_Arcane_Malice, 1, 5)}
				};
	}
	
	public void arrow(double ori) {
		new LesserDemonShot((int)(this.x + 70 * Math.cos(Math.toRadians(ori))), (int)(this.y + 70 * Math.sin(Math.toRadians(ori))), (int)ori);
		new LesserDemonShot((int)(this.x + 39 * Math.cos(Math.toRadians(ori+27))), (int)(this.y + 39 * Math.sin(Math.toRadians(ori+27))), (int)ori);
		new LesserDemonShot((int)(this.x + 39 * Math.cos(Math.toRadians(ori-27))), (int)(this.y + 39 * Math.sin(Math.toRadians(ori-27))), (int)ori);
		new LesserDemonShot((int)(this.x + 35 * Math.cos(Math.toRadians(ori+90))), (int)(this.y + 35 * Math.sin(Math.toRadians(ori+90))), (int)ori);
		new LesserDemonShot((int)(this.x + 35 * Math.cos(Math.toRadians(ori-90))), (int)(this.y + 35 * Math.sin(Math.toRadians(ori-90))), (int)ori);
	}
	
	public void cross(double ori) {
		new LesserDemonShot((int)(this.x + 45 * Math.cos(Math.toRadians(ori+45))), (int)(this.y + 45 * Math.sin(Math.toRadians(ori+45))), (int)ori);
		new LesserDemonShot((int)(this.x + 45 * Math.cos(Math.toRadians(ori-45))), (int)(this.y + 45 * Math.sin(Math.toRadians(ori-45))), (int)ori);
		new LesserDemonShot((int)(this.x + 45 * Math.cos(Math.toRadians(ori+135))), (int)(this.y + 45 * Math.sin(Math.toRadians(ori+135))), (int)ori);
		new LesserDemonShot((int)(this.x + 45 * Math.cos(Math.toRadians(ori-135))), (int)(this.y + 45 * Math.sin(Math.toRadians(ori-135))), (int)ori);
		new LesserDemonShot((int)(this.x), (int)(this.y), (int)ori);
	}
	
	public void pentagon(double ori) {
		new LesserDemonShot((int)(this.x + 40 * Math.cos(Math.toRadians(ori+72))), (int)(this.y + 40 * Math.sin(Math.toRadians(ori+72))), (int)ori);
		new LesserDemonShot((int)(this.x + 40 * Math.cos(Math.toRadians(ori-72))), (int)(this.y + 40 * Math.sin(Math.toRadians(ori-72))), (int)ori);
		new LesserDemonShot((int)(this.x + 40 * Math.cos(Math.toRadians(ori+144))), (int)(this.y + 40 * Math.sin(Math.toRadians(ori+144))), (int)ori);
		new LesserDemonShot((int)(this.x + 40 * Math.cos(Math.toRadians(ori-144))), (int)(this.y + 40 * Math.sin(Math.toRadians(ori-144))), (int)ori);
		new LesserDemonShot((int)(this.x + 40 * Math.cos(Math.toRadians(ori))), (int)(this.y + 40 * Math.sin(Math.toRadians(ori))), (int)ori);
	}

	@Override
	public void move() {
		if(this.active) {
			if(this.moving) {
				this.animate++;
				if(this.animate == 2 * ANIMATE_INTERVAL) {
					this.animate = 0;
				}
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				else {
					double[] tracking = this.relativeTrack(0.5);
					double angle = this.getAngle((int)this.x, (int)this.y, 
							(int)(Controller.chr.getX() + tracking[0]),
							(int)(Controller.chr.getY() + tracking[1]));
					int sel = Controller.random.nextInt(0, 3);
					if(sel == 0) {
						arrow(angle);
					}
					else if(sel == 1) {
						pentagon(angle);
					}
					else if(sel == 2) {
						cross(angle);
					}
					this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
				}
				
				wander(this.positions, SPEED);
			}
		}
		else {
			if(Controller.chr.getArea() == this.area || this.currentHealth < this.maxHealth ||
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
	public boolean inWall() {
		return super.inWall(0);
	}

	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 3; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_LESSERDEMON.get(i - 1);
				}
				else {
					return Target.IMGS_LESSERDEMON.get(i + 1);	
				}
			}
		}
		return Target.IMGS_LESSERDEMON.get(0);
	}
}
