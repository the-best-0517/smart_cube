javabean �������ݿ�

	����ɾ����        
		  ֧���޲δ������вδ���
		//data  	 Map<String, Object>���� 		 ���ڴ洢����  keyֵ Ҫ��SQL ������#{key}��ͬ
		//sqlPath 	 String����	 ����SQL·��   SQL�ļ���Ҫ�� src/main/resources/sql ֱ��д����·������
		LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath(sqlPath);
			int rows = exchangeDbService.saveDb(lsc);  
			
	��      	
		֧���޲δ������вδ���
		����ֵΪ List<Map<String,Object>>
		LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath(sqlPath);
			List<Map<String,Object>> list = exchangeDbService.selectDb(lsc); 
			
		���޲δ���
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath(sqlPath);
			List<Map<String,Object>> list = exchangeDbService.selectDbNoParam(lsc); 
			
		