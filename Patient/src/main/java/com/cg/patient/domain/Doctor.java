package com.cg.patient.domain;

//@Entity
public class Doctor{
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	//private long doctorId;
	private String doctorName;
	private String specialization;
	//private String department;
	private long doctorPhNo;
	
	/**
	 * Create an instance of doctor with given parameters.
	 * @param doctorId
	 * @param doctorName
	 * @param specialization
	 * @param department
	 * @param doctorPhNo
	 *  
	 */
	public Doctor(String doctorName, String specialization, long doctorPhNo) {
		super();
		
		this.doctorName = doctorName;
		this.specialization = specialization;
		//this.department = department;
		this.doctorPhNo = doctorPhNo;
	}
	
	public Doctor()
	{
		super();
	}
	/**
	 * @return the doctor id present in the database. 
	 */

//	public long getDoctorId() {
//		return doctorId;
//	}
//	/**
//	 * set the id of the doctor.
//	 * @param doctorId
//	 */
//
//	public void setDoctorId(long doctorId) {
//		this.doctorId = doctorId;
//	}
	/**
	 * @return the doctor name present in the database.
	 */

	public String getDoctorName() {
		return doctorName;
	}
	/**
	 * set the name of the doctor.
	 * @param doctorName
	 */

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	/**
	 * @return the specialization of the doctor.
	 */

	public String getSpecialization() {
		return specialization;
	}
	/**
	 * set the specialization of the doctor.
	 * @param specialization
	 */

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	/**
	 * @return the department of the doctor.
	 */

//	public String getDepartment() {
//		return department;
//	}
//	/**
//	 * set the department of the doctor.
//	 * @param department
//	 */
//
//	public void setDepartment(String department) {
//		this.department = department;
//	}
	/**
	 * @return the phone number of the doctor.
	 */

	public long getDoctorPhNo() {
		return doctorPhNo;
	}

	/**
	 * set the phone number of the doctor.
	 * @param doctorPhNo
	 */
	public void setDoctorPhNo(long doctorPhNo) {
		this.doctorPhNo = doctorPhNo;
	}

	@Override
	public String toString() {
		return "Doctor [doctorName=" + doctorName + ", specialization=" + specialization
				+ ", doctorPhNo=" + doctorPhNo + "]";
	}
	
	
}
