package project;

import java.util.Comparator;

public class CompareByStrength implements Comparator<Enemy>{
	@Override
	public int compare(Enemy e1, Enemy e2) {
		return (int) (e1.getPriority() - e2.getPriority());
	}
}
