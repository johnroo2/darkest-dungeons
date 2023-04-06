package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import control.Controller;
import statuseffects.*;

public abstract class Boss extends Enemy{
	protected int phase;
	protected String barName;
	protected int barFrames;
	
	private final static Font BOSS_BAR_FONT = new Font("Courier New", Font.PLAIN, 18);
	private final static Font BOSS_HEALTHBAR_FONT = new Font("Courier New", Font.BOLD, 18);
	
	public Boss(int x, int y, int hp, int def, Area area, String name) {
		super(x, y, hp, def, area, name);
		this.shield = new EnemyShield(this);
		this.phase = -1;
		this.priority = 1;
	}
	
	public Boss(int x, int y, int hp, int def, String name) {
		super(x, y, hp, def, name);
		this.shield = new EnemyShield(this);
		this.phase = -1;
		this.priority = 1;
	}

	public void drawBar(Graphics g, ImageObserver i) {
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect (480, 0, 300, 59);
		
		g.setColor(new Color(50,50,50));
		g.fillRect (480, 35, 300, 24);
		
		
		double pc = 300.0 * (double) this.currentHealth/(double)this.maxHealth;
		if(this.shield.vulnerable()) {
			g.setColor(new Color(70, 170, 30, (int)(this.barFrames * 2.55)));
			g.fillRect(480 + (100-this.barFrames) * 3, 35, (int)pc - (100-this.barFrames) * 3, 24);
			
			g.setFont(BOSS_BAR_FONT);
			g.setColor(new Color(255, 255, 255, (int)(this.barFrames * 2.55)));
			g.drawString(barName, 790 - 12 * barName.length(), 25); 
		}
		else {
			//g.drawImage(this.shield.getImage(), 750, 12, 16, 16, i);
			g.setColor(new Color(60, 120, 220, (int)(this.barFrames * 2.55)));
			g.fillRect(480 + (100-this.barFrames) * 3, 35, (int)pc - (100-this.barFrames) * 3, 24);
		
		}
		g.setFont(BOSS_BAR_FONT);
		g.setColor(new Color(255, 255, 255, (int)(this.barFrames * 2.55)));
		g.drawString(barName, 790 - 12 * barName.length(), 25); 
		g.setFont(BOSS_HEALTHBAR_FONT);
		String healthDisplay = convertHealth(this.currentHealth) + "/" + convertHealth(this.maxHealth);
		FontMetrics fm = g.getFontMetrics();
		g.drawString(healthDisplay, 760 - fm.stringWidth(healthDisplay), 50);
	}
	
	public String convertHealth(double health) {
		if(health >= 100000) {
			return String.format("%.0fK", health/1000.0);
		}
		if(health >= 10000) {
			return String.format("%.1fK", health/1000.0);
		}
		if(health >= 1000) {
			return String.format("%.2fK", health/1000.0);
		}
		return String.valueOf((int)health);
	}
	
	@Override
	public boolean damage(int dmg, boolean crit) {
		if(this.effects.contains(new EnemyArmourBreak())) {
			return damage(dmg, crit, true);
		}
		if(this.shield.vulnerable()) {
			double outdmg = dmg;
			if(this.defense >= outdmg * 0.9) {
				outdmg = (int)((double)dmg * 0.1);
			}
			else{
				outdmg = dmg - defense;
			}
			if(this.effects.contains(new EnemyExposed())) {
				outdmg += 20;
			}
			if(this.effects.contains(new EnemyCurse())) {
				outdmg *= 1.25;
			}
			this.currentHealth -= outdmg;
			if(crit) {
				new PopText(true, (int)this.x, (int)this.y, (int)outdmg);
			}
			else {
				new PopText((int)this.x, (int)this.y, (int)outdmg);
			}
			Controller.chr.broadcast("attack"+outdmg);
		}
		else {
			new PopText((int)this.x, (int)this.y, "Immune");
		}
		if(this.currentHealth <= 0) {
			this.drop();
			Controller.chr.experience(this.xp);
			this.end();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean damage(int dmg, boolean crit, boolean pierce) {
		if(pierce) {
			double outdmg = dmg;
			if(this.shield.vulnerable()) {
				if(Controller.random.nextInt(0, 100) < Controller.chr.getCrit()) {
					outdmg *= 1.5;
				}
				if(this.effects.contains(new EnemyExposed())) {
					outdmg += 20;
				}
				if(this.effects.contains(new EnemyCurse())) {
					outdmg *= 1.25;
				}
				this.currentHealth -= outdmg;
				if(crit) {
					new PopText(true, (int)this.x, (int)this.y, (int)outdmg, true);
				}
				else {
					new PopText((int)this.x, (int)this.y, (int)outdmg, true);
				}
				Controller.chr.broadcast("attack"+outdmg);
			}
			else {
				new PopText((int)this.x, (int)this.y, "Immune");
			}
			
			if(this.currentHealth <= 0) {
				this.drop();
				Controller.chr.experience(this.xp);
				this.end();
				return true;
			}
			return false;
		}
		else {
			return this.damage(dmg, crit);
		}
	}
	
	public abstract void begin();
	public abstract void end();
	
	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		if(this.drawable) {
			g.drawImage(getImage(), (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);
			this.drawStats(g, shiftX, shiftY, i);
		}
	}
}
