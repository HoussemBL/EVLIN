package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MarkTable {
	int idmark;
	int idvis;
	String marktype;

	
	
	public MarkTable() {
		
	}
	
	
	public MarkTable(int idmark, int idvis, String marktype) {
		super();
		this.idmark = idmark;
		this.idvis = idvis;
		this.marktype = marktype;
	}


	@Id
	public int getIdmark() {
		return idmark;
	}


	public int getIdvis() {
		return idvis;
	}


	public String getMarktype() {
		return marktype;
	}


	public void setIdmark(int idmark) {
		this.idmark = idmark;
	}


	public void setIdvis(int idvis) {
		this.idvis = idvis;
	}


	public void setMarktype(String marktype) {
		this.marktype = marktype;
	}


	
	
	
	

	
	
	
}
 