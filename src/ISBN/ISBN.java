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
		for (int i=0;i<zahlen.length+positionen.length-1;i++) {
			if (positionen[count] == i) {
				ausgabe += "-";
				count = Math.min(count+1, positionen.length-1);
			} else {
				ausgabe += zahlen[i-count];
			}
		}
		ausgabe += zahlen[zahlen.length-1];
		System.out.println(ausgabe);
	}
	
	private static int getPruefziffer(int[] isbnZahlen) {
		return (int)Math.random()*9;
	}
	
	private static int[] addToArray(int[] a, int b) {
		int[] temp = new int[a.length+1];
		System.arraycopy(a, 0, temp, 0, a.length);
		temp[temp.length-1] = b;
		return temp;
	}
}
