package Exceptions;

public class RTErrorProg1 {
	public static void main(String...args) {
		int i, base = 10;
		for (; base >= 2; --base) {
			try {
				i = Integer.parseInt("40", base);
				System.out.println("40 base " + base + " = " + i);
			} catch (NumberFormatException e) {
				System.out.println("40 kann nicht zur basis " + base + " genommen werden!");
			}
		}
	}
}
