package com.app.quantitymeasurement.model;

public enum OperationType {

	ADD,
	SUBTRACT,
	DIVIDE,
	MULTIPLY,
	COMPARE,
	CONVERT;
	
	public String getDisplayName() {
		return this.name().toLowerCase();
	}
}
