package cn.buu.smart_cube.setting.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.impl.ExchangeDbServiceImpl;
import cn.buu.smart_cube.common.service.impl.CommonServiceImpl;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/html")
public class PillWebController {
	
	@Resource
	private ExchangeDbServiceImpl exchangeDbServiceImpl;
	@Resource
	private CommonServiceImpl commonServiceImpl;
	
	  @RequestMapping("/upload")
	  public String upload(RedirectAttributes redirectAttributes,
			  @RequestParam("pillId") String pillId,
			  @RequestParam("colId") String colId,
			  @RequestParam("zFont") String zFont,
			  @RequestParam("title") String title,
			  @RequestParam("pillInstruction") String pillInstruction,
			  @RequestParam("instructions") String instructions,
	    HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException {
	   CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
	     request.getSession().getServletContext());
	   String image = null;
	   String fileName = null;
	   if (multipartResolver.isMultipart(request)) {
	    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	    Iterator<String> iter = multiRequest.getFileNames();
	    String myFileName = null;
	    String myFiledName = null;
	    while (iter.hasNext()) {
	     List<MultipartFile> file = multiRequest.getFiles(iter.next());
	     if (file != null) {
	      for (MultipartFile files : file) {
	       myFileName = files.getOriginalFilename();
	       myFiledName = files.getName();
	       if (myFileName.toString().trim() != "") {
	        String path = request.getSession().getServletContext().getRealPath("file"+File.separator+"img");
	        long getminsecond = System.currentTimeMillis();
	        fileName = getminsecond + "_" + myFileName;
	        if (myFiledName.equals("pictures")) {
	         image = fileName;
	        }
	        File targetFile = new File(path, fileName);
	        System.out.println(fileName+"     "+path);
	        files.transferTo(targetFile);
	       }
	      }
	     }
	    }
	   }
	   	Map<String,Object> data = new HashMap<String,Object>();
	   	data.put("where_eating", colId);
	   	data.put("pill_id",pillId);
	   	data.put("license_num", zFont);
	   	data.put("pill_desc",title );
	   	data.put("pill_instruction", pillInstruction);
	   	data.put("instructions", instructions);
	   	data.put("img_path","file/img/"+fileName);
	   	LscExchangeDb lsc = new LscExchangeDb();
	   	lsc.setData(data);
	   	lsc.setSqlPath("pill/insertPill");
	   	try {
	   		exchangeDbServiceImpl.saveDb(lsc);
	   	 return "redirect:billNews.html";
	   	}catch(Exception e) {
	   		e.printStackTrace();
	   	 return "redirect:insertBills.html";
	   	}
	  }
}
