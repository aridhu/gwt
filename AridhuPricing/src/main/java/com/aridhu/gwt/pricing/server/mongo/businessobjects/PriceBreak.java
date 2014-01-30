package com.aridhu.gwt.pricing.server.mongo.businessobjects;

public class PriceBreak {
	private double lineNumber;
	private double volumeFrom;
	private double volumeTo;
	private double listPrice;
	
	
	public double getLineNumber() {
		return lineNumber;
	}
	public double getListPrice() {
		return listPrice;
	}
	
	
	public double getVolumeFrom() {
		return volumeFrom;
	}
	public double getVolumeTo() {
		return volumeTo;
	}
	public void setLineNumber(double lineNumber) {
		this.lineNumber = lineNumber;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	
	public void setVolumeFrom(double volumeFrom) {
		this.volumeFrom = volumeFrom;
	}
	public void setVolumeTo(double volumeTo) {
		this.volumeTo = volumeTo;
	}
	 
}
