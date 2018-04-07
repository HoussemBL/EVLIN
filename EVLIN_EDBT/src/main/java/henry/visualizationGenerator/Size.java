package henry.visualizationGenerator;

public class Size {
	static public  int[] calculateSize(String size){
		int sizes[] = new int[2];
		
		if(size=="big") {
			sizes[0]=600;
			sizes[1]=600;
			return sizes;
		}
		else if(size == "middle") {
			sizes[0]=400;
			sizes[1]=400;
			return sizes;
		}
		
		else {
			sizes[0]=200;
			sizes[1]=200;
			return sizes;
		}
	}	
}
