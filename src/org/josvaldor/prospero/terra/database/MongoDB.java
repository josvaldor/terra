package org.josvaldor.prospero.terra.database;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDB {
	
	public static void main(String[] args){
		try {
			MongoClient mongo = new MongoClient("localhost",27017);
			DB db = mongo.getDB("earth");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
