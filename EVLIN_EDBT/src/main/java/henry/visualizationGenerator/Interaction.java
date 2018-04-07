package henry.visualizationGenerator;

import java.util.ArrayList;
import java.util.List;

import prov.chartLite.Conditions;
import prov.chartLite.Selection;
import prov.chartLite.SelectionID;

public class Interaction<T> {
	
	public InteractionContainer calculateInteraction(String interaction) {
		Selection selection2 = new Selection("id");
		Conditions conditions;
		Selection selection;
		InteractionContainer container = new InteractionContainer();
		
		if(interaction.equals("click")) {
			
			conditions = new Conditions(selection2, "grey");
			selection = new Selection(new SelectionID("click", "single"));
			container.setConditions(conditions);
			container.setSelection(selection);
			return container;
		}else if (interaction == "dblclick") {
			conditions = new Conditions(selection2, "grey");
			selection = new Selection(new SelectionID("dblclick", "single"));
			container.setConditions(conditions);
			container.setSelection(selection);
			return container;
		}else if (interaction == "mouseover") {
			conditions = new Conditions(selection2, "grey");
			selection = new Selection(new SelectionID("mouseover", "single"));
			container.setConditions(conditions);
			container.setSelection(selection);
			return container;
		}else if (interaction == "interval") {
			conditions = new Conditions(selection2, "grey");
			selection = new Selection(new SelectionID("interval"));
			container.setConditions(conditions);
			container.setSelection(selection);
			return container;
		}else {
			conditions = new Conditions(selection2, "grey");
			selection = new Selection(new SelectionID("click", "single"));
			container.setConditions(conditions);
			container.setSelection(selection);
			return container;
		}

	}
}
