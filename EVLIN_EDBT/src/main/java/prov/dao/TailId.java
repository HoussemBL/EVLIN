package prov.dao;
// Generated Jul 23, 2016 12:10:03 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * TailId generated by hbm2java
 */
public class TailId implements java.io.Serializable {

	private String tailnum;
	private String type;
	private String manufacturer;
	private String issueDate;
	private String model;
	private String status;
	private String aircraftType;
	private String engineType;
	private BigDecimal year;

	public TailId() {
	}

	public TailId(String tailnum, String type, String manufacturer, String issueDate, String model, String status,
			String aircraftType, String engineType, BigDecimal year) {
		this.tailnum = tailnum;
		this.type = type;
		this.manufacturer = manufacturer;
		this.issueDate = issueDate;
		this.model = model;
		this.status = status;
		this.aircraftType = aircraftType;
		this.engineType = engineType;
		this.year = year;
	}

	public String getTailnum() {
		return this.tailnum;
	}

	public void setTailnum(String tailnum) {
		this.tailnum = tailnum;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAircraftType() {
		return this.aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public String getEngineType() {
		return this.engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public BigDecimal getYear() {
		return this.year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TailId))
			return false;
		TailId castOther = (TailId) other;

		return ((this.getTailnum() == castOther.getTailnum()) || (this.getTailnum() != null
				&& castOther.getTailnum() != null && this.getTailnum().equals(castOther.getTailnum())))
				&& ((this.getType() == castOther.getType()) || (this.getType() != null && castOther.getType() != null
						&& this.getType().equals(castOther.getType())))
				&& ((this.getManufacturer() == castOther.getManufacturer())
						|| (this.getManufacturer() != null && castOther.getManufacturer() != null
								&& this.getManufacturer().equals(castOther.getManufacturer())))
				&& ((this.getIssueDate() == castOther.getIssueDate()) || (this.getIssueDate() != null
						&& castOther.getIssueDate() != null && this.getIssueDate().equals(castOther.getIssueDate())))
				&& ((this.getModel() == castOther.getModel()) || (this.getModel() != null
						&& castOther.getModel() != null && this.getModel().equals(castOther.getModel())))
				&& ((this.getStatus() == castOther.getStatus()) || (this.getStatus() != null
						&& castOther.getStatus() != null && this.getStatus().equals(castOther.getStatus())))
				&& ((this.getAircraftType() == castOther.getAircraftType())
						|| (this.getAircraftType() != null && castOther.getAircraftType() != null
								&& this.getAircraftType().equals(castOther.getAircraftType())))
				&& ((this.getEngineType() == castOther.getEngineType()) || (this.getEngineType() != null
						&& castOther.getEngineType() != null && this.getEngineType().equals(castOther.getEngineType())))
				&& ((this.getYear() == castOther.getYear()) || (this.getYear() != null && castOther.getYear() != null
						&& this.getYear().equals(castOther.getYear())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTailnum() == null ? 0 : this.getTailnum().hashCode());
		result = 37 * result + (getType() == null ? 0 : this.getType().hashCode());
		result = 37 * result + (getManufacturer() == null ? 0 : this.getManufacturer().hashCode());
		result = 37 * result + (getIssueDate() == null ? 0 : this.getIssueDate().hashCode());
		result = 37 * result + (getModel() == null ? 0 : this.getModel().hashCode());
		result = 37 * result + (getStatus() == null ? 0 : this.getStatus().hashCode());
		result = 37 * result + (getAircraftType() == null ? 0 : this.getAircraftType().hashCode());
		result = 37 * result + (getEngineType() == null ? 0 : this.getEngineType().hashCode());
		result = 37 * result + (getYear() == null ? 0 : this.getYear().hashCode());
		return result;
	}

}
