/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Author(s):
 * Martin Vézina mgvez Github
 * Chen Huang ALEXMORF Github
 * Joaquin Rodriguez
 */
package org.josvaldor.prospero.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.josvaldor.prospero.energy.Coordinate;
import org.josvaldor.prospero.energy.Space;
import org.josvaldor.prospero.energy.main.SolarPanel;
import org.josvaldor.prospero.energy.system.Solar;
import org.josvaldor.prospero.energy.system.planet.earth.Earth;
import org.josvaldor.prospero.energy.system.star.sun.Sun;
import org.josvaldor.prospero.terra.Terra;
import org.josvaldor.prospero.terra.main.*;

import java.awt.GridLayout;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

@SpringBootApplication
public class Main2 {
	public static void main(String[] args) {
		Solar solar = new Solar();
		JFrame window = new JFrame("Energy");
		window.setLayout(new GridLayout(2, 1));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		SolarPanel solarPanel = new SolarPanel();
		solarPanel.setSolar(solar);
		
		Terra earth = new Terra(new GregorianCalendar(2017,9,24));
		EarthPanel earthPanel = new EarthPanel();
		earthPanel.setEarth(earth);
		window.add(earthPanel);
		window.add(solarPanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		//SpringApplication.run(Main2.class, args);
	}
}
