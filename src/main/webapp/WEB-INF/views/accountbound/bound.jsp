<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>账号绑定</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet" href="${path}/html/css/mui.min.css">
<style>
html,body {
	background-color: white;
}

h5 {
	margin: 5px 7px;
}

.mui-toast-container {
	position: fixed;
	top: 25%;
	z-index: 9999;
	width: 100%;
}

.mui-toast-message {
	width: 200px;
	padding: 20px;
	margin: 5px auto;
	font-size: 14px;
	color: white;
	text-align: center;
	background-color: #696969;
	border-radius: 10px;
}

.my-message {
	margin: 0;
	padding: 0;
	position: absolute;
	top: 45%;
	/*  left:50%;  */
	background-color: white;
	width: 100%;
	height: 20px;
	line-height: 20px;
	text-align: center;
	font-size: 18px;
}
/* input::-webkit-outer-spin-button,
			input::-webkit-inner-spin-button {
				-webkit-appearance: none !important;
				margin: 0;
			}
			input[type="number"]{-moz-appearance:textfield;} */
</style>
</head>

<body>
	<c:choose>
		<c:when test="${empty user}">
			<div class="mui-content"
				style="background-color: white; height: 100%;" align="center">
				<div class="mui-input-row"
					style="background-color: white; padding: 10px 8px;" align="left">
					<h4 style="padding: 0; line-height: 32px;">温馨提示</h4>
					<h5 style="padding: 0; margin: 5px 0px; line-height: 18px;">员工需绑定账号方可使用更多功能。</h5>
				</div>
				<div style="height: 1px; background: #c8c7cc; width: 100%;"></div>
				<div class="mui-input-row"
					style="background-color: white; padding: 0px 8px;" align="left">
					<h4 style="padding: 0; line-height: 32px;">基本信息</h4>
					<input type="text" placeholder="输入姓名" name="name" id="name" /> <input
						type="tel" placeholder="输入手机号" name="phone" id="phone" /> <input
						type="hidden" name="memberId" id="memberId">
				</div>
				<div class="mui-content-padded"
					style="margin-top: 0; margin-bottom: 0px;" id="getcodebtdiv">
					<button type="button" class="mui-btn mui-btn-primary mui-btn-block"
						style="padding: 8px 0" id="getcodebt">获取手机验证码</button>
				</div>
				<div class="mui-content-padded"
					style="margin-top: 0; margin-bottom: 0px; display: none;"
					id="timershow">
					<button type="button" class="mui-btn mui-btn-primary mui-btn-block"
						style="padding: 8px 0;">
						<span id="timershowspan">60</span>秒后重新发送
					</button>
				</div>
				<div class="mui-input-row"
					style="background-color: white; padding: 0 8px; display: none;"
					align="left" id="codeinputtxt">
					<input type="text" placeholder="输入验证码" name="code" id="code">
				</div>
				<div class="mui-content-padded"
					style="margin-top: 0; margin-bottom: 0px; display: none;"
					id="boundpostdiv">
					<button type="button" class="mui-btn mui-btn-primary mui-btn-block"
						style="padding: 8px 0" id="boundpostbt">提交</button>
				</div>
			</div>
			<script src="${path}/html/js/mui.min.js"></script>
			<script src="${path}/html/js/app.js"></script>
			<script type="text/javascript" src="${path}/html/js/tools.js"></script>
			<script>
				var pattern = new RegExp(
						"[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
				mui.init();
				var path = '${path}';
				var codeinputtxt = document.getElementById("codeinputtxt");
				var boundpostdiv = document.getElementById("boundpostdiv");
				var getcodebtdiv = document.getElementById("getcodebtdiv");
				var timershowdiv = document.getElementById("timershow");
				var getcodebt = document.getElementById("getcodebt");
				var firstflag = true;
				document.getElementById("getcodebt").addEventListener(
						"tap",
						function(e) {
							/* 输入验证过程 */
							var name = document.getElementById("name").value;
							var phone = document.getElementById("phone").value;
							if (name == undefined || name == null
									|| name.trim() == '') {
								mui.toast("用户名不能为空！");
								return;
							} else if (pattern.test(name)) {
								mui.toast("收件人姓名不能包含特殊字符");
								return;
							} else if (name.length > 30) {
								mui.toast("用户名不能超过30个字符！");
								return;
							} else if (phone == undefined || phone == null
									|| phone.trim() == '') {
								mui.toast("手机号不能为空！");
								return;
							} else if (!isPhone(phone)) {
								mui.toast("手机号格式不正确！");
								return;
							}

							/* 显示控制  */
							if (firstflag) {
								if (codeinputtxt.style.display == "none") {
									codeinputtxt.style.display = "block";
								} else {
									codeinputtxt.style.display = "none";
								}
								if (boundpostdiv.style.display == "none") {
									boundpostdiv.style.display = "block";
								} else {
									boundpostdiv.style.display = "none";
								}
							}

							if (getcodebtdiv.style.display == 'none') {
								getcodebtdiv.style.display = "block";
							} else {
								getcodebtdiv.style.display = "none";
							}
							if (timershowdiv.style.display == "none") {
								timershowdiv.style.display = "block";
							} else {
								timershowdiv.style.display = "none";
							}

							/* 获取手机验证码 */
							mui.ajax(path + '/accountBound/sendCode', {
								data : {
									name : name,
									phone : phone,
								},
								dataType : "json",
								type : "post",
								timeout : 10000,
								success : function(data) {
								}
							});

							/* 倒计时过程 */
							var timespan = document
									.getElementById("timershowspan");
							var timeleft = 60;
							var c = setInterval(function() {
								timespan.innerText = --timeleft;
								if (timeleft == 0) {
									clearInterval(c);
									timespan.innerText = "60";
									firstflag = false;
									if (getcodebtdiv.style.display == "none") {
										getcodebtdiv.style.display = "block";
									}
									getcodebt.innerText = "重新获取手机验证码";
									if (timershow.style.display == "block") {
										timershow.style.display = "none";
									}
								}
							}, 1000);
						});

				document
						.getElementById("boundpostbt")
						.addEventListener(
								"tap",
								function(e) {
									var memberId = document
											.getElementById("memberId").value;
									var name = document.getElementById("name").value;
									var phone = document
											.getElementById("phone").value;
									var code = document.getElementById("code").value;
									if (name == undefined || name == null
											|| name.trim() == '') {
										mui.toast("用户名不能为空！");
										return;
									} else if (name.length > 30) {
										mui.toast("用户名不能超过30个字符！");
										return;
									} else if (phone == undefined
											|| phone == null
											|| phone.trim() == '') {
										mui.toast("手机号不能为空！");
										return;
									} else if (!isPhone(phone)) {
										mui.toast("手机号格式不正确！");
										return;
									} else if (code == undefined
											|| code == null
											|| code.trim() == '') {
										mui.toast('验证码不能为空！');
										return;
									}
									var loading = document
											.getElementById('loading');
									loading.style.height = (document.body.scrollHeight)
											+ "px";
									loading.addEventListener("touchmove",
											function(e) {
												e.preventDefault();
											});
									loading.style.display = "";
									mui
											.ajax(
													path
															+ '/accountBound/bound',
													{
														data : {
															memberId : memberId,
															name : name,
															phone : phone,
															code : code
														},
														dataType : "json",
														type : "post",
														timeout : 10000,
														success : function(data) {
															document
																	.getElementById('loading').style.display = "none";
															mui.toast(data.msg);
															if (data.status == 1) {
																setTimeout(
																		function() {
																			WeixinJSBridge
																					.invoke(
																							"closeWindow",
																							{},
																							function(
																									e) {
																							});
																		}, 1000);
															}
														}
													});
								});
			</script>
		</c:when>
		<c:otherwise>
			<!-- <div class="mui-content my-message" >
				<span>您已经绑定过微信！</span>
			</div> -->
			<script src="${path}/html/js/mui.min.js"></script>
			<script src="${path}/html/js/app.js"></script>
			<script type="text/javascript" src="${path}/html/js/tools.js"></script>
			<script>
				mui.init();
				window.onload = function() {
					mui.toast("您已经绑定过微信！");
					setTimeout(function() {
						WeixinJSBridge.invoke("closeWindow", {}, function(e) {
						});
					}, 3000);
				}
			</script>
		</c:otherwise>
	</c:choose>
	<div class="maskuu" id="loading"
		style="position: absolute; z-index: 99999; left: 0; top: 0; width: 100%; height: 100%; background-color: black; opacity: .6; display: none">
		<p
			style="line-height: 400px; font-size: 1em; text-align: center; color: white;">加载中...</p>
	</div>
</body>

</html>