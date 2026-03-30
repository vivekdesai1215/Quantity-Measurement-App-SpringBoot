package com.app.quantitymeasurement.model;

public class QuantityComparisonDTO {
	 private QuantityDTO thisQuantity;
	 private QuantityDTO thatQuantity;
	 private String result; // "EQUAL", "GREATER", "LESS"
	 
	 
	 public QuantityComparisonDTO(QuantityDTO thisQuantity, QuantityDTO thatQuantity, String result) {
		super();
		this.thisQuantity = thisQuantity;
		this.thatQuantity = thatQuantity;
		this.result = result;
	 }


	 public QuantityDTO getThisQuantity() {
		 return thisQuantity;
	 }


	 public void setThisQuantity(QuantityDTO thisQuantity) {
		 this.thisQuantity = thisQuantity;
	 }


	 public QuantityDTO getThatQuantity() {
		 return thatQuantity;
	 }


	 public void setThatQuantity(QuantityDTO thatQuantity) {
		 this.thatQuantity = thatQuantity;
	 }


	 public String getResult() {
		 return result;
	 }


	 public void setResult(String result) {
		 this.result = result;
	 }
	 
	 
	 
}
