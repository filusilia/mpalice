<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <c:choose>
        <c:when test="${edit eq true}">
            <title>编辑地址</title>
        </c:when>
        <c:otherwise>
            <title>添加地址</title>
        </c:otherwise>
    </c:choose>
    <title>添加地址</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="${path}/html/css/mui.min.css">
    <style>
        html, body {
            background-color: white;
        }

        header.mui-bar {
            display: none;
        }

        .mui-bar-nav ~ .mui-content {
            padding: 0;
        }

        .mui-input-group .mui-input-row {
            height: 50px;
        }

        .mui-input-group .mui-input-row input {
            height: 50px;
        }

        #topPopover {
            position: fixed;
            top: 16px;
            right: 6px;
        }

        #topPopover .mui-popover-arrow {
            left: auto;
            right: 6px;
        }

        p {
            text-indent: 22px;
        }

        span.mui-icon {
            font-size: 14px;
            color: #007aff;
            margin-left: -15px;
            padding-right: 10px;
        }

        .mui-popover {
            height: 300px;
        }

        .mui-toast-container {
            position: fixed;
            top: 20%;
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
            background-color: #575757;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<header class="mui-bar mui-bar-head">
    <a href="#topPopover" class="mui-btn mui-btn-link mui-pull-left">菜单</a>
</header>
<c:choose>
    <c:when test="${edit eq true}">
        <div class="mui-content" style="background-color: white;">
            <div class="mui-content-padded" style="margin:0;margin-top: 1px;">
                <form class="mui-input-group">
                    <div class="mui-input-row">
                        <a href="#topPopover" class="mui-btn mui-btn-primary mui-btn-block mui-btn-outlined"
                           id="firstLevel"
                           style="float:left;width: 100%;text-align: left;height: 100%;line-height: 28px;border:0px solid transparent">点击选择收件地址</a>
                    </div>
                    <div class="mui-input-row" id="areanamediv" style="display: none;">
                        <input type="text" name="areaname" value="" id="areaname"/>
                        <input type="hidden" name="area" value='<c:out value="${address.area}"></c:out>' id="area"/>
                        <input type="hidden" name="memberid" value='<c:out value="${address.memberId}"></c:out>'
                               id="memberid"/>
                        <input type="hidden" name="id" value='<c:out value="${address.id}"></c:out>' id="addressid"/>
                    </div>

                    <div class="mui-input-row">
                        <input type="text" placeholder="请输入详细地址" name="info" id="info"
                               value='<c:out value="${address.info}" escapeXml="false"></c:out>'>
                    </div>
                    <div class="mui-input-row">
                        <input type="text" placeholder="请输入收件人姓名" name="name" id="name"
                               value='<c:out value="${address.name}" escapeXml="false"></c:out>'>
                    </div>
                    <div class="mui-input-row">
                        <input type="tel" style="border: 0" placeholder="请输入收件人联系方式" name="phone" id="phone"
                               value='<c:out value="${address.phone}"></c:out>'>
                    </div>
                </form>
            </div>
            <div class="mui-content-padded" style="margin-top: 20px;margin-bottom: 0px;">
                <button type="button" class="mui-btn mui-btn-primary mui-btn-block" style="padding:8px 0" id="updatebt">
                    保存
                </button>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="mui-content" style="background-color: white;">
            <div class="mui-content-padded" style="margin:0;margin-top: 1px;">
                <form class="mui-input-group">
                    <div class="mui-input-row">
                        <a href="#topPopover" class="mui-btn mui-btn-primary mui-btn-block mui-btn-outlined"
                           id="firstLevel"
                           style="float:left;width: 100%;text-align: left;height: 100%;line-height: 28px;border:0px solid transparent">点击选择收件地址</a>
                    </div>
                    <div class="mui-input-row" id="areanamediv" style="display: none;">
                        <input type="text" name="areaname" value="" id="areaname"/>
                        <input type="hidden" name="area" value="" id="area"/>
                        <!-- <input type="hidden" name="weixin" value='<c:out value="${openId}"></c:out>' id="weixin" /> -->
                    </div>

                    <div class="mui-input-row">
                        <input type="text" placeholder="请输入详细地址" name="info" id="info" value="">
                    </div>
                    <div class="mui-input-row">
                        <input type="text" placeholder="请输入收件人姓名" name="name" id="name" value="">
                    </div>
                    <div class="mui-input-row">
                        <input type="tel" style="border: 0" placeholder="请输入收件人联系方式" name="phone" id="phone" value="">
                    </div>
                </form>
            </div>
            <div class="mui-content-padded" style="margin-top: 20px;margin-bottom: 0px;">
                <button type="button" class="mui-btn mui-btn-primary mui-btn-block" style="padding:8px 0" id="savebt">
                    保存
                </button>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<!--右上角弹出菜单-->
<div id="topPopover" class="mui-popover">
    <!--<div class="mui-popover-arrow"></div>-->

    <div class="mui-scroll-wrapper">
        <!--<div style="background:#EEEEEE;height: 30px;line-height: 30px;padding-left: 10px;">请点击选择城市</div>-->
        <div class="mui-scroll">
            <div style="background:#EEEEEE;height: 30px;line-height: 30px;padding-left: 12px;">请点击选择省份：</div>
            <ul class="mui-table-view" id="provice_area">
            </ul>
        </div>
    </div>
</div>
<div id="middlePopover" class="mui-popover" style="position: absolute;top:20%;left: 10%;">
    <!--<div class="mui-popover-arrow"></div>-->
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div style="background:#EEEEEE;height: 30px;line-height: 30px;padding-left: 12px;">请点击选择市区：</div>
            <ul class="mui-table-view" id="city_area">

            </ul>
        </div>
    </div>
</div>
<!--右下角弹出菜单-->
<div id="bottomPopover" class="mui-popover" style="position: absolute;top:20%;left: 10%;">
    <!--<div class="mui-popover-arrow"></div>-->
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div style="background:#EEEEEE;height: 30px;line-height: 30px;padding-left: 12px;">请点击选择街道（乡镇）：</div>
            <ul class="mui-table-view" id="district_area">

            </ul>
        </div>
    </div>

</div>
<div class="maskuu" id="loading"
     style="position:absolute;z-index:99999;left:0;top:0;width:100%;height:100%;background-color: black;opacity:.6;display: none">
    <p style="line-height: 400px; font-size:1em; text-align: center;color: white;">加载中...</p>
</div>
</body>
<script src="${path}/html/js/mui.js"></script>
<script src="${path}/html/js/app.js"></script>
<script type="text/javascript" src="${path}/html/js/plug_area.js"></script>
<script type="text/javascript" src="${path}/html/js/tools.js"></script>

<script>
    var path = "${path}";
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
    mui.init();
    mui.ready(function () {
        plug_area_province();
        <c:if test="${edit eq true}">
        var addressname = plug_code_area(<c:out value="${address.area}"></c:out>);
        document.getElementById("areaname").value = addressname;
        document.getElementById("firstLevel").innerText = addressname;
        </c:if>
    });
    mui('.mui-scroll-wrapper').scroll();
    var updatebt = document.getElementById("updatebt");
    if (updatebt) {
        updatebt.addEventListener("tap", function (e) {
            var area = document.getElementById("area").value;
            var phone = document.getElementById("phone").value;
            var name = document.getElementById("name").value;
            var info = document.getElementById("info").value;
            var areaname = document.getElementById("areaname").value;
            var addressid = document.getElementById("addressid").value;
            var memberid = document.getElementById("memberid").value;
            if (area == undefined || area == null || area.trim() == '') {
                mui.toast("收件地址不能为空！");
            } else if (info == undefined || info == null || info.trim() == '') {
                mui.toast("详细地址不能为空！");
                return;
            } else if (pattern.test(info)) {
                mui.toast("详细地址不能包含特殊字符！");
                return;
            } else if (info.length > 300) {
                mui.toast("详细地址不能超过300个字符！");
                return;
            } else if (name == undefined || name == null || name.trim() == '') {
                mui.toast("收件人姓名不能为空！");
                return;
            } else if (pattern.test(name)) {
                mui.toast("收件人姓名不能包含特殊字符");
                return;
            } else if (name.length > 50) {
                mui.toast("收件人姓名不能超过50个字符！");
                return;
            } else if (phone == undefined || phone == null || phone.trim() == '') {
                mui.toast("手机号不能为空！");
                return;
            } else if (!isPhone(phone)) {
                mui.toast("手机号格式不正确！");
                return;
            }
            document.getElementById('loading').style.display = "";
            document.getElementById('loading').addEventListener("touchmove", function (e) {
                e.preventDefault();
            });
            mui.ajax(path + '/address/update_address', {
                data: {
                    name: name,
                    phone: phone,
                    area: area,
                    info: info,
                    id: addressid,
                    memberId: memberid,
                    enterpriseId: '<c:out value="${enterpriseId}"></c:out>',
                    giftId: '<c:out value="${giftId}"></c:out>',
                },
                dataType: "json",
                type: "post",
                timeout: 10000,
                success: function (data) {
                    document.getElementById('loading').style.display = "none";
                    mui.toast(data.msg);
                    if (data.status == 200) {
                        setTimeout(function () {
                            window.location = data.url;
                        }, 1000);
                    }
                }
            });
        });
    }

    var savebt = document.getElementById("savebt");
    if (savebt) {
        savebt.addEventListener("tap", function (e) {
            var area = document.getElementById("area").value;
            var phone = document.getElementById("phone").value;
            var name = document.getElementById("name").value;
            var info = document.getElementById("info").value;
            var areaname = document.getElementById("areaname").value;
            if (area == undefined || area == null || area.trim() == '') {
                mui.toast("收件地址不能为空！");
                return;
            } else if (info == undefined || info == null || info.trim() == '') {
                mui.toast("详细地址不能为空！");
                return;
            } else if (pattern.test(info)) {
                mui.toast("详细地址不能包含特殊字符！");
                return;
            } else if (info.length > 300) {
                mui.toast("详细地址不能超过300个字符！");
                return;
            } else if (name == undefined || name == null || name.trim() == '') {
                mui.toast("收件人姓名不能为空！");
                return;
            } else if (pattern.test(name)) {
                mui.toast("收件人姓名不能包含特殊字符");
                return;
            } else if (name.length > 50) {
                mui.toast("收件人姓名不能超过50个字符！");
                return;
            } else if (phone == undefined || phone == null || phone.trim() == '') {
                mui.toast("手机号不能为空！");
                return;
            } else if (!isPhone(phone)) {
                mui.toast("手机号格式不正确！");
                return;
            }
            var loading = document.getElementById('loading');
            loading.style.height = (document.body.scrollHeight) + "px";
            loading.addEventListener("touchmove", function (e) {
                e.preventDefault();
            });
            loading.style.display = "";
            mui.ajax(path + '/address/save_address', {
                data: {
                    name: name,
                    phone: phone,
                    area: area,
                    info: info,
                    enterpriseId: '<c:out value="${enterpriseId}"></c:out>',
                    giftId: '<c:out value="${giftId}"></c:out>',
                    memberId: '<c:out value="${memberId}"></c:out>'
                },
                dataType: "json",
                type: "post",
                timeout: 10000,
                success: function (data) {
                    document.getElementById('loading').style.display = "none";
                    mui.toast(data.msg);
                    if (data.status == 200) {
                        setTimeout(
                                function () {
                                    window.location = data.url;
                                }, 1000);
                    }
                }
            });
        });
    }
</script>
</html>
