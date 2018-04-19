package org.josvaldor.prospero.terra.lithosphere;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.io.WKTReader;

public class Tectonic {

	public static void main(String[] args) {
		Tectonic a = new Tectonic();
		//a.box(90, 180, -90, -180);
		a.box(180, 90, -180, -90);
	}

	public List<MultiLineString> box(double latitudeA, double longitudeA, double latitudeB, double longitudeB) {
		File file = new File("./data/lithosphere/tectonicplates/PB2002_boundaries.shp");
		MultiLineString m = null;
		List<MultiLineString> mList = new LinkedList<MultiLineString>();
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
					GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
					WKTReader reader = new WKTReader( geometryFactory );
					m = (MultiLineString) reader.read(feature.getDefaultGeometry()+"");
					mList.add(m);
					
				}
			} finally {
				iterator.close();
			}

		} catch (Throwable e) {
		}
		return mList;
	}
}
