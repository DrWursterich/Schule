package ISBN;

import java.util.Scanner;

public class ISBN {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String eingabe = scanner.next();
		scanner.close();
		int[] positionen = new int[0];
		int[] zahlen = new int[0];

		for (int i=0;i<eingabe.length();i++) {
			char buchstabe = eingabe.charAt(i);
			try{
				zahlen = addToArray(zahlen, Integer.parseInt(""+buchstabe));
			}catch(NumberFormatException error){
				positionen = addToArray(positionen, i);
			};
		}
		positionen = addToArray(positionen, positionen.length+zahlen.length);
		zahlen = addToArray(zahlen, getPruefziffer(zahlen));
		String ausgabe = "";
		int count = 0;
		for (int i=0;i<zahlen.length+positionen.length;i++) {
			if (count < positionen.length ? positionen[count] == i : false) {
				ausgabe += "-";
				count++;
			} else {
				ausgabe += zahlen[i-count];
			}
		}
		System.out.println(ausgabe);
	}

	private static int getPruefziffer(int[] isbnZahlen) {
		int ret = 0;
		for (int i=0;i<isbnZahlen.length;i++) {
			ret += (i+1)*isbnZahlen[i];
		}
		return ret % 11;
	}

	private static int[] addToArray(int[] a, int b) {
		int[] temp = new int[a.length+1];
		System.arraycopy(a, 0, temp, 0, a.length);
		temp[temp.length-1] = b;
		return temp;
	}
}
