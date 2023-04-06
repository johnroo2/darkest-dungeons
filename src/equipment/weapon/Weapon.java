package equipment.weapon;

import control.Controller;
import equipment.st.BladeOfTheMindless;
import equipment.st.BowOfTheFractured;
import equipment.st.BrookchaserBow;
import equipment.st.CrestfellerAxe;
import equipment.st.ProwlersClaw;
import equipment.st.SpectralFireWand;
import equipment.st.SpiritStaff;
import equipment.st.TrecharousCrossbow;
import equipment.st.WandOfTheFallen;
import project.Equipment;
import statuseffects.*;

public abstract class Weapon extends Equipment{
	protected int fireRate;
	
	public final static Weapon t0_wand = new TieredWand("Simple Wand", "", 25, 35, 0); //30
	public final static Weapon t1_wand = new TieredWand("Energy Wand", "", 30, 40, 1); //35
	public final static Weapon t2_wand = new TieredWand("Missile Wand", "", 35, 45, 2); //40
	public final static Weapon t3_wand = new TieredWand("Polished Wand", "", 40, 50, 3); //45
	public final static Weapon t4_wand = new TieredWand("Crystal Wand", "", 45, 60, 4); //52.5
	public final static Weapon t5_wand = new TieredWand("Wand of Shadows", "", 50, 70, 5); //60
	public final static Weapon t6_wand = new TieredWand("Inferno Wand", "", 55, 85, 6); //70
	public final static Weapon t7_wand = new TieredWand("Wand of Annhilation", "", 60, 100, 7); //80
	public final static Weapon t8_wand = new TieredWand("Wand of Dark Enchantment", "", 65, 115, 8); //90
	public final static Weapon t9_wand = new TieredWand("Wand of Eternal Anguish", "", 70, 130, 9); //100
	public final static Weapon t10_wand = new TieredWand("Wand of Primordial Chaos", "", 80, 140, 10); //110
	public final static Weapon[] tiered_wands = new Weapon[] {t0_wand, t1_wand, t2_wand, t3_wand, t4_wand, t5_wand,
			t6_wand, t7_wand, t8_wand, t9_wand, t10_wand};
	
	public final static Weapon t0_staff = new TieredStaff("Simple Staff", "", 15, 25, 0); //40
	public final static Weapon t1_staff = new TieredStaff("Bolt Staff", "", 15, 30, 1); //45
	public final static Weapon t2_staff = new TieredStaff("Beam Staff", "", 20, 30, 2); //50
	public final static Weapon t3_staff = new TieredStaff("Staff of Silver Wings", "", 20, 35, 3); //55
	public final static Weapon t4_staff = new TieredStaff("Jeweled Staff", "", 25, 40, 4); //65
	public final static Weapon t5_staff = new TieredStaff("Staff of Horrors", "", 30, 45, 5); //75
	public final static Weapon t6_staff = new TieredStaff("Incineration Staff", "", 35, 50, 6); //85
	public final static Weapon t7_staff = new TieredStaff("Staff of Devastation", "", 40, 55, 7); //95
	public final static Weapon t8_staff = new TieredStaff("Staff of Deep Conflict", "", 45, 60, 8); //105
	public final static Weapon t9_staff = new TieredStaff("Staff of Vital Dissonance", "", 50, 65, 9); //115
	public final static Weapon t10_staff = new TieredStaff("Staff of Cosmic Unity", "", 55, 75, 10); //130
	public final static Weapon[] tiered_staves = new Weapon[] {t0_staff, t1_staff, t2_staff, t3_staff, t4_staff, t5_staff,
			t6_staff, t7_staff, t8_staff, t9_staff, t10_staff};
	
	public final static Weapon t0_crossbow = new TieredCrossbow("Simple Crossbow", "", 30, 50, 0); //40 * 0.7 = 28
	public final static Weapon t1_crossbow = new TieredCrossbow("Bloodward Crossbow", "", 40, 60, 1); //50 * 0.7 = 35
	public final static Weapon t2_crossbow = new TieredCrossbow("Blackened Crossbow", "", 50, 70, 2); //60 * 0.7 = 42
	public final static Weapon t3_crossbow = new TieredCrossbow("Crossbow of Silverlight", "", 60, 80, 3); //70 * 0.7 = 49
	public final static Weapon t4_crossbow = new TieredCrossbow("Amethyst Crossbow", "", 70, 90, 4); //80 * 0.7 = 56
	public final static Weapon t5_crossbow = new TieredCrossbow("Radiant Crossbow", "", 80, 105, 5); //92.5 -> 64.8
	public final static Weapon t6_crossbow = new TieredCrossbow("Skybreaker Crossbow", "", 90, 120, 6); //105 -> 73.5
	public final static Weapon t7_crossbow = new TieredCrossbow("Crossbow of Verdance", "", 100, 135, 7); //117.5 -> 82.3
	public final static Weapon t8_crossbow = new TieredCrossbow("Crossbow of Arcane Winds", "", 110, 150, 8); //130 -> 91
	public final static Weapon t9_crossbow = new TieredCrossbow("Crossbow of Covert Havens", "", 120, 170, 9); //145 -> 101.5
	public final static Weapon t10_crossbow = new TieredCrossbow("Crossbow of Aether Gales", "", 120, 200, 10); //160 -> 112
	public final static Weapon[] tiered_crossbows = new Weapon[] {t0_crossbow, t1_crossbow, t2_crossbow, t3_crossbow, t4_crossbow, t5_crossbow,
			t6_crossbow, t7_crossbow, t8_crossbow, t9_crossbow, t10_crossbow};
	
	public final static Weapon t0_bow = new TieredBow("Simple Bow", "", 10, 25, 0); //35
	public final static Weapon t1_bow = new TieredBow("Rosewood Bow", "", 10, 30, 1); //40
	public final static Weapon t2_bow = new TieredBow("Jagged Bow", "", 15, 30, 2); //45
	public final static Weapon t3_bow = new TieredBow("Winged Bow", "", 20, 35, 3); //55
	public final static Weapon t4_bow = new TieredBow("Enchanted Bow", "", 25, 40, 4); //65
	public final static Weapon t5_bow = new TieredBow("Bow of the Sun", "", 30, 45, 5); //75
	public final static Weapon t6_bow = new TieredBow("Mystic Bow", "", 35, 50, 6); //85
	public final static Weapon t7_bow = new TieredBow("Bow of Harmony", "", 40, 55, 7); //95
	public final static Weapon t8_bow = new TieredBow("Bow of Renewed Spirits", "", 45, 60, 8); //105
	public final static Weapon t9_bow = new TieredBow("Bow of Heavenly Tones", "", 50, 65, 9); //115
	public final static Weapon t10_bow = new TieredBow("Bow of Ascended Resonances", "", 55, 70, 10); //125
	public final static Weapon[] tiered_bows = new Weapon[] {t0_bow, t1_bow, t2_bow, t3_bow, t4_bow, t5_bow,
			t6_bow, t7_bow, t8_bow, t9_bow, t10_bow};
	
	public final static Weapon t0_sword = new TieredSword("Simple Sword", "", 35, 45, 0); //40
	public final static Weapon t1_sword = new TieredSword("Ironwood Sword", "", 40, 50, 1); //45
	public final static Weapon t2_sword = new TieredSword("Refined Sword", "", 45, 55, 2); //50
	public final static Weapon t3_sword = new TieredSword("Serrated Sword", "", 50, 70, 3); //60
	public final static Weapon t4_sword = new TieredSword("Thornsteel Sword", "", 60, 80, 4); //70
	public final static Weapon t5_sword = new TieredSword("Brightsword", "", 70, 90, 5); //80
	public final static Weapon t6_sword = new TieredSword("Mistwalker Sword", "", 75, 105, 6); //90
	public final static Weapon t7_sword = new TieredSword("Blacksteel Sword", "", 80, 120, 7); //100
	public final static Weapon t8_sword = new TieredSword("Soulcatcher Sword", "", 90, 130, 8); //110
	public final static Weapon t9_sword = new TieredSword("Sword of the Reclaimed", "", 100, 140, 9); //120
	public final static Weapon t10_sword = new TieredSword("Sword of Redemption", "", 110, 160, 10); //135
	public final static Weapon[] tiered_swords = new Weapon[] {t0_sword, t1_sword, t2_sword, t3_sword, t4_sword, t5_sword,
			t6_sword, t7_sword, t8_sword, t9_sword, t10_sword};
	
	public final static Weapon t0_axe = new TieredAxe("Simple Axe", "", 20, 40, 0); //30 * 1.2 = 36
	public final static Weapon t1_axe = new TieredAxe("Greystone Axe", "", 25, 45, 1); //35 * 1.2 = 42
	public final static Weapon t2_axe = new TieredAxe("Bonesteel Axe", "", 30, 50, 2); //40 * 1.2 = 48
	public final static Weapon t3_axe = new TieredAxe("Bloodforge Axe", "", 35, 55, 3); //45 * 1.2 = 54
	public final static Weapon t4_axe = new TieredAxe("Axe of Corruption", "", 40, 60, 4); //50 * 1.2 = 60
	public final static Weapon t5_axe = new TieredAxe("Axe of Morning Light", "", 45, 75, 5); //60 * 1.2 = 72
	public final static Weapon t6_axe = new TieredAxe("Ravenheart Axe", "", 50, 90, 6); //70 * 1.2 = 84
	public final static Weapon t7_axe = new TieredAxe("Trecharous Axe", "", 60, 100, 7); //80 * 1.2 = 96
	public final static Weapon t8_axe = new TieredAxe("Crownmaker Axe", "", 70, 110, 8); //90 * 1.2 = 108
	public final static Weapon t9_axe = new TieredAxe("Scalerender Axe", "", 80, 120, 9); //100 * 1.2 = 120
	public final static Weapon t10_axe = new TieredAxe("Godslayer Axe", "", 95, 125, 10); //110 * 1.2 = 132
	public final static Weapon[] tiered_axes = new Weapon[] {t0_axe, t1_axe, t2_axe, t3_axe, t4_axe, t5_axe, t6_axe,
			t7_axe, t8_axe, t9_axe, t10_axe};
	
	public final static Equipment Spirit_Staff = new SpiritStaff(); //tier 5.5
	public final static Equipment Brookchaser_Bow = new BrookchaserBow(); //tier 6.5
	public final static Equipment Prowlers_Claw = new ProwlersClaw(); //tier 5 sidegrade
	public final static Equipment Spectral_Fire_Wand = new SpectralFireWand(); //tier 9 damage
	public final static Equipment Trecharous_Crossbow = new TrecharousCrossbow(); //tier 8 sidegrade
	public final static Equipment Crestfeller_Axe = new CrestfellerAxe(); //tier 8 sidegrade
	public final static Equipment Blade_Of_The_Mindless = new BladeOfTheMindless(); //tier 10.5
	public final static Equipment Bow_Of_The_Fractured = new BowOfTheFractured(); //tier 10
	public final static Equipment Wand_Of_The_Fallen = new WandOfTheFallen(); //tier 10

	public int getFireCooldown(int dex) {
		if(Controller.chr.getEffects().contains(new AllyDazed())){
			return (int)(167.0/2.5);
		}
		else {
			double aps = (2.5 + 0.08 * dex) * this.fireRate/100.0;
			if(Controller.chr.getEffects().contains(new AllyBerserk())) {
				aps *= 1.35;
			}
			return (int)(167.0/aps);
		}
	}
	
	public int convert(int dmg, int atk) {
		double num = (0.5 + (double)Controller.chr.getAttack() * 0.025) * dmg;
		if(Controller.chr.getEffects().contains(new AllyFervor())){
			return (int)(1.25 * num);
		}
		return (int)num;
	}
	
	public Weapon(String title, String desc) {
		super(title, desc);
		this.type = "weapon";
	}	
}



