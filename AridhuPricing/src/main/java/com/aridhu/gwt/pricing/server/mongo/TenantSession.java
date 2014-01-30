package com.aridhu.gwt.pricing.server.mongo;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import com.aridhu.gwt.pricing.server.mongo.businessobjects.Tenant;
import com.aridhu.gwt.pricing.server.mongo.dao.TenantDao;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class TenantSession {
	private static Tenant ten;
	public  static void connect(String tenantName) {
		//Get Tenant
		TenantDao tenDao=new TenantDao();
		BasicDBObject searchQuery=null;
		searchQuery = (BasicDBObject) JSON.parse("{\"name\" : \"" + tenantName + "\"}" );

		try {
			List<Tenant> tenList = tenDao.findByObject(searchQuery);
			ten= tenList.get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public  static ObjectId getTenantID()	{
		return ten.getTenantID();
	}

}
