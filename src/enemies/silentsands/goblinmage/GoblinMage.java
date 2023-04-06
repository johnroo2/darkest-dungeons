package enemies.silentsands.goblinmage;

import java.awt.image.BufferedImage;

import control.Controller;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class GoblinMage extends Enemy{
	final static int HEALTH = 250;
	final static int DEF = 0;
	final static int SIZE = 40;
	final static double SPEED = 0.5;
	final static double SHOT_SPEED = 2;
	final static int SHOT_RANGE = 140;
	final static int DMG = 18;
	final static int MIN_FIRE_RATE = 150;
	final static int MAX_FIRE_RATE = 200;
	final static int XP = 20;
	
	final static int ANIMATE_INTERVAL = 25;
	
	final int WANDER_RADIUS = 20;
	final int ATTRACT_RADIUS = 250;
	
	double[] positions = new double[18];
	int fireTicks;
	int animate;
	int shotTurn = Controller.random.nextInt(10, 21);
	
	public GoblinMage(int x, int y) {
		super(x, y, HEALTH, DEF, "Goblin Mage");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
			{
			{lr("w1", 8, 20)},
			{lr("ab1", 6, 20)},
			{lr("ar0", 2, 20), lr("ar1", 3, 20), lr("ar2", 3, 20)},
			{lr("r0", 2, 20)}
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
					for(int i = 0; i < 10; i++) {
						new GoblinMageShot((int)this.x, (int)this.y, i * 36 + shotTurn);
					}
					shotTurn += Controller.random.nextInt(10, 21);
					shotTurn = shotTurn % 360;
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
					return Target.IMGS_GOBLINMAGE.get(i - 1);
				}
				else {
					return Target.IMGS_GOBLINMAGE.get(i + 3);	
				}
			}
		}
		return Target.IMGS_GOBLINMAGE.get(0);
	}
}
