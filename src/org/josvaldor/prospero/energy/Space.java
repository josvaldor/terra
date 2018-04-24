package org.josvaldor.prospero.energy;

import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Space {

	public String time;
	public double match;
	public List<Triangle> triangleList;
	public Vector3D eliptic;
	public Vector3D spherical;
	
	public String toString(){
		return time+" "+match+" "+triangleList;
	}
 }
