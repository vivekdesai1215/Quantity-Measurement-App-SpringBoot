package com.app.quantitymeasurement.model;

public class QuantityInputDTO {

	private QuantityDTO thisQuantityDTO;
	private QuantityDTO thatQuantityDTO;
		
	public QuantityInputDTO(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		this.thisQuantityDTO = thisDTO;
		this.thatQuantityDTO = thatDTO;
	}

	public QuantityDTO getThisQuantityDTO() {
		return thisQuantityDTO;
	}

	public void setThisQuantityDTO(QuantityDTO thisQuantityDTO) {
		this.thisQuantityDTO = thisQuantityDTO;
	}

	public QuantityDTO getThatQuantityDTO() {
		return thatQuantityDTO;
	}

	public void setThatQuantityDTO(QuantityDTO thatQuantityDTO) {
		this.thatQuantityDTO = thatQuantityDTO;
	}	
}
