package com.cg.patient.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.patient.domain.Laboratory;
import com.cg.patient.domain.Patient;
import com.cg.patient.exception.PatientException;
import com.cg.patient.service.MapValidationErrorService;
import com.cg.patient.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewPatient(@Valid @RequestBody Patient patient, BindingResult result) {
		
		ResponseEntity<?> errorMap =  mapValidationErrorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;
		Patient newPatient = patientService.saveOrUpdate(patient);
		return new ResponseEntity<Patient>(newPatient, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectIdentifier){
		return new ResponseEntity<Patient>( patientService.findPatientByPatientIdentifier(projectIdentifier),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/all", method=RequestMethod.GET)
	private Iterable<Patient> findAll(){
		
		return patientService.findAllPatient();
	}
	
	@RequestMapping(value = "/searchLaboratoryByMedicalTest", method = RequestMethod.POST)
	private ResponseEntity<?> laboratoryAvailability(@RequestBody Map<String, String> searchLaboratoryMap){
		String laboratoryName = searchLaboratoryMap.get("laboratoryName");
		String medicalTestName = searchLaboratoryMap.get("medicalTestName");
		boolean medicalTestAvailablity = patientService.searchLaboratoryByMedicalTest(laboratoryName,medicalTestName);
		if(medicalTestAvailablity) {
			return new ResponseEntity<String>(medicalTestName+" is available in the lab "+laboratoryName,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(medicalTestName+" is not available in the lab "+laboratoryName,HttpStatus.BAD_REQUEST);
		}
		
	}
	@RequestMapping(value = "/bookAppointmentForMedicalTest", method = RequestMethod.POST)
	private ResponseEntity<?> bookAppointmentForMedicalTest(@RequestBody Map<String, String> searchLaboratoryMap){
		String laboratoryName = searchLaboratoryMap.get("laboratoryName");
		String medicalTestName = searchLaboratoryMap.get("medicalTestName");
		boolean medicalTestAppointment = patientService.bookAppointmentForMedicalTest(laboratoryName,medicalTestName);
		if(medicalTestAppointment) {
			return new ResponseEntity<String>("Appointment is booked for the test name "+medicalTestName+" in the laboratory "+laboratoryName,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Appointments are full",HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value = "/uploadMedicalTestHistoryAndReports/{patientIdentifier}", method = RequestMethod.PATCH)
	private ResponseEntity<?> uploadMedicalTestHistoryAndReports(@PathVariable String patientIdentifier, @RequestBody Map<String, String> medicalTestHistoryAndReportsMapFromJson){
		Patient patient = patientService.findPatientByPatientIdentifier(patientIdentifier.toUpperCase());
		if(patient==null) {
			throw new PatientException("Patient with identifier "+patientIdentifier+" not found");
		}
		else {
			if(patient.getMedicalTestHistoryAndReportsMap().isEmpty()) {
				patient.getMedicalTestHistoryAndReportsMap().putAll(medicalTestHistoryAndReportsMapFromJson);
			}
			else
			{
				patient.getMedicalTestHistoryAndReportsMap().put(medicalTestHistoryAndReportsMapFromJson.get("medicalTestName"),medicalTestHistoryAndReportsMapFromJson.get("reports"));
			}
			
		}
		return new ResponseEntity<String>("Medical test history and reports for the patient " + patientIdentifier+" successfylly uploaded", HttpStatus.OK);
	}
	
	@RequestMapping(value= "/viewMedicalTestHistoryAndReports", method = RequestMethod.POST)
	private ResponseEntity<?> viewMedicalTestHistoryAndReports(@RequestBody Map<String,String> testMap ){
		String medicalTestName=testMap.get("medicalTestName");
		List<String> reports = patientService.viewMedicalTestHistoryAndReports(medicalTestName);
		if(reports==null) {
			return new ResponseEntity<String>("No medical test is available",HttpStatus.BAD_REQUEST);
			
		}else
		{
			return new ResponseEntity<List<String>> (reports,HttpStatus.OK);
		}
	}
}

