package com.app.quantitymeasurement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.model.MeasurementType;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

@Service
public class FetchService {

	@Autowired
	QuantityMeasurementRepository repo;
	
	public List<QuantityMeasurementEntity> getByMeasurementType(MeasurementType measurementType){
		
		List<QuantityMeasurementEntity> result = repo.findByThisMeasurementType(measurementType);
		
		return result;
	}
	
public List<QuantityMeasurementEntity> getByOperationType(OperationType operationType){
		
		List<QuantityMeasurementEntity> result = repo.findByOperation(operationType);
		
		return result;
	}

public List<QuantityMeasurementEntity> getHistory(){
	return repo.findAll();
}
	
}
