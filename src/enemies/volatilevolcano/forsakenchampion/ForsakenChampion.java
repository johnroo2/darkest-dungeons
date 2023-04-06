package enemies.volatilevolcano.forsakenchampion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import control.Controller;
import equipment.ability.Ability;
import equipment.weapon.Weapon;
import portals.CorruptedCatacombsPortal;
import portals.VolatileVolcanoPortal;
import project.Boss;
import project.LootRoll;
import project.Projectile;
import project.Target;
import project.Tile;
import statuseffects.EnemySlow;

public class ForsakenChampion extends Boss{
	final static int HEALTH = 32000;
	final static int DEF = 20;
	final static int SIZE = 100;
	
	final int VIEW_RADIUS = 1000;
	final int ATTRACT_RADIUS = 350;
	final int ANIMATE_INTERVAL = 30;
	final static int XP = 750;
	
	final static int WHIP_DMG = 20;
	final static int WHIP_RANGE = 70;
	final static int WHIP_PERIOD = 30;
	final static double WHIP_AMP = 4.5;
	final static int WHIP_STALL = 125;
	
	final static int MARKER_SPEED = 5;
	final static int MARKER_RANGE = 30;
	
	final static int BOLT_DMG = 110;
	final static int BOLT_RANGE = 200;
	final static double BOLT_SPEED = 3.2;
	
	final static int SLASH_DMG = 35;
	final static int SLASH_RANGE = 300;
	final static double SLASH_SPEED = 1.8;
	
	final static int FIREBALL_DMG = 100;
	final static int FIREBALL_RANGE = 300;
	final static double FIREBALL_SPEED = 2.5;
	
	final static int BOUNCE_DMG = 90;
	final static double BOUNCE_SPEED = 1;
	final static double BOUNCE_ACCEL = 0.04;
	final static int BOUNCE_DELAY = 40;
	final static int BOUNCE_RANGE = 200;
	
	final static int PALEARROW_DMG = 100;
	final static double PALEARROW_SPEED = 4.5;
	final static int PALEARROW_RANGE = 750;
	
	final static int FIREARROW_DMG = 80;
	final static double FIREARROW_SPEED = 2.8;
	final static int FIREARROW_RANGE = 750;
	
	final static int REFLECTARROW_DMG = 125;
	final static double REFLECTARROW_SPEED = 4;
	final static int REFLECTARROW_RANGE = 1000;
	
	final static int SWORD_LIFETIME = 900;
	final static int SWORD_FIRE = 300;
	final static double SWORD_SPEED = 1.35;
	final static int SWORD_RANGE = 400;
	final static int SWORD_DMG = 100;
	
	final static int SPEAR_LIFETIME = 180;
	final static int SPEAR_FIRE = 12;
	final static int SPEAR_DMG = 45;
	final static int SPEAR_CASTPLUS = 20;
	
	final static int KNIFE_LIFETIME = 300;
	final static int KNIFE_FIRE = 150;
	final static double KNIFE_SPEED = 2.5;
	final static double KNIFE_ROTVEL = 0.25;
	final static int KNIFE_RANGE = 180;
	final static int KNIFE_DMG = 90;

	final static int PHASE_LIMIT = 2000;
	
	private int fireTicks, shieldTicks, shotTurn;
	private int shotParity = 1;
	private int phaseTicks = PHASE_LIMIT;
	
	//control: spin (loops)
	private boolean spinning = false;
	private boolean bouncing = false;
	private int bounceAngle = 0;
	private int spinTicks = 0;
	private final static int SPIN_FRAMES = 12;
	
	//control: glow (loops)
	private boolean glowing = false;
	private int glowTicks = 0;
	private final static int GLOW_FRAMES = 12;
	
	//control: slash
	private int slashTicks = 0;
	private int slashParity = 0;
	private final static int SLASH_FRAMES = 18;
	
	//control: shoot
	private int shootTicks = 0;
	private int shootParity = 0;
	private final static int SHOOT_FRAMES = 5;
	
	//control: walk (loops)
	private boolean walking = false;
	private boolean transport = false;
	private int walkTicks = 0;
	private final static int WALK_FRAMES = 20;
	
	//control: stand
	//none lmao

	private int alt = 1;
	private int scope;
	private double speed;
	private int[] flurryAngles = null;
	private int flurryWalk = 0;
	
	private int cycle = Controller.random.nextInt(1, 4);
	private int attack = 0;

	public ForsakenChampion(int x, int y) {
		super(x, y, HEALTH, DEF, "Forsaken Champion");
		this.barName = "Forsaken Champion";
		this.shield.deactivate();
		this.size = SIZE;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
				{lr("w5", 1, 15), lr("w6", 5, 15), lr("w7", 2, 15)},
				{lr("w5", 2, 15), lr("w6", 2, 15), lr("w7", 3, 15)},
				{lr("ab3", 8, 25), lr("ab4", 4, 25)},
				{lr("ab3", 6, 25), lr("ab4", 4, 25)},
				{lr("ar5", 2, 12), lr("ar6", 3, 12), lr("ar7", 2, 12)},
				{lr("ar5", 3, 18), lr("ar6", 4, 18), lr("ar7", 3, 18)},
				{lr("r2", 8, 12)},
				{lr(Weapon.Crestfeller_Axe, 1, 7), lr(Weapon.Trecharous_Crossbow, 1, 7), 
					lr(Ability.Oathbreaker_Quiver, 1, 7), lr(Ability.Champions_Helm, 1, 7)}
				};
	}
	
	//immune to paralysis
	@Override 
	public boolean ParalyzeImmune() {return true;}

	@Override
	public void begin() {
		this.shield.activate();
		this.shieldTicks = 250;
		this.phase = 0;
		this.area.setLine(0, 28, 1, 1, Tile.VOLCANOLAVA);
		this.area.setLine(1, 28, 1, 1, Tile.VOLCANOLAVA);
		this.area.setLine(0, 28, 1, 28, Tile.VOLCANOLAVA);
		this.area.setLine(1, 28, 28, 1, Tile.VOLCANOLAVA);
		
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
		this.area.setBox(28, 28, 1, 1, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
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
		
		new VolatileVolcanoPortal(this.area.getX() + 480, this.area.getY() + 600, true);
		new CorruptedCatacombsPortal(this.area.getX() + 720, this.area.getY() + 600, false);
	}
	
	public void slash() {
		this.slashTicks = SLASH_FRAMES;
		this.slashParity = -this.slashParity + 1;
	}
	public void shoot() {
		this.shootTicks = SHOOT_FRAMES;
		this.shootParity = -this.shootParity + 1;
	}
	public void walk(int x, int y, boolean raise, boolean transport, double speed) {
		this.walking = true;
		this.go(x, y, speed);
		if(raise) {
			this.shield.activate();
		}
		this.transport = transport;
		this.speed = speed;
	}
	public void bounce(double angle, double speed) {
		this.bouncing = true;
		this.spinning = true;
		
		this.xVel = speed * Math.cos(Math.toRadians(angle));
		this.yVel = speed * Math.sin(Math.toRadians(angle));
		this.speed = speed;
	}
	public void unbounce() {
		this.bouncing = false;
		this.spinning = false;
		
		this.xVel = 0;
		this.yVel = 0;
		this.speed = 0;
	}
	
	@Override
	public boolean inWall() {
		return super.inWall(0);
	}
	
	public void reset() {
		this.fireTicks = 0;
		this.shotTurn = 0;
		this.phaseTicks = PHASE_LIMIT;
		this.shotParity *= -1;
		this.flurryAngles = null;
		this.flurryWalk = 0;
		this.glowing = false;
		this.walking = false;
		
		this.bounceAngle = Controller.random.nextInt(105, 131);
	}
	
	public void axe_whips() {
		if(this.fireTicks <= 0) {
			this.fireTicks = 2000;
		}
		else {
			if(this.fireTicks % 125 == 0 || this.fireTicks % 125 == 119 || this.fireTicks % 125 == 113 || this.fireTicks % 125 == 107) {
				for(int k = 0; k < 3; k++) {
					new FCMarker((int)this.x, (int)this.y, shotTurn + k * 120);
				}
			}

			if(this.fireTicks % 125 == 75) {
				for(int k = 0; k < 3; k++) {
					for(double i = 1.05; i <= 7.55; i += 0.5) {
						new FCWhip((int)this.x, (int)this.y, shotTurn + k * 120, i, 250.0/(i+3), alt);
						alt *= -1;
					}
				}
				shotTurn += Controller.random.nextInt(30, 46) * shotParity;
				shotTurn = shotTurn % 360;
				this.slash();
			}
			if(this.fireTicks % 400 == 0) {
				for(int i = 0; i < 12; i++) {
					//30 -> 12, 45 -> 18
					new FCBolt((int)this.x, (int)this.y, (double)shotTurn/2.5 + i * 30);
				}
				this.slash();
			}
			this.fireTicks -= 1;
		}
	}
	
	public void axe_slashes() {
		if(this.fireTicks <= 0) {
			this.fireTicks = 840;
		}
		else {
			if(this.fireTicks % 28 == 0) {
				if(this.fireTicks > 50) {
					for(int i = 0; i < 4; i++) {
						new FCSlash((int)this.x, (int)this.y, 90 * i + shotTurn);
					}
				}
				shotTurn += Controller.random.nextInt(8, 11) * shotParity;
				shotTurn = shotTurn % 360;
				this.slash();
			}
			
			if(this.fireTicks % 210 == 0) {
				for(int i = 0; i < 10; i++) {
					new FCFireball((int)this.x, (int)this.y, 36 * i + (double)shotTurn/2.0);
				}
			}
			this.fireTicks -= 1;
		}
	}
	
	public void axe_bounces() {
		if(!this.bouncing) {
			this.bounce(this.bounceAngle, 3.15);
		}
		if(this.fireTicks <= 0) {
			this.fireTicks = 80;
		}
		else {
			if(this.fireTicks == 1) {
				for(int i = 0; i < 18; i++) {
					new FCBounce((int)this.x, (int)this.y, i * 20 + shotTurn);
				}
				shotTurn += Controller.random.nextInt(8, 13) * shotParity;
				shotTurn = shotTurn % 360;
			}
			this.fireTicks -= 1;
		}
	}
	
	public void bow_volley() {
		if(!this.walking) {
			int xOffset = Controller.random.nextInt(-150, 151);
			int yOffset = Controller.random.nextInt(-150, 151);
			this.walk(Controller.chr.getX() + xOffset, Controller.chr.getY() + yOffset, false, false, 1.25);
		}
		if(this.fireTicks <= 0) {
			this.fireTicks = 500;
		}
		else {
			if(this.fireTicks % 20 == 0) {
				int xOffset = Controller.random.nextInt(-150, 151);
				int yOffset = Controller.random.nextInt(-150, 151);
				this.walk(Controller.chr.getX() + xOffset, Controller.chr.getY() + yOffset, false, false, 1.25);
			}
			for(int i = 250; i <= 450; i += 8) {
				if(this.fireTicks == i) {
					new FCPaleArrow((int)this.x, (int)this.y, shotTurn);
					shotTurn += Controller.random.nextInt(40, 60) * this.shotParity;
					this.shoot();
				}
			}
			if(this.fireTicks == 100) {
				for(int i = 0; i < 12; i++) {
					new FCPaleArrow((int)this.x, (int)this.y, 30 * i);
					this.shoot();
				}
			}
			this.fireTicks -= 1;
		}
	}
	
	public void bow_flurry() {
		int relX = (int) (this.x - this.area.getX());
		int relY = (int) (this.y - this.area.getY());
		if((relX == 600 && (relY == 120 || relY == 1080)) || (relY == 600 && (relX == 120 || relX == 1080)) && this.flurryWalk == 1){
			this.flurryWalk = 2;
		}
		if(this.flurryAngles == null) {
			this.flurryAngles = new int[5];
			for(int i = 0; i < 5; i++) {
				this.flurryAngles[i] = Controller.random.nextInt(this.scope - 110 + 44 * i, this.scope - 65 + 44 * i);
			}
		}
		if(this.flurryWalk == 2) {
			if(this.fireTicks == 0) {
				this.fireTicks = 1200;
			}
			else {
				if(this.fireTicks % 120 == 0) {
					new FCFireArrow((int)this.x, (int)this.y, Controller.random.nextInt(this.scope - 110, this.scope - 6));
					this.shoot();
				}
				if(this.fireTicks % 120 == 40) {
					new FCFireArrow((int)this.x, (int)this.y, Controller.random.nextInt(this.scope + 5, this.scope + 111));
					this.shoot();
				}
				if(this.fireTicks % 120 == 80) {
					int offset = Controller.random.nextInt(-5, 6);
					double[] tracking = this.relativeTrack(0.7);
					double angle = this.getAngle((int)this.x, (int)this.y, (int)(Controller.chr.getX() + tracking[0]), (int)(Controller.chr.getY() + tracking[1]));
					new FCFireArrow((int)this.x, (int)this.y, angle + offset);
					this.shoot();
				}
				if(this.fireTicks % 150 == 80 || this.fireTicks % 150 == 74 || this.fireTicks % 150 == 68 || this.fireTicks % 150 == 62) {
					for(int i = 0; i < this.flurryAngles.length; i++) {
						new FCMarker((int)this.x, (int)this.y, this.flurryAngles[i]);
					}
				}
				if(this.fireTicks % 150 == 30) {
					for(int i = 0; i < this.flurryAngles.length; i++) {
						new FCPaleArrow((int)this.x, (int)this.y, this.flurryAngles[i]);
					}
					this.flurryAngles = null;
					this.shoot();
				}
				this.fireTicks -= 1;
			}
		}
		else {
			if(!this.walking) {
				this.flurryWalk = 1;
				if(Controller.coinflip()) { //up, down
					if(Controller.coinflip()) {
						this.walk(this.area.getX() + 600, this.area.getY() + 120, true, false, 3.5);
						this.scope = 90;
					}
					else {
						this.walk(this.area.getX() + 600, this.area.getY() + 1080, true, false, 3.5);
						this.scope = 270;
					}
				}
				else { //left, right
					if(Controller.coinflip()) {
						this.walk(this.area.getX() + 120, this.area.getY() + 600, true, false, 3.5);
						this.scope = 0;
					}
					else {
						this.walk(this.area.getX() + 1080, this.area.getY() + 600, true, false, 3.5);
						this.scope = 180;
					}
				}
			}
		}
	}
	
	public void bow_reflects() {
		if(!this.walking) {
			int xOffset = Controller.random.nextInt(-150, 151);
			int yOffset = Controller.random.nextInt(-150, 151);
			this.walk(Controller.chr.getX() + xOffset, Controller.chr.getY() + yOffset, false, false, 1.15);
		}
		if(this.fireTicks <= 0) {
			this.fireTicks = 400;
		}
		else {
			if(this.fireTicks % 20 == 0) {
				int xOffset = Controller.random.nextInt(-150, 151);
				int yOffset = Controller.random.nextInt(-150, 151);
				this.walk(Controller.chr.getX() + xOffset, Controller.chr.getY() + yOffset, false, false, 1.15);
			}
			if(this.fireTicks == 300) {
				for(int i = 0; i < 12; i++) {
					new FCFireArrow((int)this.x, (int)this.y, 30 * i + shotTurn);
				}
				shotTurn += Controller.random.nextInt(10, 21) * this.shotParity;
				shotTurn = shotTurn % 360;
				this.shoot();
			}
			if(this.fireTicks == 100) {
				int offset = Controller.random.nextInt(-5, 6);
				double[] tracking = this.relativeTrack(0.7);
				double angle = this.getAngle((int)this.x, (int)this.y, (int)(Controller.chr.getX() + tracking[0]), (int)(Controller.chr.getY() + tracking[1]));
				for(int i = 0; i < 4; i++) {
					new FCReflectArrow((int)this.x, (int)this.y, angle + offset + 90 * i);
				}
				this.shoot();
			}
			this.fireTicks -= 1;
		}
	}
	
	public void summons_sword() {
		this.glowing = true;
		if(this.fireTicks <= 0) {
			this.fireTicks = 1200;
		}
		else {
			if(this.fireTicks == 1150) {
				for(int i = 0; i < 4; i++) {
					new FCSwordSummon((int)(this.x + 250 * Math.cos(Math.toRadians(90 * i + shotTurn))), (int)(this.y + 180 *Math.sin(Math.toRadians(90 * i + shotTurn))));
				}
			}
			if(this.fireTicks == 750) {
				for(int i = 0; i < 4; i++) {
					new FCSwordSummon((int)(this.x + 385 * Math.cos(Math.toRadians(90 * i + shotTurn))), (int)(this.y + 320 *Math.sin(Math.toRadians(90 * i + shotTurn))));
				}
			}
			if(this.fireTicks == 350) {
				for(int i = 0; i < 4; i++) {
					new FCSwordSummon((int)(this.x + 520 * Math.cos(Math.toRadians(90 * i + shotTurn))), (int)(this.y + 460 *Math.sin(Math.toRadians(90 * i + shotTurn))));
				}
			}
			
			if(this.fireTicks % 400 == 350) {
				shotTurn += 45;
				shotTurn = shotTurn % 360;
			}
			this.fireTicks -= 1;
		}
	}
	
	public void summons_spear() {
		this.glowing = true;
		if(this.fireTicks <= 0) {
			this.fireTicks = 800;
		}
		else {
			if(this.fireTicks % 400 == 300) {
				for(int i = 0; i < 9; i++) {
					new FCFireArrow((int)this.x, (int)this.y, (double)this.shotTurn/7.5 + 40 * i);
				}
				this.shoot();
			}
			if(this.fireTicks % 40 == 0) {
				int rad = Controller.random.nextInt(200, 401);
				new FCSpearSummon((int)(this.x + rad * Math.cos(Math.toRadians(shotTurn))), (int)(this.y + rad *Math.sin(Math.toRadians(shotTurn))));
				shotTurn += Controller.random.nextInt(70, 111) * this.shotParity;
				shotTurn = shotTurn % 360;
			}
			this.fireTicks -= 1;
		}
	}
	
	public void summons_knife() {
		this.glowing = true;
		if(this.fireTicks <= 0) {
			this.fireTicks = 600;
		}
		else {
			if(this.fireTicks % 60 == 0) {
				int rad = Controller.random.nextInt(125, 451);
				new FCKnifeSummon((int)(this.x + rad * Math.cos(Math.toRadians(shotTurn))), (int)(this.y + rad *Math.sin(Math.toRadians(shotTurn))));
				shotTurn += Controller.random.nextInt(70, 111) * this.shotParity;
				shotTurn = shotTurn % 360;
			}
			this.fireTicks -= 1;
		}
	}

	@Override
	public void move() {
		if(this.active) {
			//walking over bouncing
			if(this.walking) {
				approach();
				if((this.transport && Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= 2*speed*speed)
						||(!this.transport && Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= speed*speed)) {
					this.x = this.targetX;
					this.y = this.targetY;
					this.walking = false;
					this.transport = false;
					this.shield.deactivate();
					
					if(this.phase == 1) {
						this.area.setBox(28, 28, 1, 1, Tile.VOLCANOLAVA, Tile.VOLCANOLAVA);
						this.area.setCircle(2, 2, 13, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
						
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
				}
			}
			else if(this.bouncing) {
				approach();
			}
			
			if(this.shieldTicks > 0) {
				this.shield.activate();
				this.shieldTicks -= 1;
			}
			else if(this.shieldTicks == 0) {
				this.shieldTicks -= 1;
				if(this.attack == 0 || this.attack == 2) { //resets, summons
					this.walk(this.area.getX() + 600, this.area.getY() + 600, true, true, 2.5);
				}
			}
			else {
				if(!this.transport) {
					if(this.phaseTicks > 0) {
						this.phaseTicks -= 1;
					}
					else {
						if(this.attack == 2) {
							this.attack = 0;
							int nextCycle;
							do {
								nextCycle = Controller.random.nextInt(1, 4);
							}
							while(nextCycle == this.cycle);
							this.cycle = nextCycle;
							this.shieldTicks = 500;
							this.unbounce();
							reset();
							return;
						}
						else {
							this.attack++;
							this.shieldTicks = 250;
							this.unbounce();
							reset();
							return;
						}
					}
					this.shield.deactivate();
					if(this.phase == 0) {
						this.reset();
						this.walk(this.area.getX() + 600, this.area.getY() + 600, true, true, 2.5);
						this.phase = 1;
					}
					if(this.phase == 1 || this.phase == 2) {
						if(this.cycle == 1) {
							if(this.attack == 0) {
								this.axe_whips();
							}
							else if(this.attack == 1) {
								this.bow_volley();
							}
							else if(this.attack == 2) {
								this.summons_sword();
							}
						}
						else if(this.cycle == 2) {
							if(this.attack == 0) {
								this.axe_slashes();
							}
							else if(this.attack == 1) {
								this.bow_reflects();
							}
							else if(this.attack == 2) {
								this.summons_spear();
							}
						}
						else if(this.cycle == 3) {
							if(this.attack == 0) {
								this.axe_bounces();
							}
							else if(this.attack == 1) {
								this.bow_flurry();
							}
							else if(this.attack == 2) {
								this.summons_knife();
							}
						}
					}
					if(this.phase == 1) {
						if(this.currentHealth < (double)this.maxHealth * 0.5) {
							this.phase = 2;
							this.area.setBox(26, 26, 2, 2, Tile.VOLCANOLAVA, Tile.VOLCANOLAVA);
							this.area.setCircle(4, 4, 11, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
							
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
					}
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
		ArrayList<BufferedImage> chosenList;
		if(this.phase < 2) {
			chosenList = Target.IMGS_FORSAKENCHAMPION;
		}
		else {
			chosenList = Target.IMGS_FORSAKENCHAMPIONII;
		}
		
		//order: 
		//spin, slash, shoot, glow, walk, stand
		if(this.spinTicks > 0) {this.spinTicks--;}
		if(this.glowTicks > 0) {this.glowTicks--;}
		if(this.slashTicks > 0) {this.slashTicks--;}
		if(this.shootTicks > 0) {this.shootTicks--;}
		if(this.walkTicks > 0) {this.walkTicks--;}
		
		if(this.spinTicks <= 0 && this.spinning) {this.spinTicks = SPIN_FRAMES * 4;}
		if(this.glowTicks <= 0 && this.glowing) {this.glowTicks = GLOW_FRAMES * 4;}
		if(this.walkTicks <= 0 && this.walking) {this.walkTicks = WALK_FRAMES * 4;}
		
		if(this.spinning) {
			for(int i = 3; i >= 0; i--) {
				if(SPIN_FRAMES * i < this.spinTicks) {
					//6, 7, 8, 9
					return chosenList.get(9 - i);
				}
			}
		}
		else if(this.slashTicks > 0) {
			//4, 5
			return chosenList.get(4 + this.slashParity);
		}
		else if(this.shootTicks > 0) {
			//14, 15
			return chosenList.get(14 + this.shootParity);
		}
		else if(this.glowing) {
			for(int i = 3; i >= 0; i--) {
				if(GLOW_FRAMES * i < this.glowTicks) {
					//10, 11, 12, 13
					return chosenList.get(13 - i);
				}
			}
		}
		else if(this.walking) {
			for(int i = 3; i >= 0; i--) {
				if(WALK_FRAMES * i < this.walkTicks) {
					//0, 1, 2, 3
					return chosenList.get(3 - i);
				}
			}
		}
		
		return chosenList.get(0);
	}
	
	@Override
	public void approach() {
		double xv = xVel;
		double yv = yVel;
		if(this.effects.contains(new EnemySlow())) {
			xv *= 0.4;
			yv *= 0.4;
		}
		
		this.x += xv;
		if(inWall()) {
			this.x -= xv; 
			if(this.bouncing) {
				this.xVel *= -1;
			}
			else {
				if(yv != 0) {
					this.y += yv/2;
					if(inWall()) {
						this.y -= yv/2;
					}
				}
			}
		}
		this.y += yv;
		if(inWall()) {
			this.y -= yv; 
			if(this.bouncing) {
				this.yVel *= -1;
			}
			else {
				if(xv != 0) {
					this.x += xv/2;
					if(inWall()) {
						this.x -= xv/2;
					}
				}
			}
		}
	}
	
	@Override
	public boolean collision(Projectile p) {
		return super.collision(p, 0, 0, 0.15);
	}
	
	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		super.draw(g, shiftX, shiftY, i);
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
