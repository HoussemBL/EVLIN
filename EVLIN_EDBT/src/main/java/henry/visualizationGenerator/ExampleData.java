package henry.visualizationGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import prov.chartLite.DataContainer;
import prov.chartLite.DataChart;

public class ExampleData {
	
	

	
	
	public DataChart makeRandomData(int numberOfDatas, Boolean colored){
		List<DataContainer> x = new ArrayList();
		Random rand = new Random();
		for (int i = 0; i < numberOfDatas; i++) {
			int a = i ;
			if (colored){
				a = (int)(i/3);
			}
			
			DataContainer con = new DataContainer(a, rand.nextInt(50) + 1 ,i%3);
			x.add(con);
		}
		DataChart data = new DataChart("asdds", x);
		return data;
	}
	
	
	
}
