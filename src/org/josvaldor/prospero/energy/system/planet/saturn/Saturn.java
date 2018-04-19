/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josvaldor.prospero.energy.system.planet.saturn;

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
public class Saturn extends Planet {

    public Saturn(Calendar calendar,Orbital centroid) {
        super();
        this.centroid = centroid;
        this.name = "saturn";
        this.mass =5.683e26;//1.8986e27;// 5.683e26;//1.8986e27;//5.683e26;
        this.radius = 58232;
        this.color = Color.CYAN;
        this.longitudeOfAscendingNode[0] = 113.6634;// o
        this.longitudeOfAscendingNode[1] = 2.38980E-5;// o
        this.inclination[0] = 2.4886;// i//0.00005
        this.inclination[1] = -1.081E-7;// i//0.00005
        this.argumentOfPeriapsis[0] = 339.3939;
        this.argumentOfPeriapsis[1] = 2.97661E-5;
        this.semiMajorAxis[0] = 9.55475 * Unit.ASTRONOMICAL;// a//1.00000011
        this.semiMajorAxis[1] = 0;
        this.eccentricity[0] = 0.055546;// e//0.01671022
        this.eccentricity[1] = -9.499E-9;// e
        this.meanAnomaly[0] = 316.9670;
        this.meanAnomaly[1] = 0.0334442282;
        this.orbitalPeriod = 10759.22;
        this.time = calendar;
        this.position = this.getSpace(this.time).eliptic;
        this.angularVelocity = 1.636246173744684e-04;
    }
}
