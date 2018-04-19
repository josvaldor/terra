package org.josvaldor.prospero.terra.biosphere;

import java.io.File;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.ecql.ECQL;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;

public class City {

	public static void main(String[] args) {
		City a = new City();
		a.point(-75.17194183200792, 40.001919022526465);
		a.box(110, -45, 155, -10);
	}

	public void point(double latitude, double longitude) {
		File file = new File("./data/land/ne_10m_populated_places/ne_10m_populated_places.shp");
		try {
			ShapefileDataStore dataStore = new ShapefileDataStore(file.toURI().toURL());
			String[] typeNames = dataStore.getTypeNames();
			String typeName = typeNames[0];
			SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
			Filter filter = ECQL.toFilter(" CONTAINS (the_geom, POINT(" + latitude + " " + longitude + "))");
			SimpleFeatureCollection collection = featureSource.getFeatures(filter);
			SimpleFeatureIterator iterator = collection.features();
			try {
				while (iterator.hasNext()) {
					SimpleFeature feature = iterator.next();
					System.out.println(feature.getAttribute("NAMEASCII"));
				}
			} finally {
				iterator.close();
			}

		} catch (Throwable e) {
		}
	}

	public void box(double latitudeA, double longitudeA, double latitudeB, double longitudeB) {
		File file = new File("./data/land/ne_10m_populated_places/ne_10m_populated_places.shp");
		try {
			ShapefileDataStore dataStore = new ShapefileDataStore(file.toURI().toURL());
			String[] typeNames = dataStore.getTypeNames();
			String typeName = typeNames[0];
			SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
			Filter filter = ECQL.toFilter("BBOX (the_geom, " + latitudeA + ", " + longitudeA + ", " + latitudeB + ", " + longitudeB + ")");
			SimpleFeatureCollection collection = featureSource.getFeatures(filter);
			SimpleFeatureIterator iterator = collection.features();
			try {
				while (iterator.hasNext()) {
					SimpleFeature feature = iterator.next();
					String name2 = (String) feature.getAttribute("NAMEASCII");
					System.out.println("Name:" + name2);
				}
			} finally {
				iterator.close();
			}

		} catch (Throwable e) {
		}
	}
}
