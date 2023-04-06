//Player.java
//THE PLAYER!

package player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import control.Controller;
import control.Driver;
import equipment.ability.Ability;
import equipment.armour.Armour;
import equipment.ring.Ring;
import equipment.weapon.Weapon;
import project.Area;
import project.Dungeon;
import project.Equipment;
import project.LootBag;
import project.PopText;
import project.Portal;
import project.Projectile;
import project.Tile;
import statuseffects.*;

public abstract class Player {
	//static variables
	public static int count = 1;
	public static int select = -1;
	
	private final static Color CENTER_HEALTH_GREEN = new Color(135, 255, 120);
	
	private final static int ANIMATE_INTERVAL = 20; 
	private final static int SHOOT_INTERVAL = 10;	
	
	//instance variables
	public int levelReached = 0;
	public boolean win = false;
	
	protected Equipment defaultWeapon;
	protected Equipment defaultAbility;
	
	private int size = 40;
	private double x, y;
	private int motionTicks, fireTicks, castTicks, animate, dir;
	protected double maxHealth, currentHealth, maxMana, currentMana;
	protected int attack, defense, vitality, wisdom, speed, dexterity, crit, spellsurge;
	protected int currentXp;
	private int level = 1;
	
	protected TreeSet<Status> effects = new TreeSet<>();
	
	protected Sidebar sidebar = new Sidebar();
	
	protected String wSubtype, abSubtype1, abSubtype2, arSubtype;
	
	private BufferedImage[] imgs;
	
	private Area area;
	
	private Dungeon dungeon;
	public String classType;
	
	protected Equipment weapon;
	protected Equipment ability;
	protected Equipment armour;
	protected Equipment ring;
	protected Equipment[] inventory = new Equipment[8];
	
	private int tileTicks;
	private final int TILE_CYCLE = 60;
	private Tile currentTile;
	private HashMap<String, Integer> killFrequency = new HashMap<String, Integer>();
	
	//constructor :)
	public Player() {}
	
	//Desc: checks if the player is hit by a projectile
	//Param: Projectile p
	//Return: true if hit, false otherwise
	public boolean collision(Projectile p) {
		if(p.getX() > this.x - 20 && p.getX() < this.x + 20 &&
				p.getY() > this.y - 20 - 2 && p.getY() < this.y + 20) {
			return true;
		}
		return false;
	}
	
	//Desc: broadcasts an "event" to all items (i.e reactive procs)
	//Param: String event
	//Return: none
	public void broadcast(String event) {
		if(this.weapon != null) {
			this.weapon.passive(event);
		}
		if(this.ability != null) {
			this.ability.passive(event);
		}
		if(this.armour != null) {
			this.armour.passive(event);
		}
		if(this.ring != null) {
			this.ring.passive(event);
		}
	}
	
	//Desc: take damage
	//Param: source of damage, damage number
	//Return: none
	public void damage(String source, int dmg) {
		if(this.effects.contains(new AllyArmourBreak())) {
			this.damage(source, dmg, true);
			return;
		}
		double outdmg = 0;
		if(this.effects.contains(new AllyArmoured())) {
			if((this.defense+20) >= dmg * 0.9) {
				outdmg = (int)(Math.ceil((double)dmg * 0.1));
			}
			else{
				outdmg = dmg - (defense+20);
			}
		}
		else {
			if(this.defense >= dmg * 0.9) {
				outdmg = (int)(Math.ceil((double)dmg * 0.1));
			}
			else{
				outdmg = dmg - defense;
			}
		}
		if(this.effects.contains(new AllyExposed())) {
			outdmg += 20;
		}
		if(this.effects.contains(new AllyCurse())){
			outdmg *= 1.25;
		}
		this.currentHealth -= outdmg;
		new PopText((int)outdmg);
		this.broadcast("hit");
		if(outdmg > 0) {
			this.broadcast("damaged"+outdmg);
		}
		if(this.currentHealth <= 0) {
			Driver.death(source);
		}
	}
	
	//Desc: take damage, armour piercing
	//Param: source of damage, damage number
	//Return: none
	public void damage(String source, int dmg, boolean pierce) {
		if(pierce) {
			double outdmg = 0;
			outdmg = dmg;
			if(this.effects.contains(new AllyExposed())) {
				outdmg += 20;
			}
			if(this.effects.contains(new AllyCurse())){
				outdmg *= 1.25;
			}
			this.currentHealth -= outdmg;
			new PopText((int)outdmg, true);
			this.broadcast("hit");
			if(outdmg > 0) {
				this.broadcast("damaged"+outdmg);
			}
			if(this.currentHealth <= 0) {
				Driver.death(source);
			}
		}
		else {
			this.damage(source, dmg);
		}
	}
	
	//kill an enemy (add it to the map)
	public void kill(String name) {
		if(this.killFrequency.containsKey(name)) {
			this.killFrequency.put(name, this.killFrequency.get(name) + 1);
		}
		else {
			this.killFrequency.put(name, 1);
		}
	}
	
	//heal
	public void heal(int healing) {
		int ch = (int)this.currentHealth;
		if(!this.effects.contains(new AllySick())) {
			this.currentHealth += healing;
			if(this.currentHealth > this.maxHealth) {
				this.currentHealth = this.maxHealth;
			}
			if((int)this.currentHealth-ch > 0) {
				new PopText(PopText.POP_GREEN, (int)this.currentHealth-ch);
			}
		}
	}
	
	//restore mana
	public void manaHeal(int manaHealing) {
		int cm = (int)this.currentMana;
		if(!this.effects.contains(new AllyQuiet())) {
			this.currentMana += manaHealing;
			if(this.currentMana > this.maxMana) {
				this.currentMana = this.maxMana;
			}
			if((int)this.currentMana-cm > 0) {
				new PopText(PopText.POP_BLUE, (int)this.currentMana-cm);
			}
		}
	}
	
	//gain xp
	public void experience(int xp) {
		if(this.getLevel() < 15) {
			if(this.ring != null && this.ring == Ring.Mechanical_Ring) {
				this.currentXp += 2 * xp;
			}
			else {
				this.currentXp += xp;
			}
			if(this.currentXp >= getNextXp()) {
				this.levelUp();
			}
		}
	}
	
	//xp generation, stats stuff
	public int getNextXp() {return 50 + 50 * this.getLevel();}	
	public int approx(double d) {return (int)Math.round(d - 0.5 + Math.random());}	
	public int greatStat() {return approx(2.5);}
	public int highStat() {return approx(2.4);}
	public int medStat() {return approx(2.25);}
	public int lowStat() {return approx(2.1);}
	public int zeroStat() {return approx(2);}
	
	public void levelUp() {
		this.currentXp = 0;
		this.level += 1;
	}
	public void setLevel(int i) {
		if(i <= 1) {
			return;
		}
		this.levelUp();
		this.setLevel(i-1);
	}
	
	//Desc: checks if the player is in an area
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
	
	//Desc: gets the current area of the player
	//Param: none
	//Return: current area
	public Area findArea() {
		for(Area a:this.dungeon.getAreas()) {
			if(this.inArea(a)) {
				return a;
			}
		}
		System.out.println("no area!");
		return (Area) null;
	}
	
	//Desc: gets the closest loot bag
	//Param: none
	//Return: the closest loot bag
	public LootBag closeBag() {
		double minDistance = 10000;
		LootBag output = (LootBag) null;
		for(LootBag l: LootBag.list) {
			if(this.x > l.getX() - 50 && this.x < l.getX() + 50 && this.y > l.getY() - 50 && this.y < l.getY() + 50) {
				double dist = (this.x - l.getX()) * (this.x - l.getX()) + (this.y - l.getY()) + (this.y - l.getY());
				if(dist < minDistance) {
					output = l;
					minDistance = dist;
				}
			}
		}
		return output;
	}
	
	//Desc: gets the closest portal
	//Param: none
	//Return: the closest portal
	public Portal closePortal() {
		double minDistance = 640000;
		Portal output = (Portal) null;
		for(Portal p: Portal.list) {
			if(p.inRange()) {
				double dist = (this.x - p.getX()) * (this.x - p.getX()) + (this.y - p.getY()) + (this.y - p.getY());
				if(dist < minDistance) {
					output = p;
					minDistance = dist;
				}
			}
		}
		return output;
	}
	
	//checks if in wall
	public boolean inWall() {
		for(Area a: Area.list) {
			Tile t1 = a.get((int)((this.x+this.size/2-5-a.getX())/Area.BLOCK_SIZE),
					(int)((this.y+this.size/2-5-a.getY())/Area.BLOCK_SIZE));
			Tile t2 = a.get((int)((this.x-this.size/2+5-a.getX())/Area.BLOCK_SIZE),
					(int)((this.y+this.size/2-5-a.getY())/Area.BLOCK_SIZE));
			Tile t3 = a.get((int)((this.x+this.size/2-5-a.getX())/Area.BLOCK_SIZE),
					(int)((this.y-this.size/2+5-a.getY())/Area.BLOCK_SIZE));
			Tile t4 = a.get((int)((this.x-this.size/2+5-a.getX())/Area.BLOCK_SIZE),
					(int)((this.y-this.size/2+5-a.getY())/Area.BLOCK_SIZE));
			
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
	
	//checks if on water
	public boolean inWater() {
		for(Area a: Area.list) {
			Tile t1 = a.get((int)((this.x+this.size/2-12-a.getX())/Area.BLOCK_SIZE),
					(int)((this.y+this.size/2-12-a.getY())/Area.BLOCK_SIZE));
			Tile t2 = a.get((int)((this.x-this.size/2+12-a.getX())/Area.BLOCK_SIZE),
					(int)((this.y+this.size/2-12-a.getY())/Area.BLOCK_SIZE));
			Tile t3 = a.get((int)((this.x+this.size/2-12-a.getX())/Area.BLOCK_SIZE),
					(int)((this.y-this.size/2+12-a.getY())/Area.BLOCK_SIZE));
			Tile t4 = a.get((int)((this.x-this.size/2+12-a.getX())/Area.BLOCK_SIZE),
					(int)((this.y-this.size/2+12-a.getY())/Area.BLOCK_SIZE));
			
			if(t1 != null && t1.isWater()) {
				this.currentTile = t1;
				return true;
			}
			if(t2 != null && t2.isWater()) {
				this.currentTile = t2;
				return true;
			}
			if(t3 != null && t3.isWater()) {
				this.currentTile = t3;
				return true;
			}
			if(t4 != null && t4.isWater()) {
				this.currentTile = t4;
				return true;
			}
		}
		this.currentTile = null;
		return false;
	}
	
	//Desc: move
	//Param: none
	//Return: none
	public void move() {
		if(this.motionTicks > 0) {
			this.motionTicks -= 100;
		}
		else {
			if(Controller.keys[0] || Controller.keys[1] || Controller.keys[2] || Controller.keys[3]) {
				double spd = this.getMovementSpeed();
				this.motionTicks += 100;
				animate++;
				boolean diagonal = false;
//				boolean[] scan = getScan(loc, Main.wallValues);
				
				this.area = findArea();
				
				//W
				if(Controller.keys[0]) {
					if(this.fireTicks == 0) {
						this.dir = 0;
					}
					this.y -= spd;
					if(this.inWall()) {
						this.y += spd;
					}
					else {
						if (diagonal == false) {
	                        diagonal = true;
	                    }
	                    else {
	                    	this.motionTicks += 40;
	                    }
					}
				}
				//A
				if(Controller.keys[1]) {
					if(this.fireTicks == 0) {
						this.dir = 1;
					}
					this.x -= spd;
					if(this.inWall()) {
						this.x += spd;
					}
					else {
						if (diagonal == false) {
	                        diagonal = true;
	                    }
	                    else {
	                    	this.motionTicks += 40;
	                    }
					}
				}
				//S
				if(Controller.keys[2]) {
					if(this.fireTicks == 0) {
						this.dir = 2;
					}
					this.y += spd;
					if(this.inWall()) {
						this.y -= spd;
					}
					else {
						if (diagonal == false) {
	                        diagonal = true;
	                    }
	                    else {
	                    	this.motionTicks += 40;
	                    }
					}
				}
				//D
				if(Controller.keys[3]) {
					if(this.fireTicks == 0) {
						this.dir = 3;
					}
					this.x += spd;
					if(this.inWall()) {
						this.x -= spd;
					}
					else {
						if (diagonal == false) {
	                        diagonal = true;
	                    }
	                    else {
	                    	this.motionTicks += 40;
	                    }
					}
				}
			}
		}
	}
	
	//Desc: shoot
	//Param: none
	//Return: none
	public void shoot() {
		if(Controller.mouseCoords[0] <= 800 && Controller.mouseCoords[0] >= 0 && this.weapon != null && !this.effects.contains(new AllyDisarmed())) {
			if(this.fireTicks > 0) {
				this.fireTicks -= 1;
			}
			if((Controller.keys[4] || Controller.autofire) && this.fireTicks == 0) {
				if(Controller.mouseCoords[0] <= 400) {
					if(Controller.mouseCoords[1] <= 400){
						if(Controller.mouseCoords[0] >= Controller.mouseCoords[1]) {
							this.dir = 0;
						}
						else {
							this.dir = 1;
						}
					}
					else {
						if(Controller.mouseCoords[0] + Controller.mouseCoords[1] <= 800) {
							this.dir = 1;
						}
						else {
							this.dir = 2;
						}
					}
				}
				else {
					if(Controller.mouseCoords[1] <= 400){
						if(Controller.mouseCoords[0] + Controller.mouseCoords[1] <= 800) {
							this.dir = 0;
						}
						else {
							this.dir = 3;
						}
					}
					else {
						if(Controller.mouseCoords[0] >= Controller.mouseCoords[1]) {
							this.dir = 3;
						}
						else {
							this.dir = 2;
						}
					}
				}
				this.weapon.active();
			}
		}
	}
		
	//Desc: cast
	//Param: none
	//Return: none
	public void cast() {
		if(Controller.mouseCoords[0] <= 800 && Controller.mouseCoords[0] >= 0 && this.ability != null && !this.effects.contains(new AllySilenced())) {
			if(this.castTicks > 0) {
				this.castTicks -= 1;
			}
			if(Controller.keys[5] && this.castTicks == 0) {
				if(Controller.mouseCoords[0] <= 400) {
					if(Controller.mouseCoords[1] <= 400){
						if(Controller.mouseCoords[0] >= Controller.mouseCoords[1]) {
							this.dir = 0;
						}
						else {
							this.dir = 1;
						}
					}
					else {
						if(Controller.mouseCoords[0] + Controller.mouseCoords[1] <= 800) {
							this.dir = 1;
						}
						else {
							this.dir = 2;
						}
					}
				}
				else {
					if(Controller.mouseCoords[1] <= 400){
						if(Controller.mouseCoords[0] + Controller.mouseCoords[1] <= 800) {
							this.dir = 0;
						}
						else {
							this.dir = 3;
						}
					}
					else {
						if(Controller.mouseCoords[0] >= Controller.mouseCoords[1]) {
							this.dir = 3;
						}
						else {
							this.dir = 2;
						}
					}
				}
				
				this.ability.active();
			}
		}
	}
	
	//regen health
	public void regenerate() {
		this.currentHealth += this.getHealing();
		this.currentMana += this.getManaHealing();
		if(this.currentHealth > this.maxHealth) {
			this.currentHealth = this.maxHealth;
		}
		if(this.currentMana > this.maxMana) {
			this.currentMana = this.maxMana;
		}
		if(this.effects.contains(new AllyQuiet())){
			this.currentMana = 0;
		}
	}
	
	//gets vitality value
	public double getHealing() {
		if(this.effects.contains(new AllySick())) {
			return 0;
		}
		return (1.0 + 0.2 * this.vitality)/150.0;
	}
	
	//gets wisdom value
	public double getManaHealing() {
		if(this.effects.contains(new AllyQuiet())) {
			return 0;
		}
		return (0.5 + 0.15 * this.wisdom)/150.0;
	}
	
	//gets speed value
	public double getMovementSpeed() {
		if(this.effects.contains(new AllyParalyze())) {
			return 0;
		}
		double out;
		if(this.effects.contains(new AllySlow())) {
			out = 3;
		}
		else if(this.effects.contains(new AllySpeedy())) {
			out = 4.5 + 0.075 * this.speed;
		}
		else {
			out = 3 + 0.05 * this.speed;
		}
		if(this.inWater()) {
			out *= 0.5;
		}
		return out;
	}
	
	//checks if player is being damaged by water
	public void runTiles() {
		if(this.inWater()) {
			if(this.tileTicks > 0) {
				this.tileTicks -= 1;
			}
			else {
				if(this.currentTile != null && this.currentTile.getDamage() > 0) {
					this.damage(this.currentTile.getName(), this.currentTile.getDamage(), true);
				}
				this.tileTicks = TILE_CYCLE;
			}
		}
		else {
			this.tileTicks = TILE_CYCLE/2;
		}
	}
	
	//animate
	public void animate() {
		if(this.animate == 4 * ANIMATE_INTERVAL) {
			this.animate = 0;
		}
	}
	
	//draw
	public void draw(Graphics g, ImageObserver i) {
		boolean down = (this.weapon != null && this.fireTicks > ((Weapon)this.weapon).getFireCooldown(this.dexterity)-SHOOT_INTERVAL) || 
				(this.ability != null && this.castTicks > ((Ability)this.ability).getCastRate()-SHOOT_INTERVAL);
		//draw self
		if(this.dir == 1 && down) {
			g.drawImage(getImage(), 365, 380, 55, 40, i);
		}
		else if(this.dir == 3 && down) {
			g.drawImage(getImage(), 380, 380, 55, 40, i);
		}
		else{
			g.drawImage(getImage(), 380, 380, 40, 40, i);
		}
		
		this.drawStats(g, i);
		g.setColor(Color.BLACK);
		g.fillRect(380, 425, 40, 2); 
		g.setColor(CENTER_HEALTH_GREEN);
		g.fillRect(380, 425, (int) (40.0 * this.currentHealth/this.maxHealth), 2); 
	}
	
	//get current animation frame
	public BufferedImage getImage() {
		if(this.weapon != null && this.fireTicks > ((Weapon)this.weapon).getFireCooldown(this.dexterity)-SHOOT_INTERVAL) {
			return this.getImgs()[5 * dir + 4];
		}
		if(this.ability != null && this.castTicks > ((Ability)this.ability).getCastRate()-SHOOT_INTERVAL) {
			return this.getImgs()[5 * dir + 4];
		}
		else {
			for(int i = 1; i < 5; i++) {
				if(this.animate <= ANIMATE_INTERVAL * i) {
					return this.getImgs()[5 * dir + i-1];
				}
			}
			return this.getImgs()[5 * dir]; //this will never happen
		}
	}
	
	//draw status effects
	public void drawStats(Graphics g, ImageObserver i) {
		Iterator<Status> efx = this.effects.iterator();
		int j = 0;
		while(efx.hasNext()) {
			Status s = efx.next();
			if(s.active()) {
				g.drawImage(s.getImage(), (int)400+4-12*this.effects.size()+20*j, 
						(int)400 - 40, 16, 16, i);
				j++;
			}
		}
	}
	
	//Desc: a couple of getters and setters
	//Param: value for setters
	//Desc: value for getters
	public int getX() {return (int)this.x;}	
	public int getY() {return (int)this.y;}	
	public void setX(int i) {this.x = i;}
	public void setY(int i) {this.y = i;}
	public Area getArea() {return this.area;}
	public int getAttack() {return this.attack;}
	public void setAttack(int i) {this.attack = i;}
	public int getDefense() {return this.defense;}
	public void setDefense(int i) {this.defense = i;}
	public int getSpeed() {return this.speed;}
	public void setSpeed(int i) {this.speed = i;}
	public int getVitality() {return this.vitality;}
	public void setVitality(int i) {this.vitality = i;}
	public int getWisdom() {return this.wisdom;}
	public void setWisdom(int i) {this.wisdom = i;}
	public double getMaxMana() {return this.maxMana;}
	public void setMaxMana(double i) {this.maxMana = i;}
	public double getCurrentMana() {return this.currentMana;}
	public void setCurrentMana(double i) {this.currentMana = i;}
	public double getMaxHealth() {return this.maxHealth;}
	public void setMaxHealth(double i) {this.maxHealth = i;}
	public int getCrit() {return this.crit;}
	public void setCrit(int i) {this.crit = i;}
	public int getSpellsurge() {return this.spellsurge;}
	public void setSpellsurge(int i) {this.spellsurge = i;}
	public int getCastTicks() {return this.castTicks;}
	public void setCastTicks(int i) {this.castTicks = i;}
	public void setFireTicks(int i) {this.fireTicks = i;}
	public Equipment getWeapon() {return this.weapon;}
	public Equipment getAbility() {return this.ability;}
	public Equipment getArmour() {return this.armour;}
	public Equipment getRing() {return this.ring;}
	public void setWeapon(Weapon i) {this.weapon = i;}
	public void setAbility(Ability i) {this.ability = i;}
	public void setArmour(Armour i) {this.armour = i;}
	public Equipment[] getInventory() {return this.inventory;}
	public void setInventory(int i, Equipment e) {this.inventory[i] = e;}
	public int getCurrentXp() {return this.currentXp;}
	public void setCurrentXp(int i) {this.currentXp = i;}
	public Dungeon getDungeon() {return this.dungeon;}
	public void setDungeon(Dungeon i) {this.dungeon = i;}
	public int getDexterity() {return this.dexterity;}
	public void setDexterity(int i) {this.dexterity = i;}
	public TreeSet<Status> getEffects(){return this.effects;}
	public Sidebar getSidebar() {return this.sidebar;}
	public Equipment getDefaultWeapon() {return this.defaultWeapon;}
	public Equipment getDefaultAbility() {return this.defaultAbility;}
	public HashMap<String, Integer> getKillFrequency(){return this.killFrequency;}

	public int getLevel() {
		return level;
	}

	public BufferedImage[] getImgs() {
		return imgs;
	}

	public void setImgs(BufferedImage[] imgs) {
		this.imgs = imgs;
	}
}
