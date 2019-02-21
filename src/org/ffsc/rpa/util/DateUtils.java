package org.ffsc.rpa.util;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.ffsc.rpa.types.Mes;

public class DateUtils {

	public static Locale locale = new Locale("pt", "BR");	
	
	public static int getCurrentYear() {
		GregorianCalendar gc = new GregorianCalendar();

		return gc.get(GregorianCalendar.YEAR);
	}

	public static String getYear(String date) {
		String year = date.substring(0, 4);

		return year;
	}

	public static int getCurrentMonth() {
		GregorianCalendar gc = new GregorianCalendar();

		return gc.get(GregorianCalendar.MONTH) + 1;
	}

	public static String getCurrentMonthName() {
		GregorianCalendar gc = new GregorianCalendar();

		Mes month = Mes.getByIndex(gc.get(GregorianCalendar.MONTH));

		return month != null ? month.getLabel() : "";
	}

	public static String getMonthName(String date) {
		int monthInt = Integer.parseInt(date.substring(5, 7));

		Mes month = Mes.getByIndex(monthInt);

		return month != null ? month.getLabel() : "";
	}

	public static String getTimeString() {
		
		SimpleDateFormat spf = new SimpleDateFormat("HH:mm:ss", locale);

		GregorianCalendar gc = new GregorianCalendar();

		String time = "00:00:00";

		time = spf.format(gc.getTime());

		return time;
	}
}