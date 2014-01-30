package com.aridhu.gwt.pricing.server.mongo.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.aridhu.gwt.pricing.server.mongo.TenantSession;
import com.aridhu.gwt.pricing.server.mongo.businessobjects.Item;
import com.aridhu.gwt.pricing.server.mongo.util.MongoConnection;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class ItemDao {
	DB db;
	DBCollection itmcol;
	ObjectMapper mapper;
	
	public ItemDao() {
		super();
		db = MongoConnection.getDB();
		itmcol = db.getCollection("Item");
		BasicDBObject indobj1 =new BasicDBObject("tenantID",1).append("itemID", 1);
		itmcol.ensureIndex(indobj1,"ItemU1",true);
		
		BasicDBObject indobj2 =new BasicDBObject("tenantID",1).append("itemName", 1);
		itmcol.ensureIndex(indobj2,"ItemU2",true);
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
	}
	
	public void insert(Item itm)
	{
		DBObject itmDBObj = null;
		itm.setTenantID(TenantSession.getTenantID());
		itm.setItemID(new ObjectId());
		itm.setLastModified(new Date());
		itm.setCreatedOn(new Date());

		try {
			itmDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(itm));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
	    
		WriteResult wr=itmcol.insert(itmDBObj);
	}
	
	public void update(Item itm)
	{
		DBObject itmDBObj=null;
		BasicDBObject searchQuery=null;

		searchQuery = (BasicDBObject) JSON.parse("{\"itemID\" : { \"$oid\" : \"" + itm.getItemID() + "\"}}" );
		
		try {
			itmDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(itm));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
			
		WriteResult wr=itmcol.update(searchQuery,itmDBObj);

		if (wr.getN() == 0 )
		{
			throw new RuntimeException("Update failed");
		}
	}
	
	public void remove(Item itm)
	{
		BasicDBObject searchQuery=null;

		searchQuery = (BasicDBObject) JSON.parse("{\"itemID\" : { \"$oid\" : \"" + itm.getItemID() + "\"}}" );

		WriteResult wr=itmcol.remove(searchQuery);

		if (wr.getN() == 0 )
		{
			throw new RuntimeException("Delete failed");
		}
	}
	
	
	public List<Item> findAll() throws JsonParseException, JsonMappingException, IOException
	{
		List<Item> itmList = new ArrayList<Item>();
		DBCursor cursor = itmcol.find();
		Item itm;
		BasicDBObject itmDBObj;
		try {
			   while(cursor.hasNext()) {
			       
				   itmDBObj=(BasicDBObject) cursor.next();
				   itm=mapper.readValue(JSON.serialize(itmDBObj), Item.class);
//				   System.out.println(itm.getItemName());
//				   System.out.println(itm.getItemDescription());
				   itmList.add(itm);
			   }
			} finally {
			   cursor.close();
			}
		return itmList;
	}
	
	public List<Item> findByObject(BasicDBObject searchQuery) throws JsonParseException, JsonMappingException, IOException
	{
		List<Item> itmList = new ArrayList<Item>();
		DBCursor cursor = itmcol.find(searchQuery);
		BasicDBObject itmDBObj;
		Item itm;
		try {
			   while(cursor.hasNext()) {
				   itmDBObj=(BasicDBObject) cursor.next();
				   String serialJson=JSON.serialize(itmDBObj);
				   itm=mapper.readValue(serialJson, Item.class);
				   itmList.add(itm);
			   }
			} finally {
			   cursor.close();
			}
		return itmList;
	}

}
