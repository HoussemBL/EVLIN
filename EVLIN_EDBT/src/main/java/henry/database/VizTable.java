package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VizTable {
	int idvisualization;
	int idquery;
	int height;
	int width;
	
	
	public VizTable() {
		
	}


	public VizTable(int idvisualization, int idquery, int height, int width) {
		super();
		this.idvisualization = idvisualization;
		this.idquery = idquery;
		this.height = height;
		this.width = width;
	}

	@Id
	public int getIdvisualization() {
		return idvisualization;
	}


	public int getIdquery() {
		return idquery;
	}


	public int getHeight() {
		return height;
	}


	public int getWidth() {
		return width;
	}


	public void setIdvisualization(int idvisualization) {
		this.idvisualization = idvisualization;
	}


	public void setIdquery(int idquery) {
		this.idquery = idquery;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public void setWidth(int width) {
		this.width = width;
	}
	
	
	

	
	
}
 