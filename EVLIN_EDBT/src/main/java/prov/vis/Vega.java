package prov.vis;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import henry.QuerySimilarity.QueryFromDB;
import prov.chart.VegaChart;
import prov.mybean.DisplayForm;

public interface Vega {

	

	public VegaChart PrintVisrec_2D(String ColorCode);
	
	public String buildJs(VegaChart chart);

	public VegaChart PrintVisrec_ZoomInSlice(String ColorCode);

	public VegaChart PrintVisrecZoomIn(String webField,String old,String type/*,List<DisplayForm> datasetToGiveColor*/);
	
	public VegaChart PrintVisrec_ZoomInMoreDetails(String webField, String old, String  type,List<DisplayForm> datasetToGiveColor);

	public VegaChart PrintExtension_Slice();
	
	//extension visualization
	public VegaChart PrintVisrecExTension(String webField, String OperationType/*,List<DisplayForm> li*/);
	public VegaChart PrintVisExTensionBubble(String webField, String OperationType/*, List<DisplayForm> datasetToGiveColor*/);
	public VegaChart PrintVisReverseExTension(String webField, String OperationType/*, List<DisplayForm> datasetToGiveColor*/);
	
	
	
	//drill down slice
	public VegaChart PrintVisrecSlice_Drill(String webField,String old,String type/*,List<DisplayForm> datasetToGiveColor*/);
	
	
	
	
	public List<DisplayForm> Construct_ScaleColorData_AxeX2(List<DisplayForm> datasetToGiveColor);
	public List<DisplayForm> Construct_ScaleColorData(List<DisplayForm> datasetToGiveColor);

	public List<DisplayForm> Construct_ScaleColorData_AxeX2_Given_Input(List<DisplayForm> colorRanges,
			List<DisplayForm> list);
	
	
	
	public VegaChart Recommendation_Viz(QueryFromDB currentQuerry,List<QueryFromDB> querys, String webField/*,String old*/,String type,List<DisplayForm> datasetToGiveColor);

	public  void Complete_Rec_Viz_ZoomIn(VegaChart recommendation, String webField, String old, String type,
			List<DisplayForm> datasetToGiveColor, int visid, Map<String, Integer> marksElts,
			Session session);
	
	public void Complete_Rec_Viz_2D(VegaChart recommendation, List<DisplayForm> datasetToGiveColor,
			int visID, Map<String, Integer> mappingMarkID, Session session,String type);
	
	public void Complete_Rec_Viz_Extension(VegaChart recommendation, String webField, String old, String type,
			List<DisplayForm> datasetToGiveColor, int visid, Map<String,Integer> marksElts, Session session) ;
	
	//public void Complete_Rec_Viz_2D(VegaChart recommendation, List<DisplayForm> datasetToGiveColor, int visID,
		//	Map<String, Integer> mappingMarkID, Session session);

	//public VegaChart Recommendation_Viz_ZoomInSlice(QueryFromDB currentQuerry, List<QueryFromDB> querys);
	
}



	 
	