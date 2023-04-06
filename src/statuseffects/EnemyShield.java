package statuseffects;

import java.awt.image.BufferedImage;

import project.Enemy;

public class EnemyShield extends Status{
	private boolean active;
	
	public EnemyShield(Enemy e) {
		this.img = this.status_shield;
		this.subtype = "shield";
		this.active = false;
		
		this.unique = e.getEffects().add(this);
	}
	
	public EnemyShield(Enemy e, boolean active) {
		this.img = this.status_shield;
		this.subtype = "shield";
		this.active = active;
		
		this.unique = e.getEffects().add(this);
	}
	
	public void activate() {
		this.active = false;
	}
	
	public void deactivate() {
		this.active = true;
	}
	
	@Override
	public boolean active() {
		return !this.active;
	}
	
	@Override
	public boolean tick() {return false;}
	
	public boolean vulnerable() {return this.active;}
}
