package enemies.corruptedcatacombs.lostsentinel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.corruptedcatacombs.lostsentineltower.LostSentinelTower;
import enemies.corruptedcatacombs.lostsentinelturret.LostSentinelTurret;
import equipment.ability.Ability;
import equipment.armour.Armour;
import equipment.ring.Ring;
import equipment.weapon.Weapon;
import portals.CorruptedCatacombsPortal;
import portals.InnerSanctumPortal;
import project.Boss;
import project.LootRoll;
import project.Projectile;
import project.Target;

public class LostSentinel extends Boss{
	final static int HEALTH = 36000;
	final static int DEF = 35;
	final static int SIZE = 90;
	
	final int VIEW_RADIUS = 800;
	final static int XP = 750;
	
	private int gap = 2;
	
	private int fireTicks, shieldTicks, arrowTicks;
	private int animate;
	
	private static int ANIMATE_TICKS = 10;
	private LostSentinelTurret[] line = new LostSentinelTurret[18];
	private LostSentinelTower leftTower, rightTower;

	public LostSentinel(int x, int y) {
		super(x, y, HEALTH, DEF, "Lost Sentinel");
		this.barName = "Lost Sentinel";
		this.shield.deactivate();
		this.size = SIZE;
		this.xp = XP;
		for(int i = 0; i < 18; i++) {
			line[i] = new LostSentinelTurret(this.area.getX() + 60 + 40 * i, this.area.getY() + 40);
		}
		this.leftTower = new LostSentinelTower(this.area.getX() + 70, this.area.getY() + 710);
		this.rightTower = new LostSentinelTower(this.area.getX() + 730, this.area.getY() + 710);
		
		this.table = new LootRoll[][] 
				{
			{lr("w6", 4, 25), lr("w7", 7, 25)},
			{lr("w6", 2, 25), lr("w7", 10, 25)},
			{lr("ab3", 2, 40), lr("ab4", 10, 40)},
			{lr("ab3", 5, 40), lr("ab4", 5, 40)},
			{lr("ar6", 2, 20), lr("ar7", 3, 20), lr("ar8", 2, 20)},
			{lr("ar6", 3, 30), lr("ar7", 4, 30), lr("ar8", 3, 30)},
			{lr("r2", 3, 20), lr("r3", 5, 20)},
			{lr(Weapon.Bow_Of_The_Fractured, 1, 9), lr(Ability.Sentinels_Quiver, 1, 9), 
				lr(Armour.Stalwart_Shieldplate, 1, 9), lr(Ring.Tear_Of_The_Hopeless, 1, 9)}
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
		
		new InnerSanctumPortal(this.area.getX() + 400, this.area.getY() + 400, false);
	}

	@Override
	public boolean inWall() {
		return super.inWall(0);
	}
	
	public void reset() {
		this.fireTicks = 0;
		this.gap = 2;
	}
	
	public void shootMedium() {
		if(Controller.coinflip()) {
			int secondGap = this.gap + Controller.random.nextInt(5, 7);
			if(secondGap > 15) {secondGap -= 14;}
			for(int i = 0; i < 18; i++) {
				if((i-this.gap > 1 || this.gap-i > 1) && (i-secondGap > 1 || secondGap-i > 1)) {
					this.line[i].shoot();
				}
			}
		}
		else {
			for(int i = 0; i < 18; i++) {
				if(i-this.gap > 1 || this.gap-i > 1) {
					this.line[i].shoot();
				}
			}
		}
		this.gap += Controller.random.nextInt(7, 9);
		if(this.gap > 15) {this.gap -= 14;}
	}
	
	public void shootZigzag() {
		int pos;
		if(gap < 14) { //go left
			pos = 2 + gap;
		}
		else {
			pos = 29 - gap;
		}
		for(int i = 0; i < 18; i++) {
			if(i-pos > 1 || pos-i > 1) {
				this.line[i].shoot();
			}
		}
		this.gap += 3;
		
		if(gap >= 28) {
			gap -= 28;
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
			}
			else {
				this.shield.deactivate();
				if(this.phase == 0) {
					if(this.fireTicks > 0) {
						this.fireTicks -= 1;
					}
					else {
						shootMedium();
						this.fireTicks = 100;
					}
					
					if(this.currentHealth < this.maxHealth * 0.75) {
						this.shieldTicks = 250;
						this.phase = 1;
					}
				}
				else if(this.phase == 1) {
					if(this.fireTicks > 0) {
						this.fireTicks -= 1;
					}
					else {
						shootZigzag();
						this.fireTicks = 60;
					}
					
					if(this.currentHealth < this.maxHealth * 0.5) {
						this.shieldTicks = 250;
						this.phase = 2;
					}
				}
				else if(this.phase == 2) {
					if(this.fireTicks > 0) {
						this.fireTicks -= 1;
					}
					else {
						shootMedium();
						this.fireTicks = 100;
					}
					
					if(this.arrowTicks > 0) {
						this.arrowTicks -= 1;
					}
					else {
						this.leftTower.shoot(315);
						this.rightTower.shoot(225);
						this.arrowTicks = 450;
					}
					
					if(this.currentHealth < this.maxHealth * 0.25) {
						this.shieldTicks = 250;
						this.phase = 3;
					}				
				}
				else if(this.phase == 3) {
					if(this.fireTicks > 0) {
						this.fireTicks -= 1;
					}
					else {
						shootZigzag();
						this.fireTicks = 60;
					}
					
					if(this.arrowTicks > 0) {
						this.arrowTicks -= 1;
					}
					else {
						this.leftTower.shoot(315);
						this.rightTower.shoot(215);
						this.arrowTicks = 450;
					}
				}
			}
		}
		else {
			if(this.currentHealth < this.maxHealth) {
				this.active = true;
				this.begin();
			}
		}
	}

	@Override
	public BufferedImage getImage() {
		if(this.phase == -1 || this.phase == 0 || this.phase == 1) {
			return Target.IMGS_LOSTSENTINEL.get(0);
		}
		else {
			animate++;
			if(this.animate == 4 * ANIMATE_TICKS) {
				this.animate = 0;
			}
			for(int i = 1; i < 5; i++) {
				if(this.animate < i * ANIMATE_TICKS) {
					return Target.IMGS_LOSTSENTINEL.get(i);
				}
			}
		}
		return Target.IMGS_LOSTSENTINEL.get(0);
	}
	
	@Override
	public boolean collision(Projectile p) {
		return super.collision(p, 0, 0, 0.12);
	}
	
	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		for(int j = 0; j < 18; j++) {
			this.line[j].draw(g, shiftX, shiftY, i);
		}
		this.leftTower.draw(g, shiftX, shiftY, i);
		this.rightTower.draw(g, shiftX, shiftY, i);
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
