<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>资讯</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<link rel="stylesheet" href="../html/css/mui.min.css">
		<style>
			html,body {
				/*background-color: #efeff4;*/
				background-color: white;
			}
			header.mui-bar{
				display: none;
			}
			.mui-bar-nav~.mui-content{
				padding: 0;
			}
		</style>
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-center"></a>
			<h1 class="mui-title">图片轮播</h1>
		</header>
		<div class="mui-content">
			<!--<ul class="mui-table-view mui-table-view-chevron">
				<li id="switch" class="mui-table-view-cell">
					定时轮播
					<div class="mui-switch mui-active">
						<div class="mui-switch-handle"></div>
					</div>
				</li>
			</ul>-->
			<div id="slider" class="mui-slider">
				<div class="mui-slider-group mui-slider-loop">
					<c:forEach items="${recommend}" var="re">
					<div class="mui-slider-item mui-slider-item-duplicate">
						<a href="showArticle?articleId=${re.id }">
							<img src="${re.image }">
							<!-- <img src="../html/images/shuijiao.jpg"> -->
							<!-- <img src="../${re.image }"> -->
						</a>
					</div>
					</c:forEach>
				</div>
				<div class="mui-slider-indicator">
				<c:forEach items="${recommend}" var="rec" varStatus="status">
					<c:choose>
						<c:when test="${status.first }"></c:when>
						<c:when test="${status.last }"></c:when>
						<c:when test="${status.index == 1 }">
							<div class="mui-indicator mui-active"></div>
						</c:when>
						<c:otherwise>
							<div class="mui-indicator"></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</div>
			</div>
		</div>
		<div style="height: 1px;background:#c8c7cc;width: 100%;margin-top: 10px;opacity: .5;"></div>
		<div class="mui-content">
		    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
		       <c:forEach items="${all}" var="article">
			        <li class="mui-table-view-cell">
			        	<a href="showArticle?articleId=${article.id }">
			            <div class="mui-table">
			               <h5 class="mui-ellipsis"><c:out value="${article.title}" escapeXml="false"></c:out></h5> 
			            </div>
			            <div class="mui-table" align="center" style="background:url(${article.icon}) no-repeat;background-size: cover;background-position: center;width:100%;height: 100px;">
			            </div>
			            <!-- <div class="mui-table" align="center" style="background:url(../html/images/yuantiao.jpg) no-repeat;background-size: cover;background-position: center;width:100%;height: 100px;">
			            </div> -->
			            <!-- <div class="mui-table" align="center" style="background:url(../${article.icon }) no-repeat;background-size: cover;background-position: center;width:100%;height: 100px;">
			            </div> -->
			        	</a>
			        </li>
			        
			        <%-- <li class="mui-table-view-cell">
			       		<img class="mui-media-object mui-pull-left" src="${a.icon }">
			            <div class="mui-table">
			               <h5 class="mui-ellipsis">${a.title }</h5> 
			            </div>
			            <div class="mui-table" align="center" style="background:url(../images/shuijiao.jpg) no-repeat;background-size: cover;background-position: center;width:100%;height: 100px;">
			            	
			            </div>
			        </li> --%>
		        </c:forEach>
		    </ul>
		</div>
		<script src="${path }/html/js/mui.min.js"></script>
		<script src="${path }/html/js/app.js"></script>
		<script type="text/javascript" charset="utf-8">
			mui.init();
			var slider = mui("#slider");
			slider.slider({
				interval: 3000
			});
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