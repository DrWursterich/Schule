package IOStreams;

import java.io.File;
import java.io.IOException;

public class FileHandler {
	public void createFile(String filename) throws IOException {
		if (!(new File(filename)).createNewFile()) {
			throw new IOException("Unable to create file " + filename);
		}
	}
}
