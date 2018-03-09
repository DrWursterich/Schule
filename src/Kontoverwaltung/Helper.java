package Kontoverwaltung;

import java.util.GregorianCalendar;

class Helper {
	public static String getDate(GregorianCalendar g) {
		return g.get(GregorianCalendar.DAY_OF_MONTH) + "."
				+ getMonth(g) + "." + g.get(GregorianCalendar.YEAR);
	}
	
	private static String getMonth(GregorianCalendar g) {
		switch(g.get(GregorianCalendar.MONTH)) {
		case 0: return "Jan";
		case 1: return "Feb";
		case 2: return "Mar";
		case 3: return "Apr";
		case 4: return "Mai";
		case 5: return "Jun";
		case 6: return "Jul";
		case 7: return "Aug";
		case 8: return "Sep";
		case 9: return "Okt";
		case 10: return "Nov";
		case 11: return "Dez";
		default: return "";
		}
	}
}
