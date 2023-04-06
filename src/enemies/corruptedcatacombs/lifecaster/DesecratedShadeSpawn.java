package enemies.corruptedcatacombs.lifecaster;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.silentsands.scorpion.Scorpion;
import project.AoE;
import project.Enemy;
import project.LootRoll;
import project.Projectile;
import project.Rotate;
import project.Target;

public class DesecratedShadeSpawn extends AoE{
	Lifecaster source;
	
	public DesecratedShadeSpawn(int x, int y, Lifecaster source) {
		super(x, y, 32, AoE.DESECRATEDSHADE_SPAWN, 84);
		this.source = source;
		if(!this.inWall()) {
			Controller.enemyAoE.add(this);
			source.summons++;
		}
		else {
			
		}
	}
	
	@Override
	public void destroy() {
		new DesecratedShade((int)this.x, (int)this.y, source);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 0; i < 6; i ++) {
			if(this.range >= 70 - 14 * i) {
				return this.colour.get(i);
			}
		}
		return this.colour.get(0);
	}
	
	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.radius/2 - shiftX + 400, (int)this.y-this.radius/2 - shiftY + 400, radius,radius, i);	
	}
}

class DesecratedShade extends Enemy{
	final static int HEALTH = 100;
	final static int DEF = 0;
	final static int SIZE = 32;
	final static double SPEED = 2.5;
	final static int SHOT_RANGE = 80;
	final static double SHOT_SPEED = 3.5;
	final static int DMG = 75;
	final static int MIN_FIRE_RATE = 100;
	final static int MAX_FIRE_RATE = 150;
	
	final static int ANIMATE_INTERVAL = 10;
	final static int MIN_MOVE_INTERVAL = 30;
	final static int MAX_MOVE_INTERVAL = 45;

	final int CHASE_LIMIT = 750;
	final int ATTRACT_RADIUS = 750;
	
	Lifecaster source;
	
	private int fireTicks, animate, moveCycle, initX, initY;
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	
	public DesecratedShade(int x, int y, Lifecaster source) {
		super(x, y, HEALTH, DEF, "Desecrated Shade");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.source = source;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
		this.xp = 0;
		this.table = new LootRoll[][] 
				{
				};
	}
	
	@Override
	public void destroy() {
		source.summons--;
	}

	@Override
	public boolean inWall() {
		return super.inWall(2);
	}

	@Override
	public void move() {
		if(this.active) {
			this.moveCycle++;
			if(this.moving) {
				this.animate++;
				if(this.animate == 4 * ANIMATE_INTERVAL) {
					animate = 0;
				}
				if(this.moveCycle >= this.moveInterval) {
					int shiftX = Controller.random.nextInt(-250, 251);
					int shiftY = Controller.random.nextInt(-250, 251);
					this.go(Controller.chr.getX() + shiftX, Controller.chr.getY() + shiftY, SPEED);
					if(this.xVel < 0) {
						this.dir = 0;
					}
					else {
						this.dir = 1;
					}
					this.moveCycle = 0;
					this.moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
					
				}
				if(Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= SPEED * 8) {
					if(this.targetX == this.initX && this.targetY == this.initY) {
						this.active = false;
						return;
					}
					else {
						this.moving = false;
						this.go(Controller.chr.getX(), Controller.chr.getY(), SPEED);
						if(this.xVel < 0) {
							this.dir = 0;
						}
						else {
							this.dir = 1;
						}
						this.moveCycle = 0;
						this.moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
					}
				}
				else {
					approach();
				}
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				if(this.x < this.initX - this.CHASE_LIMIT || this.x > this.initX + this.CHASE_LIMIT
								|| this.y < this.initY - this.CHASE_LIMIT || this.y > this.initY + this.CHASE_LIMIT) {
					this.moving = false;
					this.go(initX, initY, SPEED);
				}
				else {
					if(fireTicks <= 0){
						int offsetX = Controller.random.nextInt(-100, 101);
						int offsetY = Controller.random.nextInt(-100, 101);
						new DesecratedShadeShot((int)this.x, (int)this.y, Controller.chr.getX() + offsetX, Controller.chr.getY() + offsetY);
						this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
					}
				}
			}
			else {
				if(this.moveCycle == 0) { //start movements
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
		else {
			if(Controller.chr.getArea() == this.area || (Controller.chr.getX() > this.x - this.ATTRACT_RADIUS && Controller.chr.getX() < this.x + this.ATTRACT_RADIUS
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
		for(int i = 1; i < 5; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 1) {
					return Target.IMGS_DESECRATEDSHADE.get(i - 1);
				}
				else {
					return Target.IMGS_DESECRATEDSHADE.get(i + 3);	
				}
			}
		}
		return Target.IMGS_DESECRATEDSHADE.get(0);
	}
}

class DesecratedShadeShot extends Projectile{
	final int SIZE = 18;
	
	int size, dmg;
	BufferedImage img;

	public DesecratedShadeShot(int x, int y, int targetX, int targetY) {
		super(x, y, targetX, targetY, DesecratedShade.SHOT_RANGE, DesecratedShade.SHOT_SPEED);
		this.dmg = DesecratedShade.DMG;
		this.size = SIZE;
		this.img = IMGS_DESECRATEDSHADE_SHOTS;
		Controller.enemyShots.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(inWall()) {
			return true;
		}
		if(Controller.chr.collision(this)) {
			Controller.chr.damage("Desecrated Shade", this.dmg);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		this.x += this.xVel;
		this.y += this.yVel;
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
