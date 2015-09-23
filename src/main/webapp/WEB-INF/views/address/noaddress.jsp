<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/base.jsp"%>
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
			html,body{
				background-color: white;
			}
			h5{
				margin: 5px 7px;
			}
			.addressdesc{
				color: #8D8D8D;
				font-size: 18px;
				font-family: "微软雅黑";
				height: 100%;
				line-height: 100px;
			}
		</style>
	</head>
	<body>
		<div class="mui-content" style="background-color: white;height: 100%;" align="center">
			<div class="mui-content-padded" style="height: 100px;">
				<span class="addressdesc">您还没有添加地址！</span>
			</div>
			<div class="mui-content-padded" style="margin-top: 0;margin-bottom: 0px;">
				<button type="button" class="mui-btn mui-btn-primary mui-btn-block" style="padding:8px 0" id="addadressbt">添加地址</button>
			</div>
		</div>
		<script src="${path}/html/js/mui.min.js"></script>
		<script src="${path}/html/js/app.js"></script>
		<script>
			mui.init();
			document.getElementById("addadressbt").addEventListener("tap",function(e){
				var memberId = '<c:out value="${memberId}"></c:out>';
				var enterpriseId = '<c:out value="${enterpriseId}"></c:out>';
				var giftId = '<c:out value="${giftId}"></c:out>';
				window.location = '${path}/address/add_address.jhtml?memberId='+ memberId + "&enterpriseId="+enterpriseId+"&giftId=" + giftId;
			});
		</script>
	</body>

</html>