package henry.database.connection;


/**
 * An object that counts and increments for each row in Table. 
 * All IDs are integer and starts by default-value 0
 * 
 * @author henry
 */
public class CounterInfo {
	public int vis = 0;
	int position = 0;
	int encoding_id = 0;
	int markprop = 0;
	Integer selectionID = 0;
	int path=0;
	int q_ID;
	int s_ID;
	int f_ID;
	int w_ID;
	int path_ID=0;
	int data_ID=0;
	int channelUsage_ID=0;
	int axis_ID = 0;
	int axis_usage__ID=0;
	
	

	public int newaxis_Usage_ID() {
		int axisIDold = axis_usage__ID;
		axis_usage__ID++;
		return axisIDold;
	};
	
	
	
	

	public int newaxis_ID(){
		int axisIDold = axis_ID;
		axis_ID++;
		return axisIDold;
	};
	
	
	
	
	
	public int newchannelUsage_ID(){
		int channelUsageIDold = channelUsage_ID;
		channelUsage_ID++;
		return channelUsageIDold;
	};
	
	
	
	public int newdataID(){
		int dataIdOld = data_ID;
		data_ID++;
		return dataIdOld;
	};
	
	
	public int newpathID(){
		int pathIdOld = path;
		path++;
		return pathIdOld;
	};
	
	public int newVisID(){
		int visIdOld = vis;
		vis++;
		return visIdOld;
	};
	
	public int newEncodingID(){
		int encodingIDOld = encoding_id;
		encoding_id++;
		return encodingIDOld;
	};
	
	public int newPositionID(){
		int positionIDOld = position;
		position++;
		return positionIDOld;
	};
	
	public int newMarkPropID(){
		int markpropIDOld = markprop;
		markprop++;
		return markpropIDOld;
	};
	
	
	public Integer newSelectionID(){
		int selectionIDOld = selectionID;
		selectionID++;
		return selectionIDOld;
	};
	
	public Integer newQ_ID(){
		int q_IDOld = q_ID;
		q_ID++;
		return q_IDOld;
	};
	
	public Integer newS_ID(){
		int s_IDOld = s_ID;
		s_ID++;
		return s_IDOld;
	};
	
	
	public Integer newF_ID(){
		int f_IDOld = f_ID;
		f_ID++;
		return f_IDOld;
	};
	
	
	public Integer newW_ID(){
		int w_IDOld = w_ID;
		w_ID++;
		return w_IDOld;
	}

	public int getVis_ID() {
		return vis;
	}

	public int getPosition() {
		return position;
	}

	public int getEncoding_id() {
		return encoding_id;
	}

	public int getMarkprop() {
		return markprop;
	}

	public Integer getSelectionID() {
		return selectionID;
	}

	public int getPath() {
		return path;
	}

	public int getQ_ID() {
		return q_ID;
	}

	public int getS_ID() {
		return s_ID;
	}

	public int getF_ID() {
		return f_ID;
	}

	public int getW_ID() {
		return w_ID;
	}

	public int getPath_ID() {
		return path_ID;
	}

	public void setVis(int vis) {
		this.vis = vis;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setEncoding_id(int encoding_id) {
		this.encoding_id = encoding_id;
	}

	public void setMarkprop(int markprop) {
		this.markprop = markprop;
	}

	public void setSelectionID(Integer selectionID) {
		this.selectionID = selectionID;
	}

	public void setPath(int path) {
		this.path = path;
	}

	public void setQ_ID(int q_ID) {
		this.q_ID = q_ID;
	}

	public void setS_ID(int s_ID) {
		this.s_ID = s_ID;
	}

	public void setF_ID(int f_ID) {
		this.f_ID = f_ID;
	}

	public void setW_ID(int w_ID) {
		this.w_ID = w_ID;
	}

	public void setPath_ID(int path_ID) {
		this.path_ID = path_ID;
	}


	public int getData_ID() {
		return data_ID;
	}


	public void setData_ID(int data_ID) {
		this.data_ID = data_ID;
	}



	public int getChannelUsage_ID() {
		return channelUsage_ID;
	}



	public void setChannelUsage_ID(int channelUsage_ID) {
		this.channelUsage_ID = channelUsage_ID;
	}





	public int getAxis_ID() {
		return axis_ID;
	}





	public void setAxis_ID(int axis_ID) {
		this.axis_ID = axis_ID;
	}





	public int getAxis_usage__ID() {
		return axis_usage__ID;
	}





	public void setAxis_usage__ID(int axis_usage__ID) {
		this.axis_usage__ID = axis_usage__ID;
	}





	
	
	
	
	
	
}
