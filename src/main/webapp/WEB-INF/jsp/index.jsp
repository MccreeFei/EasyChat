<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head>
    <title>EasyChat登录</title>
    <link rel="icon" href="<c:url value="/resources/image/favicon.ico"/> " type="image/x-icon">
</head>
<body background="<c:url value="/resources/image/1.jpg"/> ">
<h1>欢迎访问EasyChat :)</h1>

<div class="login" style="margin-top:50px;">

    <div class="header">
        <div class="switch" id="switch"><a class="switch_btn_focus" id="switch_qlogin" href="javascript:void(0);" tabindex="7">快速登录</a>
            <a class="switch_btn" id="switch_login" href="javascript:void(0);" tabindex="8">快速注册</a><div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 64px; left: 0px;"></div>
        </div>
    </div>


    <div class="web_qr_login" id="web_qr_login" style="display: block; height: 235px;">

        <!--登录-->
        <div class="web_login" id="web_login">


            <div class="login-box">


                <div class="login_form">
                    <form action="<c:url value="/chat"/> " name="loginform" accept-charset="utf-8" id="login_form" class="loginForm" method="post"><input type="hidden" name="did" value="0"/>
                        <input type="hidden" name="to" value="log"/>
                        <div class="uinArea" id="uinArea">
                            <label class="input-tips" for="u">名称：</label>
                            <div class="inputOuter" id="uArea">

                                <input type="text" id="u" name="name" class="inputstyle"/>
                            </div>
                        </div>
                        <div class="pwdArea" id="pwdArea">
                            <label class="input-tips" for="p">密码：</label>
                            <div class="inputOuter" id="pArea">

                                <input type="password" id="p" name="password" class="inputstyle"/>
                            </div>
                        </div>

                        <div style="padding-left:50px;margin-top:20px;">
                            <input id="loginBtn" type="button" value="登 录" style="width:150px;" class="button_blue"/>
                        </div>
                    </form>
                    <input id="replayLoginUrl" type="hidden" value="<c:url value="/reply/login"/> ">
                </div>

            </div>

        </div>
        <!--登录end-->
    </div>

    <!--注册-->
    <div class="qlogin" id="qlogin" style="display: none; ">

        <div class="web_login"><form name="form2" id="regUser" accept-charset="utf-8"  action="" method="post">
            <input type="hidden" name="to" value="reg"/>
            <input type="hidden" name="did" value="0"/>
            <ul class="reg_form" id="reg-ul">
                <div id="userCue" class="cue">欢迎注册EasyChat！</div>
                <li>

                    <label for="user"  class="input-tips2">名称：</label>
                    <div class="inputOuter2">
                        <input type="text" id="user" name="user" maxlength="16" class="inputstyle2"/>
                    </div>

                </li>

                <li>
                    <label for="passwd" class="input-tips2">密码：</label>
                    <div class="inputOuter2">
                        <input type="password" id="passwd"  name="passwd" maxlength="16" class="inputstyle2"/>
                        <input id="replyRegistUrl" type="hidden" value="<c:url value="/reply/regist"/> ">
                    </div>

                </li>
                <li>
                    <label for="passwd2" class="input-tips2">确认密码：</label>
                    <div class="inputOuter2">
                        <input type="password" id="passwd2" name="" maxlength="16" class="inputstyle2" />
                    </div>

                </li>

                <li>
                    <div class="inputArea">
                        <input type="button" id="reg"  style="margin-top:10px;margin-left:85px;" class="button_blue" value="注册"/>
                    </div>

                </li><div class="cl"></div>
            </ul></form>


        </div>


    </div>
    <!--注册end-->
</div>
<div class="jianyi">推荐使用Chrome内核浏览器访问本站</div>

<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="/resources/plugin/sweetalert/sweetalert.min.js"/> "></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugin/sweetalert/sweetalert.css"/> ">
<script type="text/javascript" src="<c:url value="/resources/js/login.js?v=20175131017"/> "></script>
<link href="<c:url value="/resources/css/login.css"/> " rel="stylesheet" type="text/css" />
</body></html>