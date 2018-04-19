package org.josvaldor.prospero.terra.lithosphere.volcano;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.josvaldor.prospero.terra.unit.Coordinate;
import org.josvaldor.prospero.terra.unit.Event;
import org.josvaldor.prospero.terra.unit.Time;
import org.josvaldor.prospero.terra.unit.Type;

public class Volcano {
	public static void main(String[] args) {
		Volcano a = new Volcano();
		a.read();
//		System.out.println();
	}

	public List<Event> read() {
		String target_dir = "./data/volcano/";
		File dir = new File(target_dir);
		File[] files = dir.listFiles();
		List<Event> eventList = new LinkedList<Event>();
		for (File f : files) {
			if (f.isFile()) {
				BufferedReader inputStream = null;

				try {
					inputStream = new BufferedReader(new FileReader(f));
					String line;
					line = inputStream.readLine();
					String[] labels = line.split(",");
//					for (int i = 0; i < labels.length; i++) {
//						System.out.println(i + ":" + labels[i]);
//					}
					String[] values = null;
					Event event;
					Coordinate coordinate;
					Time time;
					while ((line = inputStream.readLine()) != null) {
						values = line.split(",");
						event = new Event(Type.VOLCANO);
						double latitude = 0;
						double longitude = 0;
						try {
							latitude = Double.parseDouble(values[8]);
							longitude = Double.parseDouble(values[9]);
						} catch (NumberFormatException e) {

						}
						if (latitude > 0 && longitude > 0) {
							coordinate = new Coordinate();
							coordinate.latitude = latitude;
							coordinate.longitude = longitude;
							System.out.println(coordinate);
							event.coordinate.add(coordinate);
						}
						int year = 0;
						int month = 0;
						int day = 0;
						int hour = 0;
						int minute = 0;
						int second = 0;
						try {
							year = Integer.parseInt(values[0]);
							month = Integer.parseInt(values[1]);
							day = Integer.parseInt(values[2]);
							hour =0;
							minute =0;
							second = 0;
							time = new Time(new GregorianCalendar(year, month - 1, day, hour, minute, second));
							System.out.println(time);
							event.time.add(time);
						} catch (NumberFormatException e) {

						}
//						event.attribute.put(labels[9], values[9]);
//						event.attribute.put(labels[10], values[10]);
//						event.attribute.put(labels[11], values[11]);
//						event.attribute.put(labels[12], values[12]);
//						event.attribute.put(labels[13], values[13]);
						eventList.add(event);
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return eventList;
	}

}
