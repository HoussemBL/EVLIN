package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Query_SelectTable {
	int s_id;
	int q_id;
	String dimension;
	
	public Query_SelectTable(int s_id, int q_id, String dimension) {
		super();
		this.s_id = s_id;
		this.q_id = q_id;
		this.dimension = dimension;
	}
	@Id
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public int getQ_id() {
		return q_id;
	}
	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	
	
	

}

