//存放主要交互逻辑
//模块化 javascript
var seckill = {
		//封装秒杀相关ajax的Url
		URL:{
			now:function(){
				return "#{pageContext.request.contextPath}/seckill/time/now";
			},
			exposer:function(seckillId){
				return "#{pageContext.request.contextPath}/seckill/"+seckillId+"/exposer";
			}
		},
		//验证手机号
		validatePhone:function(phone){
			if(phone&&phone.length==11&&!isNaN(phone)){
				return true;
			}else{
				return false;
			}
		},
		countdown:function(seckillId,nowTime,startTime,endTime){
			var sb = $('#seckill-box');
			if(nowTime>endTime){
				//秒杀结束
				$('#seckill-box').val("秒杀结束");
			}else if(nowTime<startTime){
				//$('#seckill-box').val("秒杀未开始");
				//计时事件绑定
				var killTime = new Date(startTime+1000);
				sb.countdown(killTime,function(event){
					var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
					sb.html("1222");
				}).on('finish.countdown',function(){
					//时间走完后回调事件
					//获取秒杀地址，控制实现逻辑
					Seckill.handleSeckill(seckillId,sb);
				});
			}else{
				//秒杀开始
				Seckill.handleSeckill(seckillId,sb);
			}
		},
		//处理秒杀逻辑
		handleSeckill:function(seckillId,node){
			node.hide().html('<button id="killBtn">开始秒杀</button>');
			$.post(seckill.URL.exposer(seckillId),{},function(res){
				//在回调函数中处理秒杀
			});
		},
		//详情页秒杀逻辑
		detail:{
			//详情页初始化
			init:function(params){
				//手机验证和登录，计时交互
				//规划交互流程
				//在cookie中查找手机号
				var killPhone = $.cookie('killPhone');
				//验证手机号
				if(!seckill.validatePhone(killPhone)){
					//绑定phone
					//控制输出
					var killPhoneModal = $('#killPhoneModal');
					killPhoneModal.modal({
						show:true,//显示弹出层
						backdrop:'static',//禁止位置事件
						keyboard:'false'  //禁止键盘事件
					});
				}
					$('#killPhoneBtn').click(function(){
						
						var inputPhone = $('#killPhoneKey').val();
						if(seckill.validatePhone(inputPhone)){
							//电话写入cookie
							$.cookie(
								'killPhone',inputPhone
							);
							//验证通过，刷新页面
							window.location.reload();
						}else{
							$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300)
						}
					});
					
					//已经登陆
					//计时交互
					var startTime = params['startTime'];
					var endTime = params['endTime'];
					var seckillId = params['seckillId'];
					$.get(seckill.URL.now(),function(res){
						//console.log(res.s);
						//if(res.state==1){
							var timeNow = 	1544000421
							//时间判断
							seckill.countdown(seckillId,timeNow,startTime,endTime);
						//}
					});
				}
			}
};