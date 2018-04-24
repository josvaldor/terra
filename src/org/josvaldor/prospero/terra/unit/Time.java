package org.josvaldor.prospero.terra.unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Time {
	public static String defaultTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public Calendar time;

	public Time(String time) {
		this.time = this.getCalendar(null, time);
	}
	
	public Time(Calendar calendar){
		this.time = calendar;
	}

	public GregorianCalendar getCalendar(String format, String time) {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = this.getDate(format, time);
		if (date != null)
			calendar.setTime(date);
		return calendar;
	}

	public GregorianCalendar getCalendar(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	public Date getDate(String format, String time) {
		SimpleDateFormat sdf = new SimpleDateFormat((format == null) ? defaultTimeFormat : format);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getDateString(String format, Date date) {
		String string = new SimpleDateFormat((format == null) ? defaultTimeFormat : format).format(date);
		return string;
	}

	public static String getCalendarString(String format, Calendar calendar) {
		return getDateString(format, calendar.getTime());
	}
	
	public String toString(){
		return this.getCalendarString(null, this.time);
	}
}
