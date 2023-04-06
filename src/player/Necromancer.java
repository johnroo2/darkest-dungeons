package player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.Controller;
import equipment.ability.Ability;
import equipment.weapon.Weapon;
import project.Equipment;

public class Necromancer extends Player{
	
	public Necromancer() {
		super();
		this.defaultWeapon = Weapon.t0_staff;
		this.defaultAbility = Ability.t0_skull;
		this.classType = "Necromancer";
		
		//edit base stats here
		this.maxHealth = 135;
		this.defense = 0;
		this.maxMana = 75;
		this.vitality = 18;
		this.wisdom = 22;
		this.dexterity = 18;
		this.attack = 24;
		this.speed = 20;
		
		this.wSubtype = "staff";
		this.abSubtype1 = "skull";
		this.abSubtype2 = "spell";
		this.arSubtype = "robe";
		
		this.currentHealth = maxHealth;
		this.currentMana = maxMana;
		
		this.setWeapon((Weapon) defaultWeapon);
		this.setAbility((Ability) defaultAbility);
		
		try {
			this.setImgs(new BufferedImage[]{
					ImageIO.read(new File("./char/necro/necrob1.png")),ImageIO.read(new File("./char/necro/necrob2.png")),ImageIO.read(new File("./char/necro/necrob3.png")),
					ImageIO.read(new File("./char/necro/necrob4.png")),ImageIO.read(new File("./char/necro/necrob5.png")),ImageIO.read(new File("./char/necro/necrol1.png")),
					ImageIO.read(new File("./char/necro/necrol2.png")),ImageIO.read(new File("./char/necro/necrol1.png")),ImageIO.read(new File("./char/necro/necrol2.png")),
					ImageIO.read(new File("./char/necro/necrol3.png")),ImageIO.read(new File("./char/necro/necrof1.png")),ImageIO.read(new File("./char/necro/necrof2.png")),
					ImageIO.read(new File("./char/necro/necrof3.png")),ImageIO.read(new File("./char/necro/necrof4.png")),ImageIO.read(new File("./char/necro/necrof5.png")),
					ImageIO.read(new File("./char/necro/necror1.png")),ImageIO.read(new File("./char/necro/necror2.png")),ImageIO.read(new File("./char/necro/necror1.png")),
					ImageIO.read(new File("./char/necro/necror2.png")),ImageIO.read(new File("./char/necro/necror3.png"))});
		}
		catch (IOException e) {
			System.out.println("Image cannot be found!");
		} 
	}
	
	@Override
	public void levelUp() {
		super.levelUp();
		int health = Controller.random.nextInt(28, 35);
		int mana = Controller.random.nextInt(9, 13);
		this.maxHealth += health;
		this.currentHealth += health;
		this.maxMana += mana;
		this.currentMana += mana;
		this.vitality += lowStat();
		this.speed += medStat();
		this.wisdom += highStat();
		this.attack += greatStat();
		this.dexterity += lowStat();
	}
}
