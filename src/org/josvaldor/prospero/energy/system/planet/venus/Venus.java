/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josvaldor.prospero.energy.system.planet.venus;

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
public class Venus extends Planet {

    public Venus(Calendar calendar, Orbital centroid) {
        super();
        this.centroid = centroid;
        this.name = "venus";
        this.radius = 3376;
        this.mass = 4.867e24;
        this.color = Color.DARK_GRAY;
        this.longitudeOfAscendingNode[0] = 76.6799;//o
        this.longitudeOfAscendingNode[1] = 2.46590E-5;//o
        this.inclination[0] = 3.3946;//i//0.00005
        this.inclination[1] = 2.75E-8;//i//0.00005
        this.argumentOfPeriapsis[0] = 54.8910;
        this.argumentOfPeriapsis[1] = 1.38374E-5;
        this.semiMajorAxis[0] = 0.723330* Unit.ASTRONOMICAL;//a//1.00000011
        this.semiMajorAxis[1] = 0;
        this.eccentricity[0] = 0.006773;//e//0.0167102
        this.eccentricity[1] = -1.302E-9;
        this.meanAnomaly[0] = 48.0052;
        this.meanAnomaly[1] = 1.6021302244;
        this.orbitalPeriod = 225;
        this.time = calendar;
        this.position = this.getSpace(this.time).eliptic;
        this.angularVelocity = 2.99e-07;
    }
}
