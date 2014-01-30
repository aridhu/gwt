package com.aridhu.gwt.pricing.server.mongo.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.aridhu.gwt.pricing.server.mongo.TenantSession;
import com.aridhu.gwt.pricing.server.mongo.businessobjects.PriceListDetail;
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

public class PriceListDetailDao {
	DB db;
	DBCollection pldcol;
	ObjectMapper mapper;
	
	public PriceListDetailDao() {
		super();
		db = MongoConnection.getDB();
		pldcol = db.getCollection("PriceListDetail");
		
		BasicDBObject indobj1 =new BasicDBObject("tenantID",1).append("detailID", 1);
		pldcol.ensureIndex(indobj1,"pldu1",true);
		
		BasicDBObject indobj2 =new BasicDBObject("tenantID",1)
										.append("priceListID", 1)
									    .append("itemObjectType", 1)
									    .append("itemObjectID", 1)
									    .append("effectiveFrom", 1)
									    .append("effectiveTo", 1);
		
		pldcol.ensureIndex(indobj2,"pldu2",true);
		
		
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
	}
	
	public void insert(PriceListDetail pld)
	{
		DBObject pldDBObj = null;
		pld.setDetailID(new ObjectId());
		pld.setTenantID(TenantSession.getTenantID());
		pld.setLastModified(new Date());
		pld.setCreatedOn(new Date());
		
		

		try {
			pldDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(pld));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
	    
		WriteResult wr=pldcol.insert(pldDBObj);
	}
	
	public List<PriceListDetail> findAll() throws JsonParseException, JsonMappingException, IOException
	{
		List<PriceListDetail> pldList = new ArrayList<PriceListDetail>();
		DBCursor cursor = pldcol.find();
		PriceListDetail pld;
		BasicDBObject pldDBObj;
		try {
			   while(cursor.hasNext()) {
				   pldDBObj=(BasicDBObject) cursor.next();
				   pld=mapper.readValue(JSON.serialize(pldDBObj), PriceListDetail.class);
//				   System.out.println(ten.getName());
//				   System.out.println(ten.getDescription());
				   pldList.add(pld);
			   }
			} finally {
			   cursor.close();
			}
		return pldList;
	}
	
	public List<PriceListDetail> findByObject(BasicDBObject searchQuery) throws JsonParseException, JsonMappingException, IOException
	{
		List<PriceListDetail> pldList = new ArrayList<PriceListDetail>();
		DBCursor cursor = pldcol.find(searchQuery);
		BasicDBObject pldDBObj;
		PriceListDetail pld;
		try {
			   while(cursor.hasNext()) {
				   pldDBObj=(BasicDBObject) cursor.next();
				   String serialJson=JSON.serialize(pldDBObj);
				   pld=mapper.readValue(serialJson, PriceListDetail.class);
				   pldList.add(pld);
			   }
			} finally {
			   cursor.close();
			}
		return pldList;
	}
	
	public void update(PriceListDetail pld)
	{
		DBObject pldDBObj=null;
		BasicDBObject searchQuery=null;

		searchQuery = (BasicDBObject) JSON.parse("{\"tenantID\" : { \"$oid\" : \"" + pld.getTenantID() 
				+ "\"} , \"detailID\" : { \"$oid\" : \"" + pld.getDetailID() + "\"}}" );
		
		try {
			pldDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(pld));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
			
		WriteResult wr=pldcol.update(searchQuery,pldDBObj);

		if (wr.getN() == 0 )
		{
			throw new RuntimeException("Update failed");
		}
	}

	public void remove(PriceListDetail pld)
	{
		BasicDBObject searchQuery=null;

		searchQuery = (BasicDBObject) JSON.parse("{\"tenantID\" : { \"$oid\" : \"" + pld.getTenantID() 
				+ "\"} , \"detailID\" : { \"$oid\" : \"" + pld.getDetailID() + "\"}}" );


		WriteResult wr=pldcol.remove(searchQuery);

		if (wr.getN() == 0 )
		{
			throw new RuntimeException("Delete failed");
		}
	}
	
}
