package equipment.armour;

import control.Controller;
import equipment.st.*;
import project.Equipment;

public abstract class Armour extends Equipment{
	public final static Armour t0_robe = new TieredRobe("Simple Robe", "", 0, 2, 0, 0, 0);
	public final static Armour t1_robe = new TieredRobe("Apprentice Robe", "", 0, 3, 1, 0, 1);
	public final static Armour t2_robe = new TieredRobe("Battlemage Robe", "", 1, 4, 1, 5, 2);
	public final static Armour t3_robe = new TieredRobe("Warlock Robe", "", 1, 6, 2, 10, 3);
	public final static Armour t4_robe = new TieredRobe("Adept Robe", "", 1, 8, 2, 15, 4);
	public final static Armour t5_robe = new TieredRobe("Twilight Robe", "", 2, 10, 3, 20, 5);
	public final static Armour t6_robe = new TieredRobe("Robe of the Enchantress", "", 2, 12, 4, 25, 6);
	public final static Armour t7_robe = new TieredRobe("Firelight Robe", "", 3, 14, 5, 30, 7);
	public final static Armour t8_robe = new TieredRobe("Robe of the Azure", "", 3, 16, 6, 35, 8);
	public final static Armour t9_robe = new TieredRobe("Robe of the Hallowed", "", 4, 18, 7, 40, 9);
	public final static Armour t10_robe = new TieredRobe("Invoker's Robe", "", 4, 21, 8, 45, 10);
	public final static Armour t11_robe = new TieredRobe("Robe of the Starcaller", "", 5, 24, 9, 50, 11);
	public final static Armour[] tiered_robes = new Armour[]{t0_robe, t1_robe, t2_robe, t3_robe, t4_robe, t5_robe,
			t6_robe, t7_robe, t8_robe, t9_robe, t10_robe, t11_robe};
	
	public final static Armour t0_leather = new TieredLeather("Simple Leather", "", 0, 3, 0, 0);
	public final static Armour t1_leather = new TieredLeather("Recruit's Leather", "", 0, 5, 1, 1);
	public final static Armour t2_leather = new TieredLeather("Polished Leather", "", 1, 7, 1, 2);
	public final static Armour t3_leather = new TieredLeather("Reinforced Leather", "", 1, 9, 2, 3);
	public final static Armour t4_leather = new TieredLeather("Elite Leather", "", 1, 11, 2, 4);
	public final static Armour t5_leather = new TieredLeather("Royal Leather", "", 2, 13, 3, 5);
	public final static Armour t6_leather = new TieredLeather("Molten Leather", "", 2, 15, 3, 6);
	public final static Armour t7_leather = new TieredLeather("Leviathan Leather", "", 3, 17, 4, 7);
	public final static Armour t8_leather = new TieredLeather("Titansteel Leather", "", 3, 20, 4, 8);
	public final static Armour t9_leather = new TieredLeather("Hydra Scale Leather", "", 4, 23, 5, 9);
	public final static Armour t10_leather = new TieredLeather("Leather of Crimson Peaks", "", 4, 26, 6, 10);
	public final static Armour t11_leather = new TieredLeather("Nightbane Leather", "", 5, 30, 7, 11);
	
	public final static Armour[] tiered_leathers = new Armour[]{t0_leather, t1_leather, t2_leather, t3_leather, t4_leather, t5_leather,
			t6_leather, t7_leather, t8_leather, t9_leather, t10_leather, t11_leather};
	
	public final static Armour t0_armour = new TieredArmour("Simple Armour", "", 4, 0, 0);
	public final static Armour t1_armour = new TieredArmour("Chainmail", "", 6, 5, 1);
	public final static Armour t2_armour = new TieredArmour("Purple Steel Mail", "", 8, 5, 2);
	public final static Armour t3_armour = new TieredArmour("Golden Armour", "", 11, 10, 3);
	public final static Armour t4_armour = new TieredArmour("Froststeel Armour", "", 14, 15, 4);
	public final static Armour t5_armour = new TieredArmour("Lifebringer Armour", "", 17, 20, 5);
	public final static Armour t6_armour = new TieredArmour("Volcanic Armour", "", 20, 25, 6);
	public final static Armour t7_armour = new TieredArmour("Soulcrusher Armour", "", 23, 30, 7);
	public final static Armour t8_armour = new TieredArmour("Armour of Desolation", "", 26, 35, 8);
	public final static Armour t9_armour = new TieredArmour("Demonclaw Armour", "", 29, 40, 9);
	public final static Armour t10_armour = new TieredArmour("Tyrant Slayer Armour", "", 32, 45, 10);
	public final static Armour t11_armour = new TieredArmour("Annhilation Armour", "", 36, 50, 11);
	
	public final static Equipment Crab_Carapace = new CrabCarapace();
	public final static Equipment Royal_Scorpion_Hide = new RoyalScorpionHide();
	public final static Equipment Wetlands_Robe = new WetlandsRobe();
	
	public final static Equipment Runeforge_Mantle = new RuneforgeMantle();
	public final static Equipment Blackforge_Armour = new BlackforgeArmour();
	public final static Equipment Lifeforge_Chainmail = new LifeforgeChainmail();
	
	public final static Equipment Frostsworn_Robe = new FrostswornRobe();
	public final static Equipment Stalwart_Shieldplate = new StalwartShieldplate();
	
	public final static Armour[] tiered_armours = new Armour[] {t0_armour, t1_armour, t2_armour, t3_armour, t4_armour, t5_armour,
			t6_armour, t7_armour, t8_armour, t9_armour, t10_armour, t11_armour};
	
	public Armour(String title, String desc) {
		super(title, desc);
		this.type = "armour";
	}
}

class CrabCarapace extends Armour{
	public CrabCarapace() {
		super("Crab Carapace", "");
		this.tierDisplay = "ST";
		this.img = ART_CRABCARAPACE;
		this.subtype = "heavy";
		
		this.desc = new String[] {"Used by Warrior, Paladin", "+12 DEF", "+15 HP", "+5 SPD"};
	}

	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 12);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() + 15);
		Controller.chr.setSpeed(Controller.chr.getSpeed() + 5);
	}

	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 12);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() - 15);
		Controller.chr.setSpeed(Controller.chr.getSpeed() - 5);
	}
}

class WetlandsRobe extends Armour{
	public WetlandsRobe() {
		super("Wetlands Robe", "");
		this.tierDisplay = "ST";
		this.img = ART_WETLANDSROBE;
		this.subtype = "robe";
		
		this.desc = new String[] {"Used by Sorcerer, Necromancer", "+4 DEF", "+3 WIS", "+15 SPL"};
	}

	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 4);
		Controller.chr.setWisdom(Controller.chr.getWisdom() + 3);
		Controller.chr.setSpellsurge(Controller.chr.getSpellsurge() + 15);
	}

	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 4);
		Controller.chr.setWisdom(Controller.chr.getWisdom() - 3);
		Controller.chr.setSpellsurge(Controller.chr.getSpellsurge() - 15);
	}
}

class RuneforgeMantle extends Armour{
	public RuneforgeMantle() {
		super("Runeforge Mantle", "");
		this.tierDisplay = "ST";
		this.img = ART_RUNEFORGEMANTLE;
		this.subtype = "robe";
		
		this.desc = new String[] {"Used by Sorcerer, Necromancer", "+6 DEF", "+6 VIT", "+30 HP"};
	}

	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 6);
		Controller.chr.setVitality(Controller.chr.getVitality() + 6);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() + 30);
	}

	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 6);
		Controller.chr.setVitality(Controller.chr.getVitality() - 6);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() - 30);
	}
}

class BlackforgeArmour extends Armour{
	public BlackforgeArmour() {
		super("Blackforge Armour", "");
		this.tierDisplay = "ST";
		this.img = ART_BLACKFORGEARMOUR;
		this.subtype = "leather";
		
		this.desc = new String[] {"Used by Archer, Hunter", "+11 DEF", "+2 ATK", "+6 CRT"};
	}

	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 11);
		Controller.chr.setCrit(Controller.chr.getCrit() + 6);
		Controller.chr.setAttack(Controller.chr.getAttack() + 2);
	}

	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 11);
		Controller.chr.setCrit(Controller.chr.getCrit() - 6);
		Controller.chr.setAttack(Controller.chr.getAttack() - 2);
	}
}

class LifeforgeChainmail extends Armour{
	public LifeforgeChainmail() {
		super("Lifeforge Chainmail", "");
		this.tierDisplay = "ST";
		this.img = ART_LIFEFORGECHAINMAIL;
		this.subtype = "heavy";
		
		this.desc = new String[] {"Used by Warrior, Paladin", "+20 VIT"};
	}

	@Override
	public void equip() {
		Controller.chr.setVitality(Controller.chr.getVitality() + 20);
	}

	@Override
	public void dequip() {
		Controller.chr.setVitality(Controller.chr.getVitality() - 20);
	}
}

class FrostswornRobe extends Armour{
	public FrostswornRobe() {
		super("Frostsworn Robe", "");
		this.tierDisplay = "ST";
		this.img = ART_FROSTSWORNROBE;
		this.subtype = "robe";
		
		this.desc = new String[] {"Used by Sorcerer, Necromancer", "+3 DEF", "+10 DEX", "+30 MP", "+8 SPD"};
	}
	
	@Override
	public void equip() {
		Controller.chr.setSpeed(Controller.chr.getSpeed() + 8);
		Controller.chr.setDefense(Controller.chr.getDefense() + 3);
		Controller.chr.setDexterity(Controller.chr.getDexterity() + 10);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() + 30);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setSpeed(Controller.chr.getSpeed() - 8);
		Controller.chr.setDefense(Controller.chr.getDefense() - 3);
		Controller.chr.setDexterity(Controller.chr.getDexterity() - 10);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() - 30);
	}
}

class StalwartShieldplate extends Armour{
	public StalwartShieldplate() {
		super("Stalwart Shieldplate", "");
		this.tierDisplay = "ST";
		this.img = ART_STALWARTSHIELDPLATE;
		this.subtype = "heavy";
		
		this.desc = new String[] {"Used by Warrior, Paladin", "+20 DEF", "+4 ATK", "+4 DEX", "+4 SPD"};
	}
	
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 20);
		Controller.chr.setAttack(Controller.chr.getAttack() + 4);
		Controller.chr.setDexterity(Controller.chr.getDexterity() + 4);
		Controller.chr.setSpeed(Controller.chr.getSpeed() + 4);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 20);
		Controller.chr.setAttack(Controller.chr.getAttack() - 4);
		Controller.chr.setDexterity(Controller.chr.getDexterity() - 4);
		Controller.chr.setSpeed(Controller.chr.getSpeed() - 4);
	}
}

