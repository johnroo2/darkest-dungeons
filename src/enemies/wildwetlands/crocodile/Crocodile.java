package enemies.wildwetlands.crocodile;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.ability.Ability;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class Crocodile extends Enemy{
	final static int HEALTH = 600;
	final static int DEF = 10;
	final static int SIZE = 56;
	final static double SPEED = 0.4;
	final static int SHOT_RANGE = 100;
	final static int DMG_1 = 15;
	final static int DMG_2 = 25;
	final static int DMG_3 = 40;
	final static int MIN_FIRE_RATE = 150;
	final static int MAX_FIRE_RATE = 300;
	final static int XP = 80;
	
	final int WANDER_RADIUS = 50;
	final int ATTRACT_RADIUS = 250;
	
	double[] positions = new double[18];
	int fireTicks;
	int animate;
	int ANIMATE_INTERVAL = 40;
	
	public Crocodile(int x, int y) {
		super(x, y, HEALTH, DEF, "Crocodile");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1)/3;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w2", 2, 20), lr("w3", 3, 20), lr("w4", 4, 20)},
			{lr("ab1", 2, 20), lr("ab2", 3, 20)},
			{lr("ar2", 2, 20), lr("ar3", 3, 20), lr("ar4", 4, 20)},
			{lr("r0", 3, 20), lr("r1", 2, 20)},
				{lr(Ability.Serrated_Toothblades, 1, 5)}
				};
	}

	@Override
	public void move() {
		if(this.active) {
			animate++;
			if(this.moving) {
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				else {
					int offset = Controller.random.nextInt(-15, 16);
					double[] tracking = this.relativeTrack(0.4);
					new CrocodileShot((int)this.x, (int)this.y,
							(int)(Controller.chr.getX() + tracking[0]), 
							(int)(Controller.chr.getY() + tracking[1]), 40, 4, DMG_1, -8 + offset);
					new CrocodileShot((int)this.x, (int)this.y,
							(int)(Controller.chr.getX() + tracking[0]), 
							(int)(Controller.chr.getY() + tracking[1]), 32, 3.2, DMG_2, -8 + offset);
					new CrocodileShot((int)this.x, (int)this.y,
							(int)(Controller.chr.getX() + tracking[0]), 
							(int)(Controller.chr.getY() + tracking[1]), 24, 2.4, DMG_3, -8 + offset);
					new CrocodileShot((int)this.x, (int)this.y,
							(int)(Controller.chr.getX() + tracking[0]), 
							(int)(Controller.chr.getY() + tracking[1]), 40, 4, DMG_1, 8 + offset);
					new CrocodileShot((int)this.x, (int)this.y, 
							(int)(Controller.chr.getX() + tracking[0]), 
							(int)(Controller.chr.getY() + tracking[1]), 32, 3.2, DMG_2, 8 + offset);
					new CrocodileShot((int)this.x, (int)this.y, 
							(int)(Controller.chr.getX() + tracking[0]), 
							(int)(Controller.chr.getY() + tracking[1]), 24, 2.4, DMG_3, 8 + offset);
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
		return super.inWall(5);
	}

	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 3; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_CROCODILE.get(i - 1);
				}
				else {
					return Target.IMGS_CROCODILE.get(i + 1);	
				}
			}
		}
		return Target.IMGS_CROCODILE.get(0);
	}
}
