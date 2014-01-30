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
import com.aridhu.gwt.pricing.server.mongo.dao.PriceListDetailDao;
import com.aridhu.gwt.pricing.server.mongo.dao.PriceListHeaderDao;
import com.aridhu.gwt.pricing.server.mongo.util.MongoConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class TPriceListDetail {
	public static void main(String[] args) throws UnknownHostException, MongoException, ParseException {
		
		MongoConnection.connect();
		TenantSession.connect("ABC Corporation");
		PriceListDetail pld = null;
		List<PriceListDetail> pldList = new ArrayList<PriceListDetail>();
		PriceListDetailDao pldDao=new PriceListDetailDao();
		
		
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy");

		//Get PriceList Header ID
		
		PriceListHeaderDao plhDao = new PriceListHeaderDao();
		PriceListHeader plh=null;
		List<PriceListHeader> plhList;
		BasicDBObject searchPriceList=null;
		searchPriceList = (BasicDBObject) JSON.parse("{\"name\" : \"Global US Price List\","
				+ " \"tenantID\" : { \"$oid\" : \"" + TenantSession.getTenantID().toString() + "\"}}") ;
		try {
			plhList = plhDao.findByObject(searchPriceList);
			plh= plhList.get(0);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		//Get Item ID
		
		ItemDao itmDao = new ItemDao();
		Item itm=null;
		List<Item> itmList;
		BasicDBObject searchItem=null;
		searchItem = (BasicDBObject) JSON.parse("{\"itemName\" : \"Keyboard\","
				+ " \"tenantID\" : { \"$oid\" : \"" + TenantSession.getTenantID().toString() + "\"}}") ;
		try {
			itmList = itmDao.findByObject(searchItem);
			itm= itmList.get(0);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		

		
//===============Insert=======================//		
//		List<PriceBreak> pbrl=new ArrayList<PriceBreak>();
//		PriceBreak pbr;
//		
//		pbr=new PriceBreak();
//		pbr.setLineNumber(1);
//		pbr.setListPrice(50);
//		pbr.setVolumeFrom(0);
//		pbr.setVolumeFrom(100);
//		pbrl.add(pbr);
//		
//		pbr=new PriceBreak();
//		pbr.setLineNumber(2);
//		pbr.setListPrice(40);
//		pbr.setVolumeFrom(101);
//		pbr.setVolumeFrom(200);
//		pbrl.add(pbr);
//		
//		pbr=new PriceBreak();
//		pbr.setLineNumber(3);
//		pbr.setListPrice(30);
//		pbr.setVolumeFrom(201);
//		pbr.setVolumeFrom(300);
//		pbrl.add(pbr);
//		
//		
//		pld = new PriceListDetail();
//		pld.setPriceListID(plh.getPriceListID());
//		pld.setItemObjectType("Item");
//		pld.setItemObjectID(itm.getItemID());
//		pld.setPriceBreakList(pbrl);
//		pld.setListPrice(100);
//		pld.setUomCode("EA");
//		pld.setEffectiveFrom(new Date());
//		pld.setEffectiveTo(df.parse("01/05/2015"));
//		
//		pldDao.insert(pld);
		

//============End Insert======================//

//=================Query==============//
		
		try {
			pldList = pldDao.findAll();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

//==========End Query===============//
		
//===============Update============//
   //Get plh object by price List name
//		BasicDBObject searchQuery=null;
//		
//		searchQuery = (BasicDBObject) JSON.parse("{\"tenantID\" : { \"$oid\" : \"" + plh.getTenantID() 
//				+ "\"} , \"priceListID\" : { \"$oid\" : \"" + plh.getPriceListID() + "\"} " 
//				+ ", \"itemObjectID\" : { \"$oid\" : \"" + itm.getItemID() + "\"} }" );
//		
//		try {
//			pldList = pldDao.findByObject(searchQuery);
//			pld= pldList.get(0);
//		} catch (IOException e) {
//		
//			e.printStackTrace();
//		}
//		
//		//Change the value and call update.
//		pld.setListPrice(200);
//		pldDao.update(pld);
//		
//		
			
	//===============End Update===========//		

		//===============Remove============//

//				BasicDBObject searchQuery=null;
//				
//				searchQuery = (BasicDBObject) JSON.parse("{\"tenantID\" : { \"$oid\" : \"" + plh.getTenantID() 
//						+ "\"} , \"priceListID\" : { \"$oid\" : \"" + plh.getPriceListID() + "\"} " 
//						+ ", \"itemObjectID\" : { \"$oid\" : \"" + itm.getItemID() + "\"} }" );
//				
//				try {
//					pldList = pldDao.findByObject(searchQuery);
//					pld= pldList.get(0);
//				} catch (IOException e) {
//				
//					e.printStackTrace();
//				}
//
//				pldDao.remove(pld);
//				
				
					
			//===============End Update===========//		

		
		MongoConnection.close();
}
}
