<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title><c:out value="${article.title}" escapeXml="false"></c:out></title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<link rel="stylesheet" href="../html/css/mui.min.css">
		<style>
			html,body {
				/*background-color: #efeff4;*/
				background-color: white;
			}
			header.mui-bar {
				display: none;
			}
			img {
				max-width: 100%;
			}
		</style>
	</head>
	<body>
		<div class="mui-content" style="background-color: white;">
			<img src="${article.image }" style="height: 100%; width: 100%; margin: 0; padding:0;"/>
		</div>
		<!-- <div style="height: 1px;background:#c8c7cc;width: 100%;margin-top: 10px;opacity: .5;"></div> -->
		<div class="mui-content" style="margin: 0; padding: 15px 15px 15px 15px; background-color: white;" >
		   	<span style="font-size:1em;"><c:out value="${article.title }" escapeXml="false"></c:out></span><br/>
		   	<span style="font-size:0.6em; color:#808080;">${article.createTime}</span><br/>
		   	<span style="font-size:0.8em;"><c:out value="${article.info}" escapeXml="false"></c:out></span>
		</div>
		<script src="${path }/html/js/mui.min.js"></script>
		<script src="${path }/html/js/app.js"></script>
		<script type="text/javascript" charset="utf-8">
			mui.init();
			if (mui.os.plus) {
				mui.plusReady(function() {
					
				});
			} else {
				mui.ready(function() {
				});
			}
			
			/*document.getElementById("switch").addEventListener('toggle', function(e) {
				if (e.detail.isActive) {
					slider.slider({
						interval: 5000
					});
				} else {
					slider.slider({
						interval: 0
					});
				}
			});*/
		</script>
	</body>
</html>
<script type="text/javascript">
window.onload= function(){
	var images = document.querySelectorAll("img"); 
	mui.each(images,function(key,value){
		value.style.height = "auto";
	}); 
	}
</script>