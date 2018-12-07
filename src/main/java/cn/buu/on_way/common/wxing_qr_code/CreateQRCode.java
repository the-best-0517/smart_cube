package cn.buu.on_way.common.wxing_qr_code;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

//生成二维码
public class CreateQRCode {
	
	public static void main(String[] args) {
		int width = 300;
		int height = 300;
		String format = "png";
		String content = "http://www.baidu.com";
		
		//定义二维码参数
		HashMap hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN,2);                      //边框空白大小
		
		try {
			BitMatrix bit = new MultiFormatWriter()
			.encode(content,BarcodeFormat.QR_CODE, width, height,hints);
			//定义路径
			Path file = new File("D:/code.png").toPath();
			//生成图片
			MatrixToImageWriter.writeToPath(bit, format, file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
