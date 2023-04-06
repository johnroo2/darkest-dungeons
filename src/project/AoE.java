package project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class AoE extends Projectile{
	protected ArrayList<BufferedImage> colour;
	
	protected int radius, lifetime;
	protected final static ArrayList<BufferedImage> AOE_RED = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_GREEN = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_LIME = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_PINK = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_DARKGREEN = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_VIOLET = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_DARKBLUE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_TURQUOISE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_LIGHTORANGE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> AOE_GRAY = new ArrayList<>();
	protected final static ArrayList<BufferedImage> SANDBEAM = new ArrayList<>();
	protected final static ArrayList<BufferedImage> WATERBEAM = new ArrayList<>();
	protected final static ArrayList<BufferedImage> WATERCOMET = new ArrayList<>();
	protected final static ArrayList<BufferedImage> SWORDSUMMON = new ArrayList<>();
	protected final static ArrayList<BufferedImage> SPEARSUMMON = new ArrayList<>();
	protected final static ArrayList<BufferedImage> KNIFESUMMON = new ArrayList<>();
	protected final static ArrayList<BufferedImage> SPEARSPIKE = new ArrayList<>();
	protected final static ArrayList<BufferedImage> DESECRATEDSHADE_SPAWN = new ArrayList<>();
	
	public static void upload() throws IOException {
		for(int i = 0; i <= 2; i++) {AOE_RED.add(ImageIO.read(new File("./assets/effects/aoe_red/aoe_red000"+i+".png")));}
		for(int i = 0; i <= 2; i++) {AOE_GREEN.add(ImageIO.read(new File("./assets/effects/aoe_green/aoe_green000"+i+".png")));}
		for(int i = 0; i <= 2; i++) {AOE_LIME.add(ImageIO.read(new File("./assets/effects/aoe_lime/aoe_lime000"+i+".png")));}
		for(int i = 0; i <= 2; i++) {AOE_PINK.add(ImageIO.read(new File("./assets/effects/aoe_pink/aoe_pink000"+i+".png")));}
		for(int i = 0; i <= 2; i++) {AOE_DARKGREEN.add(ImageIO.read(new File("./assets/effects/aoe_darkgreen/aoe_darkgreen000"+i+".png")));}
		for(int i = 0; i <= 2; i++) {AOE_VIOLET.add(ImageIO.read(new File("./assets/effects/aoe_violet/aoe_violet000"+i+".png")));}
		for(int i = 0; i <= 2; i++) {AOE_TURQUOISE.add(ImageIO.read(new File("./assets/effects/aoe_turquoise/aoe_turquoise000"+i+".png")));}
		for(int i = 0; i <= 2; i++) {AOE_GRAY.add(ImageIO.read(new File("./assets/effects/aoe_gray/aoe_gray000"+i+".png")));}
		for(int i = 0; i <= 2; i++) {AOE_LIGHTORANGE.add(ImageIO.read(new File("./assets/effects/aoe_lightorange/aoe_lightorange000"+i+".png")));}
		for(int i = 0; i <= 19; i++) {SANDBEAM.add(ImageIO.read(new File(String.format("assets/effects/sandbeam/sandbeam00%02d.png", i))));}
		for(int i = 0; i <= 2; i ++) {AOE_DARKBLUE.add(ImageIO.read(new File("./assets/effects/aoe_darkblue/aoe_darkblue000"+i+".png")));}
		for(int i = 0; i <= 19; i++) {WATERBEAM.add(ImageIO.read(new File(String.format("assets/effects/waterbeam/waterbeam00%02d.png", i))));}
		for(int i = 0; i <= 19; i++) {WATERCOMET.add(ImageIO.read(new File(String.format("assets/effects/watercomet/watercomet00%02d.png", i))));}
		for(int i = 0; i <= 19; i++) {SWORDSUMMON.add(ImageIO.read(new File(String.format("assets/effects/swordsummon/swordsummon00%02d.png", i))));}
		for(int i = 0; i <= 19; i++) {SPEARSUMMON.add(ImageIO.read(new File(String.format("assets/effects/spearsummon/spearsummon00%02d.png", i))));}
		for(int i = 0; i <= 19; i++) {SPEARSPIKE.add(ImageIO.read(new File(String.format("assets/effects/spearspike/spearspike00%02d.png", i))));}
		for(int i = 0; i <= 19; i++) {KNIFESUMMON.add(ImageIO.read(new File(String.format("assets/effects/knifesummon/knifesummon00%02d.png", i))));}
		for(int i = 0; i <= 5; i++) {DESECRATEDSHADE_SPAWN.add(ImageIO.read(new File("./assets/effects/desecratedshadespawn/desecratedshadespawn000"+i+".png")));}
	}
	
	public AoE(int x, int y, int radius, ArrayList<BufferedImage> colour, int lifetime) {
		super(x, y, radius);
		this.colour = colour;
		this.radius = radius;
		this.lifetime = lifetime;
		this.range = lifetime;
	}
	
	public BufferedImage getImage() {
		if(this.range < lifetime * 0.6) {
			return this.colour.get(2);
		}
		else if(this.range < lifetime * 0.8) {
			return this.colour.get(1);
		}
		return this.colour.get(0);
	}

	@Override
	public abstract boolean ranged();

	@Override
	public void move() {
		this.range--;
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.radius - shiftX + 400, (int)this.y-this.radius - shiftY + 400, 
				2*radius, 2*radius, i);
		
	}
}
