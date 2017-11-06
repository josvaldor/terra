package org.josvaldor.prospero.terra.weather;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.josvaldor.prospero.terra.Coordinate;
import org.josvaldor.prospero.terra.Time;
import org.josvaldor.prospero.terra.Weather;

public class Event {
	public Weather weather;
	public List<Coordinate> location = new LinkedList<Coordinate>();
	public List<Time> date = new LinkedList<Time>();
	public Map<String,String> attribute = new HashMap<String,String>();
	
	public Event(Weather weather){
		this.weather = weather;
	}
}
