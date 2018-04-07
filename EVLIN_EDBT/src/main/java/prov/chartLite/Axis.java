package prov.chartLite;

public class Axis {
	String title = null;
	Boolean grid = null;
	
	public Axis(String titel, Boolean grid) {
		super();
		this.title = titel;
		this.grid = grid;
	}
	public String getTitel() {
		return title;
	}
	public void setTitel(String titel) {
		this.title = titel;
	}
	public Boolean getGrid() {
		return grid;
	}
	public void setGrid(Boolean grid) {
		this.grid = grid;
	}
	
}
