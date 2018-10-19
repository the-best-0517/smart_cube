package cn.buu.smart_cube.common.contoller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.ExchangeDbService;

@Controller
public class wenzisql {
	
	  private static int intKuan = 67;
	  private static int intGao = 67;
	  private static double intSuoFangKuan =1.01 ; // 横向缩放比例如 1.01 0.9
	  private static double intSuoFangGao = 1.01; // 纵向缩放比例如 1.01 0.9
	  private static int intFaZhi = -16; // 显示阀值
	 // private static String strShuRu = "猪"; // 待取字模
	  private static String strZiTi = "宋体"; // 待取字体
	
	
	@Resource
	private ExchangeDbService exchangeDbService;
		@RequestMapping("/wenzi")
		public void wenzi() {						
			
			Map<String,Object> data = new HashMap<String,Object>();
			//读取汉子
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setSqlPath("test/qryhanzi");
			List<Map<String,Object>> list = exchangeDbService.selectDbNoParam(lsc);
			//转换
			for(int i=0;i<list.size();i++) {
				String s = zhuanhuan(list.get(i).get("zi").toString());
				System.out.println("zi:"+s);
				//更新保存
				lsc.setSqlPath("test/updatehanzi");
				data.put("str", s);
				data.put("zi", list.get(i).get("zi"));
				lsc.setData(data);
				System.out.println("lsc:"+lsc);
				try {
					exchangeDbService.saveDb(lsc);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
		
		//List l = new ArrayList();
		
		  public  String zhuanhuan(String str) {
			  String sss = "";
			    BufferedImage image = new BufferedImage(intKuan, intGao, BufferedImage.TYPE_INT_BGR);
			    Graphics2D g = image.createGraphics();
			    Font myFont = new Font(strZiTi,Font.PLAIN, 67); // 定义字体样式
			    g.setFont(myFont); // 设置字体
			    g.fillRect(0, 0, intKuan, intGao); // 绘制背景
			    g.setColor(new Color(0, 0, 0)); // 设置字体颜色
			    g.scale(intSuoFangKuan, intSuoFangGao); // 设置缩放
			    g.drawString(str, 0, 54);
			    g.dispose();
			    // 生成图片文件
			    // File f1 = new File("/tmp/11.jpg");
			    // try {
			    // ImageIO.write(image, "JPG", f1);
			    // } catch (IOException e) {
			    // // TODO Auto-generated catch block
			    // e.printStackTrace();
			    // }
			    BufferedImage bi[] = new BufferedImage[16 * 16];
			    int intDianZhen[] = new int[16 * 16];
			    for (int i = 0; i < 16; i++) {
			      for (int j = 0; j < 16; j++) {
			        bi[i * 16 + j] = image.getSubimage(i * 4, j * 4, 4, 4);
			      }
			    }
			    for (int i = 0; i < bi.length; i++) {
			      int intD = 0;
			      for (int j = 0; j < 4; j++) {
			        for (int k = 0; k < 4; k++) {
			          intD += bi[i].getRGB(j, k);
			        }
			      }
			      intDianZhen[i] = intD;
			    }
			    List<String> list = new ArrayList<String>();
			    // intDianZhen是实际的像素值数组，为了方便使用下面再用一个循环取字模，实际可以和上面合并
			    for (int i = 0; i < 16; i++) {
			      StringBuffer sb = new StringBuffer();
			      StringBuffer s = new StringBuffer();
			      for (int j = 0; j < 16; j++) {
			        if (intDianZhen[i + j * 16] < intFaZhi) {
			         // sb.append("■");
			          System.out.print("·");
			          s.append("1");
			        } else {
			          //sb.append("-");
			          System.out.print(" ");
			          s.append("0");
			        }
			        if(j==7||j==15) {
			        	s = s.reverse();       //字符串翻转
			        	list.add(s.toString());
			        	s = new StringBuffer();
			        	//System.out.println("sb:"+list);
			        }
			      }
			      System.out.println();

			    }
			    System.out.println(list);
			    //转换为16进制
			    for(int i=0;i<list.size();i++) {
			    	Integer ten = binaryToDecimal(list.get(i));  //二进制转换为十进制   	
			    	String hex = Integer.toHexString(ten);				//十进制转换为二进制
			    	if(hex.length()==1) {
			    		hex = "0"+hex;
			    	}
			    	System.out.print("0x"+hex);
			    	//l.add("0x"+hex);
			    	sss = sss+"0x"+hex;
			    	if(i!=(list.size()-1)) {
			    		sss = sss+",";
			    		System.out.print(",");
			    	}
			    }
			    System.out.println("s:"+sss);
			    return sss;
			    
			  }
		  
		   /**
		   * @Description:	二进制转换成十进制 
		   * @param binarySource
		   * @return int
		    */
		   public  int binaryToDecimal(String binarySource) {
		   	BigInteger bi = new BigInteger(binarySource, 2);	//转换为BigInteger类型
		   	return Integer.parseInt(bi.toString());		//转换成十进制
		   }
}
