package IOStreams;

import java.io.*;

public class Sandbox {
	public static void main(String... args){
		byte[] b = new byte[1];
		try {
			System.out.println("Bitte Zeichen eingeben:");
			do {
				int result = System.in.read();
				if (!System.lineSeparator().contains("" + (char)result)) {
					System.out.println(
							(char)result + " hat den ASCII-Code " + result);
				}
			} while(System.in.available() > 0);
		} catch(IOException e) {
			System.out.println(e.toString());
		}
	}
}
