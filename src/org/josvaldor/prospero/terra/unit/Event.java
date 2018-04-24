package org.josvaldor.prospero.terra.unit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Event {
	public Type type;
	public List<Coordinate> coordinate = new LinkedList<Coordinate>();
	public List<Time> time = new LinkedList<Time>();
	public Map<String,String> attribute = new HashMap<String,String>();
	
	public Event(Type type){
		this.type = type;
	}
	
	public String toString(){
		String intensity = "0";
		if(type == Type.TORNADO){
			intensity=attribute.get("Fujita");
		}else if(type==Type.EARTHQUAKE){
			intensity=attribute.get("INTENSITY");
			intensity=(intensity.equals("")?"0":intensity);
		}
		return type.name()+" "+time.get(0)+" "+intensity;
	}
}
