package henry.database;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;

import evoDb.Scale;

@Entity
public class AxisTable implements java.io.Serializable {

	private int idaxis;
	private int idscale;
	private int tick;
	private String title;
	private String type;


	public AxisTable() {
	}

	public AxisTable(int idaxis) {
		this.idaxis = idaxis;
	}

	public AxisTable(int idaxis, int scale, int tick, String title, String type) {
		this.idaxis = idaxis;
		this.idscale = scale;
		this.tick = tick;
		this.title = title;
		this.type = type;
		
	}
	
	
	public AxisTable(int idaxis, int scale,  String title, String type) {
		this.idaxis = idaxis;
		this.idscale = scale;
		
		this.title = title;
		this.type = type;
		
	}
	
	
	
@Id
	public int getIdaxis() {
		return this.idaxis;
	}

	public void setIdaxis(int idaxis) {
		this.idaxis = idaxis;
	}

	public int getScale() {
		return this.idscale;
	}

	public void setScale(int scale) {
		this.idscale = scale;
	}

	public int getTick() {
		return this.tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
