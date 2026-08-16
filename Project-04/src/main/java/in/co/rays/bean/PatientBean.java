package in.co.rays.bean;

import java.util.Date;

/**
 * PatientBean is a JavaBean class that represents patient details. It contains
 * attributes like name, date of visit, mobile number, and disease.
 * <p>
 * This bean extends {@link BaseBean}.
 * </p>
 * 
 * @author Amit
 * @version 1.0
 *
 */
public class PatientBean extends BaseBean {

	/** Name of the patient */
	private String name;

	/** Date of visit of the patient */
	private Date dateofvisit;

	/** Mobile number of the patient */
	private String mobileNo;

	/** Disease of the patient */
	private String disease;

	/**
	 * Gets the name of the patient.
	 * 
	 * @return name of the patient
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the patient.
	 * 
	 * @param name name of the patient
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the date of visit.
	 * 
	 * @return date of visit
	 */
	public Date getDateofvisit() {
		return dateofvisit;
	}

	/**
	 * Sets the date of visit.
	 * 
	 * @param dateofvisit date of visit
	 */
	public void setDateofvisit(Date dateofvisit) {
		this.dateofvisit = dateofvisit;
	}

	/**
	 * Gets the mobile number of the patient.
	 * 
	 * @return mobile number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the patient.
	 * 
	 * @param mobileNo mobile number
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Gets the disease of the patient.
	 * 
	 * @return disease
	 */
	public String getDisease() {
		return disease;
	}

	/**
	 * Sets the disease of the patient.
	 * 
	 * @param disease disease
	 */
	public void setDisease(String disease) {
		this.disease = disease;
	}

	/**
	 * Gets the key for this bean.
	 * 
	 * @return disease as key
	 */
	@Override
	public String getkey() {
		return disease;
	}

	/**
	 * Gets the value for this bean.
	 * 
	 * @return disease as value
	 */
	@Override
	public String getValue() {
		return disease;
	}

}