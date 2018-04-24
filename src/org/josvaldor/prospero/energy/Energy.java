package org.josvaldor.prospero.energy;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Energy {

	public String name;
	public double mass;
	public Calendar time;
	public Vector3D position = new Vector3D(0, 0, 0);
	public Vector3D force = new Vector3D(0, 0, 0);
	public double scale;
	protected Color color;
	private static String defaultTimeFormat = "yyyy-MM-dd HH:mm:ss";
	
	public Energy() {
		this.time = new GregorianCalendar();
	}
	
	public Vector3D getGravity(Energy energy) {
		Vector3D distance = energy.position.subtract(this.position);
		double magnitude = Math.sqrt(Math.pow((double) distance.getX(), 2) + Math.pow((double) distance.getY(), 2)
				+ Math.pow((double) distance.getZ(), 2));
		double gravityForce = (Unit.GRAVITATIONAL_CONSTANT * energy.mass * this.mass) / (Math.pow(magnitude, 2));
		Vector3D force = null;
		if (magnitude != 0) {
			force = distance.normalize();
			force.scalarMultiply(gravityForce);
		}
		return force;
	}

	public void scale(double percentage) {
		this.position = this.position.scalarMultiply(percentage);
	}
	
	public void setTime(String time){
		this.setTime(this.getCalendar(null, time));
	}

	public void setTime(Calendar time){
		this.time = time;
	}

	public void setPosition(Vector3D position) {
		this.position = position;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
