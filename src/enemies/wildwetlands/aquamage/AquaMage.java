package enemies.wildwetlands.aquamage;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class AquaMage extends Enemy{
	final static int HEALTH = 1000;
	final static int DEF = 4;
	final static int SIZE = 40;
	final static double SPEED = 0.7;
	final static int DMG_PULSE = 40;
	final static double SHOT_SPEED_PULSE = 2.1;
	final static int SHOT_RANGE_PULSE = 200;
	final static int DMG_AOE = 60;
	final static int RADIUS_AOE = 80;
	final static int FIRE_RATE = 45;
	final static int CAST_RATE = 40;
	final static int MIN_CAST_RANGE = 250;
	final static int MAX_CAST_RANGE = 450;
	final static int XP = 80;
	
	final int WANDER_RADIUS = 60;
	final int ATTRACT_RADIUS = 400;
	final int ANIMATE_INTERVAL = 50;
	
	private int animate;
	private int shotTurn = 0;
	private int castTurn = Controller.random.nextInt(0, 360);
	private int castRange = Controller.random.nextInt(MIN_CAST_RANGE, MAX_CAST_RANGE+1);
	
	double[] positions = new double[18];
	int fireTicks;
	int castTicks;
	
	public AquaMage(int x, int y) {
		super(x, y, HEALTH, DEF, "Aqua Mage");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = FIRE_RATE;
		this.castTicks = CAST_RATE;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w2", 2, 20), lr("w3", 3, 20), lr("w4", 5, 20)},
			{lr("ab1", 2, 20), lr("ab2", 3, 20)},
			{lr("ar2", 2, 20), lr("ar3", 3, 20), lr("ar4", 5, 20)},
			{lr("r0", 3, 20), lr("r1", 2, 20)},
				{lr(Armour.Wetlands_Robe, 1, 5)}
				};
	}

	@Override
	public void move() {
		if(this.active) {
			this.animate++;
			if(this.animate == 2 * ANIMATE_INTERVAL) {
				this.animate = 0;
			}
			if(this.moving) {
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				else {
					for(int i = 0; i < 4; i++) {
						new AquaMagePulse((int)this.x, (int)this.y, 90 * i + shotTurn);
					}
					shotTurn += 45;
					shotTurn = shotTurn % 360;
					this.fireTicks = FIRE_RATE;
				}
				
				if(this.currentHealth > HEALTH * 0.6) {
					if(this.castTicks > 0) {
						this.castTicks -= 1;
					}
					else {			
						new AquaMageBomb((int)this.x, (int)this.y, castRange, castTurn);
						this.castTurn += Controller.random.nextInt(80, 120);
						this.castRange = Controller.random.nextInt(MIN_CAST_RANGE, MAX_CAST_RANGE+1);
						this.castTicks = CAST_RATE * 3;
					}
				}
				else if(this.currentHealth > HEALTH * 0.2) {
					if(this.castTicks > 0) {
						this.castTicks -= 1;
					}
					else {			
						new AquaMageBomb((int)this.x, (int)this.y, castRange, castTurn);
						this.castTurn += Controller.random.nextInt(80, 120);
						this.castRange = Controller.random.nextInt(MIN_CAST_RANGE, MAX_CAST_RANGE+1);
						this.castTicks = CAST_RATE * 2;
					}
				}
				else {
					if(this.castTicks > 0) {
						this.castTicks -= 1;
					}
					else {			
						new AquaMageBomb((int)this.x, (int)this.y, castRange, castTurn);
						this.castTurn += Controller.random.nextInt(80, 120);
						this.castRange = Controller.random.nextInt(MIN_CAST_RANGE, MAX_CAST_RANGE+1);
						this.castTicks = CAST_RATE;
					}
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
				if(this.dir == 0) {
					return Target.IMGS_AQUAMAGE.get(i - 1);
				}
				else {
					return Target.IMGS_AQUAMAGE.get(i + 1);	
				}
			}
		}
		return Target.IMGS_AQUAMAGE.get(0);
	}
}
