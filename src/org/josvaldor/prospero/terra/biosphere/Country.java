package org.josvaldor.prospero.terra.biosphere;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.geometry.Geometry;
//import org.geotools.geojson.geom.*;

import org.opengis.geometry.coordinate.Polygon;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.WKTReader;

public class Country {

	public static void main(String[] args) {
		Country a = new Country();
		a.point(-75.17194183200792, 40.001919022526465);
//		a.box(110, -45, 155, -10);
	}

	public MultiPolygon point(double latitude, double longitude) {
		MultiPolygon f =null;
		File file = new File("./data/land/ne_10m_admin_0_countries/ne_10m_admin_0_countries.shp");
		try {
			ShapefileDataStore dataStore = new ShapefileDataStore(file.toURI().toURL());
			String[] typeNames = dataStore.getTypeNames();
			String typeName = typeNames[0];
			SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
			Filter filter = ECQL.toFilter("CONTAINS (the_geom, POINT(" + latitude + " " + longitude + "))");
			SimpleFeatureCollection collection = featureSource.getFeatures(filter);
			SimpleFeatureIterator iterator = collection.features();
			try {
				while (iterator.hasNext()) {
					SimpleFeature feature = iterator.next();
					System.out.println(feature.getAttribute("NAME"));
					GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
					WKTReader reader = new WKTReader( geometryFactory );
					MultiPolygon polygon = (MultiPolygon) reader.read(feature.getDefaultGeometry()+"");
					f=polygon;
				}
			} finally {
				iterator.close();
			}
		} catch (Throwable e) {
		}
		return f;
	}

	public List<MultiPolygon> box(double latitudeA, double longitudeA, double latitudeB, double longitudeB) {
		File file = new File("./data/land/ne_10m_admin_0_countries/ne_10m_admin_0_countries.shp");
		MultiPolygon f =null;
		List<MultiPolygon> mList = new LinkedList<MultiPolygon>();
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
					MultiPolygon polygon = (MultiPolygon) reader.read(feature.getDefaultGeometry()+"");
					f=polygon;
					mList.add(f);
				}
			} finally {
				iterator.close();
			}

		} catch (Throwable e) {
		}
		return mList;
	}
}
