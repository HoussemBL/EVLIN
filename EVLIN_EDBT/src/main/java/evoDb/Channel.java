package evoDb;
// default package
// Generated Jan 8, 2018 2:36:58 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Channel generated by hbm2java
 */
public class Channel implements java.io.Serializable {

	private int idchannel;
	private Scale scale;
	private String field;
	private String channeltype;
	private Set channelusages = new HashSet(0);

	public Channel() {
	}

	public Channel(int idchannel) {
		this.idchannel = idchannel;
	}

	public Channel(int idchannel, Scale scale, String field, String channeltype, Set channelusages) {
		this.idchannel = idchannel;
		this.scale = scale;
		this.field = field;
		this.channeltype = channeltype;
		this.channelusages = channelusages;
	}

	public int getIdchannel() {
		return this.idchannel;
	}

	public void setIdchannel(int idchannel) {
		this.idchannel = idchannel;
	}

	public Scale getScale() {
		return this.scale;
	}

	public void setScale(Scale scale) {
		this.scale = scale;
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

	public Set getChannelusages() {
		return this.channelusages;
	}

	public void setChannelusages(Set channelusages) {
		this.channelusages = channelusages;
	}

}
