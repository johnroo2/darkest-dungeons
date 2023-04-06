package enemies.silentsands.spikedturtle;

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

public class SpikedTurtle extends Enemy{
	final static int HEALTH = 350;
	final static int DEF = 5;
	final static int SIZE = 48;
	final static double SPEED = 1.1;
	final static int SHOT_RANGE = 110;
	final static int DMG = 20;
	final static int XP = 25;
	
	final static int ANIMATE_INTERVAL = 45;
	final static int MIN_MOVE_INTERVAL = 40;
	final static int MAX_MOVE_INTERVAL = 75;
	
	final int ATTRACT_RADIUS = 450;
	final int CHASE_LIMIT = 750;
	
	int fireTicks;
	int animate;
	int moveCycle;
	int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public SpikedTurtle(int x, int y) {
		super(x, y, HEALTH, DEF, "Spiked Turtle");
		this.dir = 0;
		this.size = SIZE;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
				{lr("w1", 2, 20), lr("w2", 5, 20)},
				{lr("ab1", 5, 20)},
				{lr("ar1", 2, 20), lr("ar2", 4, 20), lr("ar3", 1, 20)},
				{lr("r0", 4, 20)}
				};
	}

	@Override
	public boolean inWall() {
		return super.inWall(8);
	}

	@Override
	public void move() {
		if(this.active) {
			this.moveCycle++;
			if(this.moveCycle == 4 * moveInterval) {
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
					new SpikedTurtleShot((int)this.x, (int)this.y);	
					this.moving = false;
				}
			}
			else {
				if(this.moveCycle == 0) { //start movement
					this.go(Controller.chr.getX(), Controller.chr.getY(), SPEED);
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
				this.go(Controller.chr.getX(), Controller.chr.getY(), SPEED);
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
					return Target.IMGS_SPIKEDTURTLE.get(i - 1);
				}
				else {
					return Target.IMGS_SPIKEDTURTLE.get(i + 1);	
				}
			}
		}
		return Target.IMGS_SPIKEDTURTLE.get(0);
	}
}
