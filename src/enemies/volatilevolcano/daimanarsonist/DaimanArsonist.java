package enemies.volatilevolcano.daimanarsonist;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class DaimanArsonist extends Enemy{
	final static int HEALTH = 600;
	final static int DEF = 5;
	final static int SIZE = 38;
	final static double SPEED = 1;
	final static int BLAST_DMG = 75;
	final static double BLAST_SPEED = 3;
	final static int BLAST_RANGE = 60;
	final static int DMG_AOE = 30;
	final static int RADIUS_AOE = 80;
	final static int FIRE_RATE = 30;
	final static int CAST_RATE = 90;
	final static int MIN_PHASE_RATE = 400;
	final static int MAX_PHASE_RATE = 600;
	final static int MIN_CAST_RANGE = 150;
	final static int MAX_CAST_RANGE = 350;
	final static int XP = 50;
	
	final int WANDER_RADIUS = 30;
	final int ATTRACT_RADIUS = 400;
	final int ANIMATE_INTERVAL = 10;
	
	
	private int animate;
	private int shotTurn = 0;
	private int castTurn = Controller.random.nextInt(0, 360);
	private int castRange = Controller.random.nextInt(MIN_CAST_RANGE, MAX_CAST_RANGE+1);
	
	double[] positions = new double[18];
	int fireTicks;
	int castTicks;
	int phase;
	int phaseTicks;
	
	public DaimanArsonist(int x, int y) {
		super(x, y, HEALTH, DEF, "Daiman Arsonist");
		this.dir = 0;
		this.size = SIZE;
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.fireTicks = FIRE_RATE;
		this.castTicks = CAST_RATE;
		this.phaseTicks = Controller.random.nextInt(MIN_PHASE_RATE, MAX_PHASE_RATE+1);
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w4", 4, 35), lr("w5", 3, 35)},
			{lr("ab2", 3, 35), lr("ab3", 4, 35)},
			{lr("ar4", 3, 35), lr("ar5", 4, 35)},
			{lr("r1", 5, 35), lr("r2", 2, 35)},
			{lr(Armour.Blackforge_Armour, 1, 11)}
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
				if(this.phase == 0) {
					if(this.phaseTicks > 125) {
						if(this.fireTicks > 0) {
							this.fireTicks -= 1;
						}
						else {
							new DaimanArsonistShot((int)this.x, (int)this.y, shotTurn);
							new DaimanArsonistShot((int)this.x, (int)this.y, shotTurn + 180);
							shotTurn += Controller.random.nextInt(40, 60);
							shotTurn = shotTurn % 360;
							this.fireTicks = FIRE_RATE;
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
						if(this.castTicks > 0) {
							this.castTicks -= 1;
						}
						else {			
							new DaimanArsonistBomb((int)this.x, (int)this.y, castRange, castTurn);
							this.castTurn += Controller.random.nextInt(60, 140);
							this.castRange = Controller.random.nextInt(MIN_CAST_RANGE, MAX_CAST_RANGE+1);
							this.castTicks = CAST_RATE;
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
		return super.inWall(2);
	}

	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_DAIMANARSONIST.get(i - 1);
				}
				else {
					return Target.IMGS_DAIMANARSONIST.get(i + 3);	
				}
			}
		}
		return Target.IMGS_DAIMANARSONIST.get(0);
	}
}
