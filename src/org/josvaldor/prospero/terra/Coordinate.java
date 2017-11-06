package org.josvaldor.prospero.terra;

public class Coordinate {
	
	public String label;
	public double latitude;
	public double longitude;
	
	public String toString(){
		return label+" "+this.latitude+" "+this.longitude;
	}

}
