package enemies.silentsands.snake;

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

public class Snake extends Enemy{
	final static int HEALTH = 150;
	final static int DEF = 0;
	final static int SIZE = 32;
	final static double SPEED = 0.5;
	final static double SHOT_SPEED = 2.4;
	final static int SHOT_RANGE = 200;
	final static int DMG = 15;
	final static int MIN_FIRE_RATE = 150;
	final static int MAX_FIRE_RATE = 250;
	final static int XP = 15;
	
	final int WANDER_RADIUS = 20;
	final int ATTRACT_RADIUS = 250;
	final int ANIMATE_INTERVAL = 25;
	
	private int animate;
	
	double[] positions = new double[18];
	int fireTicks;
	
	public Snake(int x, int y) {
		super(x, y, HEALTH, DEF, "Snake");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
				{lr("w1", 8, 20)},
				{lr("ab1", 4, 20)},
				{lr("ar0", 4, 20), lr("ar1", 2, 20)}
				};
	}

	@Override
	public void move() {
		if(this.active) {
			this.animate++;
			if(this.animate == 4 * ANIMATE_INTERVAL) {
				this.animate = 0;
			}
			if(this.moving) {
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				else {
					int offset = Controller.random.nextInt(-40, 41);
					new SnakeShot((int)this.x, (int)this.y - 10, (int)Controller.chr.getX(), (int)Controller.chr.getY(), -25 + offset);
					new SnakeShot((int)this.x, (int)this.y - 10, (int)Controller.chr.getX(), (int)Controller.chr.getY(), 25 + offset);
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
		for(int i = 1; i < 5; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_SNAKE.get(i - 1);
				}
				else {
					return Target.IMGS_SNAKE.get(i + 3);	
				}
			}
		}
		return Target.IMGS_SNAKE.get(0);
	}
}
