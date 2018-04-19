package org.josvaldor.prospero.terra.lithosphere;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import ucar.nc2.Variable;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayFloat;
import ucar.ma2.ArrayInt;
import ucar.ma2.ArrayShort;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFileWriter;

/*
 * 
 */

public class Oceanic {
	public static int longitudeCount = 0;
	public static int latitudeCount = 0;
	public static int elevationCount = 0;
	public static int timeCount = 0;
	public static int uCount = 0;
	public static int vCount = 0;
	public static String startDate = null;
	public static String fileName = "./data/elevation/RN-8098_1510354754870/GEBCO_2014_2D.nc";
	public static NetcdfFileWriter netCDFFile;

	public static void main(String[] args) {
		Oceanic j = new Oceanic();
		j.processFile();
	}

	public void processFile() {
		NetcdfFile dataFile = null;
		try {
			dataFile = NetcdfFile.open(fileName, null);
			Variable latVar = dataFile.findVariable("lat");
			Variable lonVar = dataFile.findVariable("lon");
			Variable elevation = dataFile.findVariable("elevation");
			longitudeCount = (int) lonVar.getSize();
			latitudeCount = (int) latVar.getSize();
			elevationCount = (int) elevation.getSize();
			System.out.println(longitudeCount);
			System.out.println(latitudeCount);
			System.out.println(elevationCount);
			ArrayDouble.D1 latArray = (ArrayDouble.D1) latVar.read();
			ArrayDouble.D1 lonArray = (ArrayDouble.D1) lonVar.read();
			ArrayShort.D2 elevationArray = (ArrayShort.D2) elevation.read();
			double latitude = 0;
			double longitude = 0;
			short ele = 0;
			for (int j = 0; j < latitudeCount; j++) {
				latitude = latArray.get(j);
				for (int i = 0; i < longitudeCount; i++) {
					longitude = lonArray.get(i);
					ele = elevationArray.get(i, j);
					System.out.println("lat:"+latitude+" lon:"+longitude+" elevation:"+ele);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();

		} finally {
			if (dataFile != null) {
				try {
					dataFile.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

	}

	public String defaultTimeFormat = "yyyy-MM-dd HH:mm:ss";

	public String getDate(int time) {
		GregorianCalendar g = new GregorianCalendar(1900, 0, 1, -1, 0, 0);
		g.add(Calendar.HOUR, time); // adds one hour
		g.set(Calendar.MINUTE, 0);
		g.set(Calendar.SECOND, 0);
		g.set(Calendar.MILLISECOND, 0);
		return this.getCalendarString(null, g);
	}

	public String getDateString(String format, Date date) {
		String string = new SimpleDateFormat((format == null) ? defaultTimeFormat : format).format(date);
		return string;
	}

	public String getCalendarString(String format, Calendar calendar) {
		return this.getDateString(format, calendar.getTime());
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
}
