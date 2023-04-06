package player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.Controller;
import equipment.ability.Ability;
import equipment.weapon.Weapon;
import project.Equipment;

public class Archer extends Player{

	public Archer() {
		super();
		this.defaultWeapon = Weapon.t0_bow;
		this.defaultAbility = Ability.t0_quiver;
		this.classType = "Archer";
		
		//edit base stats here
		this.maxHealth = 125;
		this.defense = 0;
		this.maxMana = 75;
		this.vitality = 20;
		this.wisdom = 20;
		this.dexterity = 24;
		this.attack = 20;
		this.speed = 18;
		
		this.wSubtype = "bow";
		this.abSubtype1 = "quiver";
		this.abSubtype2 = "";
		this.arSubtype = "leather";
		
		this.currentHealth = maxHealth;
		this.currentMana = maxMana;
		
		this.setWeapon((Weapon) defaultWeapon);
		this.setAbility((Ability) defaultAbility);
		
		try {
			this.setImgs(new BufferedImage[]{
					ImageIO.read(new File("./char/archer/archerb1.png")),ImageIO.read(new File("./char/archer/archerb2.png")),ImageIO.read(new File("./char/archer/archerb1.png")),
					ImageIO.read(new File("./char/archer/archerb2.png")),ImageIO.read(new File("./char/archer/archerb3.png")),ImageIO.read(new File("./char/archer/archerl1.png")),
					ImageIO.read(new File("./char/archer/archerl2.png")),ImageIO.read(new File("./char/archer/archerl1.png")),ImageIO.read(new File("./char/archer/archerl2.png")),
					ImageIO.read(new File("./char/archer/archerl3.png")),ImageIO.read(new File("./char/archer/archerf1.png")),ImageIO.read(new File("./char/archer/archerf2.png")),
					ImageIO.read(new File("./char/archer/archerf1.png")),ImageIO.read(new File("./char/archer/archerf2.png")),ImageIO.read(new File("./char/archer/archerf3.png")),
					ImageIO.read(new File("./char/archer/archerr1.png")),ImageIO.read(new File("./char/archer/archerr2.png")),ImageIO.read(new File("./char/archer/archerr1.png")),
					ImageIO.read(new File("./char/archer/archerr2.png")),ImageIO.read(new File("./char/archer/archerr3.png"))});
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		} 
	}
	
	@Override
	public void levelUp() {
		super.levelUp();
		int health = Controller.random.nextInt(27, 33);
		int mana = Controller.random.nextInt(9, 13);
		this.maxHealth += health;
		this.currentHealth += health;
		this.maxMana += mana;
		this.currentMana += mana;
		this.vitality += highStat();
		this.speed += lowStat();
		this.wisdom += medStat();
		this.attack += medStat();
		this.dexterity += greatStat();
	}
}
