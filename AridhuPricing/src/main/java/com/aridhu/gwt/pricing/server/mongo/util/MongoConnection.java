package com.aridhu.gwt.pricing.server.mongo.util;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MongoConnection {
	private static MongoClient mongoClient;
	private static DB db;
	private static final String DB_USERNAME = "aridhu";
	private static final char[] DB_PASSWORD = {'a', 'r', 'i', 'd', 'h', 'u'};
	
	public static void connect() throws UnknownHostException, MongoException {
		mongoClient = new MongoClient("165.225.150.200" , 27017 );
		db = mongoClient.getDB( "aridhu" );
		boolean dbstatus = db.authenticate(DB_USERNAME, DB_PASSWORD);
		System.out.println("DB status: " + dbstatus);
		Set<String> colls = db.getCollectionNames();
		for (String s : colls) {
		    System.out.println(s);
		}
	}
	public static DB getDB(){
		return db;
	}
	public static void close(){		
		mongoClient.close();
	}

}
