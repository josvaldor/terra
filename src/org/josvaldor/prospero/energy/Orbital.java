package org.josvaldor.prospero.energy;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Orbital extends Spheroid{

	public Calendar startTime = (new GregorianCalendar(2000,0,1,0,0,0));
	public double orbitalPeriod;
	public double obliquity;
    public double angularVelocity;
	public double[] semiMajorAxis = new double[3];// a
	public double[] eccentricity = new double[3];// e
	public double[] inclination = new double[3];// i
	public double[] meanLongitude = new double[3];// l
	public double[] longitudeOfPeriapsis = new double[3];
	public double[] longitudeOfAscendingNode = new double[3];// N
	public double[] argumentOfPeriapsis = new double[3];
	public double[] meanAnomaly = new double[3];
	public double rotation;
	public Orbital centroid = this;
	
	@Override
	public void setTime(Calendar time){
		super.setTime(time);
		this.position = this.getSpace(time).eliptic;
	}
	
	public Space getSpace(Calendar c) {
		double T = this.dateToJulian(this.startTime);
		double t = this.dateToJulian(c);
		double d = t - T;
		double N = this.longitudeOfAscendingNode[0] + this.longitudeOfAscendingNode[1] * d;
		double i = this.inclination[0] + this.inclination[1] * d;
		double w = this.argumentOfPeriapsis[0] + this.argumentOfPeriapsis[1] * d;
		double a = this.semiMajorAxis[0]+this.semiMajorAxis[1]*d;
		double e = this.eccentricity[0] + this.eccentricity[1] * d;
		double M = this.meanAnomaly[0] + this.meanAnomaly[1] * d;
		N = this.rev(N);
		i = this.rev(i);
		w = this.rev(w);
		M = this.rev(M);
//		E = M + e*(180/pi) * sin(M) * ( 1.0 + e * cos(M) )
//		M = toRadians(M);
//		double E = M + e * Math.sin(M) * (1.0 + e * Math.cos(M));
		double E = M + (180 / Math.PI) * e * Math.sin(toRadians(M)) * (1.0 + e * Math.cos(toRadians(M)));
		double error = 1;
		while (error > 0.005) {
			double E1 = E - (E - (180 / Math.PI) * e * Math.sin(toRadians(E)) - M) / (1 - e * Math.cos(toRadians(E)));
			error = Math.abs(E - E1);
			E = E1;
		}
//		xv = r * cos(v) = cos(E) - e
//		yv = r * sin(v) = sqrt(1.0 - e*e) * sin(E)
		double x = a * (Math.cos(toRadians(E)) - e);
		double y = a * (Math.sqrt(1.0 - e * e) * Math.sin(toRadians(E)));
		double r = Math.sqrt(x * x + y * y);
		double v = toDegrees(Math.atan2(y, x));
		double n_rad = toRadians(N);
		double vw_rad = toRadians(v + w);
		double i_rad = toRadians(i);
		// Now we know the Moon's position in the plane of the lunar orbit. To
		// compute the Moon's position in ecliptic coordinates, we apply these
		// formulae:
//		The Position in space
//		xh = r * ( cos(N) * cos(v+w) - sin(N) * sin(v+w) * cos(i) )
//		yh = r * ( sin(N) * cos(v+w) + cos(N) * sin(v+w) * cos(i) )
//		zh = r * ( sin(v+w) * sin(i) )
		double xeclip = r * (Math.cos(n_rad) * Math.cos(vw_rad) - Math.sin(n_rad) * Math.sin(vw_rad) * Math.cos(i_rad));
		double yeclip = r * (Math.sin(n_rad) * Math.cos(vw_rad) + Math.cos(n_rad) * Math.sin(vw_rad) * Math.cos(i_rad));
		double zeclip = r * Math.sin(vw_rad) * Math.sin(i_rad);
		Space space = new Space();
		Vector3D eliptic = new Vector3D(xeclip, yeclip, zeclip);
		if(this.centroid != null){
			space.eliptic = this.centroid.position.add(eliptic);
		}
		double RA = toDegrees(Math.atan2(yeclip, xeclip));
		double Decl = toDegrees(Math.asin(zeclip / r));
		Vector3D spherical = new Vector3D(r,RA,Decl);
		space.spherical = spherical;
		space.time = this.getCalendarString(null, c);
		return space;
	}
	
//	public Space getSpace(Calendar c) {
//		double T = this.dateToJulian(this.startTime);
//		double t = this.dateToJulian(c);
//		double d = t - T;
//		double N = this.longitudeOfAscendingNode[0] + this.longitudeOfAscendingNode[1] * d;
//		double i = this.inclination[0] + this.inclination[1] * d;
//		double w = this.argumentOfPeriapsis[0] + this.argumentOfPeriapsis[1] * d;
//		double a = this.semiMajorAxis[0]+this.semiMajorAxis[1]*d;
//		double e = this.eccentricity[0] + this.eccentricity[1] * d;
//		double M = this.meanAnomaly[0] + this.meanAnomaly[1] * d;
//		N = this.rev(N);
//		i = this.rev(i);
//		w = this.rev(w);
//		M = this.rev(M);
//		double E = M + (180 / Math.PI) * e * Math.sin(toRadians(M)) * (1.0 + e * Math.cos(toRadians(M)));
//		double error = 1;
//		while (error > 0.005) {
//			double E1 = E - (E - (180 / Math.PI) * e * Math.sin(toRadians(E)) - M) / (1 - e * Math.cos(toRadians(E)));
//			error = Math.abs(E - E1);
//			E = E1;
//		}
////		xv = r * cos(v) = cos(E) - e
////		yv = r * sin(v) = sqrt(1.0 - e*e) * sin(E)
//		double x = a * (Math.cos(toRadians(E)) - e);
//		double y = a * (Math.sqrt(1.0 - e * e) * Math.sin(toRadians(E)));
//		double r = Math.sqrt(x * x + y * y);
//		double v = toDegrees(Math.atan2(y, x));
//		double n_rad = toRadians(N);
//		double xw_rad = toRadians(v + w);
//		double i_rad = toRadians(i);
//		// Now we know the Moon's position in the plane of the lunar orbit. To
//		// compute the Moon's position in ecliptic coordinates, we apply these
//		// formulae:
//		double xeclip = r * (Math.cos(n_rad) * Math.cos(xw_rad) - Math.sin(n_rad) * Math.sin(xw_rad) * Math.cos(i_rad));
//		double yeclip = r * (Math.sin(n_rad) * Math.cos(xw_rad) + Math.cos(n_rad) * Math.sin(xw_rad) * Math.cos(i_rad));
//		double zeclip = r * Math.sin(xw_rad) * Math.sin(i_rad);
//
//		Space space = new Space();
//		double RA = toDegrees(Math.atan2(yeclip, xeclip));
//		double Decl = toDegrees(Math.asin(zeclip / r));
//		Vector3D eliptic = new Vector3D(xeclip, yeclip, zeclip);
//		Vector3D spherical = new Vector3D(r,RA,Decl);
//		space.eliptic = eliptic;
//		space.spherical = spherical;
//		space.time = this.getCalendarString(null, c);
//		return space;
//	}
	
	public double dateToJulian(Calendar date) {
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH) + 1;
		int day = date.get(Calendar.DAY_OF_MONTH);
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int minute = date.get(Calendar.MINUTE);
		int second = date.get(Calendar.SECOND);

		double extra = (100.0 * year) + month - 190002.5;
		return (367.0 * year) - (Math.floor(7.0 * (year + Math.floor((month + 9.0) / 12.0)) / 4.0))
				+ Math.floor((275.0 * month) / 9.0) + day + ((hour + ((minute + (second / 60.0)) / 60.0)) / 24.0)
				+ 1721013.5 - ((0.5 * extra) / Math.abs(extra)) + 0.5;
	}
	
	public double rev(double x) {
		double rv = x - Math.round(x / 360.0) * 360;
		if (rv < 0.0) {
			rv = rv + 360;
		}
		return rv;
	}
	
	public double toRadians(double d) {
		return Math.PI * d / 180;
	}

	public double toDegrees(double rad) {
		return rev(180.0 * rad / Math.PI);
	}
	
    public List<Vector3D> getOrbit() {
        LinkedList<Vector3D> vertexList = new LinkedList<Vector3D>();
        if (this.orbitalPeriod != 0) {
            int resolution = 100;
            double increment = this.orbitalPeriod / resolution;
            Calendar c = (Calendar) this.time.clone();
            double count = 0;
            while (count <= this.orbitalPeriod) {
                Vector3D position = this.getSpace(c).eliptic;
                c.add(Calendar.DATE, (int) (Math.round(increment))); // number of days to ad
                count += increment;
                vertexList.add(position);
            }
        }
        return vertexList;
    }
    
    public double getRotationCorrection(Calendar a){
    	double t = this.startTime.getTime().getTime();
    	double T = a.getTime().getTime();
    	double difference = (T-t)/3600000;
//    	System.out.println("difference: "+difference);
    	double remainder = difference%this.rotation;
//    	System.out.println("remainder: "+remainder);
//    	System.out.println("rotation:"+ this.rotation);
    	double ratio = remainder/this.rotation;
    	double angle = ratio*360;
    	return angle;
    }
}
