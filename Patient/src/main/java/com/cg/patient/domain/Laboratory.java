package com.cg.patient.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Laboratory{
	
	private String laboratoryName;
	private  List<Doctor> doctorList;
	private  List<String> medicalTestList;
	private static int noOfMedicalTestAppointments=5;
	Map<String, List<String>> medicalTestHistory = new HashMap<>();
	
	public Laboratory() {
		super();
	}
	public Laboratory(String laboratoryName, List<Doctor> doctorList, List<String> medicalTestList, 
			List<String> medicalTestName, List<String> reports) {
		super();
		this.laboratoryName = laboratoryName;
		this.doctorList = doctorList;
		this.medicalTestList = medicalTestList;
		for(String testName:medicalTestName) {
			if(!this.medicalTestHistory.containsKey(testName)) {
				this.medicalTestHistory.put(testName, reports);
			}
			else {
				for(String reportGot:reports) {
					this.medicalTestHistory.get(testName).add(reportGot);
				}
			}	
		}
	}

	public String getLaboratoryName() {
		return laboratoryName;
	}

	public void setLaboratoryName(String laboratoryName) {
		this.laboratoryName = laboratoryName;
	}

	public List<Doctor> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(List<Doctor> doctorList) {
		this.doctorList = doctorList;
	}

	public List<String> getMedicalTestList() {
		return medicalTestList;
	}

	public void setMedicalTestList(List<String> medicalTestList) {
		this.medicalTestList = medicalTestList;
	}
	public static int getNoOfMedicalTestAppointments() {
		return noOfMedicalTestAppointments;
	}
	public static void setNoOfMedicalTestAppointments(int noOfMedicalTestAppointments) {
		Laboratory.noOfMedicalTestAppointments = noOfMedicalTestAppointments;
	}
	public Map<String, List<String>> getMedicalTestHistory() {
		return medicalTestHistory;
	}
	public void setMedicalTestHistory(Map<String, List<String>> medicalTestHistory) {
		this.medicalTestHistory = medicalTestHistory;
	}
	@Override
	public String toString() {
		return "Laboratory [laboratoryName=" + laboratoryName + ", doctorList=" + doctorList + ", medicalTestList="
				+ medicalTestList + ", medicalTestHistory=" + medicalTestHistory + "]";
	}
	
	
}
