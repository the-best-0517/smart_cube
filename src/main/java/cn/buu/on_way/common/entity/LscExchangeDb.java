package cn.buu.on_way.common.entity;

import java.util.Map;

public class LscExchangeDb {
	private Map<String,Object> data;
	private String sqlPath;
	
		public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void setSqlPath(String sqlPath) {
		this.sqlPath = sqlPath;
	}

		public Map<String, Object> getData() {
		return data;
	}

	public String getSqlPath() {
		return sqlPath;
	}

		public void SaveDb() {
			System.out.println("¿Ó ¿≥¨∫‹Àß");
		}

		@Override
		public String toString() {
			return "LscSaveDb [data=" + data + ", sqlPath=" + sqlPath + "]";
		}
		
}
