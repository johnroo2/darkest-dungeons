package player;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;

import control.Controller;
import control.Driver;
import equipment.ability.Ability;
import project.Equipment;
import project.LootBag;
import project.Portal;

public class Sidebar {
	private int select = -1;
	private int hover = -1;
	private final static Color XP_PURPLE = new Color(170, 50, 190);
	private final static Color MANA_BLUE = new Color(100, 180, 255);
	private final static Color SIDE_HEALTH_GREEN = new Color(95, 205, 80);
	private final static Color ST_GOLD = new Color(235, 185, 100);
	
	private final static int BAR_REFRESH_INTERVAL = 20;
	private final static Font CHAR_BAR_FONT = new Font("Courier New", Font.BOLD, 16);
	private final static Font STATS_FONT = new Font("Courier New", Font.BOLD, 18);
	private final static Font TIER_FONT = new Font("Courier New", Font.BOLD, 20);
	private final static Font PORTAL_FONT = new Font("Courier New", Font.BOLD, 24);
	private String barHp, barMp, barXp;
	private int barTicks;
	
	public void drawSide(Graphics g, ImageObserver i) {
		this.drawBars(g);
		this.drawStats(g);
		this.drawBaseInventory(g, i);
		this.drawSelect(g, i);
		
		Portal po = Controller.chr.closePortal();
		if(po != null) {
			g.setFont(PORTAL_FONT);
			g.setColor(Color.WHITE);
			FontMetrics fm = g.getFontMetrics();
			g.drawString(po.getTitle(), 950-fm.stringWidth(po.getTitle())/2, 680);
			po.getEb().run();
			po.getEb().draw(g, i);
			
			if(select >= 12) {
				select = -1;
			}
		}
		else {
			this.drawLootInventory(g, i);
		}
		this.drawDescriptions(g, i);
	}
	
	public void drawDescriptions(Graphics g, ImageObserver i) {
		Equipment display = null;
		LootBag l = Controller.chr.closeBag();
		if(hover == 0) {display = Controller.chr.getWeapon();}
		else if(hover == 1) {display = Controller.chr.getAbility();}
		else if(hover == 2) {display = Controller.chr.getArmour();}
		else if(hover == 3) {display = Controller.chr.getRing();}
		else if(hover >= 4 && hover < 12) {display = Controller.chr.getInventory()[hover-4];}
		else if(l != null && hover >= 12 && hover < 20) {display = l.getInventory()[hover-12];}
		
		if(display != null) {
			display.drawDescription(g, i, hover);
		}
	}
	
	public void drawBars(Graphics g) {
		barTicks -= 1;
		if(barTicks <= 0) {
			barTicks = BAR_REFRESH_INTERVAL;
			barHp = "HP: " + (int)Controller.chr.currentHealth + "/" + (int)Controller.chr.maxHealth;
			barMp = "MP: " + (int)Controller.chr.currentMana + "/" + (int)Controller.chr.maxMana;	
			if(Controller.chr.getLevel() < 15) {
				barXp = "XP: " + (int)Controller.chr.currentXp + "/" + Controller.chr.getNextXp();
			}
			else {
				barXp = "Lv MAX";
			}
		}
		g.setColor(Color.BLACK);
		g.fillRect(820, 195, 260, 35); 
		g.fillRect(820, 240, 260, 35); 
		g.fillRect(820, 285, 260, 35); 
		g.setColor(XP_PURPLE);
		if(Controller.chr.getLevel() < 15) {
			g.fillRect(822, 196, (int) (256.0 * Controller.chr.currentXp/Controller.chr.getNextXp()), 31); 
		}
		else {
			g.fillRect(822, 196, 256, 31); 
		}
		g.setColor(SIDE_HEALTH_GREEN);
		g.fillRect(822, 241, (int) (256.0 * Controller.chr.currentHealth/Controller.chr.maxHealth), 31); 
		g.setColor(MANA_BLUE);
		g.fillRect(822, 286, (int) (256.0 * Controller.chr.currentMana/Controller.chr.maxMana), 31); 
		g.setColor(Color.WHITE);
		g.setFont(CHAR_BAR_FONT);
		g.drawString(barXp, 830, 212);
		if(Controller.chr.getLevel()< 15) {
			g.drawString("Lv " + Controller.chr.getLevel(), 1025, 212);
		}
		g.drawString(barHp, 830, 257);
		g.drawString(barMp, 830, 302);
	}
	
	public void drawStats(Graphics g) {
		g.setFont(STATS_FONT);
		g.drawString("ATK: " + Controller.chr.attack, 830, 60);
		g.drawString("DEX: " + Controller.chr.dexterity, 970, 60);
		g.drawString("DEF: " + Controller.chr.defense, 830, 95);
		g.drawString("SPD: " + Controller.chr.speed, 970, 95);
		g.drawString("VIT: " + Controller.chr.vitality, 830, 130);
		g.drawString("WIS: " + Controller.chr.wisdom, 970, 130);
		g.drawString("CRT: " + Controller.chr.crit, 830, 165);
		g.drawString("SPL: " + Controller.chr.spellsurge, 970, 165);
	}
	
	public void drawBaseInventory(Graphics g, ImageObserver i) {
		g.setFont(TIER_FONT);
		for(int q = 0; q < 4; q++) {
			g.drawImage(Driver.itemSquare, 814 + 70 * q, 354, 64, 64, i);
			g.drawImage(Driver.itemSquare, 814 + 70 * q, 474, 64, 64, i);
			g.drawImage(Driver.itemSquare, 814 + 70 * q, 544, 64, 64, i);
		}
		if(Controller.chr.weapon != null) {
			g.drawImage(Controller.chr.weapon.getImage(), 822, 362, 48, 48, i);
			if(!Controller.chr.weapon.getTierDisplay().equals("ST")) {
				g.drawString(Controller.chr.weapon.getTierDisplay(), 822, 410);
			}
			else {
				g.setColor(getStGold());
				g.drawString(Controller.chr.weapon.getTierDisplay(), 822, 410);
				g.setColor(Color.WHITE);
			}
		}
		else {
			g.drawImage(Driver.weaponIcon, 822, 362, 48, 48, i);
		}
		if(Controller.chr.ability != null) {
			g.drawImage(Controller.chr.ability.getImage(), 892, 362, 48, 48, i);
			if(!Controller.chr.ability.getTierDisplay().equals("ST")) {
				g.drawString(Controller.chr.ability.getTierDisplay(), 892, 410);
			}
			else {
				g.setColor(getStGold());
				g.drawString(Controller.chr.ability.getTierDisplay(), 892, 410);
				g.setColor(Color.WHITE);
			}
			if(((Ability)Controller.chr.ability).getManaCost() > Controller.chr.getCurrentMana() || Controller.chr.getCastTicks() > 0) {
				g.setColor(new Color(0, 0, 0, 100));
				g.fillRect(886, 356, 60, 60);
				g.setColor(Color.WHITE);
			}
		}
		else {
			g.drawImage(Driver.abilityIcon, 892, 362, 48, 48, i);
		}
		if(Controller.chr.armour != null) {
			g.drawImage(Controller.chr.armour.getImage(), 962, 362, 48, 48, i);
			if(!Controller.chr.armour.getTierDisplay().equals("ST")) {
				g.drawString(Controller.chr.armour.getTierDisplay(), 962, 410);
			}
			else {
				g.setColor(getStGold());
				g.drawString(Controller.chr.armour.getTierDisplay(), 962, 410);
				g.setColor(Color.WHITE);
			}
		}
		else {
			g.drawImage(Driver.armourIcon, 962, 362, 48, 48, i);
		}
		if(Controller.chr.ring != null) {
			g.drawImage(Controller.chr.ring.getImage(), 1032, 362, 48, 48, i);
			if(!Controller.chr.ring.getTierDisplay().equals("ST")) {
				g.drawString(Controller.chr.ring.getTierDisplay(), 1032, 410);
			}
			else {
				g.setColor(getStGold());
				g.drawString(Controller.chr.ring.getTierDisplay(), 1032, 410);
				g.setColor(Color.WHITE);
			}
		}
		else {
			g.drawImage(Driver.ringIcon, 1032, 362, 48, 48, i);
		}
		
		for(int q = 0; q < 8; q ++) {
			if(Controller.chr.inventory[q] != null) {
				if(q < 4) {
					g.drawImage(Controller.chr.inventory[q].getImage(), 822 + 70 * q, 482, 48, 48, i);
					if(!Controller.chr.inventory[q].getTierDisplay().equals("ST")) {
						g.drawString(Controller.chr.inventory[q].getTierDisplay(), 822 + 70 * q, 530);
					}
					else {
						g.setColor(getStGold());
						g.drawString(Controller.chr.inventory[q].getTierDisplay(), 822 + 70 * q, 530);
						g.setColor(Color.WHITE);
					}
				}
				else {
					g.drawImage(Controller.chr.inventory[q].getImage(), 822 + 70 * (q-4), 552, 48, 48, i);
					if(!Controller.chr.inventory[q].getTierDisplay().equals("ST")) {
						g.drawString(Controller.chr.inventory[q].getTierDisplay(), 822 +  70 * (q-4), 600);
					}
					else {
						g.setColor(getStGold());
						g.drawString(Controller.chr.inventory[q].getTierDisplay(), 822 +  70 * (q-4), 600);
						g.setColor(Color.WHITE);
					}
				}
			}
		}
	}
	
	public void drawSelect(Graphics g, ImageObserver i) {
		if(select >= 0) {
			if(select < 4) {
				g.drawImage(Driver.itemSelect, 814 + 70 * (select%4), 354, 64, 64, i);
			}
			else if(select < 8) {
				g.drawImage(Driver.itemSelect, 814 + 70 * (select%4), 474, 64, 64, i);
			}
			else if(select < 12) {
				g.drawImage(Driver.itemSelect, 814 + 70 * (select%4), 544, 64, 64, i);
			}
		}
	}
	
	public void drawLootInventory(Graphics g, ImageObserver i) {
		LootBag l = Controller.chr.closeBag();
		if(l != null) {
			for(int q = 0; q < 4; q++) {
				g.drawImage(Driver.itemSquare, 814 + 70 * q, 644, 64, 64, i);
				g.drawImage(Driver.itemSquare, 814 + 70 * q, 714, 64, 64, i);
			}
			for(int p = 0; p < 8; p++) {
				if(l.getInventory()[p] != null) {
					if(p < 4) {
						g.drawImage(l.getInventory()[p].getImage(), 822 + 70 * p, 652, 48, 48, i);
						if(!l.getInventory()[p].getTierDisplay().equals("ST")) {
							g.drawString(l.getInventory()[p].getTierDisplay(), 822 + 70 * p, 700);
						}
						else {
							g.setColor(getStGold());
							g.drawString(l.getInventory()[p].getTierDisplay(), 822 + 70 * p, 700);
							g.setColor(Color.WHITE);
						}
					}
					else {
						g.drawImage(l.getInventory()[p].getImage(), 822 + 70 * (p-4), 722, 48, 48, i);
						g.drawString(l.getInventory()[p].getTierDisplay(), 822 + 70 * (p-4), 770);
						if(!l.getInventory()[p].getTierDisplay().equals("ST")) {
							g.drawString(l.getInventory()[p].getTierDisplay(), 822 + 70 * (p-4), 770);
						}
						else {
							g.setColor(getStGold());
							g.drawString(l.getInventory()[p].getTierDisplay(), 822 + 70 * (p-4), 770);
							g.setColor(Color.WHITE);
						}
					}
				}
			}
		}
		if(l != null && select >= 12) {
			if(select < 16) {
				g.drawImage(Driver.itemSelect, 814 + 70 * (select%4), 644, 64, 64, i);
			}
			else if(select < 20) {
				g.drawImage(Driver.itemSelect, 814 + 70 * (select%4), 714, 64, 64, i);
			}
		}
		if(l == null && select >= 12) {
			select = -1;
		}
		
		if(select >= 20) {
			select = -1;
		}
	}
	
	public void click() throws IOException {
		hover = -1;
		Portal po = Controller.chr.closePortal();
		if(po != null) {
			po.getEb().clicked();
		}
		if(select == -1) {
			select = getBox();
		}
		else {
			swap(select, getBox());
			select = -1;
		}
	}
	
	public void rightclick() throws IOException {
		hover = -1;
		Portal po = Controller.chr.closePortal();
		if(po != null) {
			po.getEb().clicked();
		}
		if(select == -1) {
			rightclickEquip();
		}
		else {
			rightclickEquip();
			select = -1;
		}
	}
	
	public void rightclickEquip() {
		int opt = getBox();
		LootBag bag = Controller.chr.closeBag();
		if(opt < 4) { //left click an equipped item
			
		}
		else if(opt < 12) { //left click an inventory item
			Equipment e = Controller.chr.getInventory()[opt-4];
			if(e != null && e.getType() != null && !e.getType().equals("consumable")) {
				if(e.getType().equals("weapon") && Controller.chr.weapon == null) {
					swap(opt, 0);
				}
				else if(e.getType().equals("ability") && Controller.chr.ability == null) {
					swap(opt, 1);
				}
				else if(e.getType().equals("armour") && Controller.chr.armour == null) {
					swap(opt, 2);
				}
				else if(e.getType().equals("ring") && Controller.chr.ring == null) {
					swap(opt, 3);
				}
			}
		}
		else if(opt < 20 && bag != null) { //left click an inventory item
			Equipment e = bag.getInventory()[opt-12];
			if(e != null && e.getType() != null && !e.getType().equals("consumable")) {
				if(e.getType().equals("weapon") && Controller.chr.weapon == null) {
					swap(opt, 0);
				}
				else if(e.getType().equals("ability") && Controller.chr.ability == null) {
					swap(opt, 1);
				}
				else if(e.getType().equals("armour") && Controller.chr.armour == null) {
					swap(opt, 2);
				}
				else if(e.getType().equals("ring") && Controller.chr.ring == null) {
					swap(opt, 3);
				}
				else {
					for(int i = 0; i < 8; i++) {
						if(Controller.chr.getInventory()[i] == null) {
							swap(opt, i + 4);
							break;
						}
					}
				}
			}
		}
	}
	
	public void updateHover() {
		hover = this.getBox();
		if(hover == select) {
			hover = -1;
		}
	}
	
	public int getBox() {
		for(int i = 0; i < 4; i++) {
			if(810 + 70 * i < Controller.mouseCoords[0] && 880 + 70 * i > Controller.mouseCoords[0]) {
				if(354 < Controller.mouseCoords[1] && 418 > Controller.mouseCoords[1]) {
					return i;			
				}
				else if(474 < Controller.mouseCoords[1] && 538 > Controller.mouseCoords[1]) {
					return i+4;
				}
				else if(544 < Controller.mouseCoords[1] && 608 > Controller.mouseCoords[1]) {
					return i+8;
				}
				else if(644 < Controller.mouseCoords[1] && 708 > Controller.mouseCoords[1]) {
					return i+12;
				}
				else if(714 < Controller.mouseCoords[1] && 778 > Controller.mouseCoords[1]) {
					return i+16;
				}
			}
		}
		return -1;
	}
	
	public void blank(int spot) {
		if(spot == 0) {
			Controller.chr.weapon.dequip();
			Controller.chr.weapon = null;
		}
		else if(spot == 1) {
			Controller.chr.ability.dequip();
			Controller.chr.ability = null;
		}
		else if(spot == 2) {
			Controller.chr.armour.dequip();
			Controller.chr.armour = null;
		}
		else if(spot == 3) {
			Controller.chr.ring.dequip();
			Controller.chr.ring = null;
		}
		else if(spot < 12) {
			Controller.chr.inventory[spot-4] = null;
		}
		else {
			LootBag bag = Controller.chr.closeBag();
			if(bag != null) {
				bag.getInventory()[spot-12] = null;
			}
		}
	}
	
	public boolean validWeapon(Equipment e) {
		if(e != null) {
			if(e.getSubtype().equals(Controller.chr.wSubtype)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean validAbility(Equipment e) {
		if(e != null) {
			if(e.getSubtype().equals(Controller.chr.abSubtype1) || e.getSubtype().equals(Controller.chr.abSubtype2)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean validArmour(Equipment e) {
		if(e != null) {
			if(e.getSubtype().equals(Controller.chr.arSubtype)) {
				return true;
			}
		}
		return false;
	}
	
	public void swap(int first, int second) {
		Equipment eFirst = (Equipment) null;
		Equipment eSecond = (Equipment) null;
		LootBag bag = Controller.chr.closeBag();
		
		if(first < 0) {
			first = -1;
		}
		else if(first < 4) {
			if(first == 0) {
				eFirst = Controller.chr.weapon;
			}
			else if(first == 1) {
				eFirst = Controller.chr.ability;
			}
			else if(first == 2) {
				eFirst = Controller.chr.armour;
			}
			else if(first == 3) {
				eFirst = Controller.chr.ring;
			}
		}
		else if(first < 12) {
			eFirst = Controller.chr.inventory[first - 4];
		}
		else {
			if(bag != null) {
				eFirst = bag.getInventory()[first - 12];
			}
			else {
				select = -1;
				return;
			}
		}
		
		if(second < 0) {
			second = -1;
		}
		else if(second < 4) {
			if(second == 0) {
				eSecond = Controller.chr.weapon;
			}
			else if(second == 1) {
				eSecond = Controller.chr.ability;
			}
			else if(second == 2) {
				eSecond = Controller.chr.armour;
			}
			else if(second == 3) {
				eSecond = Controller.chr.ring;
			}
		}
		else if(second < 12) {
			eSecond = Controller.chr.inventory[second - 4];
		}
		else {
			if(bag != null) {
				eSecond = bag.getInventory()[second - 12];
			}
			else {
				return;
			}
		}
		
		if((eFirst == null && eSecond == null) || (eFirst == null && second == -1)) { //two nulls, do nothing
			return;
		}
		else if(eFirst == null) { //first is null, move second to that spot if possible
			if(first < 4) {
				if(first == 0) {
					if(eSecond.getType().equals("weapon") && validWeapon(eSecond)) {
						Controller.chr.weapon = eSecond;
						eSecond.equip();
					}
					else {
						return;
					}
				}
				else if(first == 1) {
					if(eSecond.getType().equals("ability") && validAbility(eSecond)) {
						Controller.chr.ability = eSecond;
						eSecond.equip();
					}
					else {
						return;
					}
				}
				else if(first == 2) {
					if(eSecond.getType().equals("armour") && validArmour(eSecond)) {
						Controller.chr.armour = eSecond;
						eSecond.equip();
					}	
					else {
						return;
					}
				}
				else if(first == 3) {
					if(eSecond.getType().equals("ring")) {
						Controller.chr.ring = eSecond;
						eSecond.equip();
					}
					else {
						return;
					}
				}
			}
			else if(first < 12) {
				Controller.chr.inventory[first-4] = eSecond;
			}
			else {
				if(bag != null) {
					bag.getInventory()[first-12] = eSecond;
				}
			}
			
			blank(second);
		}
		else if(second == -1) { //second is blank, move first out and drop a bag
			if(second < 12) {
				blank(first);
				if(bag != null && !bag.full()) {
					for(int i = 0; i < 8; i++) {
						if(bag.getInventory()[i] == null) {
							bag.getInventory()[i] = eFirst;
							break;
						}
					}
				}
				else {
					new LootBag(Controller.chr.getX(), Controller.chr.getY(), new Equipment[] {eFirst});
				}
			}
		}
		else if(eSecond == null) { //second is null, move first to that if possible
			if(second < 4) {
				if(second == 0) {
					if(eFirst.getType().equals("weapon") && validWeapon(eFirst)) {
						Controller.chr.weapon = eFirst;
						eFirst.equip();
					}
					else {
						return;
					}
				}
				else if(second == 1) {
					if(eFirst.getType().equals("ability") && validAbility(eFirst)) {
						Controller.chr.ability = eFirst;
						eFirst.equip();
					}
					else {
						return;
					}
				}
				else if(second == 2) {
					if(eFirst.getType().equals("armour") && validArmour(eFirst)) {
						Controller.chr.armour = eFirst;
						eFirst.equip();
					}	
					else {
						return;
					}
				}
				else if(second == 3) {
					if(eFirst.getType().equals("ring")) {
						Controller.chr.ring = eFirst;
						eFirst.equip();
					}
					else {
						return;
					}
				}
			}
			else if(second < 12) {
				Controller.chr.inventory[second-4] = eFirst;
			}
			else {
				if(bag != null) {
					bag.getInventory()[second-12] = eFirst;
				}
			}
			
			blank(first);
		}
		else if(first == second) {
			return;
		}
		else { //neither first nor second is null, swap two items if possible
			if(first < 4 && second < 4) {
				return;
			}
			else {
				if(first < 4) {
					if(eSecond.getType().equals("weapon") && first == 0 && eFirst.getType().equals("weapon") && validWeapon(eSecond)) {
						Controller.chr.weapon = eSecond;
						eSecond.equip();
						eFirst.dequip();
					}
					else if(eSecond.getType().equals("ability") && first == 1 && eFirst.getType().equals("ability") && validAbility(eSecond)) {
						Controller.chr.ability = eSecond;
						eSecond.equip();
						eFirst.dequip();
					}
					else if(eSecond.getType().equals("armour") && first == 2 && eFirst.getType().equals("armour") && validArmour(eSecond)) {
						Controller.chr.armour = eSecond;
						eSecond.equip();
						eFirst.dequip();
					}
					else if(eSecond.getType().equals("ring") && first == 3 && eFirst.getType().equals("ring")) {
						Controller.chr.ring = eSecond;
						eSecond.equip();
						eFirst.dequip();
					}
					else {
						return;
					}	
					if(second < 12) {
						Controller.chr.inventory[second-4] = eFirst;					
					}
					else {
						bag.getInventory()[second-12] = eFirst;
					}
				}
				else if(second < 4) {
					if(eFirst.getType().equals("weapon") && second == 0 && eSecond.getType().equals("weapon") && validWeapon(eFirst)) {
						Controller.chr.weapon = eFirst;
						eFirst.equip();
						eSecond.dequip();
					}
					else if(eFirst.getType().equals("ability") && second == 1 && eSecond.getType().equals("ability") && validAbility(eFirst)) {
						Controller.chr.ability = eFirst;
						eFirst.equip();
						eSecond.dequip();
					}
					else if(eFirst.getType().equals("armour") && second == 2 && eSecond.getType().equals("armour") && validArmour(eFirst)) {
						Controller.chr.armour = eFirst;
						eFirst.equip();
						eSecond.dequip();
					}
					else if(eFirst.getType().equals("ring") && second == 3 && eSecond.getType().equals("ring")) {
						Controller.chr.ring = eFirst;
						eFirst.equip();
						eSecond.dequip();
					}
					else {
						return;
					}
					if(first < 12) {
						Controller.chr.inventory[first-4] = eSecond;
					}
					else {
						bag.getInventory()[first-12] = eSecond;
					}
				}
				else {
					if(first < 12) {
						Controller.chr.inventory[first-4] = eSecond;
					}
					else {
						if(bag != null) {
							bag.getInventory()[first-12] = eSecond;
						}
					}
					if(second < 12) {
						Controller.chr.inventory[second-4] = eFirst;
					}
					else {
						if(bag != null) {
							bag.getInventory()[second-12] = eFirst;
						}
					}
				}
			}
		}
	}

	public static Color getStGold() {
		return ST_GOLD;
	}
}
