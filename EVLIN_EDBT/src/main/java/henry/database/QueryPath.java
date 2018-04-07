package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QueryPath {
	
	int path_id;
	int parent_query_id;
	int q_id;

	public QueryPath() {
		
	}
	
	public QueryPath(int path_id, int q_id,int parent_query_id) {
		super();
		this.q_id = q_id;
		this.path_id=path_id;
		this.parent_query_id=parent_query_id;
	}
	
	
	public QueryPath(int q_id, int parent_query_id) {
		super();
		this.q_id = q_id;
		this.parent_query_id=parent_query_id;
		
	}
		
	
	
	@Id
	public int getPath_id() {
		return path_id;
	}

	public void setPath_id(int v_id) {
		this.path_id = v_id;
	}
	
	public int getQ_id() {
		return q_id;
	}

	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}



	public int getParent_query_id() {
		return parent_query_id;
	}

	public void setParent_query_id(int parent_query_id) {
		this.parent_query_id = parent_query_id;
	}
	
	
	
	
}
