/*
 * Author Joaquin Osvado Rodriguez
 * Date 201710-201711
 * Copyright 2017 Joaquin Osvaldo Rodriguez All Rights Reserved. 
 */
package org.josvaldor.prospero.energy.system;

import org.josvaldor.prospero.energy.Coordinate;
import org.josvaldor.prospero.energy.Energy;
import org.josvaldor.prospero.energy.Orbital;
import org.josvaldor.prospero.energy.Space;
import org.josvaldor.prospero.energy.Triangle;
import org.josvaldor.prospero.energy.system.moon.luna.Luna;
import org.josvaldor.prospero.energy.system.planet.earth.Earth;
import org.josvaldor.prospero.energy.system.planet.jupiter.Jupiter;
import org.josvaldor.prospero.energy.system.planet.mars.Mars;
import org.josvaldor.prospero.energy.system.planet.mercury.Mercury;
import org.josvaldor.prospero.energy.system.planet.neptune.Neptune;
import org.josvaldor.prospero.energy.system.planet.saturn.Saturn;
import org.josvaldor.prospero.energy.system.planet.uranus.Uranus;
import org.josvaldor.prospero.energy.system.planet.venus.Venus;
import org.josvaldor.prospero.energy.system.star.sun.Sun;
import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Solar extends Orbital {

	public ArrayList<Energy> energyList;
	private static int defaultRate = Calendar.MONTH;
	
	public Solar(){
		super();
		this.energyList = this.getEnergyList(this.time);
		this.setScale(0.00000005);
	}
	
	public Solar(String time) {
		this.time = this.getCalendar(null, time);
		this.energyList = this.getEnergyList(this.time);
		this.setScale(0.00000005);
	}

	public Solar(Calendar time) {
		this.time = time;
		this.energyList = this.getEnergyList(this.time);
		this.setScale(0.00000005);
	}

	public Solar(String time, List<Triangle> triangleList) {
		this.time = this.getCalendar(null, time);
		this.energyList = this.getEnergyList(triangleList);
		this.setScale(0.00000005);
	}

	public Solar(Calendar time, List<Triangle> triangleList) {
		this.time = time;
		this.energyList = this.getEnergyList(triangleList);
		this.setScale(0.00000005);
	}
	
    public List<Coordinate> getCoordinateList(Energy energy, List<Energy> energyList){
    	List<Coordinate> coordinateList = new LinkedList<Coordinate>();
    	Coordinate coordinate = null;
    	Vector3D prime = null;
    	Vector3D out = null;
    	double alpha;
    	double delta;
    	double obliquity;
    	for(Energy e: energyList){
    		if(!e.name.equals(energy.name)){
    			prime = e.position.subtract(energy.position);
    			obliquity = this.toRadians(((Orbital)energy).obliquity);
    			out = new Vector3D(prime.getX(),
    							  ((prime.getY()*Math.cos(obliquity))-(prime.getZ()*Math.sin(obliquity))),
    							  ((prime.getY()*Math.sin(obliquity))+(prime.getZ()*Math.cos(obliquity))));
    			alpha = Math.atan(out.getY()/out.getX());
    			delta = Math.atan(out.getZ()/Math.sqrt(out.getX()*out.getX()+out.getY()*out.getY())); 
    			if(out.getX()<0){
    				alpha=alpha+Math.PI;
    			}
    			if(out.getX()>0 && out.getY()<0){
    				alpha=alpha+(2*Math.PI);
    			}
    			alpha=this.toDegrees(alpha);
    			delta=this.toDegrees(delta);
    			double angle = ((Earth)energy).getRotationCorrection(energy.time);
    			alpha=alpha+angle;
    			while(alpha>180){
    				alpha=alpha-360;
    			}
    			while(delta>90){
    				delta-=180;
    			}
    			coordinate = new Coordinate();
    			coordinate.latitude = delta;
    			coordinate.longitude = alpha;
    			coordinate.label = e.name;
    			coordinateList.add(coordinate);
    		}
    	}
    	return coordinateList;
    }
    
    public ArrayList<Energy> getEnergyList(String time){
    	return this.getEnergyList(this.getCalendar("yyyy-MM-dd", time));
    }

	public ArrayList<Energy> getEnergyList(Calendar time) {
		Sun sun = new Sun(time);
		Earth earth = new Earth(time,sun);
		Jupiter jupiter = new Jupiter(time,sun);
		Mars mars = new Mars(time,sun);
		Mercury mercury = new Mercury(time,sun);
		Neptune neptune = new Neptune(time,sun);
		Saturn saturn = new Saturn(time,sun);
		Venus venus = new Venus(time,sun);
		Uranus uranus = new Uranus(time,sun);
		Luna luna = new Luna(time,earth);
		ArrayList<Energy> energyList = new ArrayList<Energy>();
		energyList.add(earth);
		energyList.add(jupiter);
		energyList.add(mars);
		energyList.add(mercury);
		energyList.add(neptune);
		energyList.add(saturn);
		energyList.add(venus);
		energyList.add(uranus);
		energyList.add(sun);
//		energyList.add(luna);
		
		return energyList;
	}

	public ArrayList<Energy> getEnergyList(List<Triangle> triangleList) {
		ArrayList<Energy> energyList = new ArrayList<Energy>();
		for (Triangle t : triangleList) {
			if (!energyList.contains(t.i)) {
				energyList.add(t.i);
			}
			if (!energyList.contains(t.j)) {
				energyList.add(t.j);
			}
			if (!energyList.contains(t.k)) {
				energyList.add(t.k);
			}
		}
		return energyList;
	}

	public void setEnergyList(ArrayList<Energy> energyList) {
		this.energyList = energyList;
	}

 	public Map<String, List<Space>> searchSpace(String startDate, String endDate, double threshold, double match)
			throws ParseException {
		Map<String, List<Space>> timeMap = new HashMap<String, List<Space>>();
		Date start = this.getDate(null, startDate);
		Date end = this.getDate(null, endDate);
		GregorianCalendar timeline = this.getCalendar(start);
		List<Space> spaceList = null;
		while (!timeline.getTime().after(end)) {
			String timelineDate = this.getCalendarString(null, timeline);
			spaceList = this.searchSpace(timelineDate, startDate, endDate, threshold, match);
			timeMap.put(timelineDate, spaceList);
			timeline.add(defaultRate, 1);
		}
		return timeMap;
	}

	public List<Space> searchSpace(String indexDate, String startDate, String endDate, double threshold, double match) {
		GregorianCalendar array = this.getCalendar(null, startDate);
		GregorianCalendar index = this.getCalendar(null, indexDate);
		Date end = this.getDate(null, endDate);
		List<Space> spaceList = new LinkedList<Space>();
		while (!array.getTime().after(end)) {
			Space space = this.compareSpace(index, array, threshold);
			if (space.match >= match)
				spaceList.add(space);
			array.add(defaultRate, 1);
		}
		return spaceList;
	}

	public List<Triangle> getTriangleList(List<Energy> energyList) {
		List<Triangle> triangleList = new LinkedList<Triangle>();
		Sun sun = new Sun();
		Triangle triangle = null;
		List<Energy> energyStack = new LinkedList<Energy>();
		for (Energy a : energyList) {
			if (!(a instanceof Sun)) {
				for (Energy b : energyList) {
					if (!(b instanceof Sun) && a != b && !energyStack.contains(b)) {
						triangle = new Triangle(sun, a, b);
						triangleList.add(triangle);
					}
				}
				energyStack.add(a);
			}
		}
		return triangleList;
	}

	public List<Triangle> matchTriangleList(List<Triangle> aList, List<Triangle> bList, double threshold) {
		List<Triangle> triangleList = new LinkedList<Triangle>();
		for (Triangle a : aList) {
			for (Triangle b : bList) {
				if (a.j.name.equals(b.j.name) && a.k.name.equals(b.k.name)) {
					if (b.thresholdA(a.A, threshold)) {
						triangleList.add(a);
					}
				}
			}
		}
		return triangleList;
	}

	public Space compareSpace(Calendar time, double threshold) {
		List<Energy> energyList = this.getEnergyList(time);
		List<Triangle> triangleListA = this.getTriangleList(energyList);
		List<Triangle> triangleListB = this.getTriangleList(this.energyList);
		return this.compareSpace(triangleListA, triangleListB, threshold);
	}

	public Space compareSpace(Calendar timeA, Calendar timeB, double threshold) {
		List<Triangle> triangleListA = this.getTriangleList(this.getEnergyList(timeA));
		List<Triangle> triangleListB = this.getTriangleList(this.getEnergyList(timeB));
		return this.compareSpace(triangleListA, triangleListB, threshold);
	}

	public Space compareSpace(List<Triangle> aList, List<Triangle> bList, double threshold) {
		List<Triangle> triangleList = this.matchTriangleList(aList, bList, threshold);
		double total = aList.size();
		double count = triangleList.size();
		Space space = new Space();
		space.triangleList = triangleList;
		space.match = count / total;
		return space;
	}

	public double getKineticEnergy() {
		double kineticEnergy = 0;
		for (Energy e : this.energyList) {
			if(e instanceof Orbital)
				kineticEnergy += 0.5 * e.mass * Math.pow(((Orbital)e).angularVelocity, 2) * Math.pow(((Orbital)e).radius, 2);
		}
		return kineticEnergy;
	}
	
	public Vector3D getCenterOfMass(){
		double totalMass=0;
		double totalX=0;
		double totalY=0;
		double totalZ=0;
		for (Energy e : this.energyList) {
			if(!(e instanceof Sun)){
			totalMass+=e.mass;
			totalX+=e.position.getX()*e.mass;
			totalY+=e.position.getY()*e.mass;
			totalZ+=e.position.getZ()*e.mass;
			}
		}
		return new Vector3D(totalX/totalMass,totalY/totalMass,totalZ/totalMass);
	}
	
	public Vector3D getCenterOfMass(Energy e1, Energy e2){
		double totalMass=0;
		double totalX=0;
		double totalY=0;
		double totalZ=0;
		List<Energy> eList = new LinkedList<Energy>();
		eList.add(e1);
		eList.add(e2);
		for (Energy e : eList) {
			if(!(e instanceof Sun)){
			totalMass+=e.mass;
			totalX+=e.position.getX()*e.mass;
			totalY+=e.position.getY()*e.mass;
			totalZ+=e.position.getZ()*e.mass;
			}
		}
		return new Vector3D(totalX/totalMass,totalY/totalMass,totalZ/totalMass);
	}
	
	public Vector3D getCenterOfMass(Energy e1, Energy e2, List<Energy> list){
		double totalMass=0;
		double totalX=0;
		double totalY=0;
		double totalZ=0;
		List<Energy> eList = new LinkedList<Energy>();
		eList.add(e1);
		eList.add(e2);
		eList.addAll(list);
		for (Energy e : eList) {
			if(!(e instanceof Sun)){
			totalMass+=e.mass;
			totalX+=e.position.getX()*e.mass;
			totalY+=e.position.getY()*e.mass;
			totalZ+=e.position.getZ()*e.mass;
			}
		}
		return new Vector3D(totalX/totalMass,totalY/totalMass,totalZ/totalMass);
	}
	
	public Vector3D getCenterOfMass(List<Energy> eList){
		double totalMass=0;
		double totalX=0;
		double totalY=0;
		double totalZ=0;
		for (Energy e : eList) {
			if(!(e instanceof Sun)){
			totalMass+=e.mass;
			totalX+=e.position.getX()*e.mass;
			totalY+=e.position.getY()*e.mass;
			totalZ+=e.position.getZ()*e.mass;
			}
		}
		return new Vector3D(totalX/totalMass,totalY/totalMass,totalZ/totalMass);
	}

	public Calendar getTime() {
		return this.time;
	}

	public void setTime(String time) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = formatter.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			this.setTime(calendar);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public void setTime(Calendar time) {
		this.time = time;
		for (Energy energy : energyList) {
			energy.setTime(time);
		}
	}

	public int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public double calculateMean(List<Double> doubleList) {
		double sum = 0;
		for (Double d : doubleList) {
			sum += d;
		}
		return sum / doubleList.size();
	}

	public double calculateSum(List<Double> doubleList) {
		double sum = 0;
		for (Double d : doubleList) {
			sum += d;
		}
		return sum;
	}

	public void setScale(double scale) {
		this.scale = scale;
		for (Energy e : this.energyList) {
			e.scale = scale;
		}
	}

	public void getGravity() {
		for (Energy energy : energyList) {
			for (Energy e : energyList) {
				if (energy != e) {
					if (energy.mass != 1 && e.mass != 1) {
						Vector3D force = e.getGravity(energy);
						energy.force = energy.force.subtract(force);
						e.force = e.force.add(force);
					}
				}
			}
		}

	}

	public void drawDate(Graphics2D g, Calendar c) {
		g.setColor(Color.WHITE);
		g.drawString(this.getCalendarString(null, c) + "", -100, -150);
	}

	public void draw(Graphics2D g) {
		if (this.time != null) {
			drawDate(g, this.time);
		}
//		Map<String,Energy> map = this.getEnergyMap(this.energyList);
//		
//		List<Energy> eList = new LinkedList<Energy>();
//		eList.add(map.get("earth"));
//		eList.add(map.get("venus"));
//		eList.add(map.get("mercury"));
//		eList.add(map.get("mars"));
		
		Vector3D center = this.getCenterOfMass();
//		System.out.println("center: "+center);
		double x = center.getX() * scale;
		double y = center.getY() * scale;
		g.setColor(Color.white);
		int radius = 10;
		x = x - (radius / 2);
		y = y - (radius / 2);
		g.fillOval((int) x, (int) y, (int) radius, (int) radius);
		
//		center = this.getCenterOfMass(map.get("jupiter"),map.get("uranus"),eList);
//		System.out.println("jupiter uranus center: "+center);
//		x = center.getX() * scale;
//		y = center.getY() * scale;
//		g.setColor(Color.green);
//		radius = 10;
//		x = x - (radius / 2);
//		y = y - (radius / 2);
//		g.fillOval((int) x, (int) y, (int) radius, (int) radius);
//		
//		center = this.getCenterOfMass(map.get("jupiter"),map.get("neptune"),eList);
//		System.out.println("jupiter uranus center: "+center);
//		x = center.getX() * scale;
//		y = center.getY() * scale;
//		g.setColor(Color.blue);
//		radius = 10;
//		x = x - (radius / 2);
//		y = y - (radius / 2);
//		g.fillOval((int) x, (int) y, (int) radius, (int) radius);
//		
//		
//		center = this.getCenterOfMass(this.energyList);
//		System.out.println("center: "+center);
//		x = center.getX() * scale;
//		y = center.getY() * scale;
//		g.setColor(Color.white);
//		radius = 10;
//		x = x - (radius / 2);
//		y = y - (radius / 2);
//		g.fillOval((int) x, (int) y, (int) radius, (int) radius);
////		
		
		for (Energy e : this.energyList) {
			if (e instanceof Earth) {
				Earth earth = (Earth) e;
				earth.draw(g);
			} else if (e instanceof Luna) {
				Luna luna = (Luna) e;
				luna.draw(g);
			} else if (e instanceof Jupiter) {
				Jupiter jupiter = (Jupiter) e;
				jupiter.draw(g);
			} else if (e instanceof Venus) {
				Venus venus = (Venus) e;
				venus.draw(g);
			} else if (e instanceof Mars) {
				Mars mars = (Mars) e;
				mars.draw(g);
			} else if (e instanceof Mercury) {
				Mercury mercury = (Mercury) e;
				mercury.draw(g);
			} else if (e instanceof Neptune) {
				Neptune neptune = (Neptune) e;
				neptune.draw(g);
			} else if (e instanceof Saturn) {
				Saturn saturn = (Saturn) e;
				saturn.draw(g);
			} else if (e instanceof Uranus) {
				Uranus uranus = (Uranus) e;
				uranus.draw(g);
			} else if (e instanceof Sun) {
				Sun sun = (Sun) e;
				sun.draw(g);
			}
		}
	}
	
	public Map<String,Energy> getEnergyMap(List<Energy> energyList){
		Map<String,Energy> map = new HashMap<String,Energy>();
		for (Energy e : energyList) {
			if (e instanceof Earth) {
				Earth earth = (Earth) e;
				map.put("earth", earth);
			} else if (e instanceof Luna) {
				Luna luna = (Luna) e;
				map.put(luna.name, luna);
			} else if (e instanceof Jupiter) {
				Jupiter jupiter = (Jupiter) e;
				map.put(jupiter.name, jupiter);
			} else if (e instanceof Venus) {
				Venus venus = (Venus) e;
				map.put(venus.name,venus );
			} else if (e instanceof Mars) {
				Mars mars = (Mars) e;
				map.put(mars.name,mars );
			} else if (e instanceof Mercury) {
				Mercury mercury = (Mercury) e;
				map.put(mercury.name,mercury );
			} else if (e instanceof Neptune) {
				Neptune neptune = (Neptune) e;
				map.put(neptune.name,neptune );
			} else if (e instanceof Saturn) {
				Saturn saturn = (Saturn) e;
				map.put(saturn.name,saturn );
			} else if (e instanceof Uranus) {
				Uranus uranus = (Uranus) e;
				map.put(uranus.name,uranus );
			} else if (e instanceof Sun) {
				Sun sun = (Sun) e;
				map.put(sun.name, sun);
			}
		}
		return map;
	}
}