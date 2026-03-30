package com.app.quantitymeasurement.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.MeasurementType;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityComparisonDTO;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.Unit;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import java.util.*;

@Service
public class QuantityMeasurementService {
	
	private static Logger logger = LoggerFactory.getLogger(QuantityMeasurementService.class);
	
	@Autowired 
	QuantityMeasurementRepository repo;
	
	@Autowired
	FetchService fetchService;
//	public QuantityDTO addOperation(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
//	    Unit thisUnit = thisQuantity.getUnit();
//	    Unit thatUnit = thatQuantity.getUnit();
//
//	    double baseThisValue = thisUnit.toBase(thisQuantity.getValue());
//	    double baseThatValue = thatUnit.toBase(thatQuantity.getValue());
//
//	    double resultValueInBase = baseThisValue + baseThatValue;
//
//	    double resultValue = resultValueInBase / thisUnit.toBase(1.0);
//
//	    QuantityDTO result = new QuantityDTO(resultValue, thisUnit, thisQuantity.getMeasurementType());
//
//	    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
//	        thisQuantity.getValue(), thisUnit, thisQuantity.getMeasurementType(),
//	        thatQuantity.getValue(), thatUnit, thatQuantity.getMeasurementType(),
//	        OperationType.ADD, resultValue, thisUnit,
//	        thisQuantity.getMeasurementType(), "Resulting value is " + resultValue
//	    );
//
//	    repo.save(entity);
//	    return result;
//	}
	
	public QuantityDTO addOperationToType(QuantityDTO thisQuantity, QuantityDTO thatQuantity, Unit resultUnit) {
	    if (thisQuantity == null || thatQuantity == null) {
	        throw new QuantityMeasurementException("Both quantities must be provided");
	    }
	    if (thisQuantity.getMeasurementType() != thatQuantity.getMeasurementType()) {
	        throw new QuantityMeasurementException(
	            "Addition not allowed between " + thisQuantity.getMeasurementType() +
	            " and " + thatQuantity.getMeasurementType()
	        );
	    }
	    if (resultUnit != null && resultUnit.getType() != thisQuantity.getMeasurementType()) {
	        throw new QuantityMeasurementException(
	            "Invalid result unit: " + resultUnit + " does not belong to measurement type " + thisQuantity.getMeasurementType()
	        );
	    }
	    if (thisQuantity.getMeasurementType() == MeasurementType.TEMPERATURE) {
	        throw new QuantityMeasurementException("Arithmetic operations are not supported for temperature values");
	    }

	    Unit thisUnit = thisQuantity.getUnit();
	    Unit thatUnit = thatQuantity.getUnit();

	    double baseThisValue = thisUnit.toBase(thisQuantity.getValue());
	    double baseThatValue = thatUnit.toBase(thatQuantity.getValue());

	    double resultValueInBase = baseThisValue + baseThatValue;

	    Unit finalUnit;
	    double resultValue;

	    if (resultUnit == null) {
	        switch (thisQuantity.getMeasurementType()) {
	            case LENGTH: finalUnit = Unit.METER; break;
	            case WEIGHT: finalUnit = Unit.KILOGRAM; break;
	            case VOLUME: finalUnit = Unit.LITER; break;
	            case TEMPERATURE: finalUnit = Unit.CELSIUS; break;
	            default: throw new QuantityMeasurementException("Unsupported measurement type");
	        }
	        resultValue = resultValueInBase / finalUnit.toBase(1.0);
	    } else {
	        finalUnit = resultUnit;
	        resultValue = resultValueInBase / resultUnit.toBase(1.0);
	    }

	    QuantityDTO result = new QuantityDTO(resultValue, finalUnit, thisQuantity.getMeasurementType());

	    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
	        thisQuantity.getValue(), thisUnit, thisQuantity.getMeasurementType(),
	        thatQuantity.getValue(), thatUnit, thatQuantity.getMeasurementType(),
	        OperationType.ADD, resultValue, finalUnit,
	        thisQuantity.getMeasurementType(), "Resulting value is " + resultValue
	    );

	    repo.save(entity);
	    logger.info("Saved Entity to Database from Addition Arithmetic operation");
	    return result;
	}


	
	public QuantityDTO subtractOperationToType(QuantityDTO thisQuantity, QuantityDTO thatQuantity, Unit resultUnit) {
		if (thisQuantity == null || thatQuantity == null) {
	        throw new QuantityMeasurementException("Both quantities must be provided");
	    }
		
		if (thisQuantity.getMeasurementType() != thatQuantity.getMeasurementType()) {
	        throw new QuantityMeasurementException(
	            "Subtraction not allowed between " + thisQuantity.getMeasurementType() +
	            " and " + thatQuantity.getMeasurementType()
	        );
	    }
		if (resultUnit != null && resultUnit.getType() != thisQuantity.getMeasurementType()) {
	        throw new QuantityMeasurementException(
	            "Invalid result unit: " + resultUnit + " does not belong to measurement type " + thisQuantity.getMeasurementType()
	        );
	    }
		if (thisQuantity.getMeasurementType() == MeasurementType.TEMPERATURE) {
		    throw new QuantityMeasurementException("Arithmetic operations are not supported for temperature values");
		}
		Unit thisUnit = thisQuantity.getUnit();
	    Unit thatUnit = thatQuantity.getUnit();

	    double baseThisValue = thisUnit.toBase(thisQuantity.getValue());
	    double baseThatValue = thatUnit.toBase(thatQuantity.getValue());

	    double resultValueInBase = baseThisValue - baseThatValue;

	    Unit finalUnit = (resultUnit == null) ? getDefaultUnit(thisQuantity.getMeasurementType()) : resultUnit;
	    double resultValue = convertFromBase(resultValueInBase, finalUnit);

	    QuantityDTO result = new QuantityDTO(resultValue, finalUnit, thisQuantity.getMeasurementType());

	    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
	        thisQuantity.getValue(), thisUnit, thisQuantity.getMeasurementType(),
	        thatQuantity.getValue(), thatUnit, thatQuantity.getMeasurementType(),
	        OperationType.SUBTRACT, resultValue, finalUnit,
	        thisQuantity.getMeasurementType(), "Resulting value is " + resultValue
	    );

	    repo.save(entity);
	    logger.info("Saved Entity to Database from Subtraction Arithmetic operation");
	    return result;
	}

	public QuantityDTO multiplyOperationToType(QuantityDTO thisQuantity, QuantityDTO thatQuantity, Unit resultUnit) {
		if (thisQuantity == null || thatQuantity == null) {
	        throw new QuantityMeasurementException("Both quantities must be provided");
	    }
		
		if (thisQuantity.getMeasurementType() != thatQuantity.getMeasurementType()) {
	        throw new QuantityMeasurementException(
	            "Multiplication not allowed between " + thisQuantity.getMeasurementType() +
	            " and " + thatQuantity.getMeasurementType()
	        );
	    }
		if (resultUnit != null && resultUnit.getType() != thisQuantity.getMeasurementType()) {
	        throw new QuantityMeasurementException(
	            "Invalid result unit: " + resultUnit + " does not belong to measurement type " + thisQuantity.getMeasurementType()
	        );
	    }
		if (thisQuantity.getMeasurementType() == MeasurementType.TEMPERATURE) {
		    throw new QuantityMeasurementException("Arithmetic operations are not supported for temperature values");
		}
		
		Unit thisUnit = thisQuantity.getUnit();
	    Unit thatUnit = thatQuantity.getUnit();

	    double baseThisValue = thisUnit.toBase(thisQuantity.getValue());
	    double baseThatValue = thatUnit.toBase(thatQuantity.getValue());

	    double resultValueInBase = baseThisValue * baseThatValue;

	    Unit finalUnit = (resultUnit == null) ? getDefaultUnit(thisQuantity.getMeasurementType()) : resultUnit;
	    double resultValue = convertFromBase(resultValueInBase, finalUnit);

	    QuantityDTO result = new QuantityDTO(resultValue, finalUnit, thisQuantity.getMeasurementType());

	    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
	        thisQuantity.getValue(), thisUnit, thisQuantity.getMeasurementType(),
	        thatQuantity.getValue(), thatUnit, thatQuantity.getMeasurementType(),
	        OperationType.MULTIPLY, resultValue, finalUnit,
	        thisQuantity.getMeasurementType(), "Resulting value is " + resultValue
	    );

	    repo.save(entity);
	    logger.info("Saved Entity to Database from Multiplication Arithmetic operation");
	    return result;
	}

	public QuantityDTO divideOperationToType(QuantityDTO thisQuantity, QuantityDTO thatQuantity, Unit resultUnit) {
    if (thisQuantity == null || thatQuantity == null) {
        throw new QuantityMeasurementException("Both quantities must be provided");
    }
    
    if (thisQuantity.getMeasurementType() != thatQuantity.getMeasurementType()) {
        throw new QuantityMeasurementException(
            "Division not allowed between " + thisQuantity.getMeasurementType() +
            " and " + thatQuantity.getMeasurementType()
        );
    }
    if (resultUnit != null && resultUnit.getType() != thisQuantity.getMeasurementType()) {
        throw new QuantityMeasurementException(
            "Invalid result unit: " + resultUnit + " does not belong to measurement type " + thisQuantity.getMeasurementType()
        );
    }
    if (thisQuantity.getMeasurementType() == MeasurementType.TEMPERATURE) {
        throw new QuantityMeasurementException("Arithmetic operations are not supported for temperature values");
    }

    Unit thisUnit = thisQuantity.getUnit();
    Unit thatUnit = thatQuantity.getUnit();

    double baseThisValue = thisUnit.toBase(thisQuantity.getValue());
    double baseThatValue = thatUnit.toBase(thatQuantity.getValue());

    if (baseThatValue == 0) {
        throw new QuantityMeasurementException("Division by zero is not allowed");
    }

    double resultValueInBase = baseThisValue / baseThatValue;
    Unit finalUnit = (resultUnit == null) ? getDefaultUnit(thisQuantity.getMeasurementType()) : resultUnit;
    double resultValue = convertFromBase(resultValueInBase, finalUnit);

    QuantityDTO result = new QuantityDTO(resultValue, finalUnit, thisQuantity.getMeasurementType());

    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
        thisQuantity.getValue(), thisUnit, thisQuantity.getMeasurementType(),
        thatQuantity.getValue(), thatUnit, thatQuantity.getMeasurementType(),
        OperationType.DIVIDE, resultValue, finalUnit,
        thisQuantity.getMeasurementType(), "Resulting value is " + resultValue
    );

    repo.save(entity);
    logger.info("Saved Entity to Database from Division Arithmetic operation");
    return result;
}


	
	
	private Unit getDefaultUnit(MeasurementType type) {
	    switch (type) {
	        case LENGTH: return Unit.METER;
	        case WEIGHT: return Unit.KILOGRAM;
	        case VOLUME: return Unit.LITER;
	        case TEMPERATURE: return Unit.CELSIUS; 
	        default: throw new IllegalArgumentException("Unsupported measurement type");
	    }
	}

	private double convertFromBase(double valueInBase, Unit targetUnit) {
	    return valueInBase / targetUnit.toBase(1.0);
	}

	
	public QuantityComparisonDTO compareOperation(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
    if (thisQuantity.getMeasurementType() != thatQuantity.getMeasurementType()) {
        throw new QuantityMeasurementException(
            "Comparison not allowed between " + thisQuantity.getMeasurementType() +
            " and " + thatQuantity.getMeasurementType()
        );
    }

    Unit thisUnit = thisQuantity.getUnit();
    Unit thatUnit = thatQuantity.getUnit();

    double baseThisValue = thisUnit.toBase(thisQuantity.getValue());
    double baseThatValue = thatUnit.toBase(thatQuantity.getValue());

    String comparisonResult;
    if (Math.abs(baseThisValue - baseThatValue) < 1e-9) {
        comparisonResult = "EQUAL";
    } else if (baseThisValue > baseThatValue) {
        comparisonResult = "GREATER";
    } else {
        comparisonResult = "LESSER";
    }

    QuantityComparisonDTO result = new QuantityComparisonDTO(thisQuantity, thatQuantity, comparisonResult);

    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
        thisQuantity.getValue(), thisUnit, thisQuantity.getMeasurementType(),
        thatQuantity.getValue(), thatUnit, thatQuantity.getMeasurementType(),
        OperationType.COMPARE, result.getResult()
    );

    repo.save(entity);
    logger.info("Saved Entity to Database from Compare Operation");
    return result;
}


	public QuantityDTO convertOperation(QuantityDTO inputQuantity, Unit targetUnit) {
    if (inputQuantity == null || targetUnit == null) {
        throw new QuantityMeasurementException("Input quantity and target unit must be provided");
    }

    if (targetUnit.getType() != inputQuantity.getMeasurementType()) {
        throw new QuantityMeasurementException(
            "Conversion not allowed: " + inputQuantity.getMeasurementType() +
            " cannot be converted to " + targetUnit.getType()
        );
    }

    Unit sourceUnit = inputQuantity.getUnit();

    // Convert to base (Celsius for temperature, factor for others)
    double baseValue = sourceUnit.toBase(inputQuantity.getValue());

    // Convert from base to target
    double convertedValue = targetUnit.fromBase(baseValue);

    QuantityDTO result = new QuantityDTO(convertedValue, targetUnit, inputQuantity.getMeasurementType());

    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
        inputQuantity.getValue(), sourceUnit, inputQuantity.getMeasurementType(),
        OperationType.CONVERT, convertedValue, targetUnit,
        inputQuantity.getMeasurementType(), "Converted value is " + convertedValue
    );

    repo.save(entity);
    logger.info("Saved Entity to Database from Convert Operation");
    return result;
}


	public List<QuantityMeasurementEntity> fetchHistoryByMeasurementType(MeasurementType measurementType){

		return fetchService.getByMeasurementType(measurementType);
	}
	
	public List<QuantityMeasurementEntity> fetchHistoryByOperationType(OperationType operationType){

		return fetchService.getByOperationType(operationType);
	}
	
	public List<QuantityMeasurementEntity> fetchHistory(){
		return fetchService.getHistory();
	}
	
}
