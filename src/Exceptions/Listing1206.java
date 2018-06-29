package Exceptions;

public class Listing1206 {
	public static void main(String...args) {
		int i = 0, j = 0, base = 10;
		String[] numbers = {"10", "20", "30"};
		try {
			for (;base >= 2; --base) {
				for (; j <= 3; ++j) {
					i = Integer.parseInt(numbers[j], base);
					System.out.println(numbers[j] + " base " + base + " = " + i);
				}
			}
		} catch(IndexOutOfBoundsException e1) {
			System.out.println("***Fehler: " + e1);
		} catch(NumberFormatException e2) {
			System.out.println("***Fehler: " + e2);
		}
	}
}
