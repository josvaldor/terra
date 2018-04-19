/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josvaldor.prospero.energy.system.planet.mars;

import org.josvaldor.prospero.energy.Orbital;
import org.josvaldor.prospero.energy.Unit;
import org.josvaldor.prospero.energy.system.planet.Planet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Calendar;

/**
 *
 * @author jorodriguez
 */
public class Mars extends Planet {

    public Mars(Calendar calendar,Orbital centroid) {
        super();
        this.centroid = centroid;
        this.name = "mars";
        this.radius = 3376;
        this.mass = 6.39e23;
        this.color = Color.RED;
        this.longitudeOfAscendingNode[0] = 49.5574;//o
        this.longitudeOfAscendingNode[1] = 2.11081E-5;//o
        this.inclination[0] = 1.8497;//i//0.00005
        this.inclination[1] = -1.78E-8;//i//0.00005
        this.argumentOfPeriapsis[0] = 286.5016;
        this.argumentOfPeriapsis[1] = 2.92961E-5;
        this.semiMajorAxis[0] =  1.523688* Unit.ASTRONOMICAL;//a//1.00000011
        this.semiMajorAxis[1]=0;
        this.eccentricity[0] = 0.093405;//e//0.01671022
        this.eccentricity[1] = 2.516E-9;//e
        this.meanAnomaly[0] = 18.6021;
        this.meanAnomaly[1] = 0.5240207766;
        this.orbitalPeriod = 686.980;
        this.time = calendar;
        this.position = this.getSpace(this.time).eliptic;
        this.angularVelocity = 7.088218127178316e-05;
    }
}
