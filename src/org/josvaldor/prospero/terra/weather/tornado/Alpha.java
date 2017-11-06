package org.josvaldor.prospero.terra.weather.tornado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.josvaldor.prospero.terra.Coordinate;
import org.josvaldor.prospero.terra.Time;
import org.josvaldor.prospero.terra.Weather;
import org.josvaldor.prospero.terra.weather.Event;

public class Alpha {
	public static void main(String[] args) {
		Alpha a = new Alpha();
		System.out.println(a.read());
	}

	public List<Event> read() {
		String target_dir = "./data/tornado/alpha";
		File dir = new File(target_dir);
		File[] files = dir.listFiles();
		List<Event> eventList = new LinkedList<Event>();

		for (File f : files) {
			if (f.isFile()) {
				BufferedReader inputStream = null;

				try {
					inputStream = new BufferedReader(new FileReader(f));
					String line;
					String labels = inputStream.readLine();
					String[] labelArray = labels.split(",");
					String[] values = null;
					Event event;
					Coordinate coordinate;
					Time time;
					while ((line = inputStream.readLine()) != null) {
						values = line.split(",");
						if(!values[27].equals("-")&&!values[28].equals("-")){
							event = new Event(Weather.TORNADO);
							coordinate = new Coordinate();
							coordinate.latitude = Double.parseDouble(values[27]);
							coordinate.longitude = Double.parseDouble(values[28]);
							event.location.add(coordinate);
							if(!values[29].equals("-")&&!values[30].equals("-")){
								coordinate = new Coordinate();
								coordinate.latitude = Double.parseDouble(values[29]);
								coordinate.longitude = Double.parseDouble(values[30]);
								event.location.add(coordinate);
							}
							time = new Time(values[2]+" "+values[3]);
							event.date.add(time);
							event.attribute.put(labelArray[9], values[9]);
							event.attribute.put(labelArray[10], values[10]);
							event.attribute.put(labelArray[11], values[11]);
							event.attribute.put(labelArray[12], values[12]);
							event.attribute.put(labelArray[13], values[13]);
							event.attribute.put(labelArray[25], values[25]);
							eventList.add(event);
						}
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
