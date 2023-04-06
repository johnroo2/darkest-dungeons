package equipment.ability;

import equipment.st.SerratedToothblades;
import equipment.st.ShriekcallersVisage;
import equipment.st.TomeOfClarity;
import equipment.st.TuskrenderFlurry;
import equipment.st.ChampionsHelm;
import equipment.st.CrystallineScepter;
import equipment.st.DefilersSkull;
import equipment.st.OathbreakerQuiver;
import equipment.st.Rivermaker;
import equipment.st.SentinelsQuiver;
import project.Equipment;

public abstract class Ability extends Equipment{
	protected int castRate = 83;
	
	public final static Ability t0_scepter = new TieredScepter("Simple Scepter", "", 50, 120, 20, 3, 4, 0); 
	public final static Ability t1_scepter = new TieredScepter("Practical Scepter", "", 60, 180, 25, 4, 5, 1);
	public final static Ability t2_scepter = new TieredScepter("Scepter of Thunder", "", 70, 240, 30, 4, 6, 2);
	public final static Ability t3_scepter = new TieredScepter("Stormcaller's Scepter", "", 80, 320, 35, 5, 7, 3); 
	public final static Ability t4_scepter = new TieredScepter("Skybound Scepter", "", 90, 400, 40, 5, 8, 4); 
	public final static Ability t5_scepter = new TieredScepter("Arclight Scepter", "", 100, 500, 45, 6, 9, 5); 
	public final static Ability t6_scepter = new TieredScepter("Scepter of Coruscating Illumination", "", 110, 600, 50, 7, 10, 6); 
	public final static Ability[] tiered_scepters = new Ability[] {t0_scepter, t1_scepter, t2_scepter, t3_scepter, t4_scepter, 
			t5_scepter, t6_scepter};
	
	public final static Ability t0_skull =  new TieredSkull("Simple Skull", "", 40, 45, 30, 1.6, 0);
	public final static Ability t1_skull =  new TieredSkull("Necrotic Skull", "", 50, 75, 40, 2, 1);
	public final static Ability t2_skull =  new TieredSkull("Siphoning Skull", "", 60, 105, 50, 2.4, 2);
	public final static Ability t3_skull =  new TieredSkull("Lifedrinker Skull", "", 70, 135, 60, 2.8, 3);
	public final static Ability t4_skull =  new TieredSkull("Skull of Mortality", "", 80, 165, 70, 3.2, 4);
	public final static Ability t5_skull =  new TieredSkull("Transcension Skull", "", 90, 200, 80, 3.6, 5);
	public final static Ability t6_skull =  new TieredSkull("Deathbringer Skull", "", 100, 240, 90, 4, 6);
	public final static Ability[] tiered_skulls = new Ability[] {t0_skull, t1_skull, t2_skull, t3_skull, t4_skull, 
			t5_skull, t6_skull};
	
	public final static Ability t0_quiver =  new TieredQuiver("Simple Quiver", "", 40, 40, 80, 1.25, 0);
	public final static Ability t1_quiver =  new TieredQuiver("Precision Quiver", "", 50, 60, 110, 1.5, 1);
	public final static Ability t2_quiver =  new TieredQuiver("Sharpshooter Quiver", "", 60, 90, 150, 1.75, 2); 
	public final static Ability t3_quiver =  new TieredQuiver("Bloodborne Quiver", "", 70, 120, 200, 2, 3); 
	public final static Ability t4_quiver =  new TieredQuiver("Luminous Quiver", "", 80, 150, 270, 2.25, 4); 
	public final static Ability t5_quiver =  new TieredQuiver("Quiver of Tranquility", "", 90, 185, 325, 2.5, 5); 
	public final static Ability t6_quiver =  new TieredQuiver("Quiver of Everlasting Silence", "", 100, 225, 375, 2.75, 6); 
	public final static Ability[] tiered_quivers = new Ability[] {t0_quiver, t1_quiver, t2_quiver, t3_quiver, t4_quiver, 
			t5_quiver, t6_quiver};
	
	public final static Ability t0_blades = new TieredBlades("Simple Blades", "", 20, 60, 100, 1.75, 0);
	public final static Ability t1_blades = new TieredBlades("Sharpened Blades", "", 24, 90, 130, 2.25, 1);
	public final static Ability t2_blades = new TieredBlades("Lustruous Blades", "", 28, 120, 160, 2.75, 2);
	public final static Ability t3_blades = new TieredBlades("Stalker's Blades", "", 32, 150, 200, 3.25, 3);
	public final static Ability t4_blades = new TieredBlades("Obsidian Blades", "", 36, 180, 250, 3.75, 4);
	public final static Ability t5_blades = new TieredBlades("Blades of Retribution", "", 40, 210, 300, 4.25, 5);
	public final static Ability t6_blades = new TieredBlades("Blades of Nemesis", "", 45, 240, 360, 5, 6);
	public final static Ability[] tiered_blades = new Ability[] {t0_blades, t1_blades, t2_blades, t3_blades, t4_blades, 
			t5_blades, t6_blades};
	
	public final static Ability t0_helm = new TieredHelm("Simple Helm", "", 55, 330, 2, 6, 0);
	public final static Ability t1_helm = new TieredHelm("Silver Helm", "", 60, 375, 3, 6.75, 1);
	public final static Ability t2_helm = new TieredHelm("Dusky Iron Helm", "", 65, 420, 4, 7.5, 2);
	public final static Ability t3_helm = new TieredHelm("Seasteel Helm", "", 70, 465, 5, 8.25, 3);
	public final static Ability t4_helm = new TieredHelm("Helm of the Royal Guard", "", 75, 510, 6, 9, 4);
	public final static Ability t5_helm = new TieredHelm("Faithful Centurion's Helm", "", 80, 555, 7, 9.75, 5);
	public final static Ability t6_helm = new TieredHelm("Kingkiller Helm", "", 85, 600, 8, 10.5, 6);
	public final static Ability[] tiered_helms = new Ability[] {t0_helm, t1_helm, t2_helm, t3_helm, t4_helm, t5_helm, 
			t6_helm};
	
	public final static Ability t0_tome = new TieredTome("Simple Tome", "", 50, 35, 330, 1.5, 0);
	public final static Ability t1_tome = new TieredTome("Healing Tome", "", 60, 50, 375, 1.8, 1);
	public final static Ability t2_tome = new TieredTome("Renewing Tome", "", 70, 65, 420, 2.1, 2);
	public final static Ability t3_tome = new TieredTome("Tome of Rejuvination", "", 80, 85, 465, 2.4, 3);
	public final static Ability t4_tome = new TieredTome("Tome of Divine Favour", "", 90, 95, 510, 2.7, 4);
	public final static Ability t5_tome = new TieredTome("Tome of Hallowed Calling", "", 100, 110, 555, 3, 5);
	public final static Ability t6_tome = new TieredTome("Tome of Eternal Life", "", 110, 125, 600, 3.3, 6);
	public final static Ability[] tiered_tomes = new Ability[] {t0_tome, t1_tome, t2_tome, t3_tome, t4_tome, t5_tome,
			t6_tome};
	
	public final static Equipment Serrated_Toothblades = new SerratedToothblades();
	public final static Equipment Tome_Of_Clarity = new TomeOfClarity();
	public final static Equipment Rivermaker = new Rivermaker();
	public final static Equipment Defilers_Skull = new DefilersSkull();
	public final static Equipment Champions_Helm = new ChampionsHelm();
	public final static Equipment Oathbreaker_Quiver = new OathbreakerQuiver();
	public final static Equipment Shriekcallers_Visage = new ShriekcallersVisage();
	public final static Equipment Sentinels_Quiver = new SentinelsQuiver();
	public final static Equipment Crystalline_Scepter = new CrystallineScepter();
	public final static Equipment Tuskrender_Flurry = new TuskrenderFlurry();
	
	protected int manaCost;
	
	public Ability(String title, String desc, int manaCost) {
		super(title, desc);
		this.manaCost = manaCost;
		this.type = "ability";
	}
	
	public int getManaCost() {return this.manaCost;}
	public int getCastRate() {return this.castRate;}
}
