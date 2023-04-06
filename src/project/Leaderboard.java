package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

public class Leaderboard {
	private static TreeSet<PlayerInfo> board = new TreeSet<>();
	
	public static void read() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("./leaderboard.txt"));
		String line = br.readLine();
		while(line != null) {
			PlayerInfo p = PlayerInfo.readPlayer(line);
			if(p != null) {
				getBoard().add(p);
			}
			line = br.readLine();
		}
		br.close();
	}
	
	public static void write() throws IOException{
		FileWriter fw = new FileWriter(new File("./leaderboard.txt"));
		PlayerInfo[] p = top();
		int count = 0;
		while(count < 10 && p[count] != null) {
			fw.write(p[count].getString() + "\n");
			count++;
		}
		fw.close();
	}
	
	public static void insert(String type, String name, String stamp, int ticks){
		getBoard().add(new PlayerInfo(type, name, stamp, ticks));
	}
	
	public static PlayerInfo[] top() {
		PlayerInfo[] out = new PlayerInfo[10];
		Iterator<PlayerInfo> ip = getBoard().iterator();
		int count = 0;
		while(ip.hasNext() && count < 10) {
			out[count] = ip.next();
			count++;
		}
		return out;
	}

	public static TreeSet<PlayerInfo> getBoard() {
		return board;
	}

	public static void setBoard(TreeSet<PlayerInfo> board) {
		Leaderboard.board = board;
	}
}