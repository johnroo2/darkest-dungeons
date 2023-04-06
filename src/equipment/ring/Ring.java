package equipment.ring;

import control.Controller;
import project.Equipment;

public abstract class Ring extends Equipment{
	public final static Ring t0_attackring = new AttackRing("Ring of Attack", "", 10, 3, 0);
	public final static Ring t1_attackring = new AttackRing("Ring of Greater Attack", "", 20, 4, 1);
	public final static Ring t2_attackring = new AttackRing("Ring of Superior Attack", "", 30, 5, 2);
	public final static Ring t3_attackring = new AttackRing("Ring of Ascended Attack", "", 40, 6, 3);
	public final static Ring t4_attackring = new AttackRing("Ring of Exalted Attack", "", 50, 8, 4);
	public final static Ring[] tiered_attackrings = new Ring[] {t0_attackring, t1_attackring, 
			t2_attackring, t3_attackring, t4_attackring};
	
	public final static Ring t0_critring = new CritRing("Ring of Agility", "", 10, 3, 0);
	public final static Ring t1_critring = new CritRing("Ring of Greater Agility", "", 20, 4, 1);
	public final static Ring t2_critring = new CritRing("Ring of Superior Agility", "", 30, 5, 2);
	public final static Ring t3_critring = new CritRing("Ring of Ascended Agility", "", 40, 6, 3);
	public final static Ring t4_critring = new CritRing("Ring of Exalted Agility", "", 50, 8, 4);
	public final static Ring[] tiered_critrings = new Ring[] {t0_critring, t1_critring, 
			t2_critring, t3_critring, t4_critring};
	
	public final static Ring t0_defensering = new DefenseRing("Ring of Defense", "", 10, 3, 0);
	public final static Ring t1_defensering = new DefenseRing("Ring of Greater Defense", "", 20, 4, 1);
	public final static Ring t2_defensering = new DefenseRing("Ring of Superior Defense", "", 30, 5, 2);
	public final static Ring t3_defensering = new DefenseRing("Ring of Ascended Defense", "", 40, 6, 3);
	public final static Ring t4_defensering = new DefenseRing("Ring of Exalted Defense", "", 50, 8, 4);
	public final static Ring[] tiered_defenserings = new Ring[] {t0_defensering, t1_defensering, 
			t2_defensering, t3_defensering, t4_defensering};
	
	public final static Ring t0_dexring = new DexRing("Ring of Dexterity", "", 10, 3, 0);
	public final static Ring t1_dexring = new DexRing("Ring of Greater Dexterity", "", 20, 4, 1);
	public final static Ring t2_dexring = new DexRing("Ring of Superior Dexterity", "", 30, 5, 2);
	public final static Ring t3_dexring = new DexRing("Ring of Ascended Dexterity", "", 40, 6, 3);
	public final static Ring t4_dexring = new DexRing("Ring of Exalted Dex", "", 50, 8, 4);
	public final static Ring[] tiered_dexrings = new Ring[] {t0_dexring, t1_dexring, 
			t2_dexring, t3_dexring, t4_dexring};
	
	public final static Ring t0_healthring = new HealthRing("Ring of Life", "", 20, 0);
	public final static Ring t1_healthring = new HealthRing("Ring of Greater Life", "", 40, 1);
	public final static Ring t2_healthring = new HealthRing("Ring of Superior Life", "", 60, 2);
	public final static Ring t3_healthring = new HealthRing("Ring of Ascended Life", "", 80, 3);
	public final static Ring t4_healthring = new HealthRing("Ring of Exalted Life", "", 100, 4);
	public final static Ring[] tiered_healthrings = new Ring[] {t0_healthring, t1_healthring, 
			t2_healthring, t3_healthring, t4_healthring};
	
	public final static Ring t0_manaring = new ManaRing("Ring of Mana", "", 10, 15, 0);
	public final static Ring t1_manaring = new ManaRing("Ring of Greater Mana", "", 20, 30, 1);
	public final static Ring t2_manaring = new ManaRing("Ring of Superior Mana", "", 30, 45, 2);
	public final static Ring t3_manaring = new ManaRing("Ring of Ascended Mana", "", 40, 60, 3);
	public final static Ring t4_manaring = new ManaRing("Ring of Exalted Mana", "", 50, 75, 4);
	public final static Ring[] tiered_manarings = new Ring[] {t0_manaring, t1_manaring, 
			t2_manaring, t3_manaring, t4_manaring};
	
	public final static Ring t0_speedring = new SpeedRing("Ring of Speed", "", 10, 4, 0);
	public final static Ring t1_speedring = new SpeedRing("Ring of Greater Speed", "", 20, 5, 1);
	public final static Ring t2_speedring = new SpeedRing("Ring of Superior Speed", "", 30, 6, 2);
	public final static Ring t3_speedring = new SpeedRing("Ring of Ascended Speed", "", 40, 8, 3);
	public final static Ring t4_speedring = new SpeedRing("Ring of Exalted Speed", "", 50, 10, 4);
	public final static Ring[] tiered_speedrings = new Ring[] {t0_speedring, t1_speedring, 
			t2_speedring, t3_speedring, t4_speedring};
	
	public final static Ring t0_splring = new SplRing("Ring of Sorcery", "", 10, 8, 0);
	public final static Ring t1_splring = new SplRing("Ring of Greater Sorcery", "", 20, 12, 1);
	public final static Ring t2_splring = new SplRing("Ring of Superior Sorcery", "", 30, 16, 2);
	public final static Ring t3_splring = new SplRing("Ring of Ascended Sorcery", "", 40, 20, 3);
	public final static Ring t4_splring = new SplRing("Ring of Exalted Sorcery", "", 50, 25, 4);
	public final static Ring[] tiered_splrings = new Ring[] {t0_splring, t1_splring, 
			t2_splring, t3_splring, t4_splring};
	
	public final static Ring t0_vitring = new VitRing("Ring of Vitality", "", 10, 4, 0);
	public final static Ring t1_vitring = new VitRing("Ring of Greater Vitality", "", 20, 5, 1);
	public final static Ring t2_vitring = new VitRing("Ring of Superior Vitality", "", 30, 6, 2);
	public final static Ring t3_vitring = new VitRing("Ring of Ascended Vitality", "", 40, 8, 3);
	public final static Ring t4_vitring = new VitRing("Ring of Exalted Vitality", "", 50, 10, 4);
	public final static Ring[] tiered_vitrings = new Ring[] {t0_vitring, t1_vitring, 
			t2_vitring, t3_vitring, t4_vitring};
	
	public final static Ring t0_wisring = new WisRing("Ring of Wisdom", "", 10, 4, 0);
	public final static Ring t1_wisring = new WisRing("Ring of Greater Wisdom", "", 20, 5, 1);
	public final static Ring t2_wisring = new WisRing("Ring of Superior Wisdom", "", 30, 6, 2);
	public final static Ring t3_wisring = new WisRing("Ring of Ascended Wisdom", "", 40, 8, 3);
	public final static Ring t4_wisring = new WisRing("Ring of Exalted Wisdom", "", 50, 10, 4);
	public final static Ring[] tiered_wisrings = new Ring[] {t0_wisring, t1_wisring, 
			t2_wisring, t3_wisring, t4_wisring};
	
	public final static Equipment Bone_Ring = new BoneRing();
	public final static Equipment Lakestrider_Ring = new LakestriderRing();
	public final static Equipment Ring_Of_Arcane_Malice = new RingOfArcaneMalice();
	public final static Equipment Lawless_Amulet = new LawlessAmulet();
	public final static Equipment Tear_Of_The_Hopeless = new TearOfTheHopeless();
	public final static Equipment Mechanical_Ring = new MechanicalRing();
	
	public Ring(String title, String desc) {
		super(title, desc);
		this.type = "ring";
	}
}

class BoneRing extends Ring{
	public BoneRing() {
		super("Bone Ring", "");
		this.img = ART_BONERING;
		this.tierDisplay = "ST";
		
		this.desc = new String[] {"+4 ATK", "+4 WIS"};
	}
	@Override
	public void equip() {
		Controller.chr.setAttack(Controller.chr.getAttack() + 4);
		Controller.chr.setWisdom(Controller.chr.getWisdom() + 4);
	}
	@Override
	public void dequip() {
		Controller.chr.setAttack(Controller.chr.getAttack() - 4);
		Controller.chr.setWisdom(Controller.chr.getWisdom() - 4);	
	}
}

class LakestriderRing extends Ring{
	public LakestriderRing() {
		super("Lakestrider Ring", "");
		this.img = ART_LAKESTRIDERRING;
		this.tierDisplay = "ST";
		
		this.desc = new String[] {"+4 DEF", "+5 DEX", "+20 MP"};
	}
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 4);
		Controller.chr.setDexterity(Controller.chr.getDexterity() + 5);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() + 20);
	}
	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 4);
		Controller.chr.setDexterity(Controller.chr.getDexterity() - 5);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() - 20);
	}
}

class LawlessAmulet extends Ring{
	public LawlessAmulet() {
		super("Lawless Amulet", "");
		this.img = ART_LAWLESSAMULET;
		this.tierDisplay = "ST";
		
		this.desc = new String[] {"-5 DEF", "+14 ATK", "+25 HP", "+25 MP"};
	}
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 5);
		Controller.chr.setAttack(Controller.chr.getAttack() + 14);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() + 25);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() + 25);
	}
	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 5);
		Controller.chr.setAttack(Controller.chr.getAttack() - 14);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() - 25);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() - 25);
	}
}

class TearOfTheHopeless extends Ring{
	public TearOfTheHopeless() {
		super("Tear of the Hopeless", "");
		this.img = ART_TEAROFTHEHOPELESS;
		this.tierDisplay = "ST";
		
		this.desc = new String[] {"+5 DEF", "+7 DEX", "+40 HP"};
	}
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 5);
		Controller.chr.setDexterity(Controller.chr.getDexterity() + 7);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() + 40);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 5);
		Controller.chr.setDexterity(Controller.chr.getDexterity() - 7);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() - 40);
	}
}

class MechanicalRing extends Ring{
	public MechanicalRing() {
		super("Mechanical Ring", "");
		this.img = ART_MECHANICALRING;
		this.tierDisplay = "ST";
		
		this.desc = new String[] {"For those who don't have any...", "+30 DEF", "+30 VIT", "+30 WIS", "+10 ATK", "+10 DEX", "+10 SPD", "+120 MP", "+120 HP", "Doubles all XP gain"};
	}
	@Override
	public void equip() {
		Controller.chr.setDefense(Controller.chr.getDefense() + 30);
		Controller.chr.setVitality(Controller.chr.getVitality() + 30);
		Controller.chr.setWisdom(Controller.chr.getWisdom() + 30);
		Controller.chr.setAttack(Controller.chr.getAttack() + 10);
		Controller.chr.setDexterity(Controller.chr.getDexterity() + 10);
		Controller.chr.setSpeed(Controller.chr.getSpeed() + 10);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() + 120);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() + 120);
	}
	
	@Override
	public void dequip() {
		Controller.chr.setDefense(Controller.chr.getDefense() - 30);
		Controller.chr.setVitality(Controller.chr.getVitality() - 30);
		Controller.chr.setWisdom(Controller.chr.getWisdom() - 30);
		Controller.chr.setAttack(Controller.chr.getAttack() - 10);
		Controller.chr.setDexterity(Controller.chr.getDexterity() - 10);
		Controller.chr.setSpeed(Controller.chr.getSpeed() - 10);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() - 120);
		Controller.chr.setMaxHealth(Controller.chr.getMaxHealth() - 120);
	}
}

class RingOfArcaneMalice extends Ring{
	private static int COOLDOWN = 500;
	private int ticks = 0;
	
	public RingOfArcaneMalice() {
		super("Ring of Arcane Malice", "");
		this.img = ART_RINGOFARCANEMALICE;
		this.tierDisplay = "ST";
		
		this.desc = new String[] {"+5 WIS", "+40 MP", "Shot Landing: Restore 20 MP", "Cooldown: 3 seconds"};
	}
	@Override
	public void passive(String event) {
		if(ticks > 0) {
			ticks--;
		}
		if(event.startsWith("attack") && ticks <= 0 && Controller.chr.getCurrentMana() < Controller.chr.getMaxMana()) {
			ticks = COOLDOWN;
			Controller.chr.manaHeal(20);
		}
	}
	@Override
	public void equip() {
		Controller.chr.setWisdom(Controller.chr.getWisdom() + 5);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() + 40);
	}
	@Override
	public void dequip() {
		Controller.chr.setWisdom(Controller.chr.getWisdom() - 5);
		Controller.chr.setMaxMana(Controller.chr.getMaxMana() - 40);
	}
}
