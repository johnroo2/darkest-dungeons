package enemies.silentsands.scorpion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import control.Controller;
import project.Area;
import project.Enemy;
import project.LootBag;
import project.LootRoll;
import project.Projectile;
import project.Target;

public class Scorpion extends Enemy{
	final static int HEALTH = 150;
	final static int DEF = 0;
	final static int SIZE = 32;
	final static double SPEED = 0.7;
	final static double SHOT_SPEED = 2.5;
	final static int SHOT_RANGE = 150;
	final static int DMG = 12;
	final static int MIN_FIRE_RATE = 90;
	final static int MAX_FIRE_RATE = 250;
	final static int XP = 15;
	
	final int WANDER_RADIUS = 20;
	final int ATTRACT_RADIUS = 250;
	
	double[] positions = new double[18];
	int fireTicks;
	
	public Scorpion(int x, int y) {
		super(x, y, HEALTH, DEF, "Scorpion");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
				{lr("w0", 2, 20), lr("w1", 4, 20)},
				{lr("ar0", 4, 20), lr("ar1", 2, 20)}
				};
	}

	@Override
	public void move() {
		if(this.active) {
			if(this.moving) {
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				else {
					int targetX = Controller.chr.getX() + Controller.random.nextInt(-100, 101);
					int targetY = Controller.chr.getY() + Controller.random.nextInt(-100, 101);
					new ScorpionShot((int)this.x, (int)this.y - 16, targetX, targetY);
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
		if(this.dir == 0) {
			return Target.IMGS_SCORPION.get(0);
		}
		else {
			return Target.IMGS_SCORPION.get(1);
		}
	}
}
