package com.app.quantitymeasurement.model;

public class QuantityDTO {
	
	private double value;
	
	private Unit unit;
	
	private MeasurementType measurementType;

	public QuantityDTO(double value, Unit unit, MeasurementType measurementType) {
		super();
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public MeasurementType getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}

}
