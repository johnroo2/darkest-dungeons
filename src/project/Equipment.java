//Equipment.java
//Parent class for all items

package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import control.Controller;
import equipment.ability.Ability;
import equipment.armour.Armour;
import equipment.ring.Ring;
import equipment.weapon.Weapon;
import player.Sidebar;

public class Equipment {
	//static variables B)
	protected static BufferedImage IMGS_LOOTBAG_BLUE;
	protected static BufferedImage IMGS_LOOTBAG_ORANGE;
	
	protected final static ArrayList<BufferedImage> ART_STAFF = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_WAND = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_SCEPTER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_ROBE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_LEATHER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_ARMOUR = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_SKULL = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_CROSSBOW = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_BOW = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_SWORD = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_AXE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_QUIVER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_BLADES = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_HELM = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_TOME = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_ATTACKRING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_CRITRING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_DEFENSERING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_DEXRING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_HEALTHRING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_MANARING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_SPEEDRING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_SPLRING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_VITRING = new ArrayList<>();
	protected final static ArrayList<BufferedImage> ART_WISRING = new ArrayList<>();
	
	protected static BufferedImage ART_BONERING;
	protected static BufferedImage ART_SPIRITSTAFF;
	protected static BufferedImage ART_CRABCARAPACE;
	protected static BufferedImage ART_ROYALSCORPIONHIDE;
	protected static BufferedImage ART_WETLANDSROBE;
	protected static BufferedImage ART_BROOKCHASERBOW;
	protected static BufferedImage ART_PROWLERSCLAW;
	protected static BufferedImage ART_SERRATEDTOOTHBLADES;
	protected static BufferedImage ART_TOMEOFCLARITY;
	protected static BufferedImage ART_RIVERMAKER;
	protected static BufferedImage ART_LAKESTRIDERRING;
	protected static BufferedImage ART_RUNEFORGEMANTLE;
	protected static BufferedImage ART_BLACKFORGEARMOUR;
	protected static BufferedImage ART_LIFEFORGECHAINMAIL;
	protected static BufferedImage ART_RINGOFARCANEMALICE;
	protected static BufferedImage ART_DEFILERSKULL;
	protected static BufferedImage ART_SPECTRALFIREWAND;
	protected static BufferedImage ART_TRECHAROUSCROSSBOW;
	protected static BufferedImage ART_CHAMPIONSHELM;
	protected static BufferedImage ART_OATHBREAKERQUIVER;
	protected static BufferedImage ART_CRESTFELLERAXE;
	protected static BufferedImage ART_LAWLESSAMULET;
	protected static BufferedImage ART_FROSTSWORNROBE;
	protected static BufferedImage ART_STALWARTSHIELDPLATE;
	protected static BufferedImage ART_SHRIEKCALLERSVISAGE;
	protected static BufferedImage ART_BLADEOFTHEMINDLESS;
	protected static BufferedImage ART_BOWOFTHEFRACTURED;
	protected static BufferedImage ART_WANDOFTHEFALLEN;
	protected static BufferedImage ART_TEAROFTHEHOPELESS;
	protected static BufferedImage ART_TUSKRENDERFLURRY;
	protected static BufferedImage ART_SENTINELSQUIVER;
	protected static BufferedImage ART_CRYSTALLINESCEPTER;
	protected static BufferedImage ART_MECHANICALRING;
	
	private final static int [] positions = new int[] {354, 474, 544, 644, 714};
	
	//instance variables
	protected String title, type, subtype, tierDisplay;
	
	protected BufferedImage img;
	protected String[] desc;
	
	//constructor
	public Equipment(String title, String desc) {
		this.title = title;
	}
	
	//uploads all files B)
	public static void upload() throws IOException{
		IMGS_LOOTBAG_BLUE = ImageIO.read(new File("./assets/lootbags/lootbag0000.png"));
		IMGS_LOOTBAG_ORANGE = ImageIO.read(new File("./assets/lootbags/lootbag0001.png"));
		for(int i = 0; i <= 10; i++) {ART_STAFF.add(ImageIO.read(new File(String.format("./items/tieredstaff/art/t_staffart00%02d.png", i))));}
		for(int i = 0; i <= 10; i++) {ART_WAND.add(ImageIO.read(new File(String.format("./items/tieredwand/art/t_wandart00%02d.png", i))));}
		for(int i = 0; i <= 6; i++) {ART_SCEPTER.add(ImageIO.read(new File("./items/tieredscepter/art/t_scepterart000"+i+".png")));}
		for(int i = 0; i <= 6; i++) {ART_QUIVER.add(ImageIO.read(new File("./items/tieredquiver/art/t_quiverart000"+i+".png")));}
		for(int i = 0; i <= 6; i++) {ART_SKULL.add(ImageIO.read(new File("./items/tieredskull/art/t_skullart000"+i+".png")));}
		for(int i = 0; i <= 10; i++) {ART_BOW.add(ImageIO.read(new File(String.format("items/tieredbow/art/t_bowart00%02d.png", i))));}
		for(int i = 0; i <= 10; i++) {ART_CROSSBOW.add(ImageIO.read(new File(String.format("items/tieredcrossbow/art/t_crossbowart00%02d.png", i))));}
		for(int i = 0; i <= 6; i++) {ART_BLADES.add(ImageIO.read(new File("./items/tieredblade/art/t_bladeart000"+i+".png")));}
		for(int i = 0; i <= 11; i++) {ART_ROBE.add(ImageIO.read(new File(String.format("items/tieredrobe/t_robeart00%02d.png", i))));}
		for(int i = 0; i <= 11; i++) {ART_ARMOUR.add(ImageIO.read(new File(String.format("items/tieredarmour/t_armourart00%02d.png", i))));}
		for(int i = 0; i <= 11; i++) {ART_LEATHER.add(ImageIO.read(new File(String.format("items/tieredleather/t_leatherart00%02d.png", i))));}
		for(int i = 0; i <= 10; i++) {ART_SWORD.add(ImageIO.read(new File(String.format("items/tieredsword/art/t_swordart00%02d.png", i))));}
		for(int i = 0; i <= 10; i++) {ART_AXE.add(ImageIO.read(new File(String.format("items/tieredaxe/art/t_axeart00%02d.png", i))));}
		for(int i = 0; i <= 6; i++) {ART_HELM.add(ImageIO.read(new File("./items/tieredhelm/art/t_helmart000"+i+".png")));}
		for(int i = 0; i <= 6; i++) {ART_TOME.add(ImageIO.read(new File("./items/tieredtome/art/t_tomeart000"+i+".png")));}		
		for(int i = 0; i <= 4; i++) {ART_ATTACKRING.add(ImageIO.read(new File("./items/tieredrings/attackring/t_attackring000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_CRITRING.add(ImageIO.read(new File("./items/tieredrings/critring/t_critring000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_DEFENSERING.add(ImageIO.read(new File("./items/tieredrings/defensering/t_defensering000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_DEXRING.add(ImageIO.read(new File("./items/tieredrings/dexring/t_dexring000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_HEALTHRING.add(ImageIO.read(new File("./items/tieredrings/healthring/t_healthring000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_MANARING.add(ImageIO.read(new File("./items/tieredrings/manaring/t_manaring000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_SPEEDRING.add(ImageIO.read(new File("./items/tieredrings/speedring/t_speedring000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_SPLRING.add(ImageIO.read(new File("./items/tieredrings/splring/t_splring000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_VITRING.add(ImageIO.read(new File("./items/tieredrings/vitring/t_vitring000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {ART_WISRING.add(ImageIO.read(new File("./items/tieredrings/wisring/t_wisring000"+i+".png")));}
		
		ART_BONERING = ImageIO.read(new File("./items/st/rings/boneringart0000.png"));
		ART_SPIRITSTAFF = ImageIO.read(new File("./items/st/weapons/spiritstaffart0000.png"));
		ART_CRABCARAPACE = ImageIO.read(new File("./items/st/armours/crabcarapaceart0000.png"));
		ART_ROYALSCORPIONHIDE = ImageIO.read(new File("./items/st/armours/royalscorpionhideart0000.png"));
		ART_WETLANDSROBE = ImageIO.read(new File("./items/st/armours/wetlandsrobeart0000.png"));
		ART_BROOKCHASERBOW = ImageIO.read(new File("./items/st/weapons/brookchaserbowart0000.png"));
		ART_PROWLERSCLAW = ImageIO.read(new File("./items/st/weapons/prowlersclawart0000.png"));
		ART_SERRATEDTOOTHBLADES = ImageIO.read(new File("./items/st/abilities/serratedtoothbladesart0000.png"));
		ART_TOMEOFCLARITY = ImageIO.read(new File("./items/st/abilities/tomeofclarityart0000.png"));
		ART_RIVERMAKER = ImageIO.read(new File("./items/st/abilities/rivermakerart0000.png"));
		ART_LAKESTRIDERRING = ImageIO.read(new File("./items/st/rings/lakestriderringart0000.png"));
		ART_RUNEFORGEMANTLE = ImageIO.read(new File("./items/st/armours/runeforgemantleart0000.png"));
		ART_BLACKFORGEARMOUR = ImageIO.read(new File("./items/st/armours/blackforgearmourart0000.png"));
		ART_LIFEFORGECHAINMAIL = ImageIO.read(new File("./items/st/armours/lifeforgechainmailart0000.png"));
		ART_RINGOFARCANEMALICE = ImageIO.read(new File("./items/st/rings/ringofarcanemaliceart0000.png"));
		ART_DEFILERSKULL = ImageIO.read(new File("./items/st/abilities/defilerskullart0000.png"));
		ART_SPECTRALFIREWAND = ImageIO.read(new File("./items/st/weapons/spectralfirewandart0000.png"));
		ART_TRECHAROUSCROSSBOW = ImageIO.read(new File("./items/st/weapons/trecharouscrossbowart0000.png"));
		ART_CHAMPIONSHELM = ImageIO.read(new File("./items/st/abilities/championshelmart0000.png"));
		ART_OATHBREAKERQUIVER = ImageIO.read(new File("./items/st/abilities/oathbreakerquiverart0000.png"));
		ART_CRESTFELLERAXE = ImageIO.read(new File("./items/st/weapons/crestfelleraxeart0000.png"));
		ART_LAWLESSAMULET = ImageIO.read(new File("./items/st/rings/lawlessamuletart0000.png"));
		ART_FROSTSWORNROBE = ImageIO.read(new File("./items/st/armours/frostswornrobeart0000.png"));
		ART_STALWARTSHIELDPLATE = ImageIO.read(new File("./items/st/armours/stalwartshieldplateart0000.png"));
		ART_SHRIEKCALLERSVISAGE = ImageIO.read(new File("./items/st/abilities/shriekcallersvisageart0000.png"));
		ART_BLADEOFTHEMINDLESS = ImageIO.read(new File("./items/st/weapons/bladeofthemindlessart0000.png"));
		ART_BOWOFTHEFRACTURED = ImageIO.read(new File("./items/st/weapons/bowofthefracturedart0000.png"));
		ART_WANDOFTHEFALLEN = ImageIO.read(new File("./items/st/weapons/wandofthefallenart0000.png"));
		ART_TEAROFTHEHOPELESS = ImageIO.read(new File("./items/st/rings/tearofthehopelessart0000.png"));
		ART_TUSKRENDERFLURRY = ImageIO.read(new File("./items/st/abilities/tuskrenderflurryart0000.png"));
		ART_SENTINELSQUIVER = ImageIO.read(new File("./items/st/abilities/sentinelsquiverart0000.png"));
		ART_CRYSTALLINESCEPTER = ImageIO.read(new File("./items/st/abilities/crystallinescepterart0000.png"));
		ART_MECHANICALRING = ImageIO.read(new File("./items/st/rings/mechanicalringart0000.png"));
	}
	
	//various methods for getting random tiered items (given a tier)
	public static Equipment getTieredWeapon(int tier) {
		return getItem(new Equipment[] {Weapon.tiered_wands[tier], Weapon.tiered_staves[tier], Weapon.tiered_bows[tier], 
				Weapon.tiered_crossbows[tier], Weapon.tiered_swords[tier], Weapon.tiered_axes[tier]});
	}
	
	public static Equipment getTieredAbility(int tier) {
		return getItem(new Equipment[] {Ability.tiered_scepters[tier], Ability.tiered_skulls[tier], Ability.tiered_quivers[tier], 
				Ability.tiered_blades[tier], Ability.tiered_helms[tier], Ability.tiered_tomes[tier]});
	}
	
	public static Equipment getTieredArmour(int tier) {
		return getItem(new Equipment[] {Armour.tiered_robes[tier], Armour.tiered_leathers[tier], Armour.tiered_armours[tier]});
	}
	
	public static Equipment getTieredRing(int tier) {
		return getItem(new Equipment[] {Ring.tiered_attackrings[tier], Ring.tiered_critrings[tier], Ring.tiered_defenserings[tier],
				Ring.tiered_dexrings[tier], Ring.tiered_healthrings[tier], Ring.tiered_manarings[tier], Ring.tiered_speedrings[tier],
				Ring.tiered_splrings[tier], Ring.tiered_vitrings[tier], Ring.tiered_wisrings[tier]});
	}
	
	//gets a random item from an array
	public static Equipment getItem(Equipment[] e) {
		return e[Controller.random.nextInt(0, e.length)];
	}
	
	public void active() {
		
	}
	public void passive(String event) {
		
	}
	public void equip() {
		
	}
	public void dequip() {
		
	}
	
	//Desc: draws the description card
	//Param: Graphics g, ImageObserver i, int selected position
	//Return: none
	
	//starts: 814, + 70
	//354, 474, 544, 644, 714
	public void drawDescription(Graphics g, ImageObserver i, int pos) {
		int x = 814 + (70 * (pos % 4));
		int y = positions[pos/4];
		g.setColor(new Color(0, 0, 0, 225));
		x -= 375;
		y -= 200;
		g.fillRect(x, y, 390, 250);
		g.setFont(new Font("Courier New", Font.BOLD, 16));
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.WHITE);
		y += 20;
		g.drawImage(this.getImage(), x + 20, y, 32, 32, i);
		g.drawString(this.title, x + 70, y + fm.getHeight()/2);
		y += fm.getHeight();
		if(this.tierDisplay.equals("ST")) {
			g.setColor(Sidebar.getStGold());
			g.drawString("Specialized", x + 70, y + fm.getHeight()/2);
			g.setColor(Color.WHITE);
		}
		else {
			g.drawString("Tier " + this.tierDisplay.substring(1), x + 70, y + fm.getHeight()/2);
		}
		y += fm.getHeight();
		g.setFont(new Font("Courier New", Font.BOLD, 14));
		fm = g.getFontMetrics();
		if(!this.type.equals("consumable")) {
			if(this.type.equals("ring") || Controller.chr.getSidebar().validWeapon(this) || 
					Controller.chr.getSidebar().validAbility(this) || 
					Controller.chr.getSidebar().validArmour(this)) { 
				g.setColor(new Color(50, 255, 50));
				g.drawString("Useable Equipment", x + 20, y + fm.getHeight()/2);
			}
			else {
				g.setColor(new Color(255, 50, 50));
				g.drawString("Equipment Cannot Be Used", x + 20, y + fm.getHeight()/2);
			}
		}
		g.setColor(Color.WHITE);
		y += fm.getHeight();
		if(this.desc != null) {
			for(int j = 0; j < this.desc.length; j++) {
				g.drawString(this.desc[j], x + 20, y + fm.getHeight()/2);
				y += fm.getHeight();
			}
		}
	}
	
	public String toString() {return "Type: " + type + ", Subtype: " + subtype + ", Name: " + title;}
	
	//Desc: various getters and setters
	//Param: value for setters
	//Return: value for getters
	public BufferedImage getImage() {return this.img;}
	public String getTierDisplay() {return this.tierDisplay;}
	public String getType() {return this.type;}
	public String getSubtype() {return this.subtype;}
}
