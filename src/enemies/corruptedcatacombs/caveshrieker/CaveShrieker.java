package enemies.corruptedcatacombs.caveshrieker;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.ability.Ability;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class CaveShrieker extends Enemy{
	final static int HEALTH = 2800;
	final static int DEF = 20;
	final static int SIZE = 72;
	final static double SPEED = 1;
	final static int SHRIEK_DMG1 = 75;
	final static int SHRIEK_DMG2 = 125;
	final static double SHRIEK_SPEED = 2.5;
	final static int SHRIEK_RANGE = 150;
	final static int STAR_DMG = 10;
	final static double STAR_SPEED = 2;
	final static int STAR_RANGE = 300;
	final static int SHRIEK_RATE = 50;
	final static int STAR_RATE = 90;
	final static int MIN_PHASE_RATE = 500;
	final static int MAX_PHASE_RATE = 800;
	final static int XP = 200;
	
	final int WANDER_RADIUS = 30;
	final int ATTRACT_RADIUS = 400;
	final int ANIMATE_INTERVAL = 30;
	
	
	private int animate;
	private int shriekTurn = Controller.random.nextInt(0, 360);
	private int starTurn = Controller.random.nextInt(0, 360);
	
	double[] positions = new double[18];
	int shriekTicks;
	int starTicks;
	int phase;
	int phaseTicks;
	
	public CaveShrieker(int x, int y) {
		super(x, y, HEALTH, DEF, "Cave Shrieker");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.shriekTicks = SHRIEK_RATE;
		this.starTicks = STAR_RATE;
		this.phaseTicks = Controller.random.nextInt(MIN_PHASE_RATE, MAX_PHASE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w6", 2, 45), lr("w7", 5, 45)},
			{lr("ab3", 2, 45), lr("ab4", 5, 45)},
			{lr("ar6", 2, 45), lr("ar7", 3, 45), lr("ar8", 2, 45)},
			{lr("r2", 6, 45)},
			{lr(Ability.Shriekcallers_Visage, 1, 6)}
				};
	}

	@Override
	public void move() {
		if(this.active) {
			this.animate++;
			if(this.animate == 2 * ANIMATE_INTERVAL) {
				this.animate = 0;
			}
			if(this.moving) {
				if(this.phase == 0) {
					if(this.phaseTicks > 125) {
						if(this.shriekTicks > 0 && this.shriekTicks != 12) {
							this.shriekTicks -= 1;
						}
						else {
							if(this.shriekTicks == 0 || this.shriekTicks == 12) {
								new CaveShriekerShot((int)this.x, (int)this.y, shriekTurn, SHRIEK_DMG1);
								new CaveShriekerShot((int)this.x, (int)this.y, shriekTurn + 180, SHRIEK_DMG1);
								this.shriekTicks -= 1;
							}
							if(this.shriekTicks <= 0) {
								shriekTurn += Controller.random.nextInt(20, 30);
								shriekTurn = shriekTurn % 360;
								this.shriekTicks = SHRIEK_RATE;
							}
						}
						if(this.starTicks > 0){
							this.starTicks -= 1;
						}
						else {
							for(int i = 0; i < 7; i++) {
								new CaveShriekerStar((int)this.x, (int)this.y, starTurn + 51.4 * i);
							}
							starTurn += Controller.random.nextInt(20, 30);
							starTurn = starTurn % 360;
							this.starTicks = STAR_RATE;
						}
					}
					
					if(this.phaseTicks > 0) {
						this.phaseTicks -= 1;
					}
					else {
						this.phase = 1;
						this.phaseTicks = Controller.random.nextInt(MIN_PHASE_RATE, MAX_PHASE_RATE+1);
					}
				}
				else {
					if(this.phaseTicks > 125) {
						if(this.shriekTicks > 0) {
							this.shriekTicks -= 1;
						}
						else {		
							for(int i = 0; i < 5; i++) {
								new CaveShriekerShot((int)this.x, (int)this.y, 72 * i + shriekTurn, SHRIEK_DMG2);
							}
							this.shriekTurn += 35;
							this.shriekTurn = this.shriekTurn % 360;
							this.shriekTicks = (int)(SHRIEK_RATE * 0.85);
						}
					}
					
					if(this.phaseTicks > 0) {
						this.phaseTicks -= 1;
					}
					else {
						this.phase = 0;
						this.phaseTicks = Controller.random.nextInt(MIN_PHASE_RATE, MAX_PHASE_RATE+1);
					}
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
		return super.inWall(10);
	}

	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 3; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_CAVESHRIEKER.get(i - 1);
				}
				else {
					return Target.IMGS_CAVESHRIEKER.get(i + 1);	
				}
			}
		}
		return Target.IMGS_CAVESHRIEKER.get(0);
	}
}
