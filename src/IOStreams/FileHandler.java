package IOStreams;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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
	
	public static void createDirectory(String directoryname) 
			throws IOException {
		if (!(new File(directoryname)).mkdir()) {
			throw new IOException("Unable to create directory" + directoryname);
		}
	}

	public static void createFileAt(String filename, File directory) 
			throws IOException {
		if (!directory.exists() || !directory.isDirectory()) {
			throw new IOException(
					"Unable to locate directory " + directory.getName());
		}
		FileHandler.createFile(
				directory.getName() 
				+ System.getProperty("path.separator") 
				+ filename);
	}
	
	public static void renameFile(File file, String newName) 
			throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		if (!file.renameTo(new File(newName))) {
			throw new IOException("Unable to rename File " + file.getName());
		}
	}
	
	public static String getFileName(File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		return file.getName();
	}
	
	public static File getParentDirectory(File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		return new File(file.getParent());
	}
	
	public static String getAbsoloutPath(File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		return file.getAbsolutePath();
	}
	
	public static String getRelativPath(File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		return file.getPath();
	}
	
	public static double getByteSite(File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		return file.getTotalSpace();
	}
	
	public static boolean fileExists(File file) {
		return file.isFile() && file.exists();
	}
	
	public static boolean canRead(File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		return file.canRead();
	}
	
	public static boolean canWrite(File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		return file.canWrite();
	}
	
	public static void lockFile(File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		if (!file.canWrite() || !file.setReadOnly()) {
			throw new IOException("Unable to lock File " + file.getName());
		}
	}
	
	public static void writeToFile(String input, File file) throws IOException {
		if (!file.isFile() || !file.exists()) {
			throw new IOException("Unable to locate file " + file.getName());
		}
		if (!file.canWrite()) {
			throw new IOException("Unable to write to file " + file.getName());
		}
		
		BufferedOutputStream output = null;
		try {
			output = new BufferedOutputStream(new FileOutputStream(file));
			output.write(input.getBytes());
		} catch (IOException e) {
			throw e;
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}
	
	public static void createFilledFile(String filename) throws IOException {
		FileHandler.createFile(filename);
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= 100; i++) {
			builder.append("Die Zahl lautet " + i + "."
					+ (i != 100 ? System.lineSeparator() : ""));
		}
		FileHandler.writeToFile(builder.toString(), new File(filename));
	}
	
	public static String getAllLines(File file) throws IOException {
		final StringBuilder output = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			boolean isFirst = true;
			while ((line = reader.readLine()) != null) {
				output.append((isFirst
							? System.lineSeparator() 
							: "")
						+ line);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			reader.close();
		}
		return output.toString();
	}
	
	public static String getEveryThirdLine(File file) throws IOException {
		final StringBuilder output = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			int counter = -1;
			while ((line = reader.readLine()) != null) {
				output.append(++counter % 3 == 0 
						? ((counter != 0 
								? System.lineSeparator() 
								: "") 
							+ line)
						: "");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			reader.close();
		}
		return output.toString();
	}
}
