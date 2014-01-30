package com.aridhu.gwt.pricing.server.mongo.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.aridhu.gwt.pricing.server.mongo.businessobjects.Tenant;
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

public class TenantDao {
	DB db;
	DBCollection tencol;
	ObjectMapper mapper;
	
	public TenantDao() {
		super();
		db = MongoConnection.getDB();
		tencol = db.getCollection("Tenant");
		BasicDBObject indobj1 =new BasicDBObject("tenantID",1);
		tencol.ensureIndex(indobj1,"teu1",true);
		
		BasicDBObject indobj2 =new BasicDBObject("name",1);
		tencol.ensureIndex(indobj2,"teu2",true);
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
	}
	
	public void insert(Tenant ten)
	{
		DBObject tenDBObj = null;
		ten.setTenantID(new ObjectId());
		ten.setLastModified(new Date());
		ten.setCreatedOn(new Date());

		try {
			tenDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(ten));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
	    
		WriteResult wr=tencol.insert(tenDBObj);
	}
	
	public void update(Tenant ten)
	{
		DBObject tenDBObj=null;
		BasicDBObject searchQuery=null;

		searchQuery = (BasicDBObject) JSON.parse("{\"tenantID\" : { \"$oid\" : \"" + ten.getTenantID() + "\"}}" );
		
		try {
			tenDBObj=(DBObject) JSON.parse(mapper.writeValueAsString(ten));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
			
		WriteResult wr=tencol.update(searchQuery,tenDBObj);

		if (wr.getN() == 0 )
		{
			throw new RuntimeException("Update failed");
		}
	}
	
	public void remove(Tenant ten)
	{
		BasicDBObject searchQuery=null;

		searchQuery = (BasicDBObject) JSON.parse("{\"tenantID\" : { \"$oid\" : \"" + ten.getTenantID() + "\"}}" );

		WriteResult wr=tencol.remove(searchQuery);

		if (wr.getN() == 0 )
		{
			throw new RuntimeException("Delete failed");
		}
	}
	
	
	public List<Tenant> findAll() throws JsonParseException, JsonMappingException, IOException
	{
		List<Tenant> tenList = new ArrayList<Tenant>();
		DBCursor cursor = tencol.find();
		Tenant ten;
		BasicDBObject tenDBObj;
		try {
			   while(cursor.hasNext()) {
			       //plhList.add((PriceListHeader)cursor.next());
				   tenDBObj=(BasicDBObject) cursor.next();
				   ten=mapper.readValue(JSON.serialize(tenDBObj), Tenant.class);
//				   System.out.println(ten.getName());
//				   System.out.println(ten.getDescription());
				   tenList.add(ten);
			   }
			} finally {
			   cursor.close();
			}
		return tenList;
	}
	
	public List<Tenant> findByObject(BasicDBObject searchQuery) throws JsonParseException, JsonMappingException, IOException
	{
		List<Tenant> tenList = new ArrayList<Tenant>();
		DBCursor cursor = tencol.find(searchQuery);
		BasicDBObject tenDBObj;
		Tenant ten;
		try {
			   while(cursor.hasNext()) {
				   tenDBObj=(BasicDBObject) cursor.next();
				   String serialJson=JSON.serialize(tenDBObj);
				   ten=mapper.readValue(serialJson, Tenant.class);
				   tenList.add(ten);
			   }
			} finally {
			   cursor.close();
			}
		return tenList;
	}
	
	
}
