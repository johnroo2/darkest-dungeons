//Enemy.java
//parent class for all enemies

package project;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import control.Controller;
import statuseffects.*;

public abstract class Enemy extends Target{
	//instance variables
	protected int dir, size, targetX, targetY;
	protected boolean active, drawable, moving;
	protected double xVel, yVel;
	protected int xp;
	protected Area area;
	protected TreeSet<Status> effects = new TreeSet<>();
	protected EnemyShield shield = null;
	protected LootRoll[][] table;
	private String name;
	protected int priority;

	//constructor, tethered to an area
	public Enemy(int x, int y, int hp, int def, Area area, String name) {
		super(x, y, hp, def);
		this.setName(name);
		this.active = false;
		this.drawable = true;
		this.moving = false;
		this.area = area;
		this.area.getEnemies().add(this);
		this.setPriority(0); //draw at back by default
	}
	
	//constructor, find area automatically
	public Enemy(int x, int y, int hp, int def, String name) {
		super(x, y, hp, def);
		this.setName(name);
		this.active = false;
		this.drawable = true;
		this.area = findArea();
		this.area.getEnemies().add(this);
		this.setPriority(0); //draw at back by default
	}
	
	//Desc: checks if this enemy is in an area
	//Param: Area location
	//Return: true if yes, false if no
	public boolean inArea(Area loc) {
		if(loc.getGrid().length > 0 && loc.getGrid()[0].length > 0) {
			if(this.x >= loc.getX() && this.y >= loc.getY() && this.x <= loc.getX() + loc.getGrid()[0].length * Area.BLOCK_SIZE &&
					this.y <= loc.getY() + loc.getGrid().length * Area.BLOCK_SIZE) {
				return true;
			}
		}
		return false;
	}
	
	//Desc: heal an enemy for a certain amount
	//Param: int healing
	//Return: none
	public void heal(int healing) {
		this.currentHealth += healing;
		if(this.currentHealth > this.maxHealth) {
			this.currentHealth = this.maxHealth;
		}
	}
	
	//make a new item for the loot table
	public static LootRoll lr(Equipment item, int prob, int roll) {return new LootRoll(item, prob, roll);}	
	public static LootRoll lr(String item, int prob, int roll) {return new LootRoll(item, prob, roll);}
	
	//Desc: take damage
	//Param: integer damage taken, boolean critical strike
	//Return: if the enemy dies to this damage
	public boolean damage(int dmg, boolean crit) {
		if(this.effects.contains(new EnemyArmourBreak())) {
			return damage(dmg, crit, true);
		}
		if(this.shield == null || this.shield.vulnerable()) {
			double outdmg = dmg;
			if(this.defense >= outdmg * 0.9) {
				outdmg = (int)((double)dmg * 0.1);
			}
			else{
				outdmg -= defense;
			}
			if(this.effects.contains(new EnemyExposed())) {
				outdmg += 20;
			}
			if(this.effects.contains(new EnemyCurse())) {
				outdmg *= 1.25;
			}
			this.currentHealth -= outdmg;
			Controller.chr.broadcast("attack"+outdmg);
			if(crit) {
				new PopText(true, (int)this.x, (int)this.y, (int)outdmg);
			}
			else {
				new PopText((int)this.x, (int)this.y, (int)outdmg);
			}
			if(this.currentHealth <= 0) {
				this.drop();
				Controller.chr.experience(this.xp);
				return true;
			}
		}
		else {
			new PopText((int)this.x, (int)this.y, "Immune");
		}
		return false;
	}
	
	
	//Desc: take damage (armour piercing version)
	//Param: integer damage taken, boolean critical strike, whether or not this attack pierces armour
	//Return: if the enemy dies to this damage
	public boolean damage(int dmg,  boolean crit, boolean pierce) {
		if(this.shield == null || this.shield.vulnerable()) {
			if(pierce) {
				double outdmg = dmg;
				if(this.effects.contains(new EnemyExposed())) {
					outdmg += 20;
				}
				if(this.effects.contains(new EnemyCurse())) {
					outdmg *= 1.25;
				}
				this.currentHealth -= outdmg;
				if(crit) {
					new PopText(true, (int)this.x, (int)this.y, (int)outdmg, true);
				}
				else {
					new PopText((int)this.x, (int)this.y, (int)outdmg, true);
				}
				Controller.chr.broadcast("attack"+outdmg);
				if(this.currentHealth <= 0) {
					this.drop();
					Controller.chr.experience(this.xp);
					return true;
				}
				return false;
			}
			else {
				return this.damage(dmg, crit);
			}
		}
		else {
			new PopText((int)this.x, (int)this.y, "Immune");
		}
		return false;
	}
	
	//Desc: finds the current area of this enemy
	//Param: none
	//Return: the area it's in, or null if none (this will probably cause an error)
	public Area findArea() {
		for(Area a:Area.list) {
			if(this.inArea(a)) {
				return a;
			}
		}
		System.out.println("no area!");
		return null;
	}
	
	//Desc: go to a target location
	//Param: x and y destination, travel speed
	//Return: none
	public void go(int x, int y, double speed) {
		this.targetX = x;
		this.targetY = y;
		this.moving = true;
		getVelocity(speed);
	}
	
	//Desc: get velocity components given a speed and a target
	//Param: double travel speed
	//Return: none
	public void getVelocity(double speed) {
		if(this.x == this.targetX) {
			this.xVel = 0.0;
			if(y > targetY) {
				this.yVel = -speed;
			}
			else {
				this.yVel = speed;
			}
		}
		else {
			double slope = (double)(targetY-y)/(double)(targetX-x);
			if(this.x > this.targetX) {
				this.xVel = -speed/Math.sqrt(slope*slope+1);
			}
			else {
				this.xVel = speed/Math.sqrt(slope*slope+1);
			}
			if(this.y > this.targetY) {
				this.yVel = -speed*Math.sqrt((slope*slope)/(slope*slope+1));
			}
			else {
				this.yVel = speed*Math.sqrt((slope*slope)/(slope*slope+1));
			}
		}
	}
	
	//computes the angle between two points (for aiming)
	public double getAngle(int x1, int y1, int x2, int y2) {
		if(x1 == x2) {
			if(y1 > y2) {
				return 270;
			}
			else {
				return 90;
			}
		}
		else {
			if(x2 > x1) {
				return Math.toDegrees(Math.atan((double)(y2-y1)/(double)(x2-x1)));
			}
			return Math.toDegrees(Math.atan((double)(y2-y1)/(double)(x2-x1))) + 180;
		}
	}
	
	//gets the pythagorean distance between two points
	public double getDist(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2));
	}
	
	//tracks player movement based on distance
	public double[] relativeTrack(double scale) {
		double[] out = Controller.track((int) this.getDist((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY()));
		out[0] *= scale;
		out[1] *= scale;
		return out;
	}
	
	//Desc: gets wander positions for the "wander" movement pattern (3x3 grid centered around guard post)
	//Param: int distance to travel
	//Return: double array of positions
	public double[] wanderPositions(int radius) {
		double[] output = new double[18];
		output[0] = this.x - radius;
		output[1] = this.y - radius;
		
		output[2] = this.x;
		output[3] = this.y - radius;
		
		output[4] = this.x + radius;
		output[5] = this.y - radius;
		
		output[6] = this.x - radius;
		output[7] = this.y;
	
		output[8] = this.x;
		output[9] = this.y;
		
		output[10] = this.x + radius;
		output[11] = this.y;
		
		output[12] = this.x - radius;
		output[13] = this.y + radius;
		
		output[14] = this.x;
		output[15] = this.y + radius;
		
		output[16] = this.x + radius;
		output[17] = this.y + radius;
		return output;
	}
	
	//checks if a unit is in a certain range
	public boolean inRadius(int x, int y, int radius) {return (this.x-x)*(this.x-x) + (this.y-y)*(this.y-y) <= radius * radius;}
	
	//Desc: "approach" movement pattern
	//Param: none
	//Return: none
	public void approach() {
		if(!this.effects.contains(new EnemyParalyze())) {
			double xv = xVel;
			double yv = yVel;
			if(this.effects.contains(new EnemySlow())) {
				xv *= 0.4;
				yv *= 0.4;
			}
			
			this.x += xv;
			if(inWall()) {
				this.x -= xv; 
				if(yv != 0) {
					this.y += yv/2;
					if(inWall()) {
						this.y -= yv/2;
					}
				}
			}
			this.y += yv;
			if(inWall()) {
				this.y -= yv; 
				if(xv != 0) {
					this.x += xv/2;
					if(inWall()) {
						this.x -= xv/2;
					}
				}
			}
		}
	}
	
	//Desc: "wander" movement patterh
	//Param: none
	//Return: none
	public void wander(double[] positions, double speed) {
		if(!this.effects.contains(new EnemyParalyze())) {
			double xv = xVel;
			double yv = yVel;
			if(this.effects.contains(new EnemySlow())) {
				xv *= 0.4;
				yv *= 0.4;
			}
			
			this.x += xv;
			this.y += yv;
			
			if(inWall()) {
				this.x -= xv; 
				this.y -= yv;
				int space = Controller.random.nextInt(0, 9);
				this.go((int)positions[2 * space], (int)positions[2 * space + 1], speed);
				if(xv < 0) {
					this.dir = 0;
				}
				else {
					this.dir = 1;
				}
			}
			if(Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= speed * 1.5) {
				this.x = this.targetX;
				this.y = this.targetY;
				this.moving = false;
				int space = Controller.random.nextInt(0, 9);
				this.go((int)positions[2 * space], (int)positions[2 * space + 1], speed);
				if(xv < 0) {
					this.dir = 0;
				}
				else {
					this.dir = 1;
				}
			}
		}
	}
	
	//Desc: draws healthbars
	//Param: Graphics g, player coordinates
	//Return: none
	public void drawBar(Graphics g, int shiftX, int shiftY) {
		
		//healthbar
		g.setColor(Color.BLACK);
		g.fillRect((int)this.x - this.size/2 - shiftX + 400, (int)(this.y + this.size/2 + 5) - shiftY + 400, 
				this.size, 2); 
		if(this.shield == null || this.shield.vulnerable()) {
			g.setColor(Color.GREEN);
		}
		else {
			g.setColor(new Color(60, 120, 220));
		}
		g.fillRect((int)this.x - this.size/2 - shiftX + 400, (int)(this.y + this.size/2 + 5) - shiftY + 400, 
				(int) (((double)this.currentHealth/(double)this.maxHealth) * this.size), 2); 
	}
	
	//Desc: draws status effects
	//Param: Graphics g, player coordinates, ImageObserver i
	//Return: none
	public void drawStats(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		if(this.drawable) {
			Iterator<Status> efx = this.effects.iterator();
			int j = 0;
			if(this.shield != null && this.shield.vulnerable()) {
				j = 1;
			}
			while(efx.hasNext()) {
				Status s = efx.next();
				if(s.active()) {
					g.drawImage(s.getImage(), (int)this.x+4-12*this.effects.size()+20*j - shiftX + 400, 
							(int)this.y + this.size/2 + 12 - shiftY + 400, 16, 16, i);
					j++;
				}
			}
		}
	}
	
	//Desc: draws enemy
	//Param: Graphics g, player coordinates, ImageObserver i
	//Return: none
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		if(this.drawable) {
			g.drawImage(getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);
			
			this.drawBar(g, shiftX, shiftY);
			this.drawStats(g, shiftX, shiftY, i);
		}	
	}
	
	//Desc: checks for collision
	//Param: Projectile p
	//Return: true if hit, false otherwise
	public boolean collision(Projectile p) {
		int bounds = (int)((double)this.size * 0.05);
		if(p.getX() > this.x - this.size/2 + bounds && p.getX() < this.x + this.size/2 - bounds &&
				p.getY() > this.y - this.size/2 + bounds && p.getY() < this.y + this.size/2 - bounds) {
			return true;
		}
		return false;
	}
	
	//Desc: checks for collision
	//Param: Projectile p, player coordinates, reduced hitbox factor
	//Return: true if hit, false otherwise
	public boolean collision(Projectile p, int xShift, int yShift, double bounding) {
		int bounds = (int)((double)this.size * bounding);
		if(p.getX() > this.x + xShift - this.size/2 + bounds && p.getX() < this.x + xShift + this.size/2 - bounds &&
				p.getY() > this.y + yShift - this.size/2 + bounds && p.getY() < this.y + yShift + this.size/2 - bounds) {
			return true;
		}
		return false;
	}
	
	//drops loot bag
	public void drop() {
		LootBag.lootTable((int)this.x, (int)this.y, this.table);
	}
	
	//checks if enemy is in wall
	public boolean inWall(int range) {
		for(Area a: Area.list) {
			Tile t1 = a.get((int)((this.x+this.size/2-a.getX()-range)/Area.BLOCK_SIZE),
					(int)((this.y+this.size/2-a.getY()-range)/Area.BLOCK_SIZE));
			Tile t2 = a.get((int)((this.x-this.size/2-a.getX()+range)/Area.BLOCK_SIZE),
					(int)((this.y+this.size/2-a.getY()-range)/Area.BLOCK_SIZE));
			Tile t3 = a.get((int)((this.x+this.size/2-a.getX()-range)/Area.BLOCK_SIZE),
					(int)((this.y-this.size/2-a.getY()+range)/Area.BLOCK_SIZE));
			Tile t4 = a.get((int)((this.x-this.size/2-a.getX()+range)/Area.BLOCK_SIZE),
					(int)((this.y-this.size/2-a.getY()+range)/Area.BLOCK_SIZE));
			if(t1 != null && t1.isWall()) {
				return true;
			}
			if(t2 != null && t2.isWall()) {
				return true;
			}
			if(t3 != null && t3.isWall()) {
				return true;
			}
			if(t4 != null && t4.isWall()) {
				return true;
			}
		}
		return false;
	}
	
	//default values
	public void destroy() {}
	public boolean ArmourBreakImmune() {return false;}
	public boolean CurseImmune() {return false;}
	public boolean ExposedImmune() {return false;}
	public boolean ParalyzeImmune() {return false;}
	public boolean SlowImmune() {return false;}
	
	public abstract boolean inWall();
	public abstract void move();
	public abstract BufferedImage getImage();
	
	//Desc: various getters and setters
	//Param: value for setters
	//Return: value for getters
	public TreeSet<Status> getEffects(){return this.effects;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
