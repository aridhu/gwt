package com.aridhu.gwt.pricing.server.mongo.businessobjects;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import com.aridhu.gwt.pricing.server.mongo.util.MongoDateSerializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoDateDeserializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoObjectIDSerializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoObjectIDDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Tenant implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private ObjectId tenantID;
	private Date createdOn;
	private Date lastModified;

	@JsonSerialize(using=MongoDateSerializer.class)
	public Date getCreatedOn() {
		return createdOn;
	}

	
	public String getDescription() {
		return description;
	}

	@JsonSerialize(using=MongoDateSerializer.class)
	public Date getLastModified() {
		return lastModified;
	}

	public String getName() {
		return this.name;
	}

	@JsonSerialize(using=MongoObjectIDSerializer.class)
	public ObjectId getTenantID() {
		return tenantID;
	}

	@JsonDeserialize(using=MongoDateDeserializer.class)
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonDeserialize(using=MongoDateDeserializer.class)
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	public void setTenantID(ObjectId tenantID) {
		this.tenantID = tenantID;
	}
	
}
