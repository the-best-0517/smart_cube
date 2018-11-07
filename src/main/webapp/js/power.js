function getMyName(){ 
 　　var url = "../login/checkRole.do";
 	$.get(url,function(res){
 		console.log("ss:"+res.data);
 		if(res.data.length==0){
 			$('#sys').addClass("hi");
				console.log("ok");
			return;
 		}
 			if(res.data[0].roleId=="normal"){
 			//	$('#sidebar-list').find("li").eq[1].addClass("hi");
 				$('#sys').addClass("hi");
 			}else if(res.data[0].roleId=="admin"){
 				$('#nor').addClass("hi");
 			}else{
 			//	$('#sys').addClass("hi");
 		}
 	});
} 
