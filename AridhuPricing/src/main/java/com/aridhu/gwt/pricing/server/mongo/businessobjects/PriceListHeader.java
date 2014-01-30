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

//@JsonFilter("myFilter")
public class PriceListHeader implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private ObjectId tenantID;
	private ObjectId priceListID;
	private String name;
	private String	description;
	
	private String activeFlag;
	private String currencyCode;
	private Date effectiveFrom;
	
	private Date effectiveTo;
	private Date createdOn;
	private Date lastModified;
	
	public String getActiveFlag() {
		return activeFlag;
	}
	
	@JsonSerialize(using=MongoDateSerializer.class)
	public Date getCreatedOn() {
		return createdOn;
	}

		
	public String getCurrencyCode() {
		return currencyCode;
	}
	public String getDescription() {
		return description;
	}
	
	
	@JsonSerialize(using=MongoDateSerializer.class)
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}
	
	@JsonSerialize(using=MongoDateSerializer.class)
	public Date getEffectiveTo() {
		return effectiveTo;
	}
	
	@JsonSerialize(using=MongoDateSerializer.class)
	public Date getLastModified() {
		return lastModified;
	}
	
	public String getName() {
		return name;
	}
	
	@JsonSerialize(using=MongoObjectIDSerializer.class)
	public ObjectId getPriceListID() {
		return priceListID;
	}
	
	@JsonSerialize(using=MongoObjectIDSerializer.class)
	public ObjectId getTenantID() {
		return tenantID;
	}
	
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	@JsonDeserialize(using=MongoDateDeserializer.class)
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonDeserialize(using=MongoDateDeserializer.class)
	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	
	@JsonDeserialize(using=MongoDateDeserializer.class)
	public void setEffectiveTo(Date effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	
	@JsonDeserialize(using=MongoDateDeserializer.class)
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	public void setPriceListID(ObjectId priceListID) {
		this.priceListID = priceListID;
	}
	
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	public void setTenantID(ObjectId tenantID) {
		this.tenantID = tenantID;
	}

	
}
