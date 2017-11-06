/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josvaldor.prospero.terra;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Calendar;
import java.util.List;

import org.josvaldor.prospero.terra.energy.Energy;

public class Earth {
	
	public Calendar time;
	public double latitudeMin = -90.0;
	public double latitudeMax = 90.0;
	public double longitudeMin = -180;
	public double longitudeMax = 180;
	public double pressureMin = 0;
	public double pressureMax = 10000;
	public List<Coordinate> coordinateList;
	

	public Earth(Calendar calendar) {
		Energy e = new Energy();
		coordinateList = e.read("2017-10-24%2000:00:00");
	}
	
	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		double radius = 5;
		for(Coordinate c:coordinateList){
			g.drawString(c.label,(int)c.longitude, (int)c.latitude);
			System.out.println(c.longitude+" "+c.latitude+" "+c.label);
			System.out.println((int)c.longitude+" "+(int)c.latitude+" "+c.label);
			g.fillOval((int)c.longitude, (int)c.latitude, (int)radius, (int)radius);
		}
		
	}
}
