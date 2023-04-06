package enemies.volatilevolcano.daimanwitch;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class DaimanWitch extends Enemy{
	final static int HEALTH = 600;
	final static int DEF = 5;
	final static int SIZE = 38;
	final static double SPEED = 0.8;
	final static int BLAST_RANGE = 110;
	final static double BLAST_SPEED = 0.5;
	final static int BLAST_DMG = 60;
	final static int BLAST_DELAY = 100;
	final static double BLAST_ACCEL = 0.1;
	final static int BEAM_RANGE = 175;
	final static int BEAM_SPEED = 2;
	final static int BEAM_DMG = 50;
	final static int MIN_FIRE_RATE = 125;
	final static int MAX_FIRE_RATE = 175;
	final static int MIN_CAST_RATE = 400;
	final static int MAX_CAST_RATE = 500;
	
	final static int XP = 50;
	
	final static int ANIMATE_INTERVAL = 25;
	final static int MIN_MOVE_INTERVAL = 60;
	final static int MAX_MOVE_INTERVAL = 100;
	
	final int CHASE_LIMIT = 800;
	final int ATTRACT_RADIUS = 400;
	final int ALERT_RANGE = 250;
	final int WANDER_RADIUS = 20;
	
	private int fireTicks, castTicks, animate, moveCycle, initX, initY, shotTurn;
	private int moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
	private double[] positions;
	
	public DaimanWitch(int x, int y) {
		super(x, y, HEALTH, DEF, "Daiman Witch");
		this.initX = x;
		this.initY = y;
		this.dir = 0;
		this.size = SIZE;
		this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE/3, MAX_FIRE_RATE/3+1);
		this.castTicks = Controller.random.nextInt(MIN_CAST_RATE/3, MAX_CAST_RATE/3+1);
		this.positions = this.wanderPositions(WANDER_RADIUS);
		this.xp = 80;
		
		this.table = new LootRoll[][] 
				{
			{lr("w4", 4, 35), lr("w5", 3, 35)},
			{lr("ab2", 3, 35), lr("ab3", 4, 35)},
			{lr("ar4", 3, 35), lr("ar5", 4, 35)},
			{lr("r1", 5, 35), lr("r2", 2, 35)},
			{lr(Armour.Runeforge_Mantle, 1, 11)}
				};
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
				if(this.animate == 2 * ANIMATE_INTERVAL) {
					animate = 0;
				}
				if(Controller.chr.getX() > this.x - this.ALERT_RANGE && Controller.chr.getX() < this.x + this.ALERT_RANGE
							&& Controller.chr.getY() > this.y - this.ALERT_RANGE && Controller.chr.getY() < this.y + this.ALERT_RANGE) {
					if(this.moveCycle >= this.moveInterval) {
						this.go((int)(this.x + this.x - Controller.chr.getX()), (int)(this.y + this.y-Controller.chr.getY()), SPEED);
						if(this.xVel < 0) {
							this.dir = 1;
						}
						else {
							this.dir = 0;
						}
						this.moveCycle = 0;
						this.moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);				
					}
					approach();

					if(!(Controller.chr.getX() > this.x - this.ALERT_RANGE && Controller.chr.getX() < this.x + this.ALERT_RANGE
							&& Controller.chr.getY() > this.y - this.ALERT_RANGE && Controller.chr.getY() < this.y + this.ALERT_RANGE)) {
						this.moving = false;
						this.go(initX, initY, SPEED);
						this.moveCycle = 0;
					}
				}
				else {
					if(this.moveCycle >= this.moveInterval) {
						int space = Controller.random.nextInt(0, 9);
						this.go((int)this.positions[2 * space], (int)this.positions[2 * space + 1], SPEED);
						if(this.xVel < 0) {
							this.dir = 1;
						}
						else {
							this.dir = 0;
						}
						this.moveCycle = 0;
						this.moveInterval = Controller.random.nextInt(MIN_MOVE_INTERVAL, MAX_MOVE_INTERVAL+1);
					}
					approach();
				}
				if(this.fireTicks > 0) {
					this.fireTicks -= 1;
				}
				if(this.castTicks > 0) {
					this.castTicks -= 1;
				}
				if(this.x < this.initX - this.CHASE_LIMIT || this.x > this.initX + this.CHASE_LIMIT
								|| this.y < this.initY - this.CHASE_LIMIT || this.y > this.initY + this.CHASE_LIMIT) {
					this.moving = false;
					this.go(initX, initY, SPEED);
				}
				else {
					if(fireTicks <= 0){
						double[] tracking = this.relativeTrack(0.3);
						new DaimanWitchShot((int)this.x, (int)this.y, 
								(int)(Controller.chr.getX() + tracking[0]), 
								(int)(Controller.chr.getY() + tracking[1]), 0);
						new DaimanWitchShot((int)this.x, (int)this.y, 
								(int)(Controller.chr.getX() + tracking[0]), 
								(int)(Controller.chr.getY() + tracking[1]), 1);
						this.fireTicks = Controller.random.nextInt(MIN_FIRE_RATE, MAX_FIRE_RATE+1);
					}
					if(castTicks <= 0) {
						for(int i = 0; i < 8; i++) {
							new DaimanWitchBeam((int)this.x, (int)this.y, 45 * i + shotTurn);
						}
						shotTurn += Controller.random.nextInt(10, 21);
						shotTurn = shotTurn % 360;
						this.castTicks = Controller.random.nextInt(MIN_CAST_RATE, MAX_CAST_RATE+1);
					}
				}
			}
			else {
				if(this.moveCycle == 0) { //start movements
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
		for(int i = 1; i < 3; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				if(this.dir == 0) {
					return Target.IMGS_DAIMANWITCH.get(i - 1);
				}
				else {
					return Target.IMGS_DAIMANWITCH.get(i + 1);	
				}
			}
		}
		return Target.IMGS_DAIMANWITCH.get(0);
	}
}
