package enemies.corruptedcatacombs.unhallowedrevenant;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.ListIterator;

import control.Controller;
import control.Driver;
import enemies.corruptedcatacombs.unhallowedrevenantshade.UnhallowedRevenantShade;
import enemies.corruptedcatacombs.unhallowedrevenantshade.UnhallowedRevenantShadeShot;
import equipment.ability.Ability;
import equipment.weapon.Weapon;
import portals.CorruptedCatacombsPortal;
import portals.VolatileVolcanoPortal;
import project.Boss;
import project.LootRoll;
import project.Projectile;
import project.Target;
import project.Tile;

public class UnhallowedRevenant extends Boss{
	final static int HEALTH = 24000;
	final static int DEF = 10;
	final static int SIZE = 80;
	
	final int VIEW_RADIUS = 1000;
	final int ATTRACT_RADIUS = 350;
	final int ANIMATE_INTERVAL = 30;
	final static int XP = 450;
	
	final static int SPEAR_DMG = 100;
	final static double SPEAR_SPEED = 0.7;
	final static double SPEAR_ACCEL = 0.05;
	final static int SPEAR_RANGE = 160;
	
	final static int WAVE_DMG = 110;
	final static double WAVE_SPEED = 1.4;
	final static int WAVE_RANGE = 250;
	
	final static int FIREBLAST_DMG = 140;
	final static double FIREBLAST_SPEED = 1.35;
	final static int FIREBLAST_RANGE = 800;
	
	private int shieldTicks;
	private double speed;
	private int shotTurn = Controller.random.nextInt(0, 360); 
	
	private UnhallowedRevenantShade[] shades = new UnhallowedRevenantShade[8];
	
	//control: walk
	private boolean walking = false;
	private boolean transport = false;
	private int walkTicks = 0;
	private static int WALK_FRAMES = 22;
	
	//control: cross
	private int crossTicks = 0;
	private static int CROSS_FRAMES = 12;
	
	//control: swap:
	private int swapMode = 0; //0: cross, 1: flame, 2: cross
	private int swapTicks = 0;
	private static int SWAP_FRAMES = 12;
	
	//control: flames, glow
	private boolean flaming = false;
	private int flameTicks = 0;
	private static int FLAME_FRAMES = 15;
	
	private final static int MIN_RADIUS = 240;
	private final static int MAX_RADIUS = 320;
	
	private int fireTicks;
	private int walkTurn = Controller.random.nextInt(0, 360);
	private int walkRadius = Controller.random.nextInt(MIN_RADIUS, MAX_RADIUS);

	public UnhallowedRevenant(int x, int y) {
		super(x, y, HEALTH, DEF, "Unhallowed Revenant");
		this.barName = "Unhallowed Revenant";
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
		this.area.setLine(0, 22, 1, 1, Tile.CORRUPTEDICE);
		this.area.setLine(1, 22, 1, 1, Tile.CORRUPTEDICE);
		this.area.setLine(0, 22, 1, 22, Tile.CORRUPTEDICE);
		this.area.setLine(1, 22, 22, 1, Tile.CORRUPTEDICE);
		
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
		this.area.setBox(22, 22, 1, 1, Tile.ICEFLOOR, Tile.ICEFLOOR);
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
	
	public void cross() {
		this.crossTicks = CROSS_FRAMES * 3;
	}
	
	public void swap() {
		this.swapTicks = SWAP_FRAMES * 5;
		this.swapMode++; 
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
	
	public void rotate() {
		this.walkRadius = Controller.random.nextInt(MIN_RADIUS, MAX_RADIUS);
		walk((int)(this.area.getX() + 480 + this.walkRadius * Math.cos(Math.toRadians(this.walkTurn))), 
				(int) (this.area.getY() + 480 + this.walkRadius * Math.sin(Math.toRadians(this.walkTurn))),
				false, false, 1.55); 
		if(this.phase == 1) {
			this.walkTurn += Controller.random.nextInt(25, 35);
		}
		else if(this.phase == 6) {
			this.walkTurn -= Controller.random.nextInt(25, 35);
		}
	}

	@Override
	public void move() {
		if(this.active) {
			if(this.walking) {
				approach();
				if((this.transport && Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= 2*speed*speed)
						||(!this.transport && Math.sqrt((this.x-this.targetX) * (this.x-this.targetX) + (this.y-this.targetY) * (this.y-this.targetY)) <= speed*speed)) {
					this.x = this.targetX;
					this.y = this.targetY;
					this.walking = false;
					this.transport = false;
					this.shield.deactivate();	
					
					if(this.phase == 2) {
						swap();
					}
				}
			}
			if(this.shieldTicks > 0) {
				this.shield.activate();
				this.shieldTicks -= 1;
			}
			else if(this.shieldTicks == 0) {
				this.shieldTicks -= 1;
				if(this.phase == 0) {
					this.area.setBox(22, 22, 1, 1, Tile.CORRUPTEDICE, Tile.CORRUPTEDICE);
					this.area.setCircle(3, 3, 9, Tile.ICEFLOOR, Tile.ICEFLOOR);
					
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
					this.rotate();
					for(int i = 0; i < 8; i++) {shades[i] = new UnhallowedRevenantShade(this.area.getX() + 480, this.area.getY() + 480, 45 * i);}
					this.phase = 1;
				}
			}
			else {
				if(!transport) {
					this.shield.deactivate();
					if(this.phase == 1) {
						if(this.inRadius(this.targetX, this.targetY, (int)this.speed)) {
							this.rotate();
						}
						if(this.currentHealth < this.maxHealth * 2 / 3) {
							this.phase = 2;
							//remove all shots
							ListIterator<Projectile> es = Controller.enemyShots.listIterator();
							while(es.hasNext()) {
								if(es.next() instanceof UnhallowedRevenantShadeShot) {
									es.remove();
								}
							}
							walk(this.area.getX() + 480, this.area.getY() + 480, true, true, 1.85);
						}
						
						shadesrun();
						if(this.fireTicks > 0) {
							this.fireTicks -= 1;
							if(this.fireTicks == 45) {
								for(int i = 0; i < 12; i++) {
									new URWave((int)this.x, (int)this.y, 30 * i);
								}
								cross();
							}
						}
						else {
							this.fireTicks = 90;
						}
					}
					else if(this.phase == 2) {
						
					}
					else if(this.phase == 3) {
						if(this.currentHealth < this.maxHealth / 3) {
							this.shieldTicks = 200;
							this.phase = 4;
						}
						if(this.fireTicks > 0) {
							this.fireTicks -= 1;
							if(this.fireTicks == 15) {
								int shots = Controller.random.nextInt(4, 8);
								for(int i = 0; i < shots; i++) {
									new URFireblast((int)this.x, (int)this.y, (360 * i)/shots + shotTurn);
								}
								shotTurn += Controller.random.nextInt(30, 50);
								shotTurn = shotTurn % 360;
							}
						}
						else {
							this.fireTicks = 28;
						}
					}
					else if(this.phase == 4) {
						swap();
						this.phase = 5;
					}
					else if(this.phase == 5) {
						this.shield.activate();
					}
					else if(this.phase == 6) {
						shadesrun();
						if(this.fireTicks > 0) {
							this.fireTicks -= 1;
							if(this.fireTicks == 40) {
								int[] aim = aimcenter(Controller.chr.getX(), Controller.chr.getY(), 0.7);
								int offset = Controller.random.nextInt(-5, 6);
								new URSpear((int)this.x, (int)this.y, aim[0], aim[1], -20+ offset, -30);
								cross();
							}
							else if(this.fireTicks == 45) {
								int[] aim = aimcenter(Controller.chr.getX(), Controller.chr.getY(), 0.7);
								int offset = Controller.random.nextInt(-5, 6);
								new URSpear((int)this.x, (int)this.y, aim[0], aim[1], offset, 0);
							}
							else if(this.fireTicks == 50) {
								int[] aim = aimcenter(Controller.chr.getX(), Controller.chr.getY(), 0.7);
								int offset = Controller.random.nextInt(-5, 6);
								new URSpear((int)this.x, (int)this.y, aim[0], aim[1], 20 + offset, 30);
							}
							else if(this.fireTicks == 140) {
								int[] aim = aimcenter(Controller.chr.getX(), Controller.chr.getY(), 0.8);
								int offset = Controller.random.nextInt(-12, 13);
								new URSpear((int)this.x, (int)this.y, aim[0], aim[1], offset, 0);
								cross();
							}
							else if(this.fireTicks == 230) {
								int[] aim = aimcenter(Controller.chr.getX(), Controller.chr.getY(), 0.65);
								int offset = Controller.random.nextInt(-12, 13);
								new URSpear((int)this.x, (int)this.y, aim[0], aim[1], offset, 0);
								cross();
							}
						}
						else {
							this.fireTicks = 300;
						}
						if(this.inRadius(this.targetX, this.targetY, (int)this.speed)) {
							this.rotate();
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
	
	public int[] aimcenter(int x, int y, double val) {
		int relativeX = x - this.area.getX();
		int relativeY = y - this.area.getY();
		int newX = (int)((relativeX - 480) * val) + 480 + this.area.getX();
		int newY = (int)((relativeY - 480) * val) + 480 + this.area.getY();
		return new int[] {newX, newY};
	}
	
	public void shadesrun() {
		if(UnhallowedRevenantShade.FRAMES > 0) {
			UnhallowedRevenantShade.FRAMES--;
		}
		else {
			for(int i = 0; i < 8; i++) {
				shades[i].shootSpinner(this.area.getX() + 480, this.area.getY() + 480);
			}
			UnhallowedRevenantShade.FRAMES = 280;
		}
	}

	@Override
	public BufferedImage getImage() {
		
		//order: 
		//swap, cross, walk, flame, stand
		if(this.swapTicks > 0) {this.swapTicks--;}
		if(this.crossTicks > 0) {this.crossTicks--;}
		if(this.flameTicks > 0) {this.flameTicks--;}
		if(this.walkTicks > 0) {this.walkTicks--;}
		
		if(this.flameTicks <= 0 && this.flaming) {this.flameTicks = 4 * FLAME_FRAMES;}
		if(this.walkTicks <= 0 && this.walking) {this.walkTicks = 4 * WALK_FRAMES;}
		if(this.swapTicks == 1 && this.phase == 2) {
			this.phase = 3;
			this.swapTicks = 0;
			this.flaming = true;
		}
		if(this.swapTicks == 1 && this.phase == 5) {
			this.phase = 6;
			this.walkTurn = Controller.random.nextInt(0, 360);
			this.walkRadius = Controller.random.nextInt(MIN_RADIUS, MAX_RADIUS);
			this.rotate();
			this.swapTicks = 0;
			this.flaming = false;
		}
		
		if(this.swapTicks > 0) {
			for(int i = 4; i >= 0; i--) {
				if(SWAP_FRAMES * i < this.swapTicks) {
					//6, 7, 8, 9, 10
					if(this.swapMode % 2 == 0) {
						if(this.swapTicks == 1) {this.swapMode++;}
						return Target.IMGS_UNHALLOWEDREVENANT.get(6 + i);}
					else {
						if(this.swapTicks == 1) {this.swapMode++;}
						return Target.IMGS_UNHALLOWEDREVENANT.get(10 - i);}
				}
			}
		}
		
		if(this.crossTicks > 0){
			//4, 5, 4
			if(CROSS_FRAMES * 2 < this.crossTicks) {return Target.IMGS_UNHALLOWEDREVENANT.get(4);}
			else if(CROSS_FRAMES < this.crossTicks) {return Target.IMGS_UNHALLOWEDREVENANT.get(5);}
			else {return Target.IMGS_UNHALLOWEDREVENANT.get(4);}
		}
		
		if(this.walkTicks > 0 && swapMode % 2 == 0) {
			for(int i = 3; i >= 0; i--) {
				if(WALK_FRAMES * i < this.walkTicks) {
					//0, 1, 2, 3
					return Target.IMGS_UNHALLOWEDREVENANT.get(3 - i);
				}
			}
		}
		
		if(this.flameTicks > 0) {
			for(int i = 3; i >= 0; i--) {
				if(FLAME_FRAMES * i < this.flameTicks) {
					//11, 12, 13, 14
					return Target.IMGS_UNHALLOWEDREVENANT.get(14 - i);
				}
			}
		}

		if(swapMode % 2 == 0) {
			return Target.IMGS_UNHALLOWEDREVENANT.get(0);
		}
		else {
			return Target.IMGS_UNHALLOWEDREVENANT.get(11);
		}
	}
	
	@Override
	public boolean collision(Projectile p) {
		return super.collision(p, 0, 0, 0.15);
	}
	
	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		super.draw(g, shiftX, shiftY, i);
		for(int j = 0; j < 8; j++) {
			if(shades[j] != null) {
				if(!Driver.gameState.equals("pause")) {
					if(phase >= 1 && phase <= 5) {
						shades[j].move(-1);
					}
					else if(phase == 6){
						shades[j].move(1);
					}
				}
				shades[j].draw(g, shiftX, shiftY, i);
			}
		}
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

	@Override
	public boolean inWall() {
		return super.inWall(10);
	}
}
