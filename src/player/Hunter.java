package player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.Controller;
import equipment.ability.Ability;
import equipment.weapon.Weapon;
import project.Equipment;

public class Hunter extends Player{

	public Hunter() {
		super();
		this.defaultWeapon = Weapon.t0_crossbow;
		this.defaultAbility = Ability.t0_blades;
		this.classType = "Hunter";
		
		//edit base stats here
		this.maxHealth = 125;
		this.defense = 0;
		this.maxMana = 75;
		this.vitality = 18;
		this.wisdom = 18;
		this.dexterity = 22;
		this.attack = 22;
		this.speed = 24;
		
		this.wSubtype = "bow";
		this.abSubtype1 = "blades";
		this.abSubtype2 = "";
		this.arSubtype = "leather";
		
		this.currentHealth = maxHealth;
		this.currentMana = maxMana;
		
		this.setWeapon((Weapon) defaultWeapon);
		this.setAbility((Ability) defaultAbility);
		
		try {
			this.setImgs(new BufferedImage[]{
					ImageIO.read(new File("./char/hunter/hunterb1.png")),ImageIO.read(new File("./char/hunter/hunterb2.png")),ImageIO.read(new File("./char/hunter/hunterb1.png")),
					ImageIO.read(new File("./char/hunter/hunterb2.png")),ImageIO.read(new File("./char/hunter/hunterb3.png")),ImageIO.read(new File("./char/hunter/hunterl1.png")),
					ImageIO.read(new File("./char/hunter/hunterl2.png")),ImageIO.read(new File("./char/hunter/hunterl1.png")),ImageIO.read(new File("./char/hunter/hunterl2.png")),
					ImageIO.read(new File("./char/hunter/hunterl3.png")),ImageIO.read(new File("./char/hunter/hunterf1.png")),ImageIO.read(new File("./char/hunter/hunterf2.png")),
					ImageIO.read(new File("./char/hunter/hunterf1.png")),ImageIO.read(new File("./char/hunter/hunterf2.png")),ImageIO.read(new File("./char/hunter/hunterf3.png")),
					ImageIO.read(new File("./char/hunter/hunterr1.png")),ImageIO.read(new File("./char/hunter/hunterr2.png")),ImageIO.read(new File("./char/hunter/hunterr1.png")),
					ImageIO.read(new File("./char/hunter/hunterr2.png")),ImageIO.read(new File("./char/hunter/hunterr3.png"))});
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
		this.vitality += lowStat();
		this.speed += greatStat();
		this.wisdom += lowStat();
		this.attack += highStat();
		this.dexterity += highStat();
	}
}
