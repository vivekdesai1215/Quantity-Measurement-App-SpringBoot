package com.app.quantitymeasurement.controller;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import com.app.quantitymeasurement.model.MeasurementType;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityComparisonDTO;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.Unit;
import com.app.quantitymeasurement.service.QuantityMeasurementService;

@RestController
public class QuantityMeasurementController {

		@Autowired
		QuantityMeasurementService qtyService;
		
		@PostMapping("/add/resultUnit")
		public ResponseEntity<QuantityDTO> addOpWithUnit(
		        @RequestBody QuantityInputDTO quantityInputDTO,
		        @RequestParam(required = false) Unit resultUnit) {

		    QuantityDTO thisQuantity = quantityInputDTO.getThisQuantityDTO();
		    QuantityDTO thatQuantity = quantityInputDTO.getThatQuantityDTO();

		    // Delegate to service
		    QuantityDTO result = qtyService.addOperationToType(thisQuantity, thatQuantity, resultUnit);

		    return ResponseEntity.ok(result);
		}
		
		@PostMapping("/subtract/resultUnit")
	    public ResponseEntity<QuantityDTO> subtractOpWithUnit(
	            @RequestBody QuantityInputDTO quantityInputDTO,
	            @RequestParam(required = false) Unit resultUnit) {

	        QuantityDTO thisQuantity = quantityInputDTO.getThisQuantityDTO();
	        QuantityDTO thatQuantity = quantityInputDTO.getThatQuantityDTO();

	        QuantityDTO result = qtyService.subtractOperationToType(thisQuantity, thatQuantity, resultUnit);
	        return ResponseEntity.ok(result);
	    }

	    @PostMapping("/multiply/resultUnit")
	    public ResponseEntity<QuantityDTO> multiplyOpWithUnit(
	            @RequestBody QuantityInputDTO quantityInputDTO,
	            @RequestParam(required = false) Unit resultUnit) {

	        QuantityDTO thisQuantity = quantityInputDTO.getThisQuantityDTO();
	        QuantityDTO thatQuantity = quantityInputDTO.getThatQuantityDTO();

	        QuantityDTO result = qtyService.multiplyOperationToType(thisQuantity, thatQuantity, resultUnit);
	        return ResponseEntity.ok(result);
	    }

	    @PostMapping("/divide/resultUnit")
	    public ResponseEntity<QuantityDTO> divideOpWithUnit(
	            @RequestBody QuantityInputDTO quantityInputDTO,
	            @RequestParam(required = false) Unit resultUnit) {

	        QuantityDTO thisQuantity = quantityInputDTO.getThisQuantityDTO();
	        QuantityDTO thatQuantity = quantityInputDTO.getThatQuantityDTO();

	        QuantityDTO result = qtyService.divideOperationToType(thisQuantity, thatQuantity, resultUnit);
	        return ResponseEntity.ok(result);
	    }

	    @PostMapping("/compare")
	    public ResponseEntity<QuantityComparisonDTO> compareValues(@RequestBody QuantityInputDTO quantityInputDTO){
	    	
	    	 QuantityDTO thisQuantity = quantityInputDTO.getThisQuantityDTO();
		     QuantityDTO thatQuantity = quantityInputDTO.getThatQuantityDTO();
		     
		     QuantityComparisonDTO result = qtyService.compareOperation(thisQuantity, thatQuantity);
		     
		     return ResponseEntity.ok(result);
	    }
	    
	    @PostMapping("/convert")
	    public ResponseEntity<QuantityDTO> convertOp(
	            @RequestBody QuantityDTO inputQuantity,
	            @RequestParam Unit targetUnit) {

	        QuantityDTO result = qtyService.convertOperation(inputQuantity, targetUnit);
	        return ResponseEntity.ok(result);
	    }
	    
	    @GetMapping("/history/measurementType")
	    public ResponseEntity<List<QuantityMeasurementEntity>> fetchByMeasurementType(@RequestParam MeasurementType measurementType){
	    	List<QuantityMeasurementEntity> result = qtyService.fetchHistoryByMeasurementType(measurementType);
	    	
	    	return ResponseEntity.ok(result);
	    }
	    
	    @GetMapping("/history/operationType")
	    public ResponseEntity<List<QuantityMeasurementEntity>> fetchByOperationType(@RequestParam OperationType operationType){
	    	List<QuantityMeasurementEntity> result = qtyService.fetchHistoryByOperationType(operationType);
	    	
	    	return ResponseEntity.ok(result);
	    }
	    
	    @GetMapping("/history")
	    public ResponseEntity<List<QuantityMeasurementEntity>> fetchHistory(){
	    	List<QuantityMeasurementEntity> result = qtyService.fetchHistory();
	    	
	    	return ResponseEntity.ok(result);
	    }
	    	 	    
}
