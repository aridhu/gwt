package com.aridhu.gwt.pricing.server.mongo.businessobjects;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import com.aridhu.gwt.pricing.server.mongo.util.MongoDateSerializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoDateDeserializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoObjectIDSerializer;
import com.aridhu.gwt.pricing.server.mongo.util.MongoObjectIDDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PriceListDetail {
	
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	@JsonSerialize(using=MongoObjectIDSerializer.class)
	private ObjectId tenantID;
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	@JsonSerialize(using=MongoObjectIDSerializer.class)
	private ObjectId detailID;
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	@JsonSerialize(using=MongoObjectIDSerializer.class)
	private ObjectId priceListID;
	
	
	
	@JsonDeserialize(using=MongoObjectIDDeserializer.class)
	@JsonSerialize(using=MongoObjectIDSerializer.class)
	private ObjectId itemObjectID;
	
	private String itemObjectType;
	private double listPrice;
	private List<PriceBreak> priceBreakList;
	private String uomCode;
	
	@JsonDeserialize(using=MongoDateDeserializer.class)
	@JsonSerialize(using=MongoDateSerializer.class)
	private Date effectiveFrom;
	@JsonDeserialize(using=MongoDateDeserializer.class)
	@JsonSerialize(using=MongoDateSerializer.class)
	private Date effectiveTo;
	@JsonDeserialize(using=MongoDateDeserializer.class)
	@JsonSerialize(using=MongoDateSerializer.class)
	private Date createdOn;
	@JsonDeserialize(using=MongoDateDeserializer.class)
	@JsonSerialize(using=MongoDateSerializer.class)
	private Date lastModified;
	
	
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public ObjectId getDetailID() {
		return detailID;
	}
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}
	public Date getEffectiveTo() {
		return effectiveTo;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	public double getListPrice() {
		return listPrice;
	}
	public ObjectId getPriceListID() {
		return priceListID;
	}
	
	public ObjectId getTenantID() {
		return tenantID;
	}
	
	public String getUomCode() {
		return uomCode;
	}
	
	public String getItemObjectType() {
		return itemObjectType;
	}
	public ObjectId getItemObjectID() {
		return itemObjectID;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setDetailID(ObjectId detailID) {
		this.detailID = detailID;
	}
	
	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	
	public void setEffectiveTo(Date effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	
	
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	
	public void setPriceListID(ObjectId priceListID) {
		this.priceListID = priceListID;
	}
	
	public void setTenantID(ObjectId tenantID) {
		this.tenantID = tenantID;
	}
	
	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	public void setItemObjectType(String itemObjectType) {
		this.itemObjectType = itemObjectType;
	}
	
	
	public void setItemObjectID(ObjectId itemObjectID) {
		this.itemObjectID = itemObjectID;
	}
	public List<PriceBreak> getPriceBreakList() {
		return priceBreakList;
	}
	public void setPriceBreakList(List<PriceBreak> priceBreakList) {
		this.priceBreakList = priceBreakList;
	}

}
