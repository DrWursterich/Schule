package Logger;

import static java.util.logging.Level.SEVERE;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import Logger.logging.LoggingController;

public class LoggingSimulation {
	public static void main(String...args) throws IOException {
		LoggingController.log(SEVERE, "message");
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("")), 0);
	}
}
