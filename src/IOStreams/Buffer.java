package IOStreams;

import java.io.*;

public class Buffer {
	public static void main(String... args) {
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		try {
			System.out.println("Bitte Zeichen eingeben:");
			String result = br.readLine();
			System.out.println(result);

		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}
