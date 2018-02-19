package Streams;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<Musik> l = Musik.getRandomMusik(2);
		for (Musik m : l) {
			System.out.println(m.toString());
		}
	}
}
