package project;

import java.util.Comparator;

public class CompareByDepth implements Comparator<Projectile>{
	@Override
	public int compare(Projectile o1, Projectile o2) {
		return (int) (o1.y - o2.y);
	}
}
