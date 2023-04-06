package player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.Controller;
import equipment.ability.Ability;
import equipment.weapon.Weapon;
import project.Equipment;

public class Sorcerer extends Player{

	public Sorcerer() {
		super();
		this.defaultWeapon = Weapon.t0_wand;
		this.defaultAbility = Ability.t0_scepter;
		this.classType = "Sorcerer";
		
		//edit base stats here
		this.maxHealth = 115;
		this.defense = 0;
		this.maxMana = 85;
		this.vitality = 22;
		this.wisdom = 24;
		this.dexterity = 20;
		this.attack = 18;
		this.speed = 20;
		
		this.wSubtype = "staff";
		this.abSubtype1 = "scepter";
		this.abSubtype2 = "spell";
		this.arSubtype = "robe";
		
		this.currentHealth = maxHealth;
		this.currentMana = maxMana;
		this.setWeapon((Weapon) defaultWeapon);
		this.setAbility((Ability) defaultAbility);
		
		try {
			this.setImgs(new BufferedImage[]{
					ImageIO.read(new File("./char/sorc/sorcb1.png")),ImageIO.read(new File("./char/sorc/sorcb2.png")),ImageIO.read(new File("./char/sorc/sorcb3.png")),
					ImageIO.read(new File("./char/sorc/sorcb4.png")),ImageIO.read(new File("./char/sorc/sorcb5.png")),ImageIO.read(new File("./char/sorc/sorcl1.png")),
					ImageIO.read(new File("./char/sorc/sorcl2.png")),ImageIO.read(new File("./char/sorc/sorcl1.png")),ImageIO.read(new File("./char/sorc/sorcl2.png")),
					ImageIO.read(new File("./char/sorc/sorcl3.png")),ImageIO.read(new File("./char/sorc/sorcf1.png")),ImageIO.read(new File("./char/sorc/sorcf2.png")),
					ImageIO.read(new File("./char/sorc/sorcf3.png")),ImageIO.read(new File("./char/sorc/sorcf4.png")),ImageIO.read(new File("./char/sorc/sorcf5.png")),
					ImageIO.read(new File("./char/sorc/sorcr1.png")),ImageIO.read(new File("./char/sorc/sorcr2.png")),ImageIO.read(new File("./char/sorc/sorcr1.png")),
					ImageIO.read(new File("./char/sorc/sorcr2.png")),ImageIO.read(new File("./char/sorc/sorcr3.png"))});
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		} 
	}

	@Override
	public void levelUp() {
		super.levelUp();
		int health = Controller.random.nextInt(26, 32);
		int mana = Controller.random.nextInt(10, 14);
		this.maxHealth += health;
		this.currentHealth += health;
		this.maxMana += mana;
		this.currentMana += mana;
		this.vitality += highStat();
		this.speed += medStat();
		this.wisdom += greatStat();
		this.attack += lowStat();
		this.dexterity += medStat();
	}
}
