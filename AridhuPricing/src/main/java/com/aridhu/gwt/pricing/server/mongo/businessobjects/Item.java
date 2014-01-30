package com.aridhu.gwt.pricing.server.mongo.businessobjects;

import java.util.Date;

import org.bson.types.ObjectId;
import com.aridhu.gwt.pricing.server.mongo.util.MongoDateSerializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoDateDeserializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoObjectIDSerializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoObjectIDDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Item {
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	@JsonSerialize(using=MongoObjectIDSerializer.class)
	private ObjectId tenantID;
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	@JsonSerialize(using=MongoObjectIDSerializer.class)
	private ObjectId itemID;
	
	private String itemName;
	private String itemDescription;
	
	@JsonDeserialize(using=MongoDateDeserializer.class)
	@JsonSerialize(using=MongoDateSerializer.class)
	private Date createdOn;
	@JsonDeserialize(using=MongoDateDeserializer.class)
	@JsonSerialize(using=MongoDateSerializer.class)
	private Date lastModified;
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public ObjectId getItemID() {
		return itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public ObjectId getTenantID() {
			return tenantID;
		}
public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
}
public void setItemDescription(String itemDescription) {
	this.itemDescription = itemDescription;
}
public void setItemID(ObjectId itemID) {
	this.itemID = itemID;
}
public void setItemName(String itemName) {
	this.itemName = itemName;
}
public void setLastModified(Date lastModified) {
	this.lastModified = lastModified;
}
public void setTenantID(ObjectId tenantID) {
	this.tenantID = tenantID;
}

}
