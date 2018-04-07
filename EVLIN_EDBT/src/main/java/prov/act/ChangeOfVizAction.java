package prov.act;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.hibernate.Session;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import prov.CRUD.QueryConstructor;
import prov.vis.Vega;
import prov.vis.VegaBars;
import prov.vis.VegaBubbles;
import prov.vis.VegaPoints;
import prov.CRUD.VisualizationConstruction;
import prov.chart.VegaChart;
import prov.mybean.DisplayForm;
import prov.mybean.FacetForm;
import prov.mybean.QueryForm;
import prov.mybean.QueryInputForm;
import prov.mybean.VisualForm;
import prov.util.HibernateUtil;

public class ChangeOfVizAction extends Action {
	private final static String SUCCESS = "success";

	private final static String FAILURE = "echec";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		HttpSession ss = request.getSession();
		//VisualForm couranteViz = (VisualForm) ss.getAttribute("currentvis");
		List<VisualForm> ListPossibeViz = (List<VisualForm>) ss.getAttribute("vispossible");
		QueryForm UserQuery = (QueryForm) ss.getAttribute("queryBean");
		String dataJson= (String) ss.getAttribute("datajson");
		
	

				String[] nameOfviz = request.getParameterValues("typeVis");
				String VizTorender = nameOfviz[0].replace("'","");
					
			
				
			VegaChart veg_spec = new VegaChart();
			String veg = "";
		
			  
			for (VisualForm visualrender : ListPossibeViz )
			{
			
				if(visualrender.getName().equals(VizTorender)){
					visualrender.setStatus("true");
					//viscourante= visualrender;
					
					veg = visualrender.getJsonresult();
					veg_spec=visualrender.getVegafile();
					//update the visualization in the database
					/*DistinctValues.connector.Remove_Viz_DB();
					DistinctValues.connector.VizToDB(veg_spec);*/
					DistinctValues.connector.update_Viz_DB(veg_spec);
				}
				else  visualrender.setStatus("false");
			
			}
				
			
				
				
			
				
			

					
					/****************
					 * Sending JSON data to jsp page to be used later in vega
					 * visualization
					 ***************/
	
			ss.setAttribute("vegaSpec", veg_spec);
			ss.setAttribute("file", veg);
			
			
					ss.setAttribute("vispossible", ListPossibeViz);
					ss.setAttribute("queryBean", UserQuery);
					ss.setAttribute("datajson", dataJson);
				
					ss.setAttribute("dimensions", UserQuery.getGroupbyAtt().get(0));
				

				return mapping.findForward(SUCCESS);
		
		

	}

	
}
