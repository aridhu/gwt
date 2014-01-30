package com.aridhu.gwt.pricing.server.mongo.businessobjects;

import java.io.IOException;
import java.net.UnknownHostException;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bson.types.ObjectId;
import com.aridhu.gwt.pricing.server.mongo.TenantSession;
import com.aridhu.gwt.pricing.server.mongo.dao.PriceListHeaderDao;
import com.aridhu.gwt.pricing.server.mongo.util.MongoConnection;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class TPriceListHeader {
	public static void main(String[] args) throws UnknownHostException, MongoException, ParseException {
		
		MongoConnection.connect();
		TenantSession.connect("ABC Corporation");
		PriceListHeader plh = null;
		List<PriceListHeader> plhList = new ArrayList<PriceListHeader>();
		PriceListHeaderDao plhDao=new PriceListHeaderDao();
		
		
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
		
//		TimeZone tz = TimeZone.getTimeZone("UTC");
//		df.setTimeZone(tz);
		
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		
//===============Insert=======================//		
		
		plh = new PriceListHeader();
		plh.setName("Wholesale - USD");
		plh.setDescription("Wholesale Price List - USD");
		plh.setCurrencyCode("USD");
		plh.setActiveFlag("Y");
		plh.setEffectiveFrom(new Date());
		plh.setEffectiveTo(df.parse("01/05/2015"));
		
		plhDao.insert(plh);
		
		//For Multiple rows insert
//		plhList.add(plh);
//		plhDao.insertMulti(plhList);
//============End Insert======================//

//=================Query==============//
		
//		try {
//			plhList = plhDao.findAll();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}

//==========End Query===============//
		
//===============Update============//
	//Get plh object by price List name
//	BasicDBObject searchQuery=null;
//	searchQuery = (BasicDBObject) JSON.parse("{\"name\" : \"Global US Price List\"}" );
//	try {
//		plhList = plhDao.findByObject(searchQuery);
//		plh= plhList.get(0);
//	} catch (IOException e) {
//	
//		e.printStackTrace();
//	}
//	
//	//Change the value and call update.
//	plh.setDescription("Fine Working 3");
//	plhDao.update(plh);
	
	
		
//===============End Update===========//

//===============Remove============//
		//Get plh object by price List name
//		BasicDBObject searchQuery=null;
//		searchQuery = (BasicDBObject) JSON.parse("{\"name\" : \"Global US Price List\"}" );
//		try {
//			plhList = plhDao.findByObject(searchQuery);
//			plh= plhList.get(0);
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//		plhDao.remove(plh);
//		
		
			
//===============End Remove===========//
		
		MongoConnection.close();
		
	}
}
