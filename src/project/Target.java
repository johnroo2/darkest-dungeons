package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class Target {
	
	protected final static ArrayList<BufferedImage> IMGS_SCORPION = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_GOBLINMAGE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_GIANTCRAB = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_SCORPIONQUEEN = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_SNAKE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_SPIKEDTURTLE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_HORDERAT = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_SANDSHAMAN = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_WATERSPRITE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_CROCODILE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FOUNTAINFAIRY = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FOUNTAINNYMPH = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_GREATDRAGONFLY = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_AQUAMAGE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORBIDDENFOUNTAIN = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FIRESPRITE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_POWDERMONKEY = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_SHUNNEDIDOL = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_DAIMANARSONIST = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_DAIMANBRUTE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_DAIMANWITCH = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FLAMESPECTER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_LESSERDEMON = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORSAKENCHAMPION = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_FORSAKENCHAMPIONII = new ArrayList<>(); //phase 2
	protected final static ArrayList<BufferedImage> IMGS_ICESPRITE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_CAVESHRIEKER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_LIFECASTER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_DESECRATEDSHADE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_TENESCOWRICRUSADER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_TENESCOWRIFLESHTRACKER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_TENESCOWRILIEUTENANT = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_TENESCOWRISHAMAN = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_CRYOMAGE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_CRYOADEPT = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_CRYOWARLOCK = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_MAMMOTHHUNTER = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_LOSTSENTINEL = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_LOSTSENTINELTURRET = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_UNHALLOWEDREVENANT = new ArrayList<>();
	protected final static ArrayList<BufferedImage> IMGS_UNHALLOWEDREVENANTSHADE = new ArrayList<>();
	
	protected static BufferedImage IMGS_LOSTSENTINELTOWER;	
	protected static BufferedImage IMGS_FOUNTAINSPIRIT;
	
	protected double x, y; 
	protected int maxHealth, currentHealth, defense;
	
	public Target(int x, int y, int hp, int def) {
		this.x = x;
		this.y = y;
		this.maxHealth = hp;
		this.currentHealth = hp;
		this.defense = def;
	}
	
	public static void upload() throws IOException{
		
		IMGS_SCORPION.add(ImageIO.read(new File("./enemies/scorpion/scorpion0000.png")));
		IMGS_SCORPION.add(ImageIO.read(new File("./enemies/scorpion/scorpion0001.png")));
		
		IMGS_GOBLINMAGE.add(ImageIO.read(new File("./enemies/goblinmage/goblinmage0000.png")));
		IMGS_GOBLINMAGE.add(ImageIO.read(new File("./enemies/goblinmage/goblinmage0001.png")));
		IMGS_GOBLINMAGE.add(ImageIO.read(new File("./enemies/goblinmage/goblinmage0002.png")));
		IMGS_GOBLINMAGE.add(ImageIO.read(new File("./enemies/goblinmage/goblinmage0003.png")));
		IMGS_GOBLINMAGE.add(ImageIO.read(new File("./enemies/goblinmage/goblinmage0004.png")));
		IMGS_GOBLINMAGE.add(ImageIO.read(new File("./enemies/goblinmage/goblinmage0005.png")));
		IMGS_GOBLINMAGE.add(ImageIO.read(new File("./enemies/goblinmage/goblinmage0006.png")));
		IMGS_GOBLINMAGE.add(ImageIO.read(new File("./enemies/goblinmage/goblinmage0007.png")));
		
		IMGS_GIANTCRAB.add(ImageIO.read(new File("./enemies/giantcrab/giantcrab0000.png")));
		IMGS_GIANTCRAB.add(ImageIO.read(new File("./enemies/giantcrab/giantcrab0001.png")));
		IMGS_GIANTCRAB.add(ImageIO.read(new File("./enemies/giantcrab/giantcrab0002.png")));
		IMGS_GIANTCRAB.add(ImageIO.read(new File("./enemies/giantcrab/giantcrab0003.png")));
		
		IMGS_SCORPIONQUEEN.add(ImageIO.read(new File("./enemies/scorpionqueen/scorpionqueen0000.png")));
		IMGS_SCORPIONQUEEN.add(ImageIO.read(new File("./enemies/scorpionqueen/scorpionqueen0001.png")));
		IMGS_SCORPIONQUEEN.add(ImageIO.read(new File("./enemies/scorpionqueen/scorpionqueen0002.png")));
		IMGS_SCORPIONQUEEN.add(ImageIO.read(new File("./enemies/scorpionqueen/scorpionqueen0003.png")));
		IMGS_SCORPIONQUEEN.add(ImageIO.read(new File("./enemies/scorpionqueen/scorpionqueen0004.png")));
		IMGS_SCORPIONQUEEN.add(ImageIO.read(new File("./enemies/scorpionqueen/scorpionqueen0005.png")));
		IMGS_SCORPIONQUEEN.add(ImageIO.read(new File("./enemies/scorpionqueen/scorpionqueen0006.png")));
		IMGS_SCORPIONQUEEN.add(ImageIO.read(new File("./enemies/scorpionqueen/scorpionqueen0007.png")));
		
		IMGS_FOUNTAINSPIRIT = ImageIO.read(new File("./enemies/fountainspirit/fountainspirit0000.png"));
		
		for(int i = 0; i <= 7; i++) {IMGS_SNAKE.add(ImageIO.read(new File("./enemies/snake/snake000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_SPIKEDTURTLE.add(ImageIO.read(new File("./enemies/spikedturtle/spikedturtle000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_HORDERAT.add(ImageIO.read(new File("./enemies/horderat/horderat000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_SANDSHAMAN.add(ImageIO.read(new File("./enemies/sandshaman/sandshaman000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_SANDSHAMAN.add(ImageIO.read(new File("./enemies/sandshaman/sandshamancast000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_WATERSPRITE.add(ImageIO.read(new File("./enemies/watersprite/watersprite000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_CROCODILE.add(ImageIO.read(new File("./enemies/crocodile/crocodile000"+i+".png")));}
		for(int i = 0; i <= 1; i++) {IMGS_FOUNTAINFAIRY.add(ImageIO.read(new File("./enemies/fountainfairy/fountainfairy000"+i+".png")));}
		for(int i = 0; i <= 1; i++) {IMGS_FOUNTAINNYMPH.add(ImageIO.read(new File("./enemies/fountainnymph/fountainnymph000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_GREATDRAGONFLY.add(ImageIO.read(new File("./enemies/greatdragonfly/greatdragonfly000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_AQUAMAGE.add(ImageIO.read(new File("./enemies/aquamage/aquamage000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_FORBIDDENFOUNTAIN.add(ImageIO.read(new File("./enemies/forbiddenfountain/forbiddenfountain000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_FIRESPRITE.add(ImageIO.read(new File("./enemies/firesprite/firesprite000"+i+".png")));}
		for(int i = 0; i <= 9; i++) {IMGS_POWDERMONKEY.add(ImageIO.read(new File("./enemies/powdermonkey/powdermonkey000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_SHUNNEDIDOL.add(ImageIO.read(new File("./enemies/shunnedidol/shunnedidol000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_DAIMANARSONIST.add(ImageIO.read(new File("./enemies/daimanarsonist/daimanarsonist000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_DAIMANBRUTE.add(ImageIO.read(new File("./enemies/daimanbrute/daimanbrute000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_DAIMANWITCH.add(ImageIO.read(new File("./enemies/daimanwitch/daimanwitch000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_FLAMESPECTER.add(ImageIO.read(new File("./enemies/flamespecter/flamespecter000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_LESSERDEMON.add(ImageIO.read(new File("./enemies/lesserdemon/lesserdemon000"+i+".png")));}
		for(int i = 0; i <= 15; i++) {IMGS_FORSAKENCHAMPION.add(ImageIO.read(new File(String.format("enemies/forsakenchampion/forsakenchampion00%02d.png", i))));}
		for(int i = 0; i <= 15; i++) {IMGS_FORSAKENCHAMPIONII.add(ImageIO.read(new File(String.format("enemies/forsakenchampion/forsakenchampionii00%02d.png", i))));}

		for(int i = 0; i <= 7; i++) {IMGS_ICESPRITE.add(ImageIO.read(new File("./enemies/icesprite/icesprite000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_CAVESHRIEKER.add(ImageIO.read(new File("./enemies/caveshrieker/caveshrieker000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_LIFECASTER.add(ImageIO.read(new File("./enemies/lifecaster/lifecaster000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_DESECRATEDSHADE.add(ImageIO.read(new File("./enemies/desecratedshade/desecratedshade000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_TENESCOWRICRUSADER.add(ImageIO.read(new File("./enemies/tenescowricrusader/tenescowricrusader000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_TENESCOWRIFLESHTRACKER.add(ImageIO.read(new File("./enemies/tenescowrifleshtracker/tenescowrifleshtracker000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_TENESCOWRILIEUTENANT.add(ImageIO.read(new File("./enemies/tenescowrilieutenant/tenescowrilieutenant000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {IMGS_TENESCOWRISHAMAN.add(ImageIO.read(new File("./enemies/tenescowrishaman/tenescowrishaman000"+i+".png")));}
		
		for(int i = 0; i <= 7; i++) {IMGS_CRYOMAGE.add(ImageIO.read(new File("./enemies/cryomage/cryomage000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_CRYOADEPT.add(ImageIO.read(new File("./enemies/cryoadept/cryoadept000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_CRYOWARLOCK.add(ImageIO.read(new File("./enemies/cryowarlock/cryowarlock000"+i+".png")));}
		for(int i = 0; i <= 7; i++) {IMGS_MAMMOTHHUNTER.add(ImageIO.read(new File("./enemies/mammothhunter/mammothhunter000"+i+".png")));}
		for(int i = 0; i <= 14; i++) {IMGS_UNHALLOWEDREVENANT.add(ImageIO.read(new File(String.format("./enemies/unhallowedrevenant/unhallowedrevenant00%02d.png", i))));}
		
		for(int i = 0; i <= 4; i++) {IMGS_LOSTSENTINEL.add(ImageIO.read(new File("./enemies/lostsentinel/lostsentinel000"+i+".png")));}
		for(int i = 0; i <= 1; i++) {IMGS_LOSTSENTINELTURRET.add(ImageIO.read(new File("./enemies/lostsentinelturret/lostsentinelturret000"+i+".png")));}
		for(int i = 0; i <= 4; i++) {IMGS_UNHALLOWEDREVENANTSHADE.add(ImageIO.read(new File("./enemies/unhallowedrevenantshade/unhallowedrevenantshade000"+i+".png")));}
		
		IMGS_LOSTSENTINELTOWER = ImageIO.read(new File("./enemies/lostsentineltower/lostsentineltower0000.png"));
	}

	
	public double getX() {return this.x;}
	public double getY() {return this.y;}
}
