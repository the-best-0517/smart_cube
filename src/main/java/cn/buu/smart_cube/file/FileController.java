package cn.buu.smart_cube.file;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/file")
public class FileController {
	/**
	 * form1
	 * @param file
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/transferTo")
	public String transferTo( MultipartFile file) throws IllegalStateException, IOException {
//	public String transferTo(@RequestParam(required=false) CommonsMultipartFile file) {
		System.out.println("transferTo");
		System.out.println("fileName："+file.getOriginalFilename());
		String path="D:/"+new Date().getTime()+file.getOriginalFilename();
		File newFile = new File(path);
		file.transferTo(newFile);
		return "ok";
	}
	/**
	 * form2
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/request")
	public String request(HttpServletRequest  request) throws Exception  {
		System.out.println("request");
		CommonsMultipartResolver multipartResolver = 
				new CommonsMultipartResolver(request.getSession().getServletContext());
		 //检查form中是否有enctype="multipart/form-data"
		if(multipartResolver.isMultipart(request)) {
			//将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            Iterator iterator =  multiRequest.getFileNames();
            
            while(iterator.hasNext()) {
            	//一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iterator.next().toString());
                if(file!=null)
                {
                    String path="D:/springUpload"+file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }
            }
		}
		return "ok";
	}
}
