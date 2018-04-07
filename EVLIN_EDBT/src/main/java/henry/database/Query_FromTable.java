package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Query_FromTable {
	int f_id;
	int q_id;
	String froms;
	public Query_FromTable(int f_id, int q_id, String froms) {
		super();
		this.f_id = f_id;
		this.q_id = q_id;
		this.froms = froms;
	}
	@Id
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
	public int getQ_id() {
		return q_id;
	}
	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}
	public String getFroms() {
		return froms;
	}
	public void setFroms(String froms) {
		this.froms = froms;
	}
	
	
	
	

}
