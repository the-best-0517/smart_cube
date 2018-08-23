package cn.buu.smart_cube.common.web;
/**
 * 閫氳繃姝ゅ璞″皝瑁呮帶鍒跺眰杩斿洖鐨勭粨鏋�
 * @author ABC
 *
 */
public class JsonResult {
	public static final int SUCCESS = 1;
	public static final int ERROR = 0;
	 /**鐘舵��*/
     private int  state;
     /**淇℃伅*/
     private String message;
     /**鍏蜂綋涓氬姟鏁版嵁*/
     private Object data;
     public JsonResult() {
    	 this.state = SUCCESS;
    	 this.message = "ok";
     }
     
     public JsonResult(Object data) {
    	this();
    	this.data = data;
     }
     
     public JsonResult(Throwable e) {
    	 this.state = ERROR;
    	 this.message = e.getMessage();
     }
     public JsonResult(String  str) {
    	 this.state = ERROR;
    	 this.message = str;
     }
     
	public int getState() {
		return state;
	}
	public String getMessage() {
		return message;
	}
	public Object getData() {
		return data;
	}
     
}
