//Driver.java
//THIS IS THE MAIN CLASS
//responsible for some top-level stuff and general game-running that I didn't think would fit in Controller

package control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import player.Player;
import project.AoE;
import project.Area;
import project.CompareFrequency;
import project.Equipment;
import project.Leaderboard;
import project.LootBag;
import project.PopText;
import project.Portal;
import project.Projectile;
import project.Target;
import project.Tile;
import statuseffects.Status;

import java.util.*;

public class Driver {
	
	//static variables
	public static BufferedImage sidebar, itemSquare, itemSelect, weaponIcon, abilityIcon, armourIcon, ringIcon, icon;
	public static String gameState = "select";
	
	//choose class (only for testing)
	public static void classByText() throws IOException {
		Scanner s = new Scanner(System.in);
		System.out.println("Choose a class from: ");
		System.out.println("1) Sorcerer (wand, scepter, robe)");
		System.out.println("2) Necromancer (staff, skull, robe)");
		System.out.println("3) Archer (bow, quiver, leather)");
		System.out.println("4) Hunter (crossbow, blades, leather)");
		System.out.println("5) Warrior (axe, helm, heavy armour)");
		System.out.println("6) Paladin (sword, tome, heavy armour)");

		Controller.initGame(s.nextLine());
	}
	
	//upload images
	public static void upload() throws IOException{
		sidebar = ImageIO.read(new File("./assets/hud/sidebar.png"));
		itemSquare = ImageIO.read(new File("./assets/hud/itemsquare.png"));
		itemSelect = ImageIO.read(new File("./assets/hud/itemselect.png"));
		weaponIcon = ImageIO.read(new File("./assets/hud/icons0000.png"));
		abilityIcon = ImageIO.read(new File("./assets/hud/icons0001.png"));
		armourIcon = ImageIO.read(new File("./assets/hud/icons0002.png"));
		ringIcon = ImageIO.read(new File("./assets/hud/icons0003.png"));
		icon = ImageIO.read(new File("./assets/gameIcon.png"));
	}
	
	//main method
	public static void main(String[] args) throws IOException{
		//upload all images
		Tile.upload();
		Projectile.upload();
		Target.upload();
		Equipment.upload();
		Portal.upload();
		AoE.upload();
		Status.upload();
		Driver.upload();
		
		//read leaderboard
		Leaderboard.read();
		
		//initalize class select buttons
		Controller.initBars();
		//initalize frame
		Controller.initFrame();
	}
	
	//start the game given a class 
	public static void startGame(String classType) throws IOException {
		Controller.initGame(classType);
	}
	
	//Desc: returns the player's most frequently killed enemy, or their "Nemesis"
	//Param: none
	//Return: string, the nemesis
	public static String getNemesis() {
		//takes all the entries out into a list
		LinkedList<Map.Entry<String, Integer>> entries = new LinkedList<Map.Entry<String, Integer>>(Controller.chr.getKillFrequency().entrySet());
		//sorts them
		Collections.sort(entries, new CompareFrequency());
		//takes out the first one (most killed)
		Map.Entry<String, Integer> ent = entries.poll();
		//makes sure it exists
		if(ent != null && ent.getKey() != null) {
			return ent.getKey() + "("+ent.getValue() + " Slain)";
		}
		else {
			return "None";
		}
	}
	
	//Desc: resets all keys and values on death
	//Param: source of death as a string
	//Return: none
	public static void death(String s) {
		Controller.keys[0] = false;
		Controller.keys[1] = false;
		Controller.keys[2] = false;
		Controller.keys[3] = false;
		Controller.keys[4] = false;
		Controller.keys[5] = false;
		Controller.deathArgs = new String[] {s, getNemesis()};
		Controller.listClear = true;
		gameState = "death";
	}
	
	//Desc: resets all keys and values on finish
	//Param: none
	//Return: none
	public static void finish() {
		Controller.chr.win = true;
		
		Controller.keys[0] = false;
		Controller.keys[1] = false;
		Controller.keys[2] = false;
		Controller.keys[3] = false;
		Controller.keys[4] = false;
		Controller.keys[5] = false;
		Controller.deathArgs = new String[] {null, getNemesis()};
		
		//equivalent to writing Controller.listClear = true, this just makes sure it happens
		Area.list.clear();
		Controller.allyAoE.clear();
		Controller.enemyAoE.clear();
		Controller.allyShots.clear();
		Controller.enemyShots.clear();
		Controller.addList.clear();
		LootBag.list.clear();
		Portal.list.clear();
		PopText.list.clear();
		gameState = "finish";
	}
	
	//Desc: resets the game and keys
	//Param: none
	//Return: none
	public static void resetGame(){
		Controller.keys[0] = false;
		Controller.keys[1] = false;
		Controller.keys[2] = false;
		Controller.keys[3] = false;
		Controller.keys[4] = false;
		Controller.keys[5] = false;
		Controller.chr = null;
		Controller.setTicks(0);
		Controller.setTimerText("00:00.00");
		gameState = "select";
		Controller.name = "";
		Player.count++;
	}
}
