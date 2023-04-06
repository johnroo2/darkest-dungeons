package project;

public class PlayerInfo implements Comparable<PlayerInfo>{
	private String type;
	private String name;
	private String stamp;
	private int ticks;
	
	public PlayerInfo(String type, String name, String stamp, int ticks) {
		if(name.equals("")) {
			name = "(no name)";
		}
		this.type = type;
		this.name = name;
		this.stamp = stamp;
		this.ticks = ticks;
	}
	
	public static PlayerInfo readPlayer(String s) {
		//Paladin,johnroo2,10:00:00,12341234
		try {
			if(s.indexOf(',') >= 0) {
				String type = s.substring(0, s.indexOf(','));
				s = s.substring(s.indexOf(',')+1);
				String name = s.substring(0, s.indexOf(','));
				s = s.substring(s.indexOf(',')+1);
				String stamp = s.substring(0, s.indexOf(','));
				s = s.substring(s.indexOf(',')+1);
				int ticks = Integer.valueOf(s);
				return new PlayerInfo(type, name, stamp, ticks);
			}
		}
		catch(StringIndexOutOfBoundsException e) {
			System.out.println("out of bounds");
		}
		return null;
	}
	
	public String getType() {
		return this.type;
	}
	public String getName() {
		return this.name;
	}
	public String getStamp() {
		return this.stamp;
	}
	
	public int compareTo(PlayerInfo p) {
		if(this.ticks != p.ticks) {
			return this.ticks - p.ticks;
		}
		else {
			return 1;
		}
	}
	
	public String getString() {
		return type+","+name+","+stamp+","+ticks;
	}
}
