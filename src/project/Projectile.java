//Projectile.java
//parent class for all projectiles

package project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class Projectile {
	
	//Static variables :)
	protected static BufferedImage IMGS_SCORPION_SHOTS;
	protected static BufferedImage IMGS_GOBLINMAGE_SHOTS;
	protected static BufferedImage IMGS_GIANTCRAB_SHOTS;
	protected static BufferedImage IMGS_SNAKE_SHOTS;
	protected static BufferedImage IMGS_HORDERAT_SHOTS;
	protected static BufferedImage IMGS_WATERSPRITE_SHOTS;
	protected static BufferedImage IMGS_CROCODILE_SHOTS;
	protected static BufferedImage IMGS_FOUNTAINSPIRIT_SHOTS;
	protected static BufferedImage IMGS_FOUNTAINFAIRY_SHOTS;
	protected static BufferedImage IMGS_FOUNTAINNYMPH_SHOTS;
	protected static BufferedImage IMGS_AQUAMAGE_SHOTS;
	protected static BufferedImage IMGS_FIRESPRITE_SHOTS;
	protected static BufferedImage IMGS_POWDERMONKEY_SHOTS;
	protected static BufferedImage IMGS_SHUNNEDIDOL_SHOTS;
	protected static BufferedImage IMGS_DAIMANARSONIST_SHOTS;
	protected static BufferedImage IMGS_DAIMANBRUTE_SHOTS;
	protected static BufferedImage IMGS_LESSERDEMON_SHOTS;
	
	protected final static ArrayList<BufferedImage> IMGS_FORSAKENCHAMPION_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORSAKENCHAMPION_BOUNCE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORSAKENCHAMPION_ARROW = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORSAKENCHAMPION_INSWORD = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORSAKENCHAMPION_INSPEAR = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORSAKENCHAMPION_INKNIFE = new ArrayList<>();
	protected static BufferedImage IMGS_FORSAKENCHAMPION_INSWORD_SHOTS;
	protected static BufferedImage IMGS_FORSAKENCHAMPION_INKNIFE_SHOTS;
	
	protected final static ArrayList<BufferedImage> IMGS_ICESPRITE_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_CAVESHRIEKER_SHOTS = new ArrayList<>();
	protected static BufferedImage IMGS_LIFECASTER_SHOTS;
	protected static BufferedImage IMGS_TENESCOWRICRUSADER_SHOTS;
	protected static ArrayList<BufferedImage> IMGS_TENESCOWRIFLESHTRACKER_SHOTS = new ArrayList<>();
	protected static BufferedImage IMGS_TENESCOWRILIEUTENANT_SHOTS;
	protected static BufferedImage IMGS_TENESCOWRISHAMAN_SHOTS;
	protected static BufferedImage IMGS_DESECRATEDSHADE_SHOTS;
	protected static BufferedImage IMGS_CRYOMAGE_SHOTS;
	protected static BufferedImage IMGS_CRYOADEPT_SHOTS;
	protected final static ArrayList<BufferedImage> IMGS_CRYOWARLOCK_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_CRYOWARLOCK_PORTALSHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_CRYOWARLOCK_PORTAL = new ArrayList<>();
	protected static BufferedImage IMGS_MAMMOTHHUNTER_SHOTS;
	
	protected final static ArrayList<BufferedImage> IMGS_FORBIDDENFOUNTAIN_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORBIDDENFOUNTAIN_BUBBLE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_SCORPIONQUEEN_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_GREATDRAGONFLY_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_DAIMANWITCH_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FLAMESPECTER_SHOTS = new ArrayList<>();
	protected static BufferedImage IMGS_LOSTSENTINELTURRET_SHOTS;
	protected static BufferedImage IMGS_LOSTSENTINELTOWER_SHOTS;
	protected final static ArrayList<BufferedImage> IMGS_UNHALLOWEDREVENANTSHADE_SHOTS = new ArrayList<>();
	protected static BufferedImage IMGS_UNHALLOWEDREVENANT_SPEAR;
	protected final static ArrayList<BufferedImage> IMGS_UNHALLOWEDREVENANT_SHOTS = new ArrayList<>();
	
	protected final static ArrayList<BufferedImage> BULLETS_STAFF = new ArrayList<>();
	protected final static ArrayList<BufferedImage> BULLETS_SCEPTER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> BULLETS_BOW = new ArrayList<>();
	protected final static ArrayList<BufferedImage> BULLETS_QUIVER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> BULLETS_BLADES = new ArrayList<>();
	protected final static ArrayList<BufferedImage> BULLETS_SWORD = new ArrayList<>();
	protected final static ArrayList<BufferedImage> BULLETS_AXE = new ArrayList<>();
	
	protected final static ArrayList<BufferedImage> SPECTRALFIREWAND_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> BOWOFTHEFRACTURED_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> WANDOFTHEFALLEN_SHOTS = new ArrayList<>();
	protected final static ArrayList<BufferedImage> TUSKRENDERFLURRY_SHOTS = new ArrayList<>();
	
	protected static BufferedImage SPIRITSTAFF_SHOT;
	protected static BufferedImage ROYALSCORPIONHIDE_SHOT;
	protected static BufferedImage BROOKCHASERBOW_SHOT;
	protected static BufferedImage PROWLERSCLAW_SHOT;
	protected static BufferedImage SERRATEDTOOTHBLADES_SHOT;
	protected static BufferedImage RIVERMAKER_SHOT;
	protected static BufferedImage TRECHAROUSCROSSBOW_SHOT;
	protected static BufferedImage CHAMPIONSHELM_SHOT;
	protected static BufferedImage OATHBREAKERQUIVER_SHOT;
	protected static BufferedImage CRESTFELLERAXE_SHOT;
	protected static BufferedImage BLADEOFTHEMINDLESS_SHOT;
	protected static BufferedImage CRYSTALLINESCEPTER_SHOT;
	protected static BufferedImage SENTINELSQUIVER_SHOT;
	
	//instance variables
	protected double x, y, xVel, yVel;
	protected int range;
	protected static double critDamage = 1.5;
	
	//uploads all images :)
	public static void upload() throws IOException{
		
		IMGS_SCORPIONQUEEN_SHOTS.add(ImageIO.read(new File("./enemies/scorpionqueen/bullets/scorpionqueenshot0000.png")));
		IMGS_SCORPIONQUEEN_SHOTS.add(ImageIO.read(new File("./enemies/scorpionqueen/bullets/scorpionqueenshot0001.png")));
		IMGS_SCORPION_SHOTS = ImageIO.read(new File("./enemies/scorpion/bullets/scorpionshot0000.png"));	
		IMGS_GOBLINMAGE_SHOTS = ImageIO.read(new File("./enemies/goblinmage/bullets/goblinmageshot0000.png"));	
		IMGS_GIANTCRAB_SHOTS = ImageIO.read(new File("./enemies/giantcrab/bullets/giantcrabshot0000.png"));	
		IMGS_SNAKE_SHOTS = ImageIO.read(new File("./enemies/snake/bullets/snakeshot0000.png"));
		IMGS_HORDERAT_SHOTS = ImageIO.read(new File("./enemies/horderat/bullets/horderatshot0000.png"));
		IMGS_WATERSPRITE_SHOTS = ImageIO.read(new File("./enemies/watersprite/bullets/waterspriteshot0000.png"));
		IMGS_CROCODILE_SHOTS = ImageIO.read(new File("./enemies/crocodile/bullets/crocodileshot0000.png"));
		IMGS_FOUNTAINSPIRIT_SHOTS = ImageIO.read(new File("./enemies/fountainspirit/bullets/fountainspiritshot0000.png"));
		IMGS_FOUNTAINFAIRY_SHOTS = ImageIO.read(new File("./enemies/fountainfairy/bullets/fountainfairyshot0000.png"));
		IMGS_FOUNTAINNYMPH_SHOTS = ImageIO.read(new File("./enemies/fountainnymph/bullets/fountainnymphshot0000.png"));
		IMGS_GREATDRAGONFLY_SHOTS.add(ImageIO.read(new File("./enemies/greatdragonfly/bullets/greatdragonflyshot0000.png")));
		IMGS_GREATDRAGONFLY_SHOTS.add(ImageIO.read(new File("./enemies/greatdragonfly/bullets/greatdragonflyshot0001.png")));
		IMGS_AQUAMAGE_SHOTS = ImageIO.read(new File("./enemies/aquamage/bullets/aquamageshot0000.png"));
		for(int i = 0; i <= 10; i++) {BULLETS_STAFF.add(ImageIO.read(new File(String.format("items/tieredstaff/t_staffshot00%02d.png", i))));}
		for(int i = 0; i <= 6; i++) {BULLETS_SCEPTER.add(ImageIO.read(new File("./items/tieredscepter/t_sceptershot000"+i+".png")));}
		for(int i = 0; i <= 10; i++) {BULLETS_BOW.add(ImageIO.read(new File(String.format("items/tieredbow/t_bowshot00%02d.png", i))));}
		for(int i = 0; i <= 6; i++) {BULLETS_QUIVER.add(ImageIO.read(new File("./items/tieredquiver/t_quivershot000"+i+".png")));}
		for(int i = 0; i <= 6; i++) {BULLETS_BLADES.add(ImageIO.read(new File("./items/tieredblade/t_bladeshot000"+i+".png")));}
		for(int i = 0; i <= 10; i++) {BULLETS_SWORD.add(ImageIO.read(new File(String.format("items/tieredsword/t_swordshot00%02d.png", i))));}
		for(int i = 0; i <= 10; i++) {BULLETS_AXE.add(ImageIO.read(new File(String.format("items/tieredaxe/t_axeshot00%02d.png", i))));}
		for(int i = 0; i <= 12; i++) {IMGS_FORBIDDENFOUNTAIN_BUBBLE.add(ImageIO.read(new File(String.format("enemies/forbiddenfountain/bullets/forbiddenfountainbubble00%02d.png", i))));}
		for(int i = 0; i <= 7; i++) {IMGS_FORBIDDENFOUNTAIN_SHOTS.add(ImageIO.read(new File("./enemies/forbiddenfountain/bullets/forbiddenfountainshot000"+i+".png")));}
		IMGS_FORBIDDENFOUNTAIN_SHOTS.add(ImageIO.read(new File("./enemies/forbiddenfountain/bullets/forbiddenfountainspear0000.png")));
		IMGS_FIRESPRITE_SHOTS = ImageIO.read(new File("./enemies/firesprite/bullets/firespriteshot0000.png"));
		IMGS_POWDERMONKEY_SHOTS = ImageIO.read(new File("./enemies/powdermonkey/bullets/powdermonkeyshot0000.png"));
		IMGS_SHUNNEDIDOL_SHOTS = ImageIO.read(new File("./enemies/shunnedidol/bullets/shunnedidolshot0000.png"));
		IMGS_DAIMANARSONIST_SHOTS = ImageIO.read(new File("./enemies/daimanarsonist/bullets/daimanarsonistshot0000.png"));
		IMGS_DAIMANBRUTE_SHOTS = ImageIO.read(new File("./enemies/daimanbrute/bullets/daimanbruteshot0000.png"));
		IMGS_DAIMANWITCH_SHOTS.add(ImageIO.read(new File("./enemies/daimanwitch/bullets/daimanwitchshot0000.png")));
		IMGS_DAIMANWITCH_SHOTS.add(ImageIO.read(new File("./enemies/daimanwitch/bullets/daimanwitchshot0001.png")));
		IMGS_FLAMESPECTER_SHOTS.add(ImageIO.read(new File("./enemies/flamespecter/bullets/flamespectershot0000.png")));
		IMGS_FLAMESPECTER_SHOTS.add(ImageIO.read(new File("./enemies/flamespecter/bullets/flamespectershot0001.png")));
		IMGS_LESSERDEMON_SHOTS = ImageIO.read(new File("./enemies/lesserdemon/bullets/lesserdemonshot0000.png"));
		for(int i = 0; i <= 6; i++) {IMGS_FORSAKENCHAMPION_SHOTS.add(ImageIO.read(new File("./enemies/forsakenchampion/bullets/forsakenchampionshot000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {IMGS_FORSAKENCHAMPION_BOUNCE.add(ImageIO.read(new File("./enemies/forsakenchampion/bullets/forsakenchampionbounce000"+i+".png")));}
		IMGS_FORSAKENCHAMPION_ARROW.add(ImageIO.read(new File("./enemies/forsakenchampion/bullets/forsakenchampionarrow0000.png")));
		IMGS_FORSAKENCHAMPION_ARROW.add(ImageIO.read(new File("./enemies/forsakenchampion/bullets/forsakenchampionarrow0001.png")));
		IMGS_FORSAKENCHAMPION_ARROW.add(ImageIO.read(new File("./enemies/forsakenchampion/bullets/forsakenchampionarrow0002.png")));
		for(int i = 0; i <= 3; i++) {IMGS_FORSAKENCHAMPION_INSWORD.add(ImageIO.read(new File("./enemies/forsakenchampion/bullets/insword000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_FORSAKENCHAMPION_INSPEAR.add(ImageIO.read(new File("./enemies/forsakenchampion/bullets/inspear000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_FORSAKENCHAMPION_INKNIFE.add(ImageIO.read(new File("./enemies/forsakenchampion/bullets/inknife000"+i+".png")));}
		IMGS_FORSAKENCHAMPION_INSWORD_SHOTS = ImageIO.read(new File("./enemies/forsakenchampion/bullets/inswordshot0000.png"));
		IMGS_FORSAKENCHAMPION_INKNIFE_SHOTS = ImageIO.read(new File("./enemies/forsakenchampion/bullets/inknifeshot0000.png"));
		for(int i = 0; i <= 2; i++) {IMGS_ICESPRITE_SHOTS.add(ImageIO.read(new File("./enemies/icesprite/bullets/icespriteshot000"+i+".png")));}
		IMGS_CAVESHRIEKER_SHOTS.add(ImageIO.read(new File("./enemies/caveshrieker/bullets/caveshriekershot0000.png")));
		IMGS_CAVESHRIEKER_SHOTS.add(ImageIO.read(new File("./enemies/caveshrieker/bullets/caveshriekershot0001.png")));
		IMGS_LIFECASTER_SHOTS = ImageIO.read(new File("./enemies/lifecaster/bullets/lifecastershot0000.png"));
		IMGS_DESECRATEDSHADE_SHOTS = ImageIO.read(new File("./enemies/desecratedshade/bullets/desecratedshadeshot0000.png"));
		IMGS_TENESCOWRICRUSADER_SHOTS = ImageIO.read(new File("./enemies/tenescowricrusader/bullets/tenescowricrusadershot0000.png"));
		for(int i = 0; i < 90; i += 10) {IMGS_TENESCOWRIFLESHTRACKER_SHOTS.add(Rotate.rotateImage(ImageIO.read(new File("./enemies/tenescowrifleshtracker/bullets/tenescowrifleshtrackershot0000.png")), i));}
		for(int i = 0; i < 90; i += 10) {IMGS_UNHALLOWEDREVENANTSHADE_SHOTS.add(Rotate.rotateImage(ImageIO.read(new File("./enemies/unhallowedrevenantshade/bullets/unhallowedrevenantshadeshot0000.png")), i));}
		IMGS_TENESCOWRILIEUTENANT_SHOTS = ImageIO.read(new File("./enemies/tenescowrilieutenant/bullets/tenescowrilieutenantshot0000.png"));
		IMGS_CRYOMAGE_SHOTS = ImageIO.read(new File("./enemies/cryomage/bullets/cryomageshot0000.png"));
		IMGS_CRYOADEPT_SHOTS = ImageIO.read(new File("./enemies/cryoadept/bullets/cryoadeptshot0000.png"));
		for(int i = 0; i < 180; i += 10) {IMGS_CRYOWARLOCK_SHOTS.add(Rotate.rotateImage(ImageIO.read(new File("./enemies/cryowarlock/bullets/cryowarlockshot0000.png")), i));}
		IMGS_CRYOWARLOCK_PORTALSHOTS.add(ImageIO.read(new File("./enemies/cryowarlock/bullets/cryowarlockportalshot0000.png")));
		IMGS_CRYOWARLOCK_PORTALSHOTS.add(ImageIO.read(new File("./enemies/cryowarlock/bullets/cryowarlockportalshot0001.png")));
		for(int i = 0; i <= 2; i++) {IMGS_CRYOWARLOCK_PORTAL.add(ImageIO.read(new File("./enemies/cryowarlock/bullets/cryowarlockportal000"+i+".png")));}
		IMGS_MAMMOTHHUNTER_SHOTS = ImageIO.read(new File("./enemies/mammothhunter/bullets/mammothhuntershot0000.png"));
		IMGS_LOSTSENTINELTURRET_SHOTS = ImageIO.read(new File("./enemies/lostsentinelturret/bullets/lostsentinelturretshot0000.png"));
		IMGS_LOSTSENTINELTOWER_SHOTS = ImageIO.read(new File("./enemies/lostsentineltower/bullets/lostsentineltowershot0000.png"));
		IMGS_UNHALLOWEDREVENANT_SPEAR = ImageIO.read(new File("./enemies/unhallowedrevenant/bullets/unhallowedrevenantspear0000.png"));
		IMGS_UNHALLOWEDREVENANT_SHOTS.add(ImageIO.read(new File("./enemies/unhallowedrevenant/bullets/unhallowedrevenantshot0000.png")));
		IMGS_UNHALLOWEDREVENANT_SHOTS.add(ImageIO.read(new File("./enemies/unhallowedrevenant/bullets/unhallowedrevenantshot0001.png")));
		
		for(int i = 0; i <= 3; i++) {SPECTRALFIREWAND_SHOTS.add(ImageIO.read(new File("./items/st/weapons/spectralfirewandshot000"+i+".png")));}
		
		SPIRITSTAFF_SHOT = ImageIO.read(new File("./items/st/weapons/spiritstaffshot0000.png"));
		ROYALSCORPIONHIDE_SHOT = ImageIO.read(new File("./items/st/armours/royalscorpionhideshot0000.png"));
		BROOKCHASERBOW_SHOT = ImageIO.read(new File("./items/st/weapons/brookchaserbowshot0000.png"));
		PROWLERSCLAW_SHOT = ImageIO.read(new File("./items/st/weapons/prowlersclawshot0000.png"));
		SERRATEDTOOTHBLADES_SHOT = ImageIO.read(new File("./items/st/abilities/serratedtoothbladesshot0000.png"));
		RIVERMAKER_SHOT = ImageIO.read(new File("./items/st/abilities/rivermakershot0000.png"));
		TRECHAROUSCROSSBOW_SHOT = ImageIO.read(new File("./items/st/weapons/trecharouscrossbowshot0000.png"));
		CHAMPIONSHELM_SHOT = ImageIO.read(new File("./items/st/abilities/championshelmshot0000.png"));
		OATHBREAKERQUIVER_SHOT = ImageIO.read(new File("./items/st/abilities/oathbreakerquivershot0000.png"));
		CRESTFELLERAXE_SHOT = ImageIO.read(new File("./items/st/weapons/crestfelleraxeshot0000.png"));
		BLADEOFTHEMINDLESS_SHOT = ImageIO.read(new File("./items/st/weapons/bladeofthemindlessshot0000.png"));
		BOWOFTHEFRACTURED_SHOTS.add(ImageIO.read(new File("./items/st/weapons/bowofthefracturedshot0000.png")));
		BOWOFTHEFRACTURED_SHOTS.add(ImageIO.read(new File("./items/st/weapons/bowofthefracturedshot0001.png")));
		WANDOFTHEFALLEN_SHOTS.add(ImageIO.read(new File("./items/st/weapons/wandofthefallenshot0000.png")));
		WANDOFTHEFALLEN_SHOTS.add(ImageIO.read(new File("./items/st/weapons/wandofthefallenshot0001.png")));
		CRYSTALLINESCEPTER_SHOT = ImageIO.read(new File("./items/st/abilities/crystallinesceptershot0000.png"));
		SENTINELSQUIVER_SHOT = ImageIO.read(new File("./items/st/abilities/sentinelsquivershot0000.png"));
		TUSKRENDERFLURRY_SHOTS.add(ImageIO.read(new File("./items/st/abilities/tuskrenderflurryshot0000.png")));
		TUSKRENDERFLURRY_SHOTS.add(ImageIO.read(new File("./items/st/abilities/tuskrenderflurryshot0001.png")));
	}
	
	//constructor given: target, range and speed
	public Projectile(int x, int y, int targetX, int targetY, int range, double speed) {
		this.x = x;
		this.y = y;
		if(x == targetX) {
			this.xVel = 0.0;
			if(y > targetY) {
				this.yVel = -speed;
			}
			else {
				this.yVel = speed;
			}
		}
		else {
			double slope = (double)(targetY-y)/(double)(targetX-x);
			if(x > targetX) {
				this.xVel = -speed/Math.sqrt(slope*slope+1);
			}
			else {
				this.xVel = speed/Math.sqrt(slope*slope+1);
			}
			if(y > targetY) {
				this.yVel = -speed*Math.sqrt((slope*slope)/(slope*slope+1));
			}
			else {
				this.yVel = speed*Math.sqrt((slope*slope)/(slope*slope+1));
			}
		}
		this.range = range;
	}
	
	//constructor given: target, speed
	public Projectile(int x, int y, int targetX, int targetY, double speed) {
		this.x = x;
		this.y = y;
		if(x == targetX) {
			this.xVel = 0.0;
			if(y > targetY) {
				this.yVel = -speed;
			}
			else {
				this.yVel = speed;
			}
		}
		else {
			double slope = (double)(targetY-y)/(double)(targetX-x);
			if(x > targetX) {
				this.xVel = -speed/Math.sqrt(slope*slope+1);
			}
			else {
				this.xVel = speed/Math.sqrt(slope*slope+1);
			}
			if(y > targetY) {
				this.yVel = -speed*Math.sqrt((slope*slope)/(slope*slope+1));
			}
			else {
				this.yVel = speed*Math.sqrt((slope*slope)/(slope*slope+1));
			}
		}
		this.range = (int) Math.ceil(Math.sqrt((double)((targetX-x)*(targetX-x) + (targetY-y)*(targetY-y))/speed));
	}
	
	//constructor given: orientation, range, speed
	public Projectile(int x, int y, double ori, int range, double speed) {
		this.x = x;
		this.y = y;
		this.xVel = speed * Math.cos(Math.toRadians(ori));
		this.yVel = speed * Math.sin(Math.toRadians(ori));
		this.range = range;
	}
	
	//constructor for AoE
	public Projectile(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.xVel = 0;
		this.yVel = 0;
	}
	
	//Desc: checks if the projectile is in a wall
	//Param: none
	//Return: true if in wall, false otherwise
	public boolean inWall() {
		for(Area a: Area.list) {
			if(a.drawable()) {
				Tile t = a.get((int)((this.x - a.getX())/Area.BLOCK_SIZE),
						(int)((this.y - a.getY())/Area.BLOCK_SIZE));
				if(t != null && t.isWall()) {
					return true;
				}
			}
		}
		return false;
	}
		
	//gets the angle between two points
	public double getAngle(int x1, int y1, int x2, int y2) {
		if(x1 == x2) {
			if(y1 > y2) {
				return 270;
			}
			else {
				return 90;
			}
		}
		else {
			if(x2 > x1) {
				return Math.toDegrees(Math.atan((double)(y2-y1)/(double)(x2-x1)));
			}
			return Math.toDegrees(Math.atan((double)(y2-y1)/(double)(x2-x1))) + 180;
		}
	}
	
	public void destroy() {}
	public void act() {}

	//Desc: various getters and setters
	//Param: value for setters
	//Return: value for getters
	public abstract boolean ranged();
	
	public abstract void move();
	
	public abstract void draw(Graphics g, int shiftX, int shiftY, ImageObserver i);
	
	public double getX() {return this.x;}
	public double getY() {return this.y;}
}
