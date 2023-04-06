package player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.Controller;
import equipment.ability.Ability;
import equipment.weapon.Weapon;

public class Paladin extends Player{

	public Paladin() {
		super();
		this.defaultWeapon = Weapon.t0_sword;
		this.defaultAbility = Ability.t0_tome;
		this.classType = "Paladin";
		
		//edit base stats here
		this.maxHealth = 135;
		this.defense = 0;
		this.maxMana = 75;
		this.vitality = 24;
		this.wisdom = 22;
		this.dexterity = 20;
		this.attack = 20;
		this.speed = 18;
		
		this.wSubtype = "sword";
		this.abSubtype1 = "tome";
		this.abSubtype2 = "";
		this.arSubtype = "heavy";
		
		this.currentHealth = maxHealth;
		this.currentMana = maxMana;
		
		this.setWeapon((Weapon) defaultWeapon);
		this.setAbility((Ability) defaultAbility);
		
		try {
			this.setImgs(new BufferedImage[]{
					ImageIO.read(new File("./char/paladin/paladinb1.png")),ImageIO.read(new File("./char/paladin/paladinb2.png")),ImageIO.read(new File("./char/paladin/paladinb1.png")),
					ImageIO.read(new File("./char/paladin/paladinb2.png")),ImageIO.read(new File("./char/paladin/paladinb3.png")),ImageIO.read(new File("./char/paladin/paladinl1.png")),
					ImageIO.read(new File("./char/paladin/paladinl2.png")),ImageIO.read(new File("./char/paladin/paladinl1.png")),ImageIO.read(new File("./char/paladin/paladinl2.png")),
					ImageIO.read(new File("./char/paladin/paladinl3.png")),ImageIO.read(new File("./char/paladin/paladinf1.png")),ImageIO.read(new File("./char/paladin/paladinf2.png")),
					ImageIO.read(new File("./char/paladin/paladinf1.png")),ImageIO.read(new File("./char/paladin/paladinf2.png")),ImageIO.read(new File("./char/paladin/paladinf3.png")),
					ImageIO.read(new File("./char/paladin/paladinr1.png")),ImageIO.read(new File("./char/paladin/paladinr2.png")),ImageIO.read(new File("./char/paladin/paladinr1.png")),
					ImageIO.read(new File("./char/paladin/paladinr2.png")),ImageIO.read(new File("./char/paladin/paladinr3.png"))});
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		} 
	}
	
	@Override
	public void levelUp() {
		super.levelUp();
		int health = Controller.random.nextInt(28, 35);
		int mana = Controller.random.nextInt(10, 14);
		this.maxHealth += health;
		this.currentHealth += health;
		this.maxMana += mana;
		this.currentMana += mana;
		this.vitality += greatStat();
		this.speed += lowStat();
		this.wisdom += highStat();
		this.attack += medStat();
		this.dexterity += medStat();
	}
}