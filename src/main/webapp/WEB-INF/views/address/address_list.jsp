<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>选择地址</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<link rel="stylesheet" href="${path}/html/css/mui.min.css">
		<style>
			html,
			body {
				background-color: white;
			}
			header.mui-bar {
				display: none;
			}
			.mui-bar-nav~.mui-content {
				padding: 0;
			}
			.title {
				margin: 20px 15px 10px;
				color: #6d6d72;
				font-size: 15px;
			}
			.mui-ellipsis{
				white-space: normal;
			}
			h5{
				color: black;
				font-family: "微软雅黑";
				font-weight: bold;
			}
			.mui-h6.mui-ellipsis{
				color: black;
				font-family: "微软雅黑";
			}
			.mui-toast-container{
				position:fixed;
				top: 20%;
				z-index:9999;
				width:100%;
				}
			.mui-toast-message{
				width:200px;
				padding:20px;
				margin:5px auto;
				font-size:14px;
				color:white;
				text-align:center;
				background-color:#575757;
				border-radius:10px;
			}
		</style>
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">缩略图居左</h1>
		</header>
		<div class="mui-content" style="background-color: white;">
			<input type="hidden" id="num" value="${fn:length(addresslist)}">
		    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed" style="margin-top: 0;" id="uladdress">
		    
		    	<c:forEach var="address" items="${addresslist}">
			    	<li class="mui-table-view-cell" id="addressli_${address.id}">
			            <div class="mui-table">
			                <div class="mui-table-cell mui-col-xs-12">
			                    <h5 style="float: left;"><c:out value="${address.name}" escapeXml="false"></c:out> </h5>
			                    <h5 style="text-align: right;"><c:out value="${address.phone}"></c:out></h5>
			                    <p class="mui-h6 mui-ellipsis"><c:out value="${address.info}" escapeXml="false"></c:out> </p>
			                </div>
			                <div class="mui-table-cell mui-col-xs-2 mui-radio mui-text-right">
			                	<input name="addressradio" type="radio" style="line-height: 66px;right: 8px;" id="addressradio_${address.id}" class="addressradio" value="${address.id}">	
			                </div>
			            </div>
			            <div style="width: 100%;height: 1px;background-color: #E3E2E5;margin:10px 0;"></div>
			            <div class="mui-table">
			            	<div class="mui-table-cell mui-col-xs-7 mui-left"  align="center" style="border-right: 1px solid #E3E2E5;"><img src="${path}/html/images/4.png" style="width: 24px;height: 24px;" class="cancel" id="dustbin_${address.id}"/></div>
			            	<div class="mui-table-cell mui-col-xs-7 mui-right" align="center"><img src="${path}/html/images/2.png" style="width: 24px;height: 24px;" class="edit" id="addressedit_${address.id}"/></div>
			            </div>
			        </li>
		    	</c:forEach>
		    </ul>
		    
		    <div class="mui-content-padded" style="margin-top: 20px;margin-bottom: 0px;">
				<button type="button" class="mui-btn mui-btn-primary mui-btn-block" style="padding:8px 0;width:45%;float: right;" id="orderbt">提交</button>
				<button type="button" class="mui-btn mui-btn-success mui-btn-block" style="padding:8px 0;width:45%;float: left;" id="addaddressbt">添加新地址</button>
			</div>
		</div>
		<div class="maskuu" id="loading" style="position:absolute;z-index:99999;left:0;top:0;width:100%;height:100%;background-color: black;opacity:.6;display: none">
			<p style="line-height: 400px; font-size:1em; text-align: center;color: white;">加载中...</p>
		</div>
	</body>
	<script src="${path}/html/js/mui.min.js"></script>
	<script src="${path}/html/js/app.js"></script>
	<script>
		mui.init();
		var path = '${path}';
		
		/* var pattern=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im; */
		var num = document.getElementById("num").value;
		var srcTargetId = 0;
		mui.each(document.querySelectorAll(".addressradio"), function(key,value){
			if(value.checked) {
				var id = value.getAttribute("value");
				srcTargetId = id;
				var destCancelNode = document.getElementById("dustbin_"+id); 
				destCancelNode.setAttribute("src",path + "/html/images/3.png");
				destCancelNode.addEventListener("tap", canceladdress, false);
				var destEditNode =document.getElementById("addressedit_"+id); 
				destEditNode.setAttribute("src", path + "/html/images/1.png");
				destEditNode.addEventListener("tap", editaddress, false);	
			}
			value.addEventListener("change",function(e){
				if(value.checked){
					var id = value.getAttribute("value");
					if(id != srcTargetId && srcTargetId != 0){
						var srcCancelNode = document.getElementById("dustbin_"+srcTargetId);
						if(srcCancelNode !== undefined && srcCancelNode !== null){
							srcCancelNode.setAttribute("src",path + "/html/images/4.png");
							srcCancelNode.removeEventListener("tap",canceladdress,false);	
						}
						var srcEditNode =document.getElementById("addressedit_"+srcTargetId); 
						if(srcEditNode !== undefined && srcEditNode !== null){							
							srcEditNode.setAttribute("src", path + "/html/images/2.png");
							srcEditNode.removeEventListener("tap",editaddress,false);
						}
					}
					srcTargetId = id;
					var destCancelNode = document.getElementById("dustbin_"+id); 
					destCancelNode.setAttribute("src",path + "/html/images/3.png");
					destCancelNode.addEventListener("tap", canceladdress, false);
					var destEditNode =document.getElementById("addressedit_"+id); 
					destEditNode.setAttribute("src", path + "/html/images/1.png");
					destEditNode.addEventListener("tap", editaddress, false);			
				}
			});
		});
		
		function editaddress(){
			var enterpriseId='<c:out value="${enterpriseId}"></c:out>';
			var memberId='<c:out value="${memberId}"></c:out>';
			var giftId='<c:out value="${giftId}"></c:out>';
			window.location = path + "/address/modify_address?addressId=" + srcTargetId+ "&enterpriseId=" + enterpriseId +"&memberId=" + memberId + "&giftId=" + giftId;
		};
		
		function canceladdress(){
			var enterpriseId='<c:out value="${enterpriseId}"></c:out>';
			var memberId='<c:out value="${memberId}"></c:out>';
			var giftId='<c:out value="${giftId}"></c:out>';
			var result = window.confirm("你确定要删除该地址信息？");
			if(result) {
				var loading = document.getElementById('loading');
			    loading.style.height=(document.body.scrollHeight)+"px";
			    loading.addEventListener("touchmove",function(e){
			        e.preventDefault();
			    });
			    loading.style.display = "";
				mui.ajax(path + '/address/delete_address', {
					data:{
						addressId:srcTargetId,
					},
					dataType:"json",
					type:"post",
					timeout:10000,
					success:function(data){
						document.getElementById('loading').style.display = "none";
					  	if(data.status == 200) {
						  	var linode = document.getElementById("addressli_" + srcTargetId);
						  	document.getElementById("uladdress").removeChild(linode);
						  	mui.toast(data.msg);
						  	num--;
						  	if(num == 0) {
						  		window.location = path + "/address/address_list.jhtml?memberId="+ memberId +"&enterpriseId="+ enterpriseId +"&giftId="+ giftId;
						  	}
					  	} else {
						  	mui.toast(data.msg);
					  	}
					}
				});
			}
		};
		
		document.getElementById("orderbt").addEventListener("tap", function(e){
			if(srcTargetId==0) {
				mui.toast("请先选择地址！");
				return;
			} else {
				document.getElementById('loading').style.display = "";
				document.getElementById('loading').addEventListener("touchmove",function(e){
			        e.preventDefault();
			    });
				mui.ajax(path + '/integralmall/exchangepost', {
					data : {
						enterpriseId : '<c:out value="${enterpriseId}"></c:out>',
						memberId : '<c:out value="${memberId}"></c:out>',
						giftId : '<c:out value="${giftId}"></c:out>',
						addressId : srcTargetId
					},
					dataType : "json",
					type : "post",
					timeout : 10000,
					success : function(data) {
						document.getElementById('loading').style.display = "none";
						mui.toast(data.msg);
						if (data.status == 200) {
							if(data.flag == 0) {
								// 兑换成功
								setTimeout(function() {
									//WeixinJSBridge.invoke("closeWindow",{},function(e){});
									window.location.href = path + "/integralmall/exchangehistory.jhtml?memberId="+data.memberId;
								}, 1500);
							}
						}
					}
				});
			}
		});
		document.getElementById("addaddressbt").addEventListener("tap",function(e){
			var memberId = '<c:out value="${memberId}"></c:out>';
			var enterpriseId = '<c:out value="${enterpriseId}"></c:out>';
			var giftId = '<c:out value="${giftId}"></c:out>';
			window.location = '${path}/address/add_address.jhtml?memberId='+ memberId + "&enterpriseId="+enterpriseId+"&giftId=" + giftId;
		});
	</script>
</html>