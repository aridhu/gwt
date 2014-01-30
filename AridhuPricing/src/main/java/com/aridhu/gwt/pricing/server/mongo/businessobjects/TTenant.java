package com.aridhu.gwt.pricing.server.mongo.businessobjects;

import java.io.IOException;
import java.net.UnknownHostException;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.aridhu.gwt.pricing.server.mongo.dao.TenantDao;
import com.aridhu.gwt.pricing.server.mongo.util.MongoConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class TTenant {
	
	public static void main(String[] args) throws UnknownHostException, MongoException, ParseException {
		
		MongoConnection.connect();
		Tenant ten = null;
		List<Tenant> tenList = new ArrayList<Tenant>();
		TenantDao tenDao=new TenantDao();
		
		
		
		
		
		
//===============Insert=======================//		
		
		ten = new Tenant();
		ten.setTenantID(new ObjectId());
		ten.setName("ABC Corporation");
		ten.setDescription("ABC Corporation");
		tenDao.insert(ten);
		
//		ten = new Tenant();
//		ten.setTenantID(new ObjectId());
//		ten.setName("IBM Corporation");
//		ten.setDescription("IBM Corporation");
//		tenDao.insert(ten);
		
//============End Insert======================//

//=================Query==============//
		
//		try {
//			tenList = tenDao.findAll();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}

//==========End Query===============//
		
//===============Update============//
	//Get ten object by tenant name
//	BasicDBObject searchQuery=null;
//	searchQuery = (BasicDBObject) JSON.parse("{\"name\" : \"Idhayam\"}" );
//
//	try {
//		tenList = tenDao.findByObject(searchQuery);
//		ten= tenList.get(0);
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	
//	//Change the value and call update.
//	ten.setDescription("Idhayam Oil");
//	tenDao.update(ten);
	
	
		
//===============End Update===========//

//===============Remove============//
		//Get ten object by tenant name
//		BasicDBObject searchQuery=null;
//		searchQuery = (BasicDBObject) JSON.parse("{\"name\" : \"Idhayam\"}" );
//
//		try {
//			tenList = tenDao.findByObject(searchQuery);
//			ten= tenList.get(0);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		tenDao.remove(ten);
			
//===============End Remove===========//
		
		
		MongoConnection.close();
		
	}
}
