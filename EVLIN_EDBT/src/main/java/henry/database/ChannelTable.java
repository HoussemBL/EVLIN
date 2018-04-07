package henry.database;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;




@Entity
public class ChannelTable implements java.io.Serializable {

	private Long idchannel;

	
	private int idScale;
	
	private String field;
	private String channeltype;
	private String valueChannel;

	public ChannelTable() {
	}

	public ChannelTable(int idchannel) {
		this.idchannel =Long.valueOf( idchannel);
	}

	public ChannelTable(int idchannel, int scale, String field, String channeltype) {
		this.idchannel =Long.valueOf(idchannel);
		this.idScale = scale;
		this.field = field;
		this.channeltype = channeltype;
		
	}

	public ChannelTable(int chID, int i, String type) {
		this.idchannel =Long.valueOf(chID);
		this.idScale = i;
		this.channeltype = type;
	}
	
	
	public ChannelTable(String type,String color, int chID) {
		this.idchannel =Long.valueOf(chID);
		this.valueChannel=color;
		this.idScale=1111;
		this.channeltype = type;
	}
	
	
	
	

	@Id
	public Long getIdchannel() {
		return this.idchannel;
	}

	public void setIdchannel(Long idchannel) {
		this.idchannel =  idchannel;
	}

	public int getScale() {
		return this.idScale;
	}

	public void setScale(int scale) {
		this.idScale = scale;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getChanneltype() {
		return this.channeltype;
	}

	public void setChanneltype(String channeltype) {
		this.channeltype = channeltype;
	}

	public int getIdScale() {
		return idScale;
	}

	public String getValueChannel() {
		return valueChannel;
	}

	public void setIdScale(int idScale) {
		this.idScale = idScale;
	}

	public void setValueChannel(String valueChannel) {
		this.valueChannel = valueChannel;
	}

	

}
