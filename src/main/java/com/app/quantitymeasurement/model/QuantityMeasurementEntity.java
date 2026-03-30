package com.app.quantitymeasurement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="quantity_measurement_entity", indexes = {
		@Index(name="idx_operation", columnList="operation"),
		@Index(name="idx_measurement_type", columnList = "this_measurement_type"),
		@Index(name="idx_created_at", columnList="created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="this_value", nullable=false)
	public Double thisValue;
	
	
	@Column(name="this_unit", nullable=false)
	@Enumerated(EnumType.STRING)
	public Unit thisUnit;
	
	@Column(name="this_measurement_type", nullable=false)
	@Enumerated(EnumType.STRING)
	public MeasurementType thisMeasurementType;
	
	@Column(name="that_value", nullable=true)
	public Double thatValue;
	
	
	@Column(name="that_unit", nullable=true)
	@Enumerated(EnumType.STRING)
	public Unit thatUnit;
	
	
	@Column(name="that_measurement_type", nullable=true)
	@Enumerated(EnumType.STRING)
	public MeasurementType thatMeasurementType;
	
	
	@Column(name="operation")
	@Enumerated(EnumType.STRING)
	public OperationType operation;
	
	@Column(name="result_value")
	public Double resultValue;
	
	
	@Column(name="result_unit")
	@Enumerated(EnumType.STRING)
	public Unit resultUnit;
	
	@Column(name="result_measurement_type")
	@Enumerated(EnumType.STRING)
	public MeasurementType resultMeasurementType;
	
	
	@Column(name = "result_string")
	public String resultString;
	
	@Column(name = "is_error")
	public boolean isError;
	
	@Column(name = "error_message")
	public String errorMessage;
	
	@Column(name = "created_at", nullable=false, updatable=false)
	public LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	public LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}



	public QuantityMeasurementEntity() {
	}

	public QuantityMeasurementEntity(double thisValue, Unit thisUnit, MeasurementType thisMeasurementType,
			double thatValue, Unit thatUnit, MeasurementType thatMeasurementType, OperationType operation,
			double resultValue, Unit resultUnit, MeasurementType resultMeasurementType, String resultString) {
		super();
		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;
		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;
		this.operation = operation;
		this.resultValue = resultValue;
		this.resultUnit = resultUnit;
		this.resultMeasurementType = resultMeasurementType;
		this.resultString = resultString;
	}

	public Double getThisValue() {
		return thisValue;
	}

	public void setThisValue(double thisValue) {
		this.thisValue = thisValue;
	}

	public Unit getThisUnit() {
		return thisUnit;
	}

	public void setThisUnit(Unit thisUnit) {
		this.thisUnit = thisUnit;
	}

	public MeasurementType getThisMeasurementType() {
		return thisMeasurementType;
	}

	public void setThisMeasurementType(MeasurementType thisMeasurementType) {
		this.thisMeasurementType = thisMeasurementType;
	}

	public Double getThatValue() {
		return thatValue;
	}

	public void setThatValue(double thatValue) {
		this.thatValue = thatValue;
	}

	public Unit getThatUnit() {
		return thatUnit;
	}

	public void setThatUnit(Unit thatUnit) {
		this.thatUnit = thatUnit;
	}

	public MeasurementType getThatMeasurementType() {
		return thatMeasurementType;
	}

	public void setThatMeasurementType(MeasurementType thatMeasurementType) {
		this.thatMeasurementType = thatMeasurementType;
	}

	public OperationType getOperation() {
		return operation;
	}

	public void setOperation(OperationType operation) {
		this.operation = operation;
	}

	public Double getResultValue() {
		return resultValue;
	}

	public void setResultValue(double resultValue) {
		this.resultValue = resultValue;
	}

	public Unit getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(Unit resultUnit) {
		this.resultUnit = resultUnit;
	}

	public MeasurementType getResultMeasurementType() {
		return resultMeasurementType;
	}

	public void setResultMeasurementType(MeasurementType resultMeasurementType) {
		this.resultMeasurementType = resultMeasurementType;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public QuantityMeasurementEntity(double thisValue, Unit thisUnit, MeasurementType thisMeasurementType,
			double thatValue, Unit thatUnit, MeasurementType thatMeasurementType, OperationType operation,
			double resultValue, Unit resultUnit, MeasurementType resultMeasurementType, String resultString,
			boolean isError, String errorMessage) {
		super();
		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;
		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;
		this.operation = operation;
		this.resultValue = resultValue;
		this.resultUnit = resultUnit;
		this.resultMeasurementType = resultMeasurementType;
		this.resultString = resultString;
		this.isError = isError;
		this.errorMessage = errorMessage;
	}

	public QuantityMeasurementEntity(double thisValue, Unit thisUnit, MeasurementType thisMeasurementType,
			double thatValue, Unit thatUnit, MeasurementType thatMeasurementType,OperationType operation, String resultString) {
		super();
		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;
		this.operation = operation;
		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;
		this.resultString = resultString;
	}

	public QuantityMeasurementEntity(Double thisValue, Unit thisUnit, MeasurementType thisMeasurementType,
			OperationType operation, Double resultValue, Unit resultUnit, MeasurementType resultMeasurementType,
			String resultString) {
		super();
		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;
		this.operation = operation;
		this.resultValue = resultValue;
		this.resultUnit = resultUnit;
		this.resultMeasurementType = resultMeasurementType;
		this.resultString = resultString;
	}


}
