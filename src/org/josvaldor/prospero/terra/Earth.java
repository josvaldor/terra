/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josvaldor.prospero.terra;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.josvaldor.prospero.terra.cultural.Country;
import org.josvaldor.prospero.terra.energy.Energy;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.MultiPolygon;

public class Earth {
	
	public Calendar time;
	public double latitudeMin = -90.0;
	public double latitudeMax = 90.0;
	public double longitudeMin = -180;
	public double longitudeMax = 180;
	public double pressureMin = 0;
	public double pressureMax = 10000;
	public List<Coordinate> coordinateList;
	public Country c;
	public double scale;
	Energy e = new Energy();
	MultiPolygon f;
	
	private static String defaultTimeFormat = "yyyy-MM-dd HH:mm:ss";
	

	public Earth(Calendar calendar) {
		this.time = calendar;
		c = new Country();
		f = c.point(-75.17194183200792, 40.001919022526465);
		
		this.setScale(3.0);
	}
	
	public void setScale(double scale){
		this.scale = scale;
	}
	
	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}
	
	public void draw(Graphics2D g){
		coordinateList = e.read(this.getCalendarString(null, this.time).replaceAll(" ", "%20"));
		g.setColor(Color.WHITE);
		double radius = 5;
		if(this.coordinateList != null){
		for(Coordinate c:coordinateList){
			g.drawString(this.getCalendarString(null, this.time),(int)(0*scale), (int)(180*scale));
			c.latitude = c.latitude*scale;
			c.longitude=c.longitude*scale;
			g.drawString(c.label,(int)c.longitude, (int)c.latitude);
			System.out.println(c.longitude+" "+c.latitude+" "+c.label);
			System.out.println((int)c.longitude+" "+(int)c.latitude+" "+c.label);
			g.fillOval((int)c.longitude, (int)c.latitude, (int)radius, (int)radius);
		}
		}
		
		com.vividsolutions.jts.geom.Coordinate[] a = f.getCoordinates();
		for(int i=0; i<a.length;i++){
			g.fillOval((int)(a[i].x*scale), -(int)(a[i].y*scale), (int)2, (int)2);
		}
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

	public String getDateString(String format, Date date) {
		String string = new SimpleDateFormat((format == null) ? defaultTimeFormat : format).format(date);
		return string;
	}

	public String getCalendarString(String format, Calendar calendar) {
		return this.getDateString(format, calendar.getTime());
	}
}
