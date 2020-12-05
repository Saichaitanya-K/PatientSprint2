package com.cg.patient.exception;

public class LaboratoryException extends RuntimeException{
	
	public LaboratoryException() {
		super();
	}
	
	public LaboratoryException(String errMessage) {
		super(errMessage);
	}

}