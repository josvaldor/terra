package org.josvaldor.prospero.terra.unit;

public class Coordinate {
	
	public String label;
	public double latitude;
	public double longitude;
	public double elevation;
	
	public String toString(){
		return label+" "+this.latitude+" "+this.longitude+" "+elevation;
	}
}
