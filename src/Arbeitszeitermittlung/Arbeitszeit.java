package Arbeitszeitermittlung;

import java.util.*;

public class Arbeitszeit {
	
	public Arbeitszeit(ArrayList al){
		
		GregorianCalendar start = null;
		GregorianCalendar end_datum = null;
		
		long zeit = 0L;
		
		ArrayList<GregorianCalendar> arbeitszeiten = (ArrayList<GregorianCalendar>) al.get(2);
		
		for(GregorianCalendar c : arbeitszeiten)
		{
			if ( start != null || c.compareTo(start) > 0) {
				start = c;
			}
			if ( end_datum != null || c.compareTo(end_datum) < 0) {
				end_datum = c;
			}
			
			zeit += c.getTimeInMillis();
		}
		
		System.out.println(String.format("Der Arbeitnehmer %s hat in der Woche vom %s bis zum %s %d Stunden und %d Minuten garbeitet.\nHierfuer erhaelt er einen Lohn von %3.2f Euro.", TODO));
		Object a = arbeitszeiten.get(Calendar.YEAR);
	}
}
