/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Author(s):
 * Martin Vézina mgvez Github
 * Chen Huang ALEXMORF Github
 * Joaquin Rodriguez
 */
package org.josvaldor.prospero.energy.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.josvaldor.prospero.energy.Coordinate;
import org.josvaldor.prospero.energy.Space;
import org.josvaldor.prospero.energy.system.Solar;
import org.josvaldor.prospero.energy.system.planet.earth.Earth;
import org.josvaldor.prospero.energy.system.star.sun.Sun;

import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		Solar solar = new Solar();
		List<Coordinate> cList = solar.getCoordinateList(new Earth(solar.getCalendar(null,"1997-06-21 00:00:00"),new Sun()), solar.getEnergyList(solar.getCalendar(null,"1997-06-21 00:00:00")));
//		for(Coordinate c: cList){
//			System.out.println(c);
//		}
//		try {
//			Map<String,List<Space>> map = solar.searchSpace("1970-01-01 00:00:00","1980-01-01 00:00:00",0.95,0.3);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		JFrame window = new JFrame("Energy");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		SolarPanel solarPanel = new SolarPanel();
		solarPanel.setSolar(solar);
		window.add(solarPanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
//		SpringApplication.run(Main.class, args);
	}
}
