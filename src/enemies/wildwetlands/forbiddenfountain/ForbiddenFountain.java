package enemies.wildwetlands.forbiddenfountain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.silentsands.scorpionqueen.ScorpionQueen_Dagger;
import equipment.ability.Ability;
import equipment.weapon.Weapon;
import portals.SilentSandsPortal;
import portals.VolatileVolcanoPortal;
import portals.WildWetlandsPortal;
import project.Boss;
import project.LootRoll;
import project.Projectile;
import project.Target;

public class ForbiddenFountain extends Boss{
	final static int HEALTH = 12000;
	final static int DEF = 12;
	final static int SIZE = 120;

	
	final int VIEW_RADIUS = 1000;
	final int ATTRACT_RADIUS = 350;
	final int ANIMATE_INTERVAL = 30;
	final static int XP = 500;
	
	final static int BOOMERANG_DMG = 55;
	final static double BOOMERANG_SPEED = 3;
	final static int BOOMERANG_RANGE = 150;
	
	final static int ARROW_DMG = 40; //variant with ap
	final static double ARROW_SPEED = 1;
	final static double ARROW_ACCEL = 0.05;
	final static int ARROW_RANGE = 300;
	
	final static int COMET_DMG = 60;
	final static double COMET_SPEED = 1.5;
	final static int COMET_RANGE = 350;
	
	final static int SHOTGUN_DMG = 60;
	final static double SHOTGUN_SPEED = 2.5;
	final static int SHOTGUN_RANGE = 200;
	
	final static int PELLET_DMG = 25; //armour piercing
	final static double PELLET_SPEED = 1.5;
	final static int PELLET_RANGE = 350;
	
	final static int BEAM_DMG = 40;
	
	final static int SPEAR_DMG = 75;
	final static int SPEAR_DELAY = 50;
	final static double SPEAR_SPEED = -0.3;
	final static double SPEAR_ACCEL = 0.4;
	final static int SPEAR_RANGE = 300;
	
	final static int BUBBLE_DMG = 35; //armour piercing
	final static double BUBBLE_SPEED = 1;
	final static double BUBBLE_ACCEL = 0.03;
	final static int BUBBLE_RANGE = 300;
	
	final static int WAVE_DMG = 60;
	final static double WAVE_SPEED = 1.4;
	final static int WAVE_RANGE = 400;
	
	final static int PHASE_LIMIT = 1500;
	
	private int animate, attack, fireTicks, shieldTicks, shotTurn;
	private int parity = 1;
	private int phaseTicks = PHASE_LIMIT;

	public ForbiddenFountain(int x, int y) {
		super(x, y, HEALTH, DEF, "Forbidden Fountain");
		this.barName = "Forbidden Fountain";
		this.shield.deactivate();
		this.size = SIZE;
		this.xp = XP;
		
		if(Controller.coinflip()) {
			this.parity = -1;
		}
		
		this.table = new LootRoll[][] 
				{
				{lr("w2", 1, 15), lr("w3", 5, 15), lr("w4", 2, 15)},
				{lr("w3", 2, 15), lr("w4", 2, 15), lr("w5", 3, 15)},
				{lr("ab2", 8, 25), lr("ab3", 4, 25)},
				{lr("ab2", 6, 25), lr("ab3", 4, 25)},
				{lr("ar2", 2, 12), lr("ar3", 3, 12), lr("ar4", 2, 12)},
				{lr("ar3", 3, 18), lr("ar4", 4, 18), lr("ar5", 3, 18)},
				{lr("r1", 6, 12), lr("r2", 3, 12)},
				{lr(Weapon.Spirit_Staff, 1, 7), lr(Weapon.Brookchaser_Bow, 1, 7), 
					lr(Ability.Tome_Of_Clarity, 1, 7), lr(Ability.Rivermaker, 1, 7)}
				};
	}

	@Override
	public void begin() {
		this.shield.activate();
		this.shieldTicks = 250;
		this.phase = 0;
		for(int i = 0; i < area.getExits().length; i += 4) {
			for(int j = 0; j < area.getExits()[i+1]; j++) {
				if(area.getExits()[i] == 0) { //move by x
					area.set(area.getExits()[i+2]+j, area.getExits()[i+3], area.getBorder());
				}
				else {
					area.set(area.getExits()[i+2], area.getExits()[i+3]+j, area.getBorder());
				}
			}
		}
	}

	@Override
	public void end() {
		for(int i = 0; i < area.getExits().length; i += 4) {
			for(int j = 0; j < area.getExits()[i+1]; j++) {
				if(area.getExits()[i] == 0) { //move by x
					area.set(area.getExits()[i+2]+j, area.getExits()[i+3], area.getInner());
				}
				else {
					area.set(area.getExits()[i+2], area.getExits()[i+3]+j, area.getInner());
				}
			}
		}	
		
		new WildWetlandsPortal((int)this.x, (int)this.y - 120, true);
		new VolatileVolcanoPortal((int)this.x, (int)this.y+ 120, false);
	}

	@Override
	public boolean inWall() {
		return super.inWall(0);
	}
	
	public void reset() {
		this.fireTicks = 0;
		this.shotTurn = 0;
		this.phaseTicks = PHASE_LIMIT;
	}
	
	public void easy_boomerangs() {
		if(this.fireTicks <= 0) {
			for(int i = 0; i < 9; i++) {
				new FFBoomerang((int)this.x, (int)this.y, i * 40 + this.shotTurn);
			}
			this.shotTurn += Controller.random.nextInt(10, 31);
			this.shotTurn = this.shotTurn % 360;
			this.fireTicks = 200;
		}
		else {
			this.fireTicks--;
		}
	}
	
	public void easy_arrows() {
		if(this.fireTicks <= 0) {
			new FFArrow((int)this.x, (int)this.y, 0 + shotTurn, true);
			new FFArrow((int)this.x, (int)this.y, 180 + shotTurn, true);
			new FFArrow((int)this.x, (int)this.y, 90 + shotTurn, false);
			new FFArrow((int)this.x, (int)this.y, 270 + shotTurn, false);
			this.shotTurn += Controller.random.nextInt(30, 70);
			this.shotTurn = this.shotTurn % 360;
			this.fireTicks = 45;
		}
		else {
			this.fireTicks--;
		}
	}
	
	public void medium_shotguns() {
		if(this.fireTicks <= 0) {
			this.fireTicks = 800;
		}
		else {
			if(this.fireTicks % 100 == 30) {
				for(int i = 0; i < 14; i++) {
					new FFPellet((int)this.x, (int)this.y, 30  * i+ shotTurn);
				}
				this.shotTurn += Controller.random.nextInt(10, 20);
				this.shotTurn = this.shotTurn % 360;
			}
			if(this.fireTicks % 400 == 0) {
				int angle = (int)this.getAngle((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY());
				for(int i = -2; i < 3; i++) {
					new FFShotgun((int)this.x, (int)this.y, angle + 28 * i);
				}
			}
			this.fireTicks--;
		}
	}
	
	public void medium_comets() {
		if(this.fireTicks <= 0) {
			this.fireTicks = 600;
		}
		else {
			for(int i = 0; i < 5; i++) {
				if(this.fireTicks % 300 == 50 * i) {
					double a = Math.toRadians(Controller.random.nextInt(72 * i, 108 + 72 * i));
					int r = Controller.random.nextInt(100, 400);
					new FFComet((int)(Math.cos(a) * r + this.x), (int)(Math.sin(a) * r + this.y));
				}
			}
			this.fireTicks--;
		}
	}
	
	public void advanced_bubbles() {
		if(this.fireTicks <= 0) {
			this.fireTicks = 900;
		}
		else {
			if(this.fireTicks % 60 == 0) {
				for(int i = 0; i < 3; i++) {
					new FFBubbleSpawn((int)(this.x + 400 * Math.cos(Math.toRadians(shotTurn + 120 * i))),
							(int)(this.y + 400 * Math.sin(Math.toRadians(shotTurn + 120 * i))),
							shotTurn + 120 * i);
				}
				shotTurn += 12;
			}
			if(this.fireTicks % 180 == 0) {
				for(int i = 0; i < 3; i++) {
					new FFWave((int)this.x, (int)this.y, -shotTurn + 120 * i);
					new FFWave((int)this.x, (int)this.y, -shotTurn + 18 + 120 * i);
					new FFWave((int)this.x, (int)this.y, -shotTurn - 18 + 120 * i);
				}
			}
			this.fireTicks--;
		}
	}

	public void advanced_spears() {
		if(this.fireTicks <= 0) {
			this.fireTicks = 720;
		}
		else {
			for(int i = 0; i < 8; i++) {
				if(this.fireTicks % 120 == 15 * i) {
					double a = Math.toRadians(Controller.random.nextInt(45 * i, 60 + 45 * i));
					int r = Controller.random.nextInt(100, 450);
					new FFWaterbeam((int)(Math.cos(a) * r + this.x), (int)(Math.sin(a) * r + this.y));
				}
			}
			if(this.fireTicks % 90 == 0) {
				int angle = (int)this.getAngle((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY());
				int offset = Controller.random.nextInt(-10, 11);
				for(int i = 0; i < 6; i++) {
					new FFSpear((int)(30 * Math.cos(Math.toRadians(angle + 60 * i + offset + shotTurn)) + this.x), 
							(int)(30 * Math.sin(Math.toRadians(angle + 60 * i + offset + shotTurn)) + this.y), 
							angle + 60 * i + offset + shotTurn);
				}
				this.shotTurn += 30;
				this.shotTurn = this.shotTurn % 360;
			}
			this.fireTicks--;
		}
	}
	
	@Override
	public void move() {
		if(this.active) {
			if(this.shieldTicks > 0) {
				this.shield.activate();
				this.shieldTicks -= 1;
			}
			else if(this.shieldTicks == 0) {
				this.shieldTicks -= 1;
				reset();
				if(this.parity == -1) {
					this.attack = Controller.random.nextInt(2 * this.phase, 2 * this.phase + 2);
					this.parity = this.attack % 2;
				}
				else if(this.parity == 0) {
					this.attack = 2 * this.phase + 1;
					this.parity = 1;
				}
				else {
					this.attack = 2 * this.phase;
					this.parity = 0;
				}
			}
			else {
				if(this.phaseTicks > 0) {
					this.phaseTicks -= 1;
				}
				else {
					this.shieldTicks = 250;
					return;
				}
				this.shield.deactivate();
				if(this.phase == 0) {
					if(this.attack == 0) {this.easy_boomerangs();}
					else {this.easy_arrows();}
					
					if(this.currentHealth < 0.7 * this.maxHealth) {
						this.phase = 1;
						this.shieldTicks = 250; 
					}
				}
				else if(this.phase == 1) {
					if(this.attack == 2) {this.medium_comets();}
					else {this.medium_shotguns();}
					
					if(this.currentHealth < 0.4 * this.maxHealth) {
						this.phase = 2;
						this.shieldTicks = 250;
					}
				}
				else {
					if(this.attack == 4) {this.advanced_spears();}
					else {this.advanced_bubbles();}
				}
			}
		}
		else {
			if(this.currentHealth < this.maxHealth) {
				this.active = true;
				this.begin();
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
		this.animate++;
		if(this.phase == 0 || this.phase == -1) {
			return Target.IMGS_FORBIDDENFOUNTAIN.get(0);
		}
		else if(this.phase == 1) {
			if(this.animate >= 8 * ANIMATE_INTERVAL) {
				this.animate = 0;
			}
			for(int i = 1; i < 9; i++) {
				if(this.animate <= ANIMATE_INTERVAL * i) {
					if(i < 5) {
						return Target.IMGS_FORBIDDENFOUNTAIN.get(i - 1);
					}
					else {
						return Target.IMGS_FORBIDDENFOUNTAIN.get(8 - i);
					}
				}
			}
		}
		else {
			if(this.animate >= 4 * ANIMATE_INTERVAL) {
				this.animate = 0;
			}
			
			for(int i = 1; i < 5; i++) {
				if(this.animate <= ANIMATE_INTERVAL * i) {
					return Target.IMGS_FORBIDDENFOUNTAIN.get(i + 3);	
				}
			}
		}
		return Target.IMGS_FORBIDDENFOUNTAIN.get(0);
	}
	
	@Override
	public boolean collision(Projectile p) {
		return super.collision(p, 0, -20, 0.15);
	}
	
	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		super.draw(g, shiftX, shiftY + 20, i);
		if(Controller.chr.getX() > this.x - this.VIEW_RADIUS && Controller.chr.getX() < this.x + this.VIEW_RADIUS
				&& Controller.chr.getY() > this.y - this.VIEW_RADIUS && Controller.chr.getY() < this.y + this.VIEW_RADIUS) {
			if(this.barFrames < 100) {
				this.barFrames += 1;
			}
			this.drawBar(g, i);
		}
		else {
			this.barFrames = 0;
		}
	}
}
