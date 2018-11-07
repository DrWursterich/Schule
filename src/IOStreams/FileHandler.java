package IOStreams;

import java.io.File;
import java.io.IOException;

public class FileHandler {
	public static void createFile(String filename) throws IOException {
		if (!(new File(filename)).createNewFile()) {
			throw new IOException("Unable to create file " + filename);
		}
	}

	public static void deleteFile(String filename) throws IOException {
		File file = new File(filename);

		if (file.isDirectory()) {
			throw new IOException("File is a directory");

		}
		if (!file.delete()) {
			throw new IOException("Unable to delete file " + filename);
		}
	}
	
	public static void createDirectory(String directoryname) throws IOException {
		if (!(new File(directoryname)).mkdir()) {
			throw new IOException("Unable to create directory" + directoryname);
		}
	}
}
