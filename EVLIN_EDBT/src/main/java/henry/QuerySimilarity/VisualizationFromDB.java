package henry.QuerySimilarity;


/**
 * this is a class for 
 * @author henry
 *
 */
public class VisualizationFromDB {
	
	

	int width = 0;
	int height = 0;
	
	String mark = null;
	String interaction = null; 
	String color = null;
	
	String xAxisDimension = null;
	String xAxisType = null;
	String xAggregation = null;
	String xAxisTitle = null;
	Integer xAxisRangestep = null;
	Boolean xAxisGrid = null;
	
	String yAxisDimension = null;
	String yAxisType = null; 
	String yAggregation = null;
	String yAxisTitle = null;
	Integer yAxisRangeStep = null;
	Boolean yAxisgrid = null;
	
	String zAxisDimension = null; 
	String zAxisType = null; 
	
	
	public VisualizationFromDB() {
		
	}

	public VisualizationFromDB(int numberOfDimensions, int width, int height, String mark, String interaction,
			String color, String xAxisDimension, String xAxisType, String xAggregation, String xAxisTitle,
			Integer xAxisRangestep, Boolean xAxisGrid, String yAxisDimension,String yAxisType, String yAggregation, String yAxisTitle,
			Integer yAxisRangeStep, Boolean yAxisgrid) {
		super();
		this.numberOfDimensions = numberOfDimensions;
		this.width = width;
		this.height = height;
		this.mark = mark;
		this.interaction = interaction;
		this.color = color;
		this.xAxisDimension = xAxisDimension;
		this.xAxisType = xAxisType;
		this.xAggregation = xAggregation;
		this.xAxisTitle = xAxisTitle;
		this.xAxisRangestep = xAxisRangestep;
		this.xAxisGrid = xAxisGrid;
		this.yAxisDimension = yAxisDimension;
		this.yAxisType = yAxisType;
		this.yAggregation = yAggregation;
		this.yAxisTitle = yAxisTitle;
		this.yAxisRangeStep = yAxisRangeStep;
		this.yAxisgrid = yAxisgrid;
	}
	
	
	public VisualizationFromDB(int numberOfDimensions, int width, int height, String mark, String interaction,
			String color, String xAxisDimension, String xAxisType, String xAggregation, String xAxisTitle,
			Integer xAxisRangestep, Boolean xAxisGrid, String yAxisDimension,String yAxisType, String yAggregation, String yAxisTitle,
			Integer yAxisRangeStep, Boolean yAxisgrid,String zAxisDimension,String zAxisType) {
		super();
		this.numberOfDimensions = numberOfDimensions;
		this.width = width;
		this.height = height;
		this.mark = mark;
		this.interaction = interaction;
		this.color = color;
		this.xAxisDimension = xAxisDimension;
		this.xAxisType = xAxisType;
		this.xAggregation = xAggregation;
		this.xAxisTitle = xAxisTitle;
		this.xAxisRangestep = xAxisRangestep;
		this.xAxisGrid = xAxisGrid;
		this.yAxisDimension = yAxisDimension;
		this.yAxisType = yAxisType;
		this.yAggregation = yAggregation;
		this.yAxisTitle = yAxisTitle;
		this.yAxisRangeStep = yAxisRangeStep;
		this.yAxisgrid = yAxisgrid;
		this.zAxisDimension = zAxisDimension; 
		this.zAxisType = zAxisType;
		
	}

	
	int numberOfDimensions = 0; 
	public int getNumberOfDimensions() {
		return numberOfDimensions;
	}

	public void setNumberOfDimensions(int numberOfDimensions) {
		this.numberOfDimensions = numberOfDimensions;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getInteraction() {
		return interaction;
	}

	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getxAxisDimension() {
		return xAxisDimension;
	}

	public void setxAxisDimension(String xAxisDimension) {
		this.xAxisDimension = xAxisDimension;
	}

	public String getxAxisType() {
		return xAxisType;
	}

	public void setxAxisType(String xAxisType) {
		this.xAxisType = xAxisType;
	}

	public String getxAggregation() {
		return xAggregation;
	}

	public void setxAggregation(String xAggregation) {
		this.xAggregation = xAggregation;
	}

	public String getxAxisTitle() {
		return xAxisTitle;
	}

	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}

	public Integer getxAxisRangestep() {
		return xAxisRangestep;
	}

	public void setxAxisRangestep(Integer xAxisRangestep) {
		this.xAxisRangestep = xAxisRangestep;
	}

	public Boolean getxAxisGrid() {
		return xAxisGrid;
	}

	public void setxAxisGrid(Boolean xAxisGrid) {
		this.xAxisGrid = xAxisGrid;
	}

	public String getyAxisDimension() {
		return yAxisDimension;
	}

	public void setyAxisDimension(String yAxisDimension) {
		this.yAxisDimension = yAxisDimension;
	}

	public String getyAxisType() {
		return yAxisType;
	}

	public void setyAxisType(String yAxisType) {
		this.yAxisType = yAxisType;
	}

	public String getyAggregation() {
		return yAggregation;
	}

	public void setyAggregation(String yAggregation) {
		this.yAggregation = yAggregation;
	}

	public String getyAxisTitle() {
		return yAxisTitle;
	}

	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}

	public Integer getyAxisRangeStep() {
		return yAxisRangeStep;
	}

	public void setyAxisRangeStep(Integer yAxisRangeStep) {
		this.yAxisRangeStep = yAxisRangeStep;
	}

	public Boolean getyAxisgrid() {
		return yAxisgrid;
	}

	public void setyAxisgrid(Boolean yAxisgrid) {
		this.yAxisgrid = yAxisgrid;
	}

	public String getzAxisDimension() {
		return zAxisDimension;
	}

	public void setzAxisDimension(String zAxisDimension) {
		this.zAxisDimension = zAxisDimension;
	}

	public String getzAxisType() {
		return zAxisType;
	}

	public void setzAxisType(String zAxisType) {
		this.zAxisType = zAxisType;
	}
	


}
