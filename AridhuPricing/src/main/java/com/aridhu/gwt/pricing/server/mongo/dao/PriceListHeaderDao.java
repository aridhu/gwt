package com.aridhu.gwt.pricing.server.mongo.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.aridhu.gwt.pricing.server.mongo.TenantSession;
import com.aridhu.gwt.pricing.server.mongo.businessobjects.PriceListHeader;
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

public class PriceListHeaderDao {
	DB db;
	DBCollection plhcol;
	ObjectMapper mapper;
	
	public PriceListHeaderDao() {
		super();
		db = MongoConnection.getDB();
		plhcol = db.getCollection("PriceListHeader");
		BasicDBObject indobj1 =new BasicDBObject("tenantID",1).append("priceListID", 1);
		plhcol.ensureIndex(indobj1,"plu1",true);
		
		BasicDBObject indobj2 =new BasicDBObject("tenantID",1).append("name", 1);
		plhcol.ensureIndex(indobj2,"plu2",true);
		mapper = new ObjectMapper();
		//mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
	}
	
	
	public void insertMulti(List<PriceListHeader> plhList) 
	{
		List<DBObject> plhDBList = new ArrayList<DBObject>();
		DBObject plhDBObj = null;

		for (int i = 0; i < plhList.size(); i++) {
			plhList.get(i).setTenantID(TenantSession.getTenantID());
			plhList.get(i).setPriceListID(new ObjectId());
			plhList.get(i).setLastModified(new Date());
			plhList.get(i).setCreatedOn(new Date());
			try {
				plhDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(plhList.get(i)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			plhDBList.add(plhDBObj); 
	    }
		
		WriteResult wr=plhcol.insert(plhDBList);
		
		
	}
	
	public void insert(PriceListHeader plh)
	{
		DBObject plhDBObj = null;
		plh.setTenantID(TenantSession.getTenantID());
		plh.setPriceListID(new ObjectId());
		plh.setLastModified(new Date());
		plh.setCreatedOn(new Date());

		try {
			plhDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(plh));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
	    
		WriteResult wr=plhcol.insert(plhDBObj);
		
		
	}
	
	public void update(PriceListHeader plh)
	{
		DBObject plhDBObj=null;
		BasicDBObject searchQuery=null;
		
		//searchQuery = (BasicDBObject) JSON.parse("{name : \"" + plh.getName() + "\"}");
		
		searchQuery = (BasicDBObject) JSON.parse("{\"tenantID\" : { \"$oid\" : \"" + plh.getTenantID() 
				+ "\"} , \"priceListID\" : { \"$oid\" : \"" + plh.getPriceListID()+ "\"}}" );
		
//		try {
//			FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter",
//				    SimpleBeanPropertyFilter.filterOutAllExcept("name"));
//			
//			ObjectMapper fMapper = new ObjectMapper();
//			fMapper.setFilters(filters);
//			
//			
//			searchQuery = (BasicDBObject) JSON.parse(fMapper.writeValueAsString(plh));
//		} catch (JsonProcessingException e1) {
//			e1.printStackTrace();
//		}

		
			    
//		FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter",
//			    SimpleBeanPropertyFilter.serializeAllExcept(""));
//		mapper.setFilters(filters);
//	
		
		try {
			plhDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(plh));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
			
		WriteResult wr=plhcol.update(searchQuery,plhDBObj);

		if (wr.getN() == 0 )
		{
			throw new RuntimeException("Update failed");
		}
	}
	
	
	public List<PriceListHeader> findAll() throws JsonParseException, JsonMappingException, IOException
	{
		List<PriceListHeader> plhList = new ArrayList<PriceListHeader>();
		DBCursor cursor = plhcol.find();
		PriceListHeader plh;
		BasicDBObject plhDBObj;
		try {
			   while(cursor.hasNext()) {
			       //plhList.add((PriceListHeader)cursor.next());
				   plhDBObj=(BasicDBObject) cursor.next();
				   plh=mapper.readValue(JSON.serialize(plhDBObj), PriceListHeader.class);
				   plhList.add(plh);
			   }
			} finally {
			   cursor.close();
			}
		return plhList;
	}
	
	public void remove(PriceListHeader plh)
	{
		BasicDBObject searchQuery=null;

		searchQuery = (BasicDBObject) JSON.parse("{\"tenantID\" : { \"$oid\" : \"" + plh.getTenantID() 
				+ "\"} , \"priceListID\" : { \"$oid\" : \"" + plh.getPriceListID()+ "\"}}" );

		WriteResult wr=plhcol.remove(searchQuery);

		if (wr.getN() == 0 )
		{
			throw new RuntimeException("Delete failed");
		}
	}
	
	public List<PriceListHeader> findByObject(BasicDBObject searchQuery) throws JsonParseException, JsonMappingException, IOException
	{
		List<PriceListHeader> plhList = new ArrayList<PriceListHeader>();
		DBCursor cursor = plhcol.find(searchQuery);
		BasicDBObject plhDBObj;
		try {
			   while(cursor.hasNext()) {
				   plhDBObj=(BasicDBObject) cursor.next();
				   String serialJson=JSON.serialize(plhDBObj);
				   PriceListHeader plh = mapper.readValue(serialJson, PriceListHeader.class);
				   plhList.add(plh);
			   }
			} finally {
			   cursor.close();
			}
		return plhList;
	}
}
