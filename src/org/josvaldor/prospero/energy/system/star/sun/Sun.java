/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josvaldor.prospero.energy.system.star.sun;

import org.josvaldor.prospero.energy.system.star.Star;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author jorodriguez
 */
public class Sun extends Star {

//    public List<Sunspot> dailySunspotList = new ArrayList<Sunspot>();
//    public List<Sunspot> monthlySunspotList = new ArrayList<Sunspot>();
//    public List<Sunspot> monthlySmoothedSunspotList = new ArrayList<Sunspot>();
//
//    String dailySunspotFileName = "./data/SN_d_tot_V2.0.csv";
//    String monthlySunspotFileName = "./data/SN_m_tot_V2.0.csv";
//    String monthlySmoothedSunspotFileName = "./data/SN_ms_tot_V2.0.csv";
//    public Sunspot dailySunspot = null;
//    private Sunspot monthlySunspot;
//    private Sunspot monthlySmoothedSunspot;

	public Sun(){
        this.name = "sun";
        this.mass = 1.9891e30;
        this.radius = 6.96342e5;
        this.color = Color.YELLOW;
        this.position = new Vector3D(0, 0, 0);
        this.angularVelocity = 2.865329607243705e-06;
	}
    public Sun(Calendar calendar) {
        this.name = "sun";
        this.mass = 1.9891e30;
        this.radius = 6.96342e5;
        this.color = Color.YELLOW;
        this.position = new Vector3D(0, 0, 0);
        this.time = calendar;
        this.angularVelocity = 2.865329607243705e-06;
//        Sunspot sunspot = null;
//        for (String[] s : CSVParser.getData(dailySunspotFileName)) {
//            sunspot = new Sunspot();
//            sunspot.setDaily(s);
//            this.dailySunspotList.add(sunspot);
//        }
//        for (String[] s : CSVParser.getData(monthlySunspotFileName)) {
//            sunspot = new Sunspot();
//            sunspot.setMonthly(s);
//            this.monthlySunspotList.add(sunspot);
//        }
//        for (String[] s : CSVParser.getData(monthlySmoothedSunspotFileName)) {
//            sunspot = new Sunspot();
//            sunspot.setMonthlySmoothed(s);
//            this.monthlySmoothedSunspotList.add(sunspot);
//        }
    }

//    @Override
//    public void setTime(Calendar c) {
//        Calendar sunspotTime = null;
//        for (Sunspot s : dailySunspotList) {
//            sunspotTime = s.time;
//            if (c.get(Calendar.YEAR) == sunspotTime.get(Calendar.YEAR)
//                    && c.get(Calendar.MONTH) == sunspotTime.get(Calendar.MONTH)
//                    && c.get(Calendar.DAY_OF_MONTH) == sunspotTime.get(Calendar.DAY_OF_MONTH)) {
//                this.dailySunspot = s;
//                break;
//            } else {
//                dailySunspot = new Sunspot();
//            }
//        }
//
//        for (Sunspot s : monthlySunspotList) {
//            sunspotTime = s.time;
//            if (c.get(Calendar.YEAR) == sunspotTime.get(Calendar.YEAR)
//                    && c.get(Calendar.MONTH) == sunspotTime.get(Calendar.MONTH)) {
//                this.monthlySunspot = s;
//                break;
//            } else {
//                monthlySunspot = new Sunspot();
//            }
//        }
//        for (Sunspot s : monthlySmoothedSunspotList) {
//            sunspotTime = s.time;
//            if (c.get(Calendar.YEAR) == sunspotTime.get(Calendar.YEAR)
//                    && c.get(Calendar.MONTH) == sunspotTime.get(Calendar.MONTH)) {
//                this.monthlySmoothedSunspot = s;
//                break;
//            } else {
//                this.monthlySmoothedSunspot = new Sunspot();
//            }
//        }
//    }
//
//    public Sunspot getDailySunspot() {
//        return this.dailySunspot;
//    }
//
//    public Sunspot getMonthlySunspot() {
//        return this.monthlySunspot;
//    }
//
//    public Sunspot getMonthlySmoothedSunspot() {
//        return this.monthlySmoothedSunspot;
//    }

}
