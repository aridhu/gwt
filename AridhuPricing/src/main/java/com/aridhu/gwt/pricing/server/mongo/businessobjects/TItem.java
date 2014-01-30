package com.aridhu.gwt.pricing.server.mongo.businessobjects;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aridhu.gwt.pricing.server.mongo.TenantSession;
import com.aridhu.gwt.pricing.server.mongo.dao.ItemDao;
import com.aridhu.gwt.pricing.server.mongo.util.MongoConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class TItem {
	public static void main(String[] args) throws UnknownHostException, MongoException, ParseException {
		
		MongoConnection.connect();
		TenantSession.connect("ABC Corporation");
		Item itm = null;
		List<Item> itmList = new ArrayList<Item>();
		ItemDao itmDao=new ItemDao();
		
		
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy");

		
//===============Insert=======================//		
		
//		itm = new Item();
//		itm.setItemName("Desktop");;
//		itm.setItemDescription("Desktop");
//		itmDao.insert(itm);
//
//		itm = new Item();
//		itm.setItemName("Laptop");;
//		itm.setItemDescription("Laptop");
//		itmDao.insert(itm);
//
//		itm = new Item();
//		itm.setItemName("Keyboard");;
//		itm.setItemDescription("Keyboard");
//		itmDao.insert(itm);
//
//		itm = new Item();
//		itm.setItemName("Wireless Mouse");;
//		itm.setItemDescription("Wireless Mouse");
//		itmDao.insert(itm);
//
//		itm = new Item();
//		itm.setItemName("Mouse");;
//		itm.setItemDescription("Mouse");
//		itmDao.insert(itm);
//
//		itm = new Item();
//		itm.setItemName("Wireless Keyboard");;
//		itm.setItemDescription("Wireless Keyboard");
//		itmDao.insert(itm);
//
//		itm = new Item();
//		itm.setItemName("Router");;
//		itm.setItemDescription("Router");
//		itmDao.insert(itm);
//
//		itm = new Item();
//		itm.setItemName("3GB Memory");;
//		itm.setItemDescription("3GB Memory");
//		itmDao.insert(itm);
//
//		itm = new Item();
//		itm.setItemName("500 GB Hard Disk");;
//		itm.setItemDescription("500 GB Hard Disk");
//		itmDao.insert(itm);


//============End Insert======================//

//=================Query==============//
		
//		try {
//			itmList = itmDao.findAll();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}

//==========End Query===============//
		
//===============Update============//
	//Get item object by Item name
//	BasicDBObject searchQuery=null;
//	searchQuery = (BasicDBObject) JSON.parse("{\"itemName\" : \"Desktop\"}" );
//
//	try {
//		itmList = itmDao.findByObject(searchQuery);
//		itm= itmList.get(0);
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	
//	//Change the value and call update.
//	itm.setItemDescription("Desktop Description");
//	itmDao.update(itm);
	
	
		
//===============End Update===========//

//===============Remove============//
		//Get itm object by itmant name
//		BasicDBObject searchQuery=null;
//		searchQuery = (BasicDBObject) JSON.parse("{\"itemName\" : \"Desktop\"}" );
//
//		try {
//			itmList = itmDao.findByObject(searchQuery);
//			itm= itmList.get(0);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		itmDao.remove(itm);
			
//===============End Remove===========//
		
			
		
		MongoConnection.close();
}
}
