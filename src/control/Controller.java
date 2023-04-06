//Controller.java
//Consider this the pseudo-main class, where everything is run in a while loop

package control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dungeons.CorruptedCatacombs;
import dungeons.SilentSands;
import dungeons.VolatileVolcano;
import equipment.ability.Ability;
import equipment.armour.Armour;
import equipment.ring.Ring;
import equipment.weapon.Weapon;
import player.Archer;
import player.Hunter;
import player.Necromancer;
import player.Paladin;
import player.Player;
import player.Sorcerer;
import player.Warrior;
import portals.CorruptedCatacombsPortal;
import portals.InnerSanctumPortal;
import portals.VolatileVolcanoPortal;
import portals.WildWetlandsPortal;
import project.Area;
import project.Button;
import project.ClassSelectBar;
import project.CloseBackground;
import project.CompareByDepth;
import project.CompareByStrength;
import project.Dungeon;
import project.Enemy;
import project.Leaderboard;
import project.LootBag;
import project.PlayerInfo;
import project.PopText;
import project.Portal;
import project.Projectile;
import project.Tile;
import statuseffects.Status;

public class Controller extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	//static variables
	public static Player chr; //character 
	public static boolean[] keys = {false, false, false, false, false, false}; //stores the keys that are pressed
	public static boolean autofire = false; //is it autofire
	public static int[] mouseCoords = {400, 400}; //coords of the mouse
	
	public final static Random random = new Random(); //for random generation
	 
	public static ClassSelectBar[] bars; //buttons for class select
	public static String[] deathArgs; //parameters for dying/finishing
	
	//shots!
	public static LinkedList<Projectile> allyShots = new LinkedList<>(); 
	public static PriorityQueue<Projectile> allyAoE = new PriorityQueue<>(new CompareByDepth());
	public static LinkedList<Projectile> enemyShots = new LinkedList<>();
	public static PriorityQueue<Projectile> enemyAoE = new PriorityQueue<>(new CompareByDepth());
	
	//orders enemies: boss -> enemy -> spawn -> minion (desecrated shades)
	public static CompareByStrength strengthCompare = new CompareByStrength();
	
	//do stuff with bullets mid-frame to avoid concurrent modification errors (i.e splitting bullets)
	public static Stack<Projectile> addList = new Stack<>();
	//do stuff with enemies mid-frame to avoid concurrent modifaciton errors (i.e splitting enemies!)
	public static Stack<Enemy> removedEnemies = new Stack<>();
	public static Stack<Projectile> removedShots = new Stack<>();
	
	//checks if the game is about to be reset
	public static boolean listClear = false;
	
	//the frame
	private static JFrame frame;
	
	//timing
	private static int ticks;
	private static int timerTicker;
	private static String timerText = "00:00.00";
	
	//name
	public static String name = "";
	
	//cosntructor, initializes the JFrame stuff and sets preferences
	public Controller() {
		setPreferredSize(new Dimension(1100, 800));
		setBackground(new Color(0, 0, 0));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		Thread thread = new Thread(this);
		
		thread.start();
		this.setFocusable(true);
	}
	
	//chooses one option or another and returns it as a boolean
	public static boolean coinflip() {return random.nextInt(0, 2) == 0;}
	//overloaded version where it's rigged (for testing)
	public static boolean coinflip(boolean load) {return load;}
	 
	//initalizes the frame
	public static void initFrame() {
		frame = new JFrame("NICKY LOVERS");
		Controller panel = new Controller();
		frame.add(panel);
		frame.setIconImage(Driver.icon);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);	
	}
	
	//Desc: uploads the images for the bars, initalizes them
	//Param: none
	//Return: none
	public static void initBars() throws IOException{
		bars = new ClassSelectBar[] {new ClassSelectBar(200, 80, "sorcerer", "The sorcerer uses a long-range wand, wears a robe, and\ncarries a scepter that blasts through multiple enemies.", 
				ImageIO.read(new File("./char/sorc/sorcf1.png"))), 
				new ClassSelectBar(200, 200, "necromancer", "The necromancer has a twin-firing staff, wears a robe, and\nwields a lifestealing skull.", 
						ImageIO.read(new File("./char/necro/necrof1.png"))), 
				new ClassSelectBar(200, 320, "archer", "The archer carries a quick-shooting bow, wears leather\narmour, and deals massive damage with his quiver.", 
						ImageIO.read(new File("./char/archer/archerf1.png"))), 
				new ClassSelectBar(200, 440, "hunter", "The hunter holds a heavy crossbow, wears leather armour,\nand rapidly fires sharp, bouncing blades.", 
						ImageIO.read(new File("./char/hunter/hunterf1.png"))), 
				new ClassSelectBar(200, 560, "warrior", "The warrior wields a powerful axe, wears heavy armour, and\nbolsters his movement and attack speed with his magical helm.", 
						ImageIO.read(new File("./char/warrior/warriorf1.png"))), 
				new ClassSelectBar(200, 680, "paladin", "The paladin is armed with a sharp sword, wears heavy armour,\nand heals himself through his holy tome.", 
						ImageIO.read(new File("./char/paladin/paladinf1.png")))};
	}
	
	//Desc: starts the game based on a selected class
	//Param: selected class as a string
	//Return: none
	public static void initGame(String playerClass) throws IOException{
		if(playerClass.equalsIgnoreCase("sorcerer") || playerClass.equals("1")){
			chr = new Sorcerer();
		}
		else if(playerClass.equalsIgnoreCase("necromancer") || playerClass.equals("2")){
			chr = new Necromancer();
		}
		else if(playerClass.equalsIgnoreCase("archer") || playerClass.equals("3")){
			chr = new Archer();
		}
		else if(playerClass.equalsIgnoreCase("hunter") || playerClass.equals("4")){
			chr = new Hunter();
		}
		else if(playerClass.equalsIgnoreCase("warrior") || playerClass.equals("5")){
			chr = new Warrior();
		}
		else if(playerClass.equalsIgnoreCase("paladin") || playerClass.equals("6")){
			chr = new Paladin();
		}
		else {
			//if none are chosen, choose a random one
			initGame(String.valueOf(Controller.random.nextInt(1, 7)));
		}
		
		chr.getDefaultWeapon().equip();
		chr.getDefaultAbility().equip();
		
		Dungeon d = new CorruptedCatacombs();
		chr.levelReached = d.getLevel();
		d.init();
		fixGame();
		chr.setDungeon(d);
		Driver.gameState = "game";
	}
	
	//for testing only
	public static void fixGame() {
		chr.setInventory(0, Ring.Mechanical_Ring);
		chr.setInventory(1, Weapon.Bow_Of_The_Fractured);
		chr.setInventory(2, Ability.Sentinels_Quiver);
		chr.setInventory(3, Armour.t10_leather);
		chr.setLevel(12);
	}
	
	//add a projectile to the stack
	public static void act(Projectile p) {addList.add(p);}
	
	//Desc: paint component (draws everything)
	//Param: Graphics g
	//Return: none
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //clears screen
		
		//select menu
		if(Driver.gameState.equals("select")) {
			if(Player.count == 1) {
				//sets font
				g.setFont(new Font("Courier New", Font.BOLD, 32));
				FontMetrics fm = g.getFontMetrics();
				g.setColor(new Color(255, 255, 255));
				//title
				g.drawString("Class Select", 550 - fm.stringWidth("Class Select")/2, 55);
				//draws bars
				for(int i = 0; i < 6; i++) {
					bars[i].run();
					bars[i].draw(g, this);
				}
			}
			else {
				//sets font
				g.setFont(new Font("Courier New", Font.BOLD, 26));
				FontMetrics fm = g.getFontMetrics();
				g.setColor(new Color(255, 255, 255));
				//title
				g.drawString("Class Select", 550 - fm.stringWidth("Class Select")/2, 40);
				g.setFont(new Font("Courier New", Font.PLAIN, 18));
				FontMetrics fm2 = g.getFontMetrics();
				g.setColor(new Color(255, 255, 255));
				//character number
				String line = "This is character #" + Player.count + "!";
				g.drawString(line, 550 - fm2.stringWidth(line)/2, 55);
				//draws bars
				for(int i = 0; i < 6; i++) {
					bars[i].run();
					bars[i].draw(g, this);
				}
			}
		}
		//actual game 
		else if(Driver.gameState.equals("game")) {
			//add one tick to the ticks
			setTicks(getTicks() + 1);
			//animate water tiles
			for(int i = 0; i < Tile.tileList.length; i++) {
				Tile.tileList[i].runTiles();
			}
			//draw areas (and the tiles in them)
			for(Area a: Controller.chr.getDungeon().getAreas()) {
				if(a.drawable()) { //checks if the area is visible
					a.draw(g, chr.getX(),  chr.getY(), this);
				}
			}
			//ally aoe effects
			Iterator<Projectile> allyAoLi = allyAoE.iterator();
			while(allyAoLi.hasNext()) {
				Projectile p = allyAoLi.next();
				p.move();
				p.draw(g, chr.getX(), chr.getY(), this);
				if(p.ranged()) {
					allyAoLi.remove();
				}
			}
			//loot bags
			ListIterator<LootBag> lootLi = LootBag.list.listIterator();
			while(lootLi.hasNext()) {
				LootBag l = lootLi.next();
				if(l.ranged()) {
					lootLi.remove();
					continue;
				}
				if(l.getLifetime() < 9995) {
					l.draw(g, chr.getX(), chr.getY(), this);
				}
			}
			//enemy aoe effects (sorted by depth)
			Iterator<Projectile> enemyAoLi = enemyAoE.iterator();
			while(enemyAoLi.hasNext()) {
				Projectile p = enemyAoLi.next();
				if(chr != null) {
					p.move();
					p.draw(g, chr.getX(), chr.getY(), this);
					if(p.ranged()) {
						removedShots.push(p);
						enemyAoLi.remove();
					}
				}
			}
			//portals
			for(Portal p: Portal.list) {
				if(chr != null) {
					p.draw(g, this);
				}
			}
			
			//enemies
			if(chr != null) {
				for(Area a: Controller.chr.getDungeon().getAreas()) {
					Iterator<Enemy> enemyI = a.getEnemies().iterator();
					while(enemyI.hasNext()) {
						Enemy e = enemyI.next();
						e.move();
						e.draw(g, chr.getX(), chr.getY(), this);
						Iterator<Status> efx = e.getEffects().iterator();
						while(efx.hasNext()) {
							if(efx.next().tick()) {
								efx.remove();
							}
						}
					}
				}
			}
			
			//ally shots
			ListIterator<Projectile> allyLi = allyShots.listIterator();
			while(allyLi.hasNext()) {
				Projectile p = allyLi.next();
				if(chr != null) {
					p.move();
					p.draw(g, chr.getX(), chr.getY(), this);
					if(p.ranged()) {
						removedShots.push(p);
						allyLi.remove();
					}
				}
			}
	
			//enemy shots
			ListIterator<Projectile> enemyLi = enemyShots.listIterator();
			while(enemyLi.hasNext()) {
				Projectile p = enemyLi.next();
				if(chr != null) {
					p.move();
					p.draw(g, chr.getX(), chr.getY(), this);
					if(p.ranged()) {
						removedShots.push(p);
						enemyLi.remove();
					}
				}
			}
			
			//removed enemies, projectiles, enemy shots
			while(removedShots.size() > 0) {
				Projectile p = removedShots.pop();
				p.destroy();
			}
			while(removedEnemies.size() > 0) {
				Enemy e = removedEnemies.pop();
				e.destroy();
			}		
			while(addList.size() > 0) {
				Projectile p = addList.pop();
				p.act();
			}
			//run the character stuff
			if(chr != null) {
				chr.regenerate();
				chr.draw(g, this);
				chr.move();
				chr.shoot();
				chr.cast();
				chr.animate();
				chr.runTiles();
			}
			//run status effects
			if(chr != null) {
				Iterator<Status> pfx = chr.getEffects().iterator();
				while(pfx.hasNext()) {
					if(pfx.next().tick()) {
						pfx.remove();
					}
				}
				
				ListIterator<PopText> popLi = PopText.list.listIterator();
				while(popLi.hasNext()) {
					PopText p = popLi.next();
					if(p.move()) {
						popLi.remove();
						continue;
					}
					p.draw(g, chr.getX(), chr.getY());
				}
				
				//act out all item passives
				if(chr.getWeapon() != null) {
					chr.getWeapon().passive("");
				}
				if(chr.getAbility() != null) {
					chr.getAbility().passive("");
				}
				if(chr.getArmour() != null) {
					chr.getArmour().passive("");
				}
				if(chr.getRing() != null) {
					chr.getRing().passive("");
				}
				//hud
				g.setColor(new Color(80, 80, 84));
				g.drawImage(Driver.sidebar, 800, 0, this);
				chr.getSidebar().drawSide(g, this);
			}	
			
			//draw the timer
			g.setColor(new Color(0, 0, 0, 100));
			g.setFont(new Font("Courier New", Font.BOLD, 20));
			if(timerTicker > 0) {timerTicker--;}
			else {setTimerText(getStamp());}
			FontMetrics fm = g.getFontMetrics();
			g.fillRect(0, 0, fm.stringWidth(getTimerText())+40, fm.getHeight()+10);
			g.setColor(new Color(255, 255, 255));
			g.drawString(getTimerText(), 20, 25);
			
			//checks if game is to be reset
			if(listClear) {
				Area.list.clear();
				Controller.allyAoE.clear();
				Controller.enemyAoE.clear();
				Controller.allyShots.clear();
				Controller.enemyShots.clear();
				Controller.addList.clear();
				LootBag.list.clear();
				Portal.list.clear();
				PopText.list.clear();
				listClear = false;
			}
		}
		//pause screen, see respective method
		else if(Driver.gameState.equals("pause")) {
			pause(g);
		}
		//death screen, see respective method
		else if(Driver.gameState.equals("death")) {
			death(g);
		}
		//finish screen, see respective method
		else if(Driver.gameState.equals("finish")) {
			finish(g);
		}
		//leaderboard screen, see respective method
		else if(Driver.gameState.equals("leaderboard")) {
			try {
				leaderboard(g);
			} catch(IOException e) {}
		}
	}
	
	//Desc: death summary
	//Param: Graphics g
	//Return: none
	public void death(Graphics g) {
		//get the background based on highest level reached
		if(chr.levelReached == 0) {
			CloseBackground.SSCloseBackground.draw(g, this);
		}
		else if(chr.levelReached == 1) {
			CloseBackground.WWCloseBackground.draw(g, this);
		}
		else if(chr.levelReached == 2) {
			CloseBackground.VVCloseBackground.draw(g, this);
		}
		else if(chr.levelReached == 3) {
			CloseBackground.CCCloseBackground.draw(g, this);
		}
		g.setColor(new Color(55, 50, 50, 225));
		g.fillRect(0, 0, 1100, 800);
		
		int y = 75;
		String line;
		
		//Title
		g.setColor(new Color(255, 50, 50));
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		FontMetrics fm = g.getFontMetrics();
		g.drawString("You died!", 550 - fm.stringWidth("You died!")/2, y);
		y += fm.getHeight() + 20;
		//Level and class
		g.setColor(new Color(220, 220, 220));
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		fm = g.getFontMetrics();
		line = "Level " + chr.getLevel() + " " + chr.classType;
		g.drawString(line, 550 - fm.stringWidth(line)/2, y);
		y += fm.getHeight() + 10;
		//Image
		g.drawImage(chr.getImgs()[10], 522, y, 56, 56, this);
		y += 66;
		//Items
		if(chr.getWeapon() != null) {
			g.drawImage(chr.getWeapon().getImage(), 455, y, 40, 40, this);
		}
		if(chr.getAbility() != null) {
			g.drawImage(chr.getAbility().getImage(), 505, y, 40, 40, this);
		}
		if(chr.getArmour() != null) {
			g.drawImage(chr.getArmour().getImage(), 555, y, 40, 40, this);
		}
		if(chr.getRing() != null) {
			g.drawImage(chr.getRing().getImage(), 605, y, 40, 40, this);
		}
		y += 70;
		//Slain by...
		line = "Slain by " + deathArgs[0];
		g.drawString(line, 550 - fm.stringWidth(line)/2, y);
		y += fm.getHeight() + 10;
		bottom(y, g);
	}
	
	//Desc: win summary, it is very similar to death summary
	//Param: Graphics g
	//Return: none
	public void finish(Graphics g) {
		//get the background of the highest level reached
		if(chr.levelReached == 0) {
			CloseBackground.SSCloseBackground.draw(g, this);
		}
		else if(chr.levelReached == 1) {
			CloseBackground.WWCloseBackground.draw(g, this);
		}
		else if(chr.levelReached == 2) {
			CloseBackground.VVCloseBackground.draw(g, this);
		}
		else if(chr.levelReached == 3) {
			CloseBackground.CCCloseBackground.draw(g, this);
		}
		g.setColor(new Color(50, 55, 50, 225));
		g.fillRect(0, 0, 1100, 800);
		
		int y = 75;
		String line;
		
		//Title
		g.setColor(new Color(50, 255, 50));
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		FontMetrics fm = g.getFontMetrics();
		g.drawString("To Be Continued", 550 - fm.stringWidth("To Be Continued")/2, y);
		y += fm.getHeight() - 10;
		g.setFont(new Font("Courier New", Font.BOLD, 28));
		fm = g.getFontMetrics();
		g.drawString("(You Beat The Game)", 550 - fm.stringWidth("(You Beat The Game)")/2, y);
		y += fm.getHeight() + 20;
		//Level, classs
		g.setColor(new Color(220, 220, 220));
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		fm = g.getFontMetrics();
		line = "Level " + chr.getLevel() + " " + chr.classType;
		g.drawString(line, 550 - fm.stringWidth(line)/2, y);
		y += fm.getHeight() + 10;
		//Image
		g.drawImage(chr.getImgs()[10], 522, y, 56, 56, this);
		y += 66;
		//Items
		if(chr.getWeapon() != null) {
			g.drawImage(chr.getWeapon().getImage(), 455, y, 40, 40, this);
		}
		if(chr.getAbility() != null) {
			g.drawImage(chr.getAbility().getImage(), 505, y, 40, 40, this);
		}
		if(chr.getArmour() != null) {
			g.drawImage(chr.getArmour().getImage(), 555, y, 40, 40, this);
		}
		if(chr.getRing() != null) {
			g.drawImage(chr.getRing().getImage(), 605, y, 40, 40, this);
		}
		y += 70;
		bottom(y, g);
	}
	
	//Desc: draws the bottom half
	//Param: last y position, Graphics g
	//Return: none
	public void bottom(int y, Graphics g) {
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		FontMetrics fm = g.getFontMetrics();
		String line;
		//Time taken
		line = "Time: " + getStamp();
		g.drawString(line, 550 - fm.stringWidth(line)/2, y);
		y += fm.getHeight() + 10;
		//Highest level reached
		if(chr.levelReached == 0) {
			line = "Zone Reached: Silent Sands";
		}
		else if(chr.levelReached == 1) {
			line = "Zone Reached: Wild Wetlands";
		}
		else if(chr.levelReached == 2) {
			line = "Zone Reached: Volatile Volcano";
		}
		else if(chr.levelReached == 3) {
			line = "Zone Reached: Corrupted Catacombs";
		}
		else {
			line = "";
		}
		g.drawString(line, 550 - fm.stringWidth(line)/2, y);
		y += fm.getHeight() + 10;
		//# of enemies killed
		line = "Enemies Killed: " + enemiesKilled();
		g.drawString(line, 550 - fm.stringWidth(line)/2, y);
		y += fm.getHeight() + 10;
		//Most frequently killed enemy
		line = "Nemesis: " + deathArgs[1];
		g.drawString(line, 550 - fm.stringWidth(line)/2, y);
		y += fm.getHeight() + 60;
		//enter name, only if win
		if(chr.win) {g.drawString("Enter Your Name:", 550 - fm.stringWidth("Enter Your Name:")/2, y);}
			y += fm.getHeight() + 20;
		if(chr.win) {g.drawString(name, 550 - fm.stringWidth(name)/2, y);}
			y += fm.getHeight()-5;
		if(chr.win) {g.drawLine(450, y, 650, y);}
		//button stuff
		Button.deathButton.run();
		Button.deathButton.draw(g, this);
		Button.submitButton.run();
		Button.submitButton.draw(g, this);
	}
	
	//Desc: draws leaderboard
	//Param: Graphics g, ImageObserver i
	//Return: none
	public void leaderboard(Graphics g) throws IOException{
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, 1100, 800);
		
		int y = 75;
		String line;
		
		//Title
		g.setColor(new Color(180, 170, 75));
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		FontMetrics fm = g.getFontMetrics();
		g.drawString("Leaderboard", 550 - fm.stringWidth("Leaderboard")/2, y);
		y += fm.getHeight();
		
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.setColor(new Color(255, 255, 255));
		fm = g.getFontMetrics();
		
		int count = 1;
		PlayerInfo[] top = Leaderboard.top();
		g.drawString(String.format("%-12s%-20s%-25s%-8s", "#", "Class", "Player Name", "Time"), 
				550-fm.stringWidth("sixtyfivefivesixtyfivefivesixtyfivefivesixtyfivefivesixtyfivefive")/2, y);
		y += fm.getHeight() + 40;
		while(count-1 < 10 && top[count-1] != null) {
			line = String.format("%-12d%-20s%-25s%-8s", count, top[count-1].getType(), top[count-1].getName(), top[count-1].getStamp());
			if(top[count-1].getType().equals("Archer")) {
				g.drawImage(ImageIO.read(new File("./char/archer/archerf1.png")), 550-fm.stringWidth(line)/2 + fm.stringWidth("sevense"), y - fm.getHeight(), 32, 32, this);
			}
			else if(top[count-1].getType().equals("Hunter")) {
				g.drawImage(ImageIO.read(new File("./char/hunter/hunterf1.png")), 550-fm.stringWidth(line)/2 + fm.stringWidth("sevense"), y - fm.getHeight(), 32, 32, this);
			}
			else if(top[count-1].getType().equals("Necromancer")) {
				g.drawImage(ImageIO.read(new File("./char/necro/necrof1.png")), 550-fm.stringWidth(line)/2 + fm.stringWidth("sevense"), y - fm.getHeight(), 32, 32, this);	
						}
			else if(top[count-1].getType().equals("Paladin")) {
				g.drawImage(ImageIO.read(new File("./char/paladin/paladinf1.png")), 550-fm.stringWidth(line)/2 + fm.stringWidth("sevense"), y - fm.getHeight(), 32, 32, this);
			}
			else if(top[count-1].getType().equals("Sorcerer")) {
				g.drawImage(ImageIO.read(new File("./char/sorc/sorcf1.png")), 550-fm.stringWidth(line)/2 + fm.stringWidth("sevense"), y - fm.getHeight(), 32, 32, this);
			}
			else if(top[count-1].getType().equals("Warrior")) {
				g.drawImage(ImageIO.read(new File("./char/warrior/warriorf1.png")), 550-fm.stringWidth(line)/2 + fm.stringWidth("sevense"), y - fm.getHeight(), 32, 32, this);
			}
			
			g.drawString(line, 550-fm.stringWidth(line)/2, y);
			y += fm.getHeight()+20;
			count++;
		}
		
		//button stuff
		Button.lbButton.run();
		Button.lbButton.draw(g, this);
	}
	
	//Desc: gets total number of enemies killed
	//Param: none
	//Return: total number of enemies killed
	public int enemiesKilled() {
		int out = 0;
		for(int i:chr.getKillFrequency().values()) {
			out += i;
		}
		return out;
	}
	
	//Desc: gets time stamp
	//Param: none
	//Return: time stamp
	public String getStamp() {
		double seconds = getTicks() * 0.006;
		int displayMins = (int)(seconds/60);
		double displaySeconds = seconds % 60;
		timerTicker = 5;
		return String.format("%02d:%05.2f", displayMins, displaySeconds);
	}
	
	//Desc: pause gamestate, draws the game but doesn't move anything
	//Param: Graphics g
	//Return: none
	public void pause(Graphics g) {
		//draws all tiles
		for(Area a: Controller.chr.getDungeon().getAreas()) {
			if(a.drawable()) {
				a.draw(g, chr.getX(),  chr.getY(), this);
			}
		}
		//draws all ally aoe
		Iterator<Projectile> allyAoLi = allyAoE.iterator();
		while(allyAoLi.hasNext()) {
			Projectile p = allyAoLi.next();
			p.draw(g, chr.getX(), chr.getY(), this);
			if(p.ranged()) {
				allyAoLi.remove();
			}
		}
		//lootbags
		ListIterator<LootBag> lootLi = LootBag.list.listIterator();
		while(lootLi.hasNext()) {
			LootBag l = lootLi.next();
			l.draw(g, chr.getX(), chr.getY(), this);
		}
		//enemy aoe
		Iterator<Projectile> enemyAoLi = enemyAoE.iterator();
		while(enemyAoLi.hasNext()) {
			Projectile p = enemyAoLi.next();
			if(chr != null) {
				p.draw(g, chr.getX(), chr.getY(), this);
				if(p.ranged()) {
					enemyAoLi.remove();
				}
			}
		}
		//portals
		for(Portal p: Portal.list) {
			if(chr != null) {
				p.draw(g, this);
			}
		}
		//draws enemies
		if(chr != null) {
			for(Area a: Controller.chr.getDungeon().getAreas()) {
				Iterator<Enemy> enemyI = a.getEnemies().iterator();
				while(enemyI.hasNext()) {
					Enemy e = enemyI.next();
					e.draw(g, chr.getX(), chr.getY(), this);
				}
			}
		}
		//ally shots
		ListIterator<Projectile> allyLi = allyShots.listIterator();
		while(allyLi.hasNext()) {
			Projectile p = allyLi.next();
			if(chr != null) {
				p.draw(g, chr.getX(), chr.getY(), this);
				if(p.ranged()) {
					allyLi.remove();
				}
			}
		}
		//enemy shots
		ListIterator<Projectile> enemyLi = enemyShots.listIterator();
		while(enemyLi.hasNext()) {
			Projectile p = enemyLi.next();
			if(chr != null) {
				p.draw(g, chr.getX(), chr.getY(), this);
				if(p.ranged()) {
					enemyLi.remove();
				}
			}
		}
		
		//draw text
		if(chr != null) {
			chr.draw(g, this);
			
			ListIterator<PopText> popLi = PopText.list.listIterator();
			while(popLi.hasNext()) {
				PopText p = popLi.next();
				p.draw(g, chr.getX(), chr.getY());
			}

			//hud
			g.setColor(new Color(80, 80, 84));
			g.drawImage(Driver.sidebar, 800, 0, this);
			chr.getSidebar().drawSide(g, this);
		}
		
		//draw timer
		g.setColor(new Color(0, 0, 0, 100));
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		FontMetrics fm = g.getFontMetrics();
		g.fillRect(0, 0, fm.stringWidth(getTimerText())+40, fm.getHeight()+10);
		g.setColor(new Color(255, 255, 255));
		g.drawString(getTimerText(), 20, 25);
		
		//draw pause text
		g.setColor(new Color(255, 255, 255, 125));
		g.fillRect(0, 0, 1100, 800);
		g.setFont(new Font("Courier New", Font.BOLD, 50));
		FontMetrics fm2 = g.getFontMetrics();
		g.setColor(new Color(0, 0, 0));
		g.drawString("Game Paused", 550 - fm2.stringWidth("Game Paused")/2, 400);
	}

	//run method
	@Override
	public void run() {
		while(true) {
			repaint();
			try
			{	
				Thread.sleep(6);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
	}
	
	//Desc: movement tracking
	//Param: int magnitude of tracking (assumed speed)
	//Return: double array containing x and y to add
	public static double[] track(int mag) {
		double[] out = new double[2];
		if(keys[0]) {
			out[1] -= mag;
		}
		if(keys[2]) {
			out[1] += mag;
		}
		if(keys[1]) {
			out[0] -= mag;
		}
		if(keys[3]) {
			out[0] += mag;
		}
		return out;
	}
	
	//keys released!
	@Override
	public void keyReleased(KeyEvent e) {
		if(Driver.gameState.equals("game")) {
			if (Character.toLowerCase(e.getKeyChar()) == 'w')
			{
				keys[0] = false;
			}
			if (Character.toLowerCase(e.getKeyChar()) == 'a')
			{
				keys[1] = false;
			}
			if(Character.toLowerCase(e.getKeyChar()) == 's')
			{
				keys[2] = false;
			}
			if(Character.toLowerCase(e.getKeyChar()) == 'd')
			{
				keys[3] = false;
			}	
			if(e.getKeyChar() == ' ')
			{
				keys[4] = false;
			}
			if(Character.toLowerCase(e.getKeyChar()) == 'c' || Character.toLowerCase(e.getKeyChar()) == 'm' || Character.toLowerCase(e.getKeyChar()) == 'e') {
				keys[5] = false;
			}
			if(Character.toLowerCase(e.getKeyChar()) == 'p') {
				Driver.gameState = "pause";
			}
			if(Character.toLowerCase(e.getKeyChar()) == 'i')
			{
				autofire = !autofire;
			}
		}
		else if(Driver.gameState.equals("pause")) {
			if(Character.toLowerCase(e.getKeyChar()) == 'p') {
				Driver.gameState = "game";
				keys[0] = false;
				keys[1] = false;
				keys[2] = false;
				keys[3] = false;
				keys[4] = false;
				keys[5] = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	//keys pressed
	@Override
	public void keyPressed(KeyEvent e) {
		if(Driver.gameState.equals("finish")) {
			//regular typing
			if((Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyChar() == ' ')&& name.length() < 15) {
				if(!(name.length() == 0 && e.getKeyChar() == ' ')) {
					name += e.getKeyChar();
				}
			}
			
			//backspace
			if (e.getKeyCode() == 8 && name.length() > 0){
				name = name.substring(0, name.length()-1);
			}
			
			//enter
			if (name.trim().length() > 0 && e.getKeyCode() == 10)
			{
				try {
					Button.submitButton.setMouseHover(true);
					Button.submitButton.clicked();
				} catch (IOException e1) {}
			}
		}
		if(Driver.gameState.equals("game")) {
			if (Character.toLowerCase(e.getKeyChar()) == 'w')
			{
				keys[0] = true;
			}
			if (Character.toLowerCase(e.getKeyChar()) == 'a')
			{
				keys[1] = true;
			}
			if(Character.toLowerCase(e.getKeyChar()) == 's')
			{
				keys[2] = true;
			}
			if(Character.toLowerCase(e.getKeyChar()) == 'd')
			{
				keys[3] = true;
			}	
			if(e.getKeyChar() == ' ')
			{
				keys[4] = true;
			}
			if(Character.toLowerCase(e.getKeyChar()) == 'c' || Character.toLowerCase(e.getKeyChar()) == 'm' || Character.toLowerCase(e.getKeyChar()) == 'e') {
				keys[5] = true;
			}
		}
	}

	//update mouse coords
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseCoords[0] = e.getX();
		mouseCoords[1] = e.getY();	
		if(chr != null) {
			Controller.chr.getSidebar().updateHover();
		}
	}

	//update mouse coords
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseCoords[0] = e.getX();
		mouseCoords[1] = e.getY();	
		if(chr != null) {
			Controller.chr.getSidebar().updateHover();
		}
	}

	//update mouse coords
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseCoords[0] = e.getX();
		mouseCoords[1] = e.getY();	
		if(chr != null) {
			Controller.chr.getSidebar().updateHover();
		}
		if(Driver.gameState.equals("game")) {
			if(SwingUtilities.isLeftMouseButton(e)) {
				try {
					chr.getSidebar().click();
				} catch (IOException e1) {}
			}
			if(SwingUtilities.isRightMouseButton(e)) {
				try {
					chr.getSidebar().rightclick();
				} catch (IOException e1) {}
			}
		}
		else if(Driver.gameState.equals("select")) {
			for(int i = 0; i < 6; i++) {
				try {
					bars[i].clicked();
				} catch (IOException e1) {}
			}
		}
		else if(Driver.gameState.equals("death") || Driver.gameState.equals("finish")) {
			try {
				Button.deathButton.clicked();
				Button.submitButton.clicked();
			} catch (IOException e1) {}
		}
		else if(Driver.gameState.equals("leaderboard")) {
			try {
				Button.lbButton.clicked();
			} catch (IOException e1) {}
		}
	}

	//update mouse coords, click
	@Override
	public void mousePressed(MouseEvent e) {
		mouseCoords[0] = e.getX();
		mouseCoords[1] = e.getY();	
		if(chr != null) {
			Controller.chr.getSidebar().updateHover();
		}
		if(SwingUtilities.isLeftMouseButton(e)) {
			keys[4] = true;
		}
		if(SwingUtilities.isRightMouseButton(e)) {
			keys[5] = true;
		}
	}

	//update mouse coords, release click
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseCoords[0] = e.getX();
		mouseCoords[1] = e.getY();	
		if(chr != null) {
			Controller.chr.getSidebar().updateHover();
		}
		if(SwingUtilities.isLeftMouseButton(e)) {
			keys[4] = false;
		}
		if(SwingUtilities.isRightMouseButton(e)) {
			keys[5] = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	//Desc: various getters and setters
	//Param: values for setters
	//Return: values for getters
	public static int getTicks() {
		return ticks;
	}

	public static void setTicks(int ticks) {
		Controller.ticks = ticks;
	}

	public static String getTimerText() {
		return timerText;
	}

	public static void setTimerText(String timerText) {
		Controller.timerText = timerText;
	}
}
