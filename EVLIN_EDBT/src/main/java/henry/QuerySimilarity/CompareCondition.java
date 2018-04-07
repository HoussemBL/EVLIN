package henry.QuerySimilarity;

public class CompareCondition {
	
	static double compare(Condition con1 , Condition con2) {
		if(con1.dimension.equals(con2.dimension) ) {
			if (con1.operator.equals(con2.operator)) {
				return 1;
				
			}else {
				if (con1.value.equals(con2.value)) {
					return 0.75;
				}
				return 0.5;
			}
			
		}
		return 0;
	}
	
	
}
