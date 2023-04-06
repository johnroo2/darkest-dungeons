package enemies.corruptedcatacombs.lifecaster;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import equipment.ring.Ring;
import equipment.weapon.Weapon;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class Lifecaster extends Enemy{
	final static int HEALTH = 2000;
	final static int DEF = 0;
	final static int SIZE = 48;
	final static double SPEED = 0.65;
	final static double SHOT_PERIOD = 50;
	final static double SHOT_AMP = 6;
	final static double SHOT_SPEED = 2;
	final static int SHOT_RANGE = 150;
	final static int DMG = 135;
	final static int MIN_FIRE_RATE = 200;
	final static int MAX_FIRE_RATE = 250;
	final static int SUMMON_RATE = 350;
	final static int MAX_SUMMONS = 7;
	final static int XP = 200;
	
	final static int ANIMATE_INTERVAL = 40;
	final int WANDER_RADIUS = 40;
	final int ATTRACT_RADIUS = 600;
	int animate;
	int summons;
	int shotTurn = Controller.random.nextInt(0, 360);
	
	double[] positions = new double[18];
	int fireTicks;
	int summonTicks = SUMMON_RATE;
	
	public Lifecaster(int x, int y) {
		super(x, y, HEALTH, DEF, "Lifecaster");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;

		new DesecratedShadeSpawn((int)this.x + 70, (int)this.y + 70, this);
		new DesecratedShadeSpawn((int)this.x - 70, (int)this.y + 70, this);
		new DesecratedShadeSpawn((int)this.x + 70, (int)this.y - 70, this);
		new DesecratedShadeSpawn((int)this.x - 70, (int)this.y - 70, this);
		
		this.table = new LootRoll[][] 
				{
			{lr("w6", 2, 45), lr("w7", 5, 45)},
			{lr("ab3", 2, 45), lr("ab4", 5, 45)},
			{lr("ar6", 2, 45), lr("ar7", 3, 45), lr("ar8", 2, 45)},
			{lr("r2", 6, 45)},
			{lr(Weapon.Wand_Of_The_Fallen, 1, 6)}
				};
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
					for(int i = 0; i < 10; i++) {
						new LifecasterShot((int)this.x, (int)this.y, i * 36 + shotTurn, i % 2);
					}
					shotTurn += Controller.random.nextInt(15, 25);
					shotTurn = shotTurn % 360;
					this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
				}

				if(this.summonTicks > 0) {
					this.summonTicks -= 1;
				}
				else {
					if(this.summons < MAX_SUMMONS) {
						new DesecratedShadeSpawn((int)(this.x + Controller.random.nextInt(-100, 101)), (int)(this.y + Controller.random.nextInt(-100, 101)), this);
					}
					this.summonTicks = SUMMON_RATE;
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
		return super.inWall(2);
	}

	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 3; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 1) {
					return Target.IMGS_LIFECASTER.get(i - 1);
				}
				else {
					return Target.IMGS_LIFECASTER.get(i + 1);	
				}
			}
		}
		return Target.IMGS_LIFECASTER.get(0);
	}
}


