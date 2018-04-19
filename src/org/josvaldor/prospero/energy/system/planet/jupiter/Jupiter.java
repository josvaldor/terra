/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josvaldor.prospero.energy.system.planet.jupiter;

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
public class Jupiter extends Planet {
    
    public Jupiter(Calendar calendar,Orbital centroid) {
        super();
        this.centroid = centroid;
        this.name = "jupiter";
        this.mass = 1.89813e27;
        this.radius = 71492;
        this.color = Color.PINK;
        this.orbitalPeriod = 4332.589;
        this.longitudeOfAscendingNode[0] = 100.4542;// o
        this.longitudeOfAscendingNode[1] = 2.76854E-5;// o
        this.inclination[0] = 1.3030;// i//0.00005
        this.inclination[1] = -1.557E-7;// i//0.00005
        this.argumentOfPeriapsis[0] = 273.8777;
        this.argumentOfPeriapsis[1] = 1.64505E-5;
        this.semiMajorAxis[0] = 5.20256 * Unit.ASTRONOMICAL;// a//1.00000011
        this.semiMajorAxis[1] = 0;
        this.eccentricity[0] = 0.048498;// e//0.01671022
        this.eccentricity[1] = 4.469E-9;// e
        this.meanAnomaly[0] = 19.8950;
        this.meanAnomaly[1] = 0.0830853001;
        this.orbitalPeriod = 4332.59;
        this.time = calendar;
        this.position = this.getSpace(this.time).eliptic;
        this.angularVelocity = 1.773408215404907e-04;
    }    
}
