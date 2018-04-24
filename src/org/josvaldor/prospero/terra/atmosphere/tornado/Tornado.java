package org.josvaldor.prospero.terra.atmosphere.tornado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.josvaldor.prospero.terra.unit.Coordinate;
import org.josvaldor.prospero.terra.unit.Event;
import org.josvaldor.prospero.terra.unit.Time;
import org.josvaldor.prospero.terra.unit.Type;

public class Tornado {
	public static void main(String[] args) {
		Tornado a = new Tornado();
		a.read();
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
						if (!values[27].equals("-") && !values[28].equals("-")) {
							event = new Event(Type.TORNADO);
							coordinate = new Coordinate();
							coordinate.latitude = Double.parseDouble(values[27]);
							coordinate.longitude = Double.parseDouble(values[28]);
							event.coordinate.add(coordinate);
							if (!values[29].equals("-") && !values[30].equals("-")) {
								coordinate = new Coordinate();
								coordinate.latitude = Double.parseDouble(values[29]);
								coordinate.longitude = Double.parseDouble(values[30]);
								event.coordinate.add(coordinate);
							}
							time = new Time(values[2] + " " + values[3]);
							event.time.add(time);
							event.attribute.put(labelArray[8], values[8]);
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
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return eventList;
	}
}
