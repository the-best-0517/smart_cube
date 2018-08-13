javabean 操作数据库

	增、删、改        
		  支持无参传入与有参传入
		//data  	 Map<String, Object>类型 		 用于存储参数  key值 要与SQL 参数名#{key}相同
		//sqlPath 	 String类型	 设置SQL路径   SQL文件需要在 src/main/resources/sql 直接写后面路径即可
		LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath(sqlPath);
			int rows = exchangeDbService.saveDb(lsc);  
			
	查      	
		支持无参传入与有参传入
		返回值为 List<Map<String,Object>>
		LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath(sqlPath);
			List<Map<String,Object>> list = exchangeDbService.selectDb(lsc); 
			
		仅无参传入
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath(sqlPath);
			List<Map<String,Object>> list = exchangeDbService.selectDbNoParam(lsc); 
			
		