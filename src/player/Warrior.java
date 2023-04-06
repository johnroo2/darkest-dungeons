package player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.Controller;
import equipment.ability.Ability;
import equipment.weapon.Weapon;
import project.Equipment;

public class Warrior extends Player{

	public Warrior() {
		super();
		
		this.defaultWeapon = Weapon.t0_axe;
		this.defaultAbility = Ability.t0_helm;
		this.classType = "Warrior";
		
		//edit base stats here
		this.maxHealth = 135;
		this.defense = 0;
		this.maxMana = 75;
		this.vitality = 22;
		this.wisdom = 20;
		this.dexterity = 18;
		this.attack = 22;
		this.speed = 20;
		
		this.wSubtype = "sword";
		this.abSubtype1 = "helm";
		this.abSubtype2 = "";
		this.arSubtype = "heavy";
		
		this.currentHealth = maxHealth;
		this.currentMana = maxMana;
		
		this.setWeapon((Weapon) defaultWeapon);
		this.setAbility((Ability) defaultAbility);
		
		try {
			this.setImgs(new BufferedImage[]{
					ImageIO.read(new File("./char/warrior/warriorb1.png")),ImageIO.read(new File("./char/warrior/warriorb2.png")),ImageIO.read(new File("./char/warrior/warriorb1.png")),
					ImageIO.read(new File("./char/warrior/warriorb2.png")),ImageIO.read(new File("./char/warrior/warriorb3.png")),ImageIO.read(new File("./char/warrior/warriorl1.png")),
					ImageIO.read(new File("./char/warrior/warriorl2.png")),ImageIO.read(new File("./char/warrior/warriorl1.png")),ImageIO.read(new File("./char/warrior/warriorl2.png")),
					ImageIO.read(new File("./char/warrior/warriorl3.png")),ImageIO.read(new File("./char/warrior/warriorf1.png")),ImageIO.read(new File("./char/warrior/warriorf2.png")),
					ImageIO.read(new File("./char/warrior/warriorf1.png")),ImageIO.read(new File("./char/warrior/warriorf2.png")),ImageIO.read(new File("./char/warrior/warriorf3.png")),
					ImageIO.read(new File("./char/warrior/warriorr1.png")),ImageIO.read(new File("./char/warrior/warriorr2.png")),ImageIO.read(new File("./char/warrior/warriorr1.png")),
					ImageIO.read(new File("./char/warrior/warriorr2.png")),ImageIO.read(new File("./char/warrior/warriorr3.png"))});
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		} 
	}
	
	@Override
	public void levelUp() {
		super.levelUp();
		int health = Controller.random.nextInt(28, 35);
		int mana = Controller.random.nextInt(8, 12);
		this.maxHealth += health;
		this.currentHealth += health;
		this.maxMana += mana;
		this.currentMana += mana;
		this.vitality += highStat();
		this.speed += medStat();
		this.wisdom += medStat();
		this.attack += highStat();
		this.dexterity += lowStat();
	}
}