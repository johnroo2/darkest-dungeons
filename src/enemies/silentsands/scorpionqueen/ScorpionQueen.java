package enemies.silentsands.scorpionqueen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import equipment.armour.Armour;
import equipment.weapon.Weapon;
import portals.SilentSandsPortal;
import portals.WildWetlandsPortal;
import project.Boss;
import project.LootRoll;
import project.Target;

public class ScorpionQueen extends Boss{
	final static int HEALTH = 3000;
	final static int DEF = 0;
	final static int SIZE = 80;
	final static double SPEED = 0.9;
	
	final static int EYE_DMG = 25;
	final static double EYE_SPEED = 2.2;
	final static int EYE_RANGE = 500;
	
	final static int DAGGER_DMG = 32;
	final static double DAGGER_SPEED = 2.8;
	final static int DAGGER_RANGE = 500;
	
	final int VIEW_RADIUS = 1000;
	final int ATTRACT_RADIUS = 250;
	final int ANIMATE_INTERVAL = 30;
	final int WANDER_RADIUS = 60;
	final static int XP = 250;
	
	private int animate;
	private int fireTicks;
	private double[] positions;
	private int shotTurn;
	private int frameTicks;
	private int alt = 1;

	public ScorpionQueen(int x, int y) {
		super(x, y, HEALTH, DEF, "Scorpion Queen");
		this.barName = "Scorpion Queen";
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
				{lr("w1", 2, 15), lr("w2", 2, 15), lr("w3", 1, 15)},
				{lr("w1", 1, 15), lr("w2", 2, 15), lr("w3", 5, 15)},
				{lr("ab1", 2, 12), lr("ab2", 5, 12)},
				{lr("ab1", 2, 12), lr("ab2", 2, 12)},
				{lr("ar1", 2, 12), lr("ar2", 2, 12), lr("ar3", 5, 12)},
				{lr("r0", 3, 12), lr("r1", 6, 12)},
				{lr(Armour.Royal_Scorpion_Hide, 1, 5), lr(Weapon.Prowlers_Claw, 1, 5)}
				};
	}

	@Override
	public void begin() {
		this.shield.deactivate();
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
		
		new SilentSandsPortal((int)this.x, (int)this.y-120, true);
		new WildWetlandsPortal((int)this.x, (int)this.y+120, false);
	}

	@Override
	public boolean inWall() {
		return super.inWall(5);
	}

	@Override
	public void move() {
		if(this.active) {
			this.animate++;
			if(this.animate == 4 * ANIMATE_INTERVAL) {
				this.animate = 0;
			}

			wander(this.positions, SPEED);
			
			if(this.phase == 0) {
				if(this.fireTicks == 0) {
					for(int i = 0; i < 12; i++) {
						new ScorpionQueen_Eye((int)this.x, (int)this.y, 30 * i + shotTurn);
					}
					shotTurn += 7;
					shotTurn = shotTurn % 360;
					this.fireTicks = 180;
				}
				if((double)this.currentHealth <= HEALTH * 0.5) {
					this.phase = 1;
				}
			}
			else if(this.phase == 1) {
				if(this.fireTicks == 0) {
					for(int i = 0; i < 12; i++) {
						new ScorpionQueen_Eye((int)this.x, (int)this.y, 30 * i + shotTurn);
					}
					shotTurn += 7;
					shotTurn = shotTurn % 360;
					this.fireTicks = 325;
				}
				if(this.fireTicks == 100) {
					int angle = (int)this.getAngle((int)this.x, (int)this.y, Controller.chr.getX(), Controller.chr.getY());
					for(int i = -2; i < 3; i++) {
						new ScorpionQueen_Dagger((int)this.x, (int)this.y, angle + 24 * i);
					}
				}
				if((double)this.currentHealth <= HEALTH * 0.2) {
					this.phase = 2;
					this.shotTurn = 0;
					this.frameTicks = 800;
				}
			}
			else if(this.phase == 2) {
				if(this.frameTicks > 0) {
					this.shield.activate();
					this.frameTicks -= 1;
				}
				else {
					this.shield.deactivate();
				}
				if(this.fireTicks % 80 == 0 && this.frameTicks < 600) {
					for(int i = 0; i < 5; i++) {
						new ScorpionQueen_Eye((int)this.x, (int)this.y, 72 * i + shotTurn);
					}
					shotTurn += 25*alt;
				}
				if(this.fireTicks == 1000) {
					this.alt *= -1;
				}
				if(this.fireTicks > 0) {
					this.fireTicks--;
				}
				else {
					this.fireTicks = 1999;
				}
			}
			this.fireTicks -= 1;
		}
		else {
			if(Controller.chr.getX() > this.x - this.ATTRACT_RADIUS && Controller.chr.getX() < this.x + this.ATTRACT_RADIUS
							&& Controller.chr.getY() > this.y - this.ATTRACT_RADIUS && Controller.chr.getY() < this.y + this.ATTRACT_RADIUS) {
				this.active = true;
				this.begin();
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
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_SCORPIONQUEEN.get(i - 1);
				}
				else {
					return Target.IMGS_SCORPIONQUEEN.get(i + 3);	
				}
			}
		}
		return Target.IMGS_SCORPIONQUEEN.get(0);
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
