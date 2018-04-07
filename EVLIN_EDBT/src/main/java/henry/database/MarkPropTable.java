package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MarkPropTable {
	int markProp_id;
	int e_id;
	Integer selectionID;
	
	public MarkPropTable() {
		
	}
	
	public MarkPropTable(int p_id, int e_id, Integer select_id) {
		super();
		this.markProp_id = p_id;
		this.e_id = e_id;
		this.selectionID = select_id;
	}
	@Id
	public int getP_id() {
		return markProp_id;
	}

	public void setP_id(int p_id) {
		this.markProp_id = p_id;
	}

	public int getE_id() {
		return e_id;
	}

	public void setE_id(int e_id) {
		this.e_id = e_id;
	}

	public Integer getSelectionID() {
		return selectionID;
	}

	public void setSelectionID(Integer select_id) {
		this.selectionID = select_id;
	}
	
	
}
