package org.josvaldor.prospero.terra.atmosphere.jetstream;

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
import ucar.ma2.ArrayFloat;
import ucar.ma2.ArrayInt;
import ucar.ma2.ArrayShort;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFileWriter;

/*
 * 
 */

public class JetstreamExperiment {
	public static int longitudeCount = 0;
	public static int latitudeCount = 0;
	public static int levelCount = 0;
	public static int timeCount = 0;
	public static int uCount = 0;
	public static int vCount = 0;
	public static String startDate = null;
	public static String fileName = "./data/jetstream/default.nc";
	public static NetcdfFileWriter netCDFFile;

	public static void main(String[] args) {
		JetstreamExperiment j = new JetstreamExperiment();
		j.processFile();
	}

	public void processFile() {
		String filename = "./data/jetstream/netcdf.nc";
		NetcdfFile dataFile = null;
		try {
			dataFile = NetcdfFile.open(filename, null);
			Variable latVar = dataFile.findVariable("latitude");
			Variable lonVar = dataFile.findVariable("longitude");
			Variable level = dataFile.findVariable("level");
			Variable time = dataFile.findVariable("time");
			Variable uVar = dataFile.findVariable("u");
			Variable vVar = dataFile.findVariable("v");
			longitudeCount = (int) lonVar.getSize();
			latitudeCount = (int) latVar.getSize();
			levelCount = (int) level.getSize();
			timeCount = (int) time.getSize();
			uCount = (int) uVar.getSize();
			vCount = (int) uVar.getSize();
			System.out.println(longitudeCount);
			System.out.println(latitudeCount);
			System.out.println(levelCount);
			System.out.println(timeCount);
			System.out.println(uCount);
			System.out.println(vCount);
			ArrayFloat.D1 latArray = (ArrayFloat.D1) latVar.read();
			ArrayFloat.D1 lonArray = (ArrayFloat.D1) lonVar.read();
			ArrayInt.D1 levelArray = (ArrayInt.D1) level.read();
			ArrayInt.D1 timeArray = (ArrayInt.D1) time.read();
			ArrayShort.D4 uArray = (ArrayShort.D4) uVar.read();
			ArrayShort.D4 vArray = (ArrayShort.D4) vVar.read();
			ArrayFloat.D4 speedData = new ArrayFloat.D4(timeCount, levelCount, latitudeCount, longitudeCount);
			float speed = 0;
			float u = 0;
			float v = 0;
			float latitude = 0;
			float longitude = 0;
			int t = 0;
			int lev = 0;
			List<Point> pointList = new LinkedList<Point>();
			Point p = null;
			for (int l = 0; l < timeCount; l++) {
				t = timeArray.get(l);
//				System.out.println(getDate(t));
				for (int k = 0; k < levelCount; k++) {
					lev = levelArray.get(k);
					// System.out.println(lev);
					for (int j = 0; j < latitudeCount; j++) {
						latitude = latArray.get(j);
						for (int i = 0; i < longitudeCount; i++) {
							longitude = lonArray.get(i);
							u = uArray.get(l, k, j, i)/ 1000;
							v = vArray.get(l, k, j, i) / 1000;
							speed = (float) Math.sqrt((Math.pow(u, 2) + Math.pow(v, 2)));
//							System.out.println(speed);
							if (speed >  20) {
								p = new Point();
								p.calendar = this.getCalendar(null,this.getDate(t));
								p.level = k;
								p.latitude = j;
								p.longitude = i;
								p.speed = speed;
								pointList.add(p);
								speedData.set(l, k, j, i, speed);
								// if (latitude > -30 & latitude < 30) {
								// speedData.set(l, k, j, i, (short) 0);
								// }
							}
						}
					}
					for (Point point : pointList) {
//						if(point.speed>15)
							speedData.set(l, point.level, point.latitude, point.longitude, point.speed);
					}
				}
			}

			fileName = (fileName != null) ? fileName : "./data/default.nc";
			try {
				netCDFFile = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf4, fileName);
				// Dimension
				Dimension latitudeDimension = netCDFFile.addDimension(null, "latitude", (int) latArray.getSize());
				Dimension longitudeDimension = netCDFFile.addDimension(null, "longitude", (int) lonArray.getSize());
				Dimension pressureDimension = netCDFFile.addDimension(null, "level", (int) levelArray.getSize());
				Dimension timeDimension = netCDFFile.addUnlimitedDimension("time");
				Dimension speedDimension = netCDFFile.addDimension(null, "speed", (int) uArray.getSize());

				Variable longitudeVar = netCDFFile.addVariable(null, "longitude", DataType.FLOAT, "longitude");
				Variable latitudeVar = netCDFFile.addVariable(null, "latitude", DataType.FLOAT, "latitude");
				Variable pressureVar = netCDFFile.addVariable(null, "level", DataType.INT, "level");
				Variable timeVar = netCDFFile.addVariable(null, "time", DataType.INT, "time");
				Variable speedVar = netCDFFile.addVariable(null, "speed", DataType.FLOAT,
						"time level latitude longitude");

				longitudeVar.addAttribute(new Attribute("standard_name", "longitude"));
				longitudeVar.addAttribute(new Attribute("long_name", "longitude"));
				longitudeVar.addAttribute(new Attribute("units", "degrees_east"));
				longitudeVar.addAttribute(new Attribute("axis", "X"));
				latitudeVar.addAttribute(new Attribute("standard_name", "latitude"));
				latitudeVar.addAttribute(new Attribute("long_name", "latitude"));
				latitudeVar.addAttribute(new Attribute("units", "degrees_north"));
				latitudeVar.addAttribute(new Attribute("axis", "Y"));
				pressureVar.addAttribute(new Attribute("standard_name", "air_pressure"));
				pressureVar.addAttribute(new Attribute("long_name", "pressure"));
				pressureVar.addAttribute(new Attribute("units", "Pa"));
				pressureVar.addAttribute(new Attribute("positive", "down"));
				pressureVar.addAttribute(new Attribute("axis", "Z"));
				timeVar.addAttribute(new Attribute("standard_name", "time"));
				timeVar.addAttribute(new Attribute("calendar", "proleptic_gregorian"));
				timeVar.addAttribute(new Attribute("axis", "T"));
				speedVar.addAttribute(new Attribute("standard_name", "speed"));
				speedVar.addAttribute(new Attribute("long_name", "intensity"));
				speedVar.addAttribute(new Attribute("code", "138"));
				speedVar.addAttribute(new Attribute("table", "128"));
				speedVar.addAttribute(new Attribute("grid_type", "gaussian"));
				System.out.println("create netcdf file");
				netCDFFile.create();
				System.out.println("Writing data");

				// Write data to file
				netCDFFile.write(longitudeVar, lonArray);
				netCDFFile.write(latitudeVar, latArray);
				netCDFFile.write(pressureVar, levelArray);
				netCDFFile.write(timeVar, timeArray);
				netCDFFile.write(speedVar, speedData);
				netCDFFile.close();
			} catch (IOException | InvalidRangeException e) {
				e.printStackTrace();

			} finally {
				System.out.println("finally");
				if (null != netCDFFile) {
					try {
						netCDFFile.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
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
