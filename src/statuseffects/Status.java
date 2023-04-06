package statuseffects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import player.Player;
import project.Enemy;

public abstract class Status implements Comparable<Status>{
	protected static BufferedImage status_shield;
	protected static BufferedImage status_armourbreak;
	protected static BufferedImage status_curse;
	protected static BufferedImage status_dazed;
	protected static BufferedImage status_exposed;
	protected static BufferedImage status_paralyze;
	protected static BufferedImage status_quiet;
	protected static BufferedImage status_sick;
	protected static BufferedImage status_silenced;
	protected static BufferedImage status_slow;
	protected static BufferedImage status_disarmed;
	protected static BufferedImage status_speedy;
	protected static BufferedImage status_berserk;
	protected static BufferedImage status_fervor;
	protected static BufferedImage status_armoured;
	
	protected int duration;
	protected BufferedImage img;
	protected String subtype = null;
	protected boolean unique;
	
	public static void upload() throws IOException{
		status_shield = ImageIO.read(new File("./assets/status_effects/status_shield.png"));
		status_armourbreak = ImageIO.read(new File("./assets/status_effects/status_armourbreak.png"));
		status_curse = ImageIO.read(new File("./assets/status_effects/status_curse.png"));
		status_dazed = ImageIO.read(new File("./assets/status_effects/status_dazed.png"));
		status_exposed = ImageIO.read(new File("./assets/status_effects/status_exposed.png"));
		status_paralyze = ImageIO.read(new File("./assets/status_effects/status_paralyze.png"));
		status_quiet = ImageIO.read(new File("./assets/status_effects/status_quiet.png"));
		status_sick = ImageIO.read(new File("./assets/status_effects/status_sick.png"));
		status_silenced = ImageIO.read(new File("./assets/status_effects/status_silenced.png"));
		status_slow = ImageIO.read(new File("./assets/status_effects/status_slow.png"));
		status_disarmed = ImageIO.read(new File("./assets/status_effects/status_disarmed.png"));
		status_berserk = ImageIO.read(new File("./assets/status_effects/status_berserk.png"));
		status_speedy = ImageIO.read(new File("./assets/status_effects/status_speedy.png"));
		status_fervor = ImageIO.read(new File("./assets/status_effects/status_fervor.png"));
		status_armoured = ImageIO.read(new File("./assets/status_effects/status_armoured.png"));
	}
	
	public Status() {}
	
	public boolean active() {
		return true;
	}
	
	public boolean equals(Object o) {
		Status s = (Status)o;
		if(this.subtype.equals(s.subtype)) {
			return true;
		}
		return false;
	}
	
	public int compareTo(Status s) {
		if(this.subtype != null) {
			if(this.subtype.compareTo(s.subtype) == 0) {
				if(this.duration > s.duration) {
					s.duration = this.duration;
				}
				else {
					this.duration = s.duration;
				}
			}
			return this.subtype.compareTo(s.subtype);
		}
		return 0;
	}
	
	public boolean tick() {
		this.duration -= 1;
		if(this.duration < 0) {
			return true;
		}
		return false;
	}
	
	public BufferedImage getImage() {return this.img;}
	public String getType() {return this.subtype;}
}
