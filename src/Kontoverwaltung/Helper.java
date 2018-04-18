package Kontoverwaltung;

import java.util.GregorianCalendar;
import java.util.Locale;

class Helper {
	public static String getDate(GregorianCalendar g) {
		return String.format("%02d.%3s.% 4d", g.get(GregorianCalendar.DAY_OF_MONTH),
				g.getDisplayName(GregorianCalendar.MONTH,
				GregorianCalendar.SHORT, Locale.US),
				g.get(GregorianCalendar.YEAR));
	}

	public static String increaseString(String s) throws NumberFormatException {
		return String.format("%0" + s.length() + "d", Integer.parseInt(s)+1);
	}
}
