package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;

import evoDb.Axis;
import evoDb.Visualization;

@Entity
public class AxisUsageTable implements java.io.Serializable {

	private int idaxisusage;
	private int idaxis;
	private int idvisualization;
	private String typeusage;

	public AxisUsageTable() {
	}

	public AxisUsageTable(int idaxisusage, int axis, int visualization) {
		this.idaxisusage = idaxisusage;
		this.idaxis = axis;
		this.idvisualization = visualization;
	}

	public AxisUsageTable(int idaxisusage, int axis, int visualization, String typeusage) {
		this.idaxisusage = idaxisusage;
		this.idaxis = axis;
		this.idvisualization = visualization;
		this.typeusage = typeusage;
	}

	@Id
	public int getIdaxisusage() {
		return this.idaxisusage;
	}

	public void setIdaxisusage(int idaxisusage) {
		this.idaxisusage = idaxisusage;
	}

	
	public String getTypeusage() {
		return this.typeusage;
	}

	public void setTypeusage(String typeusage) {
		this.typeusage = typeusage;
	}

	public int getIdaxis() {
		return idaxis;
	}

	public int getIdvisualization() {
		return idvisualization;
	}

	public void setIdaxis(int idaxis) {
		this.idaxis = idaxis;
	}

	public void setIdvisualization(int idvisualization) {
		this.idvisualization = idvisualization;
	}
	
	

}

