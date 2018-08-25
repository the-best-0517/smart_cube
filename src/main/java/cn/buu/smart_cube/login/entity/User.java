package cn.buu.smart_cube.login.entity;

import java.util.Date;

public class User {
		private long userId;
		private String userName;
		private String pwd;
		private String phone;
		private String wxkey;
		private Date createTime;
		private Date updateTime;
		private String breakfast;
		private String lunch;
		private String dinner;
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPwd() {
			return pwd;
		}
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getWxkey() {
			return wxkey;
		}
		public void setWxkey(String wxkey) {
			this.wxkey = wxkey;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public String getBreakfast() {
			return breakfast;
		}
		public void setBreakfast(String breakfast) {
			this.breakfast = breakfast;
		}
		public String getLunch() {
			return lunch;
		}
		public void setLunch(String lunch) {
			this.lunch = lunch;
		}
		public String getDinner() {
			return dinner;
		}
		public void setDinner(String dinner) {
			this.dinner = dinner;
		}
		@Override
		public String toString() {
			return "User [userId=" + userId + ", userName=" + userName + ", pwd=" + pwd + ", phone=" + phone
					+ ", wxkey=" + wxkey + ", createTime=" + createTime + ", updateTime=" + updateTime + ", breakfast="
					+ breakfast + ", lunch=" + lunch + ", dinner=" + dinner + "]";
		}
		
		

}
