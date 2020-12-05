package com.cg.patient.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.cg.patient.domain.Doctor;
import com.cg.patient.domain.Laboratory;
import com.cg.patient.domain.Patient;
import com.cg.patient.exception.LaboratoryException;
import com.cg.patient.exception.PatientException;
import com.cg.patient.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	PatientRepository repository;
	
	Doctor doctor = new Doctor("fgh","ent",3427852452L);
	List<Doctor> doctorList = Arrays.asList(new Doctor[]{doctor});
	List<String> medicalTestList = Arrays.asList(new String[] {"Blood test","X-ray"});
	static List<String> reports = Arrays.asList(new String[] {"Blood report","x-ray report"});
	Laboratory laboratory = new Laboratory("abc",doctorList,medicalTestList,medicalTestList,reports);
	Map<String,String> medicalTestHistoryAndReports;
	public Patient saveOrUpdate(Patient patient) {

		try {
			patient.setPatientIdentifier(patient.getPatientIdentifier().toUpperCase());
			return repository.save(patient);
		} catch (Exception e) {
			throw new PatientException("PatientIdentifier " + patient.getPatientIdentifier() + " already available");
		}

	}

	public Patient findPatientByPatientIdentifier(String patientIdentifier) {
		Patient patient = repository.findByPatientIdentifier(patientIdentifier.toUpperCase());
		if (patient == null) {
			throw new PatientException("PatientIdentifier " + patientIdentifier + " not available");
			
		}
		return patient;

	}
	
	public Iterable<Patient> findAllPatient(){
		return repository.findAll();
		
	}
	
	public void deletePatientByPatientIdentifier(String patientIdentifier) {
		Patient patient=findPatientByPatientIdentifier(patientIdentifier.toUpperCase());
		if(patient==null) {
			throw new PatientException("ProjectIdentifier " + patientIdentifier + " not available");
		}
		repository.delete(patient);
	}
	public boolean searchLaboratoryByMedicalTest(String laboratoryName, String medicalTestName) {
		if(laboratory.getLaboratoryName().equalsIgnoreCase(laboratoryName)) {
			for(String testName:laboratory.getMedicalTestList()) {
				if(testName.equalsIgnoreCase(medicalTestName))
					return true;
			}	
		}
		return false;
	}
	public boolean bookAppointmentForMedicalTest(String laboratoryName, String medicalTestName) {
		try {
			if(laboratory.getLaboratoryName().equalsIgnoreCase(laboratoryName)) {
				for(String testName:laboratory.getMedicalTestList()) {
					if(testName.equalsIgnoreCase(medicalTestName)) {
						if(laboratory.getNoOfMedicalTestAppointments()!=0) {
							laboratory.setNoOfMedicalTestAppointments(laboratory.getNoOfMedicalTestAppointments()-1);
							return true;
						}
					}
				}
				return false;
			}
			else {
				throw new LaboratoryException("Laboratory name is not available");
			}
		}
		catch(LaboratoryException e) {
			throw e;
		}
	}
	public List<String> viewMedicalTestHistoryAndReports(String medicalTestName) {
		Map<String,List<String>> rep=laboratory.getMedicalTestHistory();
		if(rep.containsKey(medicalTestName)) {
			return rep.get(medicalTestName);
		}
		else {
			return null;
		}
	}
}
