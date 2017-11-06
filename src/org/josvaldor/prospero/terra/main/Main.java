/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Author(s):
 * Martin Vézina mgvez Github
 * Chen Huang ALEXMORF Github
 * Joaquin Rodriguez
 */
package org.josvaldor.prospero.terra.main;

import org.josvaldor.prospero.terra.Earth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.GregorianCalendar;
import javax.swing.JFrame;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		Earth earth = new Earth(new GregorianCalendar(2017,9,24));
		JFrame window = new JFrame("Energy");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		EarthPanel earthPanel = new EarthPanel();
		earthPanel.setEarth(earth);
		window.add(earthPanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		System.getProperties().put( "server.port", 8090);
		SpringApplication.run(Main.class, args);
	}
}
