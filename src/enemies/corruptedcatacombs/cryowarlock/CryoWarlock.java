package enemies.corruptedcatacombs.cryowarlock;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.ability.Ability;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class CryoWarlock extends Enemy{
	final static int HEALTH = 2000;
	final static int DEF = 12;
	final static int SIZE = 64;
	final static double SPEED = 0.45;
	final static double SHOT_SPEED = 3.5;
	final static int SHOT_RANGE = 80;
	final static int SHOT_STALL = 125;
	final static int DMG = 120;
	
	final static int PORTAL_LIFETIME = 500;
	final static double PORTAL_SPEED = 0.75;
	final static int PORTAL_RATE = 25;
	
	final static int FIRE_RATE = 35;
	final static int XP = 125;
	
	final static int ANIMATE_INTERVAL = 15;
	
	final int WANDER_RADIUS = 40;
	final int ATTRACT_RADIUS = 450;
	
	double[] positions = new double[18];
	int fireTicks;
	int shotTurn;
	int animate;
	boolean spawned = false;
	
	public CryoWarlock(int x, int y) {
		super(x, y, HEALTH, DEF, "Cryo Warlock");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = FIRE_RATE;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
			{
			{lr("w6", 2, 45), lr("w7", 5, 45)},
			{lr("ab3", 2, 45), lr("ab4", 5, 45)},
			{lr("ar6", 2, 45), lr("ar7", 3, 45), lr("ar8", 2, 45)},
			{lr("r2", 6, 45)},
			{lr(Armour.Frostsworn_Robe, 1, 8)},
			{lr(Ability.Crystalline_Scepter, 1, 8)}
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
					new CryoWarlockShot((int)this.x, (int)this.y, shotTurn);
					new CryoWarlockShot((int)this.x, (int)this.y, shotTurn+180);
					shotTurn += Controller.random.nextInt(28, 32);
					shotTurn = shotTurn % 360;
					this.fireTicks = FIRE_RATE;
				}
				
				wander(this.positions, SPEED);
			}	

			if(!this.spawned && this.currentHealth < this.maxHealth * 0.5) {
				new CryoWarlockPortal((int)this.x, (int)this.y);
				this.spawned = true;
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
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 1) {
					return Target.IMGS_CRYOWARLOCK.get(i - 1);
				}
				else {
					return Target.IMGS_CRYOWARLOCK.get(i + 3);	
				}
			}
		}
		return Target.IMGS_CRYOWARLOCK.get(0);
	}
}
