package henry.visualizationGenerator;

import prov.chartLite.Conditions;
import prov.chartLite.Selection;

public class InteractionContainer {
	Selection selection;
	Conditions conditions;
	
	
	public Selection getSelection() {
		return selection;
	}
	public void setSelection(Selection selection) {
		this.selection = selection;
	}
	public Conditions getConditions() {
		return conditions;
	}
	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}
}
