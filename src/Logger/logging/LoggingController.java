package Logger.logging;

import static sun.util.logging.PlatformLogger.Level.OFF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Static Controller-Class for Logging.<br/>
 * All Logging should be done with this Class.
 * @author Mario Schäper
 */
public class LoggingController {
	private static final String ENCODING = "UTF-8";
	private static final String PATH = "logs";
	private static final String LOG_NAME_PATTERN = "YYYY-MM-dd";
	private static final File FOLDER = new File(PATH);
	private static final String CLASS_NAME = LoggingController.class.getPackage().getName() +
			"." + LoggingController.class.getSimpleName();
	private static OutputStream OUT;
	private static Handler CONSOLE_HANDLER;
	private static Handler FILE_HANDLER;
	private static Formatter CONSOLE_FORMATTER = new ConsoleFormatter();
	private static Formatter FILE_FORMATTER = new FileFormatter();
	private static Filter FILE_FILTER = new Filter();
	private static Filter CONSOLE_FILTER = new Filter();
	private static Logger GLOBAL_LOGGER = Logger.getLogger("");

	static {
		try {
			com.sun.javafx.util.Logging.getAccessibilityLogger().setLevel(OFF);
			com.sun.javafx.util.Logging.getCSSLogger().setLevel(OFF);
			com.sun.javafx.util.Logging.getFocusLogger().setLevel(OFF);
			com.sun.javafx.util.Logging.getInputLogger().setLevel(OFF);
			com.sun.javafx.util.Logging.getJavaFXLogger().setLevel(OFF);
			com.sun.javafx.util.Logging.getLayoutLogger().setLevel(OFF);
		} catch (Exception e) {
			LoggingController.log(Level.INFO, "FX-Logger was not disabled: " + e.getMessage());
		}
		try {
			GLOBAL_LOGGER.setLevel(Level.FINE);
			Handler[] handlers = GLOBAL_LOGGER.getHandlers();
			for (int i=handlers.length-1;i>=0;i--) {
				GLOBAL_LOGGER.removeHandler(handlers[i]);
			}
			CONSOLE_HANDLER = new ConsoleHandler();
			CONSOLE_HANDLER.setFormatter(CONSOLE_FORMATTER);
			CONSOLE_HANDLER.setFilter(CONSOLE_FILTER);
			CONSOLE_HANDLER.setEncoding(ENCODING);
			GLOBAL_LOGGER.addHandler(CONSOLE_HANDLER);
			OUT = LoggingController.getOut();
			if (OUT != null) {
				FILE_HANDLER = new FileHandler(OUT);
				FILE_HANDLER.setFormatter(FILE_FORMATTER);
				FILE_HANDLER.setFilter(FILE_FILTER);
				FILE_HANDLER.setEncoding(ENCODING);
				FILE_HANDLER.setLevel(GLOBAL_LOGGER.getLevel());
				GLOBAL_LOGGER.addHandler(FILE_HANDLER);
			}
		} catch (Exception e) {
			LoggingController.log(Level.SEVERE, "Unable to initiate Handlers: " + e.getMessage());
		}
	}

	/**
	 * Initializes and returns the {@link java.io.OutputStream OutputStream}
	 * for the {@link FileHandler FileHandler}.
	 * @return OutputStream for the FileHandler
	 * @throws IOException if an IO-Error occours, that is not cought.
	 */
	private static OutputStream getOut() throws IOException {
		if (!FOLDER.exists()) {
			FOLDER.mkdir();
		}
		if (!FOLDER.isDirectory()) {
			LoggingController.log(Level.SEVERE, "Logging directory is a file.");
			System.exit(1);
		}
		String fileName = (new SimpleDateFormat(LOG_NAME_PATTERN)).format(new Date());
		File file = new File(PATH + "/" + fileName + ".log");
		if (file.exists() && !file.isFile()) {
			LoggingController.log(Level.SEVERE, "Logging file is a directory.");
			System.exit(0);
		}
		return new FileOutputStream(file, true);
	}

	/**
	 * Logs a message with the given {@Link java.util.logging.Level Level}.
	 * @param level the level to log with
	 * @param message the message to be logged
	 */
	public static void log(Level level, String message) {
		StackTraceElement st = LoggingController.getStackTrace();
		Logger.getLogger(st.getClassName()).log(
				level, st.getClassName() + "." + st.getMethodName() + ": " + message);
	}

	/**
	 * Returns the {@link java.lang.StackTraceElement StackTraceElement} of the calling instance.
	 * @return the StackTraceElement of the calling instance
	 */
	private static StackTraceElement getStackTrace() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement ret = stackTrace[stackTrace.length - 1];
		for (int i = stackTrace.length - 1; i >= 0
				&& !(stackTrace[i].getClassName().equals(LoggingController.CLASS_NAME)
						&& stackTrace[i].getMethodName().equals("log")); i--) {
			ret = stackTrace[i];
		}
		return ret;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		OUT.close();
		FILE_HANDLER.close();
		CONSOLE_HANDLER.close();
	}
}
