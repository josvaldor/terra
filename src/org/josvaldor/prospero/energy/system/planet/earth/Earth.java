package org.josvaldor.prospero.energy.system.planet.earth;

import org.josvaldor.prospero.energy.Orbital;
import org.josvaldor.prospero.energy.Unit;
import org.josvaldor.prospero.energy.system.planet.Planet;
import org.josvaldor.prospero.terra.Terra;
import org.josvaldor.prospero.terra.unit.Event;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author jorodriguez
 */
public class Earth extends Planet {
	Terra terra;

	public Earth(Calendar calendar, Orbital centroid) {
		super();
		this.centroid = centroid;
		this.name = "earth";
		this.mass = 5.972e24;
		this.radius = 3443.9307;
		this.color = Color.BLUE;
		this.longitudeOfAscendingNode[0] = 0;
		this.longitudeOfAscendingNode[1] = 0;
		this.inclination[0] = 0;
		this.inclination[1] = 0;
		this.argumentOfPeriapsis[0] = -282.9404;
		this.argumentOfPeriapsis[1] = -4.70935E-5;
		this.semiMajorAxis[0] = 1.000000 * Unit.ASTRONOMICAL;
		this.semiMajorAxis[1] = 0;
		this.eccentricity[0] = -0.016709;
		this.eccentricity[1] = 1.151E-9;
		this.meanAnomaly[0] = 356.0470;
		this.meanAnomaly[1] = 0.9856002585;
		this.orbitalPeriod = 365.256363004;
		this.time = calendar;
		this.position = this.getSpace(this.time).eliptic;
		this.angularVelocity = 7.292115053925690e-05;
		this.obliquity = 23.439292;
		this.rotation = 23.9345;// hour
		this.terra = new Terra(this.time);
	}
	
	@Override
	public void setTime(Calendar time){
		super.setTime(time);
		this.terra.setTime(this.time);
		List<Event> eventList = this.terra.eventList;
		List<Event> timeList = this.terra.getTimeEventList(eventList, time);
		for(Event e: timeList){
			System.out.println(e);
		}
	}
}
