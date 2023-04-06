package enemies.volatilevolcano.shunnedidol;

import java.awt.image.BufferedImage;

import control.Controller;
import equipment.ability.Ability;
import equipment.armour.Armour;
import project.Enemy;
import project.LootRoll;
import project.Target;

public class ShunnedIdol extends Enemy{
	final static int HEALTH = 2000;
	final static int DEF = 10;
	final static int SIZE = 64;
	final static int SHOT_RANGE = 375;
	final static double SHOT_SPEED = 1.25;
	final static int DMG = 75;
	final static int FIRE_RATE = 45;
	final static int XP = 100;
	
	final static int ANIMATE_INTERVAL = 40;
	
	final int ATTRACT_RADIUS = 500;
	
	int fireTicks;
	int animate;
	int shotTurn;
	
	public ShunnedIdol(int x, int y) {
		super(x, y, HEALTH, DEF, "Shunned Idol");
		this.size = SIZE;
		this.fireTicks = FIRE_RATE;
		this.xp = XP;
		
		this.table = new LootRoll[][] 
				{
			{lr("w4", 3, 28), lr("w5", 2, 28), lr("w6", 2, 28)},
			{lr("ab2", 2, 28), lr("ab3", 6, 28)},
			{lr("ar4", 1, 28), lr("ar5", 3, 28), lr("ar6", 3, 28)},
			{lr("r1", 2, 28), lr("r2", 5, 28)},
			{lr(Ability.Defilers_Skull, 1, 5)}
				};
	}
	
	@Override
	public boolean inWall() {
		return false;
	}

	@Override
	public void move() {
		if(this.active) {
			this.animate++;
			if(this.animate == 8 * ANIMATE_INTERVAL) {
				animate = 0;
			}
			if(this.fireTicks > 0) {
				this.fireTicks -= 1;
			}
			else{
				if(this.currentHealth > this.maxHealth * 0.7) {
					for(int i = 0; i < 10; i++) {
						new ShunnedIdolShot((int)this.x, (int)this.y, i * 36 + shotTurn);
					}
					shotTurn += Controller.random.nextInt(15, 22);
					shotTurn = shotTurn % 360;
					this.fireTicks = 4*FIRE_RATE;
				}
				else if(this.currentHealth > this.maxHealth * 0.4) {
					for(int i = 0; i < 10; i++) {
						new ShunnedIdolShot((int)this.x, (int)this.y, i * 36 + shotTurn);
					}
					shotTurn += Controller.random.nextInt(13, 25);
					shotTurn = shotTurn % 360;
					this.fireTicks = 3*FIRE_RATE;
				}
				else {
					for(int i = 0; i < 10; i++) {
						new ShunnedIdolShot((int)this.x, (int)this.y, i * 36 + shotTurn);
					}
					shotTurn += Controller.random.nextInt(11, 27);
					shotTurn = shotTurn % 360;
					this.fireTicks = 2*FIRE_RATE;
				}
			}
		}
		else {
			if(Controller.chr.getArea() == this.area || (Controller.chr.getX() > this.x - this.ATTRACT_RADIUS && Controller.chr.getX() < this.x + this.ATTRACT_RADIUS
							&& Controller.chr.getY() > this.y - this.ATTRACT_RADIUS && Controller.chr.getY() < this.y + this.ATTRACT_RADIUS)) {
				this.active = true;
			}
		}
	}

	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 8; i++) {
			if(this.animate <= ANIMATE_INTERVAL * i) {
				return Target.IMGS_SHUNNEDIDOL.get(i - 1);
			}
		}
		return Target.IMGS_SHUNNEDIDOL.get(0);
	}
}
