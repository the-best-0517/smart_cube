package cn.buu.smart_cube.common.service.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.ExchangeDbService;
import cn.buu.smart_cube.common.service.CommonService;
@Service
public class CommonServiceImpl implements CommonService{
	@Resource
	private ExchangeDbService exchangeDbService;
	
	
	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author: 
	 * @CreateTime: 
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	*/
	public boolean generateImage(String imgStr,String path) {
		if(imgStr==null) {
			return false;
		}
		//BASE64Decoder decoder = new BASE64Decoder();
		
		try {
			//解密
		//	byte[] b = decoder.decodeBuffer(imgStr);
			byte[] b =new byte[1];
			for(int i=0;i<b.length;i++) {
				if(b[i]<0) {
					b[i] += 256;
				}
			}
				OutputStream os = new FileOutputStream(path);
				os.write(b);
				os.flush();
				os.close();
				return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 流水号
	 */
	@Override
	public long getOnlyKey() {
		long key = 0;
		try {
			long l = System.currentTimeMillis();
			//new���ڶ���
			Date date = new Date(l);
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			String k = sdf.format(date)+(int)((Math.random()*9+1)*1000)+"";
			key = Long.parseLong(k);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	/***
	 * 定时任务 主要用于按时间提醒
	 * date  :  具体日期或者 毫秒      用于规定何时执行
	 * interval: 延时时间  毫秒  用于循环执行（不可为0）
	 * 任务逻辑后一定要有  timer.cancel()
	 */
	@Override
	public void TimerRemindTask(Object date, long interVal) {
		System.out.println("date:"+date);
		final Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				String msg = "O01101010000";
				System.out.println("提醒:");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("msg", msg);
				LscExchangeDb lsc = new LscExchangeDb();
				lsc.setData(map);
				lsc.setSqlPath("test/updateMsg");
				exchangeDbService.saveDb(lsc);
				timer.cancel();
			}			
		};
		 timer.schedule(task, 0);              //（不循环）
	      //  timer.scheduleAtFixedRate(task, 0,1); //周期性循环执行
		
	}

}
