package henry.database;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
	public class ChannelUsageTable implements java.io.Serializable {

		private int idchannelusage;
		private int idchannel;
		private int idmark;
		private String usagetype;

		public ChannelUsageTable() {
		}

		public ChannelUsageTable(int idchannelusage, int channel, int mark) {
			this.idchannelusage = idchannelusage;
			this.idchannel = channel;
			this.idmark = mark;
		}

		public ChannelUsageTable(int idchannelusage, int channel, int mark, String usagetype) {
			this.idchannelusage = idchannelusage;
			this.idchannel = channel;
			this.idmark = mark;
			this.usagetype = usagetype;
		}

		
		@Id
		public int getIdchannelusage() {
			return this.idchannelusage;
		}

		public void setIdchannelusage(int idchannelusage) {
			this.idchannelusage = idchannelusage;
		}

	

		public String getUsagetype() {
			return this.usagetype;
		}

		public void setUsagetype(String usagetype) {
			this.usagetype = usagetype;
		}

		public int getIdchannel() {
			return idchannel;
		}

		public int getIdmark() {
			return idmark;
		}

		public void setIdchannel(int idchannel) {
			this.idchannel = idchannel;
		}

		public void setIdmark(int idmark) {
			this.idmark = idmark;
		}

	
	}


