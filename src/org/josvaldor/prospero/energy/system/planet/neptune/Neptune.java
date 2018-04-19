/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josvaldor.prospero.energy.system.planet.neptune;

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
public class Neptune extends Planet {

    public Neptune(Calendar calendar,Orbital centroid) {
        super();
        this.centroid = centroid;
        this.name = "neptune";
        this.mass = 1.024E26;//102.413;
        this.radius = 24622;
        this.color = Color.BLUE;
        this.longitudeOfAscendingNode[0] = 131.6737;// o
        this.longitudeOfAscendingNode[1] = 0;// o
        this.inclination[0] = 1.7700;// i//0.00005
        this.inclination[1] = -2.55E-7;// i//0.00005
        this.argumentOfPeriapsis[0] = 272.8461;
        this.argumentOfPeriapsis[1] = -6.027E-6;
        this.semiMajorAxis[0] = 30.05826 * Unit.ASTRONOMICAL;// a//1.00000011
        this.semiMajorAxis[1] = 3.313E-9 * Unit.ASTRONOMICAL;
        this.eccentricity[0] = 0.008606;// e//0.01671022
        this.eccentricity[1] = 2.15E-9;// e
        this.meanAnomaly[0] = 260.2471;
        this.meanAnomaly[1] = 0.005995147;
        this.orbitalPeriod = 60182;
        this.time = calendar;
        this.position = this.getSpace(this.time).eliptic;
        this.angularVelocity =  1.083382527619075e-04;
    }
}
